package com.dap.blackmud.tools.mazemaker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Random;
import java.util.Stack;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.dap.blackmud.tools.mazemaker.utils.SwingWorker;
import com.dap.blackmud.world.Coordinate;
import com.dap.blackmud.world.Exit;
import com.dap.blackmud.world.Room;
import com.dap.blackmud.world.RoomTemplate;
import com.dap.blackmud.world.Zone;

public class Maze extends JPanel {

	private static final long serialVersionUID = 1L;
	public static final int ALGORITHM_EMPTY = 0;
	public static final int ALGORITHM_PRIMS = 1;
	public static final int ALGORITHM_RECURSIVEBACKTRACKER = 2;
	public static final int ALGORITHM_BINARYTREE = 3;
	public static final int ALGORITHM_GROWINGTREE = 4;
	
	private static final Color COLOR_SEPERATOR = new Color(240,240,240);
	private static final Color COLOR_ARROW = new Color(240,240,240);
	private static final Color COLOR_VOID = new Color(50,50,50);
	private static final Color COLOR_WALL = Color.black;
	private static final Color COLOR_START_ROOM = Color.green;
	private static final Color COLOR_END_ROOM = Color.red;
	private static final Color COLOR_SOLUTION_PATH = Color.cyan;
	private static final Color COLOR_MARKED_ROOM = Color.red;
	
	private static final Color[] COLOR_SECTORS = {
		new Color(255,255,255), // Inside
		new Color(200,200,200),	// City
		new Color(0,192,0),		// Field
		new Color(0,128,0),		// Forest
		new Color(0,160,0),		// Hills
		new Color(192,192,192),	// Mountains
		new Color(64,64,255),	// Water (Swim)
		new Color(64,64,255),	// Water (No swim)
		new Color(247,255,177),	// Air
		new Color(0,0,128),		// Underwater
		new Color(168,167,108),	// Desert
		new Color(0,128,0),		// Tree
		new Color(255,111,15),	// Fire
		new Color(170,104,55)};	// Inground

	public static final int MAX_ROOMS = 40000;
	
	private Zone zone = null;  //  @jve:decl-index=0:
	private double zoom = 1.0;
	private int cellWidth = 0;
	private int cellHeight = 0;
	private int xOffset = 0;
	private int yOffset = 0;
	private int centerX = 0;
	private int centerY = 0;
	private int origCenterX = 0;
	private int origCenterY = 0;
	private int maxX = 0;
	private int maxY = 0;
	private int minX = 0;
	private int minY = 0;
	private int currentLevel = 0;
	
	private HashSet solutionPath = null;
	private HashSet markedRooms = null;
	
	private boolean drawSeperators = false;
	private boolean drawArrows = false;
	private boolean animate = false;
	
	private boolean mazeComplete = true;
	
	private Room startRoom = null;
	private Room endRoom = null;
	
	private boolean useColors = true;
	
	/**
	 * This is the default constructor
	 */
	public Maze() {
		super();
		initializeMaze(1, "New Zone", 224000);
//		regenerateMaze(10, 10, 0);
		initialize();
	}

	public void regenerateMaze(final int algorithm, final int startingVNUM, final int width, final int height, final MazeMaker app) {
		if(width*height > MAX_ROOMS) {
			JOptionPane.showMessageDialog(app, "Unable to generate a maze of that size.", "Maze too large!", JOptionPane.ERROR_MESSAGE);
			app.setMessage("Maze is too large!");
			return;
		}
		SwingWorker worker = new SwingWorker() {
            public Object construct() {
                long time = System.currentTimeMillis();
                app.busy(true);

                maxX = width;
        		maxY = height;
        		minX = 0;
        		minY = 0;
        		mazeComplete = false;
        		if(algorithm == ALGORITHM_EMPTY) {
        			runCreateEmptyArea(width, height, currentLevel, app);
        		} else if(algorithm == ALGORITHM_PRIMS) {
        			runPrimsAlgorithm(width, height, currentLevel, app);
        		} else if(algorithm == ALGORITHM_RECURSIVEBACKTRACKER) {
        			runRecursiveBacktracker(width, height, currentLevel, app);
        		} else if( algorithm == ALGORITHM_BINARYTREE) {
        			runBinaryTree(width, height, currentLevel, app);
        		} else {
        			runGrowingTree(width, height, currentLevel, app, algorithm-ALGORITHM_GROWINGTREE);
        		}
        		app.setMessage("Renumbering rooms...");
        		renumberWorld(startingVNUM);
        		maxX = zone.getMaxX()+1;
        		maxY = zone.getMaxY()+1;
        		minX = zone.getMinX();
        		minY = zone.getMinY();
        		app.setProgress(100);
                mazeComplete = true;
        		repaint();
        		
                time = System.currentTimeMillis()-time;
                app.busy(false);
                app.setMessage("Total generation time: "+(time>1999?time/1000:time)+(time>1999?" seconds.":" milliseconds."));
                return null;
            }
        };
        worker.start();
	}
	
