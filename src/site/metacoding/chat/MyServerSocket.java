package site.metacoding.chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServerSocket {

    // 전역변수는 생성자 외부에 작성한다!
    // OS의 Socket 라이브러리 기반(System call)
    ServerSocket serverSocket; // 리스너 역할의 소켓 // 연결 = 세션 생성
    Socket socket; // 메시지 통신 역할의 소켓
    BufferedReader reader; // 데이터 읽기

    public MyServerSocket() { // 생성자 생성
        try {

            // 1. 서버소켓 생성 (리스너)
            // * well none port (이미 등록되어 있는 포트) 0~1023 개인적으로 사용할 수 없음.
            serverSocket = new ServerSocket(1077); // 내부적으로 While을 가지고 있다. 괄호에 포트번호 입력.
            System.out.println("서버 소켓 생성됨");
            // serverSocket.accept(); // while 실행하면서 대기 // mianThread가 demon이 된것.
            socket = serverSocket.accept(); // 사용중이지 않은 Random port로 socket연결.
            reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()) // 소켓의 내용을 쓰는 버퍼
            );
            String inputData = reader.readLine(); // 버퍼의 내용을 읽어준다.
            System.out.println("받은 메시지 : " + inputData);
            System.out.println("클라이언트 연결됨");

        } catch (Exception e) {
            System.out.println("통신오류발생 : " + e.getMessage());
            // e.printStackTrace();
        }
    }

    public static void main(String[] args) { // 메인 생성
        new MyServerSocket();
        System.out.println("메인 종료");
    }

}
