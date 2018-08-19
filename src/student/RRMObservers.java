package student;

import model.RailroadMap;
import model.RailroadMapObserver;
import model.Route;

public class RRMObservers implements RailroadMapObserver {
    /**
     * Called when a {@linkplain Route route} is successfully claimed on the
     * {@linkplain RailroadMap map}.
     *
     * @param map The {@link RailroadMap} on which the {@link Route} has been
     *            claimed.
     *
     * @param route The {@link Route} that has been claimed.
     */

    @Override
    public void routeClaimed(RailroadMap map, Route route) {

    }
}
