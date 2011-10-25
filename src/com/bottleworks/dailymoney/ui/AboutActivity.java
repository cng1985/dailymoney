package com.bottleworks.dailymoney.ui;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.bottleworks.commons.util.GUIs;
import com.bottleworks.dailymoney.context.ContextsActivity;
import com.iym.dailymoney.Res;
/**
 * 
 * @author dennis
 *
 */
public class AboutActivity extends ContextsActivity {
    
    WebView whatsnew;
    WebView aboutapp;
    
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(Res.layout.about);
        
        whatsnew = (WebView)findViewById(Res.id.about_whatsnew);
        aboutapp = (WebView)findViewById(Res.id.about_app);
        
        whatsnew.getSettings().setAllowFileAccess(true);
        whatsnew.getSettings().setJavaScriptEnabled(true);
        whatsnew.addJavascriptInterface(new JSCallHandler(),"dmctrl");
        whatsnew.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY); 
        
        aboutapp.getSettings().setAllowFileAccess(true);
        aboutapp.getSettings().setJavaScriptEnabled(true);
        aboutapp.addJavascriptInterface(new JSCallHandler(),"dmctrl");
        aboutapp.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY); 
        
        
        aboutapp.loadUrl(Constants.LOCAL_URL_PREFIX+"how2use_zh.html");
        whatsnew.loadUrl(Constants.LOCAL_URL_PREFIX+"ad.html");
    }
    
    private void onLinkClicked(final String path){
       whatsnew.setVisibility(View.GONE);
       aboutapp.loadUrl(Constants.LOCAL_URL_PREFIX+path);
    }
    
    class JSCallHandler {
        public void onLinkClicked(final String path){
            GUIs.post(new Runnable(){
                public void run() {
                    AboutActivity.this.onLinkClicked(path);
                }});
        }
    }
}
