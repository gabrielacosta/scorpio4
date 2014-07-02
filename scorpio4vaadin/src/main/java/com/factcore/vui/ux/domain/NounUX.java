package com.factcore.vui.ux.domain;
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

import com.factcore.iq.IQ;
import com.factcore.vendor.vaadin.ui.rich.Grid;
import com.factcore.vui.ux.base.BaseUX;
import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.server.Resource;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 * Fact:Core (c) 2013
 * Module: com.factcore.vui.ux.domain
 * User  : lee
 * Date  : 19/01/2014
 * Time  : 6:53 PM
 */
public class NounUX extends BaseUX {
    VerticalLayout layout = new VerticalLayout();
    FormLayout form = new FormLayout();
    TextField label = new TextField("Label");
    TextArea description = new TextArea("Description");
    Resource icon = null;
    Grid literals = new Grid();

    public NounUX(IQ iq) {
        init(iq, form);
    }

    public void init(IQ iq, FormLayout form) {
        buildForm();
        buildLiterals();

        layout.addComponent(this.form);
        layout.addComponent(literals);

        setComponent(layout);
        bind();
    }

    private void buildLiterals() {
        bind((Container) literals);
        bind(literals.getAsComponent());

        literals.setSizeFull();
        literals.setFilterBarVisible(true);

        literals.setCaption("Literals");
        literals.addContainerProperty("this", String.class, "");
        literals.addContainerProperty("label", String.class, "");
        literals.addContainerProperty("type", String.class, "");
        literals.addContainerProperty("default", String.class, "");
        literals.addContainerProperty("format", String.class, "");
        literals.addContainerProperty("editor", String.class, "");
        literals.addContainerProperty("validator", String.class, "");

        literals.setVisibleColumns("label","type", "default");
    }

    private void buildForm() {
        label.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                form.setCaption(event.getProperty().getValue().toString());
            }
        });
        form.addComponent(label);
        label.setColumns(40);
        bind(label);

        description.setRows(5);
        description.setColumns(80);
        form.addComponent(description);
        bind(description);
    }
}
