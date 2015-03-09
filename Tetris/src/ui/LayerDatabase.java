package ui;

import java.awt.Graphics;
import java.util.List;

import dto.Player;

public class LayerDatabase extends LayerData{
	
	public LayerDatabase(int x, int y, int w, int h) {
		super(x, y, w, h);
	}
	
	public void paint(Graphics g){
		this.createWindow(g);
		this.showData(Img.DB, this.dto.getDbRecorder(), g);
	}

}
