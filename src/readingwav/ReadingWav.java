/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingwav;

import java.io.File;
import java.io.IOException;
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

    static double duration;

    public int[] readWav() {

//        File inputFile = new File("Test.wav");
//        File inputFile = new File("onclassical_demo_fiati-di-parma_thuille_terzo-tempo_sestetto_small-version.wav");
        File inputFile = new File("sine.wav");
        try {
            return audioInputStream(inputFile);

        } catch (UnsupportedAudioFileException | IOException ex) {
            Logger.getLogger(ReadingWav.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new int[0];
    }

    private int[] audioInputStream(File inputFile) throws IOException, UnsupportedAudioFileException {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(inputFile);
        int bytesPerFrame = audioInputStream.getFormat().getFrameSize();
        AudioFormat format = audioInputStream.getFormat();
        long frames = audioInputStream.getFrameLength();
        duration = (frames + 0.0) / format.getFrameRate();
        System.out.println(duration);

        System.out.println("format: " + audioInputStream.getFormat().getFrameRate());
        System.out.println("frameLength: " + audioInputStream.getFrameLength());

        if (bytesPerFrame == AudioSystem.NOT_SPECIFIED) {
            bytesPerFrame = 1;
        }
        int numBytes = 1024 * bytesPerFrame;

        byte[] audioBytes = new byte[numBytes];

        int totalFramesRead = 0;
        int numBytesRead;
        int numFramesRead;

        while ((numBytesRead = audioInputStream.read(audioBytes)) != -1) {
            numFramesRead = numBytesRead / bytesPerFrame;
            totalFramesRead += numFramesRead;

        }
        System.out.println("audioByteslength " + audioBytes.length);
        int[] result = null;

        if (format.getSampleSizeInBits() == 16) {
            int samplesLength = audioBytes.length / 2;
            result = new int[samplesLength];
            if (format.isBigEndian()) {
                for (int i = 0; i < samplesLength; ++i) {
                    byte MSB = audioBytes[i * 2];
                    byte LSB = audioBytes[i * 2 + 1];
                    result[i] = MSB << 8 | (255 & LSB);
                }
            } else {
                for (int i = 0; i < samplesLength; i += 2) {
                    byte LSB = audioBytes[i * 2];
                    byte MSB = audioBytes[i * 2 + 1];
                    result[i / 2] = MSB << 8 | (255 & LSB);
                }
            }
        } else if (format.getSampleSizeInBits() == 8) {
            int samplesLength = audioBytes.length;
            result = new int[samplesLength];
            if (format.getEncoding().toString().startsWith("PCM_SIGN")) {
                for (int i = 0; i < samplesLength; ++i) {
                    result[i] = audioBytes[i];
                }
            } else {
                for (int i = 0; i < samplesLength; ++i) {
                    result[i] = audioBytes[i] - 128;
                }
            }
        }
        System.out.println("result length: " + result.length);
        return result;
    }

}
