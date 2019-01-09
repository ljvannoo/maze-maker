package com.dap.blackmud.tools.mazemaker;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

import com.dap.blackmud.tools.mazemaker.utils.SwingWorker;
import com.dap.blackmud.utils.Constants;
import com.dap.blackmud.utils.CustomFileFilter;
import com.dap.blackmud.world.Coordinate;
import com.dap.blackmud.world.Exit;
import com.dap.blackmud.world.Room;
import com.dap.blackmud.world.RoomTemplate;

public class MazeMaker extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JMenuBar mainMenuBar = null;
	private JMenu fileMenu = null;
	private JMenuItem fileExitMenuItem = null;
	private Maze maze = null;
	private JPanel statusPanel = null;
	private JLabel statusLabel = null;
	private Point lastDragLocation = null;
	private JPanel optionBarPanel = null;
	private JProgressBar progressBar = null;
	
	private int busyCount = 0;
	private JMenu viewMenu = null;
	private JCheckBoxMenuItem viewAnimateMenuItem = null;
	private JCheckBoxMenuItem viewArrowsMenuItem = null;
	private JCheckBoxMenuItem viewSeperatorsMenuItem = null;
	private JPanel mazeSizeOptionPanel = null;
	private JLabel widthLabel = null;
	private JTextField widthTextField = null;
	private JLabel heightLabel = null;
	private JTextField heightTextField = null;
	private JButton regenButton = null;
	private JSeparator seperator1 = null;
	private JButton solveMazeButton = null;
	private JMenuItem fileNewMenuItem = null;
	private JMenuItem fileSaveMenuItem = null;
	private JMenuItem fileOpenMenuItem = null;
	private JSeparator fileSeperator1 = null;
	private JMenuItem fileExportMenuItem = null;
	private JPopupMenu mainPopupMenu = null;  //  @jve:decl-index=0:visual-constraint="681,24"
	
	private Room selectedRoom = null;
	private JMenuItem popupSetStartMenuItem = null;
	private JMenuItem popupSetEndMenuItem = null;
	private JMenuItem popupClearSolutionMenuItem = null;
	private JMenuItem popupClearMarksMenuItem = null;
	private JComboBox algorithmComboBox = null;
	private JLabel vnumLabel = null;
	private JTextField vnumTextField = null;
	private JMenuItem popupEditRoomMenuItem = null;
	private JSeparator popupSeperator1 = null;
	private JSeparator popupSeperator2 = null;
	private JMenuItem popupMarkRoomMenuItem = null;
	private JToggleButton templatesToggleButton = null;
	
	private RoomTemplatesDialog templatesDialog = null;
	private ToolBar mainToolBar = null;
	private JSeparator seperator2 = null;
	private JButton levelUpButton = null;
	private JTextField levelTextField = null;
	private JButton levelDownButton = null;
	private JCheckBoxMenuItem viewColorRoomsMenuItem = null;
	private JButton renumberWorldButton = null;
	/**
	 * This is the default constructor
	 */
	public MazeMaker() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setPreferredSize(new Dimension(350, 350));
		this.setBounds(new Rectangle(0, 0, 924, 668));
		this.setJMenuBar(getMainMenuBar());
		this.setContentPane(getJContentPane());
		this.setTitle("Maze Maker");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				exit();
			}
		});
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
			jContentPane.add(getMaze(), BorderLayout.CENTER);
			jContentPane.add(getStatusPanel(), BorderLayout.SOUTH);
			jContentPane.add(getOptionBarPanel(), BorderLayout.NORTH);
			jContentPane.add(getMainToolBar(), BorderLayout.EAST);
		}
		return jContentPane;
	}

	/**
	 * This method initializes mainMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getMainMenuBar() {
		if (mainMenuBar == null) {
			mainMenuBar = new JMenuBar();
			mainMenuBar.add(getFileMenu());
			mainMenuBar.add(getViewMenu());
		}
		return mainMenuBar;
	}

	/**
	 * This method initializes fileMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getFileMenu() {
		if (fileMenu == null) {
			fileMenu = new JMenu();
			fileMenu.setText("File");
			fileMenu.add(getFileNewMenuItem());
			fileMenu.add(getFileOpenMenuItem());
			fileMenu.add(getFileSaveMenuItem());
			fileMenu.add(getFileExportMenuItem());
			fileMenu.add(getFileSeperator1());
			fileMenu.add(getFileExitMenuItem());
		}
		return fileMenu;
	}

	/**
	 * This method initializes fileExitMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getFileExitMenuItem() {
		if (fileExitMenuItem == null) {
			fileExitMenuItem = new JMenuItem();
			fileExitMenuItem.setText("Exit");
			fileExitMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					exit();
				}
			});
		}
		return fileExitMenuItem;
	}
	
	protected void exit() {
		System.exit(0);
	}

	/**
	 * This method initializes statusPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getStatusPanel() {
		if (statusPanel == null) {
			statusLabel = new JLabel();
			statusLabel.setText(" ");
			statusLabel.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			statusPanel = new JPanel();
			statusPanel.setLayout(new BorderLayout());
			statusPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			statusPanel.add(statusLabel, BorderLayout.CENTER);
			statusPanel.add(getProgressBar(), BorderLayout.EAST);
		}
		return statusPanel;
	}

	/**
	 * This method initializes optionBarPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getOptionBarPanel() {
		if (optionBarPanel == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			optionBarPanel = new JPanel();
			optionBarPanel.setLayout(flowLayout);
			optionBarPanel.add(getMazeSizeOptionPanel(), null);
			optionBarPanel.add(getSeperator1(), null);
			optionBarPanel.add(getLevelUpButton(), null);
			optionBarPanel.add(getLevelTextField(), null);
			optionBarPanel.add(getLevelDownButton(), null);
			optionBarPanel.add(getSeperator2(), null);
			optionBarPanel.add(getSolveMazeButton(), null);
			optionBarPanel.add(getTemplatesToggleButton(), null);
		}
		return optionBarPanel;
	}

	/**
	 * This method initializes progressBar	
	 * 	
	 * @return javax.swing.JProgressBar	
	 */
	private JProgressBar getProgressBar() {
		if (progressBar == null) {
			progressBar = new JProgressBar();
			progressBar.setStringPainted(true);
		}
		return progressBar;
	}

	/**
	 * This method initializes viewMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getViewMenu() {
		if (viewMenu == null) {
			viewMenu = new JMenu();
			viewMenu.setText("View");
			viewMenu.add(getViewColorRoomsMenuItem());
			viewMenu.add(getViewAnimateMenuItem());
			viewMenu.add(getViewArrowsMenuItem());
			viewMenu.add(getViewSeperatorsMenuItem());
		}
		return viewMenu;
	}

	/**
	 * This method initializes viewAnimateMenuItem	
	 * 	
	 * @return javax.swing.JCheckBoxMenuItem	
	 */
	private JCheckBoxMenuItem getViewAnimateMenuItem() {
		if (viewAnimateMenuItem == null) {
			viewAnimateMenuItem = new JCheckBoxMenuItem();
			viewAnimateMenuItem.setText("Animated Generation");
			viewAnimateMenuItem.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					maze.setAnimated(getViewAnimateMenuItem().isSelected());
				}
			});
		}
		return viewAnimateMenuItem;
	}

	/**
	 * This method initializes viewArrowsMenuItem	
	 * 	
	 * @return javax.swing.JCheckBoxMenuItem	
	 */
	private JCheckBoxMenuItem getViewArrowsMenuItem() {
		if (viewArrowsMenuItem == null) {
			viewArrowsMenuItem = new JCheckBoxMenuItem();
			viewArrowsMenuItem.setText("Draw Exit Arrows");
			viewArrowsMenuItem.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					maze.setArrowsDrawn(getViewArrowsMenuItem().isSelected());
					maze.repaint();
				}
			});
		}
		return viewArrowsMenuItem;
	}

	/**
	 * This method initializes viewSeperatorsMenuItem	
	 * 	
	 * @return javax.swing.JCheckBoxMenuItem	
	 */
	private JCheckBoxMenuItem getViewSeperatorsMenuItem() {
		if (viewSeperatorsMenuItem == null) {
			viewSeperatorsMenuItem = new JCheckBoxMenuItem();
			viewSeperatorsMenuItem.setText("Draw Room Seperators");
			viewSeperatorsMenuItem
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							maze.setSeperatorsDrawn(getViewSeperatorsMenuItem().isSelected());
							maze.repaint();
						}
					});
		}
		return viewSeperatorsMenuItem;
	}

	/**
	 * This method initializes mazeSizeOptionPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getMazeSizeOptionPanel() {
		if (mazeSizeOptionPanel == null) {
			vnumLabel = new JLabel();
			vnumLabel.setText("Starting VNUM:");
			heightLabel = new JLabel();
			heightLabel.setText("Height:");
			widthLabel = new JLabel();
			widthLabel.setText("Width: ");
			mazeSizeOptionPanel = new JPanel();
			mazeSizeOptionPanel.setLayout(new FlowLayout());
			mazeSizeOptionPanel.add(vnumLabel, null);
			mazeSizeOptionPanel.add(getVnumTextField(), null);
			mazeSizeOptionPanel.add(getRenumberWorldButton(), null);
			mazeSizeOptionPanel.add(widthLabel, null);
			mazeSizeOptionPanel.add(getWidthTextField(), null);
			mazeSizeOptionPanel.add(heightLabel, null);
			mazeSizeOptionPanel.add(getHeightTextField(), null);
			mazeSizeOptionPanel.add(getAlgorithmComboBox(), null);
			mazeSizeOptionPanel.add(getRegenButton(), null);
		}
		return mazeSizeOptionPanel;
	}

	/**
	 * This method initializes widthTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getWidthTextField() {
		if (widthTextField == null) {
			widthTextField = new JTextField();
			widthTextField.setColumns(3);
			widthTextField.setText("10");
		}
		return widthTextField;
	}

	/**
	 * This method initializes heightTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getHeightTextField() {
		if (heightTextField == null) {
			heightTextField = new JTextField();
			heightTextField.setColumns(3);
			heightTextField.setText("10");
		}
		return heightTextField;
	}

	/**
	 * This method initializes regenButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getRegenButton() {
		if (regenButton == null) {
			regenButton = new JButton();
			regenButton.setToolTipText("Regenerate current level");
			regenButton.setPreferredSize(new Dimension(24, 24));
			regenButton.setIcon(new ImageIcon(getClass().getResource("/images/refresh.png")));
			final MazeMaker theApp = this;
			regenButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					maze.regenerateMaze(getAlgorithmComboBox().getSelectedIndex(), Integer.parseInt(getVnumTextField().getText()), Integer.parseInt(getWidthTextField().getText()),
							Integer.parseInt(getHeightTextField().getText()),
							theApp);
				}
			});
		}
		return regenButton;
	}

	/**
	 * This method initializes seperator1	
	 * 	
	 * @return javax.swing.JSeparator	
	 */
	private JSeparator getSeperator1() {
		if (seperator1 == null) {
			seperator1 = new JSeparator();
			seperator1.setOrientation(SwingConstants.VERTICAL);
			seperator1.setPreferredSize(new Dimension(2, 30));
		}
		return seperator1;
	}

	/**
	 * This method initializes solveMazeButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getSolveMazeButton() {
		if (solveMazeButton == null) {
			solveMazeButton = new JButton();
			solveMazeButton.setPreferredSize(new Dimension(24, 24));
			solveMazeButton.setToolTipText("Solve maze");
			solveMazeButton.setIcon(new ImageIcon(getClass().getResource("/images/questionmark.png")));
			final MazeMaker app = this;
			solveMazeButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					SwingWorker worker = new SwingWorker() {
			            public Object construct() {
			                long time = System.currentTimeMillis();
			                app.busy(true);
			                app.setMessage("Solving maze...");
			                getProgressBar().setIndeterminate(true);
			                getProgressBar().setStringPainted(false);

			                int rooms = maze.solveMaze();
			        		
			                getProgressBar().setIndeterminate(false);
			                getProgressBar().setStringPainted(true);
			                
			                time = System.currentTimeMillis()-time;
			                app.busy(false);
			                if(rooms > 0)
			                	app.setMessage("Total solve time: "+(time>1999?time/1000:time)+(time>1999?" seconds.":" milliseconds.")+" Solution contains "+rooms+" rooms");
			                else
			                	app.setMessage("Maze has no solution!");
			                return null;
			            }
			        };
			        worker.start();
				}
			});
		}
		return solveMazeButton;
	}

	/**
	 * This method initializes fileNewMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getFileNewMenuItem() {
		if (fileNewMenuItem == null) {
			fileNewMenuItem = new JMenuItem();
			fileNewMenuItem.setText("New");
			fileNewMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getMaze().clear();
				}
			});
		}
		return fileNewMenuItem;
	}

	/**
	 * This method initializes fileSaveMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getFileSaveMenuItem() {
		if (fileSaveMenuItem == null) {
			fileSaveMenuItem = new JMenuItem();
			fileSaveMenuItem.setText("Save as...");
			fileSaveMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					save();
				}
			});
		}
		return fileSaveMenuItem;
	}

	/**
	 * This method initializes fileOpenMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getFileOpenMenuItem() {
		if (fileOpenMenuItem == null) {
			fileOpenMenuItem = new JMenuItem();
			fileOpenMenuItem.setText("Open...");
			fileOpenMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					open();
				}
			});
		}
		return fileOpenMenuItem;
	}

	/**
	 * This method initializes fileSeperator1	
	 * 	
	 * @return javax.swing.JSeparator	
	 */
	private JSeparator getFileSeperator1() {
		if (fileSeperator1 == null) {
			fileSeperator1 = new JSeparator();
		}
		return fileSeperator1;
	}

	/**
	 * This method initializes fileExportMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getFileExportMenuItem() {
		if (fileExportMenuItem == null) {
			fileExportMenuItem = new JMenuItem();
			fileExportMenuItem.setText("Export World File...");
			fileExportMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					exportWorld();
				}
			});
		}
		return fileExportMenuItem;
	}

	protected void exportWorld() {
		CustomFileFilter filter = new CustomFileFilter(Constants.WORLD_FILE_DESCRIPTION, Constants.WORLD_FILE_EXTENSION);
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(filter);
        int returnVal = fileChooser.showSaveDialog(this);
        getMaze().renumberWorld(((Integer)getMaze().getZone().getValue("startVNUM")).intValue());
        if(returnVal == JFileChooser.APPROVE_OPTION && fileChooser.getSelectedFile()!=null) {
            File file = fileChooser.getSelectedFile();
            String ext = null;
            String fileName = file.getName();
            int i = fileName.lastIndexOf('.');
            if (i > 0 &&  i < fileName.length() - 1) {
                ext = fileName.substring(i+1).toLowerCase();
            }
            if(ext==null) {
            	file = new File(file.getPath()+"."+Constants.WORLD_FILE_EXTENSION);
            }
            
			maze.writeWorldFile(file);
        }
	}

	/**
	 * This method initializes mainPopupMenu	
	 * 	
	 * @return javax.swing.JPopupMenu	
	 */
	private JPopupMenu getMainPopupMenu() {
		if (mainPopupMenu == null) {
			mainPopupMenu = new JPopupMenu();
			mainPopupMenu.add(getPopupEditRoomMenuItem());
			mainPopupMenu.add(getPopupSeperator1());
			mainPopupMenu.add(getPopupSetStartMenuItem());
			mainPopupMenu.add(getPopupSetEndMenuItem());
			mainPopupMenu.add(getPopupClearSolutionMenuItem());
			mainPopupMenu.add(getPopupSeperator2());
			mainPopupMenu.add(getPopupMarkRoomMenuItem());
			mainPopupMenu.add(getPopupClearMarksMenuItem());
		}
		return mainPopupMenu;
	}

	/**
	 * This method initializes popupSetStartMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getPopupSetStartMenuItem() {
		if (popupSetStartMenuItem == null) {
			popupSetStartMenuItem = new JMenuItem();
			popupSetStartMenuItem.setText("Set as Start");
			popupSetStartMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getMaze().setStartRoom(selectedRoom);
				}
			});
		}
		return popupSetStartMenuItem;
	}

	/**
	 * This method initializes popupSetEndMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getPopupSetEndMenuItem() {
		if (popupSetEndMenuItem == null) {
			popupSetEndMenuItem = new JMenuItem();
			popupSetEndMenuItem.setText("Set as End");
			popupSetEndMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getMaze().setEndRoom(selectedRoom);
				}
			});
		}
		return popupSetEndMenuItem;
	}

	/**
	 * This method initializes popupClearSolutionMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getPopupClearSolutionMenuItem() {
		if (popupClearSolutionMenuItem == null) {
			popupClearSolutionMenuItem = new JMenuItem();
			popupClearSolutionMenuItem.setText("Clear Solution");
			popupClearSolutionMenuItem
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							maze.clearSolution();
						}
					});
		}
		return popupClearSolutionMenuItem;
	}

	/**
	 * This method initializes popupClearMarksMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getPopupClearMarksMenuItem() {
		if (popupClearMarksMenuItem == null) {
			popupClearMarksMenuItem = new JMenuItem();
			popupClearMarksMenuItem.setText("Clear Marked Rooms");
			popupClearMarksMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					maze.clearSelectedRooms();
				}
			});
		}
		return popupClearMarksMenuItem;
	}

	/**
	 * This method initializes algorithmComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getAlgorithmComboBox() {
		if (algorithmComboBox == null) {
			algorithmComboBox = new JComboBox();
			DefaultComboBoxModel model = new DefaultComboBoxModel();
			model.addElement("Empty Area");
			model.addElement("Prim's Algorithm");
			model.addElement("Recursive Backtracker");
			model.addElement("Binary Tree");
			model.addElement("Growing Tree (Type 1)");
			model.addElement("Growing Tree (Type 2)");
			model.addElement("Growing Tree (Type 3)");
			algorithmComboBox.setModel(model);
		}
		return algorithmComboBox;
	}

	/**
	 * This method initializes vnumTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getVnumTextField() {
		if (vnumTextField == null) {
			vnumTextField = new JTextField();
			vnumTextField.setColumns(6);
			vnumTextField.setText("100");
		}
		return vnumTextField;
	}

	/**
	 * This method initializes popupEditRoomMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getPopupEditRoomMenuItem() {
		if (popupEditRoomMenuItem == null) {
			popupEditRoomMenuItem = new JMenuItem();
			popupEditRoomMenuItem.setText("Edit Room...");
			final MazeMaker app = this;
			popupEditRoomMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					RoomEditor editor = new RoomEditor(app, selectedRoom);
					
					editor.setLocationRelativeTo(app);
					int option = editor.showOptionsDialog();
					if(option == JOptionPane.OK_OPTION) {
						selectedRoom.setValue("title", editor.getTitleText());
						selectedRoom.setValue("description", editor.getDescriptionText());
						selectedRoom.setFlags(editor.getFlags());
						selectedRoom.setValue("mobLimit", new Integer(editor.getMobLimit()));
						selectedRoom.setValue("sectorType", new Integer(editor.getSectorType()));
					}
				}
			});
		}
		return popupEditRoomMenuItem;
	}

	/**
	 * This method initializes popupSeperator1	
	 * 	
	 * @return javax.swing.JSeparator	
	 */
	private JSeparator getPopupSeperator1() {
		if (popupSeperator1 == null) {
			popupSeperator1 = new JSeparator();
		}
		return popupSeperator1;
	}

	/**
	 * This method initializes popupSeperator2	
	 * 	
	 * @return javax.swing.JSeparator	
	 */
	private JSeparator getPopupSeperator2() {
		if (popupSeperator2 == null) {
			popupSeperator2 = new JSeparator();
		}
		return popupSeperator2;
	}

	/**
	 * This method initializes popupMarkRoomMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getPopupMarkRoomMenuItem() {
		if (popupMarkRoomMenuItem == null) {
			popupMarkRoomMenuItem = new JMenuItem();
			popupMarkRoomMenuItem.setText("Mark Room");
			popupMarkRoomMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					maze.toggleMark(selectedRoom);
				}
			});
		}
		return popupMarkRoomMenuItem;
	}


	/**
	 * This method initializes templatesToggleButton	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */
	private JToggleButton getTemplatesToggleButton() {
		if (templatesToggleButton == null) {
			templatesToggleButton = new JToggleButton();
			templatesToggleButton.setIcon(new ImageIcon(getClass().getResource("/images/templates2.png")));
			templatesToggleButton.setToolTipText("Toggle Room Templates View");
			templatesToggleButton.setPreferredSize(new Dimension(24, 24));
			templatesToggleButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(getTemplatesToggleButton().isSelected()) {
						getTemplatesDialog().setVisible(true);
					} else {
						getTemplatesDialog().setVisible(false);
					}
				}
			});
		}
		return templatesToggleButton;
	}

	/**
	 * This method initializes mainToolBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private ToolBar getMainToolBar() {
		if (mainToolBar == null) {
			mainToolBar = new ToolBar();
		}
		return mainToolBar;
	}

	/**
	 * This method initializes seperator2	
	 * 	
	 * @return javax.swing.JSeparator	
	 */
	private JSeparator getSeperator2() {
		if (seperator2 == null) {
			seperator2 = new JSeparator();
			seperator2.setPreferredSize(new Dimension(2, 30));
			seperator2.setOrientation(SwingConstants.VERTICAL);
		}
		return seperator2;
	}

	/**
	 * This method initializes levelUpButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getLevelUpButton() {
		if (levelUpButton == null) {
			levelUpButton = new JButton();
			levelUpButton.setIcon(new ImageIcon(getClass().getResource("/images/arrow_up.png")));
			levelUpButton.setToolTipText("Up one level");
			levelUpButton.setPreferredSize(new Dimension(24, 24));
			levelUpButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					maze.incrementCurrentLevel();
					getLevelTextField().setText(""+maze.getCurrentLevel());
					maze.repaint();
				}
			});
		}
		return levelUpButton;
	}

	/**
	 * This method initializes levelTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getLevelTextField() {
		if (levelTextField == null) {
			levelTextField = new JTextField();
			levelTextField.setColumns(2);
			levelTextField.setText("0");
			levelTextField.setHorizontalAlignment(JTextField.CENTER);
			levelTextField.setEditable(false);
		}
		return levelTextField;
	}

	/**
	 * This method initializes levelDownButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getLevelDownButton() {
		if (levelDownButton == null) {
			levelDownButton = new JButton();
			levelDownButton.setPreferredSize(new Dimension(24, 24));
			levelDownButton.setToolTipText("Down one level");
			levelDownButton.setIcon(new ImageIcon(getClass().getResource("/images/arrow_down.png")));
			levelDownButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					maze.decrementCurrentLevel();
					getLevelTextField().setText(""+maze.getCurrentLevel());
					maze.repaint();
				}
			});
		}
		return levelDownButton;
	}

	/**
	 * This method initializes viewColorRoomsMenuItem	
	 * 	
	 * @return javax.swing.JCheckBoxMenuItem	
	 */
	private JCheckBoxMenuItem getViewColorRoomsMenuItem() {
		if (viewColorRoomsMenuItem == null) {
			viewColorRoomsMenuItem = new JCheckBoxMenuItem();
			viewColorRoomsMenuItem.setText("Color Sector Types");
			viewColorRoomsMenuItem.setSelected(true);
			viewColorRoomsMenuItem
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							getMaze().setUseColors(getViewColorRoomsMenuItem().isSelected());
							maze.repaint();
						}
					});
		}
		return viewColorRoomsMenuItem;
	}

	/**
	 * This method initializes renumberWorldButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getRenumberWorldButton() {
		if (renumberWorldButton == null) {
			renumberWorldButton = new JButton();
			renumberWorldButton.setMnemonic(KeyEvent.VK_UNDEFINED);
			renumberWorldButton.setPreferredSize(new Dimension(24, 24));
			renumberWorldButton.setToolTipText("Re-number World");
			renumberWorldButton.setIcon(new ImageIcon(getClass().getResource("/images/renumber.png")));
			renumberWorldButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					renumberWorld();
				}
			});
		}
		return renumberWorldButton;
	}

	protected void renumberWorld() {
		SwingWorker worker = new SwingWorker() {
            public Object construct() {
                long time = System.currentTimeMillis();
                busy(true);
                setMessage("Re-numbering world...");
                
                getMaze().renumberWorld(Integer.parseInt(getVnumTextField().getText()));
                
                time = System.currentTimeMillis()-time;
                busy(false);
                setMessage("Re-numbering completed. Total time: "+(time>1999?time/1000:time)+(time>1999?" seconds.":" milliseconds."));
                return null;
            }
		};
		worker.start();
	}

	public static void main(String[] args) {
		MazeMaker app = new MazeMaker();
		
		app.setLocationRelativeTo(null);
		app.setVisible(true);
	}
	
	public void setMaze(Maze newMaze) {
		maze = newMaze;
		maze.addMouseListener(new java.awt.event.MouseAdapter() {   
			public void mouseClicked(java.awt.event.MouseEvent e) {
				Coordinate location = maze.getCellAt(e.getX(), e.getY());
				if(location != null) {
					if(getMainToolBar().getSelectedTool() == ToolBar.TOOL_ROOM && !SwingUtilities.isRightMouseButton(e)) {
						if(maze.toggleRoom(location))
							getMaze().getZone().setCurrentLocation(location);
					} else {
						selectedRoom = maze.getCell(location.getX(), location.getY(), location.getZ());
						if(selectedRoom != null) {
							maze.getZone().setCurrentLocation(location);
							maze.repaint();
							if(SwingUtilities.isRightMouseButton(e)) {
								getMainPopupMenu().show(e.getComponent(), e.getX(), e.getY());
							} else {
								if(getMainToolBar().getSelectedTool() == ToolBar.TOOL_WALL) {
									Rectangle2D roomBounds = maze.getRoomBounds(selectedRoom);
									Point2D upperLeft = new Point2D.Double(), lowerRight = new Point2D.Double();
									upperLeft.setLocation(roomBounds.getX(), roomBounds.getY());
									lowerRight.setLocation(roomBounds.getX()+roomBounds.getWidth(), roomBounds.getY()+roomBounds.getHeight());
									double xMargin = roomBounds.getWidth()/5;
									double yMargin = roomBounds.getHeight()/5;
									if(e.getX() > upperLeft.getX()+xMargin && e.getX() < lowerRight.getX()-xMargin) {
										if(e.getY() < upperLeft.getY()+yMargin) {
											maze.toggleWall(selectedRoom, Exit.DIRECTION_NORTH);
										} else if(e.getY() > lowerRight.getY()-yMargin) {
											maze.toggleWall(selectedRoom, Exit.DIRECTION_SOUTH);
										}
									} else if(e.getY() > upperLeft.getY()+yMargin && e.getY() < lowerRight.getY()-yMargin) {
										if(e.getX() < upperLeft.getX()+xMargin) {
											maze.toggleWall(selectedRoom, Exit.DIRECTION_WEST);
										} else if(e.getX() > lowerRight.getX()-xMargin) {
											maze.toggleWall(selectedRoom, Exit.DIRECTION_EAST);
										}
									}
								} else if(getMainToolBar().getSelectedTool() == ToolBar.TOOL_WALL_UP) {
									maze.toggleWall(selectedRoom, Exit.DIRECTION_UP);
								} else if(getMainToolBar().getSelectedTool() == ToolBar.TOOL_WALL_DOWN) {
									maze.toggleWall(selectedRoom, Exit.DIRECTION_DOWN);
								} else if(getMainToolBar().getSelectedTool() == ToolBar.TOOL_TEMPLATE) {
									RoomTemplate template = maze.getZone().getRoomTemplates().getDefaultTemplate();
									if(template != null) {
										maze.getZone().copyToRoom(selectedRoom, template);
										setMessage("Current room: "+selectedRoom.getCoordinates()+" - #"+selectedRoom.getVNUM()+" - "+selectedRoom.getTitle());
									}
								}
							}
						}
					}
				}
				
			}   
			public void mouseReleased(java.awt.event.MouseEvent e) {    
				lastDragLocation = null;
			}
		
		});
		maze.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
			public void mouseDragged(java.awt.event.MouseEvent e) {
				if(SwingUtilities.isRightMouseButton(e)) {
					if(lastDragLocation == null) {
						lastDragLocation = e.getPoint();
						maze.markCenter();
					} else {
						maze.shiftCenter(-((int)lastDragLocation.getX()-e.getX()), -((int)lastDragLocation.getY()-e.getY()));
						maze.repaint();
					}
				}
			}
			public void mouseMoved(java.awt.event.MouseEvent e) {
				if(!getGlassPane().isVisible()) {
					Coordinate location = maze.getCellAt(e.getX(), e.getY());
					if(location != null) {
						Room room = maze.getCell(location.getX(), location.getY(), location.getZ());
						if(room != null) {
							setMessage("Current room: "+room.getCoordinates()+" - #"+room.getVNUM()+" - "+room.getTitle());
						}
					}
				}
//				setMessage("Mouse at: ("+e.getX()+","+e.getY()+")");
			}
		});
		maze.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				if(e.getWheelRotation() < 0) {
					maze.zoomIn(0.1);
					maze.repaint();
				} else if(e.getWheelRotation() > 0) {
					maze.zoomOut(0.1);
					maze.repaint();
				}
			}
		});
	}

	public Maze getMaze() {
		if(maze == null) {
			setMaze(new Maze());
		}
		return maze;
	}
	
	public void setMessage(String text) {
		statusLabel.setText(text);
	}
	
	public void setProgress(int percent) {
		getProgressBar().setValue(percent);
	}
	
	public void busy(boolean state) {
        if (state) {
            busyCount++;
            if (busyCount==1) {
                getGlassPane().setVisible(true);
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            }
        }
        else {
            busyCount--;
            if (busyCount==0) {
                getGlassPane().setVisible(false);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }
	
	public void save() {
		CustomFileFilter filter = new CustomFileFilter(Constants.MAZE_FILE_DESCRIPTION, Constants.MAZE_FILE_EXTENSION);
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
            	file = new File(file.getPath()+"."+Constants.MAZE_FILE_EXTENSION);
            }
            
            try {
				FileOutputStream fileOut = new FileOutputStream(file.getPath());
				ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
				objOut.writeObject(getMaze());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	}
		
	public void open() {
		CustomFileFilter filter = new CustomFileFilter(Constants.MAZE_FILE_DESCRIPTION, Constants.MAZE_FILE_EXTENSION);
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
            	file = new File(file.getPath()+"."+Constants.MAZE_FILE_EXTENSION);
            }
            
            try {
				FileInputStream fileIn = new FileInputStream(file.getPath());
				ObjectInputStream objIn = new ObjectInputStream(fileIn);
				Object tempObj = objIn.readObject();
				if(tempObj instanceof Maze) {
					getJContentPane().remove(getMaze());
					setMaze((Maze)tempObj);
					jContentPane.add(getMaze(), BorderLayout.CENTER);
					getMaze().repaint();
					getVnumTextField().setText(""+getMaze().getZone().getValue("startVNUM"));
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

	public RoomTemplatesDialog getTemplatesDialog() {
		if(templatesDialog == null) {
			templatesDialog = new RoomTemplatesDialog(this, getMaze().getZone().getRoomTemplates(), getMaze().getZone());
			templatesDialog.setLocationRelativeTo(this);
			templatesDialog.addWindowListener(new java.awt.event.WindowAdapter() {
				public void windowClosing(java.awt.event.WindowEvent e) {
					getTemplatesToggleButton().setSelected(false);
				}
			});
		}
		return templatesDialog;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
