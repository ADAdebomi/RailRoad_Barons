package student;

import model.*;

public class Tracks implements Track {

    private int row;

    private int column;

    private Baron baron;

    private Orientation orientation;

    private Route route;

    /**
     * Creates a new track without a route
     * @param row the track row
     * @param column the track column
     * //@param baron the track baron
     * @param orientation the orientation of the track
     */

    public Tracks(int row, int column, Orientation orientation) throws SpaceException {
        if (row < 0 || column <0)
        {
            throw new SpaceException(row,column);
        }
        else{
        this.row = row;
        this.column = column;
        this.baron = Baron.UNCLAIMED;
        this.orientation = orientation;
        this.route = null;}
    }

    /**
     * Creates a new track with a route
     * @param row the track row
     * @param column the track column
     * //@param baron the track baron
     * @param orientation the orientation of the track
     * @param route the route connected to that track.
     */
    public Tracks(int row, int column, Orientation orientation, Route route) throws SpaceException {
        if (row < 0 || column <0)
        {
            throw new SpaceException(row,column);
        }
        else{
        this.row = row;
        this.column =  column;
        this.baron =  Baron.UNCLAIMED;
        this.orientation = orientation;
        this.route = route;}
    }

    public Tracks(Baron baron)
    {
        this.baron =  baron;
    }

    /**
     * Returns the orientation of the track; either
     * {@linkplain Orientation#HORIZONTAL horizontal} or
     * {@linkplain Orientation#VERTICAL vertical}. This is based on the
     * {@linkplain Orientation orientation} of the {@linkplain model.Route route}
     * that contains the track.
     *
     * @return The {@link Orientation} of the {@link Track}; this is the same
     * as the {@link Orientation} of the {@link model.Route} that contains the
     * track.
     */
    @Override
    public Orientation getOrientation() {
        return orientation;
    }

    /**
     * Returns the current {@link Baron owner} of this track, either
     * {@linkplain Baron#UNCLAIMED unclaimed} if the track has not been
     * claimed, or the {@linkplain Baron owner} that corresponds with the
     * color of the player that successfully claimed the
     * {@linkplain model.Route route} of which this track is a part.
     *
     * @return The {@link Baron} that has claimed the route of which this
     * track is a part.
     */
    @Override
    public Baron getBaron() {
        return baron;
    }

    /**
     * Returns the {@linkplain model.Route route} of which this
     * {@linkplain Track track} is a part.
     *
     * @return The {@link model.Route} that contains this track.
     */
    @Override
    public Route getRoute() {
        return route;
    }

    /**
     * Returns the row of the space's location in the map.
     *
     * @return The row of the space's location in the map.
     */
    @Override
    public int getRow() {
        return row;
    }

    /**
     * Returns the column of the space's location in the map.
     *
     * @return The column of the space's location in the map.
     */
    @Override
    public int getCol() {
        return column;
    }

    /**
     * Returns true if the other space is occupying the same physical location
     * in the map as this space.
     *
     * @param other The other space to which this space is being compared for
     *              collocation.
     *
     * @return True if the two spaces are in the same physical location (row
     * and column) in the map; false otherwise.
     */
    @Override
    public boolean collocated(model.Space other) {
        return row == other.getRow() && column == other.getCol();
    }
}
