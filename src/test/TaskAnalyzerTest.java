package test;

import analysis.TaskAnalyzer;
import data.TaskTuple;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.Assert.*;

/**
 * Created by harry on 9/17/16.
 */
public class TaskAnalyzerTest {

    @Test
    public void testSimple() {
        String test = "// TODO: Do the thing.\n";
        BufferedReader br = new BufferedReader(new StringReader(test));
        TaskTuple res = null;
        try {
            res = TaskAnalyzer.parseReader("test.java", br);
        } catch (IOException e) {
            fail();
        }
        assertNotNull(res);
        assertEquals(1, res.todos.size());
    }

    @Test(expected = IOException.class)
    public void testUnrecognizedFiletype() throws IOException {
        String test = "// TODO: Do the thing.";
        BufferedReader br = new BufferedReader(new StringReader(test));
        TaskTuple res = null;
        res = TaskAnalyzer.parseReader("test.bad", br);
    }
}