package de.sgnosti.poking;

import java.io.IOException;
import java.io.InputStreamReader;

import com.tinkerforge.AlreadyConnectedException;
import com.tinkerforge.BrickletHumidity;
import com.tinkerforge.BrickletSoundIntensity;
import com.tinkerforge.BrickletSoundIntensity.IntensityReachedListener;
import com.tinkerforge.IPConnection;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

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
		final BrickletSoundIntensity soundBricklet = new BrickletSoundIntensity(SOUND_UID, ipcon);
		final BrickletHumidity humidityBricklet = new BrickletHumidity(HUMIDITY_UID, ipcon);

		try {
			ipcon.connect(HOST, PORT);

			// trigger an event when measured intensity is over the threshold
			soundBricklet.setIntensityCallbackThreshold('>', 1200, 1200);

			soundBricklet.addIntensityReachedListener(new IntensityReachedListener() {

				@Override
				public void intensityReached(int intensity) {
					// for now, I don't care, just print it out
					System.out.println("Sound intensity reached " + intensity);
				}
			});

			// periodical event
			soundBricklet.setIntensityCallbackPeriod(10000);

			// and now with lambda expressions
			soundBricklet.addIntensityListener((final int intensity) -> System.out.println("Current sound instensity: " + intensity));

			// configure humidity sensor
			humidityBricklet.setHumidityCallbackThreshold('>', (short) 60, (short) 60);
			humidityBricklet.addHumidityReachedListener((final int humidity) -> System.out.println("Humidity reached " + humidity / 10.0 + "%"));

			humidityBricklet.setHumidityCallbackPeriod(10000);
			humidityBricklet.addHumidityListener((final int humidity) -> System.out.println("Current humidity " + humidity / 10.0 + "%"));

			new InputStreamReader(System.in).read();

		} catch (AlreadyConnectedException | IOException | TimeoutException | NotConnectedException e) {
			e.printStackTrace();
		} finally {
			try {
				ipcon.disconnect();
			} catch (final NotConnectedException e) {
				System.out.println("Cached exceptions are just anoying");
				e.printStackTrace();
			}
		}
	}
}
