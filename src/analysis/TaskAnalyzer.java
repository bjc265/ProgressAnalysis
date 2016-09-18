package analysis;

import data.Task;
import data.TaskTuple;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

        switch (fileName.substring(fileName.lastIndexOf('.'))) {
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

        List<String> lines = reader.lines().collect(Collectors.toList());

        AtomicInteger lineNo = new AtomicInteger();

        lineNo.set(1);
        List<Task> todos = lines.stream()
                .map(x -> new Pair<Integer, Matcher>(lineNo.getAndIncrement(), todoPattern.matcher(x)))
                .filter(x -> x.getValue().matches())
                .map(x -> new Task(
                        Task.TaskType.TODO,
                        x.getKey(),
                        x.getValue().group(x.getValue().groupCount()).substring(5).trim(), path))
                .collect(Collectors.toList());

        lineNo.set(1);
        List<Task> completes = lines.stream()
                .map(x -> new Pair<Integer, Matcher>(lineNo.getAndIncrement(), completePattern.matcher(x)))
                .filter(x -> x.getValue().matches())
                .map(x -> new Task(
                        Task.TaskType.COMPLETE,
                        x.getKey(),
                        x.getValue().group(x.getValue().groupCount()).substring(9).trim(), path))
                .collect(Collectors.toList());

        lineNo.set(1);
        List<Task> progresses = lines.stream()
                .map(x -> new Pair<Integer, Matcher>(lineNo.getAndIncrement(), inProgressPattern.matcher(x)))
                .filter(x -> x.getValue().matches())
                .map(x -> new Task(
                        Task.TaskType.IN_PROGRESS,
                        x.getKey(),
                        x.getValue().group(x.getValue().groupCount()).substring(12).trim(), path))
                .collect(Collectors.toList());

        return new TaskTuple(todos, progresses, completes);
    }
}
