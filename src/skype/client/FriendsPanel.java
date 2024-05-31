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
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import skype.ImgImpl;

public class FriendsPanel extends JPanel implements ImgImpl {

	// field
	private ChattingFrame chattingFrame;
	private JPanel userListPanel; // 유저 리스트 패널
	private JList<String> userList; // 유저 리스트
	private JPanel p2; // 채팅창 패널
	private JPanel p3; // 입력칸 패널
	private JTextArea mainMsgBox; // 채팅창
	private ScrollPane scrollPane; // 채팅창 스크롤
	private JTextField writeMsgBox; // 입력칸
	private JButton sendBtn; // 보내기 버튼
	// TODO
	private Vector<String> userNameVector = new Vector<>();

	// Constructor
	public FriendsPanel(ChattingFrame chattingFrame) {
		this.chattingFrame = chattingFrame;
		initData();
		setInitLayout();
		addEventListener();
	}

	// methods
	private void initData() {
		userListPanel = new JPanel();
		userList = new JList<>();

		p2 = new JPanel();
		p3 = new JPanel();

		mainMsgBox = new JTextArea();
		scrollPane = new ScrollPane();
		writeMsgBox = new JTextField(17);
		sendBtn = new JButton("전송");
	}

	private void setInitLayout() {
		setSize(getWidth(), getHeight());
		setLayout(null);

		userListPanel.setBounds(0, 0, 260, 658);
		userListPanel.setBackground(Color.WHITE);
		userListPanel.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 1), "user List"));

		p2.setBounds(260, 0, 630, 600);
		p3.setBounds(260, 610, 630, 43);

		mainMsgBox = new JTextArea();
		mainMsgBox.setBounds(110, 10, 600, 590);
		mainMsgBox.setEnabled(false);
		scrollPane = new ScrollPane();
		scrollPane.setBounds(110, 10, 600, 590);
		scrollPane.add(mainMsgBox);

		p2.add(scrollPane);
		p3.add(writeMsgBox);
		p3.add(sendBtn);

		add(p2);
		add(p3);

		userListPanel.add(userList);
		add(userListPanel);
	}

	private void addEventListener() {

		sendBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sendMsg();
			}
		});

		writeMsgBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					sendMsg();
				}
			}
		});
	}

	private void sendMsg() {
		if (!writeMsgBox.getText().equals("")) {
			String msg = writeMsgBox.getText();
			chattingFrame.getmContext().clickSendSecretMessageBtn(msg);
			writeMsgBox.setText("");
			writeMsgBox.requestFocus();
		}
	}

	// getter, setter
	public JPanel getUserListPanel() {
		return userListPanel;
	}

	public void setUserListPanel(JPanel userListPanel) {
		this.userListPanel = userListPanel;
	}

	public JList<String> getUserList() {
		return userList;
	}

	public void setUserList(JList<String> userList) {
		this.userList = userList;
	}

	public JPanel getP2() {
		return p2;
	}

	public void setP2(JPanel p2) {
		this.p2 = p2;
	}

	public JPanel getP3() {
		return p3;
	}

	public void setP3(JPanel p3) {
		this.p3 = p3;
	}

	public JTextArea getMainMsgBox() {
		return mainMsgBox;
	}

	public void setMainMsgBox(JTextArea mainMsgBox) {
		this.mainMsgBox = mainMsgBox;
	}

	public ScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(ScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	public JTextField getWriteMsgBox() {
		return writeMsgBox;
	}

	public void setWriteMsgBox(JTextField writeMsgBox) {
		this.writeMsgBox = writeMsgBox;
	}

	public JButton getSendBtn() {
		return sendBtn;
	}

	public void setSendBtn(JButton sendBtn) {
		this.sendBtn = sendBtn;
	}

	public Vector<String> getUserNameVector() {
		return userNameVector;
	}

	public void setUserNameVector(Vector<String> userNameVector) {
		this.userNameVector = userNameVector;
	}

} // end of class
