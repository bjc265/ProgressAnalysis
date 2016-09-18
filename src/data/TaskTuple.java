package data;

import java.util.List;

/**
 * Created by chris on 9/17/16.
 *
 * An instance of TaskTuple represents all tasks to be completed for a given file, split into three distinct lists:
 * one for "todos", one for "in progress"'s, and one for "done's".
 */
public class TaskTuple {

    public List<Task> todos;
    public List<Task> progresses;
    public List<Task> dones;

    /*
        Constructor

        @param: t
            the list of "todos" tasks
        @param: p
            the list of "in progress" tasks
        @param: d
            the list of "done" tasks

     */
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

    public void concat(TaskTuple tt){
        todos.addAll(tt.todos);
        progresses.addAll(tt.progresses);
        dones.addAll(tt.dones);
    }
}
