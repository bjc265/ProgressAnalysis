package generation;

import data.Task;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import analysis.TaskAnalyzer;
import data.TaskTuple;

/**
 * Created by brett on 9/17/16.
 */
public class HTMLGenerator {
    public static String HTMLText;
    static TaskAnalyzer ta;

    static Map<File, TaskTuple> taskMap;

    /*
        Generate an HTML File to display information about the class hierarchy and tasks

        @param: rootDirectory
            The root directory of the project to find information about
     */
    public static void generateHTML(File rootDirectory) {

        ta = new TaskAnalyzer();
        HTMLText =
                "<!doctype html>\n " + "<html lang = \"en\"\n" +
                        "<head>\n   <meta charset=\"utf-8\"\n\n" +
                        "   <title> Progress Analyzer </title>\n" +
                        "   meta name=\"description\" content=\"SitePoint\">\n" +
                        "   meta name=\"author\" content=\"SitePoint\"\n\n>" +
                        "</head>\n\n" +
                        "<body>\n";


        taskMap = new HashMap<>();
        generateTaskMap(rootDirectory);

        parseFileTree(rootDirectory, 0);
    }

    /*
        Generate maps for each type of task

        @param: f
            The file or directory. If file, map the file to the corresponding Tasks. If directory, map the directory
            to the corresponding subTasks, and map all subfiles and subdirectories to their corresponding tasks.

        @return: The TaskTuple that represents the corresponding tasks for this file or directory
     */
    private static TaskTuple generateTaskMap(File f) {

        TaskTuple tt = null;

        if(!f.isDirectory()){
            try {
                tt = ta.parseFile(f);
            } catch (IOException e) {

            }
            taskMap.put(f, tt);
        }

        else{
            for(File subf : f.listFiles()) {
                if(tt == null){
                    tt = generateTaskMap(subf);
                }
                else{
                    tt.concat(generateTaskMap(subf));
                }
            }
            taskMap.put(f, tt);
        }

        return tt;
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

        generateDirectoryHTML(directory.getName(), taskMap.get(directory).todos, taskMap.get(directory).progresses,
                taskMap.get(directory).dones, depth);

        for(File subf : directory.listFiles()) {
            if (!subf.isDirectory())
                generateFileHTML(subf.getName(), taskMap.get(subf).todos, taskMap.get(subf).progresses,
                        taskMap.get(subf).dones, depth + 1);
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
     */
    private static void generateFileHTML(String name, List<Task> todos, List<Task> progress, List<Task> done,
                                           int depth) {
        String str = new String();
        str += "<div>";
        for(Task t : todos){
            for(int i = 0; i < Math.min(depth,6); i++){
                str += "    ";
            }
            str +=  "Line " + t.line + "- TODO: " + t.message + "\n";
        }
        for(Task t : progress){
            for(int i = 0; i < Math.min(depth,6); i++){
                str += "    ";
            }
            str +=  "Line " + t.line + "- In Progress: " + t.message + "\n";
        }
        for(Task t : done){
            for(int i = 0; i < Math.min(depth,6); i++){
                str += "    ";
            }
            str +=  "Line " + t.line + "- Done: " + t.message + "\n";
        }
        str += "</div>";
        HTMLText += str;
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
     */
    private static void generateDirectoryHTML(String name, List<Task> todos, List<Task> progress, List<Task> done,
                                                int depth) {
        String str = new String();
        for(int i = 0; i < Math.min(depth,6); i++){
            str += "    ";
        }
        str += "<div> Directory: " + name +
                "Tasks: " +
                todos.size() + " TODO, " +
                progress.size() + "InProgress, " +
                done.size() + "Done. </div>";
        HTMLText += str;
    }
}
