package ec.edu.epn.git.project;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExplorerTest {

    Explorer e;
    @Before
    public void starUp() {
        e = new Explorer();
    }

    @Test
    public void given_a_directory_when_returning_the_files_list_then_ok() {
        String[] expected = {"arc1.aiff", "arc1.au", "arc1.wav", "cheer.wav", "converterOut", "nature.wav" };
        String[] current = e.getFilesList();

        assertArrayEquals(expected, current);
    }
}