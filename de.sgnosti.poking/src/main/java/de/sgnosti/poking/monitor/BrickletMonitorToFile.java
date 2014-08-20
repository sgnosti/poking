package de.sgnosti.poking.monitor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileWriter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import de.sgnosti.poking.bricklet.Bricklet;
import de.sgnosti.poking.util.Clock;
import de.sgnosti.poking.util.ExceptionWrapper;

public class BrickletMonitorToFile implements AutoCloseable {
	private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(new BasicThreadFactory.Builder()
	.namingPattern("monitor-file-%d").build());

	private final FileWriter writer = ExceptionWrapper.wrap(() -> new FileWriter("d:\\bricklet-monitor-output.csv"));
	private final Clock clock = new Clock();
	private final PropertyChangeListener listener = (event) -> processEvent(event);

	private final Bricklet bricklet;

	public BrickletMonitorToFile(Bricklet bricklet) {
		this.bricklet = bricklet;
	}

	public void init() {
		bricklet.addPropertyChangeListener(listener);
		executor.scheduleAtFixedRate(() -> ExceptionWrapper.wrap(() -> writer.flush()), 10, 10, TimeUnit.SECONDS);
	}

	private void processEvent(PropertyChangeEvent event) {
		switch (event.getPropertyName()) {
		case Bricklet.PERIODICAL_EVENT:
			ExceptionWrapper.wrap(() -> writer.write(clock.getCurrentTime() + ";" + event.getNewValue() + "\n"));
		}
	}

	@Override
	public void close() throws Exception {
		bricklet.removePropertyChangeListener(listener);
		writer.close();
		System.out.println("Closed file");
	}

}
