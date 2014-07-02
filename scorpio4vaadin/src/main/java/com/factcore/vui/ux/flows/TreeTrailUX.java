package com.factcore.vui.ux.flows;
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

import com.factcore.PROPS;
import com.factcore.fact.source.FactSource;
import com.factcore.oops.ConfigException;
import com.factcore.oops.FactException;
import com.factcore.vendor.vaadin.model.ContainerStream;
import com.factcore.vui.ux.base.BaseUX;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.ui.TreeTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Fact:Core (c) 2013
 * Module: com.factcore.vui.ux.flows
 * User  : lee
 * Date  : 4/01/2014
 * Time  : 3:39 AM
 */
public class TreeTrailUX extends BaseUX {
    private static final Logger log = LoggerFactory.getLogger(TreeTrailUX.class);

    public TreeTrailUX(FactSource finder, Stack<String> queries) throws ConfigException, FactException {
        init(finder,queries);
    }

    private void init(FactSource finder, Stack<String> queries) throws ConfigException, FactException {
        TreeTable treeTable = createTreeTable(finder,queries, new HashMap());
        setComponent(treeTable);
        setContainer(treeTable);
        bind();
    }

    private TreeTable createTreeTable() {
        TreeTable treeTable = new TreeTable();
        treeTable.setSizeFull();
		treeTable.setDragMode(TreeTable.TableDragMode.ROW);
		treeTable.setSelectable(true);
        return treeTable;
    }

    private TreeTable createTreeTable(FactSource finder, Stack<String> queries, Map model) throws ConfigException, FactException {
        TreeTable treeTable = createTreeTable();
        ContainerStream containerStream = new ContainerStream(treeTable);
        populate(containerStream, treeTable, finder, queries, model);
        return treeTable;
    }

    private void populate(ContainerStream containerStream, Container.Hierarchical tree, FactSource finder, Stack<String> queries, Map model) throws ConfigException, FactException {
        if (queries.isEmpty()) return;
        String query = queries.pop();
        Collection<Map<String, Object>> rows = finder.list(query, model);
        String that = (String) model.get(PROPS.THIS);
        if (rows.isEmpty()) {
            log.debug("Leaf Empty: "+query+" -> "+model);
            if (that!=null) tree.setChildrenAllowed(that, false );
            queries.push(query);
            return;
        }
        log.debug("Tree results: "+query+" x "+rows.size());
        for(Map row: rows) {
            String id = (String) row.get(PROPS.THIS);
            Item item = tree.addItem(id);
			tree.setChildrenAllowed(id, false );
            if (item!=null) {
                containerStream.setItemProperties(tree, item, row);
                if (that!=null) tree.setParent(id, that);
                tree.setChildrenAllowed(id, !queries.isEmpty() );
                populate(containerStream, tree, finder, queries, row);
            } else {
                log.debug("Tree item skipped: "+id+" -> "+row);
            }
        }
        queries.push(query);
    }

	/*
    private void populate(ContainerStream containerStream, Container.Hierarchical tree, FactSource finder, Stack<String> queries, Map model) throws ConfigException, FactException {
        if (queries.isEmpty()) return;
        String query = queries.pop();
        Collection<Map<String, Object>> rows = finder.list(query, model);
        String that = (String) model.get(PROPS.THIS);
        if (rows.isEmpty()) {
            log.debug("Leaf Empty: "+query+" -> "+model);
            if (that!=null) tree.setChildrenAllowed(that, false );
            queries.push(query);
            return;
        }
        log.debug("Tree results: "+query+" x "+rows.size());
        for(Map row: rows) {
            String id = (String) row.get(PROPS.THIS);
            Item item = tree.addItem(id);
			if (item==null) item = tree.getItem(id);
            if (item!=null) {
				tree.setChildrenAllowed(id, false );
                containerStream.setItemProperties(tree, item, row);
                if (that!=null) {
					tree.setParent(id, that);
					tree.setChildrenAllowed(that, true );
				}
                populate(containerStream, tree, finder, queries, row);
            } else {
                log.debug("Tree item skipped: "+id+" -> "+row);
            }
        }
        queries.push(query);
    }

	 */
}
