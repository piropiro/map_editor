package map;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import map.common.ImageUtils;
import map.common.SizeDialog;
import map.common.Texts;
import map.paint.BoxFill;
import map.paint.BoxLine;
import map.paint.Painter;
import map.paint.Pencil;
import map.paint.Select;
import map.paint.Stamp;
import map.pallet.PalletList;
import map.pallet.PalletListL;
import map.pallet.PalletPanel;
import map.unit.Page;
import map.unit.UnitPanel;
import mine.MineException;
import mine.MineUtils;
import mine.awt.ImageLoaderAWT;
import mine.event.CommandListener;
import mine.file.FileCommand;
import mine.file.FileManager;
import mine.io.JsonIO;
import mine.paint.MineImage;
import mine.paint.MineImageLoader;
import mine.paint.UnitMap;
import mine.util.Mement;

public class MainFrame extends JFrame implements MainWorks, CommandListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2023506370023740434L;
	private MapMenuBar mb; // メニューバー
	private MapToolBar tb; // ツールバー
	private UnitPanel up; // マップパネル
	private PalletPanel pp; // パレットパネル
	private UnitMap map; // ユニットマップ
	private JLabel points; // 座標表示

	private FileManager fileManager; // ファイル管理
	private PalletList plist; // パレットリスト

	private int color; // ユニットカラー

	private double rate = 1.0; // 表示比率

	private Mement<int[][]> mement; // Undo用マップ保持

	private int[][] copyData; // コピーデータ
	
	private MineImageLoader mil;

	/*** コンストラクタ *****************************************************/

	MainFrame() {
		super("MapEditor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mil = new ImageLoaderAWT();
		plist = new PalletListL(this);
		fileManager = new FileManager("maps.txt", 10, Texts.MAP_FILE, this, this);
		mement = new Mement<int[][]>(100);

		initPanel();

		try {
			fileManager.load();
		} catch (Exception e) {
			fileManager.create();
		}
		up.setPaintListener(new Pencil(this, up, map));
	}

	/*** パネル設定 **************************************************/

	private void initPanel() {
		mb = new MapMenuBar(this);
		mb.setMapList(fileManager.getNameList());
		mb.setPalletList(plist);
		tb = new MapToolBar(this);
		tb.setEnabled("Undo", mement.isUndoable());
		tb.setEnabled("Redo", mement.isRedoable());
		tb.setEnabled("ZoomIn", rate < 1.0);
		tb.setEnabled("ZoomOut", rate > 0.25);

		up = new UnitPanel();
		pp = new PalletPanel(this, plist);
		points = new JLabel(" Point:");

		setJMenuBar(mb);

		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		rightPanel.add(new JScrollPane(up), "Center");
		rightPanel.add(points, "South");

		JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(pp), rightPanel);
		jsp.setDividerLocation(pp.getWidth());

		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(tb, "North");
		contentPane.add(jsp, "Center");

		setContentPane(contentPane);
	}

	/*** ユニットマップ設定 ***********************************************/

	private void initMap(int[][] data) {

		int xNum = data[0].length;
		int yNum = data.length;

		map = new UnitMap(4, xNum, yNum, mil);
		map.setVisible(Page.BACK, true);
		map.setVisible(Page.COPY, true);
		map.setVisible(Page.AMI, true);
		map.setPage(Page.BACK, data);
		map.clear(Page.COPY, -1);
		
		keep();
		resetMap();
	}

	/*** ユニットマップ再設定 *****************************************/

	private void resetMap() {

		int tileW = (int) (plist.getTileWidth() * rate);
		int tileH = (int) (plist.getTileHeight() * rate);

		MineImage unit[][] = ImageUtils.getUnitImage(mil, plist, rate);
		MineImage ami[] = ImageUtils.getAmiImage(tileW, tileH);

		map.setTile(Page.BACK, unit, -1);
		map.setTile(Page.COPY, unit, -1);
		map.setTile(Page.AMI, ami, 0);
		map.repaint();

		up.reset(map);

		setTitle(fileManager.getNameList()[0] + " - MapEditor");
	}

	/** 
	 * Undo Redo
	 */
	public void keep() {
		int[][] oldmap = (int[][])mement.getCurrent();
		int[][] newmap = map.getPage(Page.BACK);
		if (oldmap == null || !MineUtils.compare(oldmap, newmap)) {
			mement.keep(newmap);
			tb.setEnabled("Undo", mement.isUndoable());
			tb.setEnabled("Redo", mement.isRedoable());
		}
	}

	private void undo() {
		int[][] data = (int[][]) mement.undo();
		map.setPage(Page.BACK, data);
		tb.setEnabled("Undo", mement.isUndoable());
		tb.setEnabled("Redo", mement.isRedoable());
	}

	private void redo() {
		int[][] data = (int[][]) mement.redo();
		map.setPage(Page.BACK, data);
		tb.setEnabled("Undo", mement.isUndoable());
		tb.setEnabled("Redo", mement.isRedoable());
	}

	private void setRate(double rate) {
		this.rate = Math.max(0.25, Math.min(rate, 1.0));
		resetMap();
		tb.setEnabled("ZoomIn", rate < 1.0);
		tb.setEnabled("ZoomOut", rate > 0.25);
	}

	/*** マップサイズ変更 ********************************************/

	public void resizeMap() {
		SizeDialog sd = new SizeDialog(this, "Map", map.getMapWidth(), map.getMapHeight());
		sd.setVisible(true);
		if (sd.isOK()) {
			try {
				int w = Integer.parseInt(sd.getSizeWidth());
				int h = Integer.parseInt(sd.getSizeHeight());
				map.setSize(w, h);
				up.reset(map);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * タイルサイズ変更
	 */
	public void resizeTile() {
		SizeDialog sd = new SizeDialog(this, "Tile", plist.getTileWidth(), plist.getTileHeight());
		sd.setVisible(true);
		if (sd.isOK()) {
			try {
				int w = Integer.parseInt(sd.getSizeWidth());
				int h = Integer.parseInt(sd.getSizeHeight());
				plist.setTileSize(w, h);
				setPallet();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
	}

	/*** MainWorks - PalletLoader ********************************************/

	public JFrame getFrame() {
		return this;
	}
	public void setPallet() {
		resetMap();
		up.reset(map);
		pp.reset();
		pp.setColor(color);
	}

	/*** MainWorks - Paint *****************************************************/

	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
		pp.setColor(color);
	}

	public void setPoints(int x, int y) {
		Thread.yield();
		points.setText(" P ( " + x + " , " + y + " )");
	}
	public void setPoints(int x, int y, int w, int h) {
		Thread.yield();
		points.setText(" P ( " + x + " , " + y + " ) >> ( " + w + " x " + h + " )");
	}

	public int[][] getCopyData() {
		if (copyData == null) {
			return null;
		} else {
			return (int[][])copyData.clone();
		}
	}

	public void setCopyData(int[][] copyData) {
		this.copyData = (int[][])copyData.clone();
	}

	public void create(String file) {
		if (map == null) {
			initMap(new int[15][20]);
		} else {
			int y = map.getMapHeight();
			int x = map.getMapWidth();
			initMap(new int[y][x]);
		}
	}

	public void load(String file) throws MineException {
		int[][] data = JsonIO.read(file, int[][].class);
		initMap(data);
		mb.setMapList(fileManager.getNameList());
	}

	public void save(String file) throws MineException {
		int data[][] = map.getPage(Page.BACK);
		JsonIO.write(file, data);
	}

	/*** MainWorks - MenuButton ***********************************************/

	public void doCommand(String command) {

		try {
			if (command.equals(FileCommand.NEW)) {
				fileManager.create();
			}
			if (command.equals(FileCommand.OPEN)) {
				fileManager.open();
			}
			for (int i = 0; i < 10; i++) {
				if (command.equals(FileCommand.OPEN_AT + i)) {
					fileManager.openAt(i);
				}
			}
			if (command.equals(FileCommand.SAVE)) {
				fileManager.save();
			}
			if (command.equals(FileCommand.SAVE_AS)) {
				fileManager.saveAs();
			}
		} catch (MineException e) {
			e.printStackTrace();
		}


		if (command.equals("Exit")) {
			dispose();
		}

		if (command.equals("Undo")) {
			undo();
			up.repaint();
			return;
		}
		if (command.equals("Redo")) {
			redo();
			up.repaint();
			return;
		}
		if (command.equals("ResizeMap")) {
			resizeMap();
			return;
		}

		if (command.equals("ZoomIn")) {
			setRate(rate + 0.25);
			return;
		}
		if (command.equals("ZoomOut")) {
			setRate(rate - 0.25);
			return;
		}
		if (command.equals("Zoom100")) {
			setRate(1.0);
			return;
		}
		if (command.equals("Zoom75")) {
			setRate(0.75);
			return;
		}
		if (command.equals("Zoom50")) {
			setRate(0.5);
			return;
		}
		if (command.equals("Zoom25")) {
			setRate(0.25);
			return;
		}

		if (command.equals("PalletOpen")) {
			if (plist.open()) {
				mb.setPalletList(plist);
				setPallet();
			}
		}

		for (int i = 0; i < plist.getSize(); i++) {
			if (command.equals("PalletHistory" + i)) {
				if (plist.open(i)) {
					mb.setPalletList(plist);
					setPallet();
				}
			}
		}

		if (command.equals("ResizeTile")) {
			resizeTile();
			return;
		}

		if (command.equals("Region"))
			up.setPaintListener(new Select(this, up, map, up));
		if (command.equals("Pencil"))
			up.setPaintListener(new Pencil(this, up, map));
		if (command.equals("Paint"))
			up.setPaintListener(new Painter(this, up, map));
		if (command.equals("Stamp"))
			up.setPaintListener(new Stamp(this, up, map));
		if (command.equals("BoxFill"))
			up.setPaintListener(new BoxFill(this, up, map));
		if (command.equals("BoxLine"))
			up.setPaintListener(new BoxLine(this, up, map));
	}
}
