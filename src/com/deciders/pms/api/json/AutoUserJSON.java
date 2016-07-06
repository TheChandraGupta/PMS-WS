package com.deciders.pms.api.json;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.deciders.pms.api.bean.User;
import com.deciders.pms.api.database.DBHandler;
import com.deciders.pms.api.database.DBManager;

public class AutoUserJSON {
	
	public static void parseUsersJSON(String json) {
		
		try {
			
			JSONObject jsonObject = new JSONObject(json);
			
			User user = new User();

			DBManager dbManager = new DBManager();
			
			JSONArray jsonArray = jsonObject.getJSONArray("results");
			
			int i;
			
			for(i = 0; i < jsonArray.length(); i++) {
				
				JSONObject innerObject = jsonArray.getJSONObject(i);
				
				JSONObject name = innerObject.getJSONObject("name");
				user.setFirstName(name.getString("first"));
				user.setLastName(name.getString("last"));
				
				user.setEmail(innerObject.getString("email"));
				
				user.setPhone(innerObject.getString("cell"));
				
				JSONObject login = innerObject.getJSONObject("login");
				user.setPassword(login.getString("password"));
				user.setDevice(login.getString("sha1"));
				
				DBHandler.registerData(user, dbManager);
				
			}
			
			dbManager.close();
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }

}
