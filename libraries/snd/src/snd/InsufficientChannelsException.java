package snd;

public class InsufficientChannelsException extends Exception {
    private int expected, got;

    private InsufficientChannelsException() {}

    public InsufficientChannelsException(int exp, int got) {
	this.expected = exp;
	this.got = got;
    }
    
    public int expected() { return expected; }
    public int got() { return got; }
}
