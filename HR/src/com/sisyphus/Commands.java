package com.sisyphus;

import java.util.ArrayList;

public class Commands {	
	
	private static final int PPL = 0;
	private static final int RES = 1;
	
	private static String invalid = "Invalid token ";
	
	/**
	 * COMMAND FORMAT
	 * commands should be accepted regardless of capitalization.
	 * recent commands should be reflected in the text log box
	 * 
	 * HELP
	 * Displays the user manual on the screen. Does NOT pause game.
	 * 		format: "help"
	 * 
	 *
	 * OUIT
	 * Saves data and returns player to main menu. TODO Add multiple saved games.
	 * 		format: "quit"
	 * 
	 * TRANSFER
	 * Switch people or resources between objects.
	 * 		format: "transfer" n ("ppl"/"res") "from" (source_name) "to" (desination_name)
	 * 		ex:
	 * 		transfer 1000 ppl from Dallas to Austin
	 * 			effect: Dallas.localPop is lessened by 1000 (less if Dallas.localPop < 1000) and the
	 * 				    amount of change is added to Austin.localPop
	 * 			before: Dallas.localPop == 650; Austin.localPop == 1350;
	 * 			after:  Dallas.localPop == 0;   Austin.localPop == 2000;
	 * 
	 * FEED HIM
	 * Remove people from objects and change God's hunger status.
	 * 		format: "feed him" n "from" (source_name)
	 * 		ex:
	 * 		feed him 1000000 from OU
	 * 			effect: OU.localPop is lessened by 1000000 (less if OU.localPop < 1000000) and the 
	 * 					God's hunger increases.
	 * 			before: OU.localPop == 2000000; oldHunger = God.hunger;
	 * 			after: OU.localPop == 1000000; oldHunger < God.hunger;
	 */
	 
	public static ArrayList<String> parseCommand(String command){
		ArrayList<String> result = new ArrayList<String>();
		command = command.toLowerCase() + " ";
		int itr = 0;
		int prevItr = 0;
		// Divide command by spaces
		while(itr < command.length()){
			// Ignore white space
			if(command.charAt(prevItr) == ' '){ //" CAT IS SW"
				itr++;
				prevItr++;
			} 
			else if(command.charAt(itr) == ' ' || command.charAt(itr) == '\0'){
				result.add(command.substring(prevItr, itr));
				prevItr = itr;
			}
			else
				itr++;
			
		}
		
		return result;
	}
	
	public static String processCommand(ArrayList<String> commandArray, ArrayList<City> cities, ArrayList<Mines> mines) {
		String expression = null;

		if (commandArray.get(0).toLowerCase().equals("feed") && commandArray.size() == 5) {
			expression = processFeed(commandArray, cities, mines);
		} else if (commandArray.get(0).toLowerCase().equals("transfer") && commandArray.size() == 7) {
			expression = processTransfer(commandArray, cities, mines);
		} else if (commandArray.get(0).toLowerCase().equals("build") && commandArray.size() == 5) {
			expression = processBuild(commandArray, cities, mines);
		} else if (commandArray.get(0).toLowerCase().equals("help") && commandArray.size() == 1) {
			processHelp();
		} else if (commandArray.get(0).toLowerCase().equals("quit")) {
			processQuit();
		} else {
			expression = invalid;
		}
		
		return expression;
	}
	
	/*
	 * 	Takes an ArrayList of Strings called commandArray that should be of the form:
	 * 	"feed him # from city/mine"
	 * 
	 *  where # is the number to feed and city/mine is a mine name
	 */
	private static String processFeed(ArrayList<String> commandArray, ArrayList<City> cities, ArrayList<Mines> mines) {		
		boolean fed = false;
		String fedName = null;
		
		if (!commandArray.get(1).toLowerCase().equals("him")) {
			return invalid + commandArray.get(1);
		}	
		
		if (Integer.parseInt(commandArray.get(2)) < 0) {
			return "Sisyphus cannot consume (-) ppl";
		}		
		int numEaten = Integer.parseInt(commandArray.get(2));
		
		if (!commandArray.get(3).toLowerCase().equals("from")) {
			return invalid + commandArray.get(3);
		}
		
		for (City city : cities) {
			if (commandArray.get(4).toLowerCase().equals(city.getName().toLowerCase())) {
				city.transferPop(numEaten);
				fed = true;
				fedName = city.getName();
			}
		}
		
		for (Mines mine : mines) {
			if (commandArray.get(4).toLowerCase().equals(mine.getName().toLowerCase())) {
				mine.popEaten(numEaten);
				fed = true;
				fedName = mine.getName();
			}
		}
		
		if (!fed) {
			return "The city/mine " + commandArray.get(4) + " does not exist";
		} else {
			return numEaten + " ppl sacrificed from " + fedName;
		}
	}


