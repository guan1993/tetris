package control;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JOptionPane;

import service.GameService;
import service.GameTetris;
import ui.window.JFrameGame;
import ui.window.JFrameSavePoint;
import ui.window.JFrameSetting;
import ui.window.JPanelGame;
import config.DataInterfaceConfig;
import config.GameConfig;
import dao.Data;
import dto.GameDto;
import dto.Player;

/**
 * 接收玩家键盘事件 控制画面 控制游戏逻辑
 * 
 * @author xring
 * 
 */
public class GameControl {

	/**
	 * 游戏逻辑层
	 */
	private GameService gameService;

	/**
	 * 数据访问接口A
	 */
	private Data dataA;

	/**
	 * 数据访问接口B
	 */
	private Data dataB;

	/**
	 * 游戏界面层
	 */
	public JPanelGame panelGame;

	/**
	 * 游戏行为控制
	 */
	private Map<Integer, Method> actionList;

	/**
	 * 玩家控制设置窗口
	 */
	public JFrameSetting frameSetting;

	/**
	 * 保存分数窗口
	 */
	public JFrameSavePoint frameSavePoint;

	/**
	 * 游戏线程
	 */
	private Thread gameThread = null;

	/**
	 * 游戏数据源
	 */
	private GameDto dto = null;

	public GameControl() {

		// 创建游戏数据源
		this.dto = new GameDto();
		// 创建游戏逻辑块 安装游戏数据源
		this.gameService = new GameTetris(dto);
		// 从数据接口A得到数据记录
		this.dataA = createDataObject(GameConfig.getDataConfig().getDataA());
		// 设置数据库记录到游戏
		this.dto.setDbRecorder(dataA.loadData());
		// 从数据接口B得到数据记录
		this.dataB = createDataObject(GameConfig.getDataConfig().getDataB());
		// 设置本地记录到游戏
		this.dto.setDiskRecorder(dataB.loadData());
		// 创建游戏面板
		this.panelGame = new JPanelGame(this, dto);
		// 读取用户按键设置
		this.setControlConfig();
		// 初始化玩家控制设置窗口
		this.frameSetting = new JFrameSetting(this);
		// 初使化分数保存窗口
		this.frameSavePoint = new JFrameSavePoint(this);
		// 安始化主窗口 安装到游戏面板
		new JFrameGame(panelGame);
	}

	/**
	 * 读取用户控制设置
	 */
	private void setControlConfig() {
		ObjectInputStream ois = null;
		try {
			// 创建方法名与键盘码的映射数组
			this.actionList = new HashMap<Integer, Method>();
			ois = new ObjectInputStream(new FileInputStream("data/control.dat"));
			@SuppressWarnings("unchecked")
			HashMap<Integer, String> cfgSet = (HashMap<Integer, String>) ois
					.readObject();
			Set<Entry<Integer, String>> entryset = cfgSet.entrySet();
			for (Entry<Integer, String> e : entryset) {
				actionList.put(e.getKey(), this.gameService.getClass()
						.getMethod(e.getValue()));
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
	 * 创建数据对象
	 */
	private Data createDataObject(DataInterfaceConfig cfg) {
		try {
			// 获得类对象
			Class<?> cls = Class.forName(cfg.getClassName());
			// 获得构造器
			Constructor<?> ctr = cls.getConstructor(HashMap.class);
			// 创建对象
			return (Data) ctr.newInstance(cfg.getParam());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据玩家控制来决定行为
	 */
	public void actionByKeyCode(int keyCode) {
		try {
			if (actionList.containsKey(keyCode)) {
				this.actionList.get(keyCode).invoke(gameService);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.panelGame.repaint();
	}

	/**
	 * 显示玩家控制设置窗口
	 */
	public void showUserConfig() {
		this.frameSetting.setVisible(true);
	}

	/**
	 * 子窗口关闭事件
	 */
	public void setOver() {
		this.panelGame.repaint();
		this.setControlConfig();
	}

	/**
	 * 开始
	 */
	public void start() {
		// 面板按钮设置为不可点击
		this.panelGame.buttonSwithch(false);
		// 关闭窗口
		this.frameSavePoint.setVisible(false);
		this.frameSetting.setVisible(false);
		// 游戏数据初始化
		this.gameService.startGame();
		// 刷新
		panelGame.repaint();
		// 创建线程对象
		this.gameThread = new MainThread();
		// 启动线程
		this.gameThread.start();
		// 刷新
		panelGame.repaint();
	}

	/**
	 * 游戏结否后的处理
	 */
	private void afterLose() {
		this.panelGame.repaint();
		if (!this.dto.isCheat()) {
			// 显示保存得分窗口
			this.frameSavePoint.setVisible(true);
			this.frameSavePoint.showSave(this.dto.getNowPoint());
		} else {
			JOptionPane.showMessageDialog(null, "使用了作弊键，本次分数无效。");
		}
		// 使按钮可以点击
		this.panelGame.buttonSwithch(true);
	}

	private class MainThread extends Thread {
		@Override
		public void run() {
			while (dto.isStart()) {
				// 刷新
				panelGame.repaint();
				try {
					// 等待0.5S
					Thread.sleep(dto.getSleepTime());
					if (dto.isPause()) {
						continue;
					}
					// 游戏主行为
					gameService.mainAction();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			afterLose();
		}
	}

	public void svaePoint(String playerName) {
		Player pla = new Player(playerName, this.dto.getNowPoint());
		// 保存记录到数据库
		this.dataA.saveData(pla);
		// 保存记录到本地
		this.dataB.saveData(pla);
		// 设置数据库记录到游戏
		this.dto.setDbRecorder(dataA.loadData());
		// 设置本地记录到游戏
		this.dto.setDiskRecorder(dataB.loadData());
		// 刷新记录
		this.panelGame.repaint();
	}
}
