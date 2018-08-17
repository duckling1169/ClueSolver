package main;

import java.util.ArrayList;

public class Hand {

	private ArrayList<Card> cardsHeld;
	private ArrayList<Card> cardsNotHeld;
	private ArrayList<Card> possibilities;
	
	public Hand() {
		cardsHeld = new ArrayList<Card>();
		cardsNotHeld = new ArrayList<Card>();
		possibilities = new ArrayList<Card>();
	}
	
	public void addCard(Card c) {
		cardsHeld.add(c);
	}
	
	public void addToCardsNotHeld(Card c) {
		cardsNotHeld.add(c);
	}
	
	public void addToUnknownCards(Card c) {
		possibilities.add(c);
	}

	public ArrayList<Card> getCardsHeld() {
		return cardsHeld;
	}

	public ArrayList<Card> getCardsNotHeld() {
		return cardsNotHeld;
	}

	public ArrayList<Card> getPossibilities() {
		return possibilities;
	}

	@Override
	public String toString() {
		String str = "Cards in hand:\n";
		if(cardsHeld.size()>0) {
			for(Card c:cardsHeld) {
				str+="\t["+c.toString()+"]\n";
			}
		}
		if(cardsNotHeld.size()>0) {
			str+="\nCards not in hand:\n";
			for(Card c:cardsNotHeld) {
				str+="\t["+c.toString()+"]\n";
			}
		}
		if(possibilities.size()>0) {
			str+="\nCards that might be in hand:\n";
			for(Card c:possibilities) {
				str+="\t["+c.toString()+"]\n";
			}
		}
		return str+"\n";
	}
	
}
