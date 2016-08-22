package map.common;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import map.MainWorks;
import mine.awt.MineAwtUtils;

public class SizeDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = -7782563506432959656L;

	private String name;
	private boolean okFlag;

	private JTextField widthText;
	private JTextField heightText;

	/*** コンストラクタ ****************************************/

	public SizeDialog(MainWorks mw, String name, int width, int height) {
		super(mw.getFrame(), name + " Size", true);
		this.name = name;
		okFlag = false;
		widthText = new JTextField("" + width);
		heightText = new JTextField("" + height);
		setPanel();
		pack();
		MineAwtUtils.setCenter(this);
	}

	/*** パネル作成 ********************************************/

	private void setPanel() {

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.add(newButton("OK"));
		buttonPanel.add(newButton("Cancel"));

		JPanel sizePanel = new JPanel();
		sizePanel.setLayout(new GridLayout(2,2));
		sizePanel.add(new JLabel(name + " Width"));
		sizePanel.add(widthText);
		sizePanel.add(new JLabel(name + " Height"));
		sizePanel.add(heightText);

		JPanel contentPane =  new JPanel();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(sizePanel, "Center");
		contentPane.add(buttonPanel, "South");
		setContentPane(contentPane);
	}

	private JButton newButton(String s) {
		JButton jb = new JButton(s);
		jb.setActionCommand(s);
		jb.addActionListener(this);
		return jb;
	}

	public boolean isOK(){
		return okFlag;
	}
	
	public String getSizeWidth(){
		return widthText.getText();
	}
	
	public String getSizeHeight(){
		return heightText.getText();
	}

	/*** アクション ******************************************/

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		if (command.equals("OK")) {
			okFlag = true;
			dispose();
		}

		if (command.equals("Cancel")) {
			dispose();
		}
	}
}
