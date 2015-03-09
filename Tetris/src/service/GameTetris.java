package service;

import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import config.GameConfig;
import dto.GameDto;
import dto.Player;
import entity.GameAct;

public class GameTetris implements GameService {

	/**
	 * 升级要消的行数
	 */
	private final int levelUp = GameConfig.getSystemConfig().getLevelUp();

	private GameDto dto;

	/**
	 * 随机数生成器
	 */
	private Random random = new Random();

	/**
	 * 方块总数
	 */
	private static final int MAX_TYPE = 100;

	/**
	 * 连续消行分数表
	 */
	private static final Map<Integer, Integer> PLUS_POINT = GameConfig
			.getSystemConfig().getPlusPoint();

	public GameTetris(GameDto dto) {
		this.dto = dto;
	}

	/**
	 * 方向操作（上）
	 */
	public boolean keyUp() {
		if (this.dto.isPause()) {
			return true;
		}
		synchronized (this.dto) {
			this.dto.getGameAct().round(this.dto.getGameMap());
		}
		return true;
	}

	/**
	 * 方向操作（下）
	 */
	public boolean keyDown() {
		if (this.dto.isPause()) {
			return true;
		}
		synchronized (this.dto) {
			if (this.dto.getGameAct().move(0, 1, this.dto.getGameMap())) {
				return false;
			}
			// 获得游戏地图对象
			boolean[][] map = this.dto.getGameMap();
			Point[] act = this.dto.getGameAct().getActPoints();
			// 将方块堆积到地图数组
			for (int i = 0; i < act.length; i++) {
				map[act[i].x][act[i].y] = true;
			}
			// 判断消行 计算消行数
			int exp = this.plusExp();
			// 如果发生消行
			if (exp > 0) {
				// 增加经验值 加分
				this.plusPoint(exp);
			}
			// 升级
			if (this.dto.isStart()) {
				// 将下一个方块创建
				this.dto.getGameAct().init(this.dto.getNext());
				// 随机生成再下一个方块
				this.dto.setNext(random.nextInt(MAX_TYPE) % 7);
			}
			// 检查游戏是否结束
			if (this.checklose()) {
				this.dto.setStart(false);
			}
		}
		return true;
	}

	/**
	 * 判断游戏是否结束
	 */
	private boolean checklose() {
		// 获得现在的活动方块
		Point[] actPoints = this.dto.getGameAct().getActPoints();
		// 获得现在的游戏地图
		boolean[][] map = this.dto.getGameMap();
		for (int i = 0; i < actPoints.length; i++) {
			if (map[actPoints[i].x][actPoints[i].y]) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 加分加行升级操作
	 */
	private void plusPoint(int plusExp) {
		int level = this.dto.getNowLevel();
		int removeLine = this.dto.getNowRemoveLine();
		int point = this.dto.getNowPoint();
		if (removeLine % levelUp + plusExp >= 20) {
			this.dto.setNowLevel(++level);
		}
		this.dto.setNowRemoveLine(removeLine + plusExp);
		this.dto.setNowPoint(point + PLUS_POINT.get(plusExp));
	}

	/**
	 * 方向操作（左）
	 */
	public boolean keyLeft() {
		if (this.dto.isPause()) {
			return true;
		}
		synchronized (this.dto) {
			this.dto.getGameAct().move(-1, 0, this.dto.getGameMap());
		}
		return true;
	}

	/**
	 * 方向操作（右）
	 */
	public boolean keyRight() {
		if (this.dto.isPause()) {
			return true;
		}
		synchronized (this.dto) {
			this.dto.getGameAct().move(1, 0, this.dto.getGameMap());
		}
		return true;
	}

	@Override
	public boolean keyFuncUp() {
		this.dto.setCheat(true);
		int point = this.dto.getNowPoint();
		int level = this.dto.getNowLevel();
		int removeLine = this.dto.getNowRemoveLine();

		point += 10;
		removeLine += 1;
		if (removeLine % 20 == 0) {
			level++;
		}
		this.dto.setNowPoint(point);
		this.dto.setNowLevel(level);
		this.dto.setNowRemoveLine(removeLine);
		return true;
	}

	@Override
	public boolean keyFuncDown() {
		if (this.dto.isPause()) {
			return true;
		}
		while (!this.keyDown())
			;
		return true;
	}

	@Override
	public boolean keyFuncLeft() {
		this.dto.changeShowShadow();
		return true;
	}

	@Override
	public boolean keyFuncRight() {
		if (this.dto.isStart()) {
			this.dto.changePause();
		}
		return true;
	}

	/**
	 * 消行
	 */
	private int plusExp() {
		int exp = 0;
		// 获得游戏地图
		boolean[][] map = this.dto.getGameMap();
		for (int y = 0; y < GameDto.GAMEZONE_H; y++) {
			// 判断是否可消行
			if (this.isCanRemoveLine(y, map)) {
				// 如果可消行
				this.removeLine(y, map);
				// 增加exp
				exp++;
			}
		}
		return exp;
	}

	/**
	 * 消行处理
	 */
	private void removeLine(int rowNumber, boolean[][] map) {
		for (int x = 0; x < GameDto.GAMEZONE_W; x++) {
			for (int y = rowNumber; y > 0; y--) {
				map[x][y] = map[x][y - 1];
			}
			map[x][0] = false;
		}
	}

	/**
	 * 判断某行是否可消
	 */
	private boolean isCanRemoveLine(int y, boolean[][] map) {
		// 单行内对每一个单元格进行扫描
		for (int x = 0; x < GameDto.GAMEZONE_W; x++) {
			if (!map[x][y]) {
				// 存在不满的 对下一行扫描
				return false;
			}
		}
		return true;
	}

	/**
	 * 启动主线程 开始游戏
	 */
	public void startGame() {
		// 随机生成下一方块
		this.dto.setNext(random.nextInt(MAX_TYPE) % 7);
		// 随机生成画面方块
		this.dto.setGameAct(new GameAct(random.nextInt(MAX_TYPE) % 7));
		// 把游戏状态设为开始
		this.dto.setStart(true);
		// dto初始化
		this.dto.dtoInit();
	}

	@Override
	public void mainAction() {
		this.keyDown();
	}

}
