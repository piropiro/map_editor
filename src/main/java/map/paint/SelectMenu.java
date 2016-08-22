package map.paint;

import java.awt.Component;

import mine.awt.CommandPopupMenu;
import mine.event.CommandListener;

/**
 * @author k-saito
 *
 */
public class SelectMenu extends CommandPopupMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3440698401930719919L;

	public SelectMenu(Component invoker, CommandListener listener){
		super(invoker, listener);

		add(newItem("切り取り(T)", "Cut", 'T'));
		add(newItem("コピー(C)", "Copy", 'C'));
		add(newItem("貼り付け(P)", "Paste", 'P'));
		addSeparator();
		add(newItem("水平反転(H)", "HFlip", 'H'));
		add(newItem("垂直反転(V)", "VFlip", 'V'));
		add(newItem("左回転(L)", "LRotate", 'L'));
		add(newItem("右回転(R)", "RRotate", 'R'));
	}
}