	private static String processTransfer(ArrayList<String> commandArray, ArrayList<City> cities, ArrayList<Mines> mines) {
		if (Integer.parseInt(commandArray.get(1)) < 0) {
			return "Cannot transfer (-) ppl/res";
		}
		int numTrans = Integer.parseInt(commandArray.get(1));
		
		if (!commandArray.get(2).toLowerCase().equals("ppl") && !commandArray.get(2).toLowerCase().equals("res")) {
			return invalid + commandArray.get(2);
		}
		
		int resourceType = RES;
		
		if (commandArray.get(2).toLowerCase().equals("ppl")) {
			resourceType = PPL;
		}
		
		if (!commandArray.get(3).toLowerCase().equals("from")) {
			return invalid + commandArray.get(3);
		}
		
		City srcCity = null;
		Mines srcMines = null;
		
		for (City city : cities) {
			if (commandArray.get(4).toLowerCase().equals(city.getName().toLowerCase())) {
				srcCity = city;
			}
		}
		
		if (srcCity == null) {
			for (Mines mine : mines) {
				if (commandArray.get(4).toLowerCase().equals(mine.getName().toLowerCase())) {
					srcMines = mine;
				}
			}
		}
		
		if (srcCity == null && srcMines == null) {
			return "The city/mine " + commandArray.get(4) + " does not exist";
		}
		
		if (!commandArray.get(5).toLowerCase().equals("to")) {
			return invalid + commandArray.get(5);
		}
		
		City destCity = null;
		Mines destMines = null;
		
		for (City city : cities) {
			if (commandArray.get(6).toLowerCase().equals(city.getName().toLowerCase())) {
				destCity = city;
			}
		}
		
		if (destCity == null) {
			for (Mines mine : mines) {
				if (commandArray.get(6).toLowerCase().equals(mine.getName().toLowerCase())) {
					destMines = mine;
				}
			}
		}
		
		// PPL transfer
		if (srcCity != null && destCity != null && resourceType == PPL) {
			srcCity.transferPop(numTrans);
			destCity.gainPop(numTrans);
			
			return numTrans + " transferred from " + srcCity.getName() + " to " + destCity.getName();
		}
		if (srcCity != null && destMines != null && resourceType == PPL) {
			srcCity.transferPop(destMines, numTrans);
			
			return numTrans + " transferred from " + srcCity.getName() + " to " + destMines.getName();
		}
		if (srcMines != null && destCity != null && resourceType == PPL) {
			srcMines.transferPop(numTrans);
			destCity.gainPop(numTrans);
			
			return numTrans + " transferred from " + srcMines.getName() + " to " + destCity.getName();
		}
		if (srcMines != null && destMines != null && resourceType == PPL) {
			srcMines.transferPop(numTrans);
			destMines.popGain(numTrans);
			
			return numTrans + " transferred from " + srcMines.getName() + " to " + destMines.getName();
		}
		
		// RES transfer
		if (srcCity != null && destCity != null && resourceType == RES) {
			srcCity.transferRes(numTrans);
			destCity.gainRes(numTrans);
			
			return numTrans + " res transferred from " + srcCity.getName() + " to " + destCity.getName();
		}
		if (srcCity != null && destMines != null && resourceType == RES) {
			return "Invalid command";
		}
		if (srcMines != null && destCity != null && resourceType == RES) {
			return "Invalid command";
		}
		if (srcMines != null && destMines != null && resourceType == RES) {			
			return "Invalid command";
		}
		
		return "Error";
	}
	
	private static String processBuild(ArrayList<String> commandArray, ArrayList<City> cities, ArrayList<Mines> mines) {
		if(!commandArray.get(1).equals("new"))
			return invalid + commandArray.get(1);
		if(!commandArray.get(3).equals("from"))
			return invalid + commandArray.get(3);
		if(!commandArray.get(2).equals("city") && !commandArray.get(2).equals("mine"))
			return "\"" + commandArray.get(2) + "\" is not a buildable type";
		boolean validSource = false;
		City source = null;
		for(int i=0; i<cities.size(); i++)
			if(cities.get(i).getName().toLowerCase().equals(commandArray.get(4))){
				validSource = true;
				source = cities.get(i);
			}
		if(!validSource)
			return commandArray.get(4) + " is not a city";
		if(source.getLocalPop() < GlobalConstants.STARTING_POP / 2)
			return source.getName() + " does not have sufficient population to transfer";
		if(source.getLocalRes() < 2*GlobalConstants.STARTING_RES * 0) //TODO change 2 to modular constant 
			return source.getName() + " does not have sufficient resources to build";
		if(commandArray.get(2).equals("city")){
			City c = new City(cities);
			cities.add(c);
			source.transferPop(GlobalConstants.STARTING_POP);
			source.transferRes(GlobalConstants.STARTING_RES*2);
			return "The people of " + source.getName() + " built a new city, " + c.getName();
		}
		if(commandArray.get(2).equals("mine")){
			Mines m = new Mines(mines);
			mines.add(m);
			return "The people of " + source.getName() + " built a new mine at " + m.getName();
		}
		
		
		return "Error";
	}

	private static void processHelp() {
		Sisyphus.help();
	}

	private static void processQuit() {
		Sisyphus.quit();
	}
}
