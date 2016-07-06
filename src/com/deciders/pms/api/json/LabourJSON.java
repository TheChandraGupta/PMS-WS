package com.deciders.pms.api.json;

import java.util.ArrayList;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.deciders.pms.api.bean.User;

public class LabourJSON {
	
	public static String allLaborerJSON(ArrayList<User> u, int status) {

		int i = 0;
		
		JSONObject data = new JSONObject();
		JSONArray array = new JSONArray();
		JSONArray dataArray = new JSONArray();
		
		try {

			for(i = 0; i < u.size(); i++) {
				
				JSONObject obj = new JSONObject();
				
				User user = u.get(i);
				
				obj.put("userId",user.getUserId());
				obj.put("firstName",user.getFirstName());
				obj.put("lastName",user.getLastName());
				obj.put("email",user.getEmail());
				obj.put("phone",user.getPhone());
				obj.put("type",user.getType());
				
				array.put(obj);
				
			}

			data.put("status", status);
			data.put("array", array);
			dataArray.put(data);
			
		}
		catch(JSONException e) {
			e.printStackTrace();
		}
		
		return dataArray.toString();
		
	}

}
