package generation;

import data.Task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import analysis.TaskAnalyzer;
import data.TaskTuple;

import javax.swing.text.html.HTML;

/**
 * Created by brett on 9/17/16.
 */
public class HTMLGenerator {
    public static String HTMLText;

    static TaskAnalyzer ta;
    static Map<File, TaskTuple> taskMap;

    private static File outDirectory;

    /*
        Generate an HTML File to display information about the class hierarchy and tasks

        @param: rootDirectory
            The root directory of the project to find information about
     */
    public static void generateHTML(File rootDirectory, File out, String name) {

        ta = new TaskAnalyzer();
        taskMap = new HashMap<>();



        outDirectory = out;

        generateTaskMap(rootDirectory);
        parseFileTree(rootDirectory, 0);
        HTMLText += startHTMLFile() +
                "<h1>" + name + "</h1>\n <ul>\n";
        HTMLText += "</ul>\n </div> \n\n <footer>Created at Big Red Hacks 2016</footer>\n\n" +
                "<script src=\"jquery.js\"></script>\n" +
                "<script src=\"index.js\"></script>\n" +
                "</body>\n" +
                "</html>";
        File file = new File(outDirectory.getPath(),"index.html");
        FileWriter fw = null;

        try {
            fw = new FileWriter(file.getAbsoluteFile());
        }
        catch(java.io.IOException E){
            E.printStackTrace();
        }

        BufferedWriter bw = new BufferedWriter(fw);
        try {
            bw.write(HTMLText);
            bw.close();
        }
        catch(java.io.IOException E){
            E.printStackTrace();
        }
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

        TaskTuple taskList = taskMap.get(directory);
        if(taskList != null){
            generateDirectoryHTML(directory, taskList.todos, taskList.progresses, taskList.dones, depth);
        }

        for(File subf : directory.listFiles()) {
            if (!subf.isDirectory()) {
                TaskTuple fileTaskList = taskMap.get(subf);
                if(fileTaskList != null) {
                    generateFileHTML(subf, fileTaskList.todos, fileTaskList.progresses, fileTaskList.dones, depth + 1);
                }
            }
            else {
                parseFileTree(subf, depth + 1);
            }
        }
    }

    /*
        Generate a string of HTML to represent one file's information about the tasks in that file

        @param: f
            The file
        @param: todos
            The list of type tod Task objects corresponding to this file
        @param: progress
            The list of type progress Task objects corresponding to this file
        @param: done
            The list of type done Task objects corresponding to this file
        @param: depth
            The number of directories that this file is inside within the main project
     */
    private static void generateFileHTML(File f, List<Task> todos, List<Task> progress, List<Task> done,
                                           int depth) {

        String name = f.getName().replace(".java","");
        String str = startHTMLFile();
        File file = new File(outDirectory.getPath(),"" + f.toString().replace("/","_").replace(".java","") + ".html");
        FileWriter fw = null;

        try {
            fw = new FileWriter(file.getAbsoluteFile());
        }
        catch(java.io.IOException E){
            E.printStackTrace();
        }

        BufferedWriter bw = new BufferedWriter(fw);

        str += "<h1>" + f.toString() + "</h1>\n";
        str += "<h3>TODO's</h3>\n" +
                "<ul>\n";
        String _path = f.toString().replace("/","_");
        for(Task t : todos){

            str +=  "<li><span class=\"message\">" + t.message + "</span><span class=\"source\">" + t.filePath +
                    "</span><span class=\"line\"" + t.line + "<span></li>\n";

            HTMLText += "<li class=\"depth file\" data-depth=" + depth + "<a href=\"" + _path + ".html\">"
                    + f.getName() + "</a></li>\n";
        }
        str += "</ul>\n" +
                "<h3>IN PROGRESS</h3>\n" +
                "<ul>";

        for(Task t : progress){
            str +=  "<li><span class=\"message\">" + t.message + "</span><span class=\"source\">" + t.filePath +
                    "</span><span class=\"line\"" + t.line + "<span></li>\n";

            HTMLText += "<li class=\"depth file\" data-depth=" + depth + "<a href=\"" + _path + ".html\">"
                    + f.getName() + "</a></li>\n";
        }

        str += "</ul>\n"+
                "<h3>DONE's</h3>\n" +
                "<ul>\n";

        for(Task t : done){
            str +=  "<li><span class=\"message\">" + t.message + "</span><span class=\"source\">" + t.filePath +
                    "</span><span class=\"line\"" + t.line + "<span></li>\n";

            HTMLText += "<li class=\"depth file\" data-depth=" + depth + "<a href=\"" + _path + ".html\">"
                    + f.getName() + "</a></li>\n";
        }

        str += " </ul>\n " +
                "</body>\n" +
                "</html>";

        try{
            bw.write(str);
            bw.close();
        }
        catch(java.io.IOException E){
            E.printStackTrace();
        }
        HTMLText += str;
    }

