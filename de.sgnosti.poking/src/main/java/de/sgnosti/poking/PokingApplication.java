package de.sgnosti.poking;

import java.io.InputStreamReader;

import com.tinkerforge.BrickletHumidity;
import com.tinkerforge.IPConnection;

import de.sgnosti.poking.bricklet.SoundIntensityBricklet;
import de.sgnosti.poking.monitor.BrickletMonitorToFile;
import de.sgnosti.poking.util.ExceptionWrapper;

/**
 * Yet another poking device
 *
 */
public class PokingApplication {

	private static final String SOUND_UID = "mkZ";
	private static final String HUMIDITY_UID = "keJ";
	private static String HOST = "localhost";
	private static int PORT = 4223;

	public static void main(String[] args) {

		final IPConnection ipcon = new IPConnection();
		ExceptionWrapper.wrap(() -> ipcon.connect(HOST, PORT));

		final BrickletHumidity humidityBricklet = new BrickletHumidity(HUMIDITY_UID, ipcon);

		final SoundIntensityBricklet soundBricklet = new SoundIntensityBricklet(SOUND_UID, ipcon);
		soundBricklet.setPeriod(300);
		soundBricklet.setThreshold(15000);

		try (BrickletMonitorToFile monitor = new BrickletMonitorToFile(soundBricklet)) {
			monitor.init();
			System.out.println("Monitoring started. Press any key to end...");
			ExceptionWrapper.wrap(() -> new InputStreamReader(System.in).read());
			System.out.println("Finishing...");
			ExceptionWrapper.wrap(() -> ipcon.disconnect());
		} catch (final Exception e) {
			e.printStackTrace();
		}

	}
}
