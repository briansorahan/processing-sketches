class OscMixer
{
	4 => int nChannels;
	Gain gains[nChannels];

	for (0 => int i; i < nChannels; i++)
	{
		gains[i] => dac;
	}

	// Connect a UGen to a channel of the mixer
	fun OscMixer connect(int chan, UGen @ input)
	{
		if (chan < 0 || chan >= nChannels)
		{
			cherr <= "[OscMixer] channel number (" <= chan
			      <= ") out of range" <= IO.newline();
			return null;
		}

		input => gains[chan];

		return this;
	}

	// Connect a UGen to a channel of the mixer
	fun OscMixer disconnect(int chan, UGen @ input)
	{
		if (chan < 0 || chan >= nChannels)
		{
			cherr <= "[OscMixer] channel number (" <= chan
			      <= ") out of range" <= IO.newline();
			return null;
		}

		input =< gains[chan];

		return this;
	}

	fun OscMixer setGain(int chan, float gain)
	{
		if (chan < 0 || chan >= nChannels)
		{
			cherr <= "[OscMixer] channel number (" <= chan
			      <= ") out of range" <= IO.newline();
			return null;
		}

		// clip the gain
		if (gain < 0.0) 0.0 => gain;
		if (gain > 1.0) 1.0 => gain;

		gain => gains[chan].gain;

		return this;
	}		
}

OscMixer mixer;

OscRecv oscRx;
57121 => oscRx.port;
oscRx.listen();

fun void oscListen(string addrPattern, OscRecv @ oscRx)
{
	oscRx.event(addrPattern) @=> OscEvent ev;
	while (true)
	{
		ev => now;
		while (ev.nextMsg() != 0)
		{
			ev.getFloat() => float val;
			
			// <<< "Received via OSC:", val >>>;
		}
	}
}

spork ~ oscListen("/osc_mixer/console/channel1/gain,f", oscRx);
spork ~ oscListen("/osc_mixer/console/channel2/gain,f", oscRx);
spork ~ oscListen("/osc_mixer/console/channel3/gain,f", oscRx);
spork ~ oscListen("/osc_mixer/console/channel4/gain,f", oscRx);
spork ~ oscListen("/osc_mixer/console/channel1/pan,f", oscRx);
spork ~ oscListen("/osc_mixer/console/channel2/pan,f", oscRx);
spork ~ oscListen("/osc_mixer/console/channel3/pan,f", oscRx);
spork ~ oscListen("/osc_mixer/console/channel4/pan,f", oscRx);

while (true)
{
	1::ms => now;
}