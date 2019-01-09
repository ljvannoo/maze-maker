package com.dap.blackmud.tools.mazemaker;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.dap.blackmud.utils.BitVector;
import com.dap.blackmud.world.Room;

public class RoomEditor extends JDialog {
	private static final long serialVersionUID = 2223849080920587157L;
	
	private JPanel jContentPane = null;
	private Room room = null;
	private JPanel mainPanel = null;
	private JPanel buttonPanel = null;
	private JButton okButton = null;
	private JButton cancelButton = null;
	protected int buttonPressed;
	private JLabel vnumLabel = null;
	private JTextField vnumTextField = null;
	private JLabel coordinateLabel = null;
	private JTextField coordinatesTextField = null;
	private JPanel titlePanel = null;
	private JTextField titleTextField = null;
	private JPanel desciptionPanel = null;
	private JTextArea descriptionTextArea = null;
	private JScrollPane descriptionScrollPane = null;
	private JLabel sectorLabel = null;
	private JComboBox sectorComboBox = null;
	private JLabel mobLimitLabel = null;
	private JTextField mobLimitTextField = null;
	private JPanel flagsPanel = null;
	private Vector roomFlagCheckBoxes;
	/**
	 * This is the default constructor
	 */
	public RoomEditor(Frame owner, Room room) {
		super(owner);
		this.room = room;
		roomFlagCheckBoxes = new Vector();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(750, 650);
		this.setPreferredSize(new java.awt.Dimension(650,650));
		this.setResizable(false);
		this.setModal(true);
		this.setTitle("Room Editor - "+room.getCoordinates());
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getMainPanel(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getButtonPanel(), java.awt.BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes mainPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getMainPanel() {
		if (mainPanel == null) {
			GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
			gridBagConstraints22.gridx = 0;
			gridBagConstraints22.gridwidth = 8;
			gridBagConstraints22.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints22.insets = new java.awt.Insets(5,20,5,20);
			gridBagConstraints22.gridy = 3;
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints12.gridy = 0;
			gridBagConstraints12.weightx = 1.0;
			gridBagConstraints12.insets = new java.awt.Insets(5,10,5,20);
			gridBagConstraints12.gridx = 7;
			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			gridBagConstraints31.gridx = 6;
			gridBagConstraints31.insets = new java.awt.Insets(5,10,5,10);
			gridBagConstraints31.gridy = 0;
			mobLimitLabel = new JLabel();
			mobLimitLabel.setText("Mob Limit:");
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints21.gridy = 0;
			gridBagConstraints21.weightx = 1.0;
			gridBagConstraints21.insets = new java.awt.Insets(5,10,5,10);
			gridBagConstraints21.gridx = 5;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 4;
			gridBagConstraints11.insets = new java.awt.Insets(5,10,5,10);
			gridBagConstraints11.gridy = 0;
			sectorLabel = new JLabel();
			sectorLabel.setText("Sector Type:");
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.insets = new java.awt.Insets(5,20,5,20);
			gridBagConstraints5.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints5.gridwidth = 8;
			gridBagConstraints5.gridy = 2;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints4.gridwidth = 8;
			gridBagConstraints4.insets = new java.awt.Insets(5,20,5,20);
			gridBagConstraints4.gridy = 1;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = java.awt.GridBagConstraints.NONE;
			gridBagConstraints3.gridy = 0;
			gridBagConstraints3.weightx = 1.0;
			gridBagConstraints3.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints3.insets = new java.awt.Insets(5,10,5,0);
			gridBagConstraints3.gridx = 3;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 2;
			gridBagConstraints2.insets = new java.awt.Insets(5,10,5,10);
			gridBagConstraints2.gridy = 0;
			coordinateLabel = new JLabel();
			coordinateLabel.setText("Coordinates: ");
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = java.awt.GridBagConstraints.NONE;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints1.gridx = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.insets = new java.awt.Insets(5,30,5,10);
			gridBagConstraints.gridy = 0;
			vnumLabel = new JLabel();
			vnumLabel.setText("VNUM:");
			vnumLabel.setToolTipText("");
			mainPanel = new JPanel();
			mainPanel.setLayout(new GridBagLayout());
			mainPanel.add(vnumLabel, gridBagConstraints);
			mainPanel.add(getVnumTextField(), gridBagConstraints1);
			mainPanel.add(coordinateLabel, gridBagConstraints2);
			mainPanel.add(getCoordinatesTextField(), gridBagConstraints3);
			mainPanel.add(getTitlePanel(), gridBagConstraints4);
			mainPanel.add(getDesciptionPanel(), gridBagConstraints5);
			mainPanel.add(sectorLabel, gridBagConstraints11);
			mainPanel.add(getSectorComboBox(), gridBagConstraints21);
			mainPanel.add(mobLimitLabel, gridBagConstraints31);
			mainPanel.add(getMobLimitTextField(), gridBagConstraints12);
			mainPanel.add(getFlagsPanel(), gridBagConstraints22);
		}
		return mainPanel;
	}

	/**
	 * This method initializes buttonPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getButtonPanel() {
		if (buttonPanel == null) {
			buttonPanel = new JPanel();
			buttonPanel.add(getOkButton(), null);
			buttonPanel.add(getCancelButton(), null);
		}
		return buttonPanel;
	}

	/**
	 * This method initializes okButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getOkButton() {
		if (okButton == null) {
			okButton = new JButton();
			okButton.setText("OK");
			okButton.setPreferredSize(new java.awt.Dimension(73,26));
			okButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(verifyData()) {
						buttonPressed = JOptionPane.OK_OPTION;
						setVisible(false);
					}
				}
			});
		}
		return okButton;
	}
	
	private boolean verifyData() {
		return true;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.setText("Cancel");
			cancelButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					buttonPressed = JOptionPane.CANCEL_OPTION;
					setVisible(false);
				}
			});
		}
		return cancelButton;
	}

	public int showOptionsDialog() {
		this.setVisible(true);
		
		return buttonPressed;
	}

	/**
	 * This method initializes vnumTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getVnumTextField() {
		if (vnumTextField == null) {
			vnumTextField = new JTextField();
			vnumTextField.setColumns(5);
			vnumTextField.setMinimumSize(new java.awt.Dimension(75,20));
			vnumTextField.setEditable(false);
			vnumTextField.setText(""+room.getVNUM());
		}
		return vnumTextField;
	}

	/**
	 * This method initializes coordinatesTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getCoordinatesTextField() {
		if (coordinatesTextField == null) {
			coordinatesTextField = new JTextField();
			coordinatesTextField.setColumns(10);
			coordinatesTextField.setEditable(false);
			coordinatesTextField.setMinimumSize(new java.awt.Dimension(75,20));
			coordinatesTextField.setText(room.getCoordinates().toString());
		}
		return coordinatesTextField;
	}

	/**
	 * This method initializes titlePanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getTitlePanel() {
		if (titlePanel == null) {
			titlePanel = new JPanel();
			titlePanel.setLayout(new BorderLayout());
			titlePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Room Title", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			titlePanel.add(getTitleTextField(), java.awt.BorderLayout.CENTER);
		}
		return titlePanel;
	}

	/**
	 * This method initializes titleTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTitleTextField() {
		if (titleTextField == null) {
			titleTextField = new JTextField();
			titleTextField.setText(room.getValue("title").toString());
		}
		return titleTextField;
	}

	/**
	 * This method initializes desciptionPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getDesciptionPanel() {
		if (desciptionPanel == null) {
			desciptionPanel = new JPanel();
			desciptionPanel.setLayout(new BorderLayout());
			desciptionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Room Description", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			desciptionPanel.add(getDescriptionScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return desciptionPanel;
	}

	/**
	 * This method initializes descriptionText	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getDescriptionTextArea() {
		if (descriptionTextArea == null) {
			descriptionTextArea = new JTextArea();
			descriptionTextArea.setText(room.getValue("description").toString());
			descriptionTextArea.setLineWrap(true);
		}
		return descriptionTextArea;
	}

	/**
	 * This method initializes descriptionScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getDescriptionScrollPane() {
		if (descriptionScrollPane == null) {
			descriptionScrollPane = new JScrollPane();
			descriptionScrollPane.setPreferredSize(new java.awt.Dimension(100,150));
			descriptionScrollPane.setViewportView(getDescriptionTextArea());
		}
		return descriptionScrollPane;
	}

	/**
	 * This method initializes sectorComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getSectorComboBox() {
		if (sectorComboBox == null) {
			sectorComboBox = new JComboBox();
			DefaultComboBoxModel model = new DefaultComboBoxModel();
			for(int i = 0; i < Room.SECTOR_TYPES.length; i++) {
				model.addElement(Room.SECTOR_TYPES[i]);
			}
			sectorComboBox.setModel(model);
			sectorComboBox.setSelectedIndex(room.getSectorType());
		}
		return sectorComboBox;
	}

	/**
	 * This method initializes mobLimitTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getMobLimitTextField() {
		if (mobLimitTextField == null) {
			mobLimitTextField = new JTextField();
			mobLimitTextField.setEditable(false);
			mobLimitTextField.setMinimumSize(new java.awt.Dimension(26,20));
			mobLimitTextField.setText(room.getValue("mobLimit").toString());
			mobLimitTextField.setColumns(2);
		}
		return mobLimitTextField;
	}

	/**
	 * This method initializes flagsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getFlagsPanel() {
		if (flagsPanel == null) {
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(9);
			gridLayout.setColumns(3);
			flagsPanel = new JPanel();
			flagsPanel.setLayout(gridLayout);
			flagsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Room Flags", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			BitVector roomFlags = room.getFlags();
			for(int i = 0; i < Room.ROOM_FLAGS.length; i++) {
				JCheckBox checkBox = new JCheckBox();
				checkBox.setText(Room.ROOM_FLAGS[i][0]);
				if(Room.ROOM_FLAGS[i][2].equals("false"))
					checkBox.setEnabled(false);
				int bit = Integer.parseInt(Room.ROOM_FLAGS[i][1]);
				if(bit == Room.getFlagValue("tunnel")) {
					final JCheckBox tunnelCheckBox = checkBox;
					tunnelCheckBox.addItemListener(new java.awt.event.ItemListener() {
						public void itemStateChanged(java.awt.event.ItemEvent e) {
							if(tunnelCheckBox.isSelected()) {
								mobLimitTextField.setEditable(true);
							} else {
								mobLimitTextField.setText("0");
								mobLimitTextField.setEditable(false);
							}
						}
					});
				}
				checkBox.setSelected(roomFlags.isFullSet(bit));
				flagsPanel.add(checkBox, null);
				roomFlagCheckBoxes.add(checkBox);
			}
		}
		return flagsPanel;
	}

	public String getTitleText() {
		return titleTextField.getText();
	}
	
	public String getDescriptionText() {
		return descriptionTextArea.getText();
	}
	
	public int getSectorType() {
		return getSectorComboBox().getSelectedIndex();
	}
	
	public int getMobLimit() {
		try {
			return Integer.parseInt(getMobLimitTextField().getText());
		} catch(NumberFormatException e) {
			return 0;
		}
	}
	
	public int getFlags() {
		int bits = 0;
		
		for(int i = 0; i < roomFlagCheckBoxes.size(); i++) {
			JCheckBox checkBox = (JCheckBox)roomFlagCheckBoxes.get(i);
			if(checkBox.isSelected())
				bits += Integer.parseInt(Room.ROOM_FLAGS[i][1]);
		}
		
		return bits;
	}
}  //  @jve:decl-index=0:visual-constraint="10,12"
