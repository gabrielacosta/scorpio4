package com.scorpio4.util;
/*
 *   Scorpio4 - Apache Licensed
 *   Copyright (c) 2009-2014 Lee Curtis, All Rights Reserved.
 *
 *
 */
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.IllegalFormatException;

public class DateXSD {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

    public DateXSD () {}

    public DateXSD (TimeZone timeZone)  {
        simpleDateFormat.setTimeZone(timeZone);
    }

    public DateXSD (String format, String timeZone)  {
		this.simpleDateFormat = new SimpleDateFormat(format);
        setTimeZone(timeZone);
    }

    public DateXSD (String format)  {
		this.simpleDateFormat = new SimpleDateFormat(format);
    }

    /**
    *  Parse a xml date string in the format produced by this class only.
    *  This method cannot parse all valid xml date string formats -
    *  so don't try to use it as part of a general xml parser
    */
    public synchronized Date parse(String xmlDateTime)  {
        if ( xmlDateTime.length() != 25 )  return null; // Date not in expected xml datetime format
		try {
	        StringBuilder sb = new StringBuilder(xmlDateTime);
	        sb.deleteCharAt(22);
	        return simpleDateFormat.parse(sb.toString());
		} catch(java.text.ParseException pe) {
			return null;
		}
    }

    public synchronized String format()  {
    	return format(new Date());
    }

    public static synchronized String today()  {
		DateXSD self = new DateXSD();
    	return self.format(new Date());
    }

    public synchronized String format(long now)  {
    	return format(new Date(now));
    }

    public synchronized String format(Date xmlDateTime)  {
    	if (xmlDateTime==null) return null;
    	try {
	        String s =  simpleDateFormat.format(xmlDateTime);
	        StringBuilder sb = new StringBuilder(s);
	        if (sb.length()>22) sb.insert(22, ':');
	        return sb.toString();
    	} catch(IllegalFormatException ife) {
    		return null;
    	}
    }

    public synchronized void setTimeZone(String timezone)  {
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
    }
}
