package data;

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

    public Task(TaskType t, int l, String m) {
        type = t;
        line = l;
        message = m;
    }
}
