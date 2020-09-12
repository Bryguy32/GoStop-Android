package Model;

import java.util.Vector;

public class Player {

    private int score = 0;
    private int tripCapPos[] = {0, 0, 0};
    private int cardPosition = 0;
    private boolean isTurn = false;

    private Vector<Card> layout = new Vector<Card>();
    private Vector<Card> deckOfCards = new Vector<Card>();


    /**
     * Constructor
     */
    public Player() {

    }

    /**
     * sets the turn
     * @param boolean turn
     */
    public void setTurn(boolean turn) {

        this.isTurn = turn;
    }

    /**
     * gets the turn
     * @return boolean
     */
    public boolean getIsTurn() {

        return this.isTurn;
    }

    /**
     * sets the score
     * @param int score
     */
    public void setScore(int score) {

        this.score = score;

    }

    /**
     * gets the score
     * @return int
     */
    public int getScore() {

        return this.score;

    }

    /**
     * gets the triple cap positions
     * @return int[]
     */
    public int[] getTripCapPos() {

        return this.tripCapPos;
    }


    /**
     * gets the cards position
     * @return int
     */
    public int getCardPosition() {

        return this.cardPosition;
    }


    /**
     * sets the layout deck
     * @param Vector<Model.Card> layout
     */
    public void setLayoutDeck(Vector<Card> layout) {

        this.layout = layout;

    }

    /**
     * gets the layout deck
     * @return Vector<Model.Card>
     */
    public Vector<Card> getLayoutDeck() {

        return this.layout;
    }

    /**
     * gets the stock pile
     * @return Vector<Model.Card>
     */
    public Vector<Card> getStockPile() {

        return this.deckOfCards;
    }

    /**
     * sets the stock pile
     * @param Vector<Model.Card> deckOfCards
     */
    public void setStockPile(Vector<Card> deckOfCards) {

        this.deckOfCards = deckOfCards;
    }

