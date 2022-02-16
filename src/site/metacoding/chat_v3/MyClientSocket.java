package site.metacoding.chat_v3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class MyClientSocket {

    Socket socket;
    String username;
    // 읽기 할 쓰레드
    BufferedReader reader;

    // 키보드로부터 받아서 쓰기 할 쓰레드
    BufferedWriter writer;
    Scanner sc;

    public MyClientSocket() {
        try {
            // 소켓 연결
            socket = new Socket("localhost", 2000);

            // 쓰기 연결
            sc = new Scanner(System.in);
            writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));

            // 읽기 연결
            reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            // 새로운 스레드 (읽기 전용) - 내부클래스 하나 만들자.
            new Thread(new 읽기전담스레드()).start();

            // 최초 아이디(username) 서버로 전송하는 프로토콜
            System.out.println("아이디를 입력하세요.");
            username = sc.nextLine();
            writer.write(username + "\n");
            writer.flush();
            System.out.println(username + "이 서버로 전송되었습니다.");

            // 메인 스레드 (쓰기 전용) -따로 만들 필요는 없다. Main Thread를 사용하면 되니까
            while (true) {
                String keyboardInputData = sc.nextLine(); // 키보드로부터 데이터를 받는다.
                writer.write(keyboardInputData + "\n"); // 내 버퍼에 담는다.
                writer.flush(); // 버퍼에 담긴 데이터를 stream으로 보내고 버퍼를 비운다.
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class 읽기전담스레드 implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {
                    String inputData = reader.readLine();
                    System.out.println(inputData);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        new MyClientSocket();
    }
}
