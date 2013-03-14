/**
 * AudioFile.java
 *
 * Read a PCM-encoded audio file.
 *
 * @author Brian Sorahan
 */

package snd;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.UnsupportedAudioFileException;
import processing.core.*;

public class AudioFile {
    private String filename;
    private File file;
    private long fileLength;
    private AudioInputStream input;
    private AudioFormat format;
    private AudioFileFormat fileFormat;
    private String encoding;
    private int channels;
    private float sampleRate;
    private int bitsPerSample;
    private int frameSize;
    private final static double SAMPLE_DIV = (double) (1 << 15);

    private AudioFile() {
    }

    private AudioFile(String filename) {
	this.filename = filename;
	file = new File(filename);

	try {
	    input = AudioSystem.getAudioInputStream(file);
	    fileFormat = AudioSystem.getAudioFileFormat(file);
	    format = fileFormat.getFormat();
	    fileLength = file.length();
	    encoding = format.getEncoding().toString();
	    channels = format.getChannels();
	    sampleRate = format.getSampleRate();
	    bitsPerSample = format.getSampleSizeInBits();
	    frameSize = format.getFrameSize();
	}
	catch (UnsupportedAudioFileException audioEx) {
	}
	catch (IOException ioex) {
	}
    }

    public static AudioFile open(String filename) {
	return new AudioFile(filename);
    }

    public String filename() {
	return filename;
    }

    public int channels() {
	return channels;
    }

    public String encoding() {
	return encoding;
    }

    public float sampleRate() {
	return sampleRate;
    }

    public int bitsPerSample() {
	return bitsPerSample;
    }

    private int frameSize() {
	return frameSize;
    }

    private int available() {
	try {
	    return input.available();
	}
	catch (IOException ioex) {
	    return 0;
	}
    }

    // TODO: Support bit rates other than 16bit
    private double getSample(byte upper, byte lower) {
	int u = upper << 8;
	int l = lower;
	return ((double) (u + l)) / AudioFile.SAMPLE_DIV;
    }

    // TODO: Support multiple channels
    // TODO: Support bit rates other than 16bit
    private double[] convertBuffer(byte[] bytes, int bufferSize) {
	double[] samples = new double[bufferSize];
	for (int i = 0; i < bufferSize; i += 2) {
	    samples[i] = getSample( bytes[i], bytes[i + 1] );
	}
	return samples;
    }

    /**
     * @param bufferSize Number of samples per buffer
     * @param renderers Renderers for each channel
     */
    public int slurp(int bufferSize, Renderer... renderers) {
	if (renderers.length != this.channels())
	    

	int total = 0;
	int index = 0;
	double sampleValue = 0.0;
	byte[] buffer = new byte[bufferSize];

	try {
	    while (input.available() > 0) {
		// read into buffer
		total += input.read(buffer, 0, bufferSize);
		renderer.render(convertBuffer(buffer, bufferSize));
	    }

	    input.reset();
	}
	catch (IOException ioex) {
	}

	return total;
    }

    public String toString() {
	StringBuilder rep = new StringBuilder();
        rep.append(String.format("============================================================"));
	rep.append(String.format("%30s: %s\n", "file", filename));
	rep.append(String.format("%30s: %s\n", "encoding", encoding));
	rep.append(String.format("%30s: %d\n", "channels", channels));
	rep.append(String.format("%30s: %f\n", "sample rate", sampleRate));
	rep.append(String.format("%30s: %d\n", "bit rate", bitsPerSample));
	rep.append(String.format("%30s: %d\n", "frame size (bytes)", frameSize));
	rep.append(String.format("%30s: %d\n", "length (bytes)", fileLength));
	rep.append(String.format("%30s: %d\n", "available (bytes)", this.available()));
	return rep.toString();
    }
}
