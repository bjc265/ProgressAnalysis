package data;

import java.util.List;
import java.util.Set;

/**
 * Created by brett on 9/17/16.
 */
public class SAObject {

    private String name;
    private Set<SAObject> references;
    private Set<SAObject> referenced_by;
    private List<Task> tasks;

    public SAObject(String s, Set<SAObject> refs, Set<SAObject> refd, List<Task> t) {
        name = s;
        references = refs;
        referenced_by = refd;
        tasks = t;
    }

}
