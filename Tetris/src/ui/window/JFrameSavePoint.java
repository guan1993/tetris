package ui.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import control.GameControl;

import util.FrameUtil;

public class JFrameSavePoint extends JFrame {

	private JButton btnOk = null;

	private JPanel south = null;

	private JPanel north = null;

	private JPanel middle = null;

	private JTextField name = null;

	private JTextField point = null;

	private GameControl gameControl = null;

	public JFrameSavePoint(GameControl gameControl) {
		this.gameControl = gameControl;
		this.setTitle("保存记录");
		this.setSize(256, 128);
		FrameUtil.setFrameCenter(this);
		this.setResizable(false);
		this.initCom();
		this.setWindowType();
		this.createAction();
	}

	/**
	 * 显示窗口
	 */
	public void showSave(int point) {
		this.point.setText(point + "");
		this.setVisible(true);
	}

	/**
	 * 事件监听
	 */
	private void createAction() {
		this.btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String playerName = name.getText();
				if (playerName.length() > 15) {
					JOptionPane.showMessageDialog(null, "输入名字过长。");
				} else {
					setVisible(false);
					// 把焦点还给panelGame
					gameControl.panelGame.requestFocus();
					gameControl.svaePoint(playerName);
				}
			}
		});
	}

	private void initCom() {
		this.south = new JPanel();
		this.middle = new JPanel(new FlowLayout(FlowLayout.LEFT));
		this.north = new JPanel(new FlowLayout(FlowLayout.LEFT));
		this.btnOk = new JButton("确定");
		this.name = new JTextField(15);
		this.point = new JTextField(5);
		this.point.setEnabled(false);

		this.south.add(this.btnOk);
		this.middle.add(new JLabel("请输入姓名："));
		this.middle.add(this.name);
		this.north.add(new JLabel("本次游戏分："));
		this.north.add(this.point);
		this.add(this.south, BorderLayout.SOUTH);
		this.add(this.middle, BorderLayout.CENTER);
		this.add(this.north, BorderLayout.NORTH);
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