    /*
        Generate a string of HTML to represent one directory's information about all tasks in files inside this
        directory

        @param: f
            The file
        @param: todos
            The list of type tod Task objects corresponding to this file
        @param: progress
            The list of type progress Task objects corresponding to this file
        @param: done
            The list of type done Task objects corresponding to this file
        @param: depth
            The number of directories that this directory is inside within the main project
     */
    private static void generateDirectoryHTML(File f, List<Task> todos, List<Task> progress, List<Task> done,
                                                int depth) {
        String str = startHTMLFile();
        String name = f.getName().replace(".java","");
        File file = new File(outDirectory.getPath(),"" + f.toString().replace("/","_").replace(".java","") + ".html");
        FileWriter fw = null;

        try {
            fw = new FileWriter(file.getAbsoluteFile());
            file = new File(Paths.get("Output").toString(),""
                    + f.getCanonicalPath().replace("/","_").replace(".java","") + ".html");
        }
        catch(java.io.IOException E){
            E.printStackTrace();
        }

        BufferedWriter bw = new BufferedWriter(fw);

        str += "<h1>" + f.toString() + "</h1>\n";
        str += "<h3>TODO's</h3>\n" +
                "<ul>\n";
        String _path = f.toString().replace("/","_");

        for(Task t : todos){
            str +=  "<li><span class=\"message\">" + t.message + "</span><span class=\"source\">" + t.filePath +
                    "</span><span class=\"line\"" + t.line + "<span></li>\n";
            HTMLText += "<li class=\"depth dir\" data-depth=" + depth + "<a href=\"" + _path + ".html\">"
                    + f.getName() + "</a></li>\n";
        }
        str += "</ul>\n" +
                "<h3>IN PROGRESS</h3>\n" +
                "<ul>";

        for(Task t : progress){
            str +=  "<li><span class=\"message\">" + t.message + "</span><span class=\"source\">" + t.filePath +
                    "</span><span class=\"line\"" + t.line + "<span></li>\n";
            HTMLText += "<li class=\"depth dir\" data-depth=" + depth + "<a href=\"" + _path + ".html\">"
                    + f.getName() + "</a></li>\n";
        }

        str += "</ul>\n"+
                "<h3>DONE's</h3>\n" +
                "<ul>\n";
        for(Task t : done){

            str +=  "<li><span class=\"message\">" + t.message + "</span><span class=\"source\">" + t.filePath +
                    "</span><span class=\"line\"" + t.line + "<span></li>\n";

            HTMLText += "<li class=\"depth dir\" data-depth=" + depth + "<a href=\"" + _path + ".html\">"
                    + f.getName() + "</a></li>\n";
        }
        str += " </ul>\n " +
                "</body>\n" +
                "</html>";

        try{
            bw.write(str);
            bw.close();
        }
        catch(java.io.IOException E){
            E.printStackTrace();
        }
        HTMLText += str;
    }
    /** Yes, this is just a string wrapped in a function. No, I'm not gonna change it :D */
    private static String startHTMLFile(){
        return "<!doctype html>\n " + "<html lang = \"en\">\n" +
                "<head>\n   <meta charset=\"utf-8\">\n\n" +
                "   <title> Progress Analyzer </title>\n" +
                "   <meta name=\"description\" content=\"SitePoint\">\n" +
                "   <meta name=\"author\" content=\"SitePoint\">\n\n" +
                "</head>\n\n" +
                "<body>\n";


    }
}
