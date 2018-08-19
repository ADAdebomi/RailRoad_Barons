package student;
//
import model.Baron;
import model.Orientation;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TracksTest {

    private Tracks testTracks;

    private Tracks testTracks1;

    @Before
    public void setUp() throws Exception {
        testTracks = new Tracks(1,2, Orientation.HORIZONTAL);
    }

    @Test
    public void getOrientation() {
        assertEquals(testTracks.getOrientation(), Orientation.HORIZONTAL);
    }

    @Test
    public void getBaron() {
        assertEquals(testTracks.getBaron(), null);
    }

    @Test
    public void getRoute() {
        assertEquals(testTracks.getRoute(), null);
    }

    @Test
    public void getRow() {
        assertEquals(testTracks.getRow(), 1);
    }

    @Test
    public void getCol() {
        assertEquals(testTracks.getCol(), 2);
    }

    @Test
    public void collocated() throws Exception{
        assertTrue(testTracks.collocated(new Tracks(1,2,Orientation.HORIZONTAL)));
        assertFalse(testTracks.collocated(new Tracks(5,6, Orientation.VERTICAL)));
    }
}