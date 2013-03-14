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

    public int frameSize() {
	return frameSize;
    }

    public int available() {
	try {
	    return input.available();
	}
	catch (IOException ioex) {
	    return 0;
	}
    }

    private double getSample(byte upper, byte lower) {
	int neg = 1 << 7;
	int u = (upper + neg) << 8;
	int l = lower + neg;
	return ((double) u + l)/((double) (1 << 15)) - 1.0d;
    }

    public int slurp(Ticker ticker, int ticks) {
	int total = 0;
	int index = 0;
	double sampleValue = 0.0;
	byte[] buffer = new byte[ticks];

	try {
	    while (input.available() > 0) {
		// read into buffer
		total += input.read(buffer, 0, ticks);

		// compute sample value
		
		
		// tick
		ticker.tick(index++, sampleValue);
	    }

	    input.reset();
	}
	catch (IOException ioex) {
	}

	return total;
    }

    public void printInfo(PrintStream printer) {
	printer.println("============================================================");
	printer.printf("%30s: %s\n", "file", filename);
	printer.printf("%30s: %s\n", "encoding", encoding);
	printer.printf("%30s: %d\n", "channels", channels);
	printer.printf("%30s: %f\n", "sample rate", sampleRate);
	printer.printf("%30s: %d\n", "bit rate", bitsPerSample);
	printer.printf("%30s: %d\n", "frame size (bytes)", frameSize);
	printer.printf("%30s: %d\n", "length (bytes)", fileLength);
	printer.printf("%30s: %d\n", "available (bytes)", this.available());
    }
}
