package student;

import static org.junit.Assert.*;

public class StationsTest {

    private Stations testStations;

    @org.junit.Before
    public void setUp() throws Exception {
        testStations = new Stations(0, 1, 2, "test1");
    }

    @org.junit.Test
    public void getName() {
        assertEquals("test1", testStations.getName());
    }

    @org.junit.Test
    public void getRow()
    {
        assertEquals(1, testStations.getRow());
    }

    @org.junit.Test
    public void getCol()
    {
        assertEquals(2, testStations.getCol());
    }

    @org.junit.Test
    public void collocated() throws model.SpaceException {
        assertTrue(testStations.collocated(new Stations(0, 1, 2, "test1")));
        assertFalse(testStations.collocated(new Stations(0, 4, 2, "test2")));
    }
}