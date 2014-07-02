package com.factcore.vui.vux;
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

import com.factcore.fact.model.Focus;
import com.factcore.fact.source.FactSource;
import com.factcore.fact.stream.FactStreamer;
import com.factcore.iq.IQ;
import com.factcore.oops.ConfigException;
import com.factcore.oops.FactException;
import com.factcore.oops.IQException;
import com.factcore.util.map.MapUtil;
import com.factcore.vendor.vaadin.util.VUIBuilder;
import com.factcore.vui.ux.UX;
import com.factcore.vui.ux.base.BaseUX;
import com.vaadin.ui.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Fact:Core (c) 2013
 * Module: com.factcore.vui.vux
 * User  : lee
 * Date  : 9/02/2014
 * Time  : 12:48 PM
 *
 * Implements a vux:UX
 *
 */
public class VUX {
    private static final Logger log = LoggerFactory.getLogger(VUX.class);

    FactStreamer factStream;
    Map classMap;
    String containsQuery = "urn:factcore:vui:contains";

    public VUX(IQ iq) throws IQException, ConfigException, FactException {
        FactSource core = iq.getFactCloud().getCore();

        // map of URI to Bean's
        String queryClasses = "urn:factcore:vui:componentClasses";
        classMap = MapUtil.getConfig(core.list(queryClasses, iq.getConfiguration() ), null, "this", "classname");
        log.debug("Resolvers: "+classMap);

        factStream = iq.getFactCloud().getFactStream(core.getIdentity());
    }

    public UX getUX(String uri) throws IQException, ConfigException, FactException {
        // build the object graph starting at the uri
        VUIBuilder vuiBuilder = new VUIBuilder();
        vuiBuilder.addClassResolvers(classMap);
        factStream.pull(containsQuery, new Focus(uri), vuiBuilder);

        // get the view
        Component component = vuiBuilder.getComponent(uri);
        if (component==null) return null;
        log.debug("UX view: "+component.getId());
        BaseUX ux = new BaseUX(component);

        // TODO: get the container (model)

        // TODO: logical behaviour (iq)
        ux.bind();

        return ux;
    }


}
