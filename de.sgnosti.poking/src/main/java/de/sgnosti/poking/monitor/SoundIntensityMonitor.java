package de.sgnosti.poking.monitor;

import com.tinkerforge.BrickletSoundIntensity;
import com.tinkerforge.IPConnection;

import de.sgnosti.poking.util.ExceptionWrapper;

public class SoundIntensityMonitor implements BrickletMonitor {

	private final String uid;
	private final IPConnection ipcon;
	private final BrickletSoundIntensity bricklet;
	
	private int threshold;
	private int period;
	
	public SoundIntensityMonitor(String uid, IPConnection ipcon) {
		this.uid = uid;
		this.ipcon = ipcon;
		this.bricklet = new BrickletSoundIntensity(uid, ipcon);
	}
	
	public void start() {
	}
	
	public void stop() {
	}

	@Override
	public long getPeriod() {
		return ExceptionWrapper.wrap(() -> bricklet.getIntensityCallbackPeriod());
	}

	@Override
	public int getThreshold() {
		return ExceptionWrapper.wrap(() -> bricklet.getIntensityCallbackThreshold().min);
	}

	@Override
	public void setPeriod(int period) {
		ExceptionWrapper.wrap(() -> bricklet.setIntensityCallbackPeriod(period));
	}

	@Override
	public void setThreshold(int threshold) {
		ExceptionWrapper.wrap(() -> bricklet.setIntensityCallbackThreshold('>', threshold, threshold));
	}
}
