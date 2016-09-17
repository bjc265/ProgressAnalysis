package generation;

import data.Task;

import java.io.File;
import java.util.List;

/**
 * Created by brett on 9/17/16.
 */
public class HTMLGenerator {
    String HTMLText;

    /*
        Generate an HTML File to display information about the class hierarchy and tasks

        @param: rootDirectory
            The root directory of the project to find information about
     */
    public static void generateHTML(File rootDirectory) {

        HTMLText = "";
        parseFileTree(rootDirectory, 0);
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

        generateDirectoryHTML(subf.getName(), getSubTasks(subf), depth);

        for(File subf : rootDirectory) {
            if (!subf.isDirectory())
                generateFileHTML(subf.getName(), getTasks(subf), depth + 1)
            else {
                parseFileTree(subf, depth + 1);
            }
        }
    }

    /*
        Parse the directory, and create a list of tasks in all files inside the directory

        @param: directory
            The directory to parse
     */
    private static List<Task> getSubTasks(File directory) {
        assert directory.isDirectory();

        HTMLText += generateDirectoryHTML(subf.getName(), getSubTasks(subf), depth);

        for(File subf : rootDirectory) {
            if (!subf.isDirectory())
                HTMLText += generateFileHTML(subf.getName(), getTasks(subf), depth + 1)
            else {
                parseFileTree(subf, depth + 1);
            }
        }
    }

    /*
        Generate a string of HTML to represent one file's information about the tasks in that file

        @param: name
            The name of the file
        @param: tasks
            The list of Task objects corresponding to this file
        @param: depth
            The number of directories that this file is inside within the main project

        @return A string representing the html needed to display the necessary information
     */
    private static String generateFileHTML(String name, List<Task> tasks, int depth) {
        String str = new String;







        return str;
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

        @return A string representing the html needed to display the necessary information
     */
    private static String generateDirectoryHTML(String name, List<Task> tasks, int depth) {
        return null;
    }
}
