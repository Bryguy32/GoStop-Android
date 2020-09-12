package Model;

/*************************************************************
 * Name:  Bryan Francis
 * Project : GoStop Java
 * Class : OPL Spring 2020
 * Date : 4/28/2020
 *************************************************************/

public class Card {

    private final String faceNames[] = { "1","2","3","4","5","6","7","8","9","X","J","Q","K", "[", "]" };
    private final String suitNames[] = { "H", "C", "D", "S", "" };
    private int face, suit;

    /**
     * Constructor
     * @param int face
     * @param int suit
     */
    Card(int face, int suit) {

        this.face = face;
        this.suit = suit;

    }

    /**
     * Constructor for Model.Serialization
     * @param String s
     */
    Card(String s) {

        for(int i = 0; i < 13; i++) {

            for (int j = 0; j < 4; j++) {

                if ((faceNames[i] + suitNames[j]).toString().trim().equalsIgnoreCase(s)) {

                    setFace(i);
                    setSuit(j);
                }
            }
        }

    }

    /**
     * Get the face of the card
     * @return int
     */
    public int getFace() {

        return this.face;
    }

    /**
     * Get the suit of the card
     * @return int
     */
    public int getSuit() {

        return this.suit;
    }

    /**
     * Sets the face
     * @param int face
     */
    public void setFace(int face) {

        this.face = face;

    }

    /**
     * Sets the suit
     * @param int suit
     */
    public void setSuit(int suit) {

        this.suit = suit;
    }

    /**
     * prints out the card face and suit
     * @param none
     */
    public void print() {

        System.out.print((faceNames[face] + suitNames[suit] + " "));
    }

    /**
     * returns a string of the card face and suit
     * @param none
     * @return String
     */
    public String cardMatcher() {

        return faceNames[face] + suitNames[suit];

    }

}
