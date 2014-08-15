package de.sgnosti.poking.bricklet;

import java.beans.PropertyChangeListener;

/**
 * TODO doc
 * 
 * @author SGN
 *
 */
public interface Bricklet {

	public static String PERIODICAL_EVENT = "periodical";
	public static String THRESHOLD_REACHED = "thresholdReached";

	/**
	 * 
	 * @return period
	 */
	public long getPeriod();

	/**
	 * 
	 * @param period
	 */
	public void setPeriod(long period);

	/**
	 * 
	 * @return threshold
	 */
	public int getThreshold();

	/**
	 * 
	 * @param threshold
	 */
	public void setThreshold(int threshold);

	/**
	 * 
	 * @param listener
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener);

	/**
	 * 
	 * @param listener
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener);

}
