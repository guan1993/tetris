package service;

public interface GameService {

	/**
	 * 方向上
	 */
	public boolean keyUp();

	/**
	 * 方向下
	 */
	public boolean keyDown();

	/**
	 * 方向左
	 */
	public boolean keyLeft();

	/**
	 * 方向右
	 */
	public boolean keyRight();

	/**
	 * 三角
	 */
	public boolean keyFuncUp();

	/**
	 * 叉叉
	 */
	public boolean keyFuncDown();

	/**
	 * 方块
	 */
	public boolean keyFuncLeft();

	/**
	 * 圆
	 */
	public boolean keyFuncRight();

	/**
	 * 启动线程开始游戏
	 */
	public void startGame();

	/**
	 * 游戏主要行为
	 */
	public void mainAction();

}
