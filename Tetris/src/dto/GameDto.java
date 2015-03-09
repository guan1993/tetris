package dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import util.GameFunction;
import config.GameConfig;
import entity.GameAct;

public class GameDto {
	/**
	 * 游戏宽度
	 */
	public static final int GAMEZONE_W = GameConfig.getSystemConfig().getMaxX() + 1;

	/**
	 * 游戏高度
	 */
	public static final int GAMEZONE_H = GameConfig.getSystemConfig().getMaxY() + 1;

	/**
	 * 数据库数据
	 */
	private List<Player> dbRecorder;

	/**
	 * 本地磁盘数据
	 */
	private List<Player> diskRecorder;

	/**
	 * 地图
	 */
	private boolean[][] gameMap;

	/**
	 * 下落的方块
	 */
	private GameAct gameAct;

	/**
	 * 下一个方块
	 */
	private int next;

	/**
	 * 现在的等级
	 */
	private int nowLevel;

	/**
	 * 现在的分数
	 */
	private int nowPoint;

	/**
	 * 现在消除的行数
	 */
	private int nowRemoveLine;

	/**
	 * 线程休眠时间
	 */
	private long sleepTime;

	/**
	 * 表示游戏是否是开始状态
	 */
	private boolean start;

	/**
	 * 阴影开关
	 */
	private boolean showShadow = true;

	/**
	 * 暂停
	 */
	private boolean pause;

	/**
	 * 是否启用作弊键
	 */
	private boolean cheat;

	/**
	 * 构造函数
	 */
	public GameDto() {
		dtoInit();
	}

	/**
	 * dto初始化
	 */
	public void dtoInit() {
		this.gameMap = new boolean[GAMEZONE_W][GAMEZONE_H];
		this.nowLevel = 0;
		this.nowPoint = 0;
		this.nowRemoveLine = 0;
		this.pause = false;
		this.cheat = false;
		this.sleepTime = GameFunction.getSleepTimeByLevel(this.nowLevel);
	}

	public List<Player> getDbRecorder() {
		return dbRecorder;
	}

	public void setDbRecorder(List<Player> dbRecorder) {
		this.dbRecorder = setFillRecorder(dbRecorder);
	}

	public List<Player> getDiskRecorder() {
		return diskRecorder;
	}

	public void setDiskRecorder(List<Player> diskRecorder) {
		this.diskRecorder = setFillRecorder(diskRecorder);
	}

	private List<Player> setFillRecorder(List<Player> players) {
		// 如果进来的时空 则创建
		if (players == null) {
			players = new ArrayList<Player>();
		}
		// 如果记录数少于5 则直接补满
		while (players.size() < 5) {
			players.add(new Player("No Data", 0));
		}
		Collections.sort(players);
		return players;
	}

	public boolean[][] getGameMap() {
		return gameMap;
	}

	public void setGameMap(boolean[][] gameMap) {
		this.gameMap = gameMap;
	}

	public GameAct getGameAct() {
		return gameAct;
	}

	public void setGameAct(GameAct gameAct) {
		this.gameAct = gameAct;
	}

	public int getNext() {
		return next;
	}

	public void setNext(int next) {
		this.next = next;
	}

	public int getNowPoint() {
		return nowPoint;
	}

	public void setNowPoint(int nowPoint) {
		this.nowPoint = nowPoint;
	}

	public int getNowRemoveLine() {
		return nowRemoveLine;
	}

	public void setNowRemoveLine(int nowRemoveLine) {
		this.nowRemoveLine = nowRemoveLine;
	}

	public int getNowLevel() {
		return nowLevel;
	}

	public void setNowLevel(int nowLevel) {
		this.nowLevel = nowLevel;
		// 计算线程睡眠时间
		this.sleepTime = GameFunction.getSleepTimeByLevel(this.nowLevel);
	}

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	public boolean isShowShadow() {
		return showShadow;
	}

	public void changeShowShadow() {
		this.showShadow = !this.showShadow;
	}

	public boolean isPause() {
		return pause;
	}

	public void changePause() {
		this.pause = !this.pause;
	}

	public boolean isCheat() {
		return cheat;
	}

	public void setCheat(boolean cheat) {
		this.cheat = cheat;
	}

	public long getSleepTime() {
		return sleepTime;
	}

}
