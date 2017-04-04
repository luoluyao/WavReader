/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingwav;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
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
//        File inputFile = new File("sine.wav");
        File inputFile = new File("onclassical_demo_fiati-di-parma_thuille_terzo-tempo_sestetto_small-version.wav");

        try {
            return audioInputStream(inputFile);

        } catch (UnsupportedAudioFileException | IOException ex) {
            Logger.getLogger(ReadingWav.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new int[0];
    }

    private int[] audioInputStream(File inputFile) throws IOException, UnsupportedAudioFileException {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(inputFile);
        AudioFormat format = audioInputStream.getFormat();

        int bytesPerFrame = audioInputStream.getFormat().getFrameSize();
        long frames = audioInputStream.getFrameLength();
        duration = (frames + 0.0) / format.getFrameRate();
        Path path = FileSystems.getDefault().getPath("", "onclassical_demo_fiati-di-parma_thuille_terzo-tempo_sestetto_small-version.wav");

        System.out.println("Duration: " + duration);
        System.out.println("Format " + format);
        System.out.println("FrameRate: " + audioInputStream.getFormat().getFrameRate());
        System.out.println("SampleRate: " + audioInputStream.getFormat().getSampleRate());
        System.out.println("FrameLength: " + audioInputStream.getFrameLength());
        System.out.println("ChannelCount: " + audioInputStream.getFormat().getChannels());

//        if (bytesPerFrame == AudioSystem.NOT_SPECIFIED) {
//            bytesPerFrame = 1;
//        }
//        int numBytes = 1024 * bytesPerFrame;
//        byte[] audioBytes = new byte[numBytes];
        byte[] audioBytes = Files.readAllBytes(path);
//        int totalFramesRead = 0;
//        int numBytesRead;
//        int numFramesRead;
//
//        while ((numBytesRead = audioInputStream.read(audioBytes)) != -1) {
//            numFramesRead = numBytesRead / bytesPerFrame;
//            totalFramesRead += numFramesRead;
//
//        }
        System.out.println("AudioBytesLength " + audioBytes.length);
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

        return result;
    }

}
