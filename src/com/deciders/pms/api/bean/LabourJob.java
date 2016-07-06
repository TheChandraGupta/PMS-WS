package com.deciders.pms.api.bean;

public class LabourJob {
	
	private String labourJobId;
	private String userId;
	private String jobId;
	private String jobDate;
	private String remark = "INCOMPLETE";
	
	public LabourJob() {
		super();
	}
	
	public LabourJob(String userId, String jobId, String jobDate) {
		super();
		this.userId = userId;
		this.jobId = jobId;
		this.jobDate = jobDate;
	}

	public LabourJob(String labourJobId, String userId, String jobId, String jobDate, String remark) {
		super();
		this.labourJobId = labourJobId;
		this.userId = userId;
		this.jobId = jobId;
		this.jobDate = jobDate;
		this.remark = remark;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getJobDate() {
		return jobDate;
	}

	public void setJobDate(String jobDate) {
		this.jobDate = jobDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLabourJobId() {
		return labourJobId;
	}

	public void setLabourJobId(String labourJobId) {
		this.labourJobId = labourJobId;
	}
	

}
