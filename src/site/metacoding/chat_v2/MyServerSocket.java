package site.metacoding.chat_v2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MyServerSocket {

    // 리스너(연결받기는 1개만 있어도 된다 = Main Thread)
    ServerSocket serverSocket;
    List<고객전담스레드> 고객리스트;

    // 서버는 메시지 받아서 보낸다. (클라이언트 수 마다)

    public MyServerSocket() {
        try {
            serverSocket = new ServerSocket(2000);

            // 고객리스트 = new ArrayList<>(); // ArrayList는 동시접근시 문제가 생긴다. 동기화x
            고객리스트 = new Vector<>(); // vector = 동기화가 된 ArrayList

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
        BufferedReader reader;
        BufferedWriter writer;

        // 통신연결이 끊기고 나서 catch가 while로 돌지 않게 하기 위해서
        boolean isLogin = true;

        public 고객전담스레드(Socket socket) {
            this.socket = socket;

            try {

                reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                writer = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream()));

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        // 버퍼

        @Override
        public void run() {
            while (true) {
                try {
                    String inputData = reader.readLine();
                    System.out.println("From Client : " + inputData);

                    // 메시지 받았으니까 List<고객전담스레드> 고객리스트 에 담긴
                    // 모든 클라이언트에게 메시지 전송 (for문 사용)
                    // for (int i = 0; 고객리스트.size(); i++) {
                    // 고객리스트.get(i).writer.write(inputData + "\n");
                    // 고객리스트.get(i).writer.flush();
                    // }
                    // for each : collection의 크기만큼만 돌아가는 for문
                    for (고객전담스레드 t : 고객리스트) { // 왼쪽 = 컬렉션타입 : 오른쪽 = 컬렉션
                        t.writer.write(inputData + "\n");
                        t.writer.flush();
                    }

                } catch (Exception e) {
                    try {
                        System.out.println("통신실패 내용 : " + e.getMessage());
                        isLogin = false; // while을 빠져나감 -> run() 종료
                        고객리스트.remove(this); // heap에 떠있는 고객전담스레드 없애기
                        reader.close();
                        writer.close();
                        socket.close();
                    } catch (Exception e1) {
                        System.out.println("연결해제 프로세스 실패 내용 : " + e1.getMessage());
                    }
                }

            }
        }

    }

    public static void main(String[] args) {
        new MyServerSocket();
    }
}
