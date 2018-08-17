package main;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Scanner;

public class Solver {

	Card[][] deck;
	Card[] suspects;
	Card[] weapons;
	Card[] rooms;
	ArrayList<Player> players;
	Scanner scan;
	int playerNum;
	int playerIndex;
	Card guessSuspect;
	Card guessWeapon;
	Card guessRoom;
	int TOTAL_CARDS;

	int suggestionNum;
	ArrayList<Suggestion> suggestions;

	public Solver() {
		suggestions = new ArrayList<Suggestion>();
		suggestionNum = -1;
		suspects = defaultSuspects();
		weapons = defaultWeapons();
		rooms = defaultRooms();
		deck = defaultDeck();
		TOTAL_CARDS = suspects.length + weapons.length + rooms.length;
		setupGame();
		getHandInfo();
	}

	private void getHandInfo() {
		boolean stop = false;
		do {
			System.out.println("Which player number are you?");
			if (scan.hasNextInt()) {
				playerNum = scan.nextInt() - 1;
				stop = true;
			}
		} while (!stop);

		stop = false;
		String str = "";
		do {
			int remainingCards = (TOTAL_CARDS / players.size())
					- players.get(playerNum).getHand().getCardsHeld().size();
			if (remainingCards == 0) {
				stop = true;
			} else {
				System.out.println("Enter a card: ('Q' to quit, remaining cards: " + remainingCards + ".");
				if (scan.hasNextLine()) {
					str = scan.nextLine();
					if (str.equals("q")) {
						stop = true;
					} else {
						for (int i = 0; i < deck.length; i++) {
							for (int j = 0; j < deck[i].length; j++) {
								if (deck[i][j].getName().equalsIgnoreCase(str) && deck[i][j].isHidden()) {
									deck[i][j].setPos(players.get(playerNum).getName());
									players.get(playerNum).getHand().addCard(deck[i][j]);
									System.out.println("Added " + deck[i][j]);
								}
							}
						}
					}
				}
			}
		} while (!stop);
		for (Card c : suspects) {
			if (c.isHidden()) {
				players.get(playerNum).getHand().addToCardsNotHeld(c);
			}
		}
		for (Card c : weapons) {
			if (c.isHidden()) {
				players.get(playerNum).getHand().addToCardsNotHeld(c);
			}
		}
		for (Card c : rooms) {
			if (c.isHidden()) {
				players.get(playerNum).getHand().addToCardsNotHeld(c);
			}
		}
		System.out.println(clear() + players.get(playerNum).toString());
	}

	private String clear() {
		return "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
	}

	private void setupGame() {
		players = new ArrayList<Player>();
		boolean stop = false;
		int num = 0;
		do {
			System.out.println("How many players?");
			scan = new Scanner(System.in);
			if (scan.hasNextInt()) {
				num = scan.nextInt();
				stop = true;
			}
		} while (!stop);

		for (int i = 0; i < num; i++) {
			stop = false;
			do {
				System.out.println("What is player " + (i + 1) + "'s name?");
				scan = new Scanner(System.in);
				if (scan.hasNext()) {
					players.add(new Player(scan.nextLine()));
					stop = true;
				}
			} while (!stop);
		}
	}

	private String getPlayerHands() {
		String str = "---- Players ----\n\n";
		for (Player p : players) {
			str += (p.toString());
		}
		return str + "\n";
	}

	private ArrayList<Card> getCardsUnknown() {
		ArrayList<Card> unknowns = new ArrayList<Card>();
		for (int i = 0; i < deck.length; i++) {
			for (int j = 0; j < deck[i].length; j++) {
				if (deck[i][j].isHidden()) {
					unknowns.add(deck[i][j]);
				}
			}
		}
		return unknowns;
	}

	private Card[] defaultRooms() {
		Card[] r = { new Card("Billiard Room", Card.NOT_FOUND, Type.room),
				new Card("Conservatory", Card.NOT_FOUND, Type.room), new Card("Dining Room", Card.NOT_FOUND, Type.room),
				new Card("Kitchen", Card.NOT_FOUND, Type.room), new Card("Library", Card.NOT_FOUND, Type.room),
				new Card("Study", Card.NOT_FOUND, Type.room), new Card("Carriage House", Card.NOT_FOUND, Type.room),
				new Card("Courtyard", Card.NOT_FOUND, Type.room), new Card("Drawing Room", Card.NOT_FOUND, Type.room),
				new Card("Fountain", Card.NOT_FOUND, Type.room), new Card("Gazebo", Card.NOT_FOUND, Type.room),
				new Card("Living Room", Card.NOT_FOUND, Type.room),
				new Card("Trophy Room", Card.NOT_FOUND, Type.room) };
		return r;
	}

	private Card[] defaultWeapons() {
		Card[] w = { new Card("Candlestick", Card.NOT_FOUND, Type.weapon),
				new Card("Knife", Card.NOT_FOUND, Type.weapon), new Card("Lead Pipe", Card.NOT_FOUND, Type.weapon),
				new Card("Revolver", Card.NOT_FOUND, Type.weapon), new Card("Rope", Card.NOT_FOUND, Type.weapon),
				new Card("Wrench", Card.NOT_FOUND, Type.weapon), new Card("Horseshoe", Card.NOT_FOUND, Type.weapon),
				new Card("Poison", Card.NOT_FOUND, Type.weapon) };
		return w;
	}

	private Card[] defaultSuspects() {
		Card[] s = { new Card("Scarlet", Card.NOT_FOUND, Type.suspect),
				new Card("Mustard", Card.NOT_FOUND, Type.suspect), new Card("White", Card.NOT_FOUND, Type.suspect),
				new Card("Green", Card.NOT_FOUND, Type.suspect), new Card("Peacock", Card.NOT_FOUND, Type.suspect),
				new Card("Plum", Card.NOT_FOUND, Type.suspect), new Card("Brunette", Card.NOT_FOUND, Type.suspect),
				new Card("Grey", Card.NOT_FOUND, Type.suspect), new Card("Peach", Card.NOT_FOUND, Type.suspect),
				new Card("Rose", Card.NOT_FOUND, Type.suspect) };
		return s;
	}

	private Card[][] defaultDeck() {
		Card[][] d = { suspects, weapons, rooms };
		return d;
	}

	public void play() {
		boolean stop = false;
		playerIndex = 0;
		do {
			if (playerIndex >= players.size()) {
				playerIndex = 0;
			}
			System.out.println("It's " + players.get(playerIndex).getName() + "'s turn...\n");
			playerTurn();
			playerIndex++;
		} while (!stop);
	}

	private void playerTurn() {
		boolean stop = false;
		do {
			System.out.print("----Menu----\n1) See possible accusations\n2) See players' hands\n"
					+ "3) See data\n4) Make suggestion\n5) See prior suggestions\n6) End "
					+ players.get(playerIndex).getName() + "'s turn\n\nType a number...");
			switch (scan.nextInt()) {
			case 1:
				generateAccusations();
				break;
			case 2:
				System.out.println(getPlayerHands());
				break;
			case 3:
				generateData();
				break;
			case 4:
				recordSuggestion();
				break;
			case 5:
				showPriorSuggestions();	
				break;
			case 6:
				stop = true;
				break;
			}
		} while (!stop);
	}

	private void showPriorSuggestions() {
		if(suggestions.size()==0) {
			return;
		}
		boolean stop = false;
		do {
			System.out.print("How many? (There have been "+suggestions.size()+" made) ");
			if (scan.hasNextInt()) {
				int sNum = scan.nextInt();
				if (sNum <= suggestions.size()) {
					stop = true;
					sNum = suggestions.size()-sNum;
					while (sNum < suggestions.size()) {
						System.out.println(suggestions.get(sNum).toString());
						sNum++;
					}
				}
			}
		} while(!stop);
	}

	private void generateAccusations() {
		ArrayList<Card> cardsNotHeldTotal = players.get(0).getHand().getCardsNotHeld();
		ListIterator<Card> li = cardsNotHeldTotal.listIterator();
		for (int playerIndex = 1; playerIndex < players.size(); playerIndex++) {
			while (li.hasNext()) {
				Card card = li.next();
				boolean found = false;
				for (Card c : players.get(playerIndex).getHand().getCardsNotHeld()) {
					if (card.getName().equals(c.getName())) {
						found = true;
					}
				}
				if (!found) {
					li.remove();
				}
			}
		}

		for (Card cardNotHeld : cardsNotHeldTotal) {
			if (cardNotHeld.getType().equals(Type.suspect)) {
				guessSuspect = cardNotHeld;
			} else if (cardNotHeld.getType().equals(Type.room)) {
				guessRoom = cardNotHeld;
			} else {
				guessWeapon = cardNotHeld;
			}
		}

		boolean skipSus = false, skipWea = false, skipRoom = false;

		double hiddenSuspects = 0;
		if (guessSuspect == null) {
			for (int i = 0; i < suspects.length; i++) {
				if (suspects[i].isHidden()) {
					hiddenSuspects++;
				}
			}
		} else {
			skipSus = true;
		}

		double hiddenWeapons = 0;
		if (guessWeapon == null) {
			for (int i = 0; i < weapons.length; i++) {
				if (weapons[i].isHidden()) {
					hiddenWeapons++;
				}
			}
		} else {
			skipWea = true;
		}

		double hiddenRooms = 0;
		if (guessRoom == null) {
			for (int i = 0; i < rooms.length; i++) {
				if (rooms[i].isHidden()) {
					hiddenRooms++;
				}
			}
		} else {
			skipRoom = true;
		}

		double suspectsP = 1 / hiddenSuspects;
		System.out.println("----Suspect Info----");
		System.out.println("A " + Double.toString(suspectsP * 100).substring(0, 2)  + "% chance of guessing suspect correctly.");
		if (!skipSus) {
			for (Card c : suspects) {
				if (c.isHidden()) {
					System.out.println("\t" + c.toString());
				}
			}
		} else {
			System.out.println(guessSuspect.toString());
		}
		System.out.println();

		double weaponsP = 1 / hiddenWeapons;
		System.out.println("----Weapon Info----");
		System.out.println("A " +  Double.toString(weaponsP * 100).substring(0, 2)  + "% chance of guessing weapon correctly.");
		if (!skipWea) {
			for (Card c : weapons) {
				if (c.isHidden()) {
					System.out.println("\t" + c.toString());
				}
			}
		} else {
			System.out.println(guessWeapon.toString());
		}
		System.out.println();

		double roomsP = 1 / hiddenRooms;
		System.out.println("----Room Info----");
		System.out.println("A " +  Double.toString(roomsP * 100).substring(0, 2) + "% chance of guessing room correctly.");
		if (!skipRoom) {
			for (Card c : rooms) {
				if (c.isHidden()) {
					System.out.println("\t" + c.toString());
				}
			}
		} else {
			System.out.println(guessRoom.toString());
		}
		System.out.println();

		System.out.println("Overall percentage: " + Double.toString(((suspectsP * weaponsP * roomsP) * 100)).substring(0, 4) + "%\n\n");

	}

	private void generateData() {
		for (Player p : players) {
			System.out.println("----Cards " + p.getName() + " doesn't have----");
			for (Card c : p.getHand().getCardsNotHeld()) {
				System.out.println(c.toString());
			}
			System.out.println("\n");
		}

		System.out.println("----Unknown cards----");
		for (Card c : getCardsUnknown()) {
			System.out.println(c.toString());
		}
		System.out.println("\n");
	}

