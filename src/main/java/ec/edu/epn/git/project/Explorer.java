package ec.edu.epn.git.project;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Explorer {

    Path directory;                                                         // Targeted directory of the explorer
    ArrayList<Exception> exceptionArrayList = new ArrayList<>();            // Array to save the occurred exceptions
    AudioPlayer audioPlayer = new AudioPlayer();                            // Audio player linked with the explorer
    File audioFile = null;                                                  // Targeted audio file of the explorer
    Converter converter = new Converter();                                  // Audio converter linked with the explorer
    public Explorer() {
        String stringDirectory = System.getProperty("user.dir");
        Path mainPath = Path.of(stringDirectory);
        this.directory = mainPath.getParent().resolve("audioLibrary");

        // Checking if the default path is accessible
        if (!checkPath(directory)) {
            this.directory = mainPath.resolve("audioLibrary");
        }
    }

    private boolean checkPath(Path path) {
        return Files.exists(path);
    }
    public String[] getFilesList() {
        // Local variables
        File directoryFile = new File(String.valueOf(directory));
        File[] filesPathsList = directoryFile.listFiles();
        ArrayList<String> fileNames = new ArrayList<>();

        String[] error = {"There are no files in the pointing directory\n"};

        if (filesPathsList != null) {
            // Sorting files
            Arrays.sort(filesPathsList);

            for (File file : filesPathsList) {
                fileNames.add(file.getName());
            }

            return fileNames.toArray(new String[0]);
        }

        return error;
    }

    public boolean moveFile(String fileName, String strSourcePath, String strDestinationPath) {
        // Checking if the given name exists
        Path sourceFilePath = Path.of(strSourcePath + "\\" + fileName);
        Path destinationPath = Path.of(strDestinationPath);

        // Checking it the given paths exists
        if (checkPath(sourceFilePath) && checkPath(destinationPath)) {
            // Moving the file
            Path destinationFilePath = Paths.get(strDestinationPath + "\\" + fileName);

            try {
                Files.move(sourceFilePath, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
                return true;
            } catch (IOException e) {
                exceptionArrayList.add(e);
                return false;
            }
        }
        return false;
    }

    public boolean playAudioFile() {
        boolean compatibility;
        try {
            compatibility = audioPlayer.playAudioFile(audioFile);
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException | InterruptedException e) {
            exceptionArrayList.add(e);
            return false;
        }
        return compatibility;
    }

    public File setAudioFileByName(String fileName) {
        if (checkPath(directory.resolve(fileName))) {
            List<Path> filesList;

            // Searching the file through layers of filters
            try (Stream<Path> walk = Files.walk(directory)) {
                Stream<Path> firstFilter = walk.filter(Files::isRegularFile);
                Stream<Path> secondFilter = firstFilter.filter(p -> p.getFileName().toString().equalsIgnoreCase(fileName));
                filesList = secondFilter.collect(Collectors.toList());
            } catch (IOException e) {
                exceptionArrayList.add(e);
                return null;
            }

            return audioFile = new File(String.valueOf(filesList.get(0)));
        }
        return null;
    }

    public void convertAudioFile(Format format) throws UnsupportedAudioFileException, IOException {
        converter.convertAudioFile(audioFile, format);
    }

    public boolean deleteFileByName(String name)  {
        if (!checkPath(directory.resolve(name))) {
            System.out.println("The file \"" + name +
                    "\" does not exists in the directory \"" + directory + "\"");
            return false;
        }

        File fileToDelete = new File(directory + "\\" + name);

        // Checking if the deletion was approved
        return fileToDelete.delete();
    }

    public boolean renameFile(String oldName, String newName) {
        File oldFile = new File(directory + "\\" + oldName);
        File newFile = new File(directory + "\\" + newName);
        return oldFile.renameTo(newFile);
    }

    public boolean createDirectory(String directoryName) {
        File newDirectory = new File(directory + "\\" + directoryName);
        return newDirectory.mkdirs();
    }

    public String getMetadata(String fileName) {
        if (checkPath(directory.resolve(fileName))){
            File file = new File(fileName);
            return "-- FILE DETAILS --" +
                    "\nName: " + fileName +
                    "\nPath: " + file.getAbsolutePath() +
                    "\nSize: " + file.length()
                    ;
        }
        return "The file does not exists";
    }

    public String getProperties(String fileName){
        if (checkPath(directory.resolve(fileName))){
            File file = new File(String.valueOf(directory.resolve(fileName)));
            try {
                return audioPlayer.getProperties(file);
            } catch (UnsupportedAudioFileException | IOException e) {
                exceptionArrayList.add(e);
                return e.getMessage();
            }
        }
        return "The file does not exists";
    }

    public long pauseAudioFile(){
        return audioPlayer.pauseAudioFile();
    }
}
