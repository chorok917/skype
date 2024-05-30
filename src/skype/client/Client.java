package skype.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import skype.ProtocallImpl;

public class Client implements ProtocallImpl {

	// field
	// 프레임
	private UserLogin userLogin; // 로그인 프레임
	private ChattingFrame chattingFrame; // 채팅창 입장 프레임
	// 소켓과 I/O
	private Socket socket; // 소켓
	private BufferedReader reader; // 입력받는거
	private PrintWriter writer; // 출력하는거
	// Client에 컴포넌트를 멤버변수로 가져와서 담는 변수
	private JTextArea mainMsgBox;
	private JList<String> userList;
	private JButton sendBtn;
	// 유저 정보
	private String ip; // 유저 ip
	private int port; // 유저 port
	private String name; // 유저 name
	// 접속자 명단 list
	private Vector<String> userNameList = new Vector<>(); // 접속자 명단
	// 토크나이저 사용 변수
	private String protocol;
	private String from;
	private String message;
	

	// Constructor
	public Client() {
		userLogin = new UserLogin(this);
		chattingFrame = new ChattingFrame(this);

		mainMsgBox = chattingFrame.getFriendsPanel().getMainMsgBox();
		userList = chattingFrame.getFriendsPanel().getUserList();
		sendBtn = chattingFrame.getFriendsPanel().getSendBtn();
	}

	// Methods
	/**
	 * UserLogin 프레임에서 로그인을 누르면 Client가 실행된다.
	 */
	public void clickConnectServerBtn(String ip, int port, String name) {
		this.ip = ip;
		this.port = port;
		this.name = name;

		connectNetwork(); // 소켓 연결
		connectIO(); // 입출력 장치 연결

		writer.write(name.trim() + "\n"); // 서버로 이름 전송
		writer.flush();
		chattingFrame.setTitle("skype " + name + "님 환영합니다.");
	}

