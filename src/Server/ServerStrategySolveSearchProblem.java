package Server;

import java.io.*;

/**
 * Server gets from client Maze object that represents a maze, solves it and return to the client
 * Solution object that has the maze solution.
 */

public class ServerStrategySolveSearchProblem implements IServerStrategy {

    @Override
    public void serverStrategy(InputStream inputStream, OutputStream outputStream) {
        try {
            ObjectOutputStream toClient = new ObjectOutputStream(outputStream);
            ObjectInputStream fromClient = new ObjectInputStream(inputStream);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
