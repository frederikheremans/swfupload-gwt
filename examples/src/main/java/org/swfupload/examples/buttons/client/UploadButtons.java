package org.swfupload.examples.buttons.client;

import org.swfupload.client.SWFUpload;
import org.swfupload.client.UploadBuilder;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

public class UploadButtons implements EntryPoint {

  public void onModuleLoad() {

    // Determine if the demo is being viewed in Hosted mode, if so display a warning. 
    // This is because Flash to JavaScript communications does not work correctly in hosted mode.
    
    if (!GWT.isScript()) {
      RootPanel.get("warning").getElement().getStyle().setProperty("display", "block");
    }
    else {
      RootPanel.get("demo").getElement().getStyle().setProperty("display", "block");
    }

    // -----------------
    
    UploadBuilder builder1 = new UploadBuilder();
    //builder1.setDebug(true);
    builder1.setHTTPSuccessCodes(200,201);
    builder1.setFileTypes("*.asf;*.wma;*.wmv;*.avi;*.flv;*.swf;*.mpg;*.mpeg;*.mp4;*.mov;*.m4v;*.aac;*.mp3;*.wav;*.png;*.jpg;*.jpeg;*.gif");
    builder1.setFileTypesDescription("Images, Video & Sound");
    
    builder1.setButtonPlaceholderID("xpbutton");
    builder1.setButtonImageURL("XPButtonUploadText_61x22.png");
    builder1.setButtonWidth(61);
    builder1.setButtonHeight(22);
    
    @SuppressWarnings("unused")
    SWFUpload upload1 = builder1.build();
    
    builder1.setButtonPlaceholderID("styled1");
    builder1.setButtonImageURL("gwt-button-standard-theme_66x27_notext.png");
    builder1.setButtonWidth(66);
    builder1.setButtonHeight(27);
    builder1.setButtonText("<span class=\"label\">Upload</span>");
    builder1.setButtonTextStyle(".label { color: #000000; font-family: sans font-size: 16pt; }");
    builder1.setButtonTextLeftPadding(7);
    builder1.setButtonTextTopPadding(4);
    
    @SuppressWarnings("unused")
    SWFUpload upload2 = builder1.build();
    
    builder1.setButtonPlaceholderID("gtkbutton");
    builder1.setButtonImageURL("gtk-human_70x23_blank.png");
    builder1.setButtonWidth(70);
    builder1.setButtonHeight(23);
    builder1.setButtonTextLeftPadding(10);
    builder1.setButtonTextTopPadding(2);
    
    @SuppressWarnings("unused")
    SWFUpload upload3 = builder1.build();

  }
}
