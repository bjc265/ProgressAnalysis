package analysis;

import data.Task;

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

    private static Pattern javaPattern = Pattern.compile("(.*\".*\".*)*//.*(TODO.*)");

    public static List<Task> parseFile(File f) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(f));

        List<Task> tasks = reader.lines().map(javaPattern::matcher)
                .filter(Matcher::matches)
                .map(x -> new Task(Task.TaskType.TODO, 0, x.group(x.groupCount() - 1).substring(5)))
                .collect(Collectors.toList());

        return tasks;
    }
}
