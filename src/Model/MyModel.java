package Model;

import Client.*;
import IO.MyDecompressorInputStream;
import Server.*;
import algorithms.mazeGenerators.*;
import algorithms.search.AState;
import algorithms.search.Solution;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class MyModel extends Observable implements IModel{
    private Maze maze;
    private ArrayList<Position> SolPath = new ArrayList<>();
    private static Server serverMazeGenerator;
    private static Server serverSolveMaze;
    private int rowCharInd;
    private int colCharInd;


    public MyModel() {
        maze = null;
        rowCharInd = 0;
        colCharInd = 0;

    }

    /**
     * Starting the servers
     */
    public void startServers(){
        this.serverMazeGenerator = new Server(5400, 1000, (IServerStrategy)new ServerStrategyGenerateMaze());
        this.serverSolveMaze = new Server(5401, 1000, (IServerStrategy)new ServerStrategySolveSearchProblem());
        this.serverMazeGenerator.start();
        this.serverSolveMaze.start();

    }

    /**
     * Stopping the servers
     */
    public void stopServers(){
        this.serverMazeGenerator.stop();
        this.serverSolveMaze.stop();
    }

    /**
     * This method will generate a new maze by using the Communicate with server method from Client.
     * We first define the client.
     * @param row - number of rows in the new maze
     * @param col - number of cols in the new maze
     */
    public void generateMaze(final int row, final int col){
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        int[] mazeDimensions = new int[]{row, col};
                        toServer.writeObject(mazeDimensions);
                        toServer.flush();
                        byte[] compressedMaze = (byte[])fromServer.readObject();
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[12 + (row+2) * (col+2)];
                        is.read(decompressedMaze);
                        MyModel.this.maze = new Maze(decompressedMaze);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
            client.communicateWithServer();
            colCharInd = this.maze.getStartPosition().getColumnIndex();
            rowCharInd = this.maze.getStartPosition().getRowIndex();

        } catch (UnknownHostException var1) {
            var1.printStackTrace();
        }
        setChanged();
        notifyObservers();
    }

    /**
     * This method gets an int that represents direction, and then changes the character's location accordingly.
     * @param direction - which direction the character went and needs to be updated
     */
    public void updateCharacterLocation(int direction)
    {
        /*
            direction = 1 -> Up
            direction = 2 -> Down
            direction = 3 -> Left
            direction = 4 -> Right
         */

        switch(direction)
        {
            case 1: //Up
                //if(rowChar!=0)
                rowCharInd--;
                break;

            case 2: //Down
                //  if(rowChar!=maze.length-1)
                rowCharInd++;
                break;
            case 3: //Left
                //  if(colChar!=0)
                colCharInd--;
                break;
            case 4: //Right
                //  if(colChar!=maze[0].length-1)
                colCharInd++;
                break;

        }

        setChanged();
        notifyObservers();
    }

    public int getRowChar() {
        return rowCharInd;
    }

    public int getColChar() {
        return colCharInd;
    }

    @Override
    public void assignObserver(Observer o) {
        this.addObserver(o);
    }

    /**
     * This method solves the maze and saves the solution as ArrayList<Position>
     */
    @Override
    public void solveMaze() {
        //Solving maze
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        toServer.writeObject(MyModel.this.maze);
                        toServer.flush();
                        Solution mazeSolution = (Solution)fromServer.readObject();
                        MyModel.this.SolPath.clear();
                        ArrayList<AState> mazeSolutionSteps = mazeSolution.getSolutionPath();
                        for(AState state: mazeSolutionSteps){
                            String[] temp = state.getState_str().split(",");
                            int PosRow = Integer.parseInt(temp[0].substring(1));
                            int PosCol = Integer.parseInt(temp[1].substring(0,temp[1].length()-1));
                            Position curPos = new Position(PosRow, PosCol);
                            MyModel.this.SolPath.add(curPos);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        setChanged();
        notifyObservers();
    }

    @Override
    public ArrayList<Position> getSolution() {
        return this.SolPath;
    }

    public Maze getMaze() {
        return maze;
    }

    public int[][] getArrMaze(){return maze.getArrMaze();}

    public Position getStartPosition(){
        return this.maze.getStartPosition();
    }

    public Position getGoalPosition(){
        return this.maze.getGoalPosition();
    }

    /**
     * Method to save the MyModel's maze to a given file.
     * @param file - the maze will be saved on
     */
    public void Save(File file){
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
            out.writeObject(this.maze);
            out.flush();
            out.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * This method will close the servers and exit the program.
     */
    public void exit(){
        this.stopServers();
        System.exit(0);
    }
}
