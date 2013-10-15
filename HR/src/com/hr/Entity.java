package com.sisyphus;

import java.util.ArrayList;

public class Entity {
	
	protected long localPop;
	protected long localRes;
	protected String name;	
	
	public Entity (ArrayList<String> names) {
		this.name = getRandomName(names);
	}

	private String getRandomName(ArrayList<String> names) {

		return null;
	}
	
	
}
