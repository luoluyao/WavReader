/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplesoundcapture;

/**
 *
 * @author Hans
 */
import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;

public class SimpleSoundCaptureUI extends JPanel implements ActionListener {

    
    Capture capture = new Capture();
    Playback playback = new Playback();

    static JButton playB, captB;
    JTextField textField;
    String errStr;

    File file;

    public SimpleSoundCaptureUI() {
        setLayout(new BorderLayout());
        EmptyBorder eb = new EmptyBorder(5, 5, 5, 5);
        SoftBevelBorder sbb = new SoftBevelBorder(SoftBevelBorder.LOWERED);
        setBorder(new EmptyBorder(5, 5, 5, 5));

        JPanel p1 = new JPanel();
        p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));

        JPanel p2 = new JPanel();
        p2.setBorder(sbb);
        p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBorder(new EmptyBorder(10, 0, 5, 0));
        playB = addButton("Play", buttonsPanel, false);
        captB = addButton("Record", buttonsPanel, true);
        p2.add(buttonsPanel);

        p1.add(p2);
        add(p1);
    }

    public void open() {
    }

    public void close() {
        if (playback.thread != null) {
            playB.doClick(0);
        }
        if (capture.thread != null) {
            captB.doClick(0);
        }
    }

    private JButton addButton(String name, JPanel p, boolean state) {
        JButton b = new JButton(name);
        b.addActionListener(this);
        b.setEnabled(state);
        p.add(b);
        return b;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj.equals(playB)) {
            if (playB.getText().startsWith("Play")) {
                playback.start();
                captB.setEnabled(false);
                playB.setText("Stop");
            } else {
                playback.stop();
                captB.setEnabled(true);
                playB.setText("Play");
            }
        } else if (obj.equals(captB)) {
            if (captB.getText().startsWith("Record")) {
                capture.start();
                playB.setEnabled(false);
                captB.setText("Stop");
            } else {
                capture.stop();
                playB.setEnabled(true);
            }

        }
    }

    /**
     * Write data to the OutputChannel.
     */
    /**
     * Reads data from the input channel and writes to the output stream
     */
}
