package map.pallet;

import java.io.Serializable;

public class Pallet implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2208911421806544437L;

	private String imagePath;
	
	private int tileWidth;
	
	private int tileHeight;

	public Pallet() {
	}


	/**
	 * @return
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * @return
	 */
	public int getTileHeight() {
		return tileHeight;
	}

	/**
	 * @return
	 */
	public int getTileWidth() {
		return tileWidth;
	}

	/**
	 * @param string
	 */
	public void setImagePath(String string) {
		imagePath = string;
	}

	/**
	 * @param i
	 */
	public void setTileHeight(int i) {
		tileHeight = i;
	}

	/**
	 * @param i
	 */
	public void setTileWidth(int i) {
		tileWidth = i;
	}

}
