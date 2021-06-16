package academy.mindswap.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

    private int PORT;
    ServerSocket serverSocket;
    Socket clientSocket;
    PrintWriter out;
    BufferedReader in;
    public TCPServer(int PORT){
        this.PORT=PORT;
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(PORT);
        System.out.println("Starting server");

        while (serverSocket.isBound()){
            System.out.println("Waiting for connections");
            clientSocket=serverSocket.accept();//blocking method
            out = new PrintWriter(clientSocket.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("Connected");
            System.out.println("Client IP: " + clientSocket.getInetAddress());
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);

            }

            //System.out.println(clientSocket.isClosed());
            closeClient();
            //System.out.println(clientSocket.isConnected());

           // clientSocket.isConnected();
            //if(clientSocket.isClosed()){

            //}
        }

    }

    private void closeClient() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        System.out.println("Client closed connection");
    }
    public static void main(String[] args) {

        TCPServer server = new TCPServer(8080);
        try{
            server.start();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }



    }
}
