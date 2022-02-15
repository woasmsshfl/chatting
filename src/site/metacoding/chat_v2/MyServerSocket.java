package site.metacoding.chat_v2;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServerSocket {

    // 리스너(연결받기는 1개만 있어도 된다 = Main Thread)
    ServerSocket serverSocket;
    List<고객전담스레드> 고객리스트;

    // 서버는 메시지 받아서 보낸다. (클라이언트 수 마다)

    public MyServerSocket() {
        try {
            serverSocket = new ServerSocket(2000);

            고객리스트 = new ArrayList<>();

            while (true) {
                Socket socket = serverSocket.accept(); // MainThread
                System.out.println("클라이언트 연결됨. 소켓 생성");

                고객전담스레드 t = new 고객전담스레드(socket);
                고객리스트.add(t); // while이 끝나기전에 컬렉션에 담아야 한다.
                System.out.println("고객리스트의 크기 : " + 고객리스트.size());
                new Thread(t).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 내부클래스
    class 고객전담스레드 implements Runnable {

        Socket socket;

        public 고객전담스레드(Socket socket) {
            this.socket = socket;
        }
        // 버퍼

        @Override
        public void run() {

        }

    }

    public static void main(String[] args) {
        new MyServerSocket();
    }
}
