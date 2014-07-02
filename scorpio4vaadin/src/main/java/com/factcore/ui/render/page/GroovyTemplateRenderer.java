package com.factcore.ui.render.page;
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
import com.factcore.fact.model.Focus;
import com.factcore.oops.ConfigException;
import com.factcore.oops.FactException;
import groovy.lang.Writable;
import groovy.text.GStringTemplateEngine;
import groovy.text.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;

/**
 * Fact:Core (c) 2010-2013
 * User: lee
 * Date: 19/01/13
 * Time: 3:46 PM
 * <p/>
 * This code does something useful
 */
public class GroovyTemplateRenderer extends FactRenderer {
	private static final Logger log = LoggerFactory.getLogger(GroovyTemplateRenderer.class);
	private PrintWriter out = null;
	private GStringTemplateEngine engine = null;

	public GroovyTemplateRenderer() {
		this(new StringWriter());
	}

	public GroovyTemplateRenderer(PrintWriter out) {
		this.out = out;
		this.engine = new GStringTemplateEngine();
	}

	public GroovyTemplateRenderer(Writer out) {
		this.out = new PrintWriter(out);
		this.engine = new GStringTemplateEngine();
	}

	public PrintWriter getWriter() {
		return out;
	}

	public void render(String gsp, Map model) throws IOException, ConfigException {
		Template template = null;
		try {
			template = engine.createTemplate(new StringReader(gsp));
		} catch (ClassNotFoundException e) {
			throw new ConfigException("Class not found: "+e.getMessage(),e);
		}
		Writable writable = template.make(model);

		writable.writeTo(out);
		out.flush();
	}

	public Focus focus(String query, Map model) throws ConfigException, FactException {
		Focus focus = new Focus(query, model);
		try {
			render(query,model);
			focus.put("out", out.toString());
			return focus;
		} catch (IOException e) {
			throw new ConfigException("Failed to write: "+query,e);
		}
	}

	public String toString() {
		return getWriter().toString();
	}
}
