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
 * User  : lee
 * Date  : 5/11/2013
 * Time  : 12:32 PM
 */
public class KeySafeException extends Exception {
    String msg = null;

    public KeySafeException(String msg) {
        this.msg = msg;
    }

    public KeySafeException(String msg, Exception e) {
        this.msg = msg;
    }
}
