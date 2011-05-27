package org.taboard.fragments.git;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class Commit {

	public String authorName;
	public String authorEmail;
	public String id;
	public String message;
	public String date;
	
	public Commit(JSONObject object) {		
		this.id = getString(object, "id");
		this.message = getString(object, "message");
		this.date = getString(object, "committed_date");
		
	}
	
	private String getString(JSONObject obj, String key) {
		try {
			return obj.getString(key);
						
		} catch (JSONException e) {
			
			return null;
		}
	}
	


}
