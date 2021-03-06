package com.hr;

import java.util.ArrayList;
import java.util.Random;

public class Mines  {
	
	private long resources = 0;
	private long localPop = 0;
	
	private String name;
	
	private City founder;
	
	public Mines(ArrayList<Mines> names, City founder) {
//		super(names);
		this.localPop = 0;
		//tags=ptags;
		this.name = getRandName(names);
		this.founder = founder;
	}
	
	private String getRandName(ArrayList<Mines> anames){ //TODO Possible Infinite Loop
		Random gen = new Random();
		String sname;
		ArrayList<String> names = new ArrayList<String>();
		for(int i = 0; i < anames.size(); i++) {
			names.add(anames.get(i).getName());
		}
		
		do {
			sname = GC.getMineNames()[gen.nextInt(GC.getMineNames().length)];
		} while (names.contains(sname));
		names.add(sname);
		return sname;
	}
	
	public long resGain() {
		resources += localPop;
		return resources;
	}
	
	public long popLoss() {
		localPop -= (localPop*(2.0/100.0));
		return localPop;
	}
	
	public long transfer(City city) {
		long temp = resources;
		resources = 0;
		city.gainRes(temp);
		return temp;
	}
	
	public long popGain(long transfer){
		localPop+=transfer;
		return localPop;
	}
	
	public String getName() {
		return this.name;
	}
	
	public long getLocalPop() {
		return localPop;
	}
	
	public void popEaten(long eaten) {
		localPop -= eaten;
	}
	
	public long transferPop(long loss) {
		if (localPop - loss < 0) {
			loss = localPop;
			localPop = 0;
			return loss;
		}
		
		localPop -= loss;
		return loss;
	}
	
	public City getFounder() {
		return this.founder;
	}
	
	public boolean equals(Object obj){
		if(obj == null)
			return false;
		if(obj == this)
			return true;
		if(!(obj instanceof Mines))
			return false;
		if(((Mines)obj).getName().equals(this.name))
			return true;
		return false;
	}
}
