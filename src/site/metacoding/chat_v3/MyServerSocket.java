package site.metacoding.chat_v3;

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
        boolean isLogin;
        String username;

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

        // 메시지를 받을 때 실행
        // 전체 채팅 메서드 ( ALL : 내용 )
        public void chatPublic(String msg) {
            try {
                for (고객전담스레드 t : 고객리스트) { // 왼쪽 = 컬렉션타입 : 오른쪽 = 컬렉션
                    if (t != this) { // 본인의 메시지는 반환하지 않기위해.
                        t.writer.write(username + " : " + msg + "\n");
                        t.writer.flush();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        // 귓속말 메서드 ( CHAT : 아이디 : 내용 )
        public void chatPrivate(String receiver, String msg) {
            try {
                for (고객전담스레드 t : 고객리스트) { // 왼쪽 = 컬렉션타입 : 오른쪽 = 컬렉션
                    if (t.username.equals(receiver)) {// 고객전담메서드
                        t.writer.write("[귓속말]" + username + " : " + msg + "\n");
                        t.writer.flush();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 재원 프로토콜 검사기(파싱)
        public void jwp(String inputData) {
            // 1. 프로토콜 분리하기
            // inputData의 내용을 : 기준으로 구분하여 배열한다.
            String[] token = inputData.split(":");
            String protocol = token[0]; // token의 0번지가 프로토콜이다.
            if (protocol.equals("ALL")) { // 프로토콜의 데이터가 ALL 이면
                String msg = token[1]; // token의 1번지를 msg에 넣고
                chatPublic(msg); // 전체채팅 메서드를 호출한다.
            } else if (protocol.equals("CHAT")) { // 프로토콜 데이터가 CHAT이면
                String receiver = token[1];// token의 1번지를 username에 넣고
                String msg = token[2];// token의 2번지를 msg에 넣고
                chatPrivate(receiver, msg); // 귓속말 메서드를 호출한다.
            } else {
                System.out.println("프로토콜 없음");
            }
        }

        // 버퍼
        @Override
        public void run() {
            // ID 만들기. Thread가 while 돌기 전에 !!
            // 클라이언트가 최초로 보낸 메시지가 username이다.
            try {
                username = reader.readLine();
                isLogin = true;
            } catch (Exception e) {
                isLogin = false;
                System.out.println("username을 받지 못했습니다.");
            }

            // MianThread 실행
            while (true) {
                try {
                    // 메시지 받는 부분
                    String inputData = reader.readLine();
                    // 메시지 받았으니까 List<고객전담스레드> 고객리스트 에 담긴
                    // 모든 클라이언트에게 메시지 전송 (for문 사용)
                    // for (int i = 0; 고객리스트.size(); i++) {
                    // 고객리스트.get(i).writer.write(inputData + "\n");
                    // 고객리스트.get(i).writer.flush();
                    // }

                    // 메시지 보내는 부분
                    // for each : collection의 크기만큼만 돌아가는 for문
                    jwp(inputData);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

    }

    public static void main(String[] args) {
        new MyServerSocket();
    }
}
