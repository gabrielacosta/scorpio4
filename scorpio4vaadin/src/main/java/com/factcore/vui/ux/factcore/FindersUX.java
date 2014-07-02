package com.factcore.vui.ux.factcore;

import com.factcore.QUERY;
import com.factcore.iq.IQ;
import com.factcore.oops.ConfigException;
import com.factcore.oops.FactException;
import com.factcore.vui.ux.base.GridUX;
import com.vaadin.data.Container;

import java.util.Collection;
import java.util.Map;

/**
 * Fact:Core (c) 2013
 * Module: com.factcore.vui.ux
 * User  : lee
 * Date  : 2/01/2014
 * Time  : 7:39 PM
 */
public class FindersUX extends GridUX {

    public FindersUX(IQ iq) throws ConfigException, FactException {
        Collection<Map<String,Object>> finders = iq.getFactCloud().getCore().list(QUERY.ALL_FINDERS, iq.getConfiguration());
        init(finders);

        Container table = getContainer();
        table.addContainerProperty("this", String.class, "");
        table.addContainerProperty("label", String.class, "");
        table.addContainerProperty("url", String.class, "");
        table.addContainerProperty("classname", String.class, "");
        table.addContainerProperty("username", String.class, "");
        table.addContainerProperty("password", String.class, "");

    }
}
