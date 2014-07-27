package com.scorpio4.util.map;
/*
 *   Scorpio4 - Apache Licensed
 *   Copyright (c) 2009-2014 Lee Curtis, All Rights Reserved.
 *
 *
 */
import com.scorpio4.util.DateXSD;

import java.io.File;
import java.util.*;

/**
 * Scorpio4 (c) 2010-2013
 * @author lee
 * Date: 22/01/13
 * Time: 7:01 PM
 * <p/>
 * This code does something useful
 */
public class MapUtil {

	public MapUtil() {
	}

	public static boolean getBoolean(Map model, String key, boolean _default) {
		Object value = model.get(key);
		if (value==null) return _default;
		return Boolean.parseBoolean(value.toString());
	}

	public static int getInt(Map model, String key, int _default) {
		Object value = model.get(key);
		if (value==null) return _default;
		return Integer.parseInt(value.toString());
	}

	public static long getLong(Map model, String key, long _default) {
		Object value = model.get(key);
		if (value==null) return _default;
		return Long.parseLong(value.toString());
	}

	public static Date getDate(Map model, String key, Date _default) {
		Object value = model.get(key);
		if (value==null) return _default;
		DateXSD xsd =  new DateXSD();
		return xsd.parse(value.toString());
	}

	public static String getString(Map model, String key, String _default) {
		Object value = model.get(key);
		if (value==null) return _default;
		return value.toString();
	}

	public static String getString(Map model, String key) {
		Object value = model.get(key);
		if (value==null) return null;
		return value.toString();
	}

	public static File getFile(Map model, String key, File _default) {
		Object value = model.get(key);
		if (value==null) return _default;
		return new File(value.toString());
	}

	public static List<String> getKeys(Map map) {
		Set<Object> keys = map.keySet();
		List list = new ArrayList();
		for (Object k : keys) list.add(k);
		return list;
	}

//    public static Trie<String,Map> getTrieByKey(Collection <Map<String,Object>> things) {
//        return getTrieByKey(things, "this");
//    }
//
//    public static Trie<String,Map> getTrieByKey(Collection <Map<String,Object>> things, String key) {
//        Trie models = new PatriciaTrie<String,Object>(new CharSequenceKeyAnalyzer());
//        for(Map thing: things) {
//            models.put(thing.get(key), thing);
//        }
//        return models;
//    }

    public static Map<String,Map> getMapByKey(Collection <Map<String,Object>> things) {
        return getMapByKey(things, "this");
    }

    public static Map<String,Map> getMapByKey(Collection <Map<String,Object>> things, String key) {
        HashMap models = new HashMap();
        for(Map thing: things) {
            if (thing.containsKey(key)) {
                models.put(thing.get(key), thing);
            }
        }
        return models;
    }

//    public static Map<String,Map> getConfig(Collection <Map<String,Object>> things) {
//        return getConfig(things, VOCAB.IQ+"configuration#", "name", "value" );
//    }

    public static Map<String,Map> getConfig(Collection <Map<String,Object>> things, String prefix, String name, String value) {
        HashMap config = new HashMap();
        for(Map thing: things) {
            if (thing.containsKey(name) && thing.containsKey(value)) {
                String id = (String) thing.get(name);
                if (prefix!=null && id.startsWith(prefix)) id = id.substring(prefix.length());
                config.put(id, thing.get(value));
            }
        }
        return config;
    }

	public static Map getConfig(Map props, String prefix) {
		HashMap config = new HashMap();
		for(Object name: props.keySet()) {
			if (name.toString().startsWith(prefix)) {
				String shortName = name.toString().substring(prefix.length());
				config.put(shortName, props.get(name));
			}
		}
		return config;
	}
}
