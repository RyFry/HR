/*
 * 
 *	GLOBAL CONSTANTS
 *
 *	This holds all of the assets and resources (images and sounds)
 *
 *	This is also where the game based constants are, such as rate of decay,
 *	default population for all cities and the maximum population a city
 *	can have before capping off 
 * 
 */

package com.hr;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class GC {
	public static final int NUM_TAGS=0;
	
	
	//	Algorithm constants
	public static final long STARTING_POP = 1000,
							 STARTING_RES = 1000,
							 RATE = 1,
							 MAX = 10000;
	
	private static String[] mineNames = {"Core-Prime", "Polaris", 
		"Eclipse-Crater", "Parallax", "Roche-Point", "Redshift", 
		"Zenith-East", "Radius", "Mt-Xerxes", "Dezornn", "Axelis-Re", 
		"Remmur", "Ilb", "West-Crave", "Bellmore-Surface", "Bellmore-Deep", 
		"The-Vore", "Elysium", "The-Howling", "Ziz-Cumulus"};
	
	private static String[] cityNames = {"Sisyphus", "Ouroborus", "Fugue", 
		"Chi-T", "Centurion", "Stargazer", "Aether", "Cralilea", "Hubble", 
		"Nouus", "Pulsar", "Cepheid", "T-Tauri", "Nebulon", "Kepler"};
	
	/*
	 * 	These are Sound and Image assets 
	 */	
	private static Sound BGM;
	private static Sound NewGameStart;
	private static Sound ButtonPress;
	
	private static Image mainMenu;
	private static Image sisyphus;
	private static Image optionsMenu;
	private static Image instructions;
	
	
	
	
	
	//	initializes all of the assets (images) and resources (sounds)
	public static void init () {
		try {
			BGM = new Sound("res/mainTheme.ogg");
			NewGameStart = new Sound("res/NewGameStart.ogg");
			ButtonPress = new Sound("res/onClick.ogg");
			
			mainMenu = new Image("assets/MainMenu.jpg");
			sisyphus = new Image("assets/Sisyphus.jpg");
			optionsMenu = new Image("assets/OptionsMenu.jpg");
			instructions = new Image("assets/Instructions.jpg");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	/*
	 * 	A crapload of get methods
	 * 
	 */

	public static String[] getCityNames() {
		return cityNames;
	}

	public static String[] getMineNames() {
		return mineNames;
	}

	public static Sound getBGM() {
		return BGM;
	}

	public static Image getInstructions() {
		return instructions;
	}

	public static Image getMainMenu() {
		return mainMenu;
	}

	public static Image getSisyphus() {
		return sisyphus;
	}

	public static Sound getNewGameStart() {
		return NewGameStart;
	}

	public static Sound getButtonPress() {
		return ButtonPress;
	}

	public static Image getOptionsMenu() {
		return optionsMenu;
	}
}
