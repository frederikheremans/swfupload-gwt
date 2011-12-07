package org.swfupload.examples.upload.client;

import java.util.ArrayList;
import java.util.List;

import org.swfupload.client.File;
import org.swfupload.client.SWFUpload;
import org.swfupload.client.UploadBuilder;
import org.swfupload.client.SWFUpload.ButtonAction;
import org.swfupload.client.SWFUpload.ButtonCursor;
import org.swfupload.client.event.FileDialogCompleteHandler;
import org.swfupload.client.event.FileQueuedHandler;
import org.swfupload.client.event.UploadCompleteHandler;
import org.swfupload.client.event.UploadErrorHandler;
import org.swfupload.client.event.UploadProgressHandler;
import org.swfupload.client.event.UploadSuccessHandler;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class UploadDemo implements EntryPoint {

 
	private boolean disabled = false; 
	SWFUpload upload;
	private List<File> files = new ArrayList<File>();  

	private void removeFile(String id) {
		for (File ff : files) {
			if (ff.getId().equals(id)) { 
				files.remove(ff); 
			}
		}
	}
	
	public void onModuleLoad() {
		
		// Determine if the demo is being viewed in Hosted mode, if so display a
		// warning.
		// This is because Flash to JavaScript communications does not work
		// correctly in hosted mode.
		if (!GWT.isScript()) {
			HTML warning = new HTML();
			warning.addStyleName("note"); 
			warning.setHTML("NIS"); 
			warning.setTitle("Not in scripting mode. You have to deploy the app to an application server!"); 
			RootPanel.get().add(warning); 
			return; 
		} 
		
		// lets go ... 
		VerticalPanel vp = new VerticalPanel(); 
		vp.setSpacing(5); 
		RootPanel.get().add(vp);
		HTML bt = new HTML("<span id=\"xpbutton\" />"); 
		vp.add(bt); 
		Button button = new Button("Enable / Disable");
		vp.add(button); 
		button.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				if ( disabled ) {
					upload.setButtonDisabled(false); 
					upload.setButtonCursor(ButtonCursor.HAND.getValue());
					disabled = false;
				} else { 
					upload.setButtonDisabled(true);
					upload.setButtonCursor(ButtonCursor.ARROW.getValue());
					disabled = true; 
				}
			}
		}); 
//		Button bt = new Button("Upload"); 
//		bt.getElement().setId("xpbutton"); 
//		vp.add(bt); 
		
		final HTML html = new HTML("----");
		HorizontalPanel hp = new HorizontalPanel();
		hp.setSpacing(5); 
		RootPanel.get().add(hp);
		hp.setWidth("100%");
		hp.add(html);
		final HTML html2 = new HTML(GWT.getHostPageBaseURL() + "<br />" + GWT.getModuleBaseURL() + 
				"<br />" + GWT.getModuleName()); 
		hp.add(html2);

		// -----------------
		
		final UploadBuilder builder1 = new UploadBuilder();
		// builder1.setDebug(true);
		builder1.setHTTPSuccessCodes(200, 201);
		builder1.setFileTypes("*.asf;*.wma;*.wmv;*.avi;*.flv;*.swf;*.mpg;*.mpeg;*.mp4;*.mov;*.m4v;*.aac;*.mp3;*.wav;*.png;*.jpg;*.jpeg;*.gif");
		builder1.setFileTypesDescription("Images, Video & Sound");

		builder1.setButtonPlaceholderID("xpbutton");
		builder1.setButtonImageURL("XPButtonUploadText_61x22.png");
//		builder1.setButtonText("Upload"); 
		builder1.setButtonDisabled(false); 
		builder1.setButtonCursor(ButtonCursor.HAND);
//		builder1.setButtonCursor(ButtonCursor.ARROW);
		builder1.setButtonWidth(61);
		builder1.setButtonHeight(22);
		builder1.setButtonAction(ButtonAction.SELECT_FILES);

		builder1.setUploadProgressHandler(new UploadProgressHandler() {

			public void onUploadProgress(UploadProgressEvent e) {
				File f = e.getFile();

				f.getName();
				String text = html.getHTML();
				text += "<br />" + e.getBytesComplete() + "; " + f.getName();
				html.setHTML(text);
			}
		});
		
		builder1.setUploadSuccessHandler(new UploadSuccessHandler() {
			public void onUploadSuccess(UploadSuccessEvent e) {
				String t = html.getHTML(); 
				t += "<br />server data : " + e.getServerData(); 
				html.setHTML(t); 
			}
		}); 

		builder1.setUploadErrorHandler(new UploadErrorHandler() {
			public void onUploadError(UploadErrorEvent e) {
				File ff = e.getFile(); 
				String message = e.getMessage(); 
				if (message == null || message.trim().length() == 0) {
					message = "upload failed"; 
				}
				String t = html.getHTML(); 
				t += "<br />error: " + ff.getId() + ", " + ff.getName() + " / " + message; 
				html.setHTML(t); 
				removeFile(ff.getId()); 
				if (files.size() > 0) {
					ff = files.get(0); 
					String id = ff.getId(); 
					String tt = html.getHTML(); 
					tt += "<br />start: " + id; 
					html.setHTML(tt); 
					upload.startUpload(id); 
				}
			}
		}); 

		String moduleBase = GWT.getModuleBaseURL();
		String moduleName = GWT.getModuleName();  
		String baseApp = moduleBase.substring(0, moduleBase.indexOf(moduleName)); 
		String url = baseApp + "upload";
		//"http://localhost:8080/swfupload-gwt-demo/upload" 
		builder1.setUploadURL(url); 

		builder1.setUploadCompleteHandler(new UploadCompleteHandler() {
			public void onUploadComplete(UploadCompleteEvent e) {
				File f = e.getFile(); 
				String t = html.getHTML();
				t += "<br />done : " + f.getId() + ", " + f.getName(); 
				html.setHTML(t);
				removeFile(f.getId()); 
				if (files.size() > 0) {
					File ff = files.get(0); 
					String id = ff.getId(); 
					String tt = html.getHTML(); 
					tt += "<br />start: " + id; 
					html.setHTML(tt); 
					upload.startUpload(id); 
				}
			}
		});

		builder1.setFileQueuedHandler(new FileQueuedHandler() {
			public void onFileQueued(FileQueuedEvent event) {
				String t = html2.getHTML();
				t += "<br />ofq: " + event.getFile().getId() + "; "
						+ event.getFile().getName();
				html2.setHTML(t);
				files.add(event.getFile()); 
			}
		});

		builder1.setFileDialogCompleteHandler(new FileDialogCompleteHandler() {
			public void onFileDialogComplete(FileDialogCompleteEvent e) {
				html.setHTML("files = " + files.size());
				if (files.size() > 0) {
					File ff = files.get(0); 
					String id = ff.getId(); 
					String t = html.getHTML(); 
					t += "<br />start: " + id; 
					html.setHTML(t); 
					upload.startUpload(id); 
				}
			}
		});
		upload = builder1.build();



	}
}
