package generation;

import data.Task;

import java.io.File;
import java.util.List;

/**
 * Created by brett on 9/17/16.
 */
public class HTMLGenerator {
    /*
        Generate an HTML File to display information about the class hierarchy and tasks

        @param: rootDirectory
            The root directory of the project to find information about
     */
    public static void generateHTML(File rootDirectory) {

        parseFileTree(rootDirectory);

    }

    /*
        Parse the file tree, utilizing a depth first search

        @param: directory
            The directory to parse
     */
    private static void parseFileTree(File directory) {

    }

    /*
        Generate a string of HTML to represent one class's information about the tasks in that class

        @param: name
            The name of the class
        @param: tasks
            The list of Task objects corresponding to this class
        @param: depth
            The number of directories that this file is inside within the main project
     */
    private static String generateFileHTML(String name, List<Task> tasks, int depth) {
        





        return null;
    }

    /*
        Generate a string of HTML to represent one directory's information about all tasks in files inside this
        directory

        @param: name
            The name of the directory
        @param: tasks
            The list of Task objects in all classes inside the directory
        @param: depth
            The number of directories that this directory is inside within the main project
     */
    private static String generateDirectoryHTML(String name, List<Task> tasks, int depth) {
        return null;
    }
}
