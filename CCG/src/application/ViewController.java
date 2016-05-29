package application;

public abstract class ViewController {
	private CollectableCardEngine parent;

	/**
	 * @param parent the parent to set
	 */
	public void setParent(CollectableCardEngine parent) {
		this.parent = parent;
	}
	
	/**
	 * Change the view from this to the target
	 * @param newView
	 */
	public void changeView(View newView) {
		parent.openView(newView);
	}
	
	
}
