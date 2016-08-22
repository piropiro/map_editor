package map;

import mine.awt.MineAwtUtils;

public class MapEditor {
	public static void main(String[] args) {
		MainFrame mf = new MainFrame();
		mf.pack();
		MineAwtUtils.setCenter(mf);
		mf.setVisible(true);
	}
}
