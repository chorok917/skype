package skype.main;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import skype.ImgImpl;
import skype.client.Client;
import skype.server.Server;

public class Skype extends JFrame implements ImgImpl{

	// field
	private JLabel bg;
	private JButton startServer;
	private JButton userLogin;

	// Constructor
	public Skype() {
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

		bg = new JLabel(new ImageIcon(startPageImg));
		startServer = new JButton(new ImageIcon(startServerBtn));
		startServer.setBounds(177, 330, 130, 50);
		startServer.setBorderPainted(false); // 버튼 테두리 없애기
		startServer.setContentAreaFilled(false); // 버튼 배경 투명
		userLogin = new JButton(new ImageIcon(userLoginBtn));
		userLogin.setBounds(177, 420, 130, 50);
		userLogin.setBorderPainted(false); // 버튼 테두리 없애기
		userLogin.setContentAreaFilled(false); // 버튼 배경 투명
	}

	private void setInitLayout() {
		setVisible(true);
		setLocationRelativeTo(null);

		bg.add(startServer);
		bg.add(userLogin);
		add(bg);
	}

	private void addEventListener() {
		startServer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Server();
				getStartServer().setEnabled(false);
			}
		});

		userLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Client();
			}
		});
	}

	// getter, setter
	public JButton getStartServer() {
		return startServer;
	}

	public void setStartServer(JButton startServer) {
		this.startServer = startServer;
	}

}
