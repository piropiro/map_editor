package map.pallet;

import java.io.File;


import map.MainWorks;
import map.common.Texts;
import mine.MineException;
import mine.file.FileUtils;
import mine.io.BeanIO;
import mine.util.History;

public class PalletListL implements PalletList {

	static final String DB_FILE = "pallets.xml";

	private MainWorks mw;
	private History<Pallet> history;
	private Pallet currentPallet;
	
	private int width = 32;
	private int height = 32;

	/**
	 * コンストラクタ
	 */
	public PalletListL(MainWorks mw) {
		this.mw = mw;
		history = new History<Pallet>();
		load();
	}

	/**
	 * 開く
	 */
	public boolean open() {
		try {
			File file = FileUtils.openFile(currentPallet.getImagePath(), mw.getFrame());
			currentPallet = new Pallet();
			currentPallet.setImagePath(file.getPath());
			currentPallet.setTileWidth(width);
			currentPallet.setTileHeight(height);
			save();
			return true;
		} catch (MineException e) {
			// ファイル選択をキャンセルした。
			return false;
		}
	}

	/**
	 * 指定のファイルを開く
	 */
	public boolean open(int i) {
		Pallet pallet = history.get(i);
		if (pallet != null) {
			currentPallet = pallet;
			save();
			return true;
		} else {
			return false;
		}
	
	}



	/**
	 * ファイル名取得
	 */
	public String getName(int i) {
		File file = new File(history.get(i).getImagePath());
		return file.getName();
	}

	public String getName() {
		if (currentPallet == null) {
			return "None";
		} else {
			return new File(currentPallet.getImagePath()).getName();
		}
	}


	/**
	 * タイルサイズ設定
	 */
	public void setTileSize(int width, int height) {	
		currentPallet.setTileWidth(width);
		currentPallet.setTileHeight(height);
	}

	/**
	 * データ取得
	 */
	public String getImagePath(){
		return currentPallet.getImagePath();
	}
	public int getTileWidth(){
		return currentPallet.getTileWidth();
	}
	public int getTileHeight(){
		return currentPallet.getTileHeight();
	}
	public int getSize(){
		return history.size();
	}

	/**
	 * データロード
	 */
	private void load() {
		File file = new File(DB_FILE);
		if (file.exists()) {
			try {
				history.reset((Pallet[])BeanIO.read(file.getPath()));
			} catch (MineException e) {
				e.printStackTrace();
				init();
			}
		} else {
			init();
		}
		currentPallet = history.get(0);
	}

	/**
	 * 初期化
	 */
	private void init() {
		history.clear();
		Pallet pallet = new Pallet();
		pallet.setImagePath(Texts.IMAGE_FILE);
		pallet.setTileWidth(width);
		pallet.setTileHeight(height);
		history.add(pallet);
	}

	/**
	 * データセーブ
	 */
	public void save() {
		history.add(currentPallet);
		try {
			BeanIO.write(DB_FILE, history.toArray(new Pallet[0]));
		} catch (MineException e) {
			e.printStackTrace();
		}
	}
}
