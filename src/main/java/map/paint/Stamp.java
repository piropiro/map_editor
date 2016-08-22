package map.paint;

import map.MainWorks;
import map.unit.Page;
import map.unit.UnitWorks;
import mine.paint.UnitMap;

public class Stamp extends PaintBase {

	private boolean keepFlag;

	/**
	 * コンストラクタ
	 * @param mw
	 * @param uw
	 * @param map
	 */
	public Stamp(MainWorks mw, UnitWorks uw, UnitMap map) {
		super(mw, uw, map);
	}
	
	/**
	 * 左クリック
	 */
	public void leftPressed(int x, int y) {
		keepFlag = false;
		draw(x, y);
	}

	/**
	 * 左ドラッグ
	 */
	public void leftDragged(int x, int y) {
		draw(x, y);
		mw.setPoints(x, y);
	}

	/**
	 * 左リリース
	 * @param x
	 * @param y
	 */
	public void leftReleased(int x, int y) {
		if (keepFlag) {
			mw.keep();
		}
	}

	/**
	 * 描画
	 */
	private void draw(int x, int y) {
		int[][] data = mw.getCopyData();
		if (data != null) {
			if (!keepFlag) {
				keepFlag = true;
			}
			map.setData(Page.BACK, x, y, data);
			uw.ppaint();
		}
	}
}
