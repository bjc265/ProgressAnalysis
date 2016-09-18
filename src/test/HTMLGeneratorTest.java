package test;

import generation.HTMLGenerator;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;


/**
 * Created by Grant on 9/17/16.
 */
public class HTMLGeneratorTest {

    @Test
    public void test(){
        File src_file = Paths.get("src").toFile();
        File output_dir = Paths.get("Output").toFile();
        HTMLGenerator.generateHTML(src_file, output_dir, "Grant");
        System.out.println(HTMLGenerator.HTMLText);
    }
}
