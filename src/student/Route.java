package student;

import model.*;
import model.Space;

import java.util.List;

public class Route implements model.Route {

    private Baron baron;

    private Station origin;

    private Orientation orientation;

    private Station dest;

    private List<Track> list;

    private int length;

    /**
     * Creates a new route
     * //@param baron the route baron
     * @param origin the station that the route begins from
     * @param dest the station that the route ends
     * @param tracks the list of tracks on that route
     * @param orientation the orientation of the route
     */
    public Route(Station origin, Station dest, List<Track> tracks, Orientation orientation)
    {
        this.baron = Baron.UNCLAIMED;
        this.origin = origin;
        this.dest = dest;
        this.orientation = orientation;
        this.list = tracks;
        this.length = tracks.size();
    }
    /**
     * Returns the {@linkplain Baron} that has claimed this route. Note that
     * this route may be {@linkplain Baron#UNCLAIMED unclaimed}.
     *
     * @return The {@link Baron} that has claimed this route.
     */
    @Override
    public Baron getBaron() {
        return baron;
    }

    /**
     * Returns the {@linkplain Station station} at the beginning of this
     * route. The origin must be directly north of or to the west of the
     * destination.
     *
     * @return The {@link Station} at the beginning of this route.
     */
    @Override
    public Station getOrigin() {
        return origin;
    }

    /**
     * Returns the {@linkplain Station station} at the end of this route. The
     * destination must be directly south of or to the east of the origin.
     *
     * @return The {@link Station} at the end of this route.
     */
    @Override
    public Station getDestination() {
        return dest;
    }

    /**
     * Returns the {@linkplain Orientation orientation} of this route; either
     * {@linkplain Orientation#HORIZONTAL horizontal} or
     * {@linkplain Orientation#VERTICAL vertical}.
     *
     * @return The {@link Orientation} of this route.
     */
    @Override
    public Orientation getOrientation() {
        return orientation;
    }

    /**
     * The set of {@linkplain Track tracks} that make up this route.
     *
     * @return The {@link List} of {@link Track tracks} that make up this
     * route.
     */
    @Override
    public List<Track> getTracks() {
        return list;
    }

    /**
     * Returns the length of the route, not including the {@linkplain Station
     * stations} at the end points.
     *
     * @return The number of {@link Track Tracks} comprising this route.
     */
    @Override
    public int getLength() {
        return length;
    }

    /**
     * Returns the number of points that this {@linkplain model.Route route} is
     * worth according to the following algorithm:
     * <ul>
     *     <li>1 - 1 point</li>
     *     <li>2 - 2 points</li>
     *     <li>3 - 4 points</li>
     *     <li>4 - 7 points</li>
     *     <li>5 - 10 points</li>
     *     <li>6 - 15 points</li>
     *     <li>7 (or more) - 5 * (length - 3) points</li>
     * </ul>
     *
     * @return The number of points that this route is worth.
     */
    @Override
    public int getPointValue() {
        int point = 0;
        switch (length)
        {
            case 1 : point = 1;
            case 2 : point = 2;
            case 3 : point = 4;
            case 4 : point = 7;
            case 5 : point = 10;
            case 6 : point = 15;
            default: point = 5 * (length - 3);
        }
        return point;
    }

    /**
     * Returns true if the route covers the ground at the location of the
     * specified {@linkplain model.Space space} and false otherwise.
     *
     * @param space The {@link model.Space} that may be in this route.
     *
     * @return True if the {@link Space Space's} coordinates are a part of
     * this route, and false otherwise.
     */
    @Override
    public boolean includesCoordinate(model.Space space) {
        for(Track track: list){
            if(track.getCol() == space.getCol() && track.getRow() == space.getRow()){
                return true;
            }
        }
        return false;
    }

    /**
     * Attempts to claim the route on behalf of the specified
     * {@linkplain Baron}. Only unclaimed routes may be claimed.
     *
     * @param claimant The {@link Baron} attempting to claim the route. Must
     *                 not be null or {@link Baron#UNCLAIMED}.
     * @return True if the route was successfully claimed. False otherwise.
     */
    @Override
    public boolean claim(Baron claimant) {
        if(!(this.baron == Baron.UNCLAIMED)){
            return false;
        }
        else{
            this.baron = claimant;
            for(Track track: list){
                track = new Tracks(baron);
            }
            return true;
        }
    }

    @Override
    public String toString() {
        String toString = ((Stations) this.origin).getStation_number()+" "+((Stations) this.dest).getStation_number()
                +" "+this.baron;
        return toString;
    }
}
