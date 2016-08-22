package map.unit;

import java.awt.Graphics;
import mine.util.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import map.paint.PaintListener;
import mine.awt.GraphicsAWT;
import mine.awt.MineAwtUtils;
import mine.paint.PaintBox;
import mine.paint.UnitMap;

public class UnitPanel extends JComponent implements UnitWorks, MouseListener, MouseMotionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5018908992638383115L;

	static final int P_TRUE = 0;
	static final int P_FALSE = 1;
	static final int P_PAINT = 2;
	static final int P_CLEAR = 3;

	private UnitMap map;
	private PaintListener pl;

	private Point ps;

	/**
	 * コンストラクタ
	 */
	public UnitPanel() {
		super();
		addMouseMotionListener(this);
		addMouseListener(this);
		ps = new Point(0, 0);
	}

	/**
	 * リセット
	 */
	public void reset(UnitMap new_map) {
		this.map = new_map;
		int width = map.getMapWidth() * map.getTileWidth();
		int height = map.getMapHeight() * map.getTileHeight();
		MineAwtUtils.setSize(this, width, height);
		repaint();
	}

	/**
	 * ペイントリスナー登録
	 */
	public synchronized void setPaintListener(PaintListener pls) {
		if (pl != null) {
			pl.destroy();
		}
		pl = pls;

	}

	/**
	 * 描画
	 */
	protected void paintComponent(Graphics g) {
		map.draw(new GraphicsAWT(g));
	}

	/**
	 * 部分描画
	 */
	public void ppaint(int x, int y) {
		int tileW = map.getTileWidth();
		int tileH = map.getTileHeight();

		repaint(x * tileW, y * tileH, tileW, tileH);
	}

	/**
	 * 部分描画
	 */
	public void ppaint() {
		PaintBox box = map.getPaintBox();
		repaint(box.getX(), box.getY(), box.getW(), box.getH());
	}

	/**
	 * 座標計算
	 */
	private Point getWaku(MouseEvent e) {
		int tileW = map.getTileWidth();
		int tileH = map.getTileHeight();
		int x = e.getX() / tileW;
		int y = e.getY() / tileH;
		return new Point(x, y);
	}

	/**
	 * マウス
	 */
	public void mousePressed(MouseEvent e) {
		Point p = getWaku(e);
		int xNum = map.getMapWidth();
		int yNum = map.getMapHeight();
		if (p.x < 0 || p.x >= xNum)
			return;
		if (p.y < 0 || p.y >= yNum)
			return;

		if (SwingUtilities.isLeftMouseButton(e)) {
			pl.leftPressed(p.x, p.y);
		} else if (SwingUtilities.isRightMouseButton(e)) {
			pl.rightPressed(p.x, p.y);
		}
	}

	public void mouseReleased(MouseEvent e) {
		Point p = getWaku(e);

		if (SwingUtilities.isLeftMouseButton(e)) {
			pl.leftReleased(p.x, p.y);
		} else if (SwingUtilities.isRightMouseButton(e)) {
			pl.rightReleased(p.x, p.y);
		}
	}

	public void mouseClicked(MouseEvent e) {
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}

	/**
	 * マウスモーション
	 */
	public void mouseMoved(MouseEvent e) {
		Point p = getWaku(e);
		int xNum = map.getMapWidth();
		int yNum = map.getMapHeight();
		if (p.x < 0 || p.x >= xNum)
			return;
		if (p.y < 0 || p.y >= yNum)
			return;

		if (p.x != ps.x || p.y != ps.y) {
			pl.mouseMoved(p.x, p.y);
			ps = p;
		}
	}

	/**
	 * マウスドラッグ
	 */
	public void mouseDragged(MouseEvent e) {
		Point p = getWaku(e);

		if (p.x == ps.x && p.y == ps.y)
			return;
		ps = p;

		if (SwingUtilities.isLeftMouseButton(e)) {
			pl.leftDragged(p.x, p.y);
		}
	}

}
