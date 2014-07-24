package de.sgnosti.poking.monitor;

/**
 * TODO doc
 * @author SGN
 *
 */
public interface BrickletMonitor {

	/**
	 * Start monitoring
	 */
	public void start();
	
	/**
	 * Stop monitoring
	 */
	public void stop();
	
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
	
}
