package Players.p14208g;

import Interface.*;

import java.util.*;

/**
 * Representation of the pathbuilder board
 * Created by Eugene on 3/13/2017.
 * Implementation added by Smruthi
 */
public class p14208g implements PlayerModulePart1, PlayerModulePart2{
    int boardSize;
    int playerid;
    int dim = 0;
    int[][] board;
    Coordinate lastCoor;

    ArrayList<Coordinate> playerOneMoves;
    HashMap<Coordinate,Integer> graph;//distance map


    /**
     * Method called to initialize a player module. Required task for Part 1.
     * Note that for tournaments of multiple games, only one instance of each PlayerModule is created.
     * The initPlayer method is called at the beginning of each game, and must be able to reset the
     * player for the next game.
     * @Param:
     * dim - size of the smaller dimension of the playing area for one player.
     * The grid of nodes for that player is of size dim x (dim+1).
     *
     * id - id (1 or 2) for this player.
     */
    @Override
    public void initPlayer(int dim, int id) {
        boardSize = 2*dim+1;
        this.dim = dim;
        board = new int[2*dim+1][2*dim+1];
        playerid = id;
        graph=new HashMap<>();
        //System.out.println("initplayer");
        System.out.println("BoardSize: " + boardSize);
        for(int r = 0; r < boardSize; r++){
            for(int c = 0; c < boardSize; c++){
                if(r%2 == 1 && c%2 == 0){
                    board[r][c] = 1;
//                    graph.put(new Coordinate(r,c),-1);
                }
                if(r%2 == 0 && c%2 == 1){
                    board[r][c] = 2;
//                    graph.put(new Coordinate(r,c),-1);
                }
            }
         }


    }


    @Override
    /**
     * Method called after every move of the game. Used to keep internal game state current.
     * Required task for Part 1.
     * Note that the engine will only call this method after verifying the validity of the current move.
     * Thus, you do not need to verify the move provided to this method. It is guaranteed to be a valid move.
     *
     * @Parameter:
     * playerMove - PlayerMove representing the most recent move
     */
    public void lastMove(PlayerMove playerMove) {
        board[playerMove.getCoordinate().getRow()][playerMove.getCoordinate().getCol()]
                = playerMove.getPlayerId();
//        if(allLegalMoves().contains(playerMove.getCoordinate())){
//            allLegalMoves().remove(playerMove.getCoordinate());
//        }

    }

    /**must be able to detect if there is a complete path from one side of the board
     * to the other for a particular player.
     *
     * Part 1 task that tests if a player has won the game given a set of PREMOVEs.
     *
     * @param i - player to test for a winning path.
     * @return - boolean value indicating if the player has a winning path.
     */
    @Override
    public boolean hasWonGame(int i) {
        //printboard(board);
        ArrayList<Coordinate> queue = new ArrayList<>();
        HashSet<Coordinate> visited = new HashSet<>();
        if(i == 1){
            for(int n = 1; n < 2*dim+1; n+=2){
                queue.add(new Coordinate(n,0));
            }
        }
        if(i == 2){
            for(int n = 1; n < 2*dim+1; n+=2){
                queue.add(new Coordinate(0, n));
            }
        }
        while(!queue.isEmpty()){
            Coordinate curr = queue.remove(0);
            lastCoor=curr;
            visited.add(curr);

            int r = curr.getRow();
            int c = curr.getCol();
            //System.out.println("Row: " + r + "   Column: " + c);

            if(i == 1 && c == boardSize-1){
                return true;
            }
            else if(i == 2 && r == boardSize-1){
                return true;
            }

            //check up all 4 neighboring cells (edge positions)
            //north edge
            if(r-1 > 0 && c > 0 && r < boardSize-1 && c < boardSize-1 && board[r-1][c] == i && !visited.contains(new Coordinate(r-1, c))){
                queue.add(new Coordinate(r-1, c));
                //System.out.println("inside north");
            }
            //south edge
            if(r+1 > 0 && c > 0 && r < boardSize-1 && c < boardSize-1 && board[r+1][c] == i && !visited.contains(new Coordinate(r+1, c))){
                queue.add(new Coordinate(r+1, c));
                //System.out.println("inside south");
            }
            //east edge
            if(r > 0 && c+1 > 0 && r < boardSize-1 && c < boardSize-1 && board[r][c+1] == i ){
                queue.add(new Coordinate(r, c+1));
                //System.out.println("inside east");
            }
            //west edge
            if(r > 0 && c-1 > 0 && r < boardSize-1 && c < boardSize-1 && board[r][c-1] == i && !visited.contains(new Coordinate(r, c-1))){
                queue.add(new Coordinate(r, c-1));
                //System.out.println("inside west");
            }


        }

//        System.out.println(lastCoor + " last coor");
        //visit each cell in a BFS manner and if the curr cell is final node.
        //e.g player 1 has 5 final nodes. (ones at the end)
        //System.out.println("out of while loop for haswon");
        return false;
    }

    @Override
    /**
     * Indicates that the other player has been invalidated. Required task for Part 2.
     */
    public void otherPlayerInvalidated() {

    }

    @Override
    /**
     * Generates the next move for this player.
     * Note that it is recommended that updating internal game state does NOT
     * occur inside of this method.
     * See lastMove. An initial, working version of this method is required for Part 2.
     * It may be refined subsequently.
     */
    public PlayerMove move() {
        List legalMoves=allLegalMoves();
        PlayerMove pMove=(PlayerMove)legalMoves.remove(legalMoves.size()-1);
        System.out.println(pMove);
        return pMove;
    }

    /**
     * Helper method to print the board
     * @param board1
     */
    public void printboard(int[][] board1){
        String printer = "";
        for(int r = 0; r<board1.length; r++){
            for(int c = 0; c<board1.length; c++){
                printer += board1[r][c] + " ";
                if(c == board1.length-1){
                    printer+="\n";
                }
            }
        }
        System.out.println(printer);
    }


    public static void main(String[] args) {
        //int[][]board1 = new int[11][11];
        //board1[5][0] = 2;
        //board1[7][6] = 3;
        /**
        String printer = "";
        for(int r = 0; r<board1.length; r++){
            for(int c = 0; c<board1.length; c++){
                printer += board1[r][c] + " ";
                if(c == board1.length-1){
                    printer+="\n";
                }
            }
        }
        System.out.println(printer);
    */

    }

    /**
     * Method to fetch all the legal moves
     * @return list of playermoves
     */
    @Override
    public List<PlayerMove> allLegalMoves() {
        List<PlayerMove> legalMoves=new ArrayList<>();
        PlayerMove p;
        Coordinate co;
        //looping thru the board and looking for edges that are not placed.
        for(int r = 1; r < boardSize-1; r++){
            for(int c = 1; c < boardSize-1; c++){
                if(board[r][c]==0 ){
                    co=new Coordinate(r,c);
                    p=new PlayerMove(co,this.playerid);
                    legalMoves.add(p);
                }
            }
        }
        System.out.println("legal moves " + "player " + this.playerid+ legalMoves);
        return legalMoves;

    }

    @Override

    /**
     * Mehtod to find the least number of moves to win the game
     */
    public int fewestSegmentsToVictory(int i) {
        int noOfSegs=Integer.MAX_VALUE;

        ArrayList<Coordinate> neighbors;
        //initializing distance map
        createDistanceMap();
        // create a distance map that will hold the shortest path distance
        // to each node from the given startNode.  We will just use the
        // maximum Integer value to represent infinity
        Map<Coordinate, Integer> distance = new HashMap<Coordinate, Integer>();

        // create a predecessor map that will be used to determine
        // the shortest path to each node from the given startNode.
        // If a node is not yet in the map, that is equivalent to the
        // node not having a predecessor, and not being reachable.
        Map<Coordinate, Coordinate> predecessors = new HashMap<Coordinate, Coordinate>();


//        printGraph();
        //check if has won
        if(hasWonGame(i)){
            return 0;
        }
        else{
            ArrayList<Coordinate> startQueue = new ArrayList<>();
            ArrayList<Coordinate> finishQueue=new ArrayList<>();
            HashSet<Coordinate> visited;// predecessor Map
            Coordinate node;
            if(i == 1){
                this.playerid=i;
                for(int n = 1; n < 2*dim+1; n+=2){
                    node=new Coordinate(n,0);
                    if(board[n][0]==1){
                    startQueue.add(node);
                    node=new Coordinate(n,boardSize-1);
                    finishQueue.add(node);
                    graph.put(new Coordinate(n,0),0);}
                    else if(board[n][0]==2 ){
                        graph.put(new Coordinate(n,0),Integer.MAX_VALUE);
                    }
                }
            }
            if(i == 2){
                this.playerid=i;
                for(int n =1; n < 2*dim+1; n+=2){

                    node=new Coordinate(0,n);
                    if(board[0][n]==2){
                        startQueue.add(new Coordinate(0, n));

                        finishQueue.add(new Coordinate(boardSize-1,n));
                        graph.put(new Coordinate(0,n),0);}
                    else if(board[0][n]==1 ){
                        graph.put(new Coordinate(0,n),Integer.MAX_VALUE);
                    }
                }
            }
//            System.out.println("finish queue: " + finishQueue);
//            printGraph();
            visited = new HashSet<>();
            //use Dijkstra's algo
            while(!startQueue.isEmpty()){
                Coordinate startNode=startQueue.remove(0);
                dijkstra(startNode, i, predecessors);

            }

            Coordinate finishNode = null;

            for (Coordinate c:finishQueue
                 ) {
//                System.out.println("finishnodes "+ c + " " + graph.get(c));
                if( graph.get(c)==Integer.MAX_VALUE){


                }
                else if(finishNode==null)finishNode=c;
                else
                    if(finishNode!=null && graph.get(c)<graph.get(finishNode)){
//                        System.out.println("Selected finishnodes "+ c + " " + graph.get(c));
                        finishNode=c;
                    }




            }
            if(finishNode!=null ){
                return graph.get(finishNode);
            }
            else
                return Integer.MAX_VALUE;



        }


    }

    /**
     * Mehtod to create the Distance map for Dijkstra's algorithm
     */
    private void createDistanceMap(){
        for(int i=0;i<boardSize;i++){
            for (int j = 0; j <boardSize ; j++) {
                if((i%2==1 && j%2==0 )|| (i%2==0 && j%2==1)){
                    graph.put(new Coordinate(i,j),Integer.MAX_VALUE);
                }

            }
        }


    }

    /**
     * Method to gett he weight of the edge
     * @param c1 the start node
     * @param c2 the end node
     * @return integer
     */
    private int getEdge(Coordinate c1,Coordinate c2){
        int x1=c1.getRow();
        int y1=c1.getCol();
        int x2=c2.getRow();
        int y2=c2.getCol();
        if(x1==x2 && y2>y1){
//            System.out.println("Edge: " +board[x1][y2-1]);
            return board[x1][y2-1];
        }
        else if(x1==x2 && y1>y2){
//            System.out.println("Edge: " + board[x1][y1-1]);
            return board[x1][y1-1];
            }

        else if(y1==y2 && x1>x2){
//            System.out.println("Edge: " + board[x1-1][y1]);
            return board[x1-1][y1];
        }
        else if (y1==y2 && x2>x1){
//            System.out.println("edge :" +board[x2-1][y1]);
            return board[x2-1][y1];
        }
        return -1;

}
    private void printGraph(){
        for(int i=0;i<boardSize;i++){
            for (int j = 0; j <boardSize ; j++) {
                if((i%2==1 && j%2==0 )|| (i%2==0 && j%2==1)){
                    System.out.println(i+ "," + j + " " +graph.get(new Coordinate(i,j)));
                }

            }
        }


    }

    /**
     * Helper method to get the neighbor
     * @param co
     * @return list of neighbors
     */

