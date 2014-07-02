package com.factcore.vui.ux.base;
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

import com.factcore.vui.ux.UX;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;

import java.util.HashMap;
import java.util.Map;

/**
 * Fact:Core (c) 2013
 * Module: com.factcore.vendor.vaadin.ux
 * User  : lee
 * Date  : 2/01/2014
 * Time  : 5:04 PM
 */
public class TabUX extends BaseUX {
    Map<String,UX> uxes = new HashMap();

    public TabUX() {
		init();
    }

    public void init() {
        TabSheet tabSheet = new TabSheet();
        tabSheet.setSizeFull();
        setComponent(tabSheet);
        bind();
    }

    public TabSheet getTabSheet() {
        return (TabSheet) getComponent();
    }

    public void addTab(Component component, String caption) {
        getTabSheet().addTab(component, caption);
    }

    public void addUX(UX ux, String caption) {
        addTab(ux,caption);
    }

    public void addTab(UX ux, String caption) {
        uxes.put(ux.getIdentity(),ux);

        TabSheet.Tab tab = getTabSheet().addTab(ux.getComponent(), caption);
        tab.setClosable(false);

        // wire events
        ux.addUXListener(this);
        bind(ux.getComponent());
        bind(ux.getContainer());
    }

    public UX getUX(String uri) {
        return uxes.get(uri);
    }
}
