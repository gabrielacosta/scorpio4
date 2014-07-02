package com.factcore.vui.event;
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

import com.factcore.core.Identifiable;
import com.factcore.vui.ux.UX;

/**
 * Fact:Core (c) 2013
 * Module: com.factcore.vui.event
 * User  : lee
 * Date  : 4/02/2014
 * Time  : 6:59 PM
 */

public class UXEvent implements Identifiable {
    public static final String TRIGGER = "urn:factcore:TriggerEvent";
    public static final String SELECT = "urn:factcore:SelectEvent";
    public static final String DESELECT = "urn:factcore:DeselectEvent";
    public static final String INPUT = "urn:factcore:InputEvent";
    public static final String REFRESH = "urn:factcore:RefreshEvent";

    private String identity = "bean:"+getClass().getCanonicalName();
    private UX ux = null;
    private Object state = null;

    public UXEvent(String id) {
        this(id,null,null);
    }

    public UXEvent(String id, UX ux) {
        this(id,ux,null);
    }

    public UXEvent(String id, UX ux, Object state) {
        this.identity=id;
        this.ux=ux;
        this.state=state;
    }

    @Override
    public String getIdentity() {
        return identity;
    }

    public UX getUX() {
        return ux;
    }

    public Object getState() {
        return state;
    }
}
