package com.deciders.pms.api.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.deciders.pms.api.constant.Constant;

public class DBManager {
	
	private Connection con;
	private Statement st;
	
	public DBManager() {
		
		try {
			Class.forName(Constant.DRIVER);
			con = DriverManager.getConnection(Constant.URL, Constant.USER, Constant.PASS);
			st = con.createStatement();
		}
		catch(ClassNotFoundException e) {
			con = null;
			st = null;
			e.printStackTrace();
		}
		catch(SQLException e) {
			con = null;
			st = null;
			e.printStackTrace();
		}
		
	}
	
	public Connection getCon() {
		return con;
	}

	public Statement getSt() {
		return st;
	}
	
	public void closeCon() {
		
		try {
			con.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void closeSt() {
		
		try {
			st.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
	}

	public void close() {
	
	try {
		con.close();
		st.close();
	}
	catch(SQLException e) {
		e.printStackTrace();
	}
	
}
	
}
