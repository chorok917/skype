package skype.client;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import skype.ImgImpl;

public class UserLogin extends JFrame implements ImgImpl {

	// field
	private Client mContext;
	private JLabel bg;
	private JTextField inputPort;
	private JTextField hostIp;
	private JTextField name;
	private JButton login;


	// Constructor
	public UserLogin(Client mContext) {
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

		bg = new JLabel(new ImageIcon(userLoginBg));
		inputPort = new JTextField();
		inputPort.setBounds(192, 260, 100, 20);
		hostIp = new JTextField();
		hostIp.setBounds(192, 360, 100, 20);
		name = new JTextField();
		name.setBounds(192, 460, 100, 20);
		login = new JButton(new ImageIcon(loginBtn));
		login.setBounds(175, 540, 135, 50);
		login.setBorderPainted(false); // 버튼 테두리 없애기
		login.setContentAreaFilled(false); // 버튼 배경 투명
		
		hostIp.setText("127.0.0.7");
		inputPort.setText("5000");
	}

	private void setInitLayout() {
		setVisible(true);
		setLocationRelativeTo(null);

		bg.add(inputPort);
		bg.add(hostIp);
		bg.add(name);
		bg.add(login);
		add(bg);
	}

	private void addEventListener() {
		login.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (inputPort.getText().equals("") || hostIp.getText().equals("") || name.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "공백을 채우세요.", "알림", JOptionPane.ERROR_MESSAGE);
				} else {
//					mContext.connectNetwork();
					String ip = hostIp.getText();
					String stringPort = inputPort.getText();
					int port = Integer.parseInt(stringPort);
					String userName = name.getText();
					mContext.clickConnectServerBtn(ip, port, userName);
//					login.setEnabled(false);
				}
			}

		});
	}
	

	// getter, setter
	public JLabel getBg() {
		return bg;
	}

	public void setBg(JLabel bg) {
		this.bg = bg;
	}

	public void setInputPort(JTextField inputPort) {
		this.inputPort = inputPort;
	}
	public String getHostIp() {
		return hostIp.getText();
	}

	public void setHostIp(JTextField hostIp) {
		this.hostIp = hostIp;
	}

	public String getUserName() {
		return name.getText();
	}

	public void setName(JTextField name) {
		this.name = name;
	}

	public JButton getLogin() {
		return login;
	}

	public void setLogin(JButton login) {
		this.login = login;
	}

	public Client getmContext() {
		return mContext;
	}

	public void setmContext(Client mContext) {
		this.mContext = mContext;
	}

} // end of class