package Model; /*************************************************************
 * Name:  Bryan Francis
 * Project : GoStop Java
 * Class : OPL Spring 2020
 * Date : 4/28/2020
 *************************************************************/

import java.util.Vector;

public class Computer extends Player {

    private Vector<Card> deckOfCards = new Vector<Card>();
    private Vector<Card> layout = new Vector<Card>();
    private Vector<Card> computerDeck = new Vector<Card>();
    private Vector<Card> computerCapture = new Vector<Card>();
    private Vector<Card> humanCapture = new Vector<Card>();

    /**
     * Constructor
     */
    public Computer() {

    }

    /**
     * Sets the computer deck
     * @param Vector<Model.Card> computerDeck
     */
    public void setComputerDeck(Vector<Card> computerDeck) {

        this.computerDeck = computerDeck;
    }

    /**
     * Sets the computer capture deck
     * @param Vector<Car> computerCapture
     */
    public void setComputerCaptureDeck(Vector<Card> computerCapture) {

        this.computerCapture = computerCapture;
    }


    /**
     * gets the human capture deck
     * @return Vector<Model.Card>
     */
    public Vector<Card> getHumanCaptureDeck() {

        return this.humanCapture;
    }

    /**
     * sets the human capture deck
     * @param Vector<Model.Card> humanCaptureDeck
     */
    public void setHumanCaptureDeck(Vector<Card> humanCaptureDeck) {

        this.humanCapture = humanCaptureDeck;
    }

