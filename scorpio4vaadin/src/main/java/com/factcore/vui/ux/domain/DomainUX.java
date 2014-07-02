package com.factcore.vui.ux.domain;
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
import com.factcore.vendor.vaadin.ui.rich.Grid;
import com.factcore.vui.ux.base.BaseUX;
import com.vaadin.data.Container;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.*;

/**
 * Fact:Core (c) 2013
 * Module: com.factcore.vui.ux.domain
 * User  : lee
 * Date  : 19/01/2014
 * Time  : 5:33 PM
 */
public class DomainUX extends BaseUX {
    VerticalLayout layout = new VerticalLayout();

    private ComboBox vocabulary = new ComboBox();
    FormLayout domainForm = new FormLayout();
    Grid nouns = new Grid();
    Window window = new Window();

    public DomainUX(IQ iq) {
        init(iq);
    }

    public void build(Container nouns) {
        buildNounWindow();

        vocabulary.setCaption("Solution Domain");
        domainForm.addComponent(vocabulary);
        this.layout.addComponent(domainForm);
        layout.addComponent(buildNouns(nouns));

    }

    private Component buildNouns(Container nouns) {
        this.nouns.addContainerProperty("this", String.class, "");
        this.nouns.addContainerProperty("label", String.class, "");
        this.nouns.setVisibleColumns("label");
        this.nouns.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent event) {
                window.setVisible(true);
            }
        });
        this.nouns.setContainerDataSource(nouns);
        return this.nouns;
    }

    private void buildNounWindow() {
        window.setCaption("Noun Definition");

        window.addCloseListener(new Window.CloseListener() {
            @Override
            public void windowClose(Window.CloseEvent e) {
                window.setVisible(false);
            }
        });
        window.setVisible(false);
    }

}
