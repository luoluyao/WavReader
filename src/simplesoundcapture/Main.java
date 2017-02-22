/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplesoundcapture;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 *
 * @author Hans
 */
public class Main {
    public static void main(String s[]) {
        SimpleSoundCaptureUI ssc = new SimpleSoundCaptureUI();
        ssc.open();
        JFrame f = new JFrame("Capture/Playback");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add("Center", ssc);
        f.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int w = 360;
        int h = 170;
        f.setLocation(screenSize.width / 2 - w / 2, screenSize.height / 2 - h / 2);
        f.setSize(w, h);
        f.show();
    }
}
