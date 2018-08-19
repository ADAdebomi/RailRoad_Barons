package student;

import model.Card;

public class Pair implements model.Pair {

    private Card firstCard;

    private Card secondCard;

    public Pair(model.Deck deck){
        this.firstCard = deck.drawACard();
        this.secondCard = deck.drawACard();
    }

    /**
     * Returns the first {@linkplain Card card} in the pair. Note that, if the
     * game deck is empty, the value of this card may be {@link Card#NONE}.
     *
     * @return The first {@link Card} in the pair.
     */
    public Card getFirstCard(){
        return this.firstCard;
    }

    /**
     * Returns the second {@linkplain Card card} in the pair. if the
     * game deck is empty, the value of this card may be {@link Card#NONE}.
     *
     * @return The second {@link Card} in the pair.
     */
    public Card getSecondCard(){
        return this.secondCard;
    }
}
