package com.hr;

public class God {
	static int hunger;

	public static String getMessage() {
		int r = (int) (Math.random()*5);
		if(hunger > 500) {
			// Satiated
			
			if(r==0)
				return "This fine cuisine satiates my palate";
			else if(r==1)
				return "You have satisfied me... for now";
			else if(r==2)
				return "You do well to doom your kind in this manner";
			else if(r==3)
				return "I require no nourishment";
			else if(r==4)
				return "I love the crunch of bones";		
		}
		else if(hunger > 400) {
			//Indifferent
			
			if(r==0)
				return "";
			else if(r==1)
				return "";
			else if(r==2)
				return "";
			else if(r==3)
				return "";
			else if(r==4)
				return "";	
		}
		else if(hunger > 300) {
			//Hungry
			
			if(r==0)
				return "My hunger knows no limits!";
			else if(r==1)
				return "I require nourishment";
			else if(r==2)
				return " ";
			else if(r==3)
				return " ";
			else if(r==4)
				return " ";	
		}
		else if(hunger > 200) {
			//Ravenous
			
			if(r==0)
				return "You shall pay for your incompetence with the screams of you people";
			else if(r==1)
				return "";
			else if(r==2)
				return " ";
			else if(r==3)
				return " ";
			else if(r==4)
				return " ";	
		}
		return "0";
	}
}