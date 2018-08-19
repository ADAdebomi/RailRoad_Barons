package student;

import model.*;
import model.Route;

import java.util.*;
import java.util.Map;

public class Gameplay implements RailroadBarons {

    private ArrayList<Player> list_players;
    private ArrayList<RailroadBaronsObserver> observers;
    private model.Deck deck;
    private RailroadMap map;
    private Player currentPlayer;
    private Collection<Route> list_routes;
    private model.Route route;
    private Map<String, Station> stationMap;
    private List<Station> northmostlist;
    private List<Station> southmostlist;
    private List<Station> eastmostlist;
    private List<Station> westmostlist;
    private List<CrossCountryRoute> crosscountryroutes;
    private List<List<Station>> crossCountryStationsNS;
    private List<List<Station>> crossCountryStationsEW;

    public Gameplay()
    {
        observers = new ArrayList<>();
        list_players = new ArrayList<>();
        northmostlist = new ArrayList<Station>();
        southmostlist = new ArrayList<Station>();
        eastmostlist  = new ArrayList<Station>();
        westmostlist = new ArrayList<Station>();
        crossCountryStationsNS = new ArrayList<List<Station>>();
        crossCountryStationsEW = new ArrayList<List<Station>>();
        crosscountryroutes = new ArrayList<CrossCountryRoute>();
    }
    /**
     * Adds a new {@linkplain RailroadBaronsObserver observer} to the
     * {@linkplain Collection collection} of observers that will be notified
     * when the state of the game changes. Game state changes include:
     * <ul>
     *     <li>A player's turn begins.</li>
     *     <li>A player's turn ends.</li>
     *     <li>The game is over.</li>
     * </ul>
     *
     * @param observer The {@link RailroadBaronsObserver} to add to the
     *                 {@link Collection} of observers.
     */
    @Override
    public void addRailroadBaronsObserver(RailroadBaronsObserver observer) {

        observers.add(observer);
    }

    /**
     * Removes the {@linkplain RailroadBaronsObserver observer} from the
     * collection of observers that will be notified when the state of the
     * game changes.
     *
     * @param observer The {@link RailroadBaronsObserver} to remove.
     */
    @Override
    public void removeRailroadBaronsObserver(RailroadBaronsObserver observer) {

        observers.remove(observer);
    }

    /**
     * Starts a new {@linkplain RailroadBarons Railroad Gameplay} game with the
     * specified {@linkplain RailroadMap map} and a default {@linkplain Deck
     * deck of cards}. If a game is currently in progress, the progress is
     * lost. There is no warning!
     *
     * By default, a new game begins with:
     * <ul>
     *     <li>A default deck that contains 20 of each color of card and 20
     *     wild cards.</li>
     *     <li>4 players, each of which has 50 train pieces.</li>
     *     <li>An initial hand of 4 cards dealt from the deck to each
     *     player</li>
     * </ul>
     *
     * @param map The {@link RailroadMap} on which the game will be played.
     */
    @Override
    public void startAGameWith(RailroadMap map)
    {
        this.map = map;
        list_routes = map.getRoutes();
        deck = new cardDeck();
        makeRouteGraph();
        buildedgeList();
        Players player1 = new Players(Baron.BLUE,deck);
        list_players.add(player1);
        Players player2 = new Players(Baron.YELLOW,deck);
        list_players.add(player2);
        Players player3 = new Players(Baron.GREEN,deck);
        list_players.add(player3);
        Players player4 = new Players(Baron.RED,deck);
        list_players.add(player4);
        currentPlayer = list_players.get(0);
        list_players.get(0).startTurn(new Pair(deck));
        for(RailroadBaronsObserver observer: observers){
            observer.turnStarted(this, currentPlayer);
        }
    }
    public void makeRouteGraph(){
        this.stationMap = new HashMap<>();
        for(Route route: this.map.getRoutes()){
            Station routeOrigin = route.getOrigin();
            Station routeDestination = route.getDestination();
            if(!stationMap.containsKey(routeOrigin.getName())){
                stationMap.put(routeOrigin.getName(), routeOrigin);
            }
            if (!stationMap.containsKey(routeDestination.getName())){
                stationMap.put(routeDestination.getName(), routeDestination);
            }
            if(!(((Stations)stationMap.get(routeOrigin.getName())).getOutNeighbors().contains(routeDestination))){
                ((Stations)stationMap.get(routeOrigin.getName())).addOutNeighbor(routeDestination);
                ((Stations)stationMap.get(routeDestination.getName())).addInNeighbor(routeOrigin);
            }
            if(!(((Stations)stationMap.get(routeDestination.getName())).getInNeighbors().contains(routeOrigin))){
                ((Stations)stationMap.get(routeOrigin.getName())).addOutNeighbor(routeDestination);
                ((Stations)stationMap.get(routeDestination.getName())).addInNeighbor(routeOrigin);
            }
        }
        setRankBFS();
    }

