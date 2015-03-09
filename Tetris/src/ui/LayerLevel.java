package ui;

import java.awt.Graphics;

public class LayerLevel extends Layer {

	/**
	 * 标题图片的宽度值
	 */
	private static final int IMG_LEVEL_W = Img.LEVEL.getWidth(null);

	public LayerLevel(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	public void paint(Graphics g) {
		this.createWindow(g);
		// 窗口标题 居中显示
		int centerX = this.x + (this.w - IMG_LEVEL_W >> 1);
		g.drawImage(Img.LEVEL, centerX, y + PADDING, null);
		// 显示等级
		this.drawNumberLeftPad(-20, 64, this.dto.getNowLevel(), 5, g);
	}

}
