package map.paint;

import map.MainWorks;
import map.unit.Page;
import map.unit.UnitWorks;
import mine.paint.UnitMap;

public abstract class PaintBase implements PaintListener {

	protected MainWorks mw;
	protected UnitWorks uw;
	protected UnitMap map;


	/**
	 * コンストラクタ
	 * @param mw
	 * @param uw
	 * @param map
	 */
	public PaintBase(MainWorks mw, UnitWorks uw, UnitMap map) {
		this.mw = mw;
		this.uw = uw;
		this.map = map;
	}

	/**
	 * マウス移動
	 */
	public void mouseMoved(int x, int y) {
		mw.setPoints(x, y);
	}

	/**
	 * 右プレス
	 */
	public void rightPressed(int x, int y) {
		int color = map.getData(Page.BACK, x, y);
		mw.setColor(color);
	}

	public void leftPressed(int x, int y){}
	public void leftReleased(int x, int y){}
	public void rightReleased(int x, int y){}
	public void leftDragged(int x, int y){}
	public void destroy(){}
	
}
