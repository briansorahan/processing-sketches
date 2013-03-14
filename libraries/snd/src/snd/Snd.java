package snd;

import java.util.Set;
import processing.core.PApplet;

public class Snd {
    private PApplet papplet;
    private AudioFile audioFile;
    
    public Snd(PApplet papplet) {
	this.papplet = papplet;
    }

    public Snd openAudioFile(String filename) {
	audioFile = AudioFile.open(filename);
	return this;
    }
}
