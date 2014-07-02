package com.factcore.vui.ux.tools;
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
import com.factcore.vui.ux.base.BaseUX;
import com.vaadin.server.FileResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Image;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.teemu.webcam.Webcam;

import java.io.*;

/**
 * Fact:Core (c) 2013
 * Module: com.factcore.vui.ux.tools
 * User  : lee
 * Date  : 6/01/2014
 * Time  : 3:17 AM
 */
public class WebCamUX extends BaseUX {
    File targetFile = null;

    public WebCamUX(IQ iq) {
        build(iq);
        bind();
    }

    protected void build(final IQ iq) {
        targetFile = new File(new File(iq.getHome(),"webcam"), "webcam.jpeg");

        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);

        // Create the webcam and assign a receiver.
        final Webcam webcam = new Webcam();
        webcam.setWidth("400px");

        webcam.setReceiver(new Upload.Receiver() {

            @Override
            public OutputStream receiveUpload(String filename, String mimeType) {
                try {

                    targetFile = File.createTempFile(filename, ".jpeg");
                    targetFile.deleteOnExit();
                    return new FileOutputStream(targetFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });

        // Add an event listener to be called after a successful capture.
        webcam.addCaptureSucceededListener(new Webcam.CaptureSucceededListener() {

            @Override
            public void captureSucceeded(Webcam.CaptureSucceededEvent event) {
                Image img = new Image("Captured image", new FileResource(targetFile));
                img.setWidth("200px");
                layout.addComponent(img);
            }
        });

        // Add a button as an alternative way to capture.
        Button button = new Button("Click the webcam viewfinder OR here to capture");
        button.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                webcam.capture();
            }
        });
        layout.addComponent(webcam);
        layout.addComponent(button);
        setComponent(layout);
    }

}
