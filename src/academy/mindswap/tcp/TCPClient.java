package academy.mindswap.tcp;

import academy.mindswap.tcp.utils.Messages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.rmi.UnexpectedException;

public class TCPClient {

    Socket clientSocket;
    PrintWriter out;
    BufferedReader in;
    public static void main(String[] args) {
        TCPClient client = new TCPClient();
        try {
            client.start();
        } catch (UnknownHostException ex) {
            System.err.println(Messages.ERROR_HOST);
        } catch (NumberFormatException ex) {
            System.err.println(Messages.ERROR_PORT);
        } catch (SocketTimeoutException exception) {
            System.err.println(Messages.ERROR_TIMEOUT);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void start() throws IOException,NumberFormatException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print(Messages.HOSTNAME);
        String host = reader.readLine();
        System.out.print(Messages.PORT);
        int port = Integer.parseInt(reader.readLine());

        try {
            openSocket(host,port);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }

        out = new PrintWriter(clientSocket.getOutputStream(),true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        while (!clientSocket.isClosed()){
            System.out.println("Send message");
            String message = reader.readLine();
            if(message.equals("/quit")){
                closeSocket();
                return;
            }
            sendMessage(message);
        }

    }

    private void openSocket(String host, int port) throws IllegalArgumentException,IOException {
        clientSocket = new Socket(host,port);

    }

    private void closeSocket() throws IOException {
        clientSocket.close();
    }
    public void sendMessage(String message) throws IOException {
        out.println(message);
        System.out.println("message sent");
    }

    public void receiveMessage() throws IOException {
        System.out.println(in.readLine());
    }

}
