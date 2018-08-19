package student;

import model.Card;
import model.Deck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class cardDeck implements Deck {
    //the deck array
    private ArrayList<Card> deck;
    //the default deck array
    private ArrayList<Card> defaultdeck;
    //Arraylist of card types
    private ArrayList<Card> cardtypes = new ArrayList<Card>(Arrays.asList(
            Card.BLACK, Card.BLUE, Card.GREEN, Card.ORANGE,
            Card.RED, Card.PINK, Card.WHITE, Card.WILD, Card.YELLOW));


    /**
     * A object representing the deck of cards.
     * Stores a Default deck containing 20 of every playable Card type
     * Stores and shuffles the actual deck of cards.
     */
    public cardDeck(){
        this.defaultdeck = Createdeck();
        this.deck = Createdeck();
        Collections.shuffle(this.deck);
    }

    /**
     * Get Deck command for testing purposes
     * @return the deck
     */
    public ArrayList<Card> getDeck() {
        return deck;
    }

    public ArrayList<Card> getDefaultdeck() {
        return defaultdeck;
    }

    /**
     * Creates a new arraylist of cards with 20 of every type of playable card
     * @return the newly created deck of cards
     */
    public ArrayList<Card> Createdeck(){
        ArrayList<Card> newdeck = new ArrayList<Card>();
        for(Card cardtype:cardtypes){
            for(int q = 0; q<20; q++){
                newdeck.add(cardtype);
            }
        }
        return newdeck;
    }
    /**
     * Resets the {@linkplain Deck deck} to its starting state. Restores any
     * {@linkplain Card cards} that were drawn and shuffles the deck.
     */
    @Override
    public void reset() {
        this.deck = this.defaultdeck;
        Collections.shuffle(this.deck);
    }

    /**
     * Draws the next {@linkplain Card card} from the "top" of the deck.
     *
     * @return The next {@link Card}, unless the deck is empty, in which case
     * this should return {@link Card#NONE}.
     */
    @Override
    public Card drawACard() {
        if(this.deck.isEmpty()){
            return Card.NONE;
        }
        else {
            return this.deck.remove(0);
        }
    }

    /**
     * Returns the number of {@link Card cards} that have yet to be drawn.
     *
     * @return The number of {@link Card cards} that have yet to be drawn.
     */
    @Override
    public int numberOfCardsRemaining() {
        return this.deck.size();
    }
}
