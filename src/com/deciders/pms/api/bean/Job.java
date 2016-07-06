package com.deciders.pms.api.bean;

public class Job {
	
	private String jobId;
	private String job;
	private String jobDetail;
	private String basicRate;
	private String dARate;
	
	public String getJob() {
		return this.job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getJobDetail() {
		return this.jobDetail;
	}
	public void setJobDetail(String jobDetail) {
		this.jobDetail = jobDetail;
	}
	public String getBasicRate() {
		return this.basicRate;
	}
	public void setBasicRate(String basicRate) {
		this.basicRate = basicRate;
	}
	public String getdARate() {
		return this.dARate;
	}
	public void setdARate(String dARate) {
		this.dARate = dARate;
	}
	
	
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public Job() {
		
	}
	
	public Job(String jobId, String job, String jobDetail, String basicRate, String dARate) {
		
		this.jobId = jobId;
		this.job = job;
		this.jobDetail = jobDetail;
		this.basicRate = basicRate;
		this.dARate = dARate;
				
	}

}
