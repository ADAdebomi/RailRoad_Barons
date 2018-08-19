package student;

import model.SpaceException;

public class Space implements model.Space {
    private int myrow;
    private int mycolumn;

    public  Space(int row, int column) throws SpaceException {
        if (row < 0 || column <0)
        {
            throw new SpaceException(row,column);
        }
        else{
            this.mycolumn = column;
            this.myrow = row;
        }
    }
    /**
     * Returns the row of the space's location in the map.
     *
     * @return The row of the space's location in the map.
     */
    public int getRow(){
        return myrow;
    }

    /**
     * Returns the column of the space's location in the map.
     *
     * @return The column of the space's location in the map.
     */
    public int getCol(){
        return mycolumn;
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
    public boolean collocated(model.Space other){
        return myrow == other.getRow() && mycolumn == other.getCol();
    }
}
