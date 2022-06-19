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

    @Test
    public void given_a_file_name_string_and_two_paths_strings_when_moving_file_then_ok() {

        boolean expected = true;
        boolean current = e.moveFile(
                "movingFile.txt",
                String.valueOf(e.directory),
                String.valueOf(e.directory.resolve("converterOut"))
        );

        assertEquals(expected, current);
    }
}