package com.factcore.vui.ux.factcore;

import com.factcore.iq.IQ;
import com.factcore.vui.ux.base.BaseUX;
import com.vaadin.data.Container;
import com.vaadin.data.util.FilesystemContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.MultiFileUpload;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.UploadFinishedHandler;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.UploadStateWindow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;

/**
 * Fact:Core (c) 2013
 * Module: com.factcore.vui.ux
 * User  : lee
 * Date  : 3/01/2014
 * Time  : 3:55 PM
 */
public class LocalFilesUX extends BaseUX implements UploadFinishedHandler {
    private static final Logger log = LoggerFactory.getLogger(LocalFilesUX.class);
    MultiFileUpload upload = null;
    Tree tree = new Tree();

    public LocalFilesUX(IQ iq) {
        init(iq.getUser().getHome(), null, true);
    }

    public LocalFilesUX(File root, FilenameFilter filter, boolean recursive) {
        init(root,filter,recursive);
    }

    public LocalFilesUX(File root, boolean recursive) {
        init(root, null, recursive);
    }

    public void init(File root, FilenameFilter filter, boolean recursive) {
		VerticalLayout layout = new VerticalLayout();

        tree.setItemCaptionPropertyId(FilesystemContainer.PROPERTY_NAME);
        tree.setId("file://" + root.getAbsolutePath());
        tree.setCaption(root.getName());
        tree.setDragMode(Tree.TreeDragMode.NODE);
		layout.addComponent(tree);
        bind((Container) tree);
        bind((Component) tree);

        UploadStateWindow stateWindow = new UploadStateWindow();
        upload = new MultiFileUpload(this, stateWindow);
        layout.addComponent(upload);
        upload.setPanelCaption("Uploading ...");
        upload.setUploadButtonCaptions("Upload File", "Upload Files");
        upload.setInterruptedMsg("Upload was interrupted");
        upload.setMaxFileSize(1024*1024*100); // 100mb limit
        bind(upload);
//        upload.setCaption("Upload");
//        layout.addComponent(stateWindow);

		setComponent(layout);
        setContainer(new FilesystemContainer(root, filter, recursive));
        bind();
        log.debug("UX Files: "+root.getAbsolutePath()+" -> "+tree.size());
    }


    @Override
    public void handleFile(InputStream inputStream, String s, String s2, long l) {
        Notification.show(s);
    }
}
