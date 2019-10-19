package main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static main.MainFrame.piano;

public class Chord {

    private static final int CHORD_TOUCH_MINOR = KeyEvent.VK_X;
    private static final int CHORD_TOUCH_MAJOR = KeyEvent.VK_N;

    static int[] touchAccord = {-1, -1};
    static int mainTouch = -1;

    static boolean touchMinorDown = false;
    static boolean touchMajorDown = false;

    public static class ChordListener extends KeyAdapter {


        @Override
        public void keyPressed(KeyEvent e) {

            int keyCode = e.getKeyCode();


            touchMinorDown = keyCode == CHORD_TOUCH_MINOR || touchMinorDown;
            touchMajorDown = keyCode == CHORD_TOUCH_MAJOR || touchMajorDown;


            if (touchMajorDown) {
                int touch = -1;

                for (int i = 0; i != piano.pianoKeys.size(); i++) {
                    if (piano.pianoKeys.get(i).isDown()) {
                        if (touch == -1) {
                            touch = i;
                        } else {
                            touch = -2;
                        }
                    }
                }
                if (touch > -1) {
                    if (touch + 4 < piano.pianoKeys.size()) {
                        piano.pianoKeys.get(touch + 4).setDown(true);
                        touchAccord[0] = touch + 4;
                    }
                    if (touch + 7 < piano.pianoKeys.size()) {
                        piano.pianoKeys.get(touch + 7).setDown(true);
                        touchAccord[1] = touch + 7;
                    }
                }
            } else if (touchMinorDown) {
                int touch = -1;

                for (int i = 0; i != piano.pianoKeys.size(); i++) {
                    if (piano.pianoKeys.get(i).isDown()) {
                        if (touch == -1) {
                            touch = i;
                        } else {
                            touch = -2;
                        }
                    }
                }
                if (touch > -1) {
                    if (touch + 3 < piano.pianoKeys.size()) {
                        piano.pianoKeys.get(touch + 3).setDown(true);
                        touchAccord[0] = touch + 3;
                    }
                    if (touch + 7 < piano.pianoKeys.size()) {
                        piano.pianoKeys.get(touch + 7).setDown(true);
                        touchAccord[1] = touch + 7;
                    }
                }
            }
        }


        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();

            if (keyCode == CHORD_TOUCH_MINOR) {

                touchMinorDown = false;

                for (int i = 0; i < touchAccord.length; i++) {
                    if (touchAccord[i] > -1)
                        piano.pianoKeys.get(touchAccord[i]).setDown(false);
                }
            }

            if (keyCode == CHORD_TOUCH_MAJOR) {

                touchMajorDown = false;

                for (int i = 0; i < touchAccord.length; i++) {
                    if (touchAccord[i] > -1)
                        piano.pianoKeys.get(touchAccord[i]).setDown(false);
                }
            }
        }


    }


}
