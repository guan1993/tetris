package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import config.FrameConfig;
import config.GameConfig;
import dto.GameDto;

/**
 * 绘制窗口
 * 
 * @author xring
 * 
 */
public abstract class Layer {

	/**
	 * 内边距
	 */
	protected static final int PADDING;

	/**
	 * 边框宽度
	 */
	protected static final int BORDER;

	protected static final Font DEF_FONT = new Font("黑体", Font.BOLD, 20);

	/**
	 * 值槽图高度
	 */
	protected static final int IMG_RECT_H = Img.RECT.getHeight(null);

	/**
	 * 值槽图宽度
	 */
	protected static final int IMG_RECT_W = Img.RECT.getWidth(null);

	/**
	 * 值槽宽度
	 */
	private final int rectW;

	/**
	 * 数字切片的宽度和高度
	 */
	protected static final int IMG_NUMBER_W = Img.NUMBER.getWidth(null) / 10;
	private static final int IMG_NUMBER_H = Img.NUMBER.getHeight(null);

	static {
		// 获得游戏配置
		FrameConfig fCfg = GameConfig.getFrameConfig();
		PADDING = fCfg.getPadding();
		BORDER = fCfg.getBorder();
	}

	protected static int WINDOW_W = Img.WINDOW.getWidth(null);
	protected static int WINDOW_H = Img.WINDOW.getHeight(null);
	/**
	 * 窗口左上角X坐标
	 */
	protected int x;

	/**
	 * 窗口左上角Y坐标
	 */
	protected int y;

	/**
	 * 窗口的宽
	 */
	protected int w;

	/**
	 * 窗口的高
	 */
	protected int h;

	/**
	 * 游戏数据
	 */
	protected GameDto dto;

	protected Layer(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.rectW = this.w - (PADDING << 1);
	}

	/**
	 * 绘制窗口
	 */
	protected void createWindow(Graphics g) {
		// 左上
		g.drawImage(Img.WINDOW, x, y, x + BORDER, y + BORDER, 0, 0, BORDER,
				BORDER, null);
		// 中上
		g.drawImage(Img.WINDOW, x + BORDER, y, w + x - BORDER, y + BORDER,
				BORDER, 0, WINDOW_W - BORDER, BORDER, null);
		// 右上
		g.drawImage(Img.WINDOW, w + x - BORDER, y, w + x, y + BORDER, WINDOW_W
				- BORDER, 0, WINDOW_W, BORDER, null);
		// 中右
		g.drawImage(Img.WINDOW, w + x - BORDER, y + BORDER, w + x, h + y
				- BORDER, WINDOW_W - BORDER, BORDER, WINDOW_W, WINDOW_H
				- BORDER, null);
		// 右下
		g.drawImage(Img.WINDOW, w + x - BORDER, h + y - BORDER, w + x, h + y,
				WINDOW_W - BORDER, WINDOW_H - BORDER, WINDOW_W, WINDOW_H, null);
		// 中下
		g.drawImage(Img.WINDOW, x + BORDER, h + y - BORDER, w + x - BORDER, h
				+ y, BORDER, WINDOW_H - BORDER, WINDOW_W - BORDER, WINDOW_H,
				null);
		// 左下
		g.drawImage(Img.WINDOW, x, h + y - BORDER, x + BORDER, h + y, 0,
				WINDOW_H - BORDER, BORDER, WINDOW_H, null);
		// 中左
		g.drawImage(Img.WINDOW, x, y + BORDER, x + BORDER, h + y - BORDER, 0,
				BORDER, BORDER, WINDOW_H - BORDER, null);
		// 中
		g.drawImage(Img.WINDOW, x + BORDER, y + BORDER, w + x - BORDER, h + y
				- BORDER, BORDER, BORDER, WINDOW_W - BORDER, WINDOW_H - BORDER,
				null);
	}

	abstract public void paint(Graphics g);

	/**
	 * 显示数字
	 * 
	 * @param x
	 *            左上角X坐标
	 * @param y
	 *            左上角Y坐标
	 * @param num
	 *            数字
	 * @param g
	 *            画笔参数
	 */
	protected void drawNumberLeftPad(int x, int y, int num, int maxBit,
			Graphics g) {
		// 把要打印的数字转换成字符串
		String strNum = Integer.toString(num);
		for (int i = 0; i < maxBit; i++) {
			if (maxBit - i <= strNum.length()) {
				// 获得数字在字符串中的下标
				int idx = i - maxBit + strNum.length();
				// 把数字num中的第一位取出来
				int bit = strNum.charAt(idx) - '0';
				g.drawImage(Img.NUMBER, this.x + x + i * IMG_NUMBER_W, this.y
						+ y, this.x + x + (i + 1) * IMG_NUMBER_W, this.y + y
						+ IMG_NUMBER_H, bit * IMG_NUMBER_W, 0, (bit + 1)
						* IMG_NUMBER_W, IMG_NUMBER_H, null);
			}
		}

	}

	/**
	 * 绘制值槽
	 */
	protected void drawRect(int y, String title, String number, double percent,
			Graphics g) {
		// 各种值初始化
		int rect_x = this.x + PADDING;
		int rect_y = this.y + y;
		// 绘制值槽外观
		g.setColor(Color.BLACK);
		g.fillRect(rect_x, rect_y, rectW, IMG_RECT_H + 4);
		g.setColor(Color.WHITE);
		g.fillRect(rect_x + 1, rect_y + 1, rectW - 2, IMG_RECT_H + 2);
		g.setColor(Color.BLACK);
		g.fillRect(rect_x + 2, rect_y + 2, rectW - 4, IMG_RECT_H);
		// 绘制值槽
		// 求出当前值槽宽度
		int w = (int) (percent * (rectW - 4));
		// 求出颜色
		int subIdx = (int) (percent * IMG_RECT_W) - 1;
		g.drawImage(Img.RECT, rect_x + 2, rect_y + 2, rect_x + 2 + w, rect_y
				+ IMG_RECT_H + 2, subIdx, 0, subIdx + 1, IMG_RECT_H, null);
		// 绘制标题
		g.setColor(Color.WHITE);
		g.setFont(DEF_FONT);
		g.drawString(title, rect_x + 4, rect_y + 23);
		if (number != null) {
			g.drawString(number, rect_x + 248, rect_y + 23);
		}
	}

	/**
	 * 在正中绘图
	 */
	protected void drawImageAtCenter(Image img, Graphics g) {
		int imgW = img.getWidth(null);
		int imgH = img.getHeight(null);
		g.drawImage(img, this.x + (this.w - imgW >> 1), this.y
				+ (this.h - imgH >> 1), null);
	}

	public void setDto(GameDto dto) {
		this.dto = dto;
	}

}