/*
 * 作成日: 2003/12/21
 *
 */
package map.common;

import java.awt.image.BufferedImage;

import map.pallet.PalletList;
import mine.MineException;
import mine.awt.ImageAWT;
import mine.paint.MineGraphics;
import mine.paint.MineImage;
import mine.paint.MineImageLoader;

/**
 * @author k-saito
 *
 */
public class ImageUtils {

	/*** ユニット画像 ***********************************************/

	public static MineImage[][] getUnitImage(MineImageLoader mil, PalletList plist, double rate) {

		MineImage unit[][] = null;

		try {
			unit = mil.loadTile(plist.getImagePath(), plist.getTileWidth(), plist.getTileHeight());
		} catch (MineException e) {
			unit =
				new MineImage[][] { { mil.getBuffer(
					plist.getTileWidth(),
					plist.getTileHeight())}
			};
		}

		if ( rate != 1.0 ) {
			for (int x = 0; x < unit.length; x++) {
				for (int y = 0; y < unit[0].length; y++) {
					unit[x][y] = mil.resize(unit[x][y], rate);
					MineGraphics g = unit[x][y].getGraphics();
					g.setColor(200, 200, 200);
					g.drawRect(0, 0, unit[x][y].getWidth() - 1, unit[x][y].getHeight() - 1);
				}
			}
		}
		return unit;
	}

	/*** 網画像 *****************************************************/

	public static MineImage[] getAmiImage(int tileW, int tileH) {


		BufferedImage sprite = new BufferedImage(tileW, tileH, BufferedImage.TYPE_INT_ARGB);
		BufferedImage ami = new BufferedImage(tileW, tileH, BufferedImage.TYPE_INT_ARGB);

		for (int x = 0; x < tileW; x++) {
			for (int y = 0; y < tileH; y++) {
				if ((x + y) % 2 == 0) {
					ami.setRGB(x, y, 0xFF000000);
				}
			}
		}
		return new MineImage[]{new ImageAWT(sprite), new ImageAWT(ami)};
	}
}
