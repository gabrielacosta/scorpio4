package com.scorpio4.util;
/*
 *   Scorpio4 - Apache Licensed
 *   Copyright (c) 2009-2014 Lee Curtis, All Rights Reserved.
 *
 *
 */
/**
 * scorpio4 (c) 2013
 * Module: com.scorpio4.util
 * @author lee
 * Date  : 28/10/2013
 * Time  : 12:34 PM
 */
public class IdentityHelper {

    public IdentityHelper() {
    }

    public static String uuid() {
        return uuid("urn:uuid:");
    }

    public static String uuid(String prefix) {
        return (prefix==null?"":prefix)+(java.util.UUID.randomUUID()).toString();
    }

    public static String uuid(String prefix, String localName) {
        return (prefix==null?"":prefix)+(java.util.UUID.nameUUIDFromBytes(localName.getBytes()));
    }

}
