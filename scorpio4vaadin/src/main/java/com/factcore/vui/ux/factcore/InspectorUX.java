package com.factcore.vui.ux.factcore;

import com.factcore.fact.model.Focus;
import com.factcore.vui.ux.UX;
import com.factcore.vui.ux.base.BaseUX;
import com.vaadin.data.Item;
import com.vaadin.event.ItemClickEvent;

/**
 * Fact:Core (c) 2013
 * Module: com.factcore.vui.ux
 * User  : lee
 * Date  : 3/01/2014
 * Time  : 8:05 PM
 */
public class InspectorUX extends BaseUX {
    UX master, detail;
    Focus focus = null;

    public InspectorUX(UX master, UX detail) {
        init(master,detail);
    }

    public void init(UX master, UX detail) {
        this.master=master;
        this.detail=detail;
        this.master.getComponent().setVisible(true);
        this.detail.getComponent().setVisible(false);

    }

    @Override
    public void itemClick(ItemClickEvent event) {
        Item item = event.getItem();
        super.itemClick(event);
    }
}