    /**
     * returns the station graph
     * @return the station map
     */
    public Map<String, Station> getStationMap(){
        return this.stationMap;
    }

    /**
     * sets the rank of all the nodes in the graph
     */
    public void setRankBFS(){
        ArrayList<Station> newqueue = new ArrayList<Station>();
        for(String key: this.getStationMap().keySet()){
            if((((Stations) this.getStationMap().get(key))).isStartNode()){
                newqueue.add(this.getStationMap().get(key));
            }
        }
        while(!(newqueue.isEmpty())){
            Station current = newqueue.remove(0);
            for(Station outStation: ((Stations)current).getOutNeighbors()){
                int newRank = ((Stations)current).getRank()+1;
                if(newRank>((Stations)outStation).getRank()){
                    ((Stations)outStation).setRank(newRank);
                    newqueue.add(outStation);
                }
            }
        }
    }

    /**
     * Vistits a node in the graph
     * @param node the node vistited
     * @param visited the list of visited nodes
     */
    private void visitDFS(Station node, Set<Station> visited) {
        for (Station neighbor : ((Stations)node).getOutNeighbors()) {
            if(!visited.contains(neighbor)) {
                visited.add(neighbor);
                visitDFS(neighbor, visited);
            }
        }
    }

    private List<Station> buildPathRecursiveDFS(Station start, Station finish, Set<Station> visited) {
        List<Station> path = new LinkedList<Station>();

        // base case when start gets to finish
        if (start.equals(finish)) {
            path.add(0, start);
            return path;
        } else {
            for (Station neighbor : ((Stations)start).getOutNeighbors()) {
                if(!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    path = buildPathRecursiveDFS(neighbor, finish, visited);
                    // if path is not empty, we are part of the solution path,
                    // append ourselves to the front of the path list and return
                    if(path.size() > 0) {
                        path.add(0, start);
                        return path;
                    }
                }
            }
            // no luck, return empty path
            return path;
        }
    }

    public List<Station> searchRecursiveDFS(String start, String finish) {
        // assumes input check occurs previously
        Station startNode, finishNode;
        startNode = stationMap.get(start);
        finishNode = stationMap.get(finish);

        // construct a visited list of all nodes reachable from the start
        Set<Station> visited = new HashSet<Station>();
        visited.add(startNode);

        List<Station> path = buildPathRecursiveDFS(startNode, finishNode, visited);
        return path;
    }

