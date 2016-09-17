package main;

import analysis.SAObjectFactory;
import data.SAObject;
import generation.HTMLGenerator;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by brett on 9/16/16.
 */
public class Main {

    public static void main(String[] args) {

        File directory = Paths.get(args[0]).toFile();
        SAObjectFactory analyzer = SAObjectFactory.getInstance();
        List<File> files = new ArrayList<>();

        getFiles(directory, files);
        List<SAObject> objects = new ArrayList<>(files.size());

        for (File f : files) {
            objects.add(analyzer.analyzeFile(f));
        }

        //HTMLGenerator.generateHTML(objects);
    }

    private static void getFiles(File f, List<File> files) {
        if (!f.isDirectory())
            files.add(f);
        else {
            for (File subf : f.listFiles())
                getFiles(subf, files);
        }
    }

}
