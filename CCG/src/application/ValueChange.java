package application;

/**
 * The Class ValueChange.
 * 
 * Represents a change in an integer value.
 */
public class ValueChange {

	/** The previous value before the change. */
	private int oldVal;

	/** The new value after the change. */
	private int newVal;

	/**
	 * Instantiates a new value change.
	 *
	 * @param oldVal
	 *            The previous value before the change.
	 * @param newVal
	 *            The new value after the change
	 */
	public ValueChange(int oldVal, int newVal) {
		this.oldVal = oldVal;
		this.newVal = newVal;
	}

	/**
	 * Instantiates a new value change.
	 *
	 * @param oldVal
	 *            The previous value before the change.
	 */
	public ValueChange(int oldVal) {
		this.oldVal = oldVal;
		this.newVal = oldVal;
	}

	/**
	 * Gets the difference between the old value and the new value (the amount
	 * it has changed).
	 *
	 * @return the difference
	 */
	public int getDiff() {
		return newVal - oldVal;
	}

	/**
	 * Sets the new value.
	 *
	 * @param newVal
	 *            The value after the change
	 */
	public void setNewVal(int newVal) {
		this.newVal = newVal;
	}

	/**
	 * Gets the new value.
	 *
	 * @return the new value after the change
	 */
	public int getNewVal() {
		return newVal;
	}

	/**
	 * Gets the old value.
	 *
	 * @return the value before the change
	 */
	public int getOldVal() {
		return oldVal;
	}

}
