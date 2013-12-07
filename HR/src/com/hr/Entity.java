package com.hr;

import java.util.ArrayList;

public abstract class Entity {
	protected long localPop;
	protected String name;
	
	public Entity (ArrayList<String> names) {
		this.name = getRandomName(names);
	}

	private String getRandomName(ArrayList<String> names) { return null; }	
}
