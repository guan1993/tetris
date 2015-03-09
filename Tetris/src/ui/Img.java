package ui;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import config.GameConfig;

public class Img {

	private Img() {
	}

	/**
	 * 方块
	 */
	public static Image ACT = new ImageIcon("Graphics/game/rect.png")
			.getImage();

	/**
	 * 个人签名
	 */
	public static Image SIGN = new ImageIcon("Graphics/string/sign.png")
			.getImage();

	/**
	 * 数据库
	 */
	public static Image DB = new ImageIcon("Graphics/string/db.png").getImage();

	/**
	 * 磁盘
	 */
	public static Image DISK = new ImageIcon("Graphics/string/disk.png")
			.getImage();

	/**
	 * 等级
	 */
	public static Image LEVEL = new ImageIcon("Graphics/string/level.png")
			.getImage();

	/**
	 * 分数
	 */
	public static Image SCORE = new ImageIcon("Graphics/string/score.png")
			.getImage();

	/**
	 * 消行
	 */
	public static Image REDUCE = new ImageIcon("Graphics/string/reduce.png")
			.getImage();

	/**
	 * 矩形值槽图
	 */
	public static Image RECT = new ImageIcon("Graphics/window/rect.png")
			.getImage();

	/**
	 * 窗口
	 */
	public static Image WINDOW = new ImageIcon("Graphics/window/Window.png")
			.getImage();

	/**
	 * 阴影
	 */
	public static Image SHADOW = new ImageIcon("Graphics/window/shadow.png")
			.getImage();

	/**
	 * 数字图片
	 */
	public static Image NUMBER = new ImageIcon("Graphics/string/number.png")
			.getImage();

	/**
	 * 暂停
	 */
	public static Image PAUSE = new ImageIcon("Graphics/string/pause.png")
			.getImage();

	/**
	 * 背景
	 */
	public static List<Image> BG_LIST;

	/**
	 * 开始按钮
	 */
	public static ImageIcon BTN_START = new ImageIcon(
			"Graphics/string/start.png");

	/**
	 * 设置按钮
	 */
	public static ImageIcon BTN_CONFIG = new ImageIcon(
			"Graphics/string/config.png");

	/**
	 * 下一个方块
	 */
	public static final Image[] NEXT_ACT;
	static {
		// 下一个方块图片
		NEXT_ACT = new Image[GameConfig.getSystemConfig().getTypeConfig()
				.size()];
		for (int i = 0; i < NEXT_ACT.length; i++) {
			NEXT_ACT[i] = new ImageIcon("Graphics/game/" + i + ".png")
					.getImage();
		}
		// 背景图片
		File dir = new File("Graphics/background");
		File[] files = dir.listFiles();
		BG_LIST = new ArrayList<Image>();
		for (File file : files) {
			if (!file.isDirectory()) {
				BG_LIST.add(new ImageIcon(file.getPath()).getImage());
			}
		}
	}
	
}
