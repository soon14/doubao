package com.xinspace.csevent.monitor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.xinspace.csevent.R;
import com.xinspace.csevent.util.LogUtil;
import com.xinspace.csevent.util.StatusBarUtils;
import com.xinspace.csevent.ui.activity.BaseActivity;

/**
 *
 * Created by Android on 2017/4/8.
 */

public class CoresunWebAct extends BaseActivity{

    private WebView web_coresun;
    private String url;
    private LinearLayout ll_web_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fra_coresun_web);
        StatusBarUtils.setWindowStatusBarColor(this , R.color.app_bottom_color);
        initView();
    }


    private void initView() {

        ll_web_back = (LinearLayout) findViewById(R.id.ll_web_back);
        ll_web_back.setVisibility(View.VISIBLE);
        ll_web_back.setOnClickListener(clickListener);

        url = "http://grandway020.com/bc/app/index.php?i=7";
        web_coresun = (WebView) findViewById(R.id.web_coresun);

        WebSettings ws = web_coresun.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setJavaScriptCanOpenWindowsAutomatically(true);
        ws.setAllowFileAccess(true);
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        ws.setSupportZoom(true);
        ws.setBuiltInZoomControls(true);
        ws.setUseWideViewPort(true);
        ws.setSupportMultipleWindows(true);
        ws.setLoadWithOverviewMode(true);
        ws.setAppCacheEnabled(true);
        ws.setDatabaseEnabled(true);
        ws.setDomStorageEnabled(true);
        ws.setGeolocationEnabled(true);
        ws.setAppCacheMaxSize(Long.MAX_VALUE);
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        ws.setPluginState(WebSettings.PluginState.ON_DEMAND);
        ws.setRenderPriority(WebSettings.RenderPriority.HIGH);

        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);// 排版适应屏幕

		/*ws.setBuiltInZoomControls(true);// 隐藏缩放按钮
		ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);// 排版适应屏幕
		ws.setUseWideViewPort(true);// 可任意比例缩放
		ws.setLoadWithOverviewMode(true);// setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
		ws.setSavePassword(true);
		ws.setSaveFormData(true);// 保存表单数据
		ws.setJavaScriptEnabled(true);
		ws.setGeolocationEnabled(true);// 启用地理定位
		ws.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");// 设置定位的数据库路径
		ws.setDomStorageEnabled(true);*/
        //videowebview.setOverScrollMode(View.OVER_SCROLL_ALWAYS);
        web_coresun.setOverScrollMode(View.SCROLL_AXIS_NONE);

        web_coresun.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageFinished(WebView view, String url)
            {
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtil.i("www拦截" + url);
                String clickFlag = "webclick://openLock&pNavId=174";
                String backFlag = "webclick://callBack";

                if (url.startsWith("http:") || url.startsWith("https:")){
                    //view.loadUrl(url);
                    return false;
                }else if (url != null && clickFlag.equals(url)){
                    Intent intent = new Intent(CoresunWebAct.this , OpenDoorActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
        web_coresun.loadUrl(url);
    }


    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ll_web_back:
                    if (web_coresun.canGoBack()){
                        web_coresun.goBack();
                    }else{
                        finish();
                    }
                    break;
            }
        }
    };


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        LogUtil.i("222222222222222222");
        if (web_coresun.canGoBack()){
            web_coresun.goBack();
        }else{
            finish();
        }
        return true;
    }
}
