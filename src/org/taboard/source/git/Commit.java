package org.taboard.source.git;

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
		this.authorEmail = getObjectString(object, "author", "email");
		
	}
	
	private String getObjectString(JSONObject object, String objectKey,
			String fieldKey) {
		try {
			return object.getJSONObject(objectKey).getString(fieldKey);
						
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	private String getString(JSONObject obj, String key) {
		try {
			return obj.getString(key);
						
		} catch (JSONException e) {
			
			return null;
		}
	}
	


}
