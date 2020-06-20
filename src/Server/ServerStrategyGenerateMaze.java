package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.*;
import java.io.*;
import java.util.Properties;

/**
 * Server gets int[] size 2 that represents the rows and cols numbers wanted in the maze
 * that will be generated for the client.
 * Server generates the maze and compresses it with MyCompressorOutputStream and sends back
 * to the client byte[] that represents the maze.
 */

public class ServerStrategyGenerateMaze implements IServerStrategy {

    @Override
    public void serverStrategy(InputStream inputStream, OutputStream outputStream) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inputStream);
            ObjectOutputStream toClient = new ObjectOutputStream(outputStream);
            toClient.flush();
            int mazeSizes[] = (int[]) fromClient.readObject();
            int rowsNum = mazeSizes[0];
            int colsNum = mazeSizes[1];
            Properties prop = Server.Configurations.loadConfig();
            IMazeGenerator mazeGenerator = Server.Configurations.getMazeGenerator(prop);
            Maze maze = mazeGenerator.generate(rowsNum, colsNum);
            ByteArrayOutputStream byteArrOutputStream = new ByteArrayOutputStream();
            MyCompressorOutputStream compressOut = new MyCompressorOutputStream(byteArrOutputStream);
            compressOut.write(maze.toByteArray());
            compressOut.flush();
            toClient.writeObject(byteArrOutputStream.toByteArray());
            toClient.flush();
        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
