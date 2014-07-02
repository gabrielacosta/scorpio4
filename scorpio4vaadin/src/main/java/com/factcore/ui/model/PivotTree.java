package com.factcore.ui.model;
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
import com.factcore.fact.FactCloud;
import com.factcore.fact.model.Focus;
import com.factcore.fact.pivot.Pivot;
import com.factcore.oops.KernelException;
import com.factcore.util.map.MapUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Fact:Core (c) 2010-2013
 * User: lee
 * Date: 17/02/13
 * Time: 10:54 PM
 * <p/>
 * This code does something useful
 */
public class PivotTree extends Pivot {
	Map<Object,Object> widget2pivot = new HashMap();
	private String pivotField = "widget";

	public PivotTree(FactCloud core) throws KernelException {
		init(core);
	}
	protected void init(FactCloud factCloud) throws KernelException {
		Map models = MapUtil.getMapByKey( factCloud.getCore().list(QUERY.WIDGET_PIVOTS, new HashMap()), "this");
		if (models==null || models.size()<1) throw new KernelException("No pivots found in: "+factCloud.getIdentity());
		Map<String, Map<String,Object>> pivotMap = (Map)models.get(QUERY.WIDGET_PIVOTS);
		for(Map.Entry pivot: pivotMap.entrySet()) {
			Map map = (Map)pivot.getValue();
//			log.debug("Cache Pivot: "+pivot.getKey()+" := "+map.get("pivot"));
			if (map!=null) widget2pivot.put(pivot.getKey(), map.get("pivot")) ;
			else throw new KernelException("No pivots for "+pivot.getKey());
		}
	}

	public Focus focus(Focus focus, Map<String, Object> model) {
		String pivotOn = (String) model.get(getPivotField());
		String pivot = getPivot(pivotOn);
		addPivot("contains", pivot);
		return focus;
	}

	public String getPivotField() {
		return pivotField;
	}

	public void setPivotField(String pivotField) {
		this.pivotField = pivotField;
	}

	public String getPivot(String widget) {
		return (String)widget2pivot.get(widget);
	}

}
