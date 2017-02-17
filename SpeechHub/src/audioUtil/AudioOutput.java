package audioUtil;

import java.io.File;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import javax.sound.sampled.*;


public class AudioOutput {
    private final CyclicBarrier barrier = new CyclicBarrier(2);

    public void output (String file) {
        try {
            play(new File(file));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void play(File file) {
        try {
            final Clip clip = (Clip)AudioSystem.getLine(new Line.Info(Clip.class));
            clip.open(AudioSystem.getAudioInputStream(file));
            listenForEndOf(clip);
            clip.start();
            waitForSoundEnd();

        }
        catch (Exception exc) {
            exc.printStackTrace(System.out);
        }
    }

    private void listenForEndOf(final Clip clip) {

        clip.addLineListener(event -> {
            if (event.getType() == LineEvent.Type.STOP) waitOnBarrier();
        });
    }

    private void waitOnBarrier() {

        try {

            barrier.await();
        } catch (final InterruptedException ignored) {
        } catch (final BrokenBarrierException e) {

            throw new RuntimeException(e);
        }
    }

    private void waitForSoundEnd() {

        waitOnBarrier();
    }

    public static void main(String[] args){
        AudioOutput ao = new AudioOutput();
        AudioFeedbackFiles files = new AudioFeedbackFiles();

        try {
            ao.play(new File(files.response()));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

}
