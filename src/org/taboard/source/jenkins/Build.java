package org.taboard.source.jenkins;

public class Build {
	
	public String status;
	public String message;
	
	public Build(String m) {
		
		if (m.contains("(aborted") || m.contains("(?"))
			this.status = "grey";
		else if (m.contains("(broken"))
			this.status = "red";
		else if (m.contains("(stable"))
			this.status = "blue";
		else 
			this.status = "grey";
		
		this.message = m.substring(0, m.indexOf("("));
	}

}
