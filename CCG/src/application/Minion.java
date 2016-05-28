package application;

import java.io.File;
import java.io.Serializable;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class PersistantCard.
 * 
 * This class represents a playable card such as a minion
 */
@XmlRootElement
public class Minion extends Card implements Attackable, Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4307839066400531082L;


	/** The card's current life. */
	private int life;

	/** The attack damage. */
	private int damage;

	public int getDamage() {
		return damage;
	}

	@XmlElement
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public static Minion loadFromXML(File file) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Minion.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			return (Minion) jaxbUnmarshaller.unmarshal(file);
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void saveToXML(Minion toSave, File destination) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Minion.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(toSave, destination);
			jaxbMarshaller.marshal(toSave, System.out);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}


	/** Whether the card is in play. */
	private boolean inPlay;

	/**  An event that is triggered when the card is damaged. */
	private Event<ValueChange> onDamaged;


	/**
	 * Constructs a new card.
	 *
	 * @param name
	 *            - Name of the card to be displayed to the player
	 * @param ruleText
	 *            - The card's rule text
	 * @param cost
	 *            - The resource cost of the card
	 * @param life
	 *            - The amount of life the card has when played
	 * @param damage
	 *            - The damage the card does when played
	 */
	public Minion(String name, String ruleText, int cost, int life, int damage) {
		super(name, ruleText, cost);

		this.life = life;
		this.damage = damage;
		onDamaged = new Event<ValueChange>();
	}

	/**
	 * Constructs a new card with blank fields. Suitable for use in an editor.
	 */
	public Minion() {
		this("", "", 0, 0, 0);
	}

	public Minion(Minion toClone) {
		this(toClone.getName(), toClone.getRuleText(), toClone.getCost(), toClone.getLife(), toClone.getDamage());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see application.Card#playCard()
	 */
	@Override
	public void playCard() {
		super.playCard();
		getOwner().getBattlefield().addEntity(this);
		getOwner().getOnSummon().fire(this);
		inPlay = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see application.Damagable#getDamageEvent()
	 */
	@Override
	public Event<ValueChange> getDamageEvent() {
		return onDamaged;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see application.Damagable#getLife()
	 */
	@Override
	public int getLife() {
		return life;
	}

	/**
	 * Sets the card's current life.
	 *
	 * @param life
	 *            - The new amount of life for the card to have
	 */
	@XmlElement
	public void setLife(int life) {
		this.life = life;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see application.Damagable#takeDamage(int, application.Attackable)
	 */
	@Override
	public ValueChange takeDamage(int amount, Attackable source) {
		ValueChange hpChange = new ValueChange(this.life);
		this.setLife(this.getLife() - amount);
		hpChange.setNewVal(this.life);
		onDamaged.fire(hpChange);
		return hpChange;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see application.Attackable#getEffectiveDamage()
	 */
	@Override
	public int getEffectiveDamage() {
		return damage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see application.Attackable#defend(application.Attackable)
	 */
	@Override
	public ValueChange defend(Attackable attacker) {
		attacker.takeDamage(getEffectiveDamage(), this);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see application.Card#isPlayable()
	 */
	@Override
	public boolean isPlayable() {
		return super.isPlayable() && getOwner().getBattlefield().getCards().size() < 7;
	}


	/**
	 * Checks if the card is in play.
	 *
	 * @return true, if is in play
	 */
	public boolean isInPlay() {
		return inPlay;
	}

}
