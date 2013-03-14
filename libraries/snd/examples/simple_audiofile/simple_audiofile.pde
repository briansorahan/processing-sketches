import snd.*;

AudioFile af;
Renderer renderer;

void setup() {
  size(400, 300);
  af = AudioFile.open("windchimes.wav");
  renderer = new Renderer() {
  };
  noLoop();
}

void draw() {
  background(30);
}

class MyRenderer implements Renderer {
  MyRenderer() {
  }
  public void render(double[] samples) {
  }
}
