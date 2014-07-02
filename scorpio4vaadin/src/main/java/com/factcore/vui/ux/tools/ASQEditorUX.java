package com.factcore.vui.ux.tools;
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
import com.factcore.fact.asq.core.ASQ;
import com.factcore.vui.ux.base.BaseUX;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Fact:Core (c) 2013
 * Module: com.factcore.vui.ux.tools
 * User  : lee
 * Date  : 8/02/2014
 * Time  : 10:48 PM
 */
public class ASQEditorUX extends BaseUX {
    private static final Logger log = LoggerFactory.getLogger(ASQEditorUX.class);

    VerticalLayout layout  = new VerticalLayout();
    VerticalLayout asqPatterns = new VerticalLayout();
    HorizontalLayout actionLayout = new HorizontalLayout();
    private Button addButton = new Button();
    private List patterns = new ArrayList();

    public ASQEditorUX() {
        build();
        setContainer(new IndexedContainer());
        bind();
    }

    public void build() {
        layout.removeAllComponents();
        actionLayout.removeAllComponents();
        layout.addComponent(asqPatterns);
        layout.addComponent(actionLayout);

        addButton.setCaption("Add");
        addButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Container container = getContainer();
                String asqID = getIdentity()+"#"+(patterns.size()+1);
                Object itemID = container.addItem( asqID );
                Item item = container.getItem(itemID);
                log.debug("New ASQ Query: "+asqID+" for "+itemID+" --> "+item);
                if (item==null) {
                    Notification.show("ASQ Error: "+asqID);
                } else {
                    build(item);
                }
            }
        });
        actionLayout.addComponent(addButton);
        setComponent(layout);
    }

    public Container setContainer(Container c) {
        return build( super.setContainer(c) );
    }

    protected Container build(Container container) {
        asqPatterns.removeAllComponents();
        patterns.clear();
        for(Object id: container.getItemIds()) {
            Item where = container.getItem(id);
            build(where);
        }
        return container;
    }

    protected ASQPattern build(Item where) {
        ASQPattern asqPattern = new ASQPattern(where);
        patterns.add(asqPattern);
        asqPatterns.addComponent(asqPattern.getComponent());
        return asqPattern;
    }
}

class ASQPattern extends BaseUX {
    ComboBox s = new ComboBox(), p = new ComboBox(), o = new ComboBox();
    HorizontalLayout layout = new HorizontalLayout();

    public ASQPattern(Item where) {
        build(where);
    }

    private void build(Item where) {
        layout.removeAllComponents();
        setComponent(layout);

        layout.addComponent(s);
        layout.addComponent(p);
        layout.addComponent(o);

        build(s, where.getItemProperty(ASQ.THIS));
        build(p, where.getItemProperty(ASQ.HAS));
        build(o, where.getItemProperty(ASQ.THAT));

    }

    private void build(ComboBox s, Property property) {
        s.setValue(property.getValue());
    }

    public void bind() {
        bind((Component) s);
        bind((Component) p);
        bind((Component) o);
    }
}