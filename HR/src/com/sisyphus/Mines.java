package com.sisyphus;

import java.util.ArrayList;
import java.util.Random;

public class Mines extends Entity {
	
	private static String[] mineNames = {"Core-Prime", "Polaris", "Eclipse-Crater", "Parallax", "Roche-Point",
		 "Redshift", "Zenith-East", "Radius", "Mt-Xerxes", "Dezornn",
		 "Axelis-Re", "Remmur", "Ilb", "West-Crave", "Bellmore-Surface",
		 "Bellmore-Deep", "The-Vore", "Elysium", "The-Howling", "Ziz-Cumulus"};
	
	private long resources = 0;
	private long localPop = 0;
	
	private String name;
	
	public Mines(ArrayList<String> names) {
		super(names);
		this.localPop = 0;
	}
	
	private String getRandName(ArrayList<String> anames){ //TODO Possible Infinite Loop
		Random gen = new Random();
		String sname;
		ArrayList<String> names = new ArrayList<String>();
		for(int i = 0; i < anames.size(); i++)
			names.add(anames.get(i)); 
		do{
			sname = mineNames[gen.nextInt(mineNames.length)];
		}while(names.contains(sname));
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
