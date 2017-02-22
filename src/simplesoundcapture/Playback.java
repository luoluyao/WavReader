/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplesoundcapture;

import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import static simplesoundcapture.Capture.audioInputStream;

/**
 *
 * @author Hans
 */
public class Playback implements Runnable {

    SourceDataLine line;
    String errStr;
    Thread thread;
    final int bufSize = 16384;

    public void start() {
        errStr = null;
        thread = new Thread(this);
        thread.setName("Playback");
        thread.start();
    }

    public void stop() {
        thread = null;
    }

    private void shutDown(String message) {
        if ((errStr = message) != null) {
            System.err.println(errStr);
        }
        if (thread != null) {
            thread = null;
            SimpleSoundCaptureUI.captB.setEnabled(true);
            SimpleSoundCaptureUI.playB.setText("Play");
        }
    }

    @Override
    public void run() {

        // make sure we have something to play
        if (audioInputStream == null) {
            shutDown("No loaded audio to play back");
            return;
        }
        // reset to the beginnning of the stream
        try {
            audioInputStream.reset();
        } catch (IOException e) {
            shutDown("Unable to reset the stream\n" + e);
            return;
        }

        // get an AudioInputStream of the desired format for playback
        AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;
        float rate = 44100.0f;
        int channels = 2;
        int frameSize = 4;
        int sampleSize = 16;
        boolean bigEndian = true;

        AudioFormat format = new AudioFormat(encoding, rate, sampleSize, channels, (sampleSize / 8)
                * channels, rate, bigEndian);

        AudioInputStream playbackInputStream = AudioSystem.getAudioInputStream(format,
                audioInputStream);

        if (playbackInputStream == null) {
            shutDown("Unable to convert stream of format " + audioInputStream + " to format " + format);
            return;
        }

        // define the required attributes for our line,
        // and make sure a compatible line is supported.
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
        if (!AudioSystem.isLineSupported(info)) {
            shutDown("Line matching " + info + " not supported.");
            return;
        }

        // get and open the source data line for playback.
        try {
            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format, bufSize);
        } catch (LineUnavailableException ex) {
            shutDown("Unable to open the line: " + ex);
            return;
        }

        // play back the captured audio data
        int frameSizeInBytes = format.getFrameSize();
        int bufferLengthInFrames = line.getBufferSize() / 8;
        int bufferLengthInBytes = bufferLengthInFrames * frameSizeInBytes;
        byte[] data = new byte[bufferLengthInBytes];
        int numBytesRead = 0;

        // start the source data line
        line.start();

        while (thread != null) {
            try {
                if ((numBytesRead = playbackInputStream.read(data)) == -1) {
                    break;
                }
                int numBytesRemaining = numBytesRead;
                while (numBytesRemaining > 0) {
                    numBytesRemaining -= line.write(data, 0, numBytesRemaining);
                }
            } catch (IOException e) {
                shutDown("Error during playback: " + e);
                break;
            }
        }
        // we reached the end of the stream.
        // let the data play out, then
        // stop and close the line.
        if (thread != null) {
            line.drain();
        }
        line.stop();
        line.close();
        line = null;
        shutDown(null);
    }
}
