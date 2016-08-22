package map.pallet;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

import map.MainWorks;
import map.common.ImageUtils;
import mine.awt.GraphicsAWT;
import mine.awt.ImageLoaderAWT;
import mine.awt.MineAwtUtils;
import mine.paint.MineImage;
import mine.paint.MineImageLoader;
import mine.paint.UnitMap;

public class PalletPanel extends JComponent
	implements MouseListener {

	private static final long serialVersionUID = 4550317690695621513L;

	static final int P_BACK = 0;
	static final int P_AMI = 1;

	private PalletList plist;
	private MainWorks mw;
	private UnitMap map;

	/**
	 * コンストラクタ
	 */
	public PalletPanel(MainWorks mw, PalletList plist) {
		super();
		this.mw = mw;
		this.plist = plist;
		addMouseListener(this);
		reset();
	}

	/**
	 * リセット
	 */
	public void reset() {
		mapInit();
		setColor(0);
	}

	/**
	 * ユニットマップ設定
	 */
	public void mapInit() {
			int tileW = plist.getTileWidth();
			int tileH = plist.getTileHeight();
			MineImageLoader mil = new ImageLoaderAWT();
	
			MineImage[][] back = ImageUtils.getUnitImage(mil, plist, 1.0);
			MineImage[] waku = ImageUtils.getAmiImage(tileW, tileH);
	
			int iNum = back.length * back[0].length;
	
			int xNum =  160 / tileW;
			int yNum = iNum / xNum + 1;
	
			map = new UnitMap(2, xNum, yNum, mil);
			map.setTile(P_BACK, back, UnitMap.FALSE);
			map.setTile(P_AMI, waku, 0);
			map.setVisible(P_BACK, true);
			map.setVisible(P_AMI, true);
	
			map.clear(P_BACK, UnitMap.FALSE);
			for (int n = 0; n < iNum; n++) {
				map.setData(P_BACK, n % xNum, n / xNum, n);
			}
			
			int width = map.getMapWidth() * map.getTileWidth();
			int height = map.getMapHeight() * map.getTileHeight();
			MineAwtUtils.setSize(this, width, height);
	}

	/**
	 * 描画
	 */
	protected void paintComponent(Graphics g) {
		map.draw(new GraphicsAWT(g));
	}

	/**
	 * マウスイベント
	 */
	public void mouseClicked(MouseEvent e) {
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	public void mouseReleased(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		int x = e.getX() / map.getTileWidth();
		int y = e.getY() / map.getTileHeight();
		int color = map.getData(P_BACK, x, y);
		if (color != UnitMap.FALSE) {
			map.clear(P_AMI, 0);
			map.setData(P_AMI, x, y, 1);
			mw.setColor(color);
			repaint();
		}
	}

	/**
	 * セットカラー
	 */
	public void setColor(int color) {
		map.clear(P_AMI, 0);
		int x = color % map.getMapWidth();
		int y = color / map.getMapWidth();
		map.setData(P_AMI, x, y, 1);
		repaint();
	}
}
