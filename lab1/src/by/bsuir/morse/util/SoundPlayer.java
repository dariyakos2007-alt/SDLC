package by.bsuir.morse.util;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

public final class SoundPlayer {

    private SoundPlayer() {}

    public static final double FREQ_DOT = 1200.0;

    public static final double FREQ_DASH = 700.0;

    public static final double FREQ_CLICK = 500.0;

    public static void play(double frequency, int durationMs) {
        new Thread(() -> {
            try {
                float sampleRate = 44100f;
                int numSamples = (int) (sampleRate * durationMs / 1000.0);
                byte[] buffer = new byte[numSamples * 2];

                for (int i = 0; i < numSamples; i++) {
                    double angle = 2.0 * Math.PI * i * frequency / sampleRate;
                    short value = (short) (Math.sin(angle) * Short.MAX_VALUE * 0.3); // 30% громкости
                    buffer[2 * i] = (byte) (value & 0xFF);
                    buffer[2 * i + 1] = (byte) ((value >> 8) & 0xFF);
                }

                AudioFormat format = new AudioFormat(sampleRate, 16, 1, true, false);
                SourceDataLine line = AudioSystem.getSourceDataLine(format);
                line.open(format);
                line.start();
                line.write(buffer, 0, buffer.length);
                line.drain();
                line.close();
            } catch (Exception e) { }
        }).start();
    }

    public static void playDot() {
        play(FREQ_DOT, 80);
    }

    public static void playDash() {
        play(FREQ_DASH, 160);
    }

    public static void playClick() {
        play(FREQ_CLICK, 50);
    }
}