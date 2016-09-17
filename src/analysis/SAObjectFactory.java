package analysis;

import data.SAObject;

import java.io.File;

/**
 * Created by brett on 9/17/16.
 */
public class SAObjectFactory {
    private static SAObjectFactory ourInstance = new SAObjectFactory();

    public static SAObjectFactory getInstance() {
        return ourInstance;
    }


    public SAObject analyzeFile(File f) {
        switch (f.getName().substring(f.getName().lastIndexOf('.'))) {
            case ".java":
                return JavaAnalyzer.parseFile(f);
            default:
                return null;
        }
    }


}
