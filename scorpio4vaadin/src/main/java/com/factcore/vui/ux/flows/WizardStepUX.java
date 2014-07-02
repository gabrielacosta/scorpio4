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

import com.factcore.vui.ux.UX;
import com.factcore.vui.ux.base.BaseUX;
import com.factcore.vui.event.UXEvent;
import com.factcore.vui.event.UXListener;
import com.vaadin.data.Container;
import com.vaadin.ui.Component;
import org.vaadin.teemu.wizards.WizardStep;

/**
 * Fact:Core (c) 2013
 * Module: com.factcore.vendor.vaadin.ui.wizard
 * User  : lee
 * Date  : 5/01/2014
 * Time  : 11:55 PM
 */
public class WizardStepUX implements UX, WizardStep {
    private boolean forwards = true;
    private boolean backwards = false;
    private UX ux = null;
    String caption = null;

    public WizardStepUX(UX ux) {
        this.ux=ux;
        this.caption = ux.getComponent().getCaption();
    }

    public WizardStepUX(String caption, UX ux, boolean forwards, boolean backwards) {
        this.caption=caption;
        this.ux=ux;
        this.setForwards(forwards);
        this.setBackwards(backwards);
    }

	public WizardStepUX(String caption, Component component, boolean forwards, boolean backwards) {
		this.caption=caption;
		this.ux=new BaseUX(component);
		this.setForwards(forwards);
		this.setBackwards(backwards);
	}

    @Override
    public String getCaption() {
        return caption;
    }

    @Override
    public Component getContent() {
        return getComponent();
    }

    @Override
    public boolean onAdvance() {
        return forwards;
    }

    @Override
    public boolean onBack() {
        return backwards;
    }

    public void setForwards(boolean forwards) {
        this.forwards = forwards;
    }

    public void setBackwards(boolean backwards) {
        this.backwards = backwards;
    }

    @Override
    public Component getComponent() {
        return ux.getComponent();
    }

    @Override
    public Container getContainer() {
        return ux.getContainer();
    }

    @Override
    public void bind() {
        ux.bind();
    }

    @Override
    public void unbind() {
        ux.unbind();
    }

    @Override
    public String getIdentity() {
        return ux.getIdentity();
    }

    public void fire(Component.Event event, String cmd) {
        fire(new UXEvent(cmd, this, event));
    }

    @Override
    public void fire(UXEvent event) {
        ux.fire(event);
    }

    @Override
    public void addUXListener(UXListener uxListener) {
        ux.addUXListener(uxListener);
    }

    @Override
    public void removeUXListener(UXListener uxListener) {
        ux.removeUXListener(uxListener);
    }
}
