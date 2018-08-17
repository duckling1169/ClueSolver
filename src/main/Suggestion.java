package main;

import java.util.ArrayList;

public class Suggestion {

	private String suspect;
	private String weapon;
	private String room;
	private Player suggester;
	private Player answerer;
	private Card card;
	private ArrayList<Player> playersSkipped;
	
	public Suggestion(String s, String w, String r, Player p) {
		suspect = s;
		weapon = w;
		room = r;
		suggester = p;
	}
	
	public void setAnswerer(Player p) {
		answerer=p;
	}
	
	public void setCard(Card c) {
		card = c;
	}
	
	public void addPlayerSkipped(Player p) {
		if(playersSkipped == null) {
			playersSkipped = new ArrayList<Player>();
		}
		playersSkipped.add(p);
	}

	@Override
	public String toString() {
		String str = "Suggestion made by " + suggester.getName() + ":\n" + suspect + " in the " + room + " with the "
				+ weapon + ".\n";
		if (card != null) 
				str += "The card, shown by "+card.getPos()+", was " + card.toString() + ".\n";
			else
				str += "The card, shown by "+answerer.getName()+", was unknown.\n";
		
		if(playersSkipped != null) {
			str+="Players who played nothing:";
			for(Player p:playersSkipped) {
				str+="\n\t"+p.getName();
			}
		}
		return str;
	}
}
