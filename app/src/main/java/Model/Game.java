package Model;

import java.util.Vector;

public class Game {

    private Round round = new Round();
    private Serialization s = new Serialization();

    private Vector<Card> v = new Vector<Card>();

    /**
     * Constructor
     */
    public Game() {

    }

    /**
     * Starts game
     * @return round
     */
    public Round startGame() {

        round.setUpRound();
        round.determineRound();
        return round;
    }

    /**
     * Loads game from file
     * @return round
     */
    public Round loadGame(String fileName) {

        s.loadGame(fileName);

        // Round number
        int roundNum = s.getRound();
        round.setRound(roundNum);


        // Computer
        int cScore = s.getComputerScore();
        round.setComputerScore(cScore);

        v = s.getComputerDeck();
        round.setComputerDeck(v);

        v = s.getComputerCaptureDeck();
        round.setComputerCaptureDeck(v);


        // Human
        int hScore = s.getHumanScore();
        round.setHumanScore(hScore);

        v = s.getHumanDeck();
        round.setHumanDeck(v);

        v = s.gethumanCaptureDeck();
        round.sethumanCaptureDeck(v);

        //Layout
        v = s.getLayoutDeck();
        round.setLayoutDeck(v);


        //Stock Pile
        v = s.getDeck();
        round.setDeck(v);

        //Next player
        boolean isTurn = s.isComputerTurn();
        round.currentPlayer(isTurn);

        return round;


    }
}
