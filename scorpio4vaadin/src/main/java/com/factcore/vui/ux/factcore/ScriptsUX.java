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
 * Time  : 7:51 PM
 */
public class ScriptsUX extends GridUX {

    public ScriptsUX(IQ iq) throws ConfigException, FactException {
        Collection<Map<String,Object>> scripts = iq.getFactCloud().getCore().list(QUERY.ALL_SCRIPTS, iq.getConfiguration());
        init(scripts);

        getContainer().addContainerProperty("this", String.class, "");
        getContainer().addContainerProperty("label", String.class, "");
    }
}