    private ArrayList<Coordinate> getNeighbors(Coordinate co){
        ArrayList neighbors=new ArrayList();
        int r ;
        int c ;
        r = co.getRow();
             c = co.getCol();


//        System.out.println("c :" +c + " j: " + j);
        if (r > 0 && c-2 > 0 && r < boardSize-1 && c < boardSize-1){//(c >2 && r > 0 && r<boardSize) {
//                 System.out.println("left");
            neighbors.add(new Coordinate(r, c - 2));
        }
        //r-1 > 0 && c > 0 && r < boardSize-1 && c < boardSize-1 && board[r-1][c] == i
        if (r-2 > 0 && c > 0 && r < boardSize-1 && c < boardSize-1){//(r >= 2 && c>0 && c<boardSize) {
//                 System.out.println("top");
            neighbors.add(new Coordinate(r - 2, c));

        }
        if (r > 0 && c <= boardSize - 3  ) {
//                 System.out.println("right");
            neighbors.add(new Coordinate(r, c + 2));
        }
        if (r <= boardSize - 3 && c >= 0 ) {//&& j<boardSize-2
//                 System.out.println("bottom");
            neighbors.add(new Coordinate(r + 2, c));
//                 System.out.println(islandList);

        }
//        }
//        System.out.println("neighbors of co " +co + " " +neighbors);

        return neighbors;
    }


//    private ArrayList<Coordinate> getNeighbors(Coordinate co){
//        ArrayList neighbors=new ArrayList();
//        int c=co.getRow();
//        int j=co.getCol();
////        if((c==0 && j==0) || (c==boardSize-1 && j==0) || (c==boardSize-1 && j==boardSize-1) ||(c==0 && j==boardSize-1)) {
//
//     System.out.println("c :" +c + " j: " + j);
//            if (j > 0 && c > 0) {
////                 System.out.println("left");
//                neighbors.add(new Coordinate(c, j - 1));
//            }
//            if (c > 0 && j>0) {
////                 System.out.println("top");
//                neighbors.add(new Coordinate(c - 1, j));
//
//            }
//            if (c > 0 && j < dim - 1) {
////                 System.out.println("right");
//                neighbors.add(new Coordinate(c, j + 1));
//            }
//            if (c < dim - 1 && j > 0) {
////                 System.out.println("bottom");
//                neighbors.add(new Coordinate(c + 1, j));
////                 System.out.println(islandList);
//
//            }
////        }
//        System.out.println("neighbors of co " +co + " " +neighbors);
//
//        return neighbors;
//    }

    /**
     * Dijkstra's algorithm
     * @param startNode
     * @param i
     * @param predecessors
     */

    private void dijkstra(Coordinate startNode, int i,
                          Map<Coordinate, Coordinate> predecessors) {
        // initialize distances - we will use Integer.MAX_VALUE to
        // represent infinity
//        createDistanceMap();
        //initialize start nodes

        graph.put(startNode,0);
        // initialize predecessors - by not yet including any other nodes,
        // they are unvisited and have no predecessor.  Source node is
        // given predecessor of itself.
        predecessors.put(startNode,startNode);
        // our priority queue will just be a list that we search to extract
        // the minimum from at each step (O(n))
        List<Coordinate> priorityQ = new LinkedList<Coordinate>();
        for (Coordinate c:graph.keySet()
             ) {
            priorityQ.add(c);

        }

        while(!priorityQ.isEmpty()){
            Coordinate c=dequeueMin(priorityQ,graph);
            // return if this node still has distance "infinity" -
            // remaining nodes are inaccessible
            if(graph.get(c) == Integer.MAX_VALUE) {
                return;
            }
            // this loop allows neighbors that have already been finalized
            // to be checked again, but they will never be updated and
            // this doesn't affect overall complexity
            for(Coordinate e : getNeighbors(c)) {
//                if(!predecessors.containsKey(e)){
                Integer w =Integer.MAX_VALUE;
                if( getEdge(e,c)==i){
                    w=0;
                }
                else if(getEdge(e,c)==0){
                    w=1;
                }
//                Coordinate n = e.getToNode();
                // relaxation
                Integer distViaU=0;
                if(w==Integer.MAX_VALUE) {
                     distViaU=w;
                }
                else {
                     distViaU = graph.get(c) + w;
                }
                if(graph.get(e) > distViaU ) {
//                    System.out.println("Adding " + e +graph.get(e)+" "+ " dist" + distViaU);
                    graph.put(e,  distViaU);
                    predecessors.put(e,  c);
                }
            }
        }



    }

    /*
 * Basic implementation of a priority queue that searches for the minimum.
 */
    private Coordinate dequeueMin(List<Coordinate> priorityQ, Map<Coordinate, Integer> distance) {

        Coordinate minNode = priorityQ.get(0);  // start off with first one
        for (Coordinate n : priorityQ) { // checks first one again...
            if(graph.get(n) < graph.get(minNode)) {
                minNode = n;
            }
        }
        return priorityQ.remove(priorityQ.indexOf(minNode));
    }

    }
