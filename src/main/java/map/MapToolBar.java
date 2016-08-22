package map;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JToggleButton;

import mine.awt.CommandToolBar;
import mine.event.CommandListener;
import mine.file.FileCommand;

public class MapToolBar extends CommandToolBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7385239326409832621L;

	private static final String RESOURCES = "map/image/";

	private JButton undoButton;
	private JButton redoButton;
	private JButton zoominButton;
	private JButton zoomoutButton;

	/*** コンストラクタ *********************************/

	public MapToolBar(CommandListener listener) {
		super(listener);

		add(newButton("New", FileCommand.NEW, RESOURCES + "create.png"));
		add(newButton("Open", FileCommand.OPEN, RESOURCES + "open.png"));
		add(newButton("Save", FileCommand.SAVE, RESOURCES + "save.png"));
		add(newButton("Save As", FileCommand.SAVE_AS, RESOURCES + "rename.png"));
		addSeparator();

		undoButton = newButton("Undo", "Undo", RESOURCES + "undo.png");
		redoButton = newButton("Redo", "Redo", RESOURCES + "redo.png");
		add(undoButton);
		add(redoButton);
		addSeparator();

		zoominButton = newButton("ZoomIn", "ZoomIn", RESOURCES + "zoomin.png");
		zoomoutButton = newButton("ZoomOut", "ZoomOut", RESOURCES + "zoomout.png");
		add(zoominButton);
		add(zoomoutButton);
		addSeparator();

		ButtonGroup group = new ButtonGroup();
		addToggleButton("Region", RESOURCES + "region.png", group);
		addToggleButton("Pencil", RESOURCES + "pencil.png", group);
		addToggleButton("Paint", RESOURCES + "paint.png", group);
		addToggleButton("Stamp", RESOURCES + "stamp.png", group);
		addToggleButton("BoxFill", RESOURCES + "boxfill.png", group);
		addToggleButton("BoxLine", RESOURCES + "boxline.png", group);
	}

	/**
	 * トグルボタンを作成する。<p>
	 * 
	 * @param command
	 * @param image
	 * @param group
	 */
	private void addToggleButton(String command, String image, ButtonGroup group) {
		JToggleButton button = super.newToggleButton(command, command, image);
		group.add(button);
		add(button);
	}

	/**
	 * ボタン状態変更
	 */
	public void setEnabled(String name, boolean flag) {
		if (name.equals("Undo")) {
			undoButton.setEnabled(flag);
		} else if (name.equals("Redo")) {
			redoButton.setEnabled(flag);
		} else if (name.equals("ZoomIn")) {
			zoominButton.setEnabled(flag);
		} else if (name.equals("ZoomOut")) {
			zoomoutButton.setEnabled(flag);
		}
	}
}
