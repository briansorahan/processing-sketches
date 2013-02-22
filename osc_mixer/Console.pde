class Console {
  public final int CHANNEL_SPACING = 30;
  
  private ControlP5 cp5;
  private OscP5 oscp5;
  private Channel[] channels;
  
  public int x, y;
  public int w = width, h = height;
  public int nChannels;
  public String name;
  public String oscAddr;
  // destination for osc messages
  public NetAddress remoteHost;

  private Console() {
  }

  public Console(String name, ControlP5 cp5, OscP5 oscp5, int x, int y) {
    this.name = name;
    this.oscAddr = "/" + name;
    this.cp5 = cp5;
    this.oscp5 = oscp5;
    this.x = x;
    this.y = y;
  }
  
  public Console setChannels(int nChannels) {
    this.nChannels = nChannels;
    channels = new Channel[nChannels];
    for (int i = 1; i <= nChannels; i++) {
      channels[i - 1] = new Channel(cp5, oscp5, this, i);
    }
    
    return this;
  }
  
  public Console setOscAddress(String path) {
    this.oscAddr = path + "/" + name;
    return this;
  }
  
  public Console setRemoteHost(NetAddress host) {
    this.remoteHost = host;
    for (int i = 0; i < nChannels; i++) {
      channels[i].setRemoteHost(host);
    }
    return this;
  }
}

