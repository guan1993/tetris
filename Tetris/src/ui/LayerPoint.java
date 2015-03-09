package ui;

import java.awt.Graphics;

import config.GameConfig;
import control.GameControl;

public class LayerPoint extends Layer {

	/**
	 * 分数最大位数
	 */
	private static final int POINT_BIT = 5;

	/**
	 * 升级要消的行数
	 */
	private final int levelUp = GameConfig.getSystemConfig().getLevelUp();

	/**
	 * 经验值槽的Y坐标
	 */
	private static int expY;

	/**
	 * 消行Y坐标
	 */
	private final int reduceY;

	/**
	 * 分数Y坐标
	 */
	private final int scoreY;

	/**
	 * 分数X坐标
	 */
	private final int comX;

	public LayerPoint(int x, int y, int w, int h) {
		super(x, y, w, h);
		// 初始化共通X坐标
		this.comX = this.w - IMG_NUMBER_W * POINT_BIT - PADDING;
		// 初始化分数显示的Y坐标
		this.scoreY = PADDING;
		// 初始化消行的Y坐标
		this.reduceY = this.scoreY + Img.SCORE.getHeight(null) + PADDING;
		// 初始化经验值Y坐标
		this.expY = this.reduceY + Img.REDUCE.getHeight(null) + PADDING;
	}

	public void paint(Graphics g) {
		this.createWindow(g);
		// 窗口标题 分数
		g.drawImage(Img.SCORE, this.x + PADDING, this.y + scoreY, null);
		// 显示分数
		this.drawNumberLeftPad(comX, scoreY, this.dto.getNowPoint(), POINT_BIT,
				g);
		// 窗口标题 消行
		g.drawImage(Img.REDUCE, this.x + PADDING, this.y + reduceY, null);
		// 显示消行
		this.drawNumberLeftPad(comX, reduceY, this.dto.getNowRemoveLine(),
				POINT_BIT, g);
		// 绘制值槽（经验值）
		int rmLine = this.dto.getNowRemoveLine();
		this.drawRect(expY, "下一级", null, (double) (rmLine % levelUp) / levelUp,
				g);
	}

}
