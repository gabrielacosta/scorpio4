package com.factcore.ui.render.page;
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

import com.factcore.factory.QueryFactory;
import com.factcore.oops.ConfigException;
import com.factcore.oops.KernelException;
import com.factcore.template.PicoTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Fact:Core (c) 2013
 * Module: com.factcore.ui.render.page
 * User  : lee
 * Date  : 3/01/2014
 * Time  : 3:18 PM
 */
public class CoreRenderer implements Renderer {
    QueryFactory pageFactory;
    Map nested = null;
    PicoTemplate template = null;

    public CoreRenderer(QueryFactory pageFactory) {
        this.pageFactory=pageFactory;
    }

    public Renderer newRenderer() {
        return new CoreRenderer(pageFactory);
    }

    public void render(String pageURI, Map model) throws IOException, KernelException {
        String template= pageFactory.query(pageURI, model);
        this.template = new PicoTemplate(template);
        nested = new HashMap();
        nested.putAll(model);
        nested.put("self", newRenderer() );
        render(nested);
    }

    @Override
    public void render(Map model) throws IOException, ConfigException {
        template.translate(nested);
    }

    public String toString() {
        return template.toString();
    }
}
