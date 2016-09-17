package data;

import java.util.Set;

/**
 * Created by brett on 9/17/16.
 */
public class SAObject {

    private Set<SAObject> references;
    private Set<SAObject> referenced_by;
    private Set<Task> tasks;
}
