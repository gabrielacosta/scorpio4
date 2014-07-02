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

import com.factcore.QUERY;
import com.factcore.core.Doer;
import com.factcore.fact.source.FactSource;
import com.factcore.iq.IQ;
import com.factcore.oops.ConfigException;
import com.factcore.oops.FactException;
import com.factcore.vendor.vaadin.model.ContainerStream;
import com.factcore.vui.ux.base.BaseUX;
import com.factcore.vui.ux.base.GridUX;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;

/**
 * Fact:Core (c) 2013
 * Module: com.factcore.vui.ux
 * User  : lee
 * Date  : 2/01/2014
 * Time  : 9:08 PM
 */
public class QueryScriptUX extends BaseUX {
    private static final Logger log = LoggerFactory.getLogger(QueryScriptUX.class);

    public QueryScriptUX(final IQ iq) throws ConfigException, FactException {
		CssLayout root = new CssLayout();
		setComponent(root);

        FactSource core = iq.getFactCloud().getCore();
        Doer user = iq.getUser();
		log.debug("UX Query Script: "+user.getIdentity());

        final Collection<Map<String,Object>> finders = core.list(QUERY.ALL_FINDERS, user.getConfiguration());
        final Collection<Map<String,Object>> scripts = core.list(QUERY.ALL_SCRIPTS, user.getConfiguration());

		log.debug("UX Query Finders: "+finders.size());
		log.debug("UX Query Scripts: "+scripts.size());

		HorizontalLayout splitPanel = new HorizontalLayout();
//		splitPanel.setSplitPosition(50);
		splitPanel.setHeight("200");
		splitPanel.setSizeFull();

		GridUX scriptUX = new GridUX(scripts);
		CustomTable scriptTable = scriptUX.getGrid();
/*
        Table scriptTable = new Table();
        scriptTable.setSelectable(true);
        scriptTable.setVisible(true);
        scriptTable.setSizeFull();
//		scriptTable.setVisibleColumns("label");

		IndexedContainer scriptContainer = new IndexedContainer();
		scriptContainer.addContainerProperty("this", String.class, "");
		scriptContainer.addContainerProperty("label", String.class, ""); // , "SPARQL query", null, Table.Align.LEFT
		new ContainerStream(scriptContainer, scripts);
		scriptTable.setContainerDataSource(scriptContainer);
		log.debug("Existing Scripts: "+scriptTable.size()+" / "+scripts.size());
*/

		VerticalLayout controlLayout = new VerticalLayout();
		controlLayout.setSizeFull();

        final TextField scriptThis = new TextField("URI");
        scriptThis.setSizeFull();
		scriptThis.setVisible(false);
		scriptThis.setEnabled(false);
		final TextField scriptLabel = new TextField("Label");
		scriptLabel.setRequired(true);
        scriptLabel.setSizeFull();

        final TextArea scriptEditor = new TextArea("SPARQL query");
		scriptEditor.setSizeFull();
		scriptEditor.setRequired(true);
        scriptEditor.setHeight("200");

        HorizontalLayout runLayout = new HorizontalLayout();
        final ListSelect selectFinders = new ListSelect();
		selectFinders.setRows(1);
		selectFinders.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
		selectFinders.setItemCaptionPropertyId("label");
		runLayout.addComponent(selectFinders);

		new ContainerStream(selectFinders, finders);
		log.debug("Query Finders: " + selectFinders.size() + " / " + finders.size());

        Button runButton = new Button("run");
        runLayout.addComponent(runButton);

		// layout
		controlLayout.addComponent(scriptThis);
		controlLayout.addComponent(scriptLabel);
		controlLayout.addComponent(scriptEditor);
        controlLayout.addComponent(runLayout);

//        splitPanel.setFirstComponent(scriptTable);
  //      splitPanel.setSecondComponent(controlLayout);

		splitPanel.addComponent(scriptTable);
		splitPanel.addComponent(controlLayout);

		Panel scriptLayout = new Panel();
		scriptLayout.setContent(splitPanel);
		scriptLayout.setSizeFull();

		log.debug("UX Results Table: "+user.getIdentity());
        // results
        final Table resultsTable = new Table("Results");
        resultsTable.setSizeFull();
        resultsTable.addContainerProperty("this", String.class, "");
        resultsTable.addContainerProperty("label", String.class, "");
        resultsTable.addContainerProperty("edit", Button.class, "");
        resultsTable.setVisible(true);
        resultsTable.setSelectable(false);

        // final layout
        root.addComponent(scriptLayout);
        root.addComponent(resultsTable);

		log.debug("UX Binding: "+getComponent()+" & "+getContainer());
//        bind();

        // UX auto-wiring
//        scriptTable.addItemClickListener(this);
        resultsTable.addItemClickListener(this);
        runButton.addClickListener(this);
        scriptEditor.addValueChangeListener(this);

        // manual wiring
/*
        scriptTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent event) {
                Item selected = event.getItem();
                log.debug("Query Selected: " + selected);
                if (selected != null) {
					Property _scriptThis = selected.getItemProperty("this");
                    Property _scriptLabel = selected.getItemProperty("label");
					Property _scriptBody = selected.getItemProperty("script");
                    log.debug("Script Selected: " + _scriptThis+" -> "+_scriptLabel+" ==> "+_scriptBody);
					if (scriptThis.getValue() != null) scriptThis.setValue(_scriptBody.getValue().toString());
					if (scriptLabel.getValue() != null) scriptLabel.setValue(_scriptBody.getValue().toString());
                    if (scriptEditor.getValue() != null) scriptEditor.setValue(_scriptBody.getValue().toString());
					scriptThis.setVisible(true);
                }
            }
        });
*/
        runButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                resultsTable.setVisible(true);
                String finderURI = (String) selectFinders.getValue();
                String script = scriptEditor.getValue();
                FactSource factSource = iq.getFactCloud().getFactSource(finderURI);
                if (factSource !=null && !script.equals("")) {
                    try {
                        log.debug("Run Script: "+script+" @ "+ factSource);
                        Collection<Map<String, Object>> found = factSource.list(script, iq.getConfiguration());
                        new ContainerStream(resultsTable, found);
                        log.debug("Found Results: "+found.size()+" / "+resultsTable.size()+" @ "+ factSource);
                        Notification.show("Found " + found.size() + " results");

                    } catch (ConfigException e) {
                        Notification.show(e.getMessage());
                    } catch (FactException e) {
                        Notification.show(e.getMessage());
                    }
                } else {
                    Notification.show("Try Again", Notification.Type.ERROR_MESSAGE);
                }
            }
        });
    }

}
