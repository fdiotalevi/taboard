package org.taboard.source.googlecode;


public class Issue {

	public String owner;
	public String authorEmail;
	public String id;
	public String message;
	public String date;
	
	public Issue(String[] nextLine) {		
		this.id = nextLine[0];
		this.owner = nextLine[5];
		this.message = nextLine[6];
	}
	
	


}
