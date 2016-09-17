package analysis;

import data.SAObject;
import java.util.Set;
import java.util.HashSet;
import java.io.File;

/**
 * Created by brett on 9/17/16.
 */
public class JavaAnalyzer implements LanguageAnalyzer {

    public Set<SAObject> parseFile(File f) {
        return new HashSet<>();
    }


}
