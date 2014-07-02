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
public class UsersUX extends GridUX {

    public UsersUX(IQ iq) throws ConfigException, FactException {
        Collection<Map<String,Object>> users = iq.getFactCloud().getCore().list(QUERY.ALL_USERS, iq.getConfiguration());
        init(users);

        getContainer().addContainerProperty("this", String.class, "");
        getContainer().addContainerProperty("label", String.class, "");
        getContainer().addContainerProperty("username", String.class, "");
        getContainer().addContainerProperty("canLogin", Boolean.class, "");
    }

}
