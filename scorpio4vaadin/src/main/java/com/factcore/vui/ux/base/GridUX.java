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

import com.factcore.oops.ConfigException;
import com.factcore.oops.FactException;
import com.factcore.vendor.vaadin.model.ContainerStream;
import com.factcore.vendor.vaadin.ui.rich.Grid;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.peter.contextmenu.ContextMenu;

import java.util.Collection;
import java.util.Map;

/**
 * Fact:Ops (c) 2013
 * Module: com.factcore.vui.ux
 * User  : lee
 * Date  : 2/01/2014
 * Time  : 4:10 PM
 */
public class GridUX extends BaseUX {
	private static final Logger log = LoggerFactory.getLogger(GridUX.class);
    CustomTable table;

    protected GridUX() {
    }

	public GridUX(Collection<Map<String, Object>> rows) throws FactException, ConfigException {
        init(rows);
	}

    public void init(Collection<Map<String, Object>> rows) throws FactException, ConfigException {
		CssLayout layout = new CssLayout();
		layout.setSizeFull();
        table = newTable(rows);
        table.setId(getIdentity());
        table.setDragMode(CustomTable.TableDragMode.ROW);
		layout.addComponent(table);
        setComponent(layout);
        setContainer(table);
        bind();
    }

	public CustomTable getGrid() {
		return table;
	}

    public CustomTable newTable() throws FactException, ConfigException {
        Grid table = new Grid();
        table.setSizeFull();
        table.setPageLength(10);
        table.setImmediate(true);
        table.setEditable(false);
        table.setSelectable(true);
        table.setFooterVisible(true);

        addContextMenu(table);
        return table;
    }

    public CustomTable newTable(Collection<Map<String, Object>> rows) throws FactException, ConfigException {
        CustomTable table = newTable();
		log.debug("New UX table: "+rows.size());
        new ContainerStream(table, rows);
        log.debug("Populated UX table: " + table.size());
        return table;
    }

    public ContextMenu addContextMenu_x(Table table) {
		log.debug("UX Table Context Menu: "+getIdentity()+" -> "+table.getCaption());
        ContextMenu.ContextMenuOpenedListener.TableListener tableOpenListener = new ContextMenu.ContextMenuOpenedListener.TableListener() {

            public void onContextMenuOpenFromRow(ContextMenu.ContextMenuOpenedOnTableRowEvent event) {
                log.debug("Grid menu: "+event.getItemId()+" -> "+event.getPropertyId());
                event.getContextMenu().open(new Label("Menu"));
            }

            public void onContextMenuOpenFromHeader(ContextMenu.ContextMenuOpenedOnTableHeaderEvent event) {
                log.debug("Grid header: "+event.getPropertyId());
                event.getContextMenu().open(new Label("Header"));
            }

            public void onContextMenuOpenFromFooter(ContextMenu.ContextMenuOpenedOnTableFooterEvent event) {
                log.debug("Grid footer: "+event.getPropertyId());
                event.getContextMenu().open(new Label("Footer"));
            }
        };

        ContextMenu contextMenu = new ContextMenu();
        contextMenu.addItem("Test 0").addItem("Test 1");
        contextMenu.setOpenAutomatically(false);
        contextMenu.setHideAutomatically(true);
        contextMenu.setAsTableContextMenu(table);

        contextMenu.addContextMenuTableListener(tableOpenListener);
        log.debug("Added Context Menu:"+getIdentity());
        return contextMenu;
    }

}
