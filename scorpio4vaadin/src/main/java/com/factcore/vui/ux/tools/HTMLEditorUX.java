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

import com.factcore.oops.ConfigException;
import com.factcore.oops.FactException;
import com.factcore.vendor.vaadin.model.ContainerStream;
import com.factcore.vui.ux.base.GridUX;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.openesignforms.ckeditor.CKEditorConfig;
import org.vaadin.openesignforms.ckeditor.CKEditorTextField;

import java.util.Collection;
import java.util.Map;

/**
 * Fact:Core (c) 2013
 * Module: com.factcore.vui.ux.tools
 * User  : lee
 * Date  : 3/01/2014
 * Time  : 9:20 PM
 */
public class HTMLEditorUX extends GridUX {
    private static final Logger log = LoggerFactory.getLogger(HTMLEditorUX.class);
    CKEditorTextField htmlEditor;
    Item selected = null;

    public HTMLEditorUX(Collection<Map<String,Object>> pages) throws FactException, ConfigException {
        init(pages);
    }

    public void init(Collection<Map<String,Object>> pages) throws FactException, ConfigException {
        HorizontalLayout root = (HorizontalLayout) setComponent(new HorizontalLayout());
        root.setSizeFull();

		CustomTable selection = createSelection(pages);
		selection.setVisibleColumns("label");
        root.addComponent(selection);

        Component editor = createEditor("untitled page");
        root.addComponent(editor);
		log.debug("HTML UX VUI: "+selection.getUI());

        bind();
        selection.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent event) {
                selected = event.getItem();
                Property body = selected.getItemProperty("body");
                log.debug("Editing HTML: "+event.getItemId()+" -> "+body.getValue());
                if (body==null) {
                    htmlEditor.setValue("");
                    return;
                }
                Object html = body.getValue();
                if (html!=null) {
                    htmlEditor.setValue(html.toString());
                }
            }
        });
        htmlEditor.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                Object value = event.getProperty().getValue();
                if (selected!=null) {
                    Property property = selected.getItemProperty("body");
                    property.setValue(value);
                }
            }
        });
    }

    private CustomTable createSelection(Collection<Map<String, Object>> pages) throws FactException, ConfigException {
        // table of html pages
        CustomTable pagesTable = newTable();
//        pagesTable.setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);
        pagesTable.addContainerProperty("label", String.class, "");
        new ContainerStream(pagesTable, pages);
        setContainer(pagesTable);
        bind((Component) pagesTable);
        log.debug("HTML Pages: "+pagesTable.size());
        return pagesTable;
    }

    public CKEditorTextField createEditor(String title) {
        // configure editor
        CKEditorConfig config = new CKEditorConfig();
        config.setStartupModeWysiwyg();
        config.setToolbarCanCollapse(true);
        config.setTabSpaces(4);
/*
//        http://stackoverflow.com/questions/2115302/ckeditor-image-upload-filebrowseruploadurl
        config.addCustomToolbarLine("{ name: 'ux_editor_fields', items: ['+','-','=','?'] }");
		config.addCustomToolbarLine("[\n" +
				"    { name: 'document', items : [ 'Source','-','NewPage','Preview','Print','-','Templates' ] },\n" +
				"        { name: 'clipboard', items : [ 'Cut','Copy','Paste','PasteText','PasteFromWord','-','Undo','Redo' ] },\n" +
				"        { name: 'editing', items : [ 'Find','Replace','-','SelectAll','-','SpellChecker', 'Scayt' ] },\n" +
				"        { name: 'forms', items : [ 'Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton', 'HiddenField' ] },\n" +
				"        '/',\n" +
				"        { name: 'basicstyles', items : [ 'Bold','Italic','Underline','Strike','Subscript','Superscript','-','RemoveFormat' ] },\n" +
				"        { name: 'paragraph', items : [ 'NumberedList','BulletedList','-','Outdent','Indent','-','Blockquote','CreateDiv','-','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock','-','BidiLtr','BidiRtl' ] },\n" +
				"        { name: 'links', items : [ 'Link','Unlink','Anchor' ] },\n" +
				"        { name: 'insert', items : [ 'Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak','Iframe' ] },\n" +
				"        '/',\n" +
				"        { name: 'styles', items : [ 'Styles','Format','Font','FontSize' ] },\n" +
				"        { name: 'colors', items : [ 'TextColor','BGColor' ] },\n" +
				"        { name: 'tools', items : [ 'Maximize', 'ShowBlocks','-','About' ] }\n" +
				"        ]\n");
        */

		config.setFilebrowserWindowHeight("300");
		config.setFilebrowserWindowWidth("300");

        config.setFilebrowserImageBrowseUrl("/rest/do/urn:factcore.vui.ux.ImageBrowser?this="+getIdentity());
		config.setFilebrowserImageWindowHeight("400");
		config.setFilebrowserImageWindowWidth("800");

        config.setFilebrowserImageUploadUrl("/rest/do/urn:factcore.vui.ux.ImageUploader?this="+getIdentity());
        config.setFilebrowserLinkBrowseUrl("/rest/do/urn:factcore.vui.ux.LinkBrowser?this="+getIdentity());

		config.useCompactTags();
//		config.disableElementsPath();
		config.setResizeDir(CKEditorConfig.RESIZE_DIR.HORIZONTAL);
		config.disableSpellChecker();

		// html editor
        htmlEditor = new CKEditorTextField();
        htmlEditor.setCaption(title);
        htmlEditor.setSizeFull();
        htmlEditor.setImmediate(true);

        htmlEditor.setConfig(config);
        return htmlEditor;
    }

}
