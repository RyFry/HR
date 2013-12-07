package com.hr;

import java.util.ArrayList;
import org.newdawn.slick.*;

public class HR extends BasicGame {

	private boolean musicOn = true;
	private boolean toggleCursor = true;

	private static final String TITLE = "H.R.";
	private static int WIDTH = 1280;
	private static int HEIGHT = 600;

	private static AppGameContainer app;

	private ArrayList<City> cities;
	private ArrayList<Mines> mines;
	private ArrayList<String> logs;

	private String commandBuff = "";

	private int cumTime = 0;
	private int cityListY = 7;
	private int mineListY = 7;

	private static Frames frame = Frames.MAIN;

	public HR() {
		super(TITLE);
	}

	public static void main(String [] args) throws SlickException {
		try {
			app = new AppGameContainer(new HR());
			app.setDisplayMode(WIDTH, HEIGHT, false);
			app.setShowFPS(false);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	// Make sound work again
	public void init(GameContainer gc) throws SlickException {		
		logs = new ArrayList<String>();

		cities = new ArrayList<City>();
		cities.add(new City(cities));

		mines = new ArrayList<Mines>();
		mines.add(new Mines(mines, null));

		System.out.println(cities.toString());

		GC.init();
	}

	private void reinit() {
		cities.clear();
		cities.add(new City(cities));
		mines.clear();		
		mines.add(new Mines(mines, cities.get(0)));
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {		
		if(frame == Frames.MAIN){
			mainRender(gc, g);
		}
		if(frame == Frames.NEWGAME){
			newGameRender(gc, g);
		}
		if(frame == Frames.CREDITS){
			creditsRender(gc, g);
		}
		if(frame == Frames.OPTIONS){
			optionsRender(gc, g);
		}
		if(frame == Frames.HELP) {
			helpRender(gc, g);
		}
	}

	public void update(GameContainer gamec, int delta) throws SlickException {		

		if (frame == Frames.NEWGAME) {
			cumTime += delta;
			if (cumTime >= 2000) {
				for (City city : cities) {
					city.upKeep(GC.RATE);
					city.grow(GC.RATE, GC.MAX);
				}

				for (Mines mine : mines) {
					mine.popLoss();
					mine.resGain();
					mine.transfer(mine.getFounder());
				}

				cumTime = 0;
			}

			if ((cumTime > 1000 && toggleCursor) || (cumTime < 1000 && !toggleCursor)) {
				toggleCursor = !toggleCursor;
			}
		}

		if(musicOn == true){
			if(frame == Frames.NEWGAME) {

			}else if (GC.getBGM().playing() == false) {
				GC.getBGM().loop();
			}
		}else if (musicOn == false){
			GC.getBGM().stop();
		}
	}	

	public void helpRender(GameContainer gc, Graphics g){
		GC.getInstructions().draw(0, 
				0, 
				GC.getInstructions().getWidth() * .8f, 
				GC.getInstructions().getHeight() * .5f);
		String helpScreen = "--This is where the short backstory will go--\nblah blah blah mor story";
		String commands = "help: \n     HELP Displays the user manual on the screen. Does NOT pause game.\n"
				+"quit: \n     QUIT Saves data and returns player to main menu.\n"
				+"transfer (# of people/resources) from (source name) to (desination name):\n     TRANSFER Switch people or resources between locations.\n"
				+"feed him (# of people from (source name):\n     FEED HIM people from cities and change God's hunger status.\n"
				+"build new city/mine from (source city name):\n     BUILDS a new city or mine using ppl from the source city.\n"+
				"     If the built entity is a mine, the resources from that mine\n     will transfer to the city it was built from.\n";
		g.setColor(Color.white);
		g.drawString(helpScreen, 300, 200);
		g.drawString(commands, 300, 260);
	}

	public void mainRender(GameContainer gamec, Graphics g){
		GC.getMainMenu().draw(0, 0, GC.getMainMenu().getWidth() * .6667f, 
				GC.getMainMenu().getHeight() * .5555f);
	}

	public void newGameRender(GameContainer gamec, Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		g.setColor(Color.white);

		GC.getSisyphus().draw(0, 0, GC.getSisyphus().getWidth() * .6667f, 
				GC.getSisyphus().getHeight() * 0.5555f);

		if (toggleCursor) {
			g.drawString(">> " + commandBuff + "|", 325, 575);
		} else {
			g.drawString(">> " + commandBuff, 325, 575);
		}

		for (int i=0; i < cities.size(); i++) {
			g.drawString(cities.get(i).getName() + "\n   population: " + cities.get(i).getLocalPop()+ 
					"\n   rescources: " + cities.get(i).getLocalRes(),10, (i*60)+cityListY);
		}

		for (int i=0; i < mines.size(); i++) {
			g.drawString(mines.get(i).getName() + "\n   population: " + 
					mines.get(i).getLocalPop(), 1100, (i*40)+mineListY);
		}

		if(logs.size() >= 1) {
			int count = 0;
			for (int i=logs.size()-1; i >= 0; i--){
				if(count < 4) {
					g.drawString(logs.get(i), 325, 570-((logs.size()-i)*20));
					count++;
				}
			}
		}

		g.drawRect(215, 10, 30, 30);
		g.drawRect(215, 375, 30, 30);
		g.drawRect(1035, 10, 30, 30);
		g.drawRect(1035, 375, 30, 30);
	}

	public void creditsRender(GameContainer gamec, Graphics g) {
		GC.getMainMenu().draw(0, 0, 
				GC.getMainMenu().getWidth() * .6667f, 
				GC.getMainMenu().getHeight() * .5555f);
		String credits = "Team Lead: Ryan\n\nArt:Jeffrey\n\nAudio: Avi\n\nCoder: Barrett\n\nCoder: Prad";
		GC.getMainMenu().draw(0, 0, 
				GC.getMainMenu().getWidth() * .6667f, 
				GC.getMainMenu().getHeight() * .5555f);
		g.setColor(Color.black);
		g.fillRect(0,0, WIDTH, HEIGHT);
		g.setColor(Color.white);
		g.drawString(credits, WIDTH/2-credits.length(), HEIGHT/2-credits.length());
	}

	public void optionsRender(GameContainer gc,Graphics g) {
		GC.getOptionsMenu().draw(0, 0,
				GC.getOptionsMenu().getWidth() * .6667f, 
				GC.getOptionsMenu().getHeight() * .5555f);
	}

	public void mouseClicked(int button, int x, int y, int clickCount) {
		if (frame == Frames.MAIN) {
			//	check to see where the player clicked the mouse
			
			if(x <= 540 && x >= 280 && y <= 560 && y >= 425) {
				//	Go to instructions menu
				frame = Frames.HELP;
				GC.getBGM().stop();
				reinit();
				GC.getNewGameStart().play();
			} else if (x <= 1015 && x >= 730 && y <= 560 && y >= 425) {
				// Start a new game
				frame = Frames.NEWGAME;
				GC.getBGM().stop();
				GC.getNewGameStart().play();
			} else if (x <= 420 && x >= 220 && y <= 300 && y >= 260) {
				//	Go to the options menu
				frame = Frames.OPTIONS;
				GC.getButtonPress().play();
			} else if (x <= 1115 && x >= 855 && y <= 300 && y >= 260) {
				//	Go to the credits menu
				frame = Frames.CREDITS;
				GC.getButtonPress().play();
			}	
		} else if (frame == Frames.OPTIONS) {
			if(x >= 230 && x <= 300 && y >= 255 && y <= 295) {
				//	while in the options menu, this code turns the music
				//	ON 
				musicOn = true;
				GC.getButtonPress().play();
			} else if (x >= 350 && x <= 430 && y >= 250 && y <= 300) {
				//	while in the options menu, this code turns the music
				//	OFF 
				musicOn = false;
				GC.getButtonPress().play();
			} else if (x >= 870 && x <= 1050 && y >= 200 && y <= 300) {
				//	TODO: THIS CODE WILL CHANGE TO FULL SCREEN MODE				
				GC.getButtonPress().play();
			}
		} else if (frame == Frames.NEWGAME) {
			//	for scrolling through mine and city lists while in game
			
			//	scrolls through cities
			if(x >= 215 && x <= 245 && y >= 10 && y <= 40  && cityListY <= 305){
				cityListY += 60;
			}else if(x >= 215 && x <= 245 && y >= 375 && y <= 405){
				cityListY -= 60;
				
			//	scrolls through mines
			}else if(x >= 1035 && x <= 1065 && y >= 10 && y <= 40 && mineListY <= 365){
				mineListY += 40;
			}else if(x >= 1035 && x <= 1065 && y >= 375 && y <= 405){
				mineListY -= 40;
			}
		} else if (frame == Frames.CREDITS) {
			//	TODO: Honestly not sure this should be here. This is for the
			//	credits menu
		}
	}

	public void keyPressed(int key, char c) {
		/*
		 * HELP Menu
		 */
		if (frame == Frames.HELP) {
			if(key == Input.KEY_ENTER) {
				frame = Frames.NEWGAME;
				GC.getNewGameStart().play();
				return;
			}
		}

		if (key == Input.KEY_ESCAPE) {
			app.exit();
		}
		/*
		 * GAME is being played
		 */
		if (frame == Frames.NEWGAME) {
			//	allows every printable key to be pressed
			if (c >= ' ' && c <= '~') {
				commandBuff += c;
			}

			if (key == Input.KEY_ENTER && commandBuff.length() > 0) {
				String command = commandBuff;	

				ArrayList<String> commandSequence = Commands.parseCommand(command);
				logs.add(Commands.processCommand(commandSequence, cities, mines));

				commandBuff = "";
			}

			if (key == Input.KEY_BACK && !commandBuff.equals("")) {
				commandBuff = commandBuff.substring(0, commandBuff.length() - 1);
			}
		}
		/*
		 * OPTIONS Menu
		 */
		if(frame == Frames.OPTIONS || frame == Frames.CREDITS){
			if (key == Input.KEY_BACK) {
				frame = Frames.MAIN;
				GC.getButtonPress().play();
			}
		}
	}
	
	/**
	 * Quits the game, and returns to the main menu
	 */
	public static void quit() {
		frame = Frames.MAIN;
	}

	/**
	 * Goes to the help menu
	 */
	public static void help() {
		frame = Frames.HELP;
	}
}
