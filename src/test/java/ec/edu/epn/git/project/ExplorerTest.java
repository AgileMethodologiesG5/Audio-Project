package ec.edu.epn.git.project;

import org.junit.Before;
import org.junit.Test;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;

import static org.junit.Assert.*;

public class ExplorerTest {

    Explorer e;

    @Before
    public void starUp() {
        e = new Explorer();
    }

    @Test
    public void given_a_directory_when_returning_the_files_list_then_ok() {
        String[] expected = {"arc1.aiff", "arc1.au", "arc1.wav", "cheer.wav", "converterOut", "house_lo.mp3", "movingFile.txt", "nature.wav"};
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

    @Test
    public void given_an_audio_file_when_playing_check_compatibility_then_ok() {
        e.setAudioFileByName("arc1.wav");
        boolean current = e.playAudioFile();

        assertTrue(current);
    }

    @Test
    public void given_the_file_when_converting_then_ok() {
        File currentFile = e.setAudioFileByName("house_lo.mp3");
        Format current = Format.WAV;
        assertThrows(UnsupportedAudioFileException.class, () -> e.convertAudioFile(current));

    }

    @Test
    public void given_the_name_of_a_file_when_deleting_then_ok() {
        String src = String.valueOf(e.directory.resolve("converterOut"));
        String dest = String.valueOf(e.directory);
        e.moveFile("AUcheer.au",src,dest);
        boolean current = e.deleteFileByName("AUcheer.au");

        assertTrue(current);
    }

    @Test
    public void given_a_file_name_string_when_renaming_then_ok() {
        e.renameFile("arc1.wav","arc2.wav");
        boolean current = e.renameFile("arc2.wav","arc1.wav");

        assertTrue(current);
    }

    @Test
    public void given_a_name_when_creating_a_directory_then_ok() {
        boolean current = e.createDirectory("new");
        assertTrue(current);
        e.deleteFileByName("new");
    }

    @Test
    public void given_a_file_name_when_showing_metadata_then_ok() {
        assertNotNull(e.getMetadata("arc1.wav"));
    }

    @Test
    public void given_a_file_name_when_showing_properties_then_ok() {
        assertNotNull(e.getProperties("arc1wav"));
    }

    @Test
    public void given_a_file_reproduction_when_use_pause_then_ok() {
        e.setAudioFileByName("nature.wav");
        e.playAudioFile();
        assertEquals(0, e.pauseAudioFile()*10000);
    }

    @Test
    public void given_a_file_reproduction_when_use_stop_then_ok() {
        e.setAudioFileByName("nature.wav");
        e.playAudioFile();
        assertEquals(0, e.pauseAudioFile()*10000);
    }
}