package skype.server;

import java.awt.Image;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import skype.ImgImpl;

public class ServerFrame extends JFrame implements ImgImpl{


	// field
	private Server mContext;
	private JLabel bg;
	private JTextField inputPort;
	private int socketNum;
	private JTextArea mainBoard;
	private ScrollPane scrollPane;
	private JButton startServer;
	private JButton closeServer;

	// Constructor
	public ServerFrame(Server mContext) {
		System.out.println("서버프레임 생성");
		this.mContext = mContext;
		initData();
		setInitLayout();
		addEventListener();
	}

	// methods
	private void initData() {
		setTitle("skype");
		setSize(500, 700);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// favicon
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.getImage(faviconImg);
		setIconImage(img);

		bg = new JLabel(new ImageIcon(serverFrameBgImg));
		inputPort = new JTextField();
		inputPort.setBounds(192, 260, 100, 20);
		inputPort.setText("5000");
		mainBoard = new JTextArea();
		mainBoard.setBounds(130, 300, 220, 200);
		mainBoard.setEnabled(false);
		scrollPane = new ScrollPane();
		scrollPane.setBounds(135, 300, 220, 200);
		startServer = new JButton(new ImageIcon(startServerBtn));
		startServer.setBounds(87, 540, 130, 50);
		startServer.setBorderPainted(false); // 버튼 테두리 없애기
		startServer.setContentAreaFilled(false); // 버튼 배경 투명
		closeServer = new JButton(new ImageIcon(closeServerBtn));
		closeServer.setBounds(270, 540, 135, 50);
		closeServer.setBorderPainted(false); // 버튼 테두리 없애기
		closeServer.setContentAreaFilled(false); // 버튼 배경 투명
		closeServer.setEnabled(false);
	}

	private void setInitLayout() {
		setVisible(true);
		setLocationRelativeTo(null);

		bg.add(inputPort);
		scrollPane.add(mainBoard);
		bg.add(scrollPane);
		bg.add(startServer);
		bg.add(closeServer);

		add(bg);
	}

	private void addEventListener() {
		startServer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				socketNum = Integer.parseInt(inputPort.getText());
				mContext.setSocketNum(socketNum);
				mContext.startServer();
			}
		});

		closeServer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mContext.closeServer();
			}
		});
	}

	// getter, setter
	public Server getmContext() {
		return mContext;
	}

	public void setmContext(Server mContext) {
		this.mContext = mContext;
	}

	public JLabel getBg() {
		return bg;
	}

	public void setBg(JLabel bg) {
		this.bg = bg;
	}

	public JTextField getInputPort() {
		return inputPort;
	}

	public void setInputPort(JTextField inputPort) {
		this.inputPort = inputPort;
	}

	public int getSocketNum() {
		return socketNum;
	}

	public void setSocketNum(int socketNum) {
		this.socketNum = socketNum;
	}

	public JTextArea getMainBoard() {
		return mainBoard;
	}

	public void setMainBoard(JTextArea mainBoard) {
		this.mainBoard = mainBoard;
	}

	public ScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(ScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	public JButton getStartServer() {
		return startServer;
	}

	public void setStartServer(JButton startServer) {
		this.startServer = startServer;
	}

	public JButton getCloseServer() {
		return closeServer;
	}

	public void setCloseServer(JButton closeServer) {
		this.closeServer = closeServer;
	}


}
