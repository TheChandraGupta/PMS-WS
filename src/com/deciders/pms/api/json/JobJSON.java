package com.deciders.pms.api.json;

import java.util.ArrayList;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.deciders.pms.api.bean.Job;

public class JobJSON {
	
	public static String allJobJSON(ArrayList<Job> j, int status) {

		int i = 0;
		
		JSONObject data = new JSONObject();
		JSONArray array = new JSONArray();
		JSONArray dataArray = new JSONArray();
		
		try {

			for(i = 0; i < j.size(); i++) {
				
				JSONObject obj = new JSONObject();
				
				Job job = j.get(i);
				
				obj.put("jobId", job.getJobId());
				obj.put("job", job.getJob());
				obj.put("jobDetail", job.getJobDetail());
				obj.put("basicRate", job.getBasicRate());
				obj.put("dARate", job.getdARate());
				
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
	
	public static String addJobJSON(Job job, int status) {

		JSONArray array = new JSONArray();
		JSONObject obj = new JSONObject();
		
		try {

			obj.put("status", status);
			obj.put("jobId", job.getJobId());
			obj.put("job", job.getJob());
			obj.put("jobDetail", job.getJobDetail());
			obj.put("basicRate", job.getBasicRate());
			obj.put("dARate", job.getdARate());
			
			array.put(obj);
			
		}
		catch(JSONException e) {
			e.printStackTrace();
		}
		
		return array.toString();
		
	}

}
