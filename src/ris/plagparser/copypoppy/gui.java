package ris.plagparser.copypoppy;
/*
User: NIK FARIS AIMAN BIN NIK RAHIMAN
*/
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.comments.Comment;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;

public class gui extends JPanel {
    /* GUI Variable Guideline
    b1 = Button for select file
    p1 = main panel
    i1 in l3 = copypoppy logo inside label
    l1 = "Number of Files to Pop"
    l2 = "Files Selected"
    f1 = text field for how many file that user wants to add
    b2 = Button for detect plagiarism
     */

    //Declare Local Database
    File db = new File("database/csc");
    File[] dbList = new File("database/csc").listFiles();
    int dbSize = dbList.length;
    filestruct[] database = new filestruct[dbSize];

    //Declare Selected File
    File selectedFile;
    filestruct selFile;

    int fileCount=0;

    JButton b1 = new JButton(new ButtonAction1());
    JPanel p1 = new JPanel();
    //JLabel l1 = new JLabel("Code to Exclude"); //Future feature
    JLabel l2 = new JLabel("File View");
    JTextField f1 = new JTextField();
    JTextArea a1 = new JTextArea();
    JScrollPane sp1 = new JScrollPane(a1);
    ImageIcon i1 = new ImageIcon("res/Copypoppy2-sized.png");
    JLabel l3 = new JLabel(i1);
    JButton b2 = new JButton(new ButtonAction2());
    JButton b3 = new JButton(new ButtonAction3());
    JLabel l4;

    JTextArea a2 = new JTextArea();
    PrintStream stream = new PrintStream(new TextAreaOutputStream(a2));
    JScrollPane sp2 = new JScrollPane(a2);

