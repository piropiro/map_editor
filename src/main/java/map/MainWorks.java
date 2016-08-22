package map;

import javax.swing.JFrame;

import mine.file.FileWorks;

public interface MainWorks extends FileWorks {

	public void keep();

	/*** PalletLoader ************************/
	public void setPallet();
	public JFrame getFrame();

	/*** Paint *****************************/

	public int getColor();
	public void setColor(int color);
	public void setPoints(int x, int y);
	public void setPoints(int x, int y, int w, int h);
	public int[][] getCopyData();
	public void setCopyData(int[][] data);
}
