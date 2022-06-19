package ec.edu.epn.git.project;

public class AudioProjectExecute {
    public static void main(String[] args) {
        try{
            System.out.println("-- Audio Project Execute --");
            Explorer explorer = new Explorer();
            String[] listFiles = explorer.getFilesList();
            System.out.println("Showing the files' list:");
            for (String nameFile : listFiles) {
                System.out.println("\t" + nameFile);
            }

            String src = String.valueOf(explorer.directory);
            String dest = String.valueOf(explorer.directory.resolve("converterOut"));
            System.out.println("\nThe moving file process has started:");
            explorer.moveFile("movingFile.txt",src, dest);
            System.out.println("\tThe file has been moved: " + src + " --> " + dest);
            explorer.moveFile("movingFile.txt",dest, src);
            System.out.println("\tThe file has been moved: " + dest + " --> " + src);

            System.out.println("\nChecking the playing process and the audio compatibility:");
            explorer.setAudioFileByName("cheer.wav");
            /*
            boolean compatibility = explorer.playAudioFile();
            if (compatibility) {
                System.out.println("\tThe file is compatible");
            }
             */

            System.out.println("\nThe conversion process has started:");
            explorer.convertAudioFile(Format.AU);


            System.out.println("\nThe deletion process has started:");
            src = String.valueOf(explorer.directory.resolve("converterOut"));
            dest = String.valueOf(explorer.directory);
            explorer.moveFile("AUcheer.au",src,dest);
            boolean success = explorer.deleteFileByName("AUcheer.au");
            if (success){
                System.out.println("\tThe file was deleted successfully");
            }

            System.out.println("\nThe rename process has started:");
            explorer.renameFile("arc1.wav","arc2.wav");
            success = explorer.renameFile("arc2.wav","arc1.wav");
            if (success){
                System.out.println("\tThe file was renamed successfully");
            }

            System.out.println("\nThe directories creation process has started:");
            success = explorer.createDirectory("new");
            explorer.deleteFileByName("new");
            if (success){
                System.out.println("\tThe directory was created successfully");
            }

            System.out.println("\nThe metadata getting/showing process has started:");
            System.out.println(explorer.getMetadata("arc1.wav"));

            System.out.println("\nThe properties getting/showing process has started:");
            System.out.println(explorer.getProperties("arc1.wav"));

            System.out.println("\nThe pausing process has started:");
            explorer.playAudioFile();
            System.out.println("\tms pointer: "+explorer.pauseAudioFile());

            System.out.println("\nThe stopping process has started:");
            explorer.playAudioFile();
            System.out.println("\tms pointer: "+ explorer.stopAudioFile());

        }catch (Exception e){}

    }
}
