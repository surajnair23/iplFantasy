package com.ipl.validator;

import java.util.Set;
import java.util.TreeSet;
public class TeamValidator{
	
	public String validateForm(String abv,String name,String pName[],String tPlayer[],String captain,String wk,String homecity) {
		String result = null;
		Set<String> playerSet = new TreeSet<String>();
		if(captain == null) {return "Captain is requried";}
		if(wk == null) {return "Wicketkeeper is requried";}
		
		if(abv == null) {return "Team abbrevation is requried";}
		if(name == null) {return "Team name is requried";}
		
		if(pName == null) {
			return "Please enter player names";
		}else{
			for(String p:pName) {
				if(p.equals("")) {
					return "Please enter all player names";
				}else {
					if(playerSet.isEmpty()) {playerSet.add(p); continue;}
					if(playerSet.contains(p)) {return "Player names should be unique";}
					else {playerSet.add(p);}
				}
			}
		}
		
		if(homecity == null)
		{ return "Home city is requried";}
		if(tPlayer != null) {
			int cbowler=0,cbats=0,callr=0;
			for(String tp:tPlayer) {
				if(tp.contains("man")) { cbats++;}
				if(tp.contains("owl")) { cbowler++;}
				if(tp.contains("round")) { callr++;}
			}
			if(cbowler<3) {return "You need to have atleast 3 bowlers!";}
			if(cbats<3) {return "You need to have atleast 3 batsman!";}
			if(callr<3) {return "You need to have atleast 3 allrounders!";}
		}
		return result;
	}
	
	public String formValidate(String team1,String team2,String city) {
		
		if(team1==null){return "Team 1 is required!";}
		if(team2==null){return "Team 2 is required!";}
		if(team1.equalsIgnoreCase(team2)) {
			return "Matches can only happen between 2 different teams!";
		}
		if(city==null) {return "Venue is required!";}
		
		return null;
	}
	
	public String selectionValidate(String playerIds[], String winner) {
		if(winner == null) {
			return "Winner selection is required!";
		}
		System.out.println("Validation in progress");
		if(playerIds == null) {
			return "Selecting players is mandatory";
			}else { 
			if(playerIds.length != 5) { 
				return "You cant only select 5 players!";
				}
			}
		return null;
	}
}
