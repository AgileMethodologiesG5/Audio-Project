package ec.edu.epn.git.project;

public class AudioProjectExecute {
    public static void main(String[] args) {
        System.out.println("-- Audio Project Execute --");
        Explorer explorer = new Explorer();
        String[] listFiles = explorer.getFilesList();
        System.out.println("Showing the files' list:");
        for (String nameFile : listFiles) {
            System.out.println("\t" + nameFile);
        }
        System.out.println();

        String source = String.valueOf(explorer.directory);
        String dest = String.valueOf(explorer.directory.resolve("converterOut"));
        System.out.println("The moving file process has started:");
        explorer.moveFile("movingFile.txt",source, dest);
        System.out.println("\tThe file has been moved: " + source + " --> " + dest);
        explorer.moveFile("movingFile.txt",dest, source);
        System.out.println("\tThe file has been moved: " + dest + " --> " + source);
    }
}
