package map.paint;

import map.MainWorks;
import map.unit.Page;
import map.unit.UnitWorks;
import mine.paint.UnitMap;

public class Painter extends PaintBase {

	/**
	 * コンストラクタ
	 * @param mw
	 * @param uw
	 * @param map
	 */
	public Painter(MainWorks mw, UnitWorks uw, UnitMap map) {
		super(mw, uw, map);
	}

	/**
	 * 左クリック
	 */
	public void leftPressed(int x, int y) {
		if (map.getData(Page.BACK, x, y) != mw.getColor()) {
			map.paint(Page.BACK, x, y, mw.getColor());
			mw.keep();
			uw.ppaint();
		}
	}
}
