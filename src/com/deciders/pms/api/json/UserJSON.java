package com.deciders.pms.api.json;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.deciders.pms.api.bean.User;

public class UserJSON {
	
	public static String loginJSON(User user, int status) {
		
		JSONArray array = new JSONArray();
		JSONObject obj = new JSONObject();
		
		try {

			obj.put("status", status);
			obj.put("userId",Integer.parseInt(user.getUserId()));
			obj.put("firstName",user.getFirstName());
			obj.put("lastName",user.getLastName());
			obj.put("email",user.getEmail());
			obj.put("phone",user.getPhone());
			obj.put("type",user.getType());
			
			array.put(obj);
			
		}
		catch(JSONException e) {
			e.printStackTrace();
		}
		
		return array.toString();
		
	}
	
	public static String registerJSON(User user, int status) {

		JSONArray array = new JSONArray();
		JSONObject obj = new JSONObject();
		
		try {

			obj.put("status", status);
			obj.put("userId",Integer.parseInt(user.getUserId()));
			obj.put("firstName",user.getFirstName());
			obj.put("lastName",user.getLastName());
			obj.put("email",user.getEmail());
			obj.put("phone",user.getPhone());
			obj.put("type",user.getType());
			
			array.put(obj);
			
		}
		catch(JSONException e) {
			e.printStackTrace();
		}
		
		return array.toString();
		
	}
	
	public static String changePasswordJSON(User user, int status) {

		JSONArray array = new JSONArray();
		JSONObject obj = new JSONObject();
		
		try {

			obj.put("status", status);
			obj.put("userId",user.getUserId());
			obj.put("firstName",user.getFirstName());
			obj.put("lastName",user.getLastName());
			obj.put("email",user.getEmail());
			obj.put("phone",user.getPhone());
			obj.put("password",user.getPassword());
			obj.put("joinDate",user.getJoinDate());
			obj.put("device",user.getDevice());
			obj.put("type",user.getType());
			
			array.put(obj);
			
		}
		catch(JSONException e) {
			e.printStackTrace();
		}
		
		return array.toString();
		
	}

}
