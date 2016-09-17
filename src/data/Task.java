package data;

import java.nio.file.Path;

/**
 * Created by brett on 9/17/16.
 */
public class Task {

    public enum TaskType {
        TODO, COMPLETE, IN_PROGRESS
    }

    public TaskType type;
    public String message;
    public int line;
    public Path filePath;

    public Task(TaskType t, int l, String m,Path p) {
        type = t;
        line = l;
        message = m;
        filePath = p;
    }
}