	private void runBinaryTree(int width, int height, int level, MazeMaker app) {
		int totalCarved = 0;
		Random generator = new Random(System.currentTimeMillis());
		Vector roomList = new Vector(width*height+1);
		Room curRoom = null, nextRoom = null;
		Coordinate coord = null;
		int dir = 0;
		
		app.setMessage("Reseting maze...");
		resetLevel(level);
		
		app.setMessage("Generating maze...");
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				coord = new Coordinate(x, y, level);
				curRoom = zone.getRoom(coord);
				if(curRoom == null) {
					curRoom = createRoom(coord);
					totalCarved++;
					app.setProgress((int)(((double)totalCarved/(double)(width*height))*100.0));
					if(animate) {
						pause(2);
						repaint();
					}
				}

				if(generator.nextInt(2) == 0) {
					dir = Exit.DIRECTION_SOUTH;
					if(Zone.getNeighborLocation(curRoom.getCoordinates(), dir).getY() < 0)
						dir = Exit.DIRECTION_WEST;	
				} else {
					dir = Exit.DIRECTION_WEST;
					if(Zone.getNeighborLocation(curRoom.getCoordinates(), dir).getX() < 0)
						dir = Exit.DIRECTION_SOUTH;	
				}
				coord = Zone.getNeighborLocation(curRoom.getCoordinates(), dir);

				if(coord.getX() >= 0 && coord.getX() < width &&
						coord.getY() >= 0 && coord.getY() < height &&
						coord.getZ() == level) {
					nextRoom = zone.getRoom(coord);
					if(nextRoom == null) {
						nextRoom = createRoom(coord);
						totalCarved++;
						app.setProgress((int)(((double)totalCarved/(double)(width*height))*100.0));
						if(animate) {
							pause(2);
							repaint();
						}
					}
					zone.connect(curRoom, dir);
				}
			}
		}
	}
	
	private void runGrowingTree(int width, int height, int level, MazeMaker app, int type) {
		int totalCarved = 0;
		Random generator = new Random(System.currentTimeMillis());
		Vector roomList = new Vector(width*height+1);
		Room curRoom = null, nextRoom = null;
		Coordinate coord = null;
		int[] directions = new int[] { Exit.DIRECTION_NORTH, Exit.DIRECTION_EAST, Exit.DIRECTION_SOUTH, Exit.DIRECTION_WEST};
		boolean carved = false;
		int dir = 0, roomIndex = 0;
		
		app.setMessage("Reseting maze...");
		resetLevel(level);
		
		app.setMessage("Generating maze...");
		// Pick a random location to start
		coord = new Coordinate(generator.nextInt(width), generator.nextInt(height), level);
		curRoom = createRoom(coord);
		roomList.add(curRoom);
		totalCarved++;
		
		while(roomList.size() > 0) {
			app.setProgress((int)(((double)totalCarved/(double)(width*height))*100.0));
			
			// Pick a room from the list
			if(type == 0) {
				if(generator.nextInt(5) < 3) {
					roomIndex = roomList.size()-1;
				} else {
					roomIndex = generator.nextInt(roomList.size());
				}
			} else if(type == 1) {
				if(generator.nextInt(5) < 4) {
					//roomIndex = roomList.size()-1;
					roomIndex = 4*(roomList.size()/5)+generator.nextInt(Math.max(roomList.size()/5, 1));
				} else {
					roomIndex = generator.nextInt(roomList.size());
				}
			} else {
				if(generator.nextInt(5) < 2) {
					//roomIndex = roomList.size()-1;
					roomIndex = generator.nextInt(Math.max(roomList.size()/5, 1));
				} else {
					roomIndex = generator.nextInt(roomList.size());
				}
			}
			curRoom = (Room)roomList.get(roomIndex);
			
			randomizeArray(generator, directions, 5);
			carved = false;
			for(int i = 0; i < directions.length; i++) {
				dir = directions[i];
				coord = Zone.getNeighborLocation(curRoom.getCoordinates(), dir);
				if(!zone.roomExistsAt(coord) &&
						coord.getX() >= 0 && coord.getX() < width &&
						coord.getY() >= 0 && coord.getY() < height &&
						coord.getZ() == level) {
					nextRoom = createRoom(coord);
					totalCarved++;
					carved = true;
					roomList.add(nextRoom);
					zone.connect(curRoom, dir);
					if(animate) {
						pause(2);
						repaint();
					}
				}
			}
			if(!carved) {
				roomList.remove(roomIndex);
			}
		}
	}
	
	private void runCreateEmptyArea(int width, int height, int level, MazeMaker app) {
		Room curRoom = null, otherRoom = null;
		Coordinate coord = null;
		int counter = 0;
		
		app.setMessage("Reseting maze...");
		resetLevel(level);
		
		app.setMessage("Generating maze...");
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				curRoom = createRoom(new Coordinate(x, y, level));
				counter++;
				app.setProgress((int)(((double)counter/(double)(width*height))*100.0));
				if(animate) {
					pause(2);
					repaint();
				}
				for(int dir = Exit.MINIMUM_DIRECTION; dir <= Exit.DIRECTION_WEST; dir++) {
					coord = Zone.getNeighborLocation(curRoom.getCoordinates(), dir);
					otherRoom = zone.getRoom(coord);
					if(otherRoom != null) {
						zone.connect(curRoom, dir);
					}
				}
			}
		}
	}
	
	private void runRecursiveBacktracker(int width, int height, int level, MazeMaker app) {
		Random generator = new Random(System.currentTimeMillis());
		Stack roomStack = new Stack();
		Coordinate nextCoords = null;
		Room nextRoom = null, curRoom = null;
		int totalCarved = 1;
		int[] directions = new int[] { Exit.DIRECTION_NORTH, Exit.DIRECTION_EAST, Exit.DIRECTION_SOUTH, Exit.DIRECTION_WEST};
		int dir = 0;
		int dirIndex = 0;
		boolean validRoom = false;
		
		app.setMessage("Reseting maze...");
		resetLevel(level);
		
		app.setMessage("Generating maze...");
		//recursiveCarve(generator, width, height, level, app, createRoom(new Coordinate(0,0,0)), 1);
		roomStack.push(createRoom(new Coordinate(0,0,0)));
		while(roomStack.size() > 0) {
			if(animate) {
				pause(2);
				repaint();
			}
			curRoom = (Room)roomStack.peek();
			
			randomizeArray(generator, directions, 10);
			validRoom = false;
			for(int i = 0; i < directions.length; i++) {
				dir = directions[i];
				nextCoords = Zone.getNeighborLocation(curRoom.getCoordinates(), dir);
				if(nextCoords.getX() >= 0 && nextCoords.getX() < width &&
						nextCoords.getY() >=0 && nextCoords.getY() < height &&
						nextCoords.getZ() == level) {
					if(!zone.roomExistsAt(nextCoords)) {
						validRoom = true;
						break;
					}
				}
			}
//			do {
//				nextCoords = Zone.getNeighborLocation(curRoom.getCoordinates(), dir);
//				if(zone.roomExistsAt(nextCoords) || nextCoords.getX() < 0 || nextCoords.getX() >= width &&
//						nextCoords.getY() < 0 && nextCoords.getY() >= height ||
//						nextCoords.getZ() != level) {
//					dirIndex = (dirIndex+1)%directions.length;
//					dir = directions[dirIndex];
//				} else
//					break;
//			} while(origDirIndex != dirIndex);
			if(validRoom) {
				nextRoom = createRoom(nextCoords);
				zone.connect(curRoom, dir);
				roomStack.push(nextRoom);
				totalCarved++;
				app.setProgress((int)(((double)totalCarved/(double)(width*height))*100.0));
			} else {
				roomStack.pop();
			}
		}
	}
	
	private void randomizeArray(Random generator, int[] array, int passes) {
		int temp = 0;
		int toIndex = 0;
		int fromIndex = 0;
		for(int i = 0; i < passes; i++) {
			toIndex = generator.nextInt(array.length);
			fromIndex = generator.nextInt(array.length);
			if(fromIndex == toIndex) {
				fromIndex = (toIndex+1)%array.length;
			}
			temp = array[toIndex];
			array[toIndex] = array[fromIndex];
			array[fromIndex] = temp;
		}
	}

	private void runPrimsAlgorithm(int width, int height, int level, MazeMaker app) {
		app.setMessage("Reseting maze...");
		resetLevel(level);
		Vector inCells = new Vector();
		Vector outCells = new Vector();
		Vector frontierCells = new Vector();
		Coordinate curLocation = null, otherLocation = null;
		Random generator = new Random(System.currentTimeMillis());
		Room curRoom = null;
		int value = 0;
		
		// Setup
		app.setMessage("Prepping maze...");
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				curLocation = new Coordinate(x, y, level);
				outCells.add(curLocation);
			}
			app.setProgress((int)(((double)y/(double)height)*100.0));
		}
		
		app.setMessage("Generating maze...");
		// Pick a random cell to start with, make it "in"
		value = generator.nextInt(outCells.size());
		curLocation = (Coordinate)outCells.get(value);
		inCells.add(curLocation);
		outCells.remove(value);
		curRoom = createRoom(curLocation);
		
		// Set all neighbors to the current room to "frontier"
		for(int i = Exit.MINIMUM_DIRECTION; i <= Exit.DIRECTION_WEST; i++) {
			otherLocation = Zone.getNeighborLocation(curLocation, i);
			if(outCells.contains(otherLocation)) {
				outCells.remove(otherLocation);
				frontierCells.add(otherLocation);
			}
		}

		Vector inNeighbors = new Vector();
		while(frontierCells.size() > 0) {
			if(animate) {
				pause(2);
				repaint();
			}
			//System.out.println("Frontier: "+frontierCells.size()+"\tIn: "+inCells.size()+"\tOut: "+outCells.size());
			inNeighbors.clear();
			app.setProgress((int)(((double)inCells.size()/(double)(width*height))*100.0));
			
			// Pick a random frontier cell
			value = generator.nextInt(frontierCells.size());
			curLocation = (Coordinate)frontierCells.get(value);
			curRoom = createRoom(curLocation);
			inCells.add(curLocation);
			frontierCells.remove(value);
			
			// Pick a random "in" neighbor, and carve from it
			for(int i = Exit.MINIMUM_DIRECTION; i <= Exit.DIRECTION_WEST; i++) {
				otherLocation = Zone.getNeighborLocation(curLocation, i);
				if(inCells.contains(otherLocation))
					inNeighbors.add(new Integer(i));
				else if(outCells.contains(otherLocation)) {
					outCells.remove(otherLocation);
					frontierCells.add(otherLocation);
				}
			}
			if(inNeighbors.size() > 0) {
				zone.connect(curRoom, ((Integer)inNeighbors.get(generator.nextInt(inNeighbors.size()))).intValue());
			}
		}
	}
		
	private void pause(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void resetLevel(int level) {
		zone.resetLevel(level);
	}
	
	private Room createRoom(Coordinate location) {
		Room newRoom = new Room(zone.getNextVNUM(true), location);
		RoomTemplate template = zone.getRoomTemplates().getDefaultTemplate();
		if(template != null) {
			zone.copyToRoom(newRoom, template);
		}
		zone.insertRoom(newRoom);
		return newRoom;
	}

//	private void carveRoom(int direction) throws RoomCreationException {
//		if(zone == null) {
//			throw new RoomCreationException("Zone is null");
//		}
//		
//		Coordinate currentLocation = zone.getCurrentLocation();
//		Room currentRoom = zone.getCurrentRoom();
//		Coordinate newLocation = Zone.getNeighborLocation(currentLocation, direction);
//		
//		if(zone.roomExistsAt(newLocation)) {
//			System.out.println("Room exists at "+newLocation);
//			if(!currentRoom.hasExit(direction)) {
//				System.out.println("\tConnecting "+currentLocation+" to "+newLocation);
//				zone.connect(currentRoom, direction);
//			}
//			System.out.println("\tMoving to "+newLocation);
//			zone.setCurrentLocation(newLocation);
//		} else {
//			System.out.println("Creating new room at "+newLocation);
//			Room newRoom = new Room(zone.getNextVNUM(true), newLocation);
//			zone.insertRoom(newRoom);
//			zone.connect(currentRoom, direction);
//			
//			RoomTemplate template = zone.getRoomTemplates().getDefaultTemplate();
//			if(template != null) {
//				zone.copyToRoom(newRoom, template);
//			}
//			
//			zone.setCurrentLocation(newLocation);
//		}
//	}

	private void initializeMaze(int zoneNumber, String zoneName, int startVNUM) {
		zone = new Zone(zoneNumber, zoneName, startVNUM, 0, 0);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.white);
		this.addComponentListener(new java.awt.event.ComponentAdapter() {
			public void componentResized(java.awt.event.ComponentEvent e) {
				setCenter(getWidth()/2, getHeight()/2);
				repaint();
			}
		});
	}

	public void paint(Graphics g) {
		//super.paint(g);
		drawMaze(g);
	}

	private void drawMaze(Graphics g) {
		if(zone == null || (!mazeComplete && !animate)) {
			super.paint(g);
			return;
		}
		
		cellWidth = (int)((this.getWidth()/((maxX-minX)+2))*zoom);
		cellHeight= (int)((this.getHeight()/((maxY-minY)+2))*zoom);
		
		if(cellWidth < cellHeight)
			cellHeight = cellWidth;
		else
			cellWidth = cellHeight;
		
		xOffset = (centerX)-(cellWidth*(maxX-minX))/2;
		yOffset = (centerY)+(cellHeight*(maxY-minY))/2;
		
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		Point2D upperLeft = new Point2D.Double();
		Point2D lowerRight = new Point2D.Double();
		Polygon arrowHead = new Polygon();

		int alen = cellWidth/7, dist = cellWidth/20;
		Room curRoom = null;

		for(int y=minY; y < maxY; y++) {
			for(int x = minX; x < maxX; x++) {
				upperLeft.setLocation(xOffset + x*cellWidth+1, yOffset - y*cellHeight-cellHeight);
				lowerRight.setLocation((int)upperLeft.getX()+cellWidth-1, (int)upperLeft.getY()+cellHeight-1);
				if(upperLeft.getX() > -cellWidth && upperLeft.getY() > -cellHeight &&
						lowerRight.getX() < getWidth()+cellWidth && lowerRight.getY() < getHeight()+cellHeight) {
					if(validCell(x, y, currentLevel)) {
						curRoom = getCell(x,y,currentLevel);
						
						g.setColor(Color.WHITE);
						if(useColors) {
							g.setColor(COLOR_SECTORS[curRoom.getSectorType()]);
							g.fillRect((int)upperLeft.getX(), (int)upperLeft.getY(), cellWidth, cellHeight);
						}

						if(getSolutionPath().contains(curRoom)) {
							g.setColor(COLOR_SOLUTION_PATH);
							g.fillRect((int)upperLeft.getX(), (int)upperLeft.getY(), cellWidth, cellHeight);
						}
						if(curRoom == startRoom) {
							g.setColor(COLOR_START_ROOM);
							g.fillRect((int)upperLeft.getX()+1, (int)upperLeft.getY()+1, cellWidth-2, cellHeight-2);
						}
						if(curRoom == endRoom) {
							g.setColor(COLOR_END_ROOM);
							g.fillRect((int)upperLeft.getX()+1, (int)upperLeft.getY()+1, cellWidth-2, cellHeight-2);
						}
						if(curRoom.getCoordinates().equals(zone.getCurrentLocation())) {
							for(int i = 1 ; i < Math.min(cellWidth/4+1, 6) ; i++) {
								g.setColor(new Color(Math.max(255-(i*10), 100), Math.max(255-(i*10), 100), 255));
								g.drawRect((int)upperLeft.getX()+i, (int)upperLeft.getY()+i, cellWidth-(i*2), cellHeight-(i*2));
							}
						}
						if(getMarkedRooms().contains(curRoom)) {
							g.setColor(COLOR_MARKED_ROOM);
							g.drawLine((int)upperLeft.getX(), (int)upperLeft.getY(), (int)lowerRight.getX(), (int)lowerRight.getY());
							g.drawLine((int)lowerRight.getX(), (int)upperLeft.getY(), (int)upperLeft.getX(), (int)lowerRight.getY());
						}
						if(curRoom.hasExit(Exit.DIRECTION_NORTH)) {
							if(drawArrows) {
								g.setColor(COLOR_ARROW);
								arrowHead.reset();
								arrowHead.addPoint((int)upperLeft.getX()+(cellWidth/2), (int)upperLeft.getY()+dist);
								arrowHead.addPoint((int)upperLeft.getX()+(cellWidth/2)+alen, (int)upperLeft.getY()+alen+dist);
								arrowHead.addPoint((int)upperLeft.getX()+(cellWidth/2)-alen, (int)upperLeft.getY()+alen+dist);
								g.fillPolygon(arrowHead);
							}
							if(drawSeperators) {
								g.setColor(COLOR_SEPERATOR);
								g.drawLine((int)upperLeft.getX()+1, (int)upperLeft.getY(),
										(int)lowerRight.getX()-1, (int)upperLeft.getY());
							}
						} else {
							g.setColor(COLOR_WALL);
							g.drawLine((int)upperLeft.getX(), (int)upperLeft.getY(),
									(int)lowerRight.getX(), (int)upperLeft.getY());
						}
						if(curRoom.hasExit(Exit.DIRECTION_EAST)) {
							if(drawArrows) {
								g.setColor(COLOR_ARROW);
								arrowHead.reset();
								arrowHead.addPoint((int)lowerRight.getX()-dist, (int)upperLeft.getY()+(cellHeight/2));
								arrowHead.addPoint((int)lowerRight.getX()-alen-dist, (int)upperLeft.getY()+(cellHeight/2)-alen);
								arrowHead.addPoint((int)lowerRight.getX()-alen-dist, (int)upperLeft.getY()+(cellHeight/2)+alen);
								g.fillPolygon(arrowHead);
							}
							if(drawSeperators) {
								g.setColor(COLOR_SEPERATOR);
								g.drawLine((int)lowerRight.getX(), (int)upperLeft.getY()+1,
										(int)lowerRight.getX(), (int)lowerRight.getY()-1);
							}
						} else {
							g.setColor(COLOR_WALL);
							g.drawLine((int)lowerRight.getX(), (int)upperLeft.getY(),
									(int)lowerRight.getX(), (int)lowerRight.getY());
						}
						if(curRoom.hasExit(Exit.DIRECTION_SOUTH)) {
							if(drawArrows) {
								g.setColor(COLOR_ARROW);
								arrowHead.reset();
								arrowHead.addPoint((int)upperLeft.getX()+(cellWidth/2), (int)lowerRight.getY()-dist);
								arrowHead.addPoint((int)upperLeft.getX()+(cellWidth/2)-alen, (int)lowerRight.getY()-alen-dist);
								arrowHead.addPoint((int)upperLeft.getX()+(cellWidth/2)+alen, (int)lowerRight.getY()-alen-dist);
								g.fillPolygon(arrowHead);
							}
							if(drawSeperators) {
								g.setColor(COLOR_SEPERATOR);
								g.drawLine((int)upperLeft.getX()+1, (int)lowerRight.getY(),
										(int)lowerRight.getX()-1, (int)lowerRight.getY());
							}
						} else {
							g.setColor(COLOR_WALL);
							g.drawLine((int)upperLeft.getX(), (int)lowerRight.getY(),
									(int)lowerRight.getX(), (int)lowerRight.getY());
						}
						if(curRoom.hasExit(Exit.DIRECTION_WEST)) {
							if(drawArrows) {
								g.setColor(COLOR_ARROW);
								arrowHead.reset();
								arrowHead.addPoint((int)upperLeft.getX()+dist, (int)upperLeft.getY()+(cellHeight/2));
								arrowHead.addPoint((int)upperLeft.getX()+alen+dist, (int)upperLeft.getY()+(cellHeight/2)-alen);
								arrowHead.addPoint((int)upperLeft.getX()+alen+dist, (int)upperLeft.getY()+(cellHeight/2)+alen);
								g.fillPolygon(arrowHead);
							}
							if(drawSeperators) {
								g.setColor(COLOR_SEPERATOR);
								g.drawLine((int)upperLeft.getX(), (int)upperLeft.getY()+1,
										(int)upperLeft.getX(), (int)lowerRight.getY()-1);
							}
						} else {
							g.setColor(COLOR_WALL);
							g.drawLine((int)upperLeft.getX(), (int)upperLeft.getY(),
									(int)upperLeft.getX(), (int)lowerRight.getY());
						}
						
						if(curRoom.hasExit(Exit.DIRECTION_DOWN)) {
							g.setColor(COLOR_WALL);
							g.drawRect((int)upperLeft.getX(), (int)upperLeft.getY(), cellWidth/4, cellHeight/2);
							for(int i = (int)upperLeft.getY(); i < (int)upperLeft.getY()+cellHeight/2; i+=3) {
								g.drawLine((int)upperLeft.getX(), i, (int)upperLeft.getX()+cellWidth/4, i);
							}
							arrowHead.reset();
							arrowHead.addPoint((int)upperLeft.getX()+(cellWidth/4)+2, (int)upperLeft.getY()+(cellHeight/4));
							arrowHead.addPoint((int)upperLeft.getX()+(cellWidth/4)+2+(cellWidth/8), (int)upperLeft.getY()+(cellHeight/4));
							arrowHead.addPoint((int)upperLeft.getX()+(cellWidth/4)+2+(cellWidth/16), (int)upperLeft.getY()+(cellHeight/4)+(cellWidth/12));
							g.fillPolygon(arrowHead);
						}
						if(curRoom.hasExit(Exit.DIRECTION_UP)) {
							g.setColor(COLOR_WALL);
							g.drawRect((int)lowerRight.getX()-cellWidth/4, (int)lowerRight.getY()-cellHeight/2, cellWidth/4, cellHeight/2);
							for(int i = (int)lowerRight.getY()-cellHeight/2; i < lowerRight.getY(); i+=3) {
								g.drawLine((int)lowerRight.getX()-cellWidth/4, i, (int)lowerRight.getX(), i);
							}
							arrowHead.reset();
							arrowHead.addPoint((int)lowerRight.getX()-cellWidth/4-2, (int)lowerRight.getY()-(cellHeight/4));
							arrowHead.addPoint((int)lowerRight.getX()-cellWidth/4-2-(cellWidth/8), (int)lowerRight.getY()-(cellHeight/4));
							arrowHead.addPoint((int)lowerRight.getX()-cellWidth/4-2-(cellWidth/16), (int)lowerRight.getY()-(cellHeight/4)-(cellWidth/12));
							g.fillPolygon(arrowHead);
						}
		
						//g.drawRect((int)upperLeft.getX(), (int)upperLeft.getY(), cellWidth , cellHeight);
						//g.fillRect(xOffset + x*cellWidth, yOffset + y*cellHeight, cellWidth , cellHeight);
					} else {
						g.setColor(COLOR_VOID);
						g.fillRect((int)upperLeft.getX(), (int)upperLeft.getY(), cellWidth , cellHeight);
					}
				}
			}
			/*
			for(int x=0; x < height; x++) {
				if (validCell(x*2, y*2 + 1))
					g.fillRect(xoff + x*xcel, yoff + y*ycel, xwid, ycel + ywid);
			}
			*/
		}
//		g.setColor(Color.red);
//		System.out.println("Cell size: ("+cellWidth+","+cellHeight+")");
//		System.out.println("Offset: ("+xOffset+","+yOffset+")");
//		System.out.println("Center: ("+centerX+","+centerY+")");
//		g.drawLine(xOffset, 0, xOffset, getHeight());
//		g.drawLine(0, yOffset, getWidth(), yOffset);
//		g.fillRect(centerX-2, centerY-2, 4, 4);
	}
	
	public boolean validCell(int x, int y, int z) {
		if(getCell(x,y,z) == null) {
			return false;
		}
		return true;
	}
	
	public boolean validCell(Coordinate location) {
		if(location == null)
			return false;
		return validCell(location.getX(), location.getY(), location.getZ());
	}
	
	public Room getCell(int x, int y, int z) {
		if(x < minX || x > maxX || y < minY || y > maxY)
			return null;
		if(zone == null)
			return null;
		Coordinate coord = new Coordinate(x, y, z);
		return zone.getRoom(coord);
	}
	
	public void zoomIn(double amount) {
		zoom += amount;
	}
	
	public void zoomOut(double amount) {
		zoom -= amount;
		if(zoom < 0)
			zoom = 0;
	}

	public Coordinate getCellAt(Coordinate location) {
		if(cellWidth == 0 || cellHeight == 0)
			return null;
		
//		if(location.getX() < xOffset || location.getY() < yOffset-(cellHeight*(maxY - minY)) ||
//				location.getX() > xOffset+(cellWidth*(maxX - minX)) || location.getY() > yOffset)
//			return null;
		int x = (location.getX()-xOffset)/cellWidth;
		int y = (location.getY()-yOffset)/-cellHeight;
		if(location.getX() < xOffset)
			x -= 1;
		
		if(location.getY() > yOffset)
			y -= 1;
		return new Coordinate(x, y, currentLevel);
	}
	
	public Coordinate getCellAt(int x, int y) {
		return getCellAt(new Coordinate(x, y, currentLevel));
	}
	
	public void shiftCenter(int deltaX, int deltaY) {
		centerX = origCenterX+deltaX;
		centerY = origCenterY+deltaY;
	}
	
	public void setCenter(int x, int y) {
		centerX = x;
		centerY = y;
	}
	
	public void markCenter() {
		origCenterX = centerX;
		origCenterY = centerY;
	}

	public void setAnimated(boolean state) {
		animate = state;
	}

	public void setArrowsDrawn(boolean state) {
		drawArrows = state;
	}

	public void setSeperatorsDrawn(boolean state) {
		drawSeperators = state;
	}

	public void renumberWorld(int startVNUM) {
		Room room = null;
		zone.setValue("startVNUM", new Integer(startVNUM));
		zone.resetVNUM();
		for(int z = zone.getMaxZ(); z >= zone.getMinZ(); z--) {
			for(int y = minY; y < maxY; y++) {
				for(int x = minX; x < maxX; x++) {
					room = zone.getRoom(new Coordinate(x, y, z));
					if(room != null) {
						room.setValue("VNUM", new Integer(zone.getNextVNUM(true)));
					}
				}
			}
		}
	}
	
	public void toggleMark(Room room) {
		if(getMarkedRooms().contains(room))
			getMarkedRooms().remove(room);
		else
			getMarkedRooms().add(room);
		repaint();
	}
	
	public void unselectRoom(Room room) {
		getMarkedRooms().remove(room);
		repaint();
	}

	public void clear() {
		zoom = 1.0;
		cellWidth = 0;
		cellHeight = 0;
		xOffset = 0;
		yOffset = 0;
		centerX = 0;
		centerY = 0;
		origCenterX = 0;
		origCenterY = 0;
		maxX = 0;
		maxY = 0;
		minX = 0;
		minY = 0;
		startRoom = null;
		endRoom = null;
		setCenter(getWidth()/2, getHeight()/2);
		initializeMaze(1, "New Zone", 224000);
		repaint();
	}

	public void writeWorldFile(File file) {
		zone.writeWorldFile(file);
	}
	
	public void setStartRoom(Room room) {
		if(room != endRoom) {
			this.startRoom = room;
			repaint();
		}
	}
	
	public void setEndRoom(Room room) {
		if(room != startRoom) {
			this.endRoom = room;
			repaint();
		}
	}
	
	public int solveMaze() {
		runShortestPathFinder();
		return getSolutionPath().size();
	}
	
	private void runShortestPathFinder() {
		Random generator = new Random(System.currentTimeMillis());
		boolean endReached = false;
		Room sourceRoom = null, destRoom = null;
		Vector frontierRooms = new Vector(zone.getRoomCount()+2), tempFrontier = new Vector(zone.getRoomCount()+2);
		Iterator itr = null;
		long startTime = System.currentTimeMillis();
		if(startRoom == null || endRoom == null)
			return;
		
		getSolutionPath().clear();
		Hashtable floodedRooms = new Hashtable(zone.getRoomCount()+2, 1.0f);
		//floodRoom(floodedRooms, startRoom, null);
		frontierRooms.add(startRoom);
		floodedRooms.put(startRoom, "null");
		int index = 0;
		while(!endReached) {// && (System.currentTimeMillis()-startTime) < 3000) {
			while(frontierRooms.size()>0) {
				index = generator.nextInt(frontierRooms.size());
				sourceRoom = (Room)frontierRooms.get(index);
				frontierRooms.remove(index);
				for(int dir = Exit.MINIMUM_DIRECTION; dir <= Exit.MAXIMUM_DIRECTION; dir++) {
					destRoom = zone.getRoom(Zone.getNeighborLocation(sourceRoom.getCoordinates(), dir));
					if(destRoom != null && sourceRoom.getExit(dir) != null && destRoom != sourceRoom && !floodedRooms.contains(destRoom)) {
						floodedRooms.put(destRoom, sourceRoom);
						tempFrontier.add(destRoom);
						if(destRoom == endRoom) {
							floodedRooms.put(endRoom, sourceRoom);
							endReached = true;
						}
					}
				}
			}
			frontierRooms.addAll(tempFrontier);
			if(frontierRooms.size() == 0) {
				endReached = true;
			}
			tempFrontier.clear();
		}
		
		Room curRoom = endRoom;
		while(curRoom != startRoom) {
			if(floodedRooms.get(curRoom) instanceof Room) {
				curRoom = (Room)floodedRooms.get(curRoom);
				if(curRoom != startRoom)
					getSolutionPath().add(curRoom);
			} else {
				curRoom = startRoom;
			}
		}
	
		repaint();
	}

	public HashSet getMarkedRooms() {
		if(markedRooms == null) {
			markedRooms = new HashSet();
		}
		return markedRooms;
	}
	
	public HashSet getSolutionPath() {
		if(solutionPath == null) {
			solutionPath = new HashSet();
		}
		return solutionPath;
	}
	
	public void clearSelectedRooms() {
		getMarkedRooms().clear();
		repaint();
	}
	
	public void clearSolution() {
		getSolutionPath().clear();
		repaint();
	}

	public Zone getZone() {
		return zone;
	}

	public Rectangle2D getRoomBounds(Room room) {
		Rectangle2D bounds = new Rectangle2D.Double();
		bounds.setRect(xOffset + room.getCoordinates().getX()*cellWidth+1, yOffset - room.getCoordinates().getY()*cellHeight-cellHeight, cellWidth, cellHeight);
		return bounds;
	}

	public void toggleWall(Room room, int dir) {
		if(dir < Exit.MINIMUM_DIRECTION || dir > Exit.MAXIMUM_DIRECTION || room == null)
			return;
		
		Room nextRoom = zone.getRoom(Zone.getNeighborLocation(room.getCoordinates(), dir));
		if(nextRoom != null) {
			if(room.getExit(dir) == null) {
				zone.connect(room, dir);
			} else {
				zone.disconnect(room, dir);
			}
		}
		repaint();
	}

	public boolean toggleRoom(Coordinate location) {
		boolean roomCreated = false;
		if(zone.roomExistsAt(location)) {
			Room curRoom = zone.getRoom(location);
			for(int dir = Exit.MINIMUM_DIRECTION; dir <= Exit.MAXIMUM_DIRECTION; dir++) {
				if(curRoom.getExit(dir) != null) {
					zone.disconnect(curRoom, dir);
				}
			}
			zone.removeRoom(location);
			roomCreated = false;
		} else {
			createRoom(location);
			roomCreated = true;
		}
		maxX = zone.getMaxX()+1;
		maxY = zone.getMaxY()+1;
		minX = zone.getMinX();
		minY = zone.getMinY();
		repaint();
		return roomCreated;
	}
	
	public void setCurrentLevel(int level) {
		currentLevel = level;
	}
	
	public void incrementCurrentLevel() {
		currentLevel++;
	}
	
	public void decrementCurrentLevel() {
		currentLevel--;
	}

	public int getCurrentLevel() {
		return currentLevel;
	}
	
	public void setUseColors(boolean useColors) {
		this.useColors = useColors;
	}

}
