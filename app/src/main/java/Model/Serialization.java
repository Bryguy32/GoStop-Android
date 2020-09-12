package Model;

import android.os.Environment;
import android.view.View;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class Serialization {

    private Vector<Card> humanDeck = new Vector<Card>();;
    private Vector<Card> computerDeck = new Vector<Card>();
    private Vector<Card> layout = new Vector<Card>();
    private Vector<Card> deckOfCards = new Vector<Card>();;
    private Vector<Card> humanCapture = new Vector<Card>();
    private Vector<Card> computerCapture = new Vector<Card>();
    private int round = 0;
    private int hScore = 0;
    private int cScore = 0;
    private boolean isTurn;

    public Serialization() {

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
     * Gets computer score
     * @return int
     */
    public int getComputerScore() {

        return this.cScore;

    }

    /**
     * Gets human score
     * @return int
     */
    public int getHumanScore() {

        return this.hScore;

    }

    /**
     * Returns true if it is computer turn
     * @return boolean
     */
    public boolean isComputerTurn() {

        if(isTurn == true) {

            return true;
        }

        else {

            return false;
        }
    }

    /**
     * Load game from file
     * @param String filename
     *  Help from Amish Regmi
     */
    public void loadGame(String filename) {

        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/goStop-Files");
        File file = new File(dir, filename);

        if(!file.exists()) {

            System.out.println("File does not exist");
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;
            boolean works = false;

            while ((line = br.readLine()) != null) {

                String[] splitStr = line.split("\\s+");

                for(String oneword: splitStr) {

                    if(oneword.equals("Round:")) {

                        works = true;
                        continue;

                    }

                    if(works) {

                        round = Integer.parseInt(oneword);
                        works = false;

                    }

                    if (oneword.equals("Human:")) {


                        // Loop 3 times to get score, hand, and capture
                        int i = 0;
                        while (i < 3) {

                            line = br.readLine();
                            String[] tempSplit = line.split("\\s+");


                            for(String loopWord : tempSplit) {

                                // Score
                                if(loopWord.equals("Score:")) {

                                    works = true;

                                    continue;
                                }

                                if(works) {

                                    hScore = Integer.parseInt(loopWord);
                                    works = false;


                                }

                                // Hand
                                if(loopWord.equals("Hand:")) {

                                    String hand = line.substring(line.indexOf(":") + 1);
                                    humanDeck = extractCards(hand);

                                }

                                // Capture
                                if(loopWord.equals("Capture")) {

                                    String hand = line.substring(line.indexOf(":") + 1);
                                    humanCapture = extractCards(hand);


                                }

                            }
                            i++;
                        }
                    }

                    if (oneword.equals("Computer:")) {


                        // Loop 3 times to get score, hand, and capture
                        int i = 0;
                        while (i < 3) {

                            line = br.readLine();
                            String[] tempSplit = line.split("\\s+");


                            for(String loopWord : tempSplit) {

                                // Score
                                if(loopWord.equals("Score:")) {

                                    works = true;

                                    continue;
                                }

                                if(works) {

                                    cScore = Integer.parseInt(loopWord);
                                    works = false;


                                }

                                // Hand
                                if(loopWord.equals("Hand:")) {

                                    String hand = line.substring(line.indexOf(":") + 1);
                                    computerDeck = extractCards(hand);

                                }

                                // Capture
                                if(loopWord.equals("Capture")) {

                                    String hand = line.substring(line.indexOf(":") + 1);
                                    computerCapture = extractCards(hand);


                                }

                            }
                            i++;
                        }

                    }

                    else if(oneword.equals("Layout:")) {

                        String layoutCards = line.substring(line.indexOf(":") + 1);
                        layout = extractCards(layoutCards);

                    }

                    else if (oneword.equals("Stock")) {

                        String deck = line.substring(line.indexOf(":") + 1);
                        deckOfCards = extractCards(deck);


                    }

                    else if (oneword.equals("Next")) {

                        String nextPlayer = line.substring(line.indexOf(":") + 1);

                        if (nextPlayer.trim().equalsIgnoreCase("Human")) {

                            isTurn = true;

                        }

                        else if (nextPlayer.trim().equalsIgnoreCase("Computer")) {

                            isTurn = false;

                        }

                    }


                }


            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("File loaded successfully!\n");
    }

    /**
     * Converts String to Vector<Model.Card>
     * @param String hand
     * @return Vector<Model.Card>
     */
    public Vector<Card> extractCards(String hand) {

        Vector<Card> cards = new Vector<Card>();
        String[] str = hand.split(" ");
        for (String eachCard: str) {
            if (eachCard.length() != 0) {
                cards.add(new Card(eachCard));
            }
        }

        return cards;
    }

    /**
     * Saves the game
     * @param int round
     * @param int computerScore
     * @param Vector<Model.Card> computerDeck
     * @param Vector<Model.Card> computerCapture
     * @param int humanScore
     * @param Vector<Model.Card> humanDeck
     * @param Vector<Model.Card> humanCapture
     * @param Vector<Model.Card> layout
     * @param Vector<Model.Card> stockPile
     * @param boolean isComputerTurn
     * @return String
     */
    public String saveGame(String fileName, int round, int computerScore, Vector<Card> computerDeck, Vector<Card> computerCapture,
                         int humanScore, Vector<Card> humanDeck, Vector<Card> humanCapture, Vector<Card> layout,
                         Vector<Card> stockPile, boolean isComputerTurn) {



        /* Location of files  */
        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/goStop-Files");
        dir.mkdir(); // Makes directory if one doesn't exist
        File file = new File(dir, fileName);

        try {


            FileWriter myWriter = new FileWriter(file);

            myWriter.write("Round: " + round + "\n");
            myWriter.write("\n");

            myWriter.write("Computer: \n");
            myWriter.write("   Score: " + computerScore);
            myWriter.write("\n");
            myWriter.write("   Hand: ");
            for(int i = 0; i < computerDeck.size(); i++) {

                myWriter.write(computerDeck.elementAt(i).cardMatcher().toString() + " ");
            }
            myWriter.write("\n   Capture Pile: ");
            for(int i = 0; i < computerCapture.size(); i++) {

                myWriter.write(computerCapture.elementAt(i).cardMatcher().toString() + " ");
            }
            myWriter.write("\n");
            myWriter.write("\n");


            myWriter.write("Human: \n");
            myWriter.write("   Score: " + humanScore);
            myWriter.write("\n");
            myWriter.write("   Hand: ");
            for(int i = 0; i < humanDeck.size(); i++) {

                myWriter.write(humanDeck.elementAt(i).cardMatcher().toString() + " ");
            }
            myWriter.write("\n   Capture Pile: ");
            for(int i = 0; i < humanCapture.size(); i++) {

                myWriter.write(humanCapture.elementAt(i).cardMatcher().toString() + " ");
            }
            myWriter.write("\n");
            myWriter.write("\n");


            myWriter.write("Layout: ");
            for(int i = 0; i < layout.size(); i++) {

                myWriter.write(layout.elementAt(i).cardMatcher().toString() + " ");
            }
            myWriter.write("\n");
            myWriter.write("\n");


            myWriter.write("Stock Pile: ");
            for(int i = 0; i < stockPile.size(); i++) {

                myWriter.write(stockPile.elementAt(i).cardMatcher().toString() + " ");
            }
            myWriter.write("\n");
            myWriter.write("\n");



            myWriter.write("Next Player: ");

            if (isComputerTurn == true) {

                myWriter.write("Computer\n");

            }

            else {

                myWriter.write("Human\n");

            }



            myWriter.close();
            return "Successfully wrote to the file!";




        }catch (IOException e) {

            e.printStackTrace();
            return "An error occurred";

        }
    }

}