    /**
     * Makes the moves for the computer
     * @param none
     * @return String explaining computer move
     */
    public String play() {

        /** Get all the decks */
        deckOfCards = getStockPile();
        layout = getLayoutDeck();
        humanCapture = getHumanCaptureDeck();
        String comExplanation = " ";

        /** Computer AI */
        int cardPosition = getCardPosition();

        if (tripleCap(layout, computerDeck) == true) {

            cardPosition = getCardPosition();
            comExplanation = "The computer chose to play: "
                    + computerDeck.elementAt(cardPosition).cardMatcher() + " to capture all four cards.";
        }

        else if (stackPair(layout, computerDeck, computerCapture) == true) {

            cardPosition = getCardPosition();
            comExplanation = "The computer chose to play: "
                    + computerDeck.elementAt(cardPosition).cardMatcher() + " to create a stack pair.";
        }

        else if (block(layout, computerDeck, humanCapture) == true) {

            cardPosition = getCardPosition();
            comExplanation = "The computer chose to play: "
                    + computerDeck.elementAt(cardPosition).cardMatcher() + " to block you from capturing the cards you need.";
        }

        else if (pairFromStockPile(computerDeck, deckOfCards) == true) {

            cardPosition = getCardPosition();
            comExplanation = "The computer chose to play: "
                    + computerDeck.elementAt(cardPosition).cardMatcher() + " to create a stack pair with the stock pile.";
        }

        else {

            cardPosition = 0;
            comExplanation = "The computer chose to play: "
                    + computerDeck.elementAt(cardPosition).cardMatcher() + ".";

        }

        String playCard = computerDeck.elementAt(cardPosition).cardMatcher();

        int cardMatches = 0; // How many times the face of the card the user picked is in the stock pile
        int choosenCard = 0; // The location in the vector of the  card the user picked
        int layoutMatch1 = 0; // The first matching face card of the user pick in the layout
        int layoutMatch2 = 0; // The second match
        int layoutMatch3 = 0; // The third match
        boolean isCard = false; // count as a card not from stackPair
        int score = getScore();


        /** Model.Card
         * Played
         * From
         * Hand
         */


        /* Plays a card from hand */
        for (int i = 0; i < computerDeck.size(); i++) {

            if(computerDeck.get(i).cardMatcher().toString().trim().equalsIgnoreCase(playCard)) {

                choosenCard = i;

                for (int j = 0; j < layout.size(); j++) {

                    if (computerDeck.elementAt(i).getFace() == layout.elementAt(j).getFace()) {

                        // Check to make sure the card that matched is not a stack pair
                        if (isStackPair(layout, j) == false) {

                            cardMatches++;
                            isCard = true;

                        }

                        else if (isStackPair(layout, j) == true) {

                            isCard = false;
                        }

                        if (cardMatches == 1 && isCard == true) {

                            layoutMatch1 = j;

                        }

                        else if (cardMatches == 2 && isCard == true) {

                            layoutMatch2 = j;
                        }

                        else if (cardMatches == 3 && isCard == true) {

                            layoutMatch3 = j;
                        }



                    }
                }

                break;
            }
        }


        /** The card is added to layout */
        if (cardMatches == 0 &&
                tripleCapPositions(layout, computerDeck, choosenCard) == false) {

            layout.addElement(computerDeck.elementAt(choosenCard));
            computerDeck.remove(choosenCard);

        }

        /** Creates stack pair which is left in layout */
        else if (cardMatches == 1) {

            // add cards to layout
            layout.addElement(new Card(13, 4));
            layout.addElement(computerDeck.elementAt(choosenCard));
            layout.addElement(layout.elementAt(layoutMatch1));
            layout.addElement(new Card(14, 4));

            computerDeck.remove(choosenCard);
            layout.remove(layoutMatch1);

        }

        /** User picks one of the two to create stack pair */
        else if (cardMatches == 2) {

           // System.out.print("Model.Card to make a stack pair with: ");
           // layout.elementAt(layoutMatch1).print();
           // layout.elementAt(layoutMatch2).print();

            String stackCard = layout.elementAt(layoutMatch1).cardMatcher();
           // System.out.println("\nThe computer chose to match with: " + stackCard);

            layout.addElement(new Card(13, 4));
            layout.addElement(computerDeck.elementAt(choosenCard));
            layout.addElement(layout.elementAt(layoutMatch1));
            layout.addElement(new Card(14, 4));


            computerDeck.remove(choosenCard);
            layout.remove(layoutMatch1);
        }

        /** Three cards in layout or triple cap */
        else if (cardMatches == 3 ||
                tripleCapPositions(layout, computerDeck, choosenCard) == true) {

            /** three cards in layout */
            if (cardMatches == 3) {

                computerCapture.addElement(computerDeck.elementAt(choosenCard));
                computerCapture.addElement(layout.elementAt(layoutMatch1));
                computerCapture.addElement(layout.elementAt(layoutMatch2));
                computerCapture.addElement(layout.elementAt(layoutMatch3));

                computerDeck.remove(choosenCard);
                layout.remove(layoutMatch3);
                layout.remove(layoutMatch2);
                layout.remove(layoutMatch1);
            }

            /** Three cards are in stack pair */
            else {

                int capPos[] = getTripCapPos();
                layoutMatch1 = capPos[0];
                layoutMatch2 = capPos[1];
                layoutMatch3 = capPos[2];

                computerCapture.addElement(computerDeck.elementAt(choosenCard));
                computerCapture.addElement(layout.elementAt(layoutMatch1));
                computerCapture.addElement(layout.elementAt(layoutMatch2));
                computerCapture.addElement(layout.elementAt(layoutMatch3));

                computerDeck.remove(choosenCard);

                /** Remove triple stack and "[" "]" from layout */
                layout.remove(layoutMatch1 - 1);
                layout.remove(layoutMatch1 - 1);
                layout.remove(layoutMatch1 - 1);
                layout.remove(layoutMatch1 - 1);
                layout.remove(layoutMatch1 - 1);

            }

            score++;
        }

        /** Model.Card
         * Played
         * From
         * Stock
         * Pile
         */


        int layoutCardMatches = 0; // How many times the face of the card in the stock pile is in the layout
        layoutMatch1 = 0; // The first matching face card in the layout
        layoutMatch2 = 0; // The second match
        layoutMatch3 = 0; // The third match
        isCard = false; // count as card

        for(int i = 0; i < layout.size(); i++) {

            if (deckOfCards.firstElement().getFace() == layout.elementAt(i).getFace()) {

                if (isStackPair(layout, i) == false) {

                    layoutCardMatches++;
                    isCard = true;


                }

                else if (isStackPair(layout, i) == true) {

                    isCard = false;

                }

                if (layoutCardMatches == 1 && isCard == true) {

                    layoutMatch1 = i;

                }

                else if (layoutCardMatches == 2 && isCard == true) {

                    layoutMatch2 = i;
                }

                else if (layoutCardMatches == 3 && isCard == true) {

                    layoutMatch3 = i;
                }

            } // End if

        } // end for

        // If stock Pile card doesn't match any card in layout



        if (cardMatches == 0 || cardMatches == 3) {

            if(layoutCardMatches == 0 &&
                    tripleCapPositions(layout, deckOfCards, 0) == false) {
                comExplanation += " The stock pile card: " + deckOfCards.firstElement().cardMatcher() +
                        " got added to the layout";

                layout.addElement(deckOfCards.firstElement());
                deckOfCards.remove(0);


            }

            else if (layoutCardMatches == 1) {
                comExplanation += " The stock pile card: " + deckOfCards.firstElement().cardMatcher() +
                        " matched with: " + layout.elementAt(layoutMatch1).cardMatcher();

                computerCapture.addElement(deckOfCards.firstElement());
                computerCapture.addElement(layout.elementAt(layoutMatch1));

                //Score
                int count = 0;
                for(int i = 0; i < computerCapture.size(); i++) {

                    if(deckOfCards.firstElement().getFace() == computerCapture.elementAt(i).getFace()) {

                        count++;

                        // Possible to have all 8 cards
                        if( count == 4 || count == 8) {

                            score++;
                        }

                    }

                }

                deckOfCards.remove(0);
                layout.remove(layoutMatch1);


            }

            else if(layoutCardMatches == 2) {
                comExplanation += " The stock pile card: " + deckOfCards.firstElement().cardMatcher() +
                        " matched with: " + layout.elementAt(layoutMatch1).cardMatcher();

               // System.out.print("Model.Card to make a stack pair with: ");
               // layout.elementAt(layoutMatch1).print();
               // layout.elementAt(layoutMatch2).print();

                String stackCard = layout.elementAt(layoutMatch1).cardMatcher();
               //
                // System.out.println("The computer chose to match with: " + stackCard);

                computerCapture.addElement(deckOfCards.firstElement());
                computerCapture.addElement(layout.elementAt(layoutMatch1));

                // Score
                int count = 0;
                for(int i = 0; i < computerCapture.size(); i++) {

                    if(deckOfCards.firstElement().getFace() == computerCapture.elementAt(i).getFace()) {

                        count++;

                        // Possible to have all 8 cards
                        if( count == 4 || count == 8) {

                            score++;
                        }

                    }

                }

                deckOfCards.remove(0);
                layout.remove(layoutMatch1);

            }

            else if (layoutCardMatches == 3 ||
                    tripleCapPositions(layout, deckOfCards, 0) == true) {
                comExplanation += " The stock pile card: " + deckOfCards.firstElement().cardMatcher() +
                        " captured all four cards";

                /** three cards in layout */
                if (layoutCardMatches == 3) {

                    computerCapture.addElement(deckOfCards.firstElement());
                    computerCapture.addElement(layout.elementAt(layoutMatch1));
                    computerCapture.addElement(layout.elementAt(layoutMatch2));
                    computerCapture.addElement(layout.elementAt(layoutMatch3));

                    deckOfCards.remove(0);
                    layout.remove(layoutMatch3);
                    layout.remove(layoutMatch2);
                    layout.remove(layoutMatch1);
                }

                /** Three cards are in stack pair */
                else {

                    int capPos[] = getTripCapPos();
                    layoutMatch1 = capPos[0];
                    layoutMatch2 = capPos[1];
                    layoutMatch3 = capPos[2];

                    computerCapture.addElement(deckOfCards.firstElement());
                    computerCapture.addElement(layout.elementAt(layoutMatch1));
                    computerCapture.addElement(layout.elementAt(layoutMatch2));
                    computerCapture.addElement(layout.elementAt(layoutMatch3));

                    deckOfCards.remove(0);

                    /** Remove triple stack and "[" "]" from layout */
                    layout.remove(layoutMatch1 - 1);
                    layout.remove(layoutMatch1 - 1);
                    layout.remove(layoutMatch1 - 1);
                    layout.remove(layoutMatch1 - 1);
                    layout.remove(layoutMatch1 - 1);

                }
                score++;

            }

        } // end if Model.Card Matches == 0 || 3


        else if (cardMatches == 1 || cardMatches == 2) {


            /** Doesn't match layout cards or stacked pair or tripleStack */
            if (layoutCardMatches == 0 &&
                    stackPairMatchesStockPile(layout, deckOfCards) == false &&
                    tripleCapPositions(layout, deckOfCards, 0) == false) {
                comExplanation += " The stock pile card: " + deckOfCards.firstElement().cardMatcher() +
                        " ended up in the layout";

                int count =  layout.size();

                /** start end of vectors because the stack pair was just added
                 * to the end
                 */
                computerCapture.addElement(layout.elementAt(count - 2));
                computerCapture.addElement(layout.elementAt(count - 3));

                //score
                int scoreCount = 0;
                for(int i = 0; i < computerCapture.size(); i++) {

                    if(layout.elementAt(count - 2).getFace() == computerCapture.elementAt(i).getFace()) {

                        scoreCount++;
                        // Possible to have all 8 cards
                        if( scoreCount == 4 || scoreCount == 8) {

                            score++;
                        }

                    }

                }

                layout.remove(count - 1);
                layout.remove(count - 2);
                layout.remove(count - 3);
                layout.remove(count - 4);

                // Add card to layout
                layout.addElement(deckOfCards.firstElement());
                deckOfCards.remove(0);


            }

            else if (layoutCardMatches == 3 ||
                    tripleCapPositions(layout, deckOfCards, 0) == true) {
                comExplanation += " The stock pile card: " + deckOfCards.firstElement().cardMatcher() +
                        " matched with all 3 cards in layout";

                /** three cards in layout */
                if (layoutCardMatches == 3) {

                    computerCapture.addElement(deckOfCards.firstElement());
                    computerCapture.addElement(layout.elementAt(layoutMatch1));
                    computerCapture.addElement(layout.elementAt(layoutMatch2));
                    computerCapture.addElement(layout.elementAt(layoutMatch3));

                    deckOfCards.remove(0);
                    layout.remove(layoutMatch3);
                    layout.remove(layoutMatch2);
                    layout.remove(layoutMatch1);
                }

                /** Three cards are in triple cap */
                else {

                    int capPos[] = getTripCapPos();
                    layoutMatch1 = capPos[0];
                    layoutMatch2 = capPos[1];
                    layoutMatch3 = capPos[2];

                    computerCapture.addElement(deckOfCards.firstElement());
                    computerCapture.addElement(layout.elementAt(layoutMatch1));
                    computerCapture.addElement(layout.elementAt(layoutMatch2));
                    computerCapture.addElement(layout.elementAt(layoutMatch3));

                    deckOfCards.remove(0);

                    /** Remove triple stack and "[" "]" from layout */
                    layout.remove(layoutMatch1 - 1);
                    layout.remove(layoutMatch1 - 1);
                    layout.remove(layoutMatch1 - 1);
                    layout.remove(layoutMatch1 - 1);
                    layout.remove(layoutMatch1 - 1);

                }
                score++;


                /* Add stacked cards from H1/H2 */
                int count;
                count = layout.size();

                /** start end of vectors because the stack pair was just added
                 * to the end
                 */
                computerCapture.addElement(layout.elementAt(count - 2));
                computerCapture.addElement(layout.elementAt(count - 3));

                //score

                int scoreCount = 0;
                for (int i = 0; i < computerCapture.size(); i++) {

                    if (layout.elementAt(count - 2).getFace() == computerCapture.elementAt(i).getFace()) {

                        scoreCount++;
                        // Possible to have all 8 cards
                        if (scoreCount == 4 || scoreCount == 8) {

                            score++;
                        }

                    }

                }

                //Remove cards in layout that were moved to stack pair
                layout.remove(count - 1);
                layout.remove(count - 2);
                layout.remove(count - 3);
                layout.remove(count - 4);



            }

            else if (layoutCardMatches == 1 || layoutCardMatches == 2) {
                comExplanation += " The stock pile card: " + deckOfCards.firstElement().cardMatcher() +
                        " matched with: " + layout.elementAt(layoutMatch1).cardMatcher();


                /** Add cards */
                computerCapture.addElement(deckOfCards.firstElement());
                computerCapture.addElement(layout.elementAt(layoutMatch1));

                /** Add stacked Pair */
                int count =  layout.size();
                computerCapture.addElement(layout.elementAt(count - 2));
                computerCapture.addElement(layout.elementAt(count - 3));

                // Check for four matching cards from stock pile card
                int scoreCount = 0;
                for(int i = 0; i < computerCapture.size(); i++) {

                    if(deckOfCards.firstElement().getFace() == computerCapture.elementAt(i).getFace()) {

                        scoreCount++;
                        // Possible to have all 8 cards
                        if( scoreCount == 4 || scoreCount == 8) {

                            score++;
                        }

                    }
                }


                /* Check for four matching cards from stack pair
                *   only if stack pair and stock pile care are different */
                if (deckOfCards.firstElement().getFace() != layout.elementAt(count - 2).getFace()) {
                    scoreCount = 0;
                    for (int i = 0; i < humanCapture.size(); i++) {

                        if (layout.elementAt(count - 2).getFace() == humanCapture.elementAt(i).getFace()) {

                            scoreCount++;
                            // Possible to have all 8 cards
                            if (scoreCount == 4 || scoreCount == 8) {

                                score++;
                            }

                        }
                    }

                }



                // Erase Cards
                deckOfCards.remove(0);
                layout.remove(layoutMatch1);

                count =  layout.size();
                layout.remove(count - 1);
                layout.remove(count - 2);
                layout.remove(count - 3);
                layout.remove(count - 4);

            }

            /** No match in layout just in stack pair */
            // Works
            else if (layoutCardMatches == 0 &&
                    stackPairMatchesStockPile(layout, deckOfCards) == true) {
                comExplanation += " The stock pile card: " + deckOfCards.firstElement().cardMatcher() +
                        " matched with the stack pair to create a triple stack";

                // Create triple stack
                int count =  layout.size();

                // Add element to stack before bracket
                layout.add(count - 2, deckOfCards.firstElement());


                // Delete from stock pile
                deckOfCards.remove(0);


            }


        } // end else if Card Matches == 1 || 2
        setScore(score);

        return  comExplanation;

    }







}
