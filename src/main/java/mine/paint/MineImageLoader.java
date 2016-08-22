/*
 * Created on 2004/10/10
 */
package mine.paint;

import mine.MineException;

/**
 * @author saito
 */
public interface MineImageLoader {
	public MineImage load(String path) throws MineException;

	public MineImage[][] loadTile(String path, int width, int height) throws MineException;

	public MineImage getBuffer(int width, int height);

	/**
	 * イメージを指定されたサイズに拡大縮小します。
	 * @param img - ソースイメージ
	 * @param width - 指定された幅
	 * @param height - 指定された高さ
	 * @return 指定されたサイズに拡大縮小されたイメージ
	 */
	public MineImage resize(MineImage img, int width, int height);

	/**
	 * イメージを指定された比率で拡大縮小します。
	 * @param img - ソースイメージ
	 * @param rate - サイズ比率
	 * @return 指定された比率で拡大縮小されたイメージ
	 */
	public MineImage resize(MineImage img, double rate);
}
