package map.paint;

import map.MainWorks;
import map.unit.UnitWorks;
import mine.paint.UnitMap;

public class BoxLine extends BoxFill {

	/**
	 * コンストラクタ
	 * @param mw
	 * @param uw
	 * @param map
	 */
	public BoxLine(MainWorks mw, UnitWorks uw, UnitMap map) {
		super(mw, uw, map);
	}

	/**
	 * 描画
	 */
	protected void writeBox(int p, int color) {
		map.drawRect(p, cr.getX(), cr.getY(), cr.getW(), cr.getH(), color);
	}
}
