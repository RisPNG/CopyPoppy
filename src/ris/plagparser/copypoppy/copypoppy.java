package ris.plagparser.copypoppy;
/*
User: NIK FARIS AIMAN BIN NIK RAHIMAN
*/
import static ris.plagparser.copypoppy.gui.createGUI;

public class copypoppy {

    public static void main(String[] args) {
        //Program Start
        try {
            createGUI();
        } catch (Exception e) {
            System.out.println("System error!\nExiting program...");
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
