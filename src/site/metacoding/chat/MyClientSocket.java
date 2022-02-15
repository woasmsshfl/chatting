package site.metacoding.chat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
// import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MyClientSocket {

    Socket socket;
    BufferedWriter writer;
    Scanner sc;
    // PrintWriter pw;

    BufferedReader reader;

    public MyClientSocket() {
        System.out.println("채팅방에 입장하셨습니다.");
        System.out.println("종료 를 입력하면 채팅이 종료됩니다.");
        try {
            socket = new Socket("localhost", 2000); // 이 라인에서MyserverSocket의 socket에 입력됨.
            writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()) // 소켓의 내용을 읽는 버퍼
            );

            sc = new Scanner(System.in);

            reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            // 메시지 받기
            new Thread(() -> {
                try {
                    while (true) {
                        String outputData = reader.readLine();
                        System.out.println("받은 메시지 : " + outputData);

                        if (outputData.equals("종료")) {
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

            // 메시지 보내기
            while (true) {
                String inputData = sc.nextLine();
                // 버퍼에 데이터를 담아야 한다.
                writer.write(inputData + "\n"); // \n : 메시지의 끝임을 알려준다.
                writer.flush(); // 꽉 차지 않은 버퍼를 내려준다.

                if (inputData.equals("종료")) {
                    break;
                }
            }

            // pw = new PrintWriter(socket.getOutputStream(), true);
            // pw.println("안녕");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MyClientSocket();
        System.out.println("채팅이 종료되었습니다.");
    }

}
