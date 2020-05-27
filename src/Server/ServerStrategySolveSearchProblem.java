package Server;

import java.io.*;

/**
 * Server gets from client Maze object that represents a maze, solves it and return to the client
 * Solution object that has the maze solution.
 */

public class ServerStrategySolveSearchProblem implements IServerStrategy {

    @Override
    public void serverStrategy(InputStream inputStream, OutputStream outputStream) {
        BufferedReader fromClient = new BufferedReader(new InputStreamReader(inputStream));
        PrintWriter toClient = new PrintWriter(outputStream);

        String phrase;
        try {
            String reversedPhrase;
            while (!(phrase = fromClient.readLine()).equals("Thanks!")) {
                reversedPhrase = new StringBuilder(phrase).reverse().toString();
                toClient.write(reversedPhrase+"\r\n");
                toClient.flush();
            }
            toClient.write("you welcome, bye!\r\n");
            toClient.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
