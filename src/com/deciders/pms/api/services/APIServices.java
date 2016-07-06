package com.deciders.pms.api.services;

import java.util.ArrayList;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.deciders.pms.api.bean.Job;
import com.deciders.pms.api.bean.LabourJob;
import com.deciders.pms.api.bean.LabourJobDetail;
import com.deciders.pms.api.bean.User;
import com.deciders.pms.api.constant.Constant;
import com.deciders.pms.api.database.DBHandler;
import com.deciders.pms.api.database.DBManager;
import com.deciders.pms.api.json.AutoUserJSON;
import com.deciders.pms.api.json.JobJSON;
import com.deciders.pms.api.json.LabourJSON;
import com.deciders.pms.api.json.LabourJobJSON;
import com.deciders.pms.api.json.UserJSON;
import com.deciders.pms.api.mail.EmailSendingServlet;
import com.deciders.pms.api.mail.MailThread;

@Path("api")
public class APIServices {
	
	@GET
	@Path("test_api")
	public Response apiTest() {
		System.out.println("apiTest");
		return Response.ok("API Connection Successful").header("Access-Control-Allow-Origin", "*").build();
	}
	
	@POST
	@Consumes(Constant.REST_RESPONSE_TYPE)
	@Path("add_random_users")
	public Response addRandomUsers(
			@FormParam("usersJSON") String usersJSON) {
		System.out.println("addRandomUsers");
		
		String send = "Added All Users";
		
		//usersJSON = "";
		
		AutoUserJSON.parseUsersJSON(usersJSON);
		
		return Response.ok(send).header("Access-Control-Allow-Origin", "*").build();
	}
	
	
	@POST
	@Consumes(Constant.REST_RESPONSE_TYPE)
	@Path("login")
	@Produces(MediaType.APPLICATION_JSON)
	//@Produces(Constant.REST_RESPONSE_TYPE)
	//@Consumes(Constant.REST_REQUEST_TYPE)
	public Response userLogin(
			@FormParam("email") String email,
			@FormParam("password") String password) {

		System.out.println("userLogin");
		
		String send = "";
		
		User user = null;
		DBManager dbManager = new DBManager();
		
		user = DBHandler.loginData(email, password, dbManager);
		
		if(user == null || user.getUserId()==null || Integer.parseInt(user.getUserId())==0) {
			user = new User();
			user.setUserId(String.valueOf(0));
			send = UserJSON.loginJSON(user, 203);
		}
		else {
			send = UserJSON.loginJSON(user, 200);
		}
		
		dbManager.close();
		
		return Response.ok(send).header("Access-Control-Allow-Origin", "*").build();
		
	}
	
	@POST
	@Consumes(Constant.REST_RESPONSE_TYPE)
	@Path("register")
	@Produces(MediaType.APPLICATION_JSON)
	//@Produces(Constant.REST_RESPONSE_TYPE)
	//@Consumes(Constant.REST_REQUEST_TYPE)
	public Response userRegister(
			@FormParam("firstName") String firstName,
			@FormParam("lastName") String lastName,
			@FormParam("email") String email,
			@FormParam("password") String password,
			@FormParam("phone") String phone,
			@FormParam("joinDate") String joinDate,
			@FormParam("device") String device) {

		System.out.println("userRegister");
		
		String send = "";

		User user = new User(firstName, lastName, email, phone, password, device);
		DBManager dbManager = new DBManager();
		
		user = DBHandler.registerData(user, dbManager);
		
		if(user == null || user.getUserId()==null || Integer.parseInt(user.getUserId())==0) {
			user = new User();
			user.setUserId(String.valueOf(0));
			send = UserJSON.registerJSON(user, 203);
		}
		else {
			send = UserJSON.registerJSON(user, 200);
		}

		dbManager.close();
		
		return Response.ok(send).header("Access-Control-Allow-Origin", "*").build();
		
	}
	
	@POST
	@Consumes(Constant.REST_RESPONSE_TYPE)
	@Path("forget")
	@Produces(MediaType.APPLICATION_JSON)
	//@Produces(Constant.REST_RESPONSE_TYPE)
	//@Consumes(Constant.REST_REQUEST_TYPE)
	public Response userForget(
			@FormParam("email") String email) {

		System.out.println("userForget");
		
		String send = "";

		EmailSendingServlet mail =new EmailSendingServlet();
		
		DBManager dbManager = new DBManager();
		
		User user = DBHandler.forgetData(email, dbManager);
		
		if(user == null || user.getUserId()==null || Integer.parseInt(user.getUserId())==0) {
			
			user = new User();
			user.setUserId(String.valueOf(0));
			
			send = UserJSON.registerJSON(user, 203);
			
		}
		else {
			
			send = UserJSON.registerJSON(user, 200);
			
			String recipient = email;
			String subject = "Password Recovered";
			String content = "Dear "+ user.getFirstName() +",\n\nYour password has been recovered\nEmail : "+email+"\nPassword : "+user.getPassword();
			
			mail.setRecipient(recipient);
			mail.setSubject(subject);
			mail.setContent(content);
			
			MailThread sendMail = new MailThread(mail);
			sendMail.start();
		}
		
		dbManager.close();
		
		return Response.ok(send).header("Access-Control-Allow-Origin", "*").build();
		
	}
	
