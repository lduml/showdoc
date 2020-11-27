package com.lduml.showdoc;

//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;

import static com.lduml.showdoc.MainTreeActivity.str_markdown;

public class PreviewActivity extends AppCompatActivity {

    //@BindView(R.id.markdown_content) WebView webView;

    WebView webView;
    private boolean pageFinish = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        webView = (WebView)findViewById(R.id.markdown_content);
        configWebView();

    }

    public void configWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setSupportZoom(true);
        // 设置出现缩放工具
        webSettings.setBuiltInZoomControls(true);
        //不显示webview缩放按钮
        webSettings.setDisplayZoomControls(false);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                Log.d("001", "onProgressChanged: "+newProgress);
                if (newProgress == 100) {
                    pageFinish = true;
                    loadMarkdown(str_markdown);
                }
            }
        });
        webView.loadUrl("file:///android_asset/markdown.html");
    }
    public void loadMarkdown(String markdown) {
        if (pageFinish) {
            Log.d("001", "loadMarkdown: ");
            String content = markdown.replace("\n", "\\n").replace("\"", "\\\"")
                    .replace("'", "\\'");//.replace("#","# ");
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                webView.evaluateJavascript("javascript:parseMarkdown(\"" + content + "\");", null);
            } else {
                webView.loadUrl("javascript:parseMarkdown(\"" + content + "\");");
            }
        }
    }

}
