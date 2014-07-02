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

import com.factcore.QUERY;
import com.factcore.fact.model.Focus;
import com.factcore.iq.Capability;
import com.factcore.iq.IQ;
import com.factcore.oops.ConfigException;
import com.factcore.oops.FactException;
import com.factcore.oops.IQException;
import com.factcore.vendor.vaadin.model.ContainerStream;
import com.factcore.vui.VUIHelper;
import com.factcore.vui.ux.UX;
import com.factcore.vui.event.UXEvent;
import com.factcore.vui.event.UXListener;
import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.MouseEvents;
import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.TargetDetails;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.PaintException;
import com.vaadin.server.PaintTarget;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.peter.contextmenu.ContextMenu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Module: com.factcore.vendor.vaadin.mvp
 *
 * User  : lee
 * Date  : 29/11/2013
 * Time  : 8:13 PM
 *
 */

// we're fundamentally a Vaadin -> VUI event router
public class BaseUX implements UX, Capability, View, UXListener,
		DropHandler,
		Container.ItemSetChangeListener, Property.ValueChangeListener,
		HasComponents.ComponentAttachListener, Button.ClickListener, MouseEvents.ClickListener,
		ItemClickEvent.ItemClickListener, TabSheet.SelectedTabChangeListener,
		Table.HeaderClickListener, Table.FooterClickListener,
		Tree.ExpandListener, Tree.CollapseListener
{

    private static final Logger log = LoggerFactory.getLogger(BaseUX.class);
    private String identity = "bean:"+getClass().getCanonicalName();

    Collection<UXListener> uxListeners = new ArrayList();

    private Component view = null;
    private Container model = null;
    private IQ iq;

    public static final String MODEL_CHANGED = "urn:factcore:vui:model:changed";
    public static final String VALUE_CHANGED = "urn:factcore:vui:model:value:changed";

    protected BaseUX() {
	}

    public BaseUX(Component view) {
        setComponent(view);
    }

    public BaseUX(Component view, Container model) {
        setComponent(view);
        setContainer(model);
        bind();
    }

    public BaseUX(Component view, Collection<Map<String, Object>> models) {
        this.view = view;
        this.model = new IndexedContainer();
        new ContainerStream( this.model, models );
        bind();
    }

    // initialize

    public void init(IQ iq) {
        this.iq = iq;
    }

    public IQ getIQ() {
        return iq;
    }

    // Getters / Setters

    @Override
    public Component getComponent() {
        return this.view;
    }

    @Override
    public Container getContainer() {
        return model;
    }

    public Component setComponent(Component view) {
        if (view!=null ) {
            if (view.getId()==null||view.getId().equals("")) view.setId(getIdentity());
            setIdentity(view.getId());
        }
        return this.view = view;
    }

    public Container setContainer(Container model) {
        return this.model = model;
    }

    // Wiring

	public void bind() {
		// bind model (container) to view - safely
		if (view!=null && view instanceof Container.Viewer && this.model != null && view!=model) {
			Container.Viewer viewer = (Container.Viewer)view;
			viewer.setContainerDataSource(this.model);
			log.debug("UX bound container: "+view.getId()+" -> "+view.getCaption());
		}
		bind(view);
		bind(model);
	}

    @Override
    public void unbind() {
        // TODO
    }

    public void bind(Component c) {
		if (c==null) return;
		log.debug("Wire component: "+c.getId());
        if (c instanceof ComponentContainer) {
            ComponentContainer cc = (ComponentContainer)c;
            cc.addComponentAttachListener(this);
	        // recursively bind form fields events
	        for(Component cf: cc) {
		        if (cf instanceof Field) bind(cf);
	        }

        }
        if (c instanceof Layout) {
            Layout l = (Layout)c;
            l.addComponentAttachListener(this);
        }
        if (c instanceof AbstractSelect) {
            AbstractSelect as = (AbstractSelect)c;
            //as.addPropertySetChangeListener(this);
            //as.addItemSetChangeListener(this);
            as.addValueChangeListener(this);
        }
		if (c instanceof ItemClickEvent.ItemClickNotifier) {
			ItemClickEvent.ItemClickNotifier n = (ItemClickEvent.ItemClickNotifier)c;
			n.addItemClickListener(this);
		}
		if (c instanceof TabSheet) {
			TabSheet t = (TabSheet)c;
			t.addSelectedTabChangeListener(this);
		}
		if (c instanceof Button) {
			Button b = (Button)c;
			b.addClickListener(this);
		}
        // drag-n-drop
        if (c instanceof Table) {
            Table t = (Table)c;
            t.setDropHandler(this);
        }
        if (c instanceof CustomTable) {
            CustomTable t = (CustomTable)c;
            t.setDropHandler(this);
        }
        if (c instanceof Tree) {
            Tree t = (Tree)c;
            t.setDropHandler(this);
        }
        if (c instanceof DragAndDropWrapper) {
            DragAndDropWrapper d = (DragAndDropWrapper)c;
            d.setDropHandler(this);
        }
	    if (c.getId()==null) {
		    c.setId(getIdentity()+"#"+c.toString());
	    }
    }

    public void bind(Container c) {
		if (c==null) return;
        if (c instanceof Container.ItemSetChangeNotifier) {
            log.debug("Wire container: "+c.getItemIds());
            Container.ItemSetChangeNotifier iscn = (Container.ItemSetChangeNotifier)c;
            iscn.addItemSetChangeListener(this);
            log.debug("Curated Container: "+c.getItemIds());
        }
    }

    /** Bind to all events from other UX **/
    public void bind(UX other) {
        bind(other.getComponent());
        bind(other.getContainer());
    }

    // Component / View Events

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        fire(clickEvent, "urn:factcore:vui:ux:button:click");
    }

    @Override
    public void click(MouseEvents.ClickEvent clickEvent) {
        fire(clickEvent, "urn:factcore:vui:ux:mouse:click");
    }

    @Override
    public void selectedTabChange(TabSheet.SelectedTabChangeEvent selectedTabChangeEvent) {
        fire(selectedTabChangeEvent, "urn:factcore:vui:ux:tab:select");
    }

    @Override
    public void headerClick(Table.HeaderClickEvent headerClickEvent) {
        fire(headerClickEvent, "urn:factcore:vui:ux:table:header:click");
    }

    @Override
    public void footerClick(Table.FooterClickEvent footerClickEvent) {
        fire(footerClickEvent, "urn:factcore:vui:ux:table:footer:click");
    }

    @Override
    public void nodeCollapse(Tree.CollapseEvent collapseEvent) {
        fire(collapseEvent, "urn:factcore:vui:ux:tree:collapse");
    }

    @Override
    public void nodeExpand(Tree.ExpandEvent expandEvent) {
        fire(expandEvent, "urn:factcore:vui:ux:tree:expand");
    }

    @Override
    public void itemClick(ItemClickEvent event) {
        fire(event, "urn:factcore:vui:ux:item:click");
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        log.debug("Enter UX: " + event.getViewName());
    }

    // Container / Model Events

    @Override
    public void componentAttachedToContainer(HasComponents.ComponentAttachEvent event) {
        Component attached = event.getAttachedComponent();
        log.debug("Attached UX: " + attached.getId()+" -> "+attached.getCaption());
        bind(attached);
        for(UXListener UXListener : uxListeners) UXListener.onUXEvent(new UXEvent("urn:factcore:vui:ux:attached", this, event));
    }

    @Override
    public void valueChange(Property.ValueChangeEvent event) {
        Property property = event.getProperty();
        log.debug("Value Changed: "+property.getValue()+" -> "+property.getType());
        for(UXListener UXListener : uxListeners) UXListener.onUXEvent(new UXEvent(VALUE_CHANGED, this, event));
    }

    @Override
    public void containerItemSetChange(Container.ItemSetChangeEvent event) {
        Container container = event.getContainer();
        log.debug("Container Changed: "+container.getItemIds());
        for(UXListener UXListener : uxListeners) UXListener.onUXEvent( new UXEvent(MODEL_CHANGED, this, event) );
    }

	public void drop(DragAndDropEvent event) {
		Transferable source = event.getTransferable();
        TargetDetails target = event.getTargetDetails();
		log.debug("Dropped: "+source.getDataFlavors()+" -> "+source.getSourceComponent().getId());
        log.debug("Target : "+target.getTarget().getId());
        Notification.show("Dropped ");
		for(UXListener UXListener : uxListeners) UXListener.onUXEvent( new UXEvent("urn:factcore:vui:ux:drop", this, event));
	}

	public AcceptCriterion getAcceptCriterion() {
		return new AcceptCriterion() {
			@Override
			public boolean isClientSideVerifiable() {
				return false;
			}

			@Override
			public void paint(PaintTarget target) throws PaintException {
			}

			@Override
			public void paintResponse(PaintTarget target) throws PaintException {
			}

			@Override
			public boolean accept(DragAndDropEvent event) {
                Transferable source = event.getTransferable();
                TargetDetails target = event.getTargetDetails();
                log.debug("Accept Source: "+source.getDataFlavors()+" -> "+source.getSourceComponent().getId());
                log.debug("Accept Target : "+target.getTarget().getId());
				return true;
			}
		};
	}
    // UX re-wiring

    public void fire(Component.Event event, String cmd) {
        fire(new UXEvent(cmd, this, event));
    }

    @Override
    public void fire(UXEvent event) {
        log.debug("UX Event:"+event.getIdentity()+" -> "+ VUIHelper.toString(event.getUX().getComponent()) );
        for(UXListener uxListener : uxListeners) uxListener.onUXEvent(event);
    }


    @Override
    public void onUXEvent(UXEvent event) {
        log.debug("urn:factcore:vui:event#" + event.getIdentity(), event.getUX());
    }

    @Override
    public void addUXListener(UXListener UXListener) {
        if (UXListener ==this) return; // prevent circular events
        uxListeners.add(UXListener);
    }

    @Override
    public void removeUXListener(UXListener UXListener) {
        uxListeners.remove(UXListener);
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    @Override
    public String getIdentity() {
        return this.identity;
    }

	// Context Menu

	public ContextMenu addContextMenu(Component widget) throws FactException, ConfigException {
		if (!(widget instanceof com.vaadin.server.AbstractClientConnector)) {
			log.debug("UX Context Menu: Invalid Widget"+getIdentity()+" -> "+widget.getCaption());
			return null;
		}
		ContextMenu contextMenu = new ContextMenu();
        contextMenu.setOpenAutomatically(true);
        contextMenu.setHideAutomatically(true);

        if (iq!=null) {
            Focus menuFocus = new Focus(getIdentity());
            menuFocus.setContext(widget.getId());
            menuFocus.setUser(getIQ().getUser());

            Collection<Map<String, Object>> menus = iq.getFactCloud().getCore().list(QUERY.ALL_UX_MENUS, menuFocus);
            log.debug("UX Context Menus: "+getIdentity()+" -> "+menuFocus+" --> "+menus);
            for(Map<String, Object>menu: menus) {
                ContextMenu.ContextMenuItem menuItem = contextMenu.addItem(menu.get("caption").toString());
                menuItem.setData(menu);
            }
        }
        contextMenu.addItem("Menu 0").addItem("Menu 1");

		com.vaadin.server.AbstractClientConnector component = (com.vaadin.server.AbstractClientConnector) widget;
		contextMenu.setAsContextMenuOf(component);

		contextMenu.addContextMenuComponentListener(new ContextMenu.ContextMenuOpenedListener.ComponentListener() {
			@Override
			public void onContextMenuOpenFromComponent(ContextMenu.ContextMenuOpenedOnComponentEvent event) {
				log.debug("UX Menu: "+event.getRequestSourceComponent().getId()+" -> "+event.getRequestSourceComponent().getCaption());
				event.getContextMenu().open(new Label("MENU"));
			}
		});
		log.debug("Added Context Menu:"+getIdentity());
		return contextMenu;
	}

}
