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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PivotPage {
	private static final Logger log = LoggerFactory.getLogger(PivotPage.class);

	String pivot = null;
	private String header = null;
	private String body = null;
	private String footer = null;
	private String empty = null;

	PivotPage(String pivot, String body) {
		this.setPivot(pivot);
		this.setBody(body);
	}

	PivotPage(String pivot, String header, String body, String footer, String empty) {
		this.setPivot(pivot);
		this.setBody(body);
		this.setHeader(header);
		this.setFooter(footer);
		this.setEmpty(empty);
	}

	public String getPivot() {
		return pivot;
	}

	public void setPivot(String pivot) {
		this.pivot = pivot;
	}

	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getFooter() {
		return this.footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public String getEmpty() {
		return this.empty;
	}

	public void setEmpty(String empty) {
		this.empty = empty;
	}
}