package Model;

import java.util.Vector;
import java.util.Collections;

public class Round {


    private Deck d = new Deck();
    private Player player = new Player();
    private Computer computer = new Computer();
    private Human human = new Human();
    private Serialization s = new Serialization();

    private int round = 1;
    private Vector<Card> humanDeck = new Vector<Card>();;
    private Vector<Card> computerDeck = new Vector<Card>();
    private Vector<Card> layout = new Vector<Card>();
    private Vector<Card> deckOfCards = new Vector<Card>();;
    private Vector<Card> humanCapture = new Vector<Card>();
    private Vector<Card> computerCapture = new Vector<Card>();

    public Round() {

    }

    /**
     * Set the round
     * @param int round
     */
    public void setRound(int round) {

        this.round = round;

    }

    /**
     * Set the layout
     * @param Vector<Model.Card> layout
     */
    public void setLayoutDeck(Vector<Card> layout) {

        this.layout = layout;

    }

    /**
     * Set the human Model.Deck
     * @param Vector<Model.Card> humanDeck
     */
    public void setHumanDeck(Vector<Card> humanDeck) {

        this.humanDeck = humanDeck;
    }


    /**
     * Set the human capture deck
     * @param Vector<Model.Card> humanCapture
     */
    public void sethumanCaptureDeck(Vector<Card> humanCapture) {

        this.humanCapture = humanCapture;
    }

    /**
     * Set the Model.Computer Model.Deck
     * @param Vector<Model.Card> computerDeck
     */
    public void setComputerDeck(Vector<Card> computerDeck) {

        this.computerDeck = computerDeck;
    }

    /**
     * set the computer capture deck
     * @param Vector<Model.Card> computerCapture
     */
    public void setComputerCaptureDeck(Vector<Card> computerCapture) {

        this.computerCapture = computerCapture;
    }

    /**
     * Set the stockPile
     * @param Vector<Model.Card> deckOfCards
     */
    public void setDeck(Vector<Card> deckOfCards) {

        this.deckOfCards = deckOfCards;
    }

    /**
     * Set the computer score
     * @param int cScore
     */
    public void setComputerScore(int cScore) {

        computer.setScore(cScore);
    }

    /**
     * get Computer score
     * @return int
     */
    public int getComputerScore() {
        return computer.getScore();
    }

    /**
     * set the human score
     * @param int hScore
     */
    public void setHumanScore(int hScore) {

        human.setScore(hScore);

    }

    /**
     * get human score
     * @return int
     */
    public int getHumanScore() {
        return human.getScore();
    }

    /**
     * Get the round
     * @return int
     */
    public int getRound() {

        return this.round;
    }

    /**
     * Gets the layout
     * @return Vector<Model.Card>
     */
    public Vector<Card> getLayoutDeck() {

        return this.layout;
    }


    /**
     * Gets the human Model.Deck
     * @return Vector<Model.Card>
     */
    public Vector<Card> getHumanDeck() {

        return this.humanDeck;
    }

    /**
     * Gets the computer Model.Deck
     * @return Vector<Model.Card>
     */
    public Vector<Card> getComputerDeck() {

        return this.computerDeck;
    }

    /**
     * Gets the computer capture deck
     * @return Vector<Model.Card>
     */
    public Vector<Card> getComputerCaptureDeck() {

        return this.computerCapture;
    }

    /**
     * Gets the human capture deck
     * @return Vector<Model.Card>
     */
    public Vector<Card> gethumanCaptureDeck() {

        return this.humanCapture;
    }

    /**
     * Gets the stock Pile
     * @return Vector<Model.Card>
     */
    public Vector<Card> getDeck() {

        return this.deckOfCards;
    }


    /**
     * gets the current player
     * @param boolean isTurn
     */
    public void currentPlayer(boolean isTurn) {

        if(isTurn == true) {

            computer.setTurn(true);
            human.setTurn(false);
        }

        else {

            computer.setTurn(false);
            human.setTurn(true);
        }
    }

    /**
     * Gets the next player
     * @return string of next player
     */
    public String getNextPlayer() {

        if (computer.getIsTurn()) {
            return "Human";
        }

        return "Computer";

    }

    /**
     * Sets up round
     */
    public void setUpRound() {

        Vector<Card> newDeck;
        d.shuffleCards();
        newDeck = d.getDeck();


        d.shuffleCards();
        deckOfCards = d.getDeck();
        deckOfCards.addAll(newDeck);
        Collections.shuffle(deckOfCards);

        /**
         put cards in hand
         Five cards to human
         Five cards to computer
         Four cards to layout
         Five cards to human
         Five cards to computer
         Four cards to layout
         */


        for(int i = 0; i < 5; i++) {
            humanDeck.addElement(deckOfCards.elementAt(0));
            deckOfCards.remove(0);
        }
        for(int i = 0; i < 5; i++) {
            computerDeck.addElement(deckOfCards.elementAt(0));
            deckOfCards.remove(0);
        }
        for(int i = 0; i < 4; i++) {
            layout.addElement(deckOfCards.elementAt(0));
            deckOfCards.remove(0);
        }
        for(int i = 0; i < 5; i++) {
            humanDeck.addElement(deckOfCards.elementAt(0));
            deckOfCards.remove(0);
        }
        for(int i = 0; i < 5; i++) {
            computerDeck.addElement(deckOfCards.elementAt(0));
            deckOfCards.remove(0);
        }
        for(int i = 0; i < 4; i++) {
            layout.addElement(deckOfCards.elementAt(0));
            deckOfCards.remove(0);
        }

        d.setDeck(deckOfCards);

    }

    /**
     * Determines player based off hand or score
     */
    public String determineRound() {

        int computerHand = 0;
        int humanHand = 0;
        int highestCard = 12;

        // If the game started round based on who has better hand
        // Or if the score is tied
        if ((round == 1) || (computer.getScore() == human.getScore())) {

            do {
                for (int i = 0; i < computerDeck.size(); i++) {

                    /* 12 == King , 11 == Queen...*/
                    /*Model.Computer card matches highest card */
                    if (computerDeck.elementAt(i).getFace() == highestCard) {

                        /*Model.Human and computer card match highest card */
                        if (humanDeck.elementAt(i).getFace() == highestCard) {

                            computerHand++;
                            humanHand++;
                        }
                        else {

                            computerHand++;
                        }
                    }
                    /* Model.Human card matches highest card*/
                    else if (humanDeck.elementAt(i).getFace() == highestCard) {

                        humanHand++;
                    }
                }

                /* All cards match game must start over*/
                if (highestCard == 0) {
                    break;
                }
                highestCard--;

                /* While highest cards equal in both deck or cards completely match */
            } while (computerHand == humanHand);


            if (highestCard == 0) {

                new Round();
                //Restart class

            }

            else if (computerHand > humanHand) {

                computer.setTurn(true);
                human.setTurn(false);
                return "Computer has the better hand and will start";

            }

            else if (humanHand > computerHand) {

                computer.setTurn(false);
                human.setTurn(true);
                return "Human has the better hand and will start";

            }
        }

        /* Higher Score goes first */
        else if ((round > 1) && (computer.getScore() != human.getScore())) {

            if (computer.getScore() > human.getScore()) {
                computer.setTurn(true);
                human.setTurn(false);
                return "Computer has the better score and will start";


            }
            else {

                computer.setTurn(false);
                human.setTurn(true);

            }
        }
        return "Human has the better score and will start";

    }

    /**
     * Display cards
     */
    public void display() {

        System.out.println("-----------------------------");
        System.out.println("Model.Round: " + round + "\n");

        System.out.println("Model.Computer: ");
        System.out.println("   Score: " + computer.getScore());
        System.out.print("   Hand: ");
        d.printVector(computerDeck);
        System.out.print("\n   Capture Pile: ");
        d.printVector(computerCapture);
        System.out.println("\n");

        System.out.println("Model.Human: ");
        System.out.println("   Score: " + human.getScore());
        System.out.print("   Hand: ");
        d.printVector(humanDeck);
        System.out.print("\n   Capture Pile: ");
        d.printVector(humanCapture);
        System.out.println("\n");


        System.out.print("Layout: ");
        d.printVector(layout);
        System.out.println("\n");

        System.out.print("Stock Pile: ");
        d.printVector(deckOfCards);
        System.out.println("\n");

        System.out.print("Next Model.Player: ");

        if (computer.getIsTurn() == false) {

            System.out.println("Model.Computer!\n");
            System.out.println("-----------------------------\n");
//            menu();
        }

        else {

            System.out.println("Model.Human!\n");
            System.out.println("-----------------------------\n");
//            menu();
        }

    }


    /**
     * If current player has no cards it switches players
     * @return string
     */
    public String checkHand() {

        if (computer.getIsTurn() && computerDeck.size() == 0) {
            switchPlayers();
            return "No cards, switching players";

        }

        else if (human.getIsTurn() && humanDeck.size() == 0) {
            switchPlayers();
            return "No cards, switching players";

        }

        return "";

    }

    /**
     * Swap current player
     */
    public void switchPlayers() {

        computer.setTurn(!computer.getIsTurn());
        human.setTurn(!human.getIsTurn());
    }

    /**
     * Move for computer
     * @return String explaining computer move
     */
    public String moveComputer() {

        computer.setComputerDeck(computerDeck);
        computer.setLayoutDeck(layout);
        computer.setComputerCaptureDeck(computerCapture);
        computer.setHumanCaptureDeck(humanCapture);
        computer.setStockPile(deckOfCards);
        switchPlayers();

        return computer.play();


    }

    /**
     * move for human
     */
    public void moveHuman(String cardPicked) {

        human.setLayoutDeck(layout);
        human.setHumanDeck(humanDeck);
        human.setHumanCaptureDeck(humanCapture);
        human.setStockPile(deckOfCards);
        switchPlayers();
        human.play(cardPicked);

    }

    /**
     * Ends the game
     */
    public void endGame() {

        if (computer.getScore() > human.getScore()) {

            System.out.println("Sorry computer won :( better luck next time!");

        }

        else if (computer.getScore() == human.getScore()) {

            System.out.println("Tie Model.Game!");
        }

        else {

            System.out.println("Congrats you won!");
        }

        System.exit(0);
    }

    /**
     * saves the game
     * @param fileName
     * @return String
     */
    public String saveGame(String fileName) {
        int computerScore = computer.getScore();
        int humanScore = human.getScore();
        boolean isComputerTurn = computer.getIsTurn();

        return s.saveGame(fileName, round, computerScore, computerDeck, computerCapture, humanScore, humanDeck,
                humanCapture, layout, deckOfCards, isComputerTurn);
    }

    /**
     * Gets help for human
     * @return String
     */
    public String help() {

        return player.play(deckOfCards, layout, humanDeck, humanCapture, computerCapture);
    }

    /**
     * Clears decks
     *
     */

    public void clearDecks() {
        humanDeck.clear();
        computerDeck.clear();
        layout.clear();
        deckOfCards.clear();
        humanCapture.clear();
        computerCapture.clear();
        round++;
    }

}
