package map.common;

public class Box {

	private int ax;
	private int ay;
	private int bx;
	private int by;

	public Box() {
		ax = 0;
		ay = 0;
		bx = 0;
		by = 0;
	}

	public Box(int x, int y, int width, int height) {
		this.ax = x;
		this.ay = y;
		this.bx = x + width - 1;
		this.by = y + height - 1;
	}

	public Box copy() {
		return new Box(ax, ay, bx, by);
	}

	/**
	 * データ設定
	 */
	public void setA(int ax, int ay) {
		this.ax = ax;
		this.ay = ay;
	}
	public void setB(int bx, int by) {
		this.bx = bx;
		this.by = by;
	}
	public void setLocation(int x, int y) {
		int w = getW();
		int h = getH();
		ax = x;
		ay = y;
		bx = x + w - 1;
		by = y + h - 1;
	}
	public void setSize(int w, int h) {
		ax = Math.min(ax, bx);
		ay = Math.min(ay, by);
		bx = ax + w - 1;
		by = ay + h - 1;
	}

	/**
	 * データ取得
	 */
	public int getX() {
		return Math.min(ax, bx);
	}
	public int getY() {
		return Math.min(ay, by);
	}
	public int getW() {
		return Math.abs(ax - bx) + 1;
	}
	public int getH() {
		return Math.abs(ay - by) + 1;
	}
}
