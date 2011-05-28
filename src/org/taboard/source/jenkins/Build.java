package org.taboard.source.jenkins;

public class Build {
	
	public String status;
	public String message;
	
	public Build(String m) {
		this.message = m;
		if (message.contains("(aborted") || message.contains("(?"))
			status = "grey";
		else if (message.contains("(broken"))
			status = "red";
		else if (message.contains("(stable"))
			status = " blue";
		else 
			status = "grey";
	}

}
