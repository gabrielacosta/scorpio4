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

import com.factcore.iq.IQ;
import com.factcore.vui.ux.UX;
import com.factcore.vui.ux.base.BaseUX;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import org.vaadin.teemu.wizards.Wizard;
import org.vaadin.teemu.wizards.event.*;

import java.util.Collection;

/**
 * Fact:Core (c) 2013
 * Module: com.factcore.vui.ux.flows
 * User  : lee
 * Date  : 19/01/2014
 * Time  : 7:33 PM
 */
public class WizardUX extends BaseUX {
    Wizard wizard = new Wizard();
    WizardStepUX state = null;

    public WizardUX(IQ iq) {
        init(iq);
    }

    public WizardUX(IQ iq, Collection<WizardStepUX> steps, String state) {
        init(iq);
        for(WizardStepUX step: steps) {
            addStep(step);
        }
    }

    public void init(IQ iq, FormLayout form) {
        super.init(iq);
        wizard.setHeight("80%");
		wizard.setWidth("100%");
        wizard.getNextButton().setCaption("next >>");
        wizard.getBackButton().setCaption("<< back");
        wizard.getFinishButton().setCaption("finish");
        wizard.getCancelButton().setCaption("cancel");
		setComponent(wizard);
        bind();
    }

    public WizardStepUX getState() {
        return state;
    }

	public Wizard getWizard() {
		return this.wizard;
	}

    public WizardStepUX addStep(WizardStepUX wizardStepUX) {
        this.wizard.addStep(wizardStepUX);
        this.wizard.addListener(new WizardProgressListener() {
            @Override
            public void activeStepChanged(WizardStepActivationEvent wizardStepActivationEvent) {
                state = (WizardStepUX) wizardStepActivationEvent.getActivatedStep();
                Notification.show(state.getCaption());
            }

            @Override
            public void stepSetChanged(WizardStepSetChangedEvent wizardStepSetChangedEvent) {
                // NO-OP
            }

            @Override
            public void wizardCompleted(WizardCompletedEvent wizardCompletedEvent) {
                // NO-OP
                fire(wizardCompletedEvent, "urn:factcore:vui:ux:flows:complete");
                wizard.setEnabled(false);
                Notification.show("Completed");
            }

            @Override
            public void wizardCancelled(WizardCancelledEvent wizardCancelledEvent) {
                // NO-OP
            }
        });
        bind(wizardStepUX.getComponent());
        bind(wizardStepUX.getContainer());
        return wizardStepUX;
    }

    public WizardStepUX addStep(String caption, Component widget, boolean fwd, boolean back) {
        return addStep( new WizardStepUX( caption, new BaseUX( widget ), fwd, back) );
    }

	public WizardStepUX addStep(String caption, UX ux, boolean fwd, boolean back) {
		return addStep( new WizardStepUX( caption, ux, fwd, back ) );
	}
}
