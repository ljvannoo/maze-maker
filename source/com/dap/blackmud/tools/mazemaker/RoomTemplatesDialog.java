package com.dap.blackmud.tools.mazemaker;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Enumeration;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.dap.blackmud.tools.mazemaker.utils.ListCellRenderer;
import com.dap.blackmud.utils.Constants;
import com.dap.blackmud.utils.CustomFileFilter;
import com.dap.blackmud.world.RoomTemplate;
import com.dap.blackmud.world.RoomTemplateList;
import com.dap.blackmud.world.Zone;
import java.awt.Insets;

public class RoomTemplatesDialog extends JDialog {
	private static final long serialVersionUID = 944827937809631220L;
	
	private JPanel jContentPane = null;
	private JPanel mainPanel = null;
	private JPanel buttonPanel = null;
	private JButton saveButton = null;
	private JButton insertButton = null;
	private JButton deleteButton = null;
	private JButton defaultButton = null;
	private JButton closeButton = null;
	private JScrollPane templatesScrollPane = null;
	private JList templateList = null;
	
	//private BMWE3 app = null;
	private RoomTemplateList roomTemplates = null;  //  @jve:decl-index=0:
	
	private Zone zone = null;

	private JButton saveTemplatesButton = null;

	private JButton loadTemplatesButton = null;
	/**
	 * This is the default constructor
	 */
	public RoomTemplatesDialog(Frame app, RoomTemplateList roomTemplates, Zone zone) {
		super(app);
		this.zone = zone;
		this.roomTemplates = roomTemplates;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(310, 442);
		this.setTitle("Room Templates");
		this.setContentPane(getJContentPane());
		this.setAlwaysOnTop(true);
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
			mainPanel = new JPanel();
			mainPanel.setLayout(new BorderLayout());
			mainPanel.add(getTemplatesScrollPane(), java.awt.BorderLayout.CENTER);
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
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.gridx = 1;
			gridBagConstraints21.fill = GridBagConstraints.BOTH;
			gridBagConstraints21.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints21.gridy = 3;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 0;
			gridBagConstraints11.fill = GridBagConstraints.BOTH;
			gridBagConstraints11.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints11.gridy = 3;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 1;
			gridBagConstraints4.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints4.insets = new java.awt.Insets(5,5,5,5);
			gridBagConstraints4.gridy = 2;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 1;
			gridBagConstraints3.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints3.insets = new java.awt.Insets(5,5,5,5);
			gridBagConstraints3.gridy = 1;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints2.insets = new java.awt.Insets(5,5,5,5);
			gridBagConstraints2.gridy = 2;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints1.insets = new java.awt.Insets(5,5,5,5);
			gridBagConstraints1.gridy = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridwidth = 2;
			gridBagConstraints.insets = new java.awt.Insets(5,5,5,5);
			gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
			buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridBagLayout());
			buttonPanel.add(getSaveButton(), gridBagConstraints);
			buttonPanel.add(getInsertButton(), gridBagConstraints1);
			buttonPanel.add(getDeleteButton(), gridBagConstraints2);
			buttonPanel.add(getDefaultButton(), gridBagConstraints3);
			buttonPanel.add(getCloseButton(), gridBagConstraints4);
			buttonPanel.add(getSaveTemplatesButton(), gridBagConstraints11);
			buttonPanel.add(getLoadTemplatesButton(), gridBagConstraints21);
		}
		return buttonPanel;
	}

	/**
	 * This method initializes saveButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getSaveButton() {
		if (saveButton == null) {
			saveButton = new JButton();
			saveButton.setText("Save Current Room As Template");
			saveButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					RoomTemplate template = roomTemplates.createNewTemplate(zone.getCurrentRoom());
					DefaultListModel model = (DefaultListModel)templateList.getModel();
					model.addElement(template);
				}
			});
		}
		return saveButton;
	}

	/**
	 * This method initializes insertButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getInsertButton() {
		if (insertButton == null) {
			insertButton = new JButton();
			insertButton.setText("Insert Template");
			insertButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(templateList.getSelectedValue() != null) {
						RoomTemplate template = (RoomTemplate)templateList.getSelectedValue();
						zone.copyToCurrentRoom(template);
					}
				}
			});
		}
		return insertButton;
	}

	/**
	 * This method initializes deleteButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getDeleteButton() {
		if (deleteButton == null) {
			deleteButton = new JButton();
			deleteButton.setText("Delete Template");
			deleteButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(templateList.getSelectedValue() != null) {
						RoomTemplate template = (RoomTemplate)templateList.getSelectedValue();
						DefaultListModel model = (DefaultListModel)templateList.getModel();
						model.removeElement(template);
						roomTemplates.remove(template.getID());
					}
				}
			});
		}
		return deleteButton;
	}

	/**
	 * This method initializes defaultButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getDefaultButton() {
		if (defaultButton == null) {
			defaultButton = new JButton();
			defaultButton.setText("Set As Default");
			defaultButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(templateList.getSelectedValue() != null) {
						RoomTemplate template = (RoomTemplate)templateList.getSelectedValue();
						roomTemplates.setDefaultID(template.getID());
						templateList.repaint();
					}
				}
			});
		}
		return defaultButton;
	}

	/**
	 * This method initializes closeButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCloseButton() {
		if (closeButton == null) {
			closeButton = new JButton();
			closeButton.setText("Close Window");
			closeButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setVisible(false);
					dispose();
				}
			});
		}
		return closeButton;
	}

	/**
	 * This method initializes templatesScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getTemplatesScrollPane() {
		if (templatesScrollPane == null) {
			templatesScrollPane = new JScrollPane();
			templatesScrollPane.setViewportView(getTemplateList());
		}
		return templatesScrollPane;
	}

	/**
	 * This method initializes templateList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getTemplateList() {
		if (templateList == null) {
			templateList = new JList();
			updateTemplateList();
		}
		return templateList;
	}

	private void updateTemplateList() {
		DefaultListModel model = new DefaultListModel();
		Enumeration templateIDs = roomTemplates.getTemplateIDs();
		while(templateIDs.hasMoreElements()) {
			model.addElement(roomTemplates.get(((Integer)templateIDs.nextElement()).intValue()));
		}
		getTemplateList().setModel(model);
		getTemplateList().setCellRenderer(new ListCellRenderer(roomTemplates));
	}

	/**
	 * This method initializes saveTemplatesButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getSaveTemplatesButton() {
		if (saveTemplatesButton == null) {
			saveTemplatesButton = new JButton();
			saveTemplatesButton.setText("Save Templates");
			saveTemplatesButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					save();
				}
			});
		}
		return saveTemplatesButton;
	}
	
	public void save() {
		CustomFileFilter filter = new CustomFileFilter(Constants.TEMPLATE_FILE_DESCRIPTION, Constants.TEMPLATE_FILE_EXTENSION);
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(filter);
        int returnVal = fileChooser.showSaveDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION && fileChooser.getSelectedFile()!=null) {
            File file = fileChooser.getSelectedFile();
            String ext = null;
            String fileName = file.getName();
            int i = fileName.lastIndexOf('.');
            if (i > 0 &&  i < fileName.length() - 1) {
                ext = fileName.substring(i+1).toLowerCase();
            }
            if(ext==null) {
            	file = new File(file.getPath()+"."+Constants.TEMPLATE_FILE_EXTENSION);
            }
            
            try {
				FileOutputStream fileOut = new FileOutputStream(file.getPath());
				ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
				objOut.writeObject(zone.getRoomTemplates());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	}

	/**
	 * This method initializes loadTemplatesButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getLoadTemplatesButton() {
		if (loadTemplatesButton == null) {
			loadTemplatesButton = new JButton();
			loadTemplatesButton.setText("Load Templates");
			loadTemplatesButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					load();
				}
			});
		}
		return loadTemplatesButton;
	}
	
	private void load() {
		CustomFileFilter filter = new CustomFileFilter(Constants.TEMPLATE_FILE_DESCRIPTION, Constants.TEMPLATE_FILE_EXTENSION);
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(filter);
        int returnVal = fileChooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION && fileChooser.getSelectedFile()!=null) {
            File file = fileChooser.getSelectedFile();
            String ext = null;
            String fileName = file.getName();
            int i = fileName.lastIndexOf('.');
            if (i > 0 &&  i < fileName.length() - 1) {
                ext = fileName.substring(i+1).toLowerCase();
            }
            if(ext==null) {
            	file = new File(file.getPath()+"."+Constants.TEMPLATE_FILE_EXTENSION);
            }
            
            try {
				FileInputStream fileIn = new FileInputStream(file.getPath());
				ObjectInputStream objIn = new ObjectInputStream(fileIn);
				Object tempObj = objIn.readObject();
				if(tempObj instanceof RoomTemplateList) {
					zone.setRoomTemplates((RoomTemplateList)tempObj);
					this.roomTemplates = zone.getRoomTemplates();
					updateTemplateList();
				}
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(this, "File '"+file.getPath()+"' does not exist!", "File Not Found Error", JOptionPane.ERROR_MESSAGE);
			} catch (InvalidClassException e){
				JOptionPane.showMessageDialog(this, "File '"+file.getPath()+"' is incompatible!", "Incompatible File Error", JOptionPane.ERROR_MESSAGE);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
        }
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
