package ui.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import util.FrameUtil;
import control.GameControl;

public class JFrameSetting extends JFrame {

	private JButton btnOk = new JButton("确定");

	private JList skinList = new JList();

	private DefaultListModel skinData = new DefaultListModel();

	private JButton btnCancel = new JButton("取消");

	private JButton btnUse = new JButton("应用");

	private TextCtrl[] keyTexts = new TextCtrl[8];

	private static final Image IMG_PSP = new ImageIcon("data/psp.jpg")
			.getImage();

	private static final Image ICO = new ImageIcon("window/ico.jpg").getImage();

	private final static String[] METHOD_NAMES = { "keyRight", "keyUp",
			"keyLeft", "keyDown", "keyFuncLeft", "keyFuncUp", "keyFuncRight",
			"keyFuncDown" };

	private final static String PATH = "data/control.dat";

	private JLabel errorMsg = new JLabel();

	private GameControl gameControl;

	public JFrameSetting(GameControl gameControl) {
		// 获得游戏控制器对象
		this.gameControl = gameControl;
		// 边界布局
		this.setLayout(new BorderLayout());
		// 初始化按键输入框
		this.initKeyText();
		// 添加主面板
		this.add(this.createMainPanel(), BorderLayout.CENTER);
		// 添加按钮面板
		this.add(this.createButtonPanel(), BorderLayout.SOUTH);
		// 设置标题
		this.setTitle("游戏设置");
		// 设置大小
		this.setSize(830, 380);
		// 设置居中
		FrameUtil.setFrameCenter(this);
		// 设置大小不可变
		this.setResizable(false);
		// 设置成系统风格
		// this.setWindowType();
		this.requestFocus();
	}

	/**
	 * 初始化按键输入框
	 */
	private void initKeyText() {
		int x = 25;
		int y = 55;
		int w = 64;
		int h = 20;
		for (int i = 0; i < 4; i++) {
			keyTexts[i] = new TextCtrl(x, y, w, h, METHOD_NAMES[i]);
			y += 35;
		}
		x = 700;
		y = 55;
		for (int i = 4; i < 8; i++) {
			keyTexts[i] = new TextCtrl(x, y, w, h, METHOD_NAMES[i]);
			y += 35;
		}
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(PATH));
			HashMap<Integer, String> cfgSet = (HashMap<Integer, String>) ois
					.readObject();
			Set<Entry<Integer, String>> entryset = cfgSet.entrySet();
			for (Entry<Integer, String> e : entryset) {
				for (TextCtrl tc : keyTexts) {
					if (tc.getMethodName().equals(e.getValue())) {
						tc.setKeyCode(e.getKey());
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	/**
	 * 创建按钮面板
	 */
	private JPanel createButtonPanel() {
		// 创建按钮面板 流式面目局
		JPanel jp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		// 给确定按钮增加事件监听 <匿名类的使用 不能用static方法>
		jp.add(this.btnOk);
		this.btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (writeConfig()) {
					closeWindow();
					gameControl.setOver();
				}
			}
		});
		jp.add(this.btnCancel);
		// 给取消按钮增加事件监听
		this.btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeWindow();
				gameControl.setOver();
			}
		});
		jp.add(this.btnUse);
		// 给应用按钮增加事件监听
		this.btnUse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (writeConfig()) {
					JOptionPane.showMessageDialog(null, "修改按键成功。");
					gameControl.setOver();
				}
			}
		});
		return jp;
	}

	/**
	 * 创建主面板(选项卡面板)
	 */
	private JTabbedPane createMainPanel() {
		JTabbedPane jtp = new JTabbedPane();
		jtp.addTab("按键设置", this.createControlPanel());
		// jtp.addTab("皮肤设置", this.createSkinPanel());
		return jtp;
	}

	/**
	 * 皮肤设置面板
	 */
	private JPanel createSkinPanel() {
		JPanel jp = new JPanel();
		return jp;
	}

	/**
	 * 玩家控制设置面板
	 */
	private JPanel createControlPanel() {
		JPanel jp = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				g.drawImage(IMG_PSP, 0, 0, null);
			}
		};
		// 设置布局管理器为空
		jp.setLayout(null);
		for (int i = 0; i < keyTexts.length; i++) {
			jp.add(keyTexts[i]);
		}
		return jp;
	}

	/**
	 * 关闭窗口
	 */
	private void closeWindow() {
		this.setVisible(false);
		gameControl.panelGame.requestFocus();
	}

	/**
	 * 写入游戏配置
	 */
	private boolean writeConfig() {
		HashMap<Integer, String> keySet = new HashMap<Integer, String>();
		for (int i = 0; i < this.keyTexts.length; i++) {
			int keyCode = this.keyTexts[i].getKeyCode();
			if (keyCode == 0) {
				JOptionPane.showMessageDialog(this, "存在错误按键，请继续设置。");
				return false;
			}
			keySet.put(keyCode, this.keyTexts[i].getMethodName());
		}
		if (keySet.size() != 8) {
			JOptionPane.showMessageDialog(this, "存在重复按键，请重新确认。");
			return false;
		}
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(PATH));
			oos.writeObject(keySet);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				oos.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * 设置窗口风格
	 */
	public void setWindowType() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception exe) {
			exe.printStackTrace();
		}
	}
}
