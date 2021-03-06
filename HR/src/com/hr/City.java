package com.hr;

import java.util.ArrayList;
import java.util.Random;

public class City {
		
	private long localPop;
	private long localRes;
	private String name;
//	private boolean[] tags = new boolean[GC.NUM_TAGS];
	
	public City(ArrayList<City> names){
		localPop = GC.STARTING_POP;
		localRes = GC.STARTING_RES;
		//tags=ptags;
		name = getRandName(names);
	}
	
	private String getRandName(ArrayList<City> anames) {
		Random gen = new Random();
		String sname;
		ArrayList<String> names = new ArrayList<String>();
		
		for(int i = 0; i < anames.size(); i++) {
			names.add(anames.get(i).getName());
		}
		
		do {
			sname = GC.getCityNames()[gen.nextInt(GC.getCityNames().length)];
		} while (names.contains(sname));
		
		names.add(sname);
		return sname;
	}

	public long grow(long rate, long max) {
		if ((10*localRes-localPop)>=0) {
			upKeep(GC.RATE);
			localPop += (long)(rate*localPop*(1.0-(localPop)/max));
			return localPop;
		}
		else {
			localPop -= localPop*(2.0/100.0);
			return localPop;
		}
	}
	
		
	public long upKeep(long rate){
		long loss = (rate*localPop)/10;
		
		if (localRes - loss < 0) {
			loss = localRes;
			localRes = 0;
			return loss;
		}		
		
		localRes -= loss;
		return loss;
	}
	
	public long transferPop(Mines mine ,long numLoss) {
		if (localPop - numLoss < 0) {
			mine.popGain(localPop);
			localPop = numLoss;
			localPop = 0;
			return numLoss;
		}
		
		mine.popGain(numLoss);
		localPop -= numLoss;
		return numLoss;
	}
	
	public long transferPop(City city,long numLoss){
		city.gainPop(numLoss);
		localPop -= numLoss;
		return localPop;
	}
	
	public void gainPop(long transfer){
		localPop += transfer;
	}
	
	public void gainRes(long transfer){
		localRes += transfer;
	}
	
	public long transferPop(long numLoss){
		localPop -= numLoss;
		return numLoss;
	}
	
	public long transferRes(long numLoss){
		localRes -= numLoss;
		return numLoss;
	}
	
	public long getLocalPop() {
		return this.localPop;
	}
	
	public long getLocalRes() {
		return this.localRes;
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean equals(Object obj){
		if(obj == null)
			return false;
		if(obj == this)
			return true;
		if(!(obj instanceof City))
			return false;
		if(((City)obj).getName().equals(this.name))
			return true;
		return false;
	}
}
