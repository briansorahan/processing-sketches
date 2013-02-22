class Channel {
  private Console console;
  private NetAddress remoteHost;

  public String oscAddr;
  public int number;
  public int x=0, y=0, w=50, h=height;
  public final int MARGIN_L = 2;
  public final int MARGIN_TOP = 2;
  public final int MARGIN_R = 2;
  public final int MARGIN_BOTTOM = 36;
  public final int GAIN_W = 50;
  public final int GAIN_H = 180;
  public final int PAN_W = GAIN_W;
  public final int PAN_H = PAN_W;
  public final int PAN_MARGIN_BOTTOM = 10;

  private Channel() {
  }

  public Channel(final ControlP5 cp5, final OscP5 oscp5, final Console console, int number) {
    this.number = number;
    this.console = console;
    this.oscAddr = console.oscAddr + "/channel" + number;
    this.remoteHost = console.remoteHost;

    int cx = console.x, cy = console.y;
    int chanx = cx + ((this.w + console.CHANNEL_SPACING) * (number - 1));
    int gainy = cy + this.y + (h - MARGIN_BOTTOM - GAIN_H);
    final String gain_name = "gain" + number;
    final String pan_name = "pan" + number;
    final String oscAddress = this.oscAddr;
    final NetAddress addr = console.remoteHost;

    cp5.addSlider(gain_name)
      .setPosition(chanx, gainy)
        .setSize(GAIN_W, GAIN_H)
          .setRange(0, 1)
            .captionLabel().setVisible(false);

    // Get rid of value label
    cp5.getController(gain_name).valueLabel().setVisible(false);

    // Handle controller events
    cp5.getController(gain_name).addListener(new ControlListener() {
      public void controlEvent(ControlEvent ev) {
        if (addr != null) {
          OscMessage msg = new OscMessage(oscAddress + "/gain");
          msg.add(ev.getValue());
          oscp5.send(msg, remoteHost);
        }
      }
    }
    );

    int pany = gainy - PAN_MARGIN_BOTTOM - PAN_H;
    cp5.addKnob(pan_name)
      .setPosition(chanx, pany)
        .setSize(PAN_W, PAN_H)
          .setRange(0, 1)
            .setNumberOfTickMarks(2)
              .captionLabel().setVisible(false);

    // Get rid of value label
    cp5.getController(pan_name).valueLabel().setVisible(false);

    // Handle controller events
    cp5.getController(pan_name).addListener(new ControlListener() {
      public void controlEvent(ControlEvent ev) {
        if (addr != null) {
          OscMessage msg = new OscMessage(oscAddress + "/pan");
          msg.add(ev.getValue());
          oscp5.send(msg, remoteHost);
        }
      }
    }
    );
  }

  public Channel setRemoteHost(NetAddress host) {
    this.remoteHost = host;
    return this;
  }
}

