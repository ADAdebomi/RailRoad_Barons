package student;

import model.Card;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PairTest {

    private Pair testpair;
    cardDeck Deck;
    cardDeck TestDeck;

    @org.junit.Before
    public void setup(){
        Deck = new cardDeck();
        TestDeck = Deck;
        System.out.println(Deck.getDeck());
        System.out.println(TestDeck.getDeck());
        testpair = new Pair(Deck);
    }

    @Test
    public void getFirstCard() {
        assertEquals(TestDeck.drawACard(), testpair.getFirstCard());
    }

    @Test
    public void getSecondCard() {
        assertEquals(TestDeck.drawACard(), testpair.getSecondCard());
    }
}