package student;

import org.junit.Test;

import static org.junit.Assert.*;

public class SpaceTest {
    private Space testspace;
    @org.junit.Before
    public void setup() throws model.SpaceException{
        testspace = new Space(0,2);
    }
    @Test
    public void getRow() {
        assertEquals(0, testspace.getRow());
    }

    @Test
    public void getCol() {
        assertEquals(2, testspace.getCol());
    }

    @Test
    public void collocated() throws model.SpaceException {
        assertTrue(testspace.collocated(new Space(0,2)));
        assertFalse(testspace.collocated(new Space(1,5)));
    }
}