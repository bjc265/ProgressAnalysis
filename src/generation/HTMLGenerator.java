package generation;

import data.Task;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import analysis.TaskAnalyzer;
import data.TaskTuple;

/**
 * Created by brett on 9/17/16.
 */
public class HTMLGenerator {
    static String HTMLText;
    static TaskAnalyzer ta;

    static HashMap<File, List<Task>>  todoMap;
    static HashMap<File, List<Task>>  progressMap;
    static HashMap<File, List<Task>>  doneMap;

    /*
        Generate an HTML File to display information about the class hierarchy and tasks

        @param: rootDirectory
            The root directory of the project to find information about
     */
    public static void generateHTML(File rootDirectory) {

        ta = new TaskAnalyzer();
        HTMLText = "";

        parseFileTree(rootDirectory, 0);
    }

    /*
        Generate maps for each type of task

        @param: directory
            The directory to parse
     */
    private static TaskTuple generateTaskMaps(File directory) {
        assert directory.isDirectory();

        for(File subf : directory.listFiles()) {
            if (!subf.isDirectory())
                try {
                    TaskTuple tt = new TaskTuple(ta.parseFile(subf), ta.parseFile(subf), ta.parseFile(subf));
                } catch (IOException e) {
                    System.out.println("Problem parsing file");
                }
            else {
                parseFileTree(subf, 1);
            }
        }
    }

    /*
        Parse the file tree, utilizing a depth first search

        @param: directory
            The directory to parse
        @param: depth
            The number of directories that this directory is inside
     */
    private static void parseFileTree(File directory, int depth) {
        assert directory.isDirectory();

        generateDirectoryHTML(directory.getName(), null, null, null, depth);

        for(File subf : directory.listFiles()) {
            if (!subf.isDirectory())
                try {
                    generateFileHTML(subf.getName(), ta.parseFile(subf), null, null, depth + 1);
                } catch (IOException e){
                    System.out.println("Problem Parsing File");
                }
            else {
                parseFileTree(subf, depth + 1);
            }
        }
    }

    /*
        Generate a string of HTML to represent one file's information about the tasks in that file

        @param: name
            The name of the file
        @param: todos
            The list of type tod Task objects corresponding to this file
        @param: progress
            The list of type progress Task objects corresponding to this file
        @param: done
            The list of type done Task objects corresponding to this file
        @param: depth
            The number of directories that this file is inside within the main project

        @return A string representing the html needed to display the necessary information
     */
    private static String generateFileHTML(String name, List<Task> todos, List<Task> progress, List<Task> done,
                                           int depth) {
        String str = new String;







        return str;
    }

    /*
        Generate a string of HTML to represent one directory's information about all tasks in files inside this
        directory

        @param: name
            The name of the directory
                @param: todos
            The list of type tod Task objects corresponding to this file
        @param: progress
            The list of type progress Task objects corresponding to this file
        @param: done
            The list of type done Task objects corresponding to this file
        @param: depth
            The number of directories that this directory is inside within the main project

        @return A string representing the html needed to display the necessary information
     */
    private static String generateDirectoryHTML(String name, List<Task> todos, List<Task> progress, List<Task> done,
                                                int depth) {
        return null;
    }
}
