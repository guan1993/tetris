package ui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class LayerNext extends Layer {

	public LayerNext(int x, int y, int w, int h) {
		super(x, y, w, h);

	}

	public void paint(Graphics g) {
		this.createWindow(g);
		if (this.dto.isStart()) {
			// 画出下一个方块
			this.drawImageAtCenter(Img.NEXT_ACT[this.dto.getNext()], g);
		}
	}

}