    /**
     * Checks to see if card in layout is apart of a stack pair
     * @param Vector<Model.Card> layout
     * @param int cardPosition
     * @return boolean
     */
    public boolean isStackPair(Vector<Card> layout, int cardPosition) {

        if(cardPosition == 0) {
            return false;
        }

        else if (cardPosition + 1 >= layout.size()) {
            return false;
        }

        else if(layout.elementAt(cardPosition - 1).cardMatcher().toString().trim().equalsIgnoreCase("[")) {
            return true;
        }

        else if(layout.elementAt(cardPosition + 1).cardMatcher().toString().trim().equalsIgnoreCase("]")) {
            return true;
        }

        /* Check to make sure not triple cap as well */
        else if (layout.elementAt(cardPosition - 1).getFace() == layout.elementAt(cardPosition).getFace() &&
                layout.elementAt(cardPosition + 1).getFace() == layout.elementAt(cardPosition).getFace()) {

            /* make sure cardPosition is not out of range */
            if (cardPosition - 2 >= 0) {

                if (layout.elementAt(cardPosition - 2).cardMatcher().toString().trim().equalsIgnoreCase("[")) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Looks if first card in stockPile will match anything in layout to create a stack pair
     * @param Vector<Model.Card> layout
     * @param Vector<Model.Card> stockPile
     * @return boolean
     */
    public boolean stackPairMatchesStockPile(Vector<Card> layout, Vector<Card> stockPile) {

        for (int i = 0; i < layout.size(); i++) {

            if (layout.elementAt(i).getFace() == stockPile.elementAt(0).getFace()) {

                return true;
            }
        }


        return false;
    }

    /**
     * Checks for tripleCap, if true copies tripCap positions into array
     * @param Vector<Model.Card> layout
     * @param Vector<Model.Card> deck
     * @param int choosenCard
     * @return boolean
     */
    public boolean tripleCapPositions(Vector<Card> layout, Vector<Card> deck, int choosenCard) {

        for (int i = 0; i < layout.size(); i++) {
            int j = i + 1;
            int k = j + 1;

            if (k + 1 < layout.size() && (i - 1) >= 0) {

                if (layout.elementAt(i-1).cardMatcher().toString().trim().equalsIgnoreCase("[") &&
                        layout.elementAt(k + 1).cardMatcher().toString().trim().equalsIgnoreCase("]")) {

                    if (layout.elementAt(i).getFace() == layout.elementAt(j).getFace() && layout.elementAt(i).getFace()
                            == layout.elementAt(k).getFace()) {

                        /* Checks if card in deck matches triple cap card*/
                        if (deck.elementAt(choosenCard).getFace() == layout.elementAt(i).getFace()) {

                            tripCapPos[0] = i;
                            tripCapPos[1] = j;
                            tripCapPos[2] = k;
                            return true;
                        }

                    }
                }
            }

        }
        return false;

    }

    /**
     * Checks if tripleCap exists in layout
     * @param Vector<Model.Card> layout
     * @param Vector<Model.Card> deck
     * @return boolean
     */
    public boolean tripleCap(Vector<Card> layout, Vector<Card> deck) {

        int count = 0;

        // check to see if there are three matching cards
        for (int i = 0; i < layout.size(); i++) {
            for (int j = i + 1; j < layout.size(); j++) {

                if (layout.elementAt(i).getFace() == layout.elementAt(j).getFace()) {

                    count++;
                }

                if (count == 2) {

                    // Check if humanDeck has matching card
                    for (int k = 0; k < deck.size(); k++) {

                        if (layout.elementAt(i).getFace() == deck.elementAt(k).getFace()) {

                            cardPosition = k;
                            //	setCardPosition(k);

                            return true;
                        }
                    }
                }
            }
            count = 0;
        }
        return false;
    }

    /**
     * Looks for a stackPair to create to match with capture pile, else creates a stack pair
     * @param Vector<Model.Card>layout
     * @param Vector<Model.Card> deck
     * @param Vector<Model.Card> capturePile
     * @return boolean
     */
    public boolean stackPair(Vector<Card> layout, Vector<Card> deck, Vector<Card> capturePile) {

        /** Check to see if stackPair already captured that can be matched
         else return stack pair */

        int count = 0;
        cardPosition = 0;

        for (int i = 0; i < layout.size(); i++) {
            for (int j = 0; j < deck.size(); j++) {

                if (layout.elementAt(i).getFace() == deck.elementAt(j).getFace()) {

                    cardPosition = j;

                    //If stack pair check if face is in capture pile to add point
                    for (int k = 0; k < capturePile.size(); k++) {

                        if (deck.elementAt(j).getFace() == capturePile.elementAt(k).getFace()) {

                            count++;
                        }
                    }

					/* If stack pair will add point use, otherwise search
					for another card that will */
                    if (count == 2 || count == 6) {

                        return true;
                    }
                }

                count = 0;

            }
        }

        if (cardPosition != 0) {

            return true;
        }

        return false;

    }

    /**
     * Looks to create stack pair to block enemy from getting a point
     * @param Vector<Model.Card> layout
     * @param Vector<Model.Card> deck
     * @param Vector<Model.Card> enemyCapturePile
     * @return boolean
     */
    public boolean block(Vector<Card> layout, Vector<Card> deck, Vector<Card> enemyCapturePile) {

        int count = 0;
        int blockCard = 0;

        for (int i = 0; i < enemyCapturePile.size(); i++) {
            for (int j = i + 1; j < enemyCapturePile.size(); j++) {

                if (enemyCapturePile.elementAt(i).getFace() == enemyCapturePile.elementAt(j).getFace()) {

                    count++;
                }
            }

            if (count == 2 || count == 6) {

                blockCard = i;
                break;

            }
            count = 0;
        }

        if (count == 2 || count == 6) {

            for (int i = 0; i < layout.size(); i++) {
                for (int j = 0; j < deck.size(); j++) {

                    if ((layout.elementAt(i).getFace() == enemyCapturePile.elementAt(blockCard).getFace()) &&
                            (deck.elementAt(j).getFace() == enemyCapturePile.elementAt(blockCard).getFace())) {

                        cardPosition = j;
                        return true;
                    }
                }
            }
        }


        return false;
    }

    /**
     * Looks to create stack pair with stock pile card
     * @param Vector<Model.Card> deck
     * @param Vector<Model.Card> stockPile
     * @return boolean
     */
    public boolean pairFromStockPile(Vector<Card> deck, Vector<Card> stockPile) {

        for (int i = 0; i < deck.size(); i++) {

            if (stockPile.firstElement().getFace() == deck.elementAt(i).getFace()) {

                cardPosition = i;
                return true;
            }
        }

        return false;
    }

    /**
     * play function which is get help for human
     * @param Vector<Model.Card> stockPile
     * @param Vector<Model.Card> layout
     * @param Vector<Model.Card> humanDeck
     * @param Vector<Model.Card> humanCapture
     * @param Vector<Model.Card> computerCapture
     */
    public String play(Vector<Card> stockPile, Vector<Card> layout, Vector<Card> humanDeck, Vector<Card> humanCapture, Vector<Card> computerCapture) {


        if (tripleCap(layout, humanDeck) == true) {

            return ("I recommend you play: "
                    + humanDeck.elementAt(cardPosition).cardMatcher() + " to capture all four cards");

        }

        else if (stackPair(layout, humanDeck, humanCapture) == true) {

            return ("I recommend you play: "
                    + humanDeck.elementAt(cardPosition).cardMatcher() + " to create a stack pair");

        }

        else if (pairFromStockPile(humanDeck, stockPile) == true) {

            return ("I recommend you play: "
                    + humanDeck.elementAt(cardPosition).cardMatcher() + " to create a stack pair with the stock pile");

        }

        else if (block(layout, humanDeck, computerCapture) == true) {

            return ("I recommend you play: "
                    + humanDeck.elementAt(cardPosition).cardMatcher() + " to block the computer from capturing the cards it needs");

        }

        else {
           return ("I don't have recommendations sorry");

        }

    }





}
