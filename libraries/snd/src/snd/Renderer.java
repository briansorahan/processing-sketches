
package snd;

/**
 * Render buffers of audio samples.
 */
public interface Renderer {
    public void render(double[] samples, int bufferSize);
}
