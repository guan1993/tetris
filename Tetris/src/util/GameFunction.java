package util;

public class GameFunction {

	/**
	 * 计算线程休眠时间
	 */
	public static long getSleepTimeByLevel(int level) {
		long sleepTime = (-40 * level + 540);
		sleepTime = sleepTime < 100 ? 100 : sleepTime;
		return sleepTime;
	}

}
