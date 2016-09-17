package analysis;

import java.io.File;
import data.SAObject;
import java.util.Set;

/**
 * Created by brett on 9/17/16.
 */
public interface LanguageAnalyzer {

    Set<SAObject> parseFile(File f);

}
