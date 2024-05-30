package skype.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import skype.ProtocallImpl;

public class Server {

	// field
	// 접속한 유저 벡터
	private Vector<ConnectedUser> connectedUsers = new Vector<>();
	// 프레임
	private ServerFrame serverFrame; // 서버 레이아웃
	private JTextArea mainBoard; // 서버 레이아웃에 메인보드
	// 서버 소켓
	private ServerSocket serverSocket; // 서버 소켓
	private Socket socket; // 소켓
	private int socketNum; // 소켓 번호
	// 파일 저장
	private FileWriter fileWriter;
	// while문 flag
	private boolean flag; // 클라이언트 대기 while문

	private String protocol;
	private String from;
	private String message;

	// Constructor
	public Server() {
		serverFrame = new ServerFrame(this);
		mainBoard = serverFrame.getMainBoard();
		System.out.println("Server 클래스 생성");
	}

	// methods
	/**
	 * 원하는 포트 번호로 서버 실행.
	 */
	public void startServer() {
		try {
			serverSocket = new ServerSocket(socketNum);
			serverViewAppendWriter("[서버 생성] " + socketNum + "번 서버 생성\n");
			serverFrame.getStartServer().setEnabled(false);
			serverFrame.getCloseServer().setEnabled(true);
			setFlag(true);
			connectClient();
		} catch (BindException e) {
			JOptionPane.showMessageDialog(null, "이미 실행 중인 서버입니다.", "접속 에러!", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 서버 종료
	public void closeServer() {
		try {
//			socket.close();
			serverSocket.close();
			setFlag(false);
			mainBoard.setText("");
			serverViewAppendWriter("[알림] " + socketNum + "번 서버 종료\n");
			serverFrame.getStartServer().setEnabled(true);
			serverFrame.getCloseServer().setEnabled(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 서버가 소켓 열고 유저 소켓 연결 대기 유저가 연결되면 스레드 실행됨.
	 */
	private void connectClient() {
		new Thread(() -> {
			while (flag) {
				try {
					serverViewAppendWriter("[접속 대기] 사용자 접속 대기중\n");
					socket = serverSocket.accept();

					// 연결을 대기하다가 유저가 들어오면 유저 생성
					ConnectedUser user = new ConnectedUser(socket);
					user.start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * 접속한 전체 유저에게 출력
	 * 
	 * @param msg
	 */
	private void broadCast(String msg) {
		for (int i = 0; i < connectedUsers.size(); i++) {
			ConnectedUser user = connectedUsers.elementAt(i);
			user.writer(msg);
			System.out.println("브로드캐스트 작동함" + msg);
		}
	}

	/**
	 * 서버로 들어오는 요청 저장되는 파일 writer
	 */
	private void serverViewAppendWriter(String str) {
		try {
			fileWriter = new FileWriter("skype_server_log.txt", true);
			mainBoard.append(str);
			fileWriter.write(str);
			fileWriter.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 유저 소켓 연결되면 실행되는 내부 클래스
	 */
	private class ConnectedUser extends Thread implements ProtocallImpl {

		private Socket socket; // 소켓
		private BufferedReader reader; // 읽어들이는거
		private PrintWriter writer; // 출력하는거
		private String name; // 유저 이름

		public ConnectedUser(Socket socket) {
			this.socket = socket;
			connectIO();
		}

		// 입출력 장치
		private void connectIO() {
			try {
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writer = new PrintWriter(socket.getOutputStream(), true);
				sendInfomation();
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "서버 입출력 장치 에러!", "알림", JOptionPane.ERROR_MESSAGE);
				serverViewAppendWriter("[에러] 서버 입출력 장치 에러! \n");
			}
		}

		/**
		 * 입장한 유저 명단 업데이트와 접속되어 있는 다른 유저에게 입장한 유저 알림
		 */
		private void sendInfomation() {
			try {
				// TODO - 유저 이름 가져오기
				name = reader.readLine();
				serverViewAppendWriter("[접속] " + name + "님 접속 완료\n");
				// 접속된 유저들에게 유저 명단 업데이트를 위한 출력
				newUser();
				// 방금 연결된 유저측에서 유저 명단 업데이트를 위한 출력
				connectedUser();
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "접속 에러 !", "알림", JOptionPane.ERROR_MESSAGE);
				serverViewAppendWriter("[에러] " + name + "님이 나갔습니다.\n");
			}
		}

		@Override
		public void run() {
			try {
				while (true) {
					String str = reader.readLine();
					checkProtocall(str);
					System.out.println("작동함");
				}
			} catch (Exception e) {
				serverViewAppendWriter("[유저 접속 끊김] " + name + "님이 나갔습니다.\n");
				connectedUsers.remove(this);
				broadCast("UserOut/" + name);
			}
		}

		private void checkProtocall(String str) {
			StringTokenizer tokenizer = new StringTokenizer(str, "/");

			protocol = tokenizer.nextToken();
			from = tokenizer.nextToken();

			if (protocol.equals("Chatting")) {
				message = tokenizer.nextToken();
				chatting();
			} else if (protocol.equals("SecretMessage")) {
				message = tokenizer.nextToken();
				secretMessage();
			}
//			else if (protocol.equals("MakeRoom")) {
//				makeRoom();
//
//			} else if (protocol.equals("OutRoom")) {
//				outRoom();
//
//			} else if (protocol.equals("EnterRoom")) {
//				enterRoom();
//			}
		}

		/**
		 * Client로 보내는 응답
		 */
		private void writer(String str) {
			writer.write(str + "\n");
			writer.flush();
		}

		/**
		 * Protocall interface
		 */
		public void newUser() {
			connectedUsers.add(this);
			broadCast("NewUser/" + name);
		}

		public void connectedUser() {
			for (int i = 0; i < connectedUsers.size(); i++) {
				ConnectedUser user = connectedUsers.elementAt(i);
				writer("ConnectedUser/" + user.name);
			}
		}

		@Override
		public void chatting() {
			serverViewAppendWriter("[메세지] " + from + "_" + message + "\n");
			// TODO Auto-generated method stub
		}

		@Override
		public void secretMessage() {
			serverViewAppendWriter("[비밀 메세지] " + name + "ㅡ>" + from + " : " + message + "\n");
			for (int i = 0; i < connectedUsers.size(); i++) {
				ConnectedUser user = connectedUsers.elementAt(i);

				if (user.name.equals(from)) {
					user.writer("SecretMessage/" + name + "/" + message);
				}
			}
			// TODO Auto-generated method stub
		}

	} // end of ConnectedUser class

	// getter, setter
	public int getSocketNum() {
		return socketNum;
	}

	public void setSocketNum(int socketNum) {
		this.socketNum = socketNum;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

}
