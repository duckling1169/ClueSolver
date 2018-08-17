package main;

public class Card {

	private String name;
	private String pos;
	private Type type;
	public static final String NOT_FOUND = "Unknown";

	public Card(String n, String p, Type t) {
		pos = p;
		name = n;
		type = t;
	}

	public boolean isHidden() {
		return pos == NOT_FOUND;
	}

	public String getName() {
		return name;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public Type getType() {
		return type;
	}

	@Override
	public String toString() {
		return name + " (" + type + ")";
	}
}