    public void buildedgeList(){
        int numrows = this.getRailroadMap().getRows();
        int numcols = this.getRailroadMap().getCols();
        for(Route route: this.getRailroadMap().getRoutes()){
            if(route.getOrigin().getRow() == 0){
                if(!(this.northmostlist.contains(route.getOrigin()))){
                    this.northmostlist.add(route.getOrigin());
                }
            }
            if(route.getOrigin().getRow() == numrows){
                if(!(this.southmostlist.contains(route.getOrigin()))){
                    this.southmostlist.add(route.getOrigin());
                }
            }
            if(route.getOrigin().getCol() == 0){
                if(!(this.westmostlist.contains(route.getOrigin()))){
                    this.westmostlist.add(route.getOrigin());
                }
            }
            if(route.getOrigin().getCol() == numcols){
                if(!(this.eastmostlist.contains(route.getOrigin()))){
                    this.eastmostlist.add(route.getOrigin());
                }
            }
            if(route.getDestination().getRow() == 0){
                if(!(this.northmostlist.contains(route.getDestination()))){
                    this.northmostlist.add(route.getDestination());
                }
            }
            if(route.getDestination().getRow() == numrows){
                if(!(this.southmostlist.contains(route.getDestination()))){
                    this.southmostlist.add(route.getDestination());
                }
            }
            if(route.getDestination().getCol() == 0){
                if(!(this.westmostlist.contains(route.getDestination()))){
                    this.westmostlist.add(route.getDestination());
                }
            }
            if(route.getDestination().getCol() == numcols){
                if(!(this.eastmostlist.contains(route.getDestination()))){
                    this.eastmostlist.add(route.getDestination());
                }
            }
        }
        //north south
        for(Station station: northmostlist){
            Set<Station> visted = new HashSet<Station>();
            for(Station station2: southmostlist){
                List<Station> path = buildPathRecursiveDFS(station, station2, visted);
                if(!(path.isEmpty())){
                    if (!(crossCountryStationsNS.contains(path))){
                        crossCountryStationsNS.add(path);
                    }
                }
            }
        }
        //east west
        for(Station station: eastmostlist){
            Set<Station> visted = new HashSet<Station>();
            for(Station station2: westmostlist){
                List<Station> path = buildPathRecursiveDFS(station, station2, visted);
                if(!(path.isEmpty())){
                    if (!(crossCountryStationsEW.contains(path))){
                        crossCountryStationsEW.add(path);
                    }
                }
            }
        }
        //end of list generation
        for (int i =0; i<crossCountryStationsNS.size(); i++){
            CrossCountryRoute newCross = new CrossCountryRoute(crossCountryStationsNS.get(i), this.getRailroadMap(), "NS");
            crosscountryroutes.add(newCross);
        }
        for (int i =0; i<crossCountryStationsEW.size(); i++){
            CrossCountryRoute newCross = new CrossCountryRoute(crossCountryStationsEW.get(i), this.getRailroadMap(), "EW");
            crosscountryroutes.add(newCross);
        }
    }
    /**
     * Starts a new {@linkplain RailroadBarons Railroad Gameplay} game with the
     * specified {@linkplain RailroadMap map} and {@linkplain Deck deck of
     * cards}. This means that the game should work with any implementation of
     * the {@link Deck} interface (not just a specific implementation)!
     * Otherwise, the starting state of the game is the same as a
     * {@linkplain #startAGameWith(RailroadMap) normal game}.
     *
     * @param map The {@link RailroadMap} on which the game will be played.
     * @param deck The {@link Deck} of cards used to play the game. This may
     *             be ANY implementation of the {@link Deck} interface,
     *             meaning that a valid implementation of the
     *             {@link RailroadBarons} interface should use only the
     *             {@link Deck} interface and not a specific implementation.
     */
    @Override
    public void startAGameWith(RailroadMap map, Deck deck) {
        this.deck = deck;
        this.map = map;
        makeRouteGraph();
        buildedgeList();
        Players player1 = new Players(Baron.BLUE,deck);
        list_players.add(player1);
        Players player2 = new Players(Baron.YELLOW,deck);
        list_players.add(player2);
        Players player3 = new Players(Baron.GREEN,deck);
        list_players.add(player3);
        Players player4 = new Players(Baron.RED,deck);
        list_players.add(player4);
        currentPlayer = list_players.get(0);
        list_players.get(0).startTurn(new Pair(deck));
        for(RailroadBaronsObserver observer: observers){
            observer.turnStarted(this, currentPlayer);
        }
    }

    /**
     * Returns the {@linkplain RailroadMap map} currently being used for play.
     * If a game is not in progress, this may be null!
     *
     * @return The {@link RailroadMap} being used for play.
     */
    @Override
    public RailroadMap getRailroadMap() {
        return map;
    }

    /**
     * Returns the number of {@linkplain Card cards} that remain to be dealt
     * in the current game's {@linkplain Deck deck}.
     *
     * @return The number of cards that have not yet been dealt in the game's
     * {@link Deck}.
     */
    @Override
    public int numberOfCardsRemaining() {
        return deck.numberOfCardsRemaining();
    }

