package data;

import java.util.List;

/**
 * Created by chris on 9/17/16.
 */
public class TaskTuple {

    public List<Task> todos;
    public List<Task> progresses;
    public List<Task> dones;

    public TaskTuple(List<Task> t, List<Task> p, List<Task> d){
        todos = t;
        progresses = p;
        dones = d;
    }

    public void addTodos(List<Task> t){
        todos.addAll(t);
    }

    public void addProgresses(List<Task> p){
        progresses.addAll(p);
    }

    public void addDones(List<Task> d){
        dones.addAll(d);
    }
}
