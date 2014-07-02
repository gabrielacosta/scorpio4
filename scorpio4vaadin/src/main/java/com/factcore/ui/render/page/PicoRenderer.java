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
import com.factcore.template.PicoTemplate;

import java.util.Map;

/**
 * Fact:Core (c) Lee Curtis 2012
 * User: lee
 * Date: 17/03/13
 * Time: 1:47 PM
 * <p/>
 * This code does something useful
 */
public class PicoRenderer extends FactRenderer {
	PicoTemplate template = null;
	String out = null;

	public PicoRenderer() {
	}

	@Override
	public void render(String pivot, Map model) throws ConfigException {
		this.template = new PicoTemplate(pivot);
		this.out = this.template.translate(model);
		model.put("out", this.out);
	}

	public Focus focus(String query, Map model) throws ConfigException, FactException {
		Focus focus = new Focus(query, model);
		render(query, focus);
		return focus.focused();
	}

	public String toString() {
		return out==null?"Pico Not Rendered":out;
	}

}
