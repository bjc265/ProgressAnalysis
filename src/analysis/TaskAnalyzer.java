package analysis;

import data.Task;
import data.TaskTuple;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by brett on 9/17/16.
 */
public class TaskAnalyzer {

    public static TaskTuple parseFile(File f) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(f));

        return parseReader(f.getName(), reader, Paths.get(f.getPath()));
    }

    public static TaskTuple parseReader(String fileName, BufferedReader reader, Path path) throws IOException {

        Pattern todoPattern;
        Pattern completePattern;
        Pattern inProgressPattern;

        switch(fileName.substring(fileName.lastIndexOf('.'))) {
            case ".java":
            case ".c":
            case ".cpp":
            case ".cs":
                todoPattern = Pattern.compile("([^\"]*\"[^\"]*\"[^\"]*)*//.*(TODO.*)");
                completePattern = Pattern.compile("([^\"]*\"[^\"]*\"[^\"]*)*//.*(COMPLETE.*)");
                inProgressPattern = Pattern.compile("([^\"]*\"[^\"]*\"[^\"]*)*//.*(IN_PROGRESS.*)");
                break;
            case ".py":
            case ".rb":
                todoPattern = Pattern.compile("([^\"]*\"[^\"]*\"[^\"]*)*#.*(TODO.*)");
                completePattern = Pattern.compile("([^\"]*\"[^\"]*\"[^\"]*)*#.*(COMPLETE.*)");
                inProgressPattern = Pattern.compile("([^\"]*\"[^\"]*\"[^\"]*)*#.*(IN_PROGRESS.*)");
                break;
            default:
                throw new IOException();
        }

        Stream<Task> temp = reader.lines().map(todoPattern::matcher)
                .filter(Matcher::matches)
                .map(x -> new Task(Task.TaskType.TODO, 0, x.group(x.groupCount()).substring(5).trim(), path));
        List<Task> todos = temp.collect(Collectors.toList());


        List<Task> completes = reader.lines().map(completePattern::matcher)
                .filter(Matcher::matches)
                .map(x -> new Task(Task.TaskType.COMPLETE, 0, x.group(x.groupCount()).substring(9).trim(), path))
                .collect(Collectors.toList());

        List<Task> progresses = reader.lines().map(inProgressPattern::matcher)
                .filter(Matcher::matches)
                .map(x -> new Task(Task.TaskType.IN_PROGRESS, 0, x.group(x.groupCount()).substring(12).trim(), path))
                .collect(Collectors.toList());

        return new TaskTuple(todos, progresses, completes);
    }
}
