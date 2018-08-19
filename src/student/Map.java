package student;

import model.*;
import model.Route;

import java.util.ArrayList;
import java.util.Collection;

public class Map implements RailroadMap{
    private ArrayList<RailroadMapObserver> myObservers;
    private ArrayList<model.Route> myRoutes;
    private ArrayList<Station> mystations;
    private ArrayList<Space> myspaces;
    private Space[][] spaces;

    public Map(ArrayList<Station> stations, ArrayList<model.Route> routes ) throws SpaceException{
        myRoutes = routes;
        mystations = stations;
        myObservers = new ArrayList<>();
        spaces = new Space[this.getRows()][this.getCols()];
        for(int r = 0; r<this.getRows(); r++){
            for(int c =0; c<this.getCols();c++ ){
                spaces[r][c] = new Space(r, c);
            }
        }

    }

    /**
     * Adds the specified {@linkplain RailroadMapObserver observer} to the
     * map. The observer will be notified of significant events involving this
     * map such as when a {@linkplain Route route} has been claimed by a
     * {@linkplain Baron}.
     *
     * @param observer The {@link RailroadMapObserver} being added to the map.
     */
    @Override
    public void addObserver(RailroadMapObserver observer) {
        myObservers.add(observer);
    }

    /**
     * Removes the specified {@linkplain RailroadMapObserver observer} from
     * the map. The observer will no longer be notified of significant events
     * involving this map.
     *
     * @param observer The observer to remove from the collection of
     *                 registered observers that will be notified of
     *                 significant events involving this map.
     */
    @Override
    public void removeObserver(RailroadMapObserver observer) {
        myObservers.remove(observer);
    }

    /**
     * Returns the number of rows in the map. This is determined by the
     * location of the southernmost {@linkplain Station station} on the map.
     *
     * @return The number of rows in the map.
     */
    @Override
    public int getRows() {
        int southmostrow = 0;
        for(Station station: mystations){
            int newrow = station.getRow();
            if(newrow>southmostrow){
                southmostrow = newrow;
            }
        }
        return southmostrow+1;
    }

    /**
     * Returns the number of columns in the map. This is determined by the
     * location of the easternmost {@linkplain Station station} on the map.
     *
     * @return The number of columns in the map.
     */
    @Override
    public int getCols() {
        int eastmost = 0;
        for(Station station: mystations){
            int newrow = station.getCol();
            if(newrow>eastmost){
                eastmost = newrow;
            }
        }
        return eastmost+1;
    }

    /**
     * Returns the {@linkplain model.Space space} located at the specified
     * coordinates.
     *
     * @param row The row of the desired {@link model.Space}.
     * @param col The column of the desired {@link model.Space}.
     *
     * @return The {@link model.Space} at the specified location, or null if the
     * location doesn't exist on this map.
     */
    @Override
    public model.Space getSpace(int row, int col){
        if(row>this.getRows() || col>this.getCols()){
            return null;
        }
        for(Station station: mystations){
            if(station.getRow() == row && station.getCol() ==col){
                return station;
            }
        }
        for(Route route: myRoutes){
            for(Track track: route.getTracks()){
                if(track.getRow() == row && track.getCol() == col){
                        return track; //May or may not work
                }
            }
        }
        return spaces[row][col];
    }

    /**
     * Returns the {@linkplain Route route} that contains the
     * {@link Track track} at the specified location (if such a route exists}.
     *
     * @param row The row of the location of one of the {@link Track tracks}
     *            in the route.
     * @param col The column of the location of one of the
     * {@link Track tracks} in the route.
     *
     * @return The {@link Route} that contains the {@link Track} at the
     * specified location, or null if there is no such {@link Route}.
     */
    @Override
    public Route getRoute(int row, int col){
        try {
            for (model.Route route : myRoutes) {
                if (route.includesCoordinate(new Space(row, col))) {
                    return route;
                }
            }

        }catch(model.SpaceException ex){
            System.out.println("Space exception");
        }
        return null;
    }

    /**
     * Called to update the {@linkplain RailroadMap map} when a
     * {@linkplain Baron} has claimed a {@linkplain Route route}.
     *
     * @param route The {@link Route} that has been claimed.
     */
    @Override
    public void routeClaimed(Route route) {
        for (RailroadMapObserver observer : myObservers)
        {
            observer.routeClaimed(this,route);
        }

    }

    /**
     * Returns the length of the shortest unclaimed {@linkplain Route route}
     * in the map.
     *
     * @return The length of the shortest unclaimed {@link Route}.
     */
    @Override
    public int getLengthOfShortestUnclaimedRoute() {
        double oldlength = Double.POSITIVE_INFINITY;
        for(model.Route route: myRoutes){
            int newlength = route.getLength();
            if(newlength<oldlength){
                oldlength = newlength;
            }
        }
        return (int)oldlength;
    }

    /**
     * Returns all of the {@link Route Routes} in this map.
     *
     * @return A {@link Collection} of all of the {@link Route Routes} in this
     * RailroadMap.
     */
    @Override
    public ArrayList<Route> getRoutes() {
        return myRoutes;
    }

    public ArrayList<Station> getMystations() {
        return mystations;
    }
}
