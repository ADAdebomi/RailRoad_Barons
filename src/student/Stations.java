package student;

import model.Space;
import model.SpaceException;
import model.Station;

import java.util.LinkedList;
import java.util.List;

public class Stations implements Station {

    /**
     * the station row
     */
    private int row;

    /**
     * the station column
     */
    private int column;

    /**
     * the station name
     */
    private String Station_name;
    /**
     * the station number
     */
    private int Station_number;

    private List<Station> outNeighbors;
    private List<Station> inNeighbors;
    private int Rank;
    /**
     * Creates a new station
     * @param row the station row
     * @param column the station column
     * @param name the name of the station
     */
    public Stations(int number, int row, int column, String name) throws SpaceException {
        if (row < 0 || column <0)
        {
            throw new SpaceException(row,column);
        }
        else {
            this.row = row;
            this.column = column;
            Station_name = name;
            this.Station_number = number;
            this.outNeighbors = new LinkedList<>();
            this.inNeighbors = new LinkedList<>();
        }
    }

    /**
     * Gets the station number
     * @return the station number
     */
    public int getStation_number() {
        return Station_number;
    }

    /**
     * The name of the station, e.g. "Trainsville Station".
     *
     * @return The name of the station.
     */
    @Override
    public String getName() {
        return Station_name;
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
    public boolean collocated(Space other) {
        return row == other.getRow() && column == other.getCol();
    }

    @Override
    public String toString() {
        String toString = this.getStation_number()+" "+this.getRow()+" "+this.getCol()+" "+this.getName();
        return toString;
    }
    public void addInNeighbor(Station inStation){
        if(!(this.inNeighbors.contains(inStation))){
            this.inNeighbors.add(inStation);
        }
    }
    public void addOutNeighbor(Station outStation){
        if(!(this.outNeighbors.contains(outStation))){
            this.outNeighbors.add(outStation);
        }
    }

    public List<Station> getInNeighbors() {
        return inNeighbors;
    }

    public List<Station> getOutNeighbors() {
        return outNeighbors;
    }
    public void setRank(int rank){
        this.Rank = rank;
    }

    public int getRank() {
        return Rank;
    }
    public boolean isStartNode(){
        return inNeighbors.isEmpty();
    }
}
