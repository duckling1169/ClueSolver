package main;

import java.util.ArrayList;

public class Player {
	
	private Hand hand;
	private String name;

	public Player(String n) {
		name = n;
		hand = new Hand();
	}
	
	public String getName() {
		return name;
	}
	
	public Hand getHand() {
		return hand;
	}

	@Override
	public String toString() {
		return name+"'s hand: \n"+hand.toString();
	}
}
