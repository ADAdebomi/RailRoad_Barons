package student;

import model.Player;
import model.PlayerObserver;
import model.Route;

public class Player_Observers implements PlayerObserver {
    /**
     * Called whenever the {@linkplain Player player} of interest has changed
     * in some way including:
     * <ul>
     *     <li>The number of cards in the player's hand.</li>
     *     <li>The player's score.</li>
     *     <li>The number of train pieces that the player has remaining.</li>
     *     <li>A {@link Route route} has been claimed by the player.</li>
     * </ul>
     * @param player The {@link Player} of interest.
     */
    @Override
    public void playerChanged(Player player) {
        
    }
}
