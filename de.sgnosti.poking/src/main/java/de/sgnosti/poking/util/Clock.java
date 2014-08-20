package de.sgnosti.poking.util;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

public class Clock {
	private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(new BasicThreadFactory.Builder()
	.namingPattern("clock-%d").build());

	private LocalDateTime currentTime = LocalDateTime.now();

	public Clock() {
		executor.scheduleAtFixedRate(() -> (currentTime = LocalDateTime.now()), 100, 100, TimeUnit.MILLISECONDS);
	}

	public LocalDateTime getCurrentTime() {
		return currentTime;
	}
}
