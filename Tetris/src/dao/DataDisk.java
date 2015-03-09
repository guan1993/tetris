package dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;

import dto.Player;

public class DataDisk implements Data {

	private final String filePaht;

	public DataDisk(HashMap<String, String> param) {
		this.filePaht = param.get("path");
	}

	@Override
	public List<Player> loadData() {
		ObjectInputStream ois = null;
		List<Player> players = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(filePaht));
			players = (List<Player>) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return players;
	}

	@Override
	public void saveData(Player player) {
		// 先取出数据
		List<Player> players = this.loadData();
		// 追加新记录
		players.add(player);
		// 重新写入
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(filePaht));
			oos.writeObject(players);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				oos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