	/**
	 * 소켓 장치 연결
	 */
	public void connectNetwork() {
		try {
			socket = new Socket(ip, port);
			System.out.println(ip + "," + port + "," + name);
			userLogin.setVisible(false);
			chattingFrame.setVisible(true);

		} catch (ConnectException e) {
			JOptionPane.showMessageDialog(null, "서버를 먼저 실행하세요.", "접속 에러!", JOptionPane.ERROR_MESSAGE);
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "올바르지 않은 IP입니다.", "접속 에러!", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * I/O 연결
	 */
	private void connectIO() {
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream(), true);

			// 입력 스레드
			readThread();
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "클라이언트 입출력 장치 에러 !", "알림", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "클라이언트 입출력 장치 에러 !", "알림", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void readThread() {
		new Thread(() -> {
			while (true) {
				try {
					String msg = reader.readLine();

					checkProtocall(msg);
				} catch (IOException e) {
					e.printStackTrace();
					break;
				}
			}
		}).start();
	}

	/**
	 * 클라이언트 측에서 서버측으로 보내는 Writer 서버측에서 readLine()으로 읽기에 "\n" 엔터가 필요하다.
	 */
	private void writer(String str) {
		writer.write(str + "\n");
		writer.flush();
	}

	private void checkProtocall(String msg) {
		StringTokenizer tokenizer = new StringTokenizer(msg, "/");

		protocol = tokenizer.nextToken();
		from = tokenizer.nextToken();

		if (protocol.equals("Chatting")) {
			message = tokenizer.nextToken();
			chatting();

		} else if (protocol.equals("SecretMessage")) {
			message = tokenizer.nextToken();
			secretMessage();
		}
//		else if (protocol.equals("MakeRoom")) {
//			makeRoom();
//
//		} else if (protocol.equals("MadeRoom")) {
//			madeRoom();
//
//		} else if (protocol.equals("NewRoom")) {
//			newRoom();
//
//		} else if (protocol.equals("OutRoom")) {
//			outRoom();
//
//		} else if (protocol.equals("EnterRoom")) {
//			enterRoom();
//
//		} 
		else if (protocol.equals("NewUser")) {
			newUser();

		} else if (protocol.equals("ConnectedUser")) {
			connectedUser();
		}
//		else if (protocol.equals("EmptyRoom")) {
//			roomNameList.remove(from);
//			roomList.setListData(roomNameList);
//			makeRoomBtn.setEnabled(true);
//			enterRoomBtn.setEnabled(true);
//			outRoomBtn.setEnabled(false);
//		} else if (protocol.equals("FailMakeRoom")) {
//			JOptionPane.showMessageDialog(null, "같은 이름의 방이 존재합니다 !", "[알림]",
//					JOptionPane.ERROR_MESSAGE, icon);
//		} else if (protocol.equals("UserOut")) {
//			userIdList.remove(from);
//			userList.setListData(userIdList);
//		}
	}

	@Override
	public void newUser() {
		if (!from.equals(this.name)) {
			userNameList.add(from);
			userList.setListData(userNameList);
		}
	}

	@Override
	public void connectedUser() {
		userNameList.add(from);
		userList.setListData(userNameList);
	}

	@Override
	public void chatting() {
		if (name.equals(from)) {
			mainMsgBox.append("[나] \n" + message + "\n");
		} else if (from.equals("입장")) {
			mainMsgBox.append("▶" + from + "◀" + message + "\n");
		} else if (from.equals("퇴장")) {
			mainMsgBox.append("▷" + from + "◁" + message + "\n");
		} else {
			mainMsgBox.append("[" + from + "] \n" + message + "\n");
		}
		// TODO Auto-generated method stub
	}

	@Override
	// 서버에서 받아오는거
	public void secretMessage() {
//		if(!name.equals(from)) {
//			chattingFrame.getFriendsPanel().getMainMsgBox().append("[" + from + "]님으로부터 비밀메시지 : " + message + "\n");
//		}
		if (name != from) {
			chattingFrame.getFriendsPanel().getMainMsgBox().append("[" + from + "]님으로부터 비밀메시지 : " + message + "\n");
		}
	}

	// 서버에 보내는 거
	public void clickSendSecretMessageBtn(String msg) {
		String user = (String) chattingFrame.getFriendsPanel().getUserList().getSelectedValue();
		writer("SecretMessage/" + user + "/" + msg);
		chattingFrame.getFriendsPanel().getMainMsgBox().append("[나] -> [" + user + "]" + " : " + msg + "\n");
//		String user = (String) chattingFrame.getFriendsPanel().getUserList().getSelectedValue();
//		if (user == from) {
//			writer("SecretMessage/" + user + "/" + msg);
//			chattingFrame.getFriendsPanel().getMainMsgBox().append("[" + from + "]님께 전달완료" + " : " + msg + "\n");
//		} else if (user == null) {
//			writer("SecretMessage/" + user + "/" + msg);
//			chattingFrame.getFriendsPanel().getMainMsgBox().append("나에게 보내는 메시지 : " + msg + "\n");
//		} else if (user == name) {
//			writer("SecretMessage/" + user + "/" + msg);
//			chattingFrame.getFriendsPanel().getMainMsgBox().append("나에게 보내는 메시지 : " + msg + "\n");
//		}
	}

	/**
	 * Client 화면에서 정보를 받아오는 CallBack interface
	 * 
	 * @param msg
	 */
	public void clickSendMsgBtn(String msg) {
		writer("Chatting/" + msg);
		System.out.println("샌드메시지 메소드 작동");
	}

	// getter, setter
	public UserLogin getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(UserLogin userLogin) {
		this.userLogin = userLogin;
	}

	public ChattingFrame getChattingFrame() {
		return chattingFrame;
	}

	public void setChattingFrame(ChattingFrame chattingFrame) {
		this.chattingFrame = chattingFrame;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public BufferedReader getReader() {
		return reader;
	}

	public void setReader(BufferedReader reader) {
		this.reader = reader;
	}

	public PrintWriter getWriter() {
		return writer;
	}

	public void setWriter(PrintWriter writer) {
		this.writer = writer;
	}

	public JTextArea getMainMsgBox() {
		return mainMsgBox;
	}

	public void setMainMsgBox(JTextArea mainMsgBox) {
		this.mainMsgBox = mainMsgBox;
	}

	public JList<String> getUserList() {
		return userList;
	}

	public void setUserList(JList<String> userList) {
		this.userList = userList;
	}

	public JButton getSendBtn() {
		return sendBtn;
	}

	public void setSendBtn(JButton sendBtn) {
		this.sendBtn = sendBtn;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vector<String> getUserNameList() {
		return userNameList;
	}

	public void setUserNameList(Vector<String> userNameList) {
		this.userNameList = userNameList;
	}

} // end of class
