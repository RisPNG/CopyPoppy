package ris.plagparser.copypoppy;
/*
User: NIK FARIS AIMAN BIN NIK RAHIMAN
*/
import java.io.File;

public class filestruct {
    String fileName, progLang, path;
    File file;
    String[] sim;
    String[] line;

    public filestruct(String fileName, String progLang, String path, File file, int line, int sim) {
        this.fileName = fileName;
        this.progLang = progLang;
        this.path = path;
        this.file = file;
        this.line = new String[line];
        this.sim = new String[sim];
    }

    //getter
    public File getFile() {
        return file;
    }
    public String getFileName() {
        return fileName;
    }
    public String getPath() { return path; }
    public String getProgLang() { return progLang; }
    public String getLine(int args) { return line[args]; }
    public String getSim(int args) { return sim[args]; }
}
