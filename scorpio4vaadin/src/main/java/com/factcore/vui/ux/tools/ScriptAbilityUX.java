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

import com.factcore.iq.IQ;
import com.factcore.iq.ability.script.ScriptWork;
import com.factcore.iq.capability.VUICapability;
import com.factcore.oops.KernelException;
import com.factcore.oops.FactException;
import com.factcore.oops.IQException;
import com.factcore.oops.KernelException;
import com.factcore.vui.ux.UX;
import com.factcore.vui.ux.base.BaseUX;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;

/**
 * Fact:Core (c) 2013
 * Module: com.factcore.vui.ux.tools
 * User  : lee
 * Date  : 4/01/2014
 * Time  : 6:01 PM
 */
public class ScriptAbilityUX extends BaseUX {
    private static final Logger log = LoggerFactory.getLogger(ScriptAbilityUX.class);
    private TextArea scriptEditor = null;
    private Label results = null, output;
    private ListSelect languages = null, bindingSet = null;
    private Window outputWindow = null;
    private String labelPropertyId = "label";

    public ScriptAbilityUX(IQ iq) {
        init(iq);
    }

    public void init(IQ iq) {
		super.init(iq);
        outputWindow = new OutputWindow("Scripted Output");
        output = new Label("Output", ContentMode.HTML);
        outputWindow.setContent(output);
        outputWindow.setClosable(true);
        outputWindow.setWidth("50%");
        outputWindow.setHeight("50%");

        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        scriptEditor = new TextArea();
        scriptEditor.setSizeFull();

        Button runButton = new Button("run");
        bind(runButton);

        results = new Label("<h1>no results</h1>", ContentMode.HTML);
        bindingSet = new ListSelect("Bindings");
        bindingSet.setRows(4);

        languages = new ListSelect("Languages");
        languages.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
        languages.setItemCaptionPropertyId(labelPropertyId);
		languages.addContainerProperty(labelPropertyId, String.class, "");
        languages.setRows(1);

		populateLanguages(languages, "application/x-groovy");

        layout.addComponent(languages);
        layout.addComponent(scriptEditor);
        layout.addComponent(bindingSet);
        layout.addComponent(runButton);
        layout.addComponent(results);

        setComponent(layout);

        runButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
				runScript(getIQ());
            }
        });

        bind();
    }

	void runScript(IQ iq) {
		try {
			ScriptEngineFactory scriptEngineFactory = (ScriptEngineFactory)languages.getValue();
			if (scriptEngineFactory==null && !scriptEngineFactory.getMimeTypes().isEmpty())  return;
			String mimeType = scriptEngineFactory.getMimeTypes().get(0);
			log.debug("Script language: "+scriptEngineFactory.getLanguageName()+" -> "+mimeType);
            final UX that = this;
			ScriptWork scriptWork = (ScriptWork)iq.doable(getIdentity(), mimeType, iq.getConfiguration());
			iq.addCapability("vui", new VUICapability( iq, getComponent().getUI() ) );
			iq.instruct(scriptWork);
			scriptWork.learn("ux", that);
            scriptWork.learn("window", outputWindow);
			StringWriter writer = new StringWriter() {
                @Override
                public void write(String s) {
                    if (!s.toString().startsWith("<")) super.write("<pre>" + s + "</pre>");
                    else super.write(s.toString());
                }
            };
			scriptWork.setWriter(new PrintWriter(writer));

            writer.write("<div class='vui-console'>");
			Object result = scriptWork.getResult(scriptEditor.getValue().toString(), mimeType, iq.getConfiguration());
            writer.write("</div>");

			output.setVisible(!writer.toString().equals(""));
			output.setValue(writer.toString());

			results.setCaption("Finished "+new Date());
			if (result!=null) {
				results.setValue(JSONObject.fromObject(result).toString());
			}
            if (!getComponent().getUI().getWindows().contains(outputWindow)) {
                getComponent().getUI().addWindow(outputWindow);
                outputWindow.center();
            }
            outputWindow.setCaption(scriptEngineFactory.getLanguageName()+" console ");
            outputWindow.setVisible((result!=null && !result.equals("")));

		} catch (IQException e) {
			results.setCaption("IQ Error");
			results.setValue(e.getLocalizedMessage());
		} catch (FactException e) {
			results.setValue(e.getLocalizedMessage());
			results.setCaption("Fact Error");
		} catch (ScriptException e) {
			results.setCaption("Script Error @ line #"+e.getLineNumber());
			results.setValue(e.getLocalizedMessage());
		} catch (NullPointerException e) {
			results.setCaption("NULL Pointer");
			results.setValue(e.getLocalizedMessage());
		} catch (KernelException e) {
			results.setCaption("CRUD Error");
			results.setValue(e.getLocalizedMessage());
		}
	}

	ScriptEngineFactory populateLanguages(AbstractSelect languages, String defaultLanguage) {
		ScriptEngineFactory defaultEngineFactory = null;
        languages.removeAllItems();
		ScriptEngineManager sem = new ScriptEngineManager();
		List<ScriptEngineFactory> engineFactories = sem.getEngineFactories();
		for(ScriptEngineFactory engineFactory:engineFactories) {
			if (!engineFactory.getMimeTypes().isEmpty() ) {
				String label = engineFactory.getLanguageName();
				if (label.trim().equals(""))
					label = engineFactory.getMimeTypes().get(0);
				else
					label = label+" - "+engineFactory.getLanguageVersion();
				if (label!=null && !label.equals("")) {
					Item item = languages.addItem(engineFactory);
					item.getItemProperty(labelPropertyId).setValue(label);
					log.debug("Language: "+engineFactory.getLanguageName()+" -> "+engineFactory.getMimeTypes());
                    if (engineFactory.getMimeTypes().contains(defaultLanguage)) defaultEngineFactory = engineFactory;
				}
			}
		}
		if (languages instanceof Container.Sortable) ((Container.Sortable)languages).sort(new String[] { labelPropertyId },new boolean[] {false} );
		log.debug("Default language: "+defaultEngineFactory);
		if (defaultEngineFactory!=null) languages.setValue(defaultEngineFactory);
		return defaultEngineFactory;
	}
}
class OutputWindow extends Window {

    public OutputWindow(String l) {
        setCaption(l);
    }
}