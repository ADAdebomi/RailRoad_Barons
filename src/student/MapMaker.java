package student;

import model.*;
import model.Route;

import java.io.*;
import java.util.ArrayList;

public class MapMaker implements model.MapMaker {
    private model.RailroadMap GameMap;
    private InputStream filein;
    private OutputStream fileout;

    public MapMaker()
    {

    }
//    public MapMaker(File readfile, File writefile) throws RailroadBaronsException, IOException{
//        try{
//        this.filein = new FileInputStream(readfile);
//        this.GameMap = readMap(this.filein);
//        FileOutputStream filestreamout = new FileOutputStream(writefile);
//        }catch(FileNotFoundException ex){
//            System.out.println("Error file not found exception");
//           ex.printStackTrace();
//        }
//    }
    /**
     * Loads a {@linkplain RailroadMap map} using the data in the given
     * {@linkplain InputStream input stream}.
     *
     * @param in The {@link InputStream} used to read the {@link RailroadMap
     * map} data.
     * @return The {@link RailroadMap map} read from the given
     * {@link InputStream}.
     *
     * @throws RailroadBaronsException If there are any problems reading the
     * data from the {@link InputStream}.
     */
    public RailroadMap readMap(InputStream in) throws RailroadBaronsException{
        java.util.Scanner scanner = new java.util.Scanner(in, "UTF-8").useDelimiter("\n");
        ArrayList<Route> routes = new ArrayList<Route>();
        ArrayList<model.Station> stations= new ArrayList<Station>();

        int maxRow = 0;
        int maxColumn = 0;
        while(scanner.hasNext()){
            String readline = scanner.next();
            String[] splitline = readline.split(" ");
            if(splitline.length == 4){
                Stations newStation = new Stations(Integer.parseInt(splitline[0]),
                        Integer.parseInt(splitline[1]), Integer.parseInt(splitline[2]), splitline[3]);
                stations.add(newStation);
                if (newStation.getCol() > maxColumn)
                {
                    maxColumn = newStation.getCol();
                }
                if (newStation.getRow() > maxRow);
                {
                    maxRow = newStation.getRow();
                }
            }
            else{
                //Route generation  electric boogaloo
                if (splitline.length == 3){
                    Station origin = null;
                    Station destination = null;
                    Orientation orientation = null;
                    //get origin and destination stations
                    ArrayList<model.Track> ListTracks = new ArrayList<Track>();
                    for (Station station: stations){
                        if (((Stations)station).getStation_number() == Integer.parseInt(splitline[0])){
                            origin = station;
                        }
                        if(((Stations)station).getStation_number() == Integer.parseInt(splitline[1])){
                            destination = station;
                        }
                    }
                    //end of for loop
                    //sets the orientation and generates tracks
                    if(origin.getRow() == destination.getRow()){
                        orientation = Orientation.HORIZONTAL;
                        for(int c = origin.getCol()+1; c<destination.getCol(); c++){
                            //System.out.println("Creating a new track...");
                            Tracks newTrack = new Tracks(origin.getRow(), c, orientation);
                            ListTracks.add(newTrack);
                        }
                        //System.out.println("Adding Tracks: "+ ListTracks);
                    }
                    else{
                        if(origin.getCol() == destination.getCol()){
                            orientation = Orientation.VERTICAL;
                            for(int r = origin.getRow()+1; r<destination.getRow(); r++){
                            Tracks newTrack = new Tracks(r, origin.getCol(), orientation);
                            ListTracks.add(newTrack);
                            }
                        }
                    }
                    //Tracks generation ended
                    //Generates Route
                    Route newRoute = new student.Route(origin, destination, ListTracks, orientation);
                    routes.add(newRoute);
                }
            }
        }

        scanner.close();
        Map GameMap = new Map(stations, routes);
        return GameMap;
    }

    /**
     * Writes the specified {@linkplain RailroadMap map} in the Railroad
     * Gameplay map file format to the given {@linkplain OutputStream output
     * stream}. The written map should include an accurate record of any
     * routes that have been claimed, and by which {@linkplain Baron}.
     *
     * @param map The {@link RailroadMap map} to write out to the
     * {@link OutputStream}.
     * @param out The {@link OutputStream} to which the
     * {@link RailroadMap map} data should be written.
     *
     * @throws RailroadBaronsException If there are any problems writing the
     * data to the {@link OutputStream}.
     */
    public void writeMap(RailroadMap map, OutputStream out)
            throws RailroadBaronsException{
        PrintStream printer = new PrintStream(out);
        for(Station lstat: ((student.Map) map).getMystations()){
            printer.println(lstat.toString());
        }
        printer.println("##ROUTES##");
        for(Route route : map.getRoutes()){
            printer.println(route.toString());
        }
    }
}
