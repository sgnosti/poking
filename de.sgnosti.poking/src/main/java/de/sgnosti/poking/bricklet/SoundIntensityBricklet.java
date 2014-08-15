package de.sgnosti.poking.bricklet;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import com.tinkerforge.BrickletSoundIntensity;
import com.tinkerforge.BrickletSoundIntensity.IntensityListener;
import com.tinkerforge.BrickletSoundIntensity.IntensityReachedListener;
import com.tinkerforge.IPConnection;

import de.sgnosti.poking.util.ExceptionWrapper;

public class SoundIntensityBricklet implements Bricklet {

	private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(new BasicThreadFactory.Builder()
			.namingPattern("sound-intensity-bricklet-%d").build());

	private final BrickletSoundIntensity bricklet;
	private AtomicBoolean activity = new AtomicBoolean(false);

	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	private IntensityListener intensityListener = (intensity) -> (propertyChangeSupport.firePropertyChange(Bricklet.PERIODICAL_EVENT, null,
			intensity)
			);

	private IntensityReachedListener intensityReachedListener = new IntensityReachedListener() {

		@Override
		public void intensityReached(int intensity) {
			if (!activity.getAndSet(true)) {
				propertyChangeSupport.firePropertyChange(THRESHOLD_REACHED, false, true);
				executor.scheduleWithFixedDelay(activityStoppedTask, getPeriod(), 0, TimeUnit.MILLISECONDS);
			}
		}
	};

	private Runnable activityStoppedTask = new Runnable() {

		@Override
		public void run() {
			if (activity.getAndSet(false)) {
				propertyChangeSupport.firePropertyChange(THRESHOLD_REACHED, true, false);
			}
		}
	};


	public SoundIntensityBricklet(String uid, IPConnection ipcon) {
		this.bricklet = new BrickletSoundIntensity(uid, ipcon);
		bricklet.addIntensityListener(intensityListener);
		bricklet.addIntensityReachedListener(intensityReachedListener);
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
