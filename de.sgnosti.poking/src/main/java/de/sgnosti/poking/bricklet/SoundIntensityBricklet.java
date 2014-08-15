package de.sgnosti.poking.bricklet;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import com.tinkerforge.BrickletSoundIntensity;
import com.tinkerforge.IPConnection;

import de.sgnosti.poking.util.ExceptionWrapper;

public class SoundIntensityBricklet implements Bricklet {

	private final BrickletSoundIntensity bricklet;

	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	public SoundIntensityBricklet(String uid, IPConnection ipcon) {
		this.bricklet = new BrickletSoundIntensity(uid, ipcon);
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
	public void setPeriod(long period) {
		ExceptionWrapper.wrap(() -> bricklet.setIntensityCallbackPeriod(period));
	}

	@Override
	public void setThreshold(int threshold) {
		ExceptionWrapper.wrap(() -> bricklet.setIntensityCallbackThreshold('>', threshold, threshold));
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

}
