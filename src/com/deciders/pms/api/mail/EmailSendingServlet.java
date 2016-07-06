package com.deciders.pms.api.mail;

public class EmailSendingServlet {
	
	private String host;
	private String port;
	private String user;
	private String pass;
	
	private String recipient;
	private String subject;
	private String content;
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public EmailSendingServlet() {
		this.host = "smtp.gmail.com";
		this.port = "587";
		this.user = "cbcs.cit@gmail.com";
		this.pass = "thedeciders";
	}

	public EmailSendingServlet(String host,String port, String user, String pass) {
		
		// reads SMTP server setting from object initialization
		
		this.host = host;
		this.port = port;
		this.user = user;
		this.pass = pass;
	}

	public void newMail(EmailSendingServlet mail) {
		// reads form fields

		System.out.println(mail.getContent()+"**2**"+mail+"***"+mail.getHost()+""+mail.getPort()+""+mail.getUser()+""+mail.getPass()+""+mail.getRecipient()+""+mail.getSubject());
		
		String resultMessage = "";

		try {
			
			EmailUtility.sendEmail(mail.getHost(), mail.getPort(), mail.getUser(), mail.getPass(), mail.getRecipient(), mail.getSubject(), mail.getContent());
			
			resultMessage = "The e-mail was sent successfully";
			
		}
		catch (Exception ex) {
			
			ex.printStackTrace();
			
			resultMessage = "There were an error: " + ex.getMessage();
			
		}
		finally {
			
			System.out.println(resultMessage);
			
		}
	}

}
