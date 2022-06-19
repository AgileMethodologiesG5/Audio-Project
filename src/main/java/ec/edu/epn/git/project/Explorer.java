package ec.edu.epn.git.project;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

public class Explorer {

    Path directory;
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
}
