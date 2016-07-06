package com.deciders.pms.api.json;

import java.util.ArrayList;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.deciders.pms.api.bean.LabourJob;
import com.deciders.pms.api.bean.LabourJobDetail;

public class LabourJobJSON {
	
	public static String assignedJobJSON(LabourJob labourJob, int status) {

		JSONArray array = new JSONArray();
		JSONObject data = new JSONObject();
		
		try {

			data.put("status", status);
			data.put("labourJobId", labourJob.getLabourJobId());
			data.put("userId", labourJob.getUserId());
			data.put("jobId", labourJob.getJobId());
			data.put("jobDate", labourJob.getJobDate());
			data.put("remark", labourJob.getRemark());
			
			array.put(data);
			
		}
		catch(JSONException e) {
			e.printStackTrace();
		}
		
		return array.toString();
		
	}
	
	public static String myJobJSON(LabourJobDetail labourJob, int status) {

		JSONArray array = new JSONArray();
		JSONObject data = new JSONObject();
		
		try {

			data.put("status", status);
			data.put("labourJobId", labourJob.getLabourJobId());
			data.put("userId", labourJob.getUserId());
			data.put("firstName", labourJob.getFirstName());
			data.put("lastName", labourJob.getLastName());
			data.put("email", labourJob.getEmail());
			data.put("phone", labourJob.getPhone());
			data.put("type", labourJob.getType());
			data.put("jobId", labourJob.getJobId());
			data.put("job", labourJob.getJob());
			data.put("jobDetail", labourJob.getJobDetail());
			data.put("basicRate", labourJob.getBasicRate());
			data.put("dARate", labourJob.getdARate());
			data.put("jobDate", labourJob.getJobDate());
			data.put("remark", labourJob.getRemark());
			
			array.put(data);
			
		}
		catch(JSONException e) {
			e.printStackTrace();
		}
		
		return array.toString();
		
	}
	
	public static String allLabourJobJSON(ArrayList<LabourJobDetail> ljd, int status) {

		JSONArray array = new JSONArray();
		JSONArray objArray = new JSONArray();
		JSONObject obj = new JSONObject();
		
		try {
			
			for(int i = 0; i < ljd.size(); i++) {

				JSONObject data = new JSONObject();
				
				LabourJobDetail labourJob = ljd.get(i);
				
				data.put("labourJobId", labourJob.getLabourJobId());
				data.put("userId", labourJob.getUserId());
				data.put("firstName", labourJob.getFirstName());
				data.put("lastName", labourJob.getLastName());
				data.put("email", labourJob.getEmail());
				data.put("phone", labourJob.getPhone());
				data.put("type", labourJob.getType());
				data.put("jobId", labourJob.getJobId());
				data.put("job", labourJob.getJob());
				data.put("jobDetail", labourJob.getJobDetail());
				data.put("basicRate", labourJob.getBasicRate());
				data.put("dARate", labourJob.getdARate());
				data.put("jobDate", labourJob.getJobDate());
				data.put("remark", labourJob.getRemark());
				
				array.put(data);
				
			}

			obj.put("status", status);
			obj.put("array", array);
			objArray.put(obj);
			
		}
		catch(JSONException e) {
			e.printStackTrace();
		}
		
		return objArray.toString();
		
	}

}
