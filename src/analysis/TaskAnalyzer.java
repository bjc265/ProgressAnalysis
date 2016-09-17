package analysis;

import data.Task;
import data.TaskTuple;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by brett on 9/17/16.
 */
public class TaskAnalyzer {

    public static TaskTuple parseFile(File f) throws IOException {

        Pattern todoPattern;
        Pattern completePattern;
        Pattern inProgressPattern;

        switch(f.getName().substring(f.getName().lastIndexOf('.'))) {
            case ".java":
            case ".c":
            case ".cpp":
            case ".cs":
                todoPattern = Pattern.compile("(.*\".*\".*)*//.*(TODO.*)");
                completePattern = Pattern.compile("(.*\".*\".*)*//.*(COMPLETE.*)");
                inProgressPattern = Pattern.compile("(.*\".*\".*)*//.*(IN_PROGRESS.*)");
                break;
            case ".py":
            case ".rb":
                todoPattern = Pattern.compile("(.*\".*\".*)*#.*(TODO.*)");
                completePattern = Pattern.compile("(.*\".*\".*)*#.*(COMPLETE.*)");
                inProgressPattern = Pattern.compile("(.*\".*\".*)*#.*(IN_PROGRESS.*)");
                break;
            default:
                todoPattern = null;
                completePattern = null;
                inProgressPattern = null;
        }

        BufferedReader reader = new BufferedReader(new FileReader(f));

        List<Task> todos = reader.lines().map(todoPattern::matcher)
                .filter(Matcher::matches)
                .map(x -> new Task(Task.TaskType.TODO, 0, x.group(x.groupCount() - 1).substring(5)))
                .collect(Collectors.toList());

        List<Task> completes = reader.lines().map(completePattern::matcher)
                .filter(Matcher::matches)
                .map(x -> new Task(Task.TaskType.COMPLETE, 0, x.group(x.groupCount() - 1).substring(9)))
                .collect(Collectors.toList());

        List<Task> progresses = reader.lines().map(inProgressPattern::matcher)
                .filter(Matcher::matches)
                .map(x -> new Task(Task.TaskType.IN_PROGRESS, 0, x.group(x.groupCount() - 1).substring(12)))
                .collect(Collectors.toList());

        return new TaskTuple(todos, progresses, completes);
    }
}
