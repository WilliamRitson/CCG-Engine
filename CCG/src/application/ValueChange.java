package application;

public class ValueChange {
	private int oldVal;
	private int newVal;
	
	public ValueChange(int oldVal, int newVal) {
		this.oldVal = oldVal;
		this.newVal = newVal;
	}
	
	public ValueChange(int oldVal) {
		this.oldVal = oldVal;
	}
	
	public int getDiff() {
		return oldVal - newVal;
	}

	public void setNewVal(int newVal) {
		this.newVal = newVal;
	}
	
	public int getNewVal() {
		return newVal;
	}

}
