package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.util.List;

import config.GameConfig;

import dto.Player;

public abstract class LayerData extends Layer {

	/**
	 * 最大行数
	 */
	protected static final int MAX_ROW = GameConfig.getDataConfig().getMaxRow();

	/**
	 * 值槽外径
	 */
	protected static final int RECT_H = IMG_RECT_H + 4;

	/**
	 * 起始Y坐标
	 */
	protected static int START_Y = 0;

	/**
	 * 行间距
	 */
	protected static int SPA = 0;

	protected LayerData(int x, int y, int w, int h) {
		super(x, y, w, h);
		this.SPA = ((this.h - RECT_H * MAX_ROW - (PADDING << 1) - Img.DB
				.getHeight(null)) / MAX_ROW);
		this.START_Y = PADDING + Img.DB.getHeight(null) + SPA;
	}

	abstract public void paint(Graphics g);

	/**
	 * 绘制该窗口所有值槽
	 * 
	 * @param imgTitle
	 * @param players
	 * @param g
	 */
	public void showData(Image imgTitle, List<Player> players, Graphics g) {
		g.drawImage(imgTitle, x + PADDING, y + PADDING, null);
		int nowPoint = this.dto.getNowPoint();
		for (int i = 0; i < 5; i++) {
			Player pla = players.get(i);
			String strNumber;
			int recorderPoint = pla.getPoint();
			strNumber = recorderPoint == 0 ? null : Integer
					.toString(recorderPoint);
			double percent = (double) nowPoint / recorderPoint;
			percent = percent > 1 ? 1.0 : percent;
			this.drawRect(START_Y + (SPA + RECT_H) * i, pla.getName(),
					strNumber, percent, g);
		}
	}

}
