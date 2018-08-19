package student;

import model.Player;
import model.RailroadBarons;
import model.RailroadBaronsObserver;

public class RRBObservers implements RailroadBaronsObserver {
    /**
     * Called when a {@linkplain Player player's} turn has started.
     * @param game The {@link RailroadBarons} game for which a new turn has
     *             started.
     * @param player The {@link Player} that has just started a turn.
     */
    @Override
    public void turnStarted(RailroadBarons game, Player player) {
        //TODO
    }

    /**
     * Called when a {@linkplain Player player's} turn has ended.
     *
     * @param game The {@link RailroadBarons} game for which the current turn
     *             has ended.
     * @param player The {@link Player} whose turn has ended.
     */
    @Override
    public void turnEnded(RailroadBarons game, Player player) {
        //TODO
    }

    /**
     * Called when the {@linkplain RailroadBarons Railroad Gameplay game} is
     * over.
     *
     * @param game The {@link RailroadBarons} game that has ended.
     * @param winner The winning {@link Player}.
     */
    @Override
    public void gameOver(RailroadBarons game, Player winner) {
        //TODO
    }
}
