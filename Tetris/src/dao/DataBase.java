package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dto.Player;

public class DataBase implements Data {

	/**
	 * 数据库URL
	 */
	private final String dbUrl;

	/**
	 * 用户名
	 */
	private final String dbUser;

	/**
	 * 密码
	 */
	private final String dbPwd;

	private static final String LOAD_SQL = "SELECT user_name,point FROM user_point WHERE type_id = 1 ORDER BY point DESC limit 5";

	private static String SAVE_SQL = "INSERT INTO user_point(user_name,point,type_id) VALUES (?,?,?)";

	public DataBase(HashMap<String, String> param) {
		this.dbUrl = param.get("dbUrl");
		this.dbUser = param.get("dbUser");
		this.dbPwd = param.get("dbPwd");
		try {
			Class.forName(param.get("driver"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Player> loadData() {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Player> players = null;
		try {
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
			stmt = conn.prepareStatement(LOAD_SQL);
			rs = stmt.executeQuery();
			players = new ArrayList<Player>();
			while (rs.next()) {
				Player pla = new Player(rs.getString(1), rs.getInt(2));
				players.add(pla);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return players;
	}

	@Override
	public void saveData(Player player) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
			stmt = conn.prepareStatement(SAVE_SQL);
			stmt.setString(1, player.getName());
			stmt.setInt(2, player.getPoint());
			stmt.setInt(3, 1);
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

}
