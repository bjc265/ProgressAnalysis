package main;

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
        File outDirectory = Paths.get(args[1]).toFile();
        String projectName = args[1];

        List<File> files = new ArrayList<>();

        HTMLGenerator.generateHTML(directory, outDirectory, projectName);
    }
}
