package map.pallet;

public interface PalletList {

	public boolean open();
	public boolean open(int n);

	public String getName();
	public String getName(int n);

	public void setTileSize(int width, int height);

	public String getImagePath();
	public int getTileWidth();
	public int getTileHeight();

	public int getSize();
}
