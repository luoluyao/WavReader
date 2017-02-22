/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingwav;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Hans
 */
public class ReadingWav {

    public byte[] readWav() {
        int totalFramesRead = 0;
        File inputFile = new File("Test.wav");

        try {
          return audioInputStream(inputFile, totalFramesRead);

        } catch (UnsupportedAudioFileException | IOException ex) {
            Logger.getLogger(ReadingWav.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private byte[] audioInputStream(File inputFile, int totalFramesRead) throws IOException, UnsupportedAudioFileException {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(inputFile);
        int bytesPerFrame = audioInputStream.getFormat().getFrameSize();

        if (bytesPerFrame == AudioSystem.NOT_SPECIFIED) {
            bytesPerFrame = 1;
        }
        int numBytes = 1024 * bytesPerFrame;
        byte[] audioBytes = new byte[numBytes];

        int numBytesRead = 0;
        int numFramesRead = 0;

        while ((numBytesRead = audioInputStream.read(audioBytes)) != -1) {
            numFramesRead = numBytesRead / bytesPerFrame;
            totalFramesRead += numFramesRead;
            
        }
        return audioBytes;
    }

}
