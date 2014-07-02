package com.factcore.vui;
/*
 *   Fact:Core - CONFIDENTIAL
 *   Unpublished Copyright (c) 2009-2014 Lee Curtis, All Rights Reserved.
 *
 *   NOTICE:  All information contained herein is, and remains the property of Lee Curtis. The intellectual and technical concepts contained
 *   herein are proprietary to Lee Curtis and may be covered by Australian, U.S. and Foreign Patents, patents in process, and are protected by trade secret or copyright law.
 *   Dissemination of this information or reproduction of this material is strictly forbidden unless prior written permission is obtained
 *   from Lee Curtis.  Access to the source code contained herein is hereby forbidden to anyone except current Lee Curtis employees, managers or contractors who have executed
 *   Confidentiality and Non-disclosure agreements explicitly covering such access.
 *
 *   The copyright notice above does not evidence any actual or intended publication or disclosure  of  this source code, which includes
 *   information that is confidential and/or proprietary, and is a trade secret, of Lee Curtis.   ANY REPRODUCTION, MODIFICATION, DISTRIBUTION, PUBLIC  PERFORMANCE,
 *   OR PUBLIC DISPLAY OF OR THROUGH USE  OF THIS  SOURCE CODE  WITHOUT  THE EXPRESS WRITTEN CONSENT OF LEE CURTIS IS STRICTLY PROHIBITED, AND IN VIOLATION OF APPLICABLE
 *   LAWS AND INTERNATIONAL TREATIES.  THE RECEIPT OR POSSESSION OF  THIS SOURCE CODE AND/OR RELATED INFORMATION DOES NOT CONVEY OR IMPLY ANY RIGHTS
 *   TO REPRODUCE, DISCLOSE OR DISTRIBUTE ITS CONTENTS, OR TO MANUFACTURE, USE, OR SELL ANYTHING THAT IT  MAY DESCRIBE, IN WHOLE OR IN PART.
 *

 */

import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.server.Page;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.HasComponents;
import com.vaadin.ui.UI;
import org.apache.jackrabbit.spi.commons.name.HashCache;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Fact:Core (c) 2013
 * Module: com.factcore.vui
 * User  : lee
 * Date  : 2/03/2014
 * Time  : 10:27 PM
 */
public class VUIHelper {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(VUIHelper.class);

    public VUIHelper() {
    }

    /* *
     * bind matching Fields to properties from in Item
     * */
    public static void selected(Iterable<Component> fields, Container container, Object itemId) {
        for(Component c: fields) {
            if (c instanceof Field) {
                Field field = (Field)c;
                String id = field.getId();
                if (id!=null) {
                    Property property = container.getContainerProperty(itemId, id);
                    if (property!=null) {
                        field.setPropertyDataSource(property);
                    } else {
                        log.warn("Property not bound: "+id);
                    }
                }
            }
        }
    }

    /* *
     *  Display a human-readable label of a Component
     * */
	public static String toString(Object bean) {
        if (bean==null) return "vui:anon:NULL";
        if (!Component.class.isInstance(bean)) {
            return bean.toString();
        }
        Component c = (Component)bean;
		if (c.getCaption()!=null) return c.getCaption();
		if (c.getId()!=null) return c.getId();
		return "vui:anon:"+c.toString();
	}

	public static URI getURI(Component c, String path) {
		if (c==null) return null;
        UI ui = c.getUI();
        if (ui==null) return null;
        Page page = ui.getPage();
        URI l = page.getLocation();
        try {
            return new URI(l.getScheme(), l.getUserInfo(), l.getHost(), l.getPort(), path, null, null);
        } catch (URISyntaxException e) {
            return null;
        }
	}

    public static void dump(Component c) {
        dump(0, c);
    }

    public static void dump(int depth, Component c) {
        StringBuilder str$ = new StringBuilder();
        for(int i=0;i<depth;i++) str$.append("\t");
        log.debug(str$+"> "+toString(c)+" : "+c.getClass().getCanonicalName());
        if (HasComponents.class.isInstance(c)) {
            log.debug("\t-- ");
            HasComponents parent = (HasComponents)c;
            for(Component cc: parent) {
                dump(depth+1, cc);
            }
        }
    }
}
