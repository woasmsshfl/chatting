package site.metacoding.chatpractice;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class MyServerSocket {

    ServerSocket serverSocket;
    List<ClientThread> ClientList;

    public MyServerSocket() {

    }

    class ClientThread implements Runnable {

        Socket socket;
        BufferedReader reader;
        BufferedWriter writer;
        boolean isLogin;
        String username;

        public ClientThread(Socket socket) {
            this.socket = socket;

            try {
                reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                writer = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream()));

            } catch (Exception e) {
                e.printStackTrace();
            }
        } // ClientThread END

        @Override
        public void run() {

        }

    }

    public static void main(String[] args) {
        new MyServerSocket();
    }

}