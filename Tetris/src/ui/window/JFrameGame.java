package ui.window;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import util.FrameUtil;
import config.FrameConfig;
import config.GameConfig;

@SuppressWarnings("serial")
public class JFrameGame extends JFrame {

	public JFrameGame(JPanelGame panelGame) {
		// 获得游戏配置
		FrameConfig fCfg = GameConfig.getFrameConfig();
		// 设置窗口属性
		this.setTitle(fCfg.getTitle());
		// 设置默认关闭
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 设置窗口大小
		this.setSize(fCfg.getWidth(), fCfg.getHeight());
		// 设置不可调大小
		this.setResizable(false);
		// 获取居中
		FrameUtil.setFrameCenter(this);
		// 设置默认Panel
		this.setContentPane(panelGame);
		// 这句一定要放在后面
		this.setVisible(true);
	}

}
