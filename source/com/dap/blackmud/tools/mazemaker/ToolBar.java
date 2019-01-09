package com.dap.blackmud.tools.mazemaker;

import java.awt.Dimension;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.JButton;

public class ToolBar extends JToolBar {

	private static final long serialVersionUID = 1L;
	public static final int TOOL_ARROW = 0;
	public static final int TOOL_WALL = 1;
	public static final int TOOL_WALL_UP = 2;
	public static final int TOOL_WALL_DOWN = 3;
	public static final int TOOL_ROOM = 4;
	public static final int TOOL_TEMPLATE = 5;
	private JToggleButton arrowToolButton = null;
	private JToggleButton wallToolButton = null;
	private Vector buttonList = null;  //  @jve:decl-index=0:
	private JToggleButton roomToolButton = null;
	private JToggleButton templateToolButton = null;
	private JToggleButton wallUpToolButton = null;
	private JToggleButton wallDownToolButton = null;
	/**
	 * This method initializes 
	 * 
	 */
	public ToolBar() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setOrientation(JToolBar.VERTICAL);
        this.add(getArrowToolButton());
        this.add(getWallToolButton());
        this.add(getWallUpToolButton());
        this.add(getWallDownToolButton());
        this.add(getRoomToolButton());
        this.add(getTemplateToolButton());
			
	}

	/**
	 * This method initializes arrowToolButton	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */
	private JToggleButton getArrowToolButton() {
		if (arrowToolButton == null) {
			arrowToolButton = new JToggleButton();
			getButtonList().add(arrowToolButton);
			arrowToolButton.setPreferredSize(new Dimension(34, 34));
			arrowToolButton.setIcon(new ImageIcon(getClass().getResource("/images/arrow.png")));
			arrowToolButton.setSize(new Dimension(36, 36));
			arrowToolButton.setToolTipText("Selection tool");
			arrowToolButton.setSelected(true);
			arrowToolButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectButton(getArrowToolButton());
				}
			});
		}
		return arrowToolButton;
	}

	protected void selectButton(JToggleButton button) {
		if(button.isSelected()) {
			JToggleButton curButton = null;
			for(int i = 0; i < getButtonList().size(); i++) {
				curButton = (JToggleButton)getButtonList().get(i);
				if(curButton != button)
					curButton.setSelected(false);
			}
		} else {
			button.setSelected(true);
		}
	}

	/**
	 * This method initializes wallToolButton	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */
	private JToggleButton getWallToolButton() {
		if (wallToolButton == null) {
			wallToolButton = new JToggleButton();
			getButtonList().add(wallToolButton);
			wallToolButton.setPreferredSize(new Dimension(36, 36));
			wallToolButton.setSize(new Dimension(36, 36));
			wallToolButton.setMaximumSize(new Dimension(36, 36));
			wallToolButton.setMinimumSize(new Dimension(36, 36));
			wallToolButton.setToolTipText("Wall tool");
			wallToolButton.setIcon(new ImageIcon(getClass().getResource("/images/trowel.png")));
			wallToolButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectButton(getWallToolButton());
				}
			});
		}
		return wallToolButton;
	}

	public Vector getButtonList() {
		if(buttonList == null) {
			buttonList = new Vector();
		}
		return buttonList;
	}
	
	public int getSelectedTool() {
		for(int i = 0; i < getButtonList().size(); i++) {
			if(((JToggleButton)getButtonList().get(i)).isSelected())
				return i;
		}
		return 0;
	}

	/**
	 * This method initializes roomToolButton	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */
	private JToggleButton getRoomToolButton() {
		if (roomToolButton == null) {
			roomToolButton = new JToggleButton();
			getButtonList().add(roomToolButton);
			roomToolButton.setPreferredSize(new Dimension(36, 36));
			roomToolButton.setText("");
			roomToolButton.setToolTipText("Room tool");
			roomToolButton.setIcon(new ImageIcon(getClass().getResource("/images/home.png")));
			roomToolButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectButton(getRoomToolButton());
				}
			});
		}
		return roomToolButton;
	}

	/**
	 * This method initializes templateToolButton	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */
	private JToggleButton getTemplateToolButton() {
		if (templateToolButton == null) {
			templateToolButton = new JToggleButton();
			getButtonList().add(templateToolButton);
			templateToolButton.setPreferredSize(new Dimension(36, 36));
			templateToolButton.setToolTipText("Template tool");
			templateToolButton.setIcon(new ImageIcon(getClass().getResource("/images/template_out.png")));
			templateToolButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectButton(getTemplateToolButton());
				}
			});
		}
		return templateToolButton;
	}

	/**
	 * This method initializes wallUpToolButton	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */
	private JToggleButton getWallUpToolButton() {
		if (wallUpToolButton == null) {
			wallUpToolButton = new JToggleButton();
			getButtonList().add(wallUpToolButton);
			wallUpToolButton.setPreferredSize(new Dimension(36, 36));
			wallUpToolButton.setSize(new Dimension(36, 36));
			wallUpToolButton.setMaximumSize(new Dimension(36, 36));
			wallUpToolButton.setMinimumSize(new Dimension(36, 36));
			wallUpToolButton.setToolTipText("Ceiling Tool");
			wallUpToolButton.setIcon(new ImageIcon(getClass().getResource("/images/trowel_up.png")));
			wallUpToolButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectButton(getWallUpToolButton());
				}
			});
		}
		return wallUpToolButton;
	}

	/**
	 * This method initializes wallDownToolButton	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */
	private JToggleButton getWallDownToolButton() {
		if (wallDownToolButton == null) {
			wallDownToolButton = new JToggleButton();
			getButtonList().add(wallDownToolButton);
			wallDownToolButton.setPreferredSize(new Dimension(36, 36));
			wallDownToolButton.setSize(new Dimension(36, 36));
			wallDownToolButton.setMaximumSize(new Dimension(36, 36));
			wallDownToolButton.setMinimumSize(new Dimension(36, 36));
			wallDownToolButton.setToolTipText("Floor Tool");
			wallDownToolButton.setIcon(new ImageIcon(getClass().getResource("/images/trowel_down.png")));
			wallDownToolButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectButton(getWallDownToolButton());
				}
			});
		}
		return wallDownToolButton;
	}

}
