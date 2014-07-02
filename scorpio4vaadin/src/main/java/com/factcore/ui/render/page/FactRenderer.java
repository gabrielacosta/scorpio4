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
import com.factcore.oops.ConfigException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Fact:Core (c) 2010-2013
 * User: lee
 * Date: 19/01/13
 * Time: 3:46 PM
 * <p/>
 * This code does something useful
 */
public abstract class FactRenderer implements Renderer {
	private static final Logger log = LoggerFactory.getLogger(FactRenderer.class);
	private Map<String, PivotPage> pivots = new HashMap();

	public FactRenderer() {
	}

	public List<Map> pivot(String pivotOn, Map model) {
		Object pivot = model.get(pivotOn);
		if (pivot==null) return null;
		if (pivot instanceof List) return (List)pivot;
		return null;
	}

	public void render(Map model) throws IOException, ConfigException {
//		PivotPage page = pivots.get("");
		log.debug("Master Page: "+model);
		boolean hasRendered = false;
		for(Map.Entry pivot: pivots.entrySet()) {
			if (model.get(pivot.getKey())!=null) {
				render((PivotPage)pivot.getValue(), model);
				hasRendered = true;
			}
		}
	}


	public void render(PivotPage page, Map model) throws IOException, ConfigException {
		if (page==null) return;
//		log.debug("Render: {}", page.getPivot());
		String pivot = page.getPivot();
		if (pivot!=null && !pivot.equals("")) {
			List<Map> models = pivot(pivot, model);
			if (models!=null && models.size()>0) {
				renderHeader(page, model);
				for(int i=0;i<models.size();i++) {
					Map body = new HashMap();
//						body.putAll(model);
					body.putAll(models.get(i));

					renderBody(page, body );
					render( body);
				}
				renderFooter( page, model );
			} else {
				renderEmpty( page, model );
			}
		}
	}

	public abstract void render(String pivot,  Map model) throws IOException, ConfigException;

	public void renderHeader(PivotPage page,  Map model) throws ConfigException, IOException{
		render(page.getHeader(), model);
	}

	public void renderBody(PivotPage page, Map model) throws IOException, ConfigException {
		render(page.getBody(), model);
	}

	public void renderFooter(PivotPage page, Map model) throws IOException, ConfigException {
		render(page.getFooter(), model);
	}


	public void renderEmpty(PivotPage page, Map model) throws IOException, ConfigException {
		render(page.getEmpty(), model);
	}

	public Map<String, PivotPage> getPivots() {
		return pivots;
	}

	public void add(PivotPage page) {
		this.pivots.put(page.getPivot(), page);
	}

	public PivotPage getPage(String pivot) {
		return (PivotPage)pivots.get(pivot);
	}

}