    /**
     * Returns true iff the current {@linkplain Player player} can claim the
     * {@linkplain model.Route route} at the specified location, i.e. the player has
     * enough cards and pieces, and the route is not currently claimed by
     * another player. Should delegate to the
     * {@link Player#canClaimRoute(model.Route)} method on the current player.
     *
     * @param row The row of a {@link Track} in the {@link model.Route} to check.
     * @param col The column of a {@link Track} in the {@link model.Route} to check.
     * @return True iff the {@link Route} can be claimed by the current
     * player.
     */
    @Override
    public boolean canCurrentPlayerClaimRoute(int row, int col) {
        for (Route route : list_routes)
        {
            try {
                if (route.includesCoordinate(new Space(row,col)))
                {
                    if (currentPlayer.canClaimRoute(route))
                    {
                        this.route = route;
                        return true;
                    }
                }
            } catch (SpaceException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Attempts to claim the {@linkplain Route route} at the specified
     * location on behalf of the current {@linkplain Player player}.
     *
     * @param row The row of a {@link Track} in the {@link Route} to claim.
     * @param col The column of a {@link Track} in the {@link Route} to claim.
     */
    @Override
    public void claimRoute(int row, int col) {
        if (canCurrentPlayerClaimRoute(row,col))
        {
            try {
                currentPlayer.claimRoute(route);
                map.routeClaimed(route);
            } catch (RailroadBaronsException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Called when the current {@linkplain Player player} ends their turn.
     */
    @Override
    public void endTurn()
    {
        for(RailroadBaronsObserver observer: observers){
            observer.turnEnded(this, list_players.get(0));
        }
        Player lastPlayer = list_players.get(0);
        list_players.remove(0);
        list_players.add(lastPlayer);
        currentPlayer = list_players.get(0);
        gameIsOver();
        list_players.get(0).startTurn(new Pair(deck));
        for(RailroadBaronsObserver observer: observers){
            observer.turnStarted(this, currentPlayer);
        }
    }

    /**
     * Returns the {@linkplain Player player} whose turn it is.
     *
     * @return The {@link Player} that is currently taking a turn.
     */
    @Override
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Returns all of the {@linkplain Player players} currently playing the
     * game.
     *
     * @return The {@link Player Players} currently playing the game.
     */
    @Override
    public Collection<Player> getPlayers()
    {
        return new ArrayList<Player>(list_players);
    }

    /**
     * Indicates whether or not the game is over. This occurs when no more
     * plays can be made. Reasons include:
     * <ul>
     *     <li>No one player has enough pieces to claim a route.</li>
     *     <li>No one player has enough cards to claim a route.</li>
     *     <li>All routes have been claimed.</li>
     * </ul>
     *
     * @return True if the game is over, false otherwise.
     */
    @Override
    public boolean gameIsOver() {
        ArrayList<Route> list = new ArrayList<>();
        int count = 0;
        for (Route route : list_routes)
        {
            if (route.getBaron() == Baron.UNCLAIMED)
            {
                list.add(route);
            }
        }
        if (list.size() == 0)
        {
            for(Player player: list_players){
                for(CrossCountryRoute curcross: crosscountryroutes){
                    boolean ownall = true;
                    for(Route crossroutes: curcross.getRoutes()){
                        if(!(player.getBaron().equals(crossroutes.getBaron()))){
                            ownall = false;
                        }
                    }
                    if(ownall){
                        if(player instanceof CompPlayer){
                            if(curcross.getOrientation().equals("NS")){
                                ((CompPlayer) player).AlterScore(5*this.getRailroadMap().getRows());
                            }
                            if(curcross.getOrientation().equals("EW")){
                                ((CompPlayer) player).AlterScore(5*this.getRailroadMap().getCols());
                            }
                        }
                        if(curcross.getOrientation().equals("NS")){
                            ((Players) player).AlterScore(5*this.getRailroadMap().getRows());
                        }
                        if(curcross.getOrientation().equals("EW")){
                            ((Players) player).AlterScore(5*this.getRailroadMap().getCols());
                        }
                    }
                }
            }
            Player winner = currentPlayer;
            for(Player player: list_players){
                if(player.getScore()>winner.getScore()){
                    winner = player;
                }
            }
            for(RailroadBaronsObserver observer: observers){
                observer.gameOver(this, winner);
            }
            return true;
        }
        for (Player player : list_players)
        {
            for (Route route : list) {
                if (player.canContinuePlaying(route.getLength()))
                {
                    count += 1;
                }
            }
        }
        return count == 0;
    }
}