	@POST
	@Consumes(Constant.REST_RESPONSE_TYPE)
	@Path("change")
	@Produces(MediaType.APPLICATION_JSON)
	//@Produces(Constant.REST_RESPONSE_TYPE)
	//@Consumes(Constant.REST_REQUEST_TYPE)
	public Response userChangePassword(
			@FormParam("userId") String userId,
			@FormParam("oldPassword") String oldPassword,
			@FormParam("newPassword") String newPassword) {

		System.out.println("userChangePassword");
		
		String send = "";
		
		User user = new User();

		DBManager dbManager = new DBManager();
		
		if(DBHandler.changePasswordData(userId, oldPassword, newPassword, dbManager)) {
			user.setUserId(userId);
			user.setPassword(newPassword);
			send = UserJSON.changePasswordJSON(user, 200);
		}
		else {
			user.setUserId(String.valueOf(0));
			send = UserJSON.changePasswordJSON(user, 203);
		}
		
		dbManager.close();
		
		return Response.ok(send).header("Access-Control-Allow-Origin", "*").build();
		
	}
	
	@POST
	@Path("alllaborerjobs")
	@Produces(MediaType.APPLICATION_JSON)
	//@Produces(Constant.REST_RESPONSE_TYPE)
	//@Consumes(Constant.REST_REQUEST_TYPE)
	public Response allLaborerJobs() {

		System.out.println("allLaborerJobs");
		
		String send = "";
		
		DBManager dbManager = new DBManager();
		
		ArrayList<LabourJobDetail> ljd = DBHandler.allLaborerJobsData(dbManager);
		
		if(ljd.size() > 0) {

			send = LabourJobJSON.allLabourJobJSON(ljd, 200);
			
		}
		else {

			send = LabourJobJSON.allLabourJobJSON(ljd, 203);
			
		}
		
		dbManager.close();
		
		return Response.ok(send).header("Access-Control-Allow-Origin", "*").build();
		
	}
	
	@POST
	@Consumes(Constant.REST_RESPONSE_TYPE)
	@Path("myjob")
	@Produces(MediaType.APPLICATION_JSON)
	//@Produces(Constant.REST_RESPONSE_TYPE)
	//@Consumes(Constant.REST_REQUEST_TYPE)
	public Response myJob(
			@FormParam("userId") String userId) {

		System.out.println("myJob");
		
		String send = "";

		DBManager dbManager = new DBManager();
		
		LabourJobDetail labourJobDetail = DBHandler.myJobsData(userId, dbManager);
		
		if(labourJobDetail == null || labourJobDetail.getUserId()==null || Integer.parseInt(labourJobDetail.getUserId())==0) {

			send = LabourJobJSON.myJobJSON(labourJobDetail, 203);
			
		}
		else {

			send = LabourJobJSON.myJobJSON(labourJobDetail, 200);
			
		}
		
		dbManager.close();
		
		return Response.ok(send).header("Access-Control-Allow-Origin", "*").build();
		
	}
	
	@POST
	@Consumes(Constant.REST_RESPONSE_TYPE)
	@Path("myweeklyjob")
	@Produces(MediaType.APPLICATION_JSON)
	//@Produces(Constant.REST_RESPONSE_TYPE)
	//@Consumes(Constant.REST_REQUEST_TYPE)
	public Response myWeeklyJob(
			@FormParam("userId") String userId) {

		System.out.println("myWeeklyJob");
		
		String send = "";

		DBManager dbManager = new DBManager();
		
		ArrayList<LabourJobDetail> ljd = DBHandler.myWeeklyJobsData(userId, dbManager);
		
		if(ljd.size() > 0) {

			send = LabourJobJSON.allLabourJobJSON(ljd, 200);
			
		}
		else {

			send = LabourJobJSON.allLabourJobJSON(ljd, 203);
			
		}
		
		dbManager.close();
		
		return Response.ok(send).header("Access-Control-Allow-Origin", "*").build();
		
	}
	
	@POST
	@Consumes(Constant.REST_RESPONSE_TYPE)
	@Path("addlaborerjob")
	@Produces(MediaType.APPLICATION_JSON)
	//@Produces(Constant.REST_RESPONSE_TYPE)
	//@Consumes(Constant.REST_REQUEST_TYPE)
	public Response addLaborerJob(
			@FormParam("userId") String userId,
			@FormParam("jobId") String jobId,
			@FormParam("jobDate") String jobDate) {

		System.out.println("addLaborerJob");
		
		String send = "";

		DBManager dbManager = new DBManager();
		
		LabourJob labourJob = new LabourJob(userId, jobId, jobDate);
		
		labourJob = DBHandler.assignLabourJobData(labourJob, dbManager);
		
		if(labourJob == null || labourJob.getLabourJobId()==null || Integer.parseInt(labourJob.getLabourJobId())==0) {

			send = LabourJobJSON.assignedJobJSON(labourJob, 203);
			
		}
		else {

			send = LabourJobJSON.assignedJobJSON(labourJob, 200);
			
		}
		
		dbManager.close();
		
		return Response.ok(send).header("Access-Control-Allow-Origin", "*").build();
		
	}
	
