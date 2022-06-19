package ec.edu.epn.git.project;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {

    Clip clip;
    long msPosition;
    String filename;

    // Method that checks if an audio file is compatible with the Audio Player
    public boolean checkCompatibility(File audioFile) {
        String fileName = audioFile.getName();
        String fileFormat = fileName.substring(fileName.lastIndexOf(".") + 1);

        boolean formatIsAIFF = fileFormat.equalsIgnoreCase("AIFF");
        boolean formatIsAU = fileFormat.equalsIgnoreCase("AU");
        boolean formatIsWAV = fileFormat.equalsIgnoreCase("WAV");

        // Checking if the audio file format is compatible
        return formatIsAIFF || formatIsAU || formatIsWAV;
    }

    // Method that plays the given audio file
    public boolean playAudioFile(File audioFile) throws LineUnavailableException, IOException, UnsupportedAudioFileException, InterruptedException {
        if (checkCompatibility(audioFile)) {
            // Getting the raw data of the audio file
            AudioInputStream stream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = stream.getFormat();
            int bufferSize = ((int) stream.getFrameLength() * format.getFrameSize());

            DataLine.Info info = new DataLine.Info(Clip.class, format, bufferSize);


            if (!audioFile.getName().equals(filename)){
                this.filename = audioFile.getName();
                this.msPosition = 0;
            }

            this.clip = (Clip) AudioSystem.getLine(info);


            // Playing the sound of the audio file
            this.clip.open(stream);
            this.clip.setMicrosecondPosition(msPosition);
            this.clip.start();

            return true;
        }

        return false;
    }

    public long pauseAudioFile(){
        if (this.clip != null){
            this.msPosition = this.clip.getMicrosecondPosition();
            clip.stop();
        }
        return this.msPosition;
    }

    public String getProperties(File audioFile) throws UnsupportedAudioFileException, IOException {
        if (checkCompatibility(audioFile)) {
            AudioInputStream stream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = stream.getFormat();
            int frameLength = (int) stream.getFrameLength();
            int frameSize = format.getFrameSize();
            int bufferSize = (frameLength * frameSize);
            return "-- PROPERTIES --" +
                    "\nName: " + audioFile.getName() +
                    "\nFormat: " + format +
                    "\nDuration: " + (stream.getFrameLength() + 0.0) / format.getFrameRate() + "s" +
                    "\nFrame Length: " + frameLength +
                    "\nFrame Size: " + frameSize +
                    "\nBuffer Size" + bufferSize
                    ;
        }
        return null;
    }

}
