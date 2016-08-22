package map;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;

import map.pallet.PalletList;
import mine.awt.CommandMenuBar;
import mine.event.CommandListener;
import mine.file.FileCommand;

class MapMenuBar extends CommandMenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6115666436996788261L;
	private JMenu fileMenu;
	private JMenu palletMenu;

	/*** コンストラクタ ****************************/

	public MapMenuBar(CommandListener listener) {
		super(listener);

		fileMenu = newMenu("ファイル(F)", 'F');
		add(fileMenu);

		JMenu editMenu = newMenu("編集(E)", 'E');
		editMenu.add(newItem("元に戻す(U)", "Undo", 'U', 'Z'));
		editMenu.add(newItem("やり直し(R)", "Redo", 'R', 'Y'));
		editMenu.add(newItem("マップサイズ(M)", "ResizeMap", 'M'));
		add(editMenu);

		JMenu viewMenu = newMenu("表示(V)", 'V');
		viewMenu.add(newItem("拡大(U)", "ZoomIn", 'U', KeyEvent.VK_UP));
		viewMenu.add(newItem("縮小(D)", "ZoomOut", 'D', KeyEvent.VK_DOWN));
		viewMenu.addSeparator();
		viewMenu.add(newItem("1 100%", "Zoom100", KeyEvent.VK_1));
		viewMenu.add(newItem("2 75%", "Zoom75", KeyEvent.VK_2));
		viewMenu.add(newItem("3 50%", "Zoom50", KeyEvent.VK_3));
		viewMenu.add(newItem("4 25%", "Zoom25", KeyEvent.VK_4));
		add(viewMenu);

		palletMenu = newMenu("パレット(P)", 'P');
		add(palletMenu);
	}

	/**
	 * Fileメニューを設定する。
	 */
	public void setMapList(String[] fileNames){
		fileMenu.removeAll();
		fileMenu.add(newItem("新規作成(N)", FileCommand.NEW, 'N', 'N'));
		fileMenu.add(newItem("開く(O)", FileCommand.OPEN, 'O', 'O'));
		fileMenu.add(newItem("上書き保存(S)", FileCommand.SAVE, 'S', 'S'));
		fileMenu.add(newItem("名前を付けて保存(A)", FileCommand.SAVE_AS, 'A'));
		fileMenu.addSeparator();

		for (int i=0; i<fileNames.length; i++) {
			fileMenu.add(newItem(i + " " + fileNames[i], FileCommand.OPEN_AT + i, '0' + i));
		}

		fileMenu.addSeparator();
		fileMenu.add(newItem("終了(X)", "Exit", 'X'));
	}

	/**
	 * Palletメニューを設定する。
	 */
	public void setPalletList(PalletList list){
		palletMenu.removeAll();
		palletMenu.add(newItem("開く(O)", "PalletOpen", 'O'));
		palletMenu.add(newItem("タイルサイズ(T)", "ResizeTile", 'T'));
		
		palletMenu.addSeparator();
		
		for (int i=0; i<list.getSize(); i++) {
			palletMenu.add(newItem(i + " " + list.getName(i), "PalletHistory" + i, '0' + i));
		}
	}
}
