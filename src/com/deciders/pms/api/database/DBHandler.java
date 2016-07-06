package com.deciders.pms.api.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import com.deciders.pms.api.bean.Job;
import com.deciders.pms.api.bean.LabourJob;
import com.deciders.pms.api.bean.LabourJobDetail;
import com.deciders.pms.api.bean.User;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;

public class DBHandler {
	
	public static User loginData(String email, String password, DBManager dbManager) {

		System.out.println("loginData");
		
		User user = new User();
		
		try {
			
			CallableStatement pstm = dbManager.getCon().prepareCall("call login(?,?,?,?,?,?,?,?)");
			
			pstm.registerOutParameter(1, Types.VARCHAR);
			pstm.registerOutParameter(2, Types.VARCHAR);
			pstm.setString(3, email);
			pstm.setString(4, password);
			pstm.registerOutParameter(5, Types.VARCHAR);
			pstm.registerOutParameter(6, Types.VARCHAR);
			pstm.registerOutParameter(7, Types.VARCHAR);
			pstm.registerOutParameter(8, Types.INTEGER);
			
			pstm.executeQuery();

			user.setFirstName(pstm.getString(1));
			user.setLastName(pstm.getString(2));
			user.setEmail(email);
			user.setPassword(password);
			user.setPhone(pstm.getString(5));
			user.setDevice(pstm.getString(6));
			user.setType(pstm.getString(7));
			user.setUserId(String.valueOf(pstm.getInt(8)));
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return user;
		
	}

	public static User registerData(User user, DBManager dbManager) {

		System.out.println("registerData");
		
		try {
			
			CallableStatement pstm1 = dbManager.getCon().prepareCall("call findlabour(?,?)");
			
			pstm1.setString(1, user.getEmail());
			pstm1.registerOutParameter(2, Types.INTEGER);

			pstm1.executeQuery();
			
			if(pstm1.getInt(2)>0) {
				user.setUserId(String.valueOf(0));
			}
			else {
				
				CallableStatement pstm = dbManager.getCon().prepareCall("call register(?,?,?,?,?,?,?,?)");
				
				pstm.setString(1, user.getFirstName());
				pstm.setString(2, user.getLastName());
				pstm.setString(3, user.getEmail());
				pstm.setString(4, user.getPassword());
				pstm.setString(5, user.getPhone());
				pstm.setString(6, user.getDevice());
				pstm.setString(7, user.getType());
				pstm.registerOutParameter(8, Types.INTEGER);
				
				pstm.executeQuery();

				user.setUserId(String.valueOf(pstm.getInt(8)));
				
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return user;
		
	}

	public static User forgetData(String email, DBManager dbManager) {

		System.out.println("forgetData");
		
		User user = new User();
		
		try {
			
			String query = "SELECT * FROM user WHERE email='"+email+"'";
			
			ResultSet rs = dbManager.getSt().executeQuery(query);
			
			while(rs.next()) {
				
				user.setFirstName(rs.getString("firstName"));
				user.setLastName(rs.getString("lastName"));
				user.setEmail(email);
				user.setPassword(rs.getString("password"));
				user.setPhone(rs.getString("phone"));
				user.setDevice(rs.getString("device"));
				user.setType(rs.getString("type"));
				user.setUserId(String.valueOf(rs.getInt("userId")));
				
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return user;
		
	}

	public static boolean changePasswordData(String userId, String oldPassword, String newPassword, DBManager dbManager) {

		System.out.println("changePasswordData");
		
		boolean flag = false;
		
		try {
			
			String query = "SELECT * FROM user WHERE userId="+userId+" AND password=PASSWORD("+oldPassword+")";
			
			ResultSet rs = dbManager.getSt().executeQuery(query);
			
			while(rs.next()) {
				
				flag = true;
				
				query = "UPDATE user SET password=PASSWORD("+newPassword+") WHERE userId="+userId;
				
				dbManager.getSt().executeUpdate(query);
				
			}
			
			rs.close();
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return flag;
		
	}
	
	public static ArrayList<LabourJobDetail> allLaborerJobsData(DBManager dbManager) {

		System.out.println("allLaborerJobsData");
		
		ArrayList<LabourJobDetail> ljd = new ArrayList<LabourJobDetail>();
		
		int i = 0;
		
		try {
			
			String query = "SELECT * FROM labourjobview WHERE jobDate=DATE_FORMAT(NOW(),'%d %b %y') ORDER BY labourJobId desc";
			
			ResultSet rs = dbManager.getSt().executeQuery(query);
			
			while(rs.next()) {
				
				LabourJobDetail labourJobDetail = new LabourJobDetail();
				
				labourJobDetail.setLabourJobId(String.valueOf(rs.getInt("labourJobId")));
				labourJobDetail.setUserId(String.valueOf(rs.getInt("userId")));
				labourJobDetail.setFirstName(rs.getString("firstName"));
				labourJobDetail.setLastName(rs.getString("lastName"));
				labourJobDetail.setEmail(rs.getString("email"));
				labourJobDetail.setPhone(rs.getString("phone"));
				labourJobDetail.setDevice(rs.getString("device"));
				labourJobDetail.setType(rs.getString("type"));
				labourJobDetail.setJobId(String.valueOf(rs.getInt("jobId")));
				labourJobDetail.setJob(rs.getString("job"));
				labourJobDetail.setJobDetail(rs.getString("jobDetail"));
				labourJobDetail.setBasicRate(String.valueOf(rs.getInt("basicRate")));
				labourJobDetail.setdARate(String.valueOf(rs.getInt("dARate")));
				labourJobDetail.setJobDate(rs.getString("jobDate"));
				labourJobDetail.setRemark(rs.getString("remark"));
				
				ljd.add(i, labourJobDetail);
				
				i++;
				
			}
			
			rs.close();
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return ljd;
		
	}

	public static LabourJobDetail myJobsData(String userId, DBManager dbManager) {

		System.out.println("myJobsData");
		
		LabourJobDetail labourJobDetail = new LabourJobDetail();
		
		try {
			
			String query = "SELECT * FROM labourjobview WHERE userId="+userId+" AND jobDate=DATE_FORMAT(NOW(),'%d %b %y')";
			
			ResultSet rs = dbManager.getSt().executeQuery(query);
			
			while(rs.next()) {
				
				labourJobDetail.setLabourJobId(String.valueOf(rs.getInt("labourJobId")));
				labourJobDetail.setUserId(String.valueOf(rs.getInt("userId")));
				labourJobDetail.setFirstName(rs.getString("firstName"));
				labourJobDetail.setLastName(rs.getString("lastName"));
				labourJobDetail.setEmail(rs.getString("email"));
				labourJobDetail.setPhone(rs.getString("phone"));
				labourJobDetail.setDevice(rs.getString("device"));
				labourJobDetail.setType(rs.getString("type"));
				labourJobDetail.setJobId(String.valueOf(rs.getInt("jobId")));
				labourJobDetail.setJob(rs.getString("job"));
				labourJobDetail.setJobDetail(rs.getString("jobDetail"));
				labourJobDetail.setBasicRate(String.valueOf(rs.getInt("basicRate")));
				labourJobDetail.setdARate(String.valueOf(rs.getInt("dARate")));
				labourJobDetail.setJobDate(rs.getString("jobDate"));
				labourJobDetail.setRemark(rs.getString("remark"));
								
			}
			
			rs.close();
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return labourJobDetail;
		
	}

	public static Job addJobData(Job job, DBManager dbManager) {

		System.out.println("addJobData");
		
		try {
			
			CallableStatement pstm = dbManager.getCon().prepareCall("call addnewjob(?,?,?,?,?)");
			
			pstm.setString(1, job.getJob());
			pstm.setString(2, job.getJobDetail());
			pstm.setFloat(3, Float.parseFloat(job.getBasicRate()));
			pstm.setFloat(4, Float.parseFloat(job.getdARate()));
			pstm.registerOutParameter(5, Types.INTEGER);
			
			pstm.executeQuery();

			job.setJobId(String.valueOf(pstm.getInt(5)));
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return job;
		
	}
	
	public static ArrayList<Job> allJobsData(DBManager dbManager) {

		System.out.println("allJobsData");
		
		ArrayList<Job> j = new ArrayList<Job>();
		
		int i = 0;
		
		try {
			
			String query = "SELECT * FROM job ORDER BY job.job";
			
			ResultSet rs = dbManager.getSt().executeQuery(query);
			
			while(rs.next()) {
				
				Job job = new Job();
				
				job.setJobId(String.valueOf(rs.getInt("jobId")));
				job.setJob(rs.getString("job"));
				job.setJobDetail(rs.getString("jobDetail"));
				job.setBasicRate(String.valueOf(rs.getFloat("basicRate")));
				job.setdARate(String.valueOf(rs.getFloat("dARate")));
				
				j.add(i, job);

				i++;
				
			}
			
			rs.close();
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return j;
		
	}
	
	public static ArrayList<User> allLaborersData(String jobDate, DBManager dbManager) {

		System.out.println("allLaborersData");
		
		ArrayList<User> u = new ArrayList<User>();
		
		int i = 0;
		
		try {
			
			String query = "SELECT * FROM labourjobview WHERE checklabourschedule(userId, "+jobDate+")=0 GROUP BY userId";
			
			ResultSet rs = dbManager.getSt().executeQuery(query);
			
			while(rs.next()) {
				
				User user = new User();
				
				user.setUserId(String.valueOf(rs.getInt("userId")));
				user.setFirstName(rs.getString("firstName"));
				user.setLastName(rs.getString("lastName"));
				user.setEmail(rs.getString("email"));
				user.setPhone(rs.getString("phone"));
				user.setDevice(rs.getString("device"));
				user.setType(rs.getString("type"));
				
				u.add(i, user);
				
				i++;
				
			}
			
			rs.close();
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return u;
		
	}
	
	public static LabourJob assignLabourJobData(LabourJob labourJob, DBManager dbManager) {

		System.out.println("assignLabourJobData");
		
		try {
			
			CallableStatement pstm = dbManager.getCon().prepareCall("call assignlabourjob(?,?,?,?)");
			
			pstm.setInt(1, Integer.parseInt(labourJob.getUserId()));
			pstm.setInt(2, Integer.parseInt(labourJob.getJobId()));
			pstm.setInt(3, Integer.parseInt(labourJob.getJobDate()));
			pstm.registerOutParameter(4, Types.INTEGER);
			
			pstm.executeQuery();
			
			labourJob.setLabourJobId(String.valueOf(pstm.getInt(4)));
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return labourJob;
		
	}
	
	public static LabourJob jobRemarkData(LabourJob labourJob, DBManager dbManager) {

		System.out.println("jobRemarkData");
		
		try {
			
			CallableStatement pstm = dbManager.getCon().prepareCall("call labourjobremark(?,?)");
			
			pstm.setInt(1, Integer.parseInt(labourJob.getLabourJobId()));
			pstm.setString(2, labourJob.getRemark());
			
			pstm.executeQuery();
						
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return labourJob;
		
	}

	public static boolean paymentData(String userId, String amount, DBManager dbManager) {

		System.out.println("paymentData");
		
		boolean flag = false;
		
		try {
			
			CallableStatement pstm = dbManager.getCon().prepareCall("call laboursalarypayment(?,?)");
			
			pstm.setInt(1, Integer.parseInt(userId));
			pstm.setFloat(2, Float.parseFloat(amount));
			
			pstm.executeQuery();
			
			flag = true;
						
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return flag;
		
	}
	
	public static ArrayList<LabourJobDetail> myWeeklyJobsData(String userId, DBManager dbManager) {

		System.out.println("myWeeklyJobsData");
		
		ArrayList<LabourJobDetail> ljd = new ArrayList<LabourJobDetail>();
		
		int i = 0;
		
		try {
			
			String query = "SELECT *, DATE_FORMAT(jobDate, '%d %b %y') AS jobDate2 FROM labourjobview2 WHERE userId="+userId+" AND jobDate<=DATE_ADD(NOW(), INTERVAL 7 DAY) AND jobDate>=DATE_SUB(NOW(), INTERVAL 7 DAY) ORDER BY labourJobId desc";
			
			ResultSet rs = dbManager.getSt().executeQuery(query);
			
			while(rs.next()) {
				
				LabourJobDetail labourJobDetail = new LabourJobDetail();
				
				labourJobDetail.setLabourJobId(String.valueOf(rs.getInt("labourJobId")));
				labourJobDetail.setUserId(String.valueOf(rs.getInt("userId")));
				labourJobDetail.setFirstName(rs.getString("firstName"));
				labourJobDetail.setLastName(rs.getString("lastName"));
				labourJobDetail.setEmail(rs.getString("email"));
				labourJobDetail.setPhone(rs.getString("phone"));
				labourJobDetail.setDevice(rs.getString("device"));
				labourJobDetail.setType(rs.getString("type"));
				labourJobDetail.setJobId(String.valueOf(rs.getInt("jobId")));
				labourJobDetail.setJob(rs.getString("job"));
				labourJobDetail.setJobDetail(rs.getString("jobDetail"));
				labourJobDetail.setBasicRate(String.valueOf(rs.getInt("basicRate")));
				labourJobDetail.setdARate(String.valueOf(rs.getInt("dARate")));
				labourJobDetail.setJobDate(rs.getString("jobDate2"));
				labourJobDetail.setRemark(rs.getString("remark"));
				
				ljd.add(i, labourJobDetail);
				
				i++;
								
			}
			
			rs.close();
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return ljd;
		
	}

}
