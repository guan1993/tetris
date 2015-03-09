package dao;

import java.util.ArrayList;
import java.util.List;

import dto.Player;

public class DataTest implements Data {

	@Override
	public List<Player> loadData() {
		List<Player> players = new ArrayList<Player>();
		players.add(new Player("区别", 1330));
		players.add(new Player("rix", 230));
		players.add(new Player("xring", 2330));
		players.add(new Player("arix", 3330));
		players.add(new Player("gzhi", 330));
		return players;
	}

	@Override
	public void saveData(Player player) {
	}

}
