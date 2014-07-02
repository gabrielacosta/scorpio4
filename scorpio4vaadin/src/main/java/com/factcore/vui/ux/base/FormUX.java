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

import com.vaadin.ui.*;
import org.slf4j.LoggerFactory;

/**
 * Fact:Core (c) 2013
 * Module: com.factcore.vui.ux.base
 * User  : lee
 * Date  : 7/03/2014
 * Time  : 8:57 AM
 */
public class FormUX extends BaseUX {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(FormUX.class);

    private FormLayout form = new FormLayout();

    protected FormUX() {
    }

    public FormLayout getForm() {
        return form;
    }

    public void setForm(FormLayout form) {
        this.form=form;
        form.setEnabled(true);
        bind();
    }

    public AbstractField addField(String id, AbstractField field) {
        form.addComponent(field);
        field.setId(id);
        field.setImmediate(true);
        bind(field);
        return field;
    }

    public AbstractField addField(String id, AbstractField field, String required) {
        AbstractField addField = addField(id, field);
        addField.setRequired(true);
        addField.setRequiredError(required);
        return addField;
    }

}
