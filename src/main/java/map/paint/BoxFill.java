package map.paint;

import map.MainWorks;
import map.common.Box;
import map.unit.Page;
import map.unit.UnitWorks;
import mine.paint.UnitMap;

public class BoxFill extends PaintBase {

	protected Box cr;

	/**
	 * コンストラクタ
	 * @param mw
	 * @param uw
	 * @param map
	 */
	public BoxFill(MainWorks mw, UnitWorks uw, UnitMap map) {
		super(mw, uw, map);
	}

	/**
	 * 左クリック
	 */
	public void leftPressed(int x, int y) {
		cr = new Box(x, y, 1, 1);
	}

	/**
	 * 左ドラッグ
	 */
	public void leftDragged(int x, int y) {
		if (cr == null)
			return;
		cr.setB(x, y);
		map.clear(Page.BUFFER, 0);
		writeBox(Page.BUFFER, 1);
		map.copyPage(Page.BUFFER, Page.AMI);
		uw.repaint();
		mw.setPoints(cr.getX(), cr.getY(), cr.getW(), cr.getH());
	}

	/*** 左リリース *****************************/

	public void leftReleased(int x, int y) {
		if (cr == null)
			return;
		map.clear(Page.AMI, 0);
		if (cr.getW() > 1 || cr.getH() > 1) {
			writeBox(Page.BACK, mw.getColor());
			mw.keep();
		}
		uw.ppaint();
	}

	/*** 描画 ***********************************/

	protected void writeBox(int p, int color) {
		map.fillRect(p, cr.getX(), cr.getY(), cr.getW(), cr.getH(), color);
	}
}
