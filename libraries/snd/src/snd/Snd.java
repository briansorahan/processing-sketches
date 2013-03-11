package snd;

import processing.core.*;

public class Snd {
    private PApplet papplet;
    private static Snd instance;

    private Snd() {}

    private Snd(PApplet papplet) {
	this.papplet = papplet;
    }

    public static Snd getInstance(PApplet papplet) {
	if (instance == null)
	    instance = new Snd(papplet);
	return instance;
    }
}