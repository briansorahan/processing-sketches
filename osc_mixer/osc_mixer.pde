import controlP5.*;
import oscP5.*;
import netP5.*;

ControlP5 cp5;
OscP5 oscp5;
NetAddress dest;
Console console;
final int OSC_PORT = 57120;

void setup() {
  size(350, 300);
  noStroke();

  cp5 = new ControlP5(this);
  oscp5 = new OscP5(this, OSC_PORT);
  dest = new NetAddress("127.0.0.1", OSC_PORT);
  console = new Console("console", cp5, oscp5, 30, 0);
  console.setOscAddress("/osc_mixer")
    .setRemoteHost(dest)
      .setChannels(4);
}

void draw() {
  background(20);
}

void oscEvent(OscMessage msg) {
  System.out.println("Received OSC message:\n");
  System.out.println(msg.addrPattern() + "," + msg.typetag());
  System.out.println(msg.get(0).floatValue());
}
