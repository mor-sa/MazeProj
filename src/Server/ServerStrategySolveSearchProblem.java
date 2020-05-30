package Server;

import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.search.ISearchable;
import algorithms.search.ISearchingAlgorithm;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;

import java.io.*;
import java.util.Properties;

/**
 * Server gets from client Maze object that represents a maze, solves it and return to the client
 * Solution object that has the maze solution.
 */

public class ServerStrategySolveSearchProblem implements IServerStrategy {

    @Override
    public void serverStrategy(InputStream inputStream, OutputStream outputStream) {
        try {
            ObjectOutputStream toClient = new ObjectOutputStream(outputStream);
            toClient.flush();
            ObjectInputStream fromClient = new ObjectInputStream(inputStream);
            // Gets the temp directory path
            String tempDirectoryPath = System.getProperty("java.io.tmpdir");
            ISearchable searchable = new SearchableMaze((Maze)fromClient.readObject());
            int mazeHash = searchable.hashCode();
            // Create a string that holds this exact maze got from client
            String mazeTempPath = String.format("%s\\%s", tempDirectoryPath, mazeHash);
            // Check if this exact maze already has solution
            Solution MazeSol;
            if (new File(mazeTempPath).isFile()){
                FileInputStream fileInputMazeSol = new FileInputStream(new File(mazeTempPath));
                ObjectInputStream objInputMazeSol = new ObjectInputStream(fileInputMazeSol);
                MazeSol = (Solution)objInputMazeSol.readObject();
                toClient.writeObject(MazeSol);
                toClient.flush();
                toClient.close();
            }
            else{
                Properties prop = Server.Configurations.loadConfig();
                ISearchingAlgorithm searchingAlgorithm = Server.Configurations.getSearchingAlgorithm(prop);
                MazeSol = searchingAlgorithm.solve(searchable);
                File mazeSolFile = new File(mazeTempPath);
                FileOutputStream fileOutputMazeSol = new FileOutputStream(mazeSolFile);
                ObjectOutputStream objOutputMazeSol = new ObjectOutputStream(fileOutputMazeSol);
                objOutputMazeSol.writeObject(MazeSol);
                objOutputMazeSol.flush();
                toClient.writeObject(MazeSol);
                toClient.flush();
                objOutputMazeSol.close();
                toClient.close();
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