	private void recordSuggestion() {
		boolean stop = false;
		String str = "";
		String suspect = "", weapon = "", room = "";

		do {
			System.out.println("Enter person: ");
			if (scan.hasNextLine()) {
				suspect = scan.nextLine();
				for (int i = 0; i < suspects.length; i++) {
					if (suspects[i].getName().equalsIgnoreCase(suspect)) {
						System.out.println(suspects[i].toString() + ": " + suspects[i].getPos());
						stop = true;
					}
				}
			}
		} while (!stop);

		stop = false;
		do {
			System.out.println("Enter weapon: ");
			if (scan.hasNextLine()) {
				weapon = scan.nextLine();
				for (int i = 0; i < weapons.length; i++) {
					if (weapons[i].getName().equalsIgnoreCase(weapon)) {
						System.out.println(weapons[i].toString() + ": " + weapons[i].getPos());
						stop = true;
					}
				}
			}
		} while (!stop);

		stop = false;
		do {
			System.out.print("Enter room: ");
			if (scan.hasNextLine()) {
				room = scan.nextLine();
				for (int i = 0; i < rooms.length; i++) {
					if (rooms[i].getName().equalsIgnoreCase(room)) {
						System.out.println(rooms[i].toString() + ": " + rooms[i].getPos());
						stop = true;
					}
				}
			}

		} while (!stop);

		addNewSuggestion(suspect, weapon, room);

		stop = false;
		boolean skipAsking = false;
		Card card = null;
		int index = playerIndex + 1;
		do {
			if (index >= players.size()) {
				index = 0;
			}
			if (index == playerIndex) {
				stop = true;
				break;
			}
			if(!skipAsking) {
				System.out.println("Did " + players.get(index).getName() + " show a card? ");
				if (scan.hasNextLine()) {
					str = scan.nextLine();
					if (str.equalsIgnoreCase("y")) {
						suggestions.get(suggestionNum).setAnswerer(players.get(index));
						boolean cardEnteredCorrectly = false;
						do {
							System.out.println("Which card? ('n' for not shown)");
							if (scan.hasNextLine()) {
								str = scan.nextLine();
								card = null;
								if (str.equalsIgnoreCase("n") || (str.equalsIgnoreCase(suspect)
										|| str.equalsIgnoreCase(weapon) || str.equalsIgnoreCase(room))) {
									cardEnteredCorrectly = true;
									if (!str.equalsIgnoreCase("n")) {
										card = findCard(str);
										card.setPos(players.get(index).getName());
										players.get(index).getHand().addCard(card);
										switch (card.getType().ordinal()) {
										case 0:
											checkIfUnknownCard(findCard(weapon), index);
											checkIfUnknownCard(findCard(room), index);
											break;
										case 1:
											checkIfUnknownCard(findCard(suspect), index);
											checkIfUnknownCard(findCard(room), index);
											break;
										case 2:
											checkIfUnknownCard(findCard(suspect), index);
											checkIfUnknownCard(findCard(weapon), index);
											break;
										}
									} else {
										checkIfUnknownCard(findCard(suspect), index);
										checkIfUnknownCard(findCard(weapon), index);
										checkIfUnknownCard(findCard(room), index);
	
									}
									suggestions.get(suggestionNum).setCard(card);
									System.out.println(suggestions.get(suggestionNum).toString());
									skipAsking = true;
								}
							}
						} while (!cardEnteredCorrectly);
					} else {
						card = findCard(suspect);
						if (card != null && !players.get(index).getHand().getCardsNotHeld().contains(card)) {
							players.get(index).getHand().addToCardsNotHeld(card);
						}
						card = findCard(weapon);
						if (card != null && !players.get(index).getHand().getCardsNotHeld().contains(card)) {
							players.get(index).getHand().addToCardsNotHeld(card);
						}
						card = findCard(room);
						if (card != null && !players.get(index).getHand().getCardsNotHeld().contains(card)) {
							players.get(index).getHand().addToCardsNotHeld(card);
						}
						suggestions.get(suggestionNum).addPlayerSkipped(players.get(index));
					}
				}
			} else {
				switch (card.getType().ordinal()) {
				case 0:
					checkIfUnknownCard(findCard(weapon), index);
					checkIfUnknownCard(findCard(room), index);
					break;
				case 1:
					checkIfUnknownCard(findCard(suspect), index);
					checkIfUnknownCard(findCard(room), index);
					break;
				case 2:
					checkIfUnknownCard(findCard(suspect), index);
					checkIfUnknownCard(findCard(weapon), index);
					break;
				}
			}
			index++;
		} while (!stop);
	}

	private void checkIfUnknownCard(Card findCard, int index) {
		boolean found = false;
		for (Player p : players) {
			if (p.getHand().getCardsHeld().contains(findCard)) {
				found = true;
			}
		}
		if (!found && !players.get(index).getHand().getPossibilities().contains(findCard)) {
			players.get(index).getHand().addToUnknownCards(findCard);
		}
	}

	private Card findCard(String name) {
		for (int i = 0; i < deck.length; i++) {
			for (int j = 0; j < deck[i].length; j++) {
				if (deck[i][j].getName().equalsIgnoreCase(name)) {
					return deck[i][j];
				}
			}
		}
		return null;
	}

	private void addNewSuggestion(String suspect, String weapon, String room) {
		suggestions.add(new Suggestion(suspect, weapon, room, players.get(playerNum)));
		suggestionNum++;
		System.out.println("Suggestion confirmed as: " + suspect + " in the " + room + " with the " + weapon + ".\n");
	}
}
