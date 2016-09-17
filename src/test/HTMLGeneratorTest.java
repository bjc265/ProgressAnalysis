package test;

import generation.HTMLGenerator;
import org.junit.*;
import java.io.File;

import static org.junit.Assert.assertTrue;


/**
 * Created by Grant on 9/17/16.
 */
public class HTMLGeneratorTest {

    @Test
    public void test(){
        File src_file = new File("src");
        HTMLGenerator.generateHTML(src_file);
        System.out.println(HTMLGenerator.HTMLText);
    }

}
