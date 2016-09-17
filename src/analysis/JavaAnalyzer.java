package analysis;

import data.SAObject;
import data.Task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by brett on 9/17/16.
 */
public class JavaAnalyzer {

    public static SAObject parseFile(File f) {

        String name = null;
        List<Task> tasks = new ArrayList<>();
        Set<SAObject> references = new HashSet<>();
        Set<SAObject> referenced_by = new HashSet<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(f));
            String line = reader.readLine();
            int lineNum = 1;
            while (line != null) {
                int todoIndex = line.indexOf("//");
                if (todoIndex > -1) {
                    String before = line.substring(0, todoIndex);
                    before.replace("\\\"", "");
                    int beforeQuot = before.indexOf("\"");
                    int totalQuots = 0;
                    while (beforeQuot > -1) {
                        totalQuots++;
                        before = before.substring(beforeQuot + 1);
                        beforeQuot = before.indexOf("\"");
                    }
                    if (totalQuots % 2 == 0) {
                        switch (line.substring(todoIndex + 2, todoIndex + 7)) {
                            case "TODO ":
                                tasks.add(new Task(Task.TaskType.TODO, lineNum,
                                        line.substring(todoIndex + 6)));
                                break;
                            case "COMPL":
                                if (line.substring(todoIndex + 2, todoIndex + 11).equals("COMPLETE ")) {
                                    tasks.add(new Task(Task.TaskType.COMPLETE, lineNum,
                                            line.substring(todoIndex + 11)));
                                    break;
                                }
                            case "IN_PR":
                                if (line.substring(todoIndex + 2, todoIndex + 13).equals("IN_PROGRESS ")) {
                                    tasks.add(new Task(Task.TaskType.IN_PROGRESS, lineNum,
                                            line.substring(todoIndex + 13)));
                                    break;
                                }
                            default:
                                break;
                        }

                    }
                }


                line = reader.readLine();
                lineNum++;
            }

            return new SAObject(name, references, referenced_by, tasks);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
