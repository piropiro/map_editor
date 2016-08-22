package map.paint;

import java.awt.Component;

import map.MainWorks;
import map.common.Box;
import map.unit.Page;
import map.unit.UnitWorks;
import mine.MineUtils;
import mine.event.CommandListener;
import mine.paint.UnitMap;

public class Select extends PaintBase implements CommandListener {


	private boolean catchFlag; // 状態フラグ
	private int oldx, oldy; // 前位置
	private SelectMenu menu; // ポップアップメニュー

	private Box dragBox;
	private int[][] catchData; // 選択しているデータ

	/**
	 * コンストラクタ
	 * @param mw
	 * @param uw
	 * @param map
	 * @param invoker
	 */
	public Select(MainWorks mw, UnitWorks uw, UnitMap map, Component invoker) {
		super(mw, uw, map);
		catchFlag = false;
		dragBox = null;
		catchData = null;
		menu = new SelectMenu(invoker, this);
	}

	/**
	 * コピー
	 */
	public void copyButton() {
		if (dragBox != null) {
			mw.setCopyData(catchData);
		}
	}

	/**
	 * カット
	 */
	public void cutButton() {
		if (dragBox != null) {
			copyButton();  
			map.clear(Page.COPY, -1);
			map.clear(Page.AMI, 0);
			catchData = null;
			dragBox = null;
			uw.repaint();
			mw.keep();
		}
	}

	/**
	 * ペースト
	 */
	public void pasteButton() {
		if (dragBox != null) {
			rectRelease();
			uw.repaint();
		} else {
			int[][] copyData = mw.getCopyData();
			if (copyData != null) {
				catchData = copyData;
				int w = copyData[0].length;
				int h = copyData.length;
				int x = oldx - w/2;
				int y = oldy - h/2;
				dragBox = new Box(x, y, w, h);
				paste();
				uw.repaint();
			}
		}
	}

	private void paste() {
		map.clear(Page.COPY, -1);
		map.setData(
			Page.COPY,
			dragBox.getX(),
			dragBox.getY(),
			catchData
		);
		map.clear(Page.AMI, 0);
		map.fillRect(
			Page.AMI,
			dragBox.getX(),
			dragBox.getY(),
			dragBox.getW(),
			dragBox.getH(),
			1
		);
	}


	/**
	 * 縦反転
	 */
	public void vflipButton() {
		if (dragBox != null) {
			int width = catchData[0].length;
			int height = catchData.length;
			int[] af = { 1, 0, 0, 0, -1, height - 1 };
			int[][] data = new int[height][width];
			MineUtils.affine(catchData, data, af);
			catchData = data;
			paste();
			uw.repaint();
		}
	}

	/**
	 * 横反転
	 */
	public void hflipButton() {
		if (dragBox != null) {
			int width = catchData[0].length;
			int height = catchData.length;
			int[] af = { -1, 0, width - 1, 0, 1, 0 };
			int[][] data = new int[height][width];
			MineUtils.affine(catchData, data, af);
			catchData = data;
			paste();
			uw.repaint();
		}
	}

	/**
	 * 左回転
	 */
	public void lrotateButton() {
		if (dragBox != null) {
			int width = catchData[0].length;
			int height = catchData.length;
			int[] af = { 0, 1, 0, -1, 0, width - 1 };
			int[][] data = new int[width][height];
			MineUtils.affine(catchData, data, af);
			catchData = data;
			dragBox.setSize(height, width);
			paste();
			uw.repaint();
		}
	}

	/**
	 * 右回転
	 */
	public void rrotateButton() {
		if (dragBox != null) {
			int width = catchData[0].length;
			int height = catchData.length;
			int[] af = { 0, -1, height - 1, 1, 0, 0 };
			int[][] data = new int[width][height];
			MineUtils.affine(catchData, data, af);
			catchData = data;
			dragBox.setSize(height, width);
			paste();
			uw.repaint();
		}
	}

	/**
	 * 範囲キャッチ
	 */
	private boolean rectCatch() {
		if (dragBox == null) {
			return false;
		} else { 
			catchFlag = true;

			int x = dragBox.getX();
			int y = dragBox.getY();
			int w = dragBox.getW();
			int h = dragBox.getH();

			if (w == 1 && h == 1) {
				map.setData(Page.AMI, x, y, 0);
				return false;
			} else {
				catchData = map.getData(Page.BACK, x, y, w, h);
				map.copyRect(Page.BACK, x, y, w, h, Page.COPY, x, y);
				map.fillRect(Page.BACK, x, y, w, h, 0);
				return true;
			}
		}
	}

	/**
	 * 範囲リリース
	 */
	private void rectRelease() {
		catchFlag = false;
		if (dragBox != null) {
			map.setData(
				Page.BACK,
				dragBox.getX(),
				dragBox.getY(),
				catchData
			);
			map.clear(Page.COPY, -1);
			map.clear(Page.AMI, 0);
			mw.keep();
		}
	}

	/**
	 * 範囲移動
	 */
	private void rectMove(int x, int y){
		dragBox.setLocation(
			dragBox.getX() + x - oldx,
			dragBox.getY() + y - oldy
		);
		oldx = x;
		oldy = y;
		paste();
	}

	/**
	 * 左クリック
	 */
	public void leftPressed(int x, int y) {
		oldx = x;
		oldy = y;
		if (map.getData(Page.AMI, x, y) == 0) {
			rectRelease();
			dragBox = new Box(x, y, 1, 1);
		} else {
			catchFlag = true;
		}
	}

	/**
	 * 左リリース
	 */
	public void leftReleased(int x, int y) {
		if (catchFlag) {
			return;
		} else if (rectCatch()) {
			return;
		} else {
			uw.repaint();
			dragBox = null;
			catchData = null;
		}
	}

	/**
	 * 左ドラッグ
	 */
	public void leftDragged(int x, int y) {
		if (dragBox != null) {
			if (catchFlag) {
				rectMove(x, y);
			} else {
				dragBox.setB(
					MineUtils.mid(0, x, map.getMapWidth() - 1),
					MineUtils.mid(0, y, map.getMapHeight() - 1));
				map.clear(Page.BUFFER, 0);
				map.fillRect(
					Page.BUFFER,
					dragBox.getX(),
					dragBox.getY(),
					dragBox.getW(),
					dragBox.getH(),
					1);
				map.copyPage(Page.BUFFER, Page.AMI);
			}
			mw.setPoints(dragBox.getX(), dragBox.getY(), dragBox.getW(), dragBox.getH());
			uw.ppaint();
		}
	}

	/**
	 * 右クリック
	 */
	public void rightPressed(int x, int y) {
	}

	public void rightReleased(int x, int y) {
		oldx = x;
		oldy = y;
		menu.show( x * map.getTileWidth(), y * map.getTileHeight());
	}
	
	public void destroy(){
		rectRelease();
		map.clear(Page.COPY, -1);
		map.clear(Page.AMI, 0);
		uw.repaint();
	}

	/**
	 * アクション
	 */
	public void doCommand(String command) {
		if (command.equals("Copy"))
			copyButton();
		else if (command.equals("Cut"))
			cutButton();
		else if (command.equals("Paste"))
			pasteButton();
		else if (command.equals("HFlip"))
			hflipButton();
		else if (command.equals("VFlip"))
			vflipButton();
		else if (command.equals("LRotate"))
			lrotateButton();
		else if (command.equals("RRotate"))
			rrotateButton();
	}
}