    public gui() {
        try {
            //Read Files in Local Database
            a1.append("Database files:\n");

            for (int i=0;i<dbList.length;i++) {
                if (dbList[i].isFile()) {
                    String fpath = dbList[i].getAbsolutePath();

                    //Get total line from file
                    Path path = Paths.get(dbList[i].getAbsolutePath());
                    int lines = 0;
                    lines = (int) Files.lines(path).count();

                    int fsize = dbSize;
                    File temp = new File(fpath);
                    String fname = temp.getName();
                    String flang = FilenameUtils.getExtension(fpath);
                    database[i] = new filestruct(fname, flang, fpath, temp, lines, fsize);
                    fileCount++;
                    a1.append(i+1 + ". " + database[i].getFileName() + "\n");
                }
                else;
            }

            setLayout(null);
            add(p1);
            l3.setBounds(10,10,i1.getIconWidth(),i1.getIconHeight());
            add(l3);
            b1.setBounds(10,80,200,25);
            add(b1);
            l2.setBounds(225,110,145,25);
            add(l2);
            sp1.setBounds(225,140,500,100);
            add(sp1);
            b2.setBounds(755,140,200,25);
            b2.setEnabled(false);
            add(b2);
            b3.setBounds(755,170,200,25);
            b3.setEnabled(false);
            add(b3);
            l4 = new JLabel("Total Files Currently in Database: "+fileCount);
            l4.setBounds(10,140,200,25);
            add(l4);

            a1.setEditable(false);
            a2.setEditable(false);

            System.setOut(stream);
            System.setErr(stream);
            sp2.setBounds(225,270,500,200);
            add(sp2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class TextAreaOutputStream extends OutputStream {

        private JTextArea textArea1;

        public TextAreaOutputStream(JTextArea textArea1) {
            this.textArea1 = textArea1;
        }

        @Override
        public void write(int character) throws IOException {
            textArea1.append(String.valueOf((char) character));
            textArea1.setCaretPosition(textArea1.getDocument().getLength());
        }
    }

    public class ButtonAction1 extends AbstractAction implements ActionListener {
        public ButtonAction1() {
            super("Select File to Pop");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                JFrame fileSel = new JFrame("Select Source Code File");

                //Choose file, create and write new local filestruct array
                JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
                int result = fileChooser.showOpenDialog(fileSel);
                if (result == JFileChooser.APPROVE_OPTION) {
                    b1.setEnabled(false);
                    selectedFile = fileChooser.getSelectedFile();
                    String fpath = selectedFile.getAbsolutePath();

                    //Get total line from file
                    Path path = Paths.get(selectedFile.getAbsolutePath());
                    int lines = 0;
                    lines = (int) Files.lines(path).count();

                    int fsize = 1;
                    File temp = new File(fpath);
                    String fname = temp.getName();
                    String flang = FilenameUtils.getExtension(fpath);
                    selFile = new filestruct(fname, flang, fpath, temp, lines, fsize);

                    //add selected file path to list
                    a1.append("\nFile selected:\n");
                    a1.append("1. " + selFile.getFileName() + "\n");
                    b2.setEnabled(true);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public class ButtonAction2 extends AbstractAction implements ActionListener {
        public ButtonAction2() {
            super("POP!");
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                b2.setEnabled(false);
                b3.setEnabled(true);

                boolean commentFlag; //Flag for Multiline Comments

                Scanner localScan = new Scanner(selFile.getFile());

                int lW = 0;
                int localSize = selFile.line.length;
                int localSizeSim = localSize;

                //Put Content into Appropriate Line for Selected File (Local File)
                while (localScan.hasNextLine()) {
                    selFile.line[lW] = localScan.nextLine();
                    lW++;
                }
                localScan.close();
                lW = 0;


                List<Comment> localComments = null; //Declare Comment List for Selected File (Local File)
                NodeList<ImportDeclaration> localRefs = null; //Declare Referenced Files (import, #include, etc.) List for Selected File (Local File)
                //Extract Comments and Referenced Files from Selected File (Local File)
                if (selFile.getProgLang().contains("java")) {
                    CompilationUnit compilationUnit = StaticJavaParser.parse(selFile.getFile());
                    localComments = compilationUnit.getAllComments();
                    localRefs = compilationUnit.getImports();
                } else;

                double[] simRate = new double[dbSize]; //Declare Variable for Calculating Similarity Percentage of Each File in the Database

                //???
                for (int i = 0; i < dbSize; i++) {
                    database[i].sim[i] = "";
                    simRate[i] = 0;
                }

                commentFlag = false; //Reset Comment Flag

                //Count Number of Lines to Be Checked
                while (lW < localSize) {
                    String localContent = selFile.getLine(lW);

                    if (selFile.getProgLang().contains("java")) {
                        localContent = PlagFilter.JavaProcessContent(localContent);

                        int filterCode = PlagFilter.JavaFilter(localContent, commentFlag);
                        switch (filterCode) {
                            case 0:
                                commentFlag = false;
                                localSizeSim--;
                                break;
                            case 1:
                                localSizeSim--;
                                break;
                            case 2:
                                commentFlag = true;
                                localSizeSim--;
                                break;
                            case 3:
                                break;
                            case 4:
                                localSizeSim--;
                                break;
                            default:
                                System.out.println("An error occured. Error code: " + filterCode);
                                break;
                        }
                    }

                    if (lW < localSize) {
                        lW++;
                    }
                    else {
                        break;
                    }
                }

                boolean[][] simLine = new boolean[dbSize][localSizeSim]; //Create boolean for every line to be compared

                lW=0; //Reset Value for Actual Plagiarism Checking
                int localLW = 0; //Declare Variable to Count the Number of Lines Left to be Compared for Selected File (local File)
                commentFlag = false; //Reset Comment Flag
                while (lW < localSize) {
                    String localContent = selFile.getLine(lW);
                    if (selFile.getProgLang().contains("java")) {
                        localContent = PlagFilter.JavaProcessContent(localContent);

                        int filterCode = PlagFilter.JavaFilter(localContent, commentFlag);
                        switch (filterCode) {
                            case 0:
                                commentFlag = false;
                                break;
                            case 1:
                                break;
                            case 2:
                                commentFlag = true;
                                break;
                            case 3:
                            case 4:

                                //Plagiarism Check

                                //Count Up Checked Lines
                                if (!(localLW == 0)) {
                                    if (localLW < localSizeSim) {
                                        localLW++;
                                    } else;
                                }

                                Scanner[] dbScan = new Scanner[dbSize]; //Declare Scanner for Database Files
                                int dbW = 0, dbNo = 0; //Declare Variables for Total Lines and Database File Array Pointer

                                //Put Content into Appropriate Line for Each Database File
                                while (dbNo < dbSize) {
                                    dbScan[dbNo] = new Scanner(database[dbNo].getFile());

                                    while (dbScan[dbNo].hasNextLine()) {
                                        database[dbNo].line[dbW] = dbScan[dbNo].nextLine();
                                        dbW++;
                                    }
                                    dbScan[dbNo].close();
                                    dbW = 0;
                                    if (dbNo < dbSize) {
                                        dbNo++;
                                    } else;
                                }

                                dbNo = 0; //Reset Pointer
                                while (dbNo < dbSize) {
                                    simLine[dbNo][localLW] = false;
                                    int dbLength = database[dbNo].line.length;
                                    dbW = 0;

                                    List<Comment> dbComments = null; //Declare Comment List for Database File
                                    NodeList<ImportDeclaration> dbRefs = null; //Declare Referenced Files (import, #include, etc.) List for Database File
                                    //Extract Comments and Referenced Files from Database File
                                    if (selFile.getProgLang().contains("java")) {
                                        CompilationUnit dbCU = StaticJavaParser.parse(database[dbNo].getFile());
                                        dbComments = dbCU.getAllComments();
                                        dbRefs = dbCU.getImports();
                                    } else;

                                    //Comment Line Matching and Referenced File Matching
                                    localComments.retainAll(dbComments);
                                    localRefs.retainAll(dbRefs);

                                    commentFlag = false; //Reset Comment Flag
                                    while (dbW < dbLength) {
                                        String dbContent = database[dbNo].getLine(dbW);
                                        dbContent = PlagFilter.JavaProcessContent(dbContent);

                                        filterCode = PlagFilter.JavaFilter(dbContent, commentFlag);
                                        switch (filterCode) {
                                            case 0:
                                                commentFlag = false;
                                                break;
                                            case 1:
                                                break;
                                            case 2:
                                                commentFlag = true;
                                                break;
                                            case 3:
                                            case 4:

                                                if (localContent.equals(dbContent)) {
                                                    database[dbNo].sim[dbNo] = database[dbNo].sim[dbNo] + ("Line " + (lW + 1) + ", \"" + localContent + "\" = Line " + (dbW + 1) + ", \"" + dbContent +  "\"\n");
                                                    if (!(simLine[dbNo][localLW])){
                                                        simRate[dbNo]++;
                                                        simLine[dbNo][localLW] = true;
                                                    }
                                                }

                                                break;
                                            default:
                                                System.out.println("An error occured. Error code: " + filterCode);
                                                break;
                                        }

                                        if (dbW < dbLength) {
                                            dbW++;
                                        } else;
                                    }
                                    if (dbNo < dbSize) {
                                        //Output Results for Comment Line Matching and Referenced File Matching
                                        if (!(localComments.isEmpty())) {
                                            System.out.println("Similar Comments (File to Database File "+(dbNo+1)+"): \n"+ localComments);
                                        } else;
                                        if (!(localRefs.isEmpty())) {
                                            System.out.println("Similar Imports (File to Database File " + (dbNo + 1) + "): \n" + localRefs);
                                        } else;
                                        dbNo++;
                                    } else;

                                }

                                break;
                            default:
                                System.out.println("An error occured. Error code: " + filterCode);
                                break;
                        }
                    } else;

                    if (lW < localSize) {
                        lW++;
                    }
                    else {
                        break;
                    }
                }

                //Set Decimal Point Format to 2 Points Decimals
                DecimalFormat df = new DecimalFormat("0.00");
                df.setRoundingMode(RoundingMode.HALF_UP);

                //Output Results
                for (int i = 0; i < dbSize; i++) {
                    System.out.println("\nFile vs DB File "+(i+1)+" (Similarity Percentage: "+ df.format(simRate[i]/localSizeSim*100)+"%)");
                    System.out.println(database[i].getSim(i));
                }
                System.out.println("End of Operation, Scanned a Total Line of " + (lW));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public class ButtonAction3 extends AbstractAction implements ActionListener {
        public ButtonAction3() {
            super("Upload File to DB");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                b3.setEnabled(false);
                FileUtils.copyFileToDirectory(selFile.getFile(), db);
                System.out.println("Successfully uploaded file to database");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void createGUI() {
        JFrame frame = new JFrame("CopyPoppy");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int sHeight = screenSize.height;
        int sWidth = screenSize.width;

        ImageIcon icon = new ImageIcon("res/Copypoppy.png");
        frame.setIconImage(icon.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new gui());
        frame.pack();
        frame.setSize(sWidth/2, sHeight/2);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}