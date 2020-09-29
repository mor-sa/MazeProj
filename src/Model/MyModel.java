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
import java.util.*;

public class MyModel extends Observable implements IModel{
    private Maze maze;
    private ArrayList<Position> SolPath;
    private static Server serverMazeGenerator;
    private static Server serverSolveMaze;
    private Position charPos;
    private Properties prop;

    public MyModel() {
        maze = null;
        charPos = new Position();
        SolPath = new ArrayList<>();
        prop = Server.Configurations.loadConfig();
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
        SolPath = new ArrayList<>();
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
            charPos.setRowIndex(this.maze.getStartPosition().getRowIndex());
            charPos.setColumnIndex(this.maze.getStartPosition().getColumnIndex());

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
            direction = 1 -> Down Left
            direction = 2 -> Down
            direction = 3 -> Down Right
            direction = 4 -> Left
            direction = 6 -> Right
            direction = 7 -> Up Left
            direction = 8 -> Up
            direction = 9 -> Up Right
         */
        List<Position> possibleMoves = maze.getClockNeighbors(charPos);
        Position nextPos = new Position();
        switch(direction)
        {
            case 1: //Down Left
                nextPos.setRowIndex(charPos.getRowIndex() + 1);
                nextPos.setColumnIndex(charPos.getColumnIndex() - 1);
                break;
            case 2: //Down
                nextPos.setRowIndex(charPos.getRowIndex() + 1);
                nextPos.setColumnIndex(charPos.getColumnIndex());
                break;
            case 3: //Down Right
                nextPos.setRowIndex(charPos.getRowIndex() + 1);
                nextPos.setColumnIndex(charPos.getColumnIndex() + 1);
                break;
            case 4: //Left
                nextPos.setRowIndex(charPos.getRowIndex());
                nextPos.setColumnIndex(charPos.getColumnIndex() - 1);
                break;
            case 6: //Right
                nextPos.setRowIndex(charPos.getRowIndex());
                nextPos.setColumnIndex(charPos.getColumnIndex() + 1);
                break;
            case 7: //Up Left
                nextPos.setRowIndex(charPos.getRowIndex() - 1);
                nextPos.setColumnIndex(charPos.getColumnIndex() - 1);
                break;
            case 8: //Up
                nextPos.setRowIndex(charPos.getRowIndex() - 1);
                nextPos.setColumnIndex(charPos.getColumnIndex());
                break;
            case 9: //Up Right
                nextPos.setRowIndex(charPos.getRowIndex() - 1);
                nextPos.setColumnIndex(charPos.getColumnIndex() + 1);
                break;

        }
        for (Position pos:possibleMoves){
            if (pos.equals(nextPos)){
                charPos = pos;
                setChanged();
                notifyObservers();
                break;
            }
        }
    }

    public int getRowChar() {
        return charPos.getRowIndex();
    }

    public int getColChar() {
        return charPos.getColumnIndex();
    }

    @Override
    public void assignObserver(Observer o) {
        this.addObserver(o);
    }

    /**
     * This method solves the maze and saves the solution as ArrayList<Position>
     */
    @Override
    public void solveMaze(int rowStartPos, int colStartPos) {
        //Solving maze
        Position recovery = maze.getStartPosition();
        try {
            maze.setStartPosition(rowStartPos, colStartPos);
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
                        for(int i = 0; i < mazeSolutionSteps.size(); ++i) {
                            String s = mazeSolutionSteps.get(i).toString();
                            String[] temp = s.split(",");
                            int PosRow = Integer.parseInt(temp[0].substring(1));
                            int PosCol = Integer.parseInt(temp[1].substring(0, temp[1].length() - 1));
                            Position curPos = new Position(PosRow, PosCol);
                            MyModel.this.SolPath.add(curPos);
                        }
                    } catch (Exception e) {
                        maze.setStartPosition(recovery.getRowIndex(), recovery.getColumnIndex());
                        e.printStackTrace();
                    }

                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            maze.setStartPosition(recovery.getRowIndex(), recovery.getColumnIndex());
            e.printStackTrace();
        }
        setChanged();
        notifyObservers();
    }


    @Override
    public ArrayList<Position> getSolutionPath() {
        return this.SolPath;
    }

    public Maze getMaze() {
        return maze;
    }

    public int[][] getArrMaze(){return maze.getArrMaze();}

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

    @Override
    public void Load(File file) throws IOException, ClassNotFoundException {
        try {
            FileInputStream in = new FileInputStream(file);
            ObjectInputStream inputStream = new ObjectInputStream(in);
            Maze loadedMaze = (Maze)inputStream.readObject();
            this.charPos.setRowIndex(loadedMaze.getStartPosition().getRowIndex());
            this.charPos.setColumnIndex(loadedMaze.getStartPosition().getColumnIndex());
            this.maze = loadedMaze;
            setChanged();
            notifyObservers();
            inputStream.close();
        }
        catch(IOException | ClassNotFoundException e ){
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

    public String getMazeGenAlg(){
        return prop.getProperty("MazeGenerator");
    }

    public String getMazeSolveAlg() {
        return prop.getProperty("SearchingAlgorithm");
    }

    public String getNumOfThreads() {
        return prop.getProperty("NumOfThreads");
    }

    public void clearSolPath(){ this.SolPath.clear(); }
}
