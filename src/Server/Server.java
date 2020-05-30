package Server;

import algorithms.mazeGenerators.*;
import algorithms.search.BestFirstSearch;
import algorithms.search.BreadthFirstSearch;
import algorithms.search.DepthFirstSearch;
import algorithms.search.ISearchingAlgorithm;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private int port;//The port
    private int listeningInterval;
    private IServerStrategy serverStrategy;//The strategy for handling clients
    private volatile boolean stop = false;
    private ExecutorService threadPool;


    public Server(int port, int listeningInterval, IServerStrategy serverStrategy){
        this.port = port;
        this.listeningInterval = listeningInterval;
        this.serverStrategy = serverStrategy;
    }

    public void start()
    {
        (new Thread(()-> runServer()))
                .start();
    }

    public void runServer(){

        try {
            Properties prop = Configurations.loadConfig();
            int threadsNum = Configurations.getNumOfThreads(prop);
            this.threadPool = Executors.newFixedThreadPool(threadsNum);
            ServerSocket serverSocket = new ServerSocket(this.port);
            serverSocket.setSoTimeout(this.listeningInterval);
            while (!stop)
            {
                try {
                    Socket clientSocket = serverSocket.accept();

                    new Thread(() ->{
                        clientHandle(clientSocket);
                    }).start();
                }
                catch (IOException e) {
                    System.out.println("Where are the clients??");
                }
            }
            serverSocket.close();
            this.threadPool.shutdown();
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * This function receives client socket and handles it
     * @param clientSocket - The client socket
     */
    private void clientHandle(Socket clientSocket) {
        try {
            InputStream inFromClient = clientSocket.getInputStream();
            OutputStream outToClient = clientSocket.getOutputStream();
            this.serverStrategy.serverStrategy(inFromClient, outToClient);

            inFromClient.close();
            outToClient.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop()
    {
        System.out.println("The server has stopped!");
        this.stop = true;
    }


    public static class Configurations{
        public static Properties loadConfig() {
            try {
                //set properties
                Properties properties = new Properties();
                OutputStream output = new FileOutputStream("Resources/config.properties");
                properties.setProperty("NumOfThreads" , "4");
                properties.setProperty("MazeGenerator","MyMazeGenerator");
                properties.setProperty("SearchingAlgorithm" , "BestFirstSearch");
                //save properties
                properties.store(output,null);
                InputStream input = new FileInputStream("resources/config.properties");
                properties.load(input);
                input.close();
                return properties;
            }
            catch (IOException e){
                e.printStackTrace();
                return null;
            }
        }

        public static IMazeGenerator getMazeGenerator(Properties prop){
            String mazeGenerator = prop.getProperty("MazeGenerator");
            if (mazeGenerator.equals("MyMazeGenerator")){
                return new MyMazeGenerator();
            }
            else if (mazeGenerator.equals("SimpleMazeGenerator")){
                return new SimpleMazeGenerator();
            }
            else{
                return new EmptyMazeGenerator();
            }
        }

        public static ISearchingAlgorithm getSearchingAlgorithm(Properties prop){
            String searchingAlg = prop.getProperty("SearchingAlgorithm");
            if (searchingAlg.equals("BestFirstSearch")){
                return new BestFirstSearch();
            }
            else if(searchingAlg.equals("BreadthFirstSearch")){
                return new BreadthFirstSearch();
            }
            else{
                return new DepthFirstSearch();
            }
        }

        public static int getNumOfThreads(Properties prop){
            String numofThreads = prop.getProperty("NumOfThreads");
            if (numofThreads.equals("")){
                return 4;
            }
            return Integer.parseInt(numofThreads);
        }

    }
}

