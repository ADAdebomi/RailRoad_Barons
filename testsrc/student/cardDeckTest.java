package student;

import org.junit.Test;

import static org.junit.Assert.*;

public class cardDeckTest {

    private cardDeck TestDeck;

    @org.junit.Before
    public void setup(){
        TestDeck = new cardDeck();
    }

    @Test
    public void reset() {
        assertTrue(TestDeck.getDeck().size() == TestDeck.getDefaultdeck().size());
        assertFalse(TestDeck.getDeck() == TestDeck.getDefaultdeck());
    }

    @Test
    public void drawACard() {
    }

    @Test
    public void numberOfCardsRemaining() {
        assertFalse(TestDeck.getDeck().size() == 200);
    }
}