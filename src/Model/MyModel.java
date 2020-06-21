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

    public void startServers(){
        this.serverMazeGenerator = new Server(5400, 1000, (IServerStrategy)new ServerStrategyGenerateMaze());
        this.serverSolveMaze = new Server(5401, 1000, (IServerStrategy)new ServerStrategySolveSearchProblem());
        this.serverMazeGenerator.start();
        this.serverSolveMaze.start();

    }

    public void stopServers(){
        this.serverMazeGenerator.stop();
        this.serverSolveMaze.stop();
    }

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
                rowCharInd--;
                break;
            case 4: //Right
                //  if(colChar!=maze[0].length-1)
                rowCharInd++;
                break;

        }

        setChanged();
        notifyObservers();
    }

    public int getRowChar() {
        return rowCharInd;
    }

    public int getColChar() {
        return rowCharInd;
    }

    @Override
    public void assignObserver(Observer o) {
        this.addObserver(o);
    }

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
    public void getSolution() {
        //return this.solution;

    }

    public Maze getMaze() {
        return maze;
    }

    public int[][] getArrMaze(){return maze.getArrMaze();}
}
