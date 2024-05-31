package skype.client;

import java.awt.Color;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class MakeRoomPanel extends JPanel {

	private ChattingFrame chattingFrame;
	private JPanel roomListPanel;
	private JPanel p1; // 채팅창 패널
	private JPanel p2; // 채팅창 패널
	private JPanel p3; // 입력칸 패널
	private JTextArea mainMsgBox; // 채팅창
	private ScrollPane scrollPane; // 채팅창 스크롤
	private JTextField writeMsgBox; // 입력칸
	private JButton sendBtn; // 보내기 버튼
	private JList<String> roomList;
	private Vector<String> roomVector = new Vector<>();
	// TODO
	private JButton makeRoomBtn;
	private JButton outRoomBtn;
	private JButton enterRoomBtn;

	public MakeRoomPanel(ChattingFrame chattingFrame) {
		this.chattingFrame = chattingFrame;
		initData();
		setInitLayout();
		addEventListener();
	}

	private void initData() {
		roomListPanel = new JPanel();
		roomList = new JList<>();

		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();

		mainMsgBox = new JTextArea();
		scrollPane = new ScrollPane();
		writeMsgBox = new JTextField(17);
		sendBtn = new JButton("전송");

		makeRoomBtn = new JButton("방 만들기");
		outRoomBtn = new JButton("방 나가기");
		enterRoomBtn = new JButton("방 들어가기");
	}

	private void setInitLayout() {
		setSize(getWidth(), getHeight());
		setLayout(null);

		roomListPanel.setBounds(0, 0, 260, 500);
		roomListPanel.setBackground(Color.WHITE);
		roomListPanel.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 1), "Room List"));

		p1.setBounds(0, 500, 260, 140);
		p2.setBounds(260, 0, 630, 600);
		p3.setBounds(260, 610, 630, 43);

		mainMsgBox = new JTextArea();
		mainMsgBox.setBounds(110, 10, 600, 590);
		mainMsgBox.setEnabled(false);
		scrollPane = new ScrollPane();
		scrollPane.setBounds(110, 10, 600, 590);
		scrollPane.add(mainMsgBox);

		p1.add(makeRoomBtn);
		p1.add(outRoomBtn);
		p1.add(enterRoomBtn);
		p2.add(scrollPane);
		p3.add(writeMsgBox);
		p3.add(sendBtn);

		add(p1);
		add(p2);
		add(p3);

		roomListPanel.add(roomList);
		add(roomListPanel);

	}

	private void addEventListener() {
		
		// 방 만들기 버튼 누르면 methods 실행
		makeRoomBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String roomName = JOptionPane.showInputDialog("[ 방 이름 설정 ]");
				System.out.println("방 이름 담김");
				if (!roomName.equals("")) {
					chattingFrame.getmContext().clickMakeRoomBtn(roomName);
				}
			}
		});
	}

} // end of class
