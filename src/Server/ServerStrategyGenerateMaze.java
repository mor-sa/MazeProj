package Server;

import java.io.*;

/**
 * Server gets int[] size 2 that represents the rows and cols numbers wanted in the maze
 * that will be generated for the client.
 * Server generates the maze and compresses it with MyCompressorOutputStream and sends back
 * to the client byte[] that represents the maze.
 */

public class ServerStrategyGenerateMaze implements IServerStrategy {

    @Override
    public void serverStrategy(InputStream inputStream, OutputStream outputStream) {




//        BufferedReader fromClient = new BufferedReader(new InputStreamReader(inputStream));
//        PrintWriter toClient = new PrintWriter(outputStream);
//
//        String phrase;
//        try {
//            String reversedPhrase;
//            while (!(phrase = fromClient.readLine()).equals("Thanks!")) {
//                reversedPhrase = new StringBuilder(phrase).reverse().toString();
//                toClient.write(reversedPhrase+"\r\n");
//                toClient.flush();
//            }
//            toClient.write("you welcome, bye!\r\n");
//            toClient.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