	@POST
	@Consumes(Constant.REST_RESPONSE_TYPE)
	@Path("addjob")
	@Produces(MediaType.APPLICATION_JSON)
	//@Produces(Constant.REST_RESPONSE_TYPE)
	//@Consumes(Constant.REST_REQUEST_TYPE)
	public Response addJob(
			@FormParam("job") String job,
			@FormParam("jobDetail") String jobDetail,
			@FormParam("basicRate") String basicRate,
			@FormParam("dARate") String dARate) {

		System.out.println("addJob");
		
		String send = "";

		DBManager dbManager = new DBManager();
		
		Job j = new Job("" + 0, job, jobDetail, basicRate, dARate);
		
		j = DBHandler.addJobData(j, dbManager);
		
		if(j.getJobId().equals("0") || j==null || j.getJobId()==null) {

			send = JobJSON.addJobJSON(j, 203);
			
		}
		else {

			send = JobJSON.addJobJSON(j, 200);
			
		}
		
		dbManager.close();
		
		return Response.ok(send).header("Access-Control-Allow-Origin", "*").build();
		
	}
	
	@POST
	@Consumes(Constant.REST_RESPONSE_TYPE)
	@Path("alllaborer")
	@Produces(MediaType.APPLICATION_JSON)
	//@Produces(Constant.REST_RESPONSE_TYPE)
	//@Consumes(Constant.REST_REQUEST_TYPE)
	public Response allLaborer(
			@FormParam("jobDate") String jobDate) {

		System.out.println("allLaborer");
		
		String send = "";

		DBManager dbManager = new DBManager();
		
		ArrayList<User> u = DBHandler.allLaborersData(jobDate, dbManager);
				
		if(u.size() > 0) {

			send = LabourJSON.allLaborerJSON(u, 200);
			
		}
		else {

			send = LabourJSON.allLaborerJSON(u, 203);
			
		}
		
		dbManager.close();
		
		return Response.ok(send).header("Access-Control-Allow-Origin", "*").build();
		
	}
	
	@POST
	@Consumes(Constant.REST_RESPONSE_TYPE)
	@Path("alljobs")
	@Produces(MediaType.APPLICATION_JSON)
	//@Produces(Constant.REST_RESPONSE_TYPE)
	//@Consumes(Constant.REST_REQUEST_TYPE)
	public Response allJobs() {

		System.out.println("allJobs");
		
		String send = "";

		DBManager dbManager = new DBManager();
		
		ArrayList<Job> j = DBHandler.allJobsData(dbManager);
		
		if(j.size() > 0) {

			send = JobJSON.allJobJSON(j, 200);
			
		}
		else {

			send = JobJSON.allJobJSON(j, 203);
			
		}
		
		dbManager.close();
		
		return Response.ok(send).header("Access-Control-Allow-Origin", "*").build();
		
	}
	
	@POST
	@Consumes(Constant.REST_RESPONSE_TYPE)
	@Path("jobremark")
	@Produces(MediaType.APPLICATION_JSON)
	//@Produces(Constant.REST_RESPONSE_TYPE)
	//@Consumes(Constant.REST_REQUEST_TYPE)
	public Response jobRemark(
			@FormParam("labourJobId") String labourJobId,
			@FormParam("userId") String userId,
			@FormParam("jobId") String jobId,
			@FormParam("remark") String remark) {

		System.out.println("jobRemark");
		
		String send = "";

		DBManager dbManager = new DBManager();
		
		LabourJob labourJob = new LabourJob(labourJobId, userId, jobId, "0", remark);
		
		labourJob = DBHandler.jobRemarkData(labourJob, dbManager);
		
		if(labourJob == null || labourJob.getLabourJobId()==null || Integer.parseInt(labourJob.getLabourJobId())==0) {

			send = LabourJobJSON.assignedJobJSON(labourJob, 203);
			
		}
		else {

			send = LabourJobJSON.assignedJobJSON(labourJob, 200);
			
		}
		
		dbManager.close();
		
		return Response.ok(send).header("Access-Control-Allow-Origin", "*").build();
		
	}
	
	@POST
	@Consumes(Constant.REST_RESPONSE_TYPE)
	@Path("payment")
	@Produces(MediaType.APPLICATION_JSON)
	//@Produces(Constant.REST_RESPONSE_TYPE)
	//@Consumes(Constant.REST_REQUEST_TYPE)
	public Response payment(
			@FormParam("userId") String userId,
			@FormParam("amount") String amount) {

		System.out.println("payment");
		
		String send = "";

		DBManager dbManager = new DBManager();
		
		if(DBHandler.paymentData(userId, amount, dbManager)) {
			
			LabourJobJSON.assignedJobJSON(new LabourJob(), 200);
			
		}
		else {

			LabourJobJSON.assignedJobJSON(new LabourJob(), 203);
			
		}
		
		dbManager.close();
		
		return Response.ok(send).header("Access-Control-Allow-Origin", "*").build();
		
	}

}
