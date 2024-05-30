package skype.client;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import skype.ImgImpl;

public class ChattingFrame extends JFrame implements ImgImpl {

	// field
	private Client mContext; // 클라이언트 생성
	private JPanel mainPanel; // 기본 패널
	private JTabbedPane tabPane; // 탭 바
	private JButton friends; // 친구 탭
	private JButton makeRoom; // 방 만들기 탭
	private JButton logOut; // 로그아웃 탭
	private FriendsPanel friendsPanel; // 친구 패널
	private JPanel p2; // 방 만들기 패널
	private JPanel p3; // 로그아웃 패널

	// Constructor
	public ChattingFrame(Client mContext) {
		this.mContext = mContext;
		initData();
		setInitLayout();
		addEventListener();
	}

	// methods
	private void initData() {
		setTitle("skype");
		setSize(1000, 700);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// favicon
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.getImage(faviconImg);
		setIconImage(img);

		mainPanel = new JPanel(); // TODO
		friendsPanel = new FriendsPanel(this);
		p2 = new JPanel();
		p3 = new JPanel();
		tabPane = new JTabbedPane(JTabbedPane.LEFT);

		friends = new JButton(new ImageIcon(friendsIcon));
		friends.setBounds(17, 35, 40, 40);
		friends.setBorderPainted(false); // 버튼 테두리 없애기
		friends.setContentAreaFilled(false); // 버튼 배경 투명
		makeRoom = new JButton(new ImageIcon(makeRoomIcon));
		makeRoom.setBounds(15, 140, 40, 40);
		makeRoom.setBorderPainted(false); // 버튼 테두리 없애기
		makeRoom.setContentAreaFilled(false); // 버튼 배경 투명
		logOut = new JButton(new ImageIcon(logOutIcon));
		logOut.setBounds(20, 530, 40, 40);
		logOut.setBorderPainted(false); // 버튼 테두리 없애기
		logOut.setContentAreaFilled(false); // 버튼 배경 투명
	}

	private void setInitLayout() {
		setVisible(false);
		setLocationRelativeTo(null);

		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainPanel.setLayout(null);
		setContentPane(mainPanel);

		tabPane.setBounds(0, 0, getWidth(), getHeight());
		mainPanel.add(tabPane);

		friendsPanel.setLayout(null);

		tabPane.addTab("Friends", null, friendsPanel, null);
		tabPane.addTab("Make Room", null, p2, null);
		tabPane.addTab("Log Out", null, p3, null);
	}

	private void addEventListener() {

	}

	// getter, setter
	public Client getmContext() {
		return mContext;
	}

	public void setmContext(Client mContext) {
		this.mContext = mContext;
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

	public void setMainPanel(JPanel mainPanel) {
		this.mainPanel = mainPanel;
	}

	public JTabbedPane getTabPane() {
		return tabPane;
	}

	public void setTabPane(JTabbedPane tabPane) {
		this.tabPane = tabPane;
	}

	public JButton getFriends() {
		return friends;
	}

	public void setFriends(JButton friends) {
		this.friends = friends;
	}

	public JButton getMakeRoom() {
		return makeRoom;
	}

	public void setMakeRoom(JButton makeRoom) {
		this.makeRoom = makeRoom;
	}

	public JButton getLogOut() {
		return logOut;
	}

	public void setLogOut(JButton logOut) {
		this.logOut = logOut;
	}

	public FriendsPanel getFriendsPanel() {
		return friendsPanel;
	}

	public void setFriendsPanel(FriendsPanel friendsPanel) {
		this.friendsPanel = friendsPanel;
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

} // end of class
