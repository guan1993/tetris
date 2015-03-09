package ui.window;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import ui.Img;
import ui.Layer;

import config.FrameConfig;
import config.GameConfig;
import config.LayerConfig;
import control.GameControl;
import control.PlayerControl;
import dto.GameDto;

public class JPanelGame extends JPanel {

	private final static int BTN_SIZE_W = GameConfig.getFrameConfig()
			.getButtonConfig().getButtonW();

	private final static int BTN_SIZE_H = GameConfig.getFrameConfig()
			.getButtonConfig().getButtonH();

	private final static int BTN_START_X = GameConfig.getFrameConfig()
			.getButtonConfig().getStartX();

	private final static int BTN_START_Y = GameConfig.getFrameConfig()
			.getButtonConfig().getStartY();

	private final static int BTN_USERCONFIG_X = GameConfig.getFrameConfig()
			.getButtonConfig().getUserconfigX();

	private final static int BTN_USERCONFIG_Y = GameConfig.getFrameConfig()
			.getButtonConfig().getUserconfigY();

	private List<Layer> layers = null;

	private JLabel btnStart;

	private JLabel btnConfig;

	private GameControl gameControl = null;

	public JPanelGame(GameControl gameControl, GameDto dto) {
		// 连接游戏控制器
		this.gameControl = gameControl;
		// 设置自由布局
		this.setLayout(null);
		// 初始化组件
		this.initComponent();
		// 初始化层
		this.initLayer(dto);
		// 设置窗口风格
		this.setWindowType();
		// 安装键盘监听器
		this.addKeyListener(new PlayerControl(gameControl));
	}

	/**
	 * 初始化组件
	 */
	public void initComponent() {
		// 初始化开始按钮
		this.btnStart = new JLabel(Img.BTN_START);
		// 添加鼠标监听
		this.btnStart.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				gameControl.start();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}
		});
		// 开始按钮位置
		this.btnStart.setBounds(BTN_START_X, BTN_START_Y, BTN_SIZE_W,
				BTN_SIZE_H);
		this.add(btnStart);
		// 初始化设置按钮
		this.btnConfig = new JLabel(Img.BTN_CONFIG);
		// 添加鼠标监听
		this.btnConfig.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				gameControl.showUserConfig();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}
		});
		// 设置按钮位置
		this.btnConfig.setBounds(BTN_USERCONFIG_X, BTN_USERCONFIG_Y,
				BTN_SIZE_W, BTN_SIZE_H);
		this.add(btnConfig);
	}

	/**
	 * 初始化层
	 */
	public void initLayer(GameDto dto) {
		try {
			// 获得游戏配置
			FrameConfig fCfg = GameConfig.getFrameConfig();
			// 获得层配置
			List<LayerConfig> layersCfg = fCfg.getLayersConfig();
			// 创建游戏层数组
			layers = new ArrayList<Layer>(layersCfg.size());
			// 创建所有层对象
			for (LayerConfig layerCfg : layersCfg) {
				// 获得对象
				Class<?> cls = Class.forName(layerCfg.getClassName());
				// 获得构造函数
				Constructor<?> ctr = cls.getConstructor(int.class, int.class,
						int.class, int.class);
				// 调用构造函数创建层对象 返回的是一个object 需要强制声明
				Layer layer = (Layer) ctr.newInstance(layerCfg.getX(),
						layerCfg.getY(), layerCfg.getW(), layerCfg.getH());
				// 设置游戏数据对象
				layer.setDto(dto);
				// 把创建好的Layer对象放入集合
				layers.add(layer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		// 调用父类方法重写
		super.paintComponents(g);
		// 绘制游戏界面
		for (int i = 0; i < layers.size(); layers.get(i++).paint(g))
			;
		// 返回焦点
		if (gameControl.frameSetting.isVisible()
				|| gameControl.frameSavePoint.isVisible()) {
			return;
		}
		this.requestFocus();

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

	/**
	 * 控制按钮是否可以点击
	 */
	public void buttonSwithch(boolean onOff) {
		this.btnConfig.setEnabled(onOff);
		this.btnStart.setEnabled(onOff);
	}
}
