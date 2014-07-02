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

import com.factcore.iq.IQ;
import com.factcore.util.IdentityHelper;
import com.factcore.vendor.vaadin.ui.rich.Grid;
import com.factcore.vui.VUIHelper;
import com.vaadin.data.Item;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.LayoutEvents;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.*;
import org.slf4j.LoggerFactory;
import org.vaadin.dialogs.ConfirmDialog;

import java.net.URI;

/**
 * Fact:Core (c) 2013
 * Module: com.factcore.vui.ux.base
 * User  : lee
 * Date  : 19/01/2014
 * Time  : 8:22 PM
 */
public class MasterDetailUX extends FormUX {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(MasterDetailUX.class);

    HorizontalLayout toolbar = new HorizontalLayout();
    HorizontalLayout masterDetail = new HorizontalLayout();
    Grid grid = new Grid();
    BrowserFrame help = null;

	protected MasterDetailUX() {
	}

    protected MasterDetailUX(IQ iq) {
        init(iq);
    }

    @Override
    public void init(IQ iq) {
        super.init(iq);
        unbind();
        VerticalLayout gridLayout = new VerticalLayout();
        gridLayout.addComponent(getGrid());
        gridLayout.addComponent(buildToolbar());

        masterDetail.removeAllComponents();
	    masterDetail.setMargin(true);
        masterDetail.addComponent(gridLayout);
        masterDetail.addComponent(getForm());
        masterDetail.setSizeFull();

        getGrid().addItemClickListener(new ItemClickEvent.ItemClickListener() {
	        @Override
	        public void itemClick(ItemClickEvent event) {
		        selectDetail(event.getItemId());
	        }
        });

        setComponent(masterDetail);
        setContainer(getGrid().getContainerDataSource());

        grid.setSizeFull();
        grid.setSelectable(true);
        grid.setMultiSelect(false);

        FormLayout form = getForm();
        form.setWidth("100%");
        form.setEnabled(false);
        form.setMargin(true);
	    bind(form);

        help = new BrowserFrame();
        help.setSizeFull();
        masterDetail.addComponent(help);

        final Object that = this;
	    masterDetail.addLayoutClickListener(new LayoutEvents.LayoutClickListener() {
		    @Override
		    public void layoutClick(LayoutEvents.LayoutClickEvent event) {
//			    setHelp("/rest/www/help/"+that.getClass().getSimpleName()+".html");
//			    showHelp();
		    }
	    });
	    super.bind();
	    showHelp();
    }


    private Component buildToolbar() {
        Button add = addButton("+");
        add.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
	            String id = IdentityHelper.uuid(getIdentity());
                getContainer().addItem(id);
                getGrid().setValue(id);
                selectDetail(id);
            }
        });
        Button delete = addButton("-");
	    delete.addClickListener(new Button.ClickListener() {
		    @Override
		    public void buttonClick(Button.ClickEvent event) {
			    final Object id = getGrid().getValue();
                if (id==null) return;
			    final ConfirmDialog confirm = new ConfirmDialog();
			    confirm.setCaption("Are you sure?");
                confirm.addCloseListener(new Window.CloseListener() {
                    @Override
                    public void windowClose(Window.CloseEvent e) {
                        if (confirm.isConfirmed()) {
                            Notification.show("Deleted "+id);
                            getGrid().removeItem(id);
                        }
                    }
                });
		    }
	    });
        return toolbar;
    }

    public Item selectDetail(Object id) {
        VUIHelper.selected(getForm(), getContainer(), id);
	    showForm();
        return getContainer().getItem(id);
    }

    public Button addButton(String label) {
        Button button = new Button(label);
        bind(button);
        toolbar.addComponent(button);
        return button;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid=grid;
        bind();
    }

	public BrowserFrame getHelp() {
		return this.help;
	}

	public BrowserFrame setHelp(String help) {
        URI helpPage = VUIHelper.getURI(getComponent(), help);
        log.debug("Help Page: "+helpPage.toString());
		this.help.setSource(new ExternalResource(helpPage.toString()));
		return getHelp();
	}

    public void setForm(FormLayout form) {
        super.setForm(form);
        form.setEnabled(true);
        bind();
    }

    public Layout getToolBar() {
        return toolbar;
    }

	public void showForm() {
        FormLayout form = getForm();
		form.setVisible(true);
		form.setEnabled(true);
		help.setVisible(!form.isVisible());
	}

	public void showHelp() {
        FormLayout form = getForm();
		form.setVisible(false);
		help.setVisible(!form.isVisible());
	}
}
