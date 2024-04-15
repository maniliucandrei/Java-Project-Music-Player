import javax.swing.*;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MusicPlayer extends JFrame {

    private Clip clip;
    private JButton playButton, stopButton, pauseButton;
    private boolean isPaused = false;
    private long clipTimePosition = 0;

    public MusicPlayer() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Simple Music Player");
        this.setSize(300, 100);
        this.setLayout(new FlowLayout());

        playButton = new JButton("Play");
        stopButton = new JButton("Stop");
        pauseButton = new JButton("Pause");

        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isPaused) {
                    clip.setMicrosecondPosition(clipTimePosition);
                    clip.start();
                    isPaused = false;
                } else {
                    playAudio();
                }
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (clip != null) {
                    clipTimePosition = clip.getMicrosecondPosition();
                    clip.stop();
                    isPaused = true;
                }
            }
        });

        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (clip != null) {
                    clip.stop();
                    clip.setMicrosecondPosition(0);
                    isPaused = false;
                }
            }
        });

        this.add(playButton);
        this.add(pauseButton);
        this.add(stopButton);
    }

    private void playAudio() {
        try {
            File audioFile = new File("C:\\Users\\User\\Music\\Universal MusicDownloader\\Downloaded Files");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MusicPlayer().setVisible(true);
            }
        });
    }
}