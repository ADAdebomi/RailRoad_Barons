package student;

import model.RailroadMap;
import model.Route;
import model.Station;

import java.util.ArrayList;
import java.util.List;

public class CrossCountryRoute {
    private List<Station> stations;
    private List<Route> routes;
    private String orientation;
    public CrossCountryRoute(List<Station> stationslist, RailroadMap map, String orientation){
        this.stations = stationslist;
        this.routes = new ArrayList<>();
        this.orientation = orientation;
        for(Route route: map.getRoutes()){
            for (int i = 0; i<stations.size(); i++){
                Station origin = stations.get(i);
                Station Destination = stations.get(i+1);
                if(route.getOrigin().equals(origin) && route.getDestination().equals(Destination)){
                    this.routes.add(route);
                }
            }
        }

    }

    public List<Station> getStations() {
        return this.stations;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public String getOrientation() {
        return orientation;
    }
}
