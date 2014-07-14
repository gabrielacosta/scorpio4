package com.scorpio4.util.io;
/*
 *   Scorpio4 - Apache Licensed
 *   Copyright (c) 2009-2014 Lee Curtis, All Rights Reserved.
 *
 *
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Scorpio4 (c) Lee Curtis 2012
 * @author lee
 * Date: 15/03/13
 * Time: 12:13 PM
 * <p/>
 * This code does something useful
 */
public class JDBCHelper {

	JDBCHelper() {
	}

	public static Object driver(String driver) {
		try {
			return Class.forName(driver).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();  
		}
		return null;
	}

	public static Connection connect(String url, String user, String passwd) throws SQLException {
		if (user==null) return DriverManager.getConnection(url);
		return DriverManager.getConnection(url, user, passwd);
	}
}
