package site.metacoding.chat;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class MyClientSocket {

    Socket socket;
    BufferedWriter writer;

    public MyClientSocket() {
        try {
            socket = new Socket("localhost", 1077); // 이 라인에서MyserverSocket의 socket에 입력됨.
            writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()) // 소켓의 내용을 읽는 버퍼
            );
            // 버퍼에 데이터를 담아야 한다.
            writer.write("안녕\n"); // \n : 메시지의 끝임을 알려준다.
            writer.flush(); // 꽉 차지 않은 버퍼를 내려준다.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MyClientSocket();
    }

}
