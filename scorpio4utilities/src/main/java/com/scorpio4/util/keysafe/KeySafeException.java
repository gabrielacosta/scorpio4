package com.scorpio4.util.keysafe;
/*
 *   Scorpio4 - Apache Licensed
 *   Copyright (c) 2009-2014 Lee Curtis, All Rights Reserved.
 *
 *
 */
/**
 * scorpio4 (c) 2013
 * Module: com.scorpio4.security.keysafe
 * @author lee
 * Date  : 5/11/2013
 * Time  : 12:32 PM
 */
public class KeySafeException extends Exception {

    public KeySafeException(String msg) {
	    super(msg);
    }

    public KeySafeException(String msg, Exception e) {
	    super(msg,e);
    }
}
