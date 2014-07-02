package com.factcore.vui.ux.factcore;

import com.factcore.QUERY;
import com.factcore.iq.IQ;
import com.factcore.oops.ConfigException;
import com.factcore.oops.FactException;
import com.factcore.vui.ux.base.GridUX;

import java.util.Collection;
import java.util.Map;

/**
 * Fact:Core (c) 2013
 * Module: com.factcore.vui.ux
 * User  : lee
 * Date  : 2/01/2014
 * Time  : 7:46 PM
 */
public class AppsUX extends GridUX {

    public AppsUX(IQ iq) throws ConfigException, FactException {
        Collection<Map<String,Object>> finders = iq.getFactCloud().getCore().list(QUERY.ALL_APPS, iq.getConfiguration());
        init(finders);

        getContainer().addContainerProperty("this", String.class, "");
        getContainer().addContainerProperty("label", String.class, "");
        getContainer().addContainerProperty("id", String.class, "");
        getContainer().addContainerProperty("enabled", Boolean.class, "");
        getContainer().addContainerProperty("core", String.class, "");

    }
}
