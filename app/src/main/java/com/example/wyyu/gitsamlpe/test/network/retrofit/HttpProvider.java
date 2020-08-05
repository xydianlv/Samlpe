package com.example.wyyu.gitsamlpe.test.network.retrofit;

import android.os.Build;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.example.wyyu.gitsamlpe.BuildConfig;
import com.example.wyyu.gitsamlpe.framework.ULog;
import com.example.wyyu.gitsamlpe.framework.application.AppController;

/**
 * Created by wyyu on 2020-07-27.
 **/

public class HttpProvider {

    private static class HttpProviderHolder {
        private static final HttpProvider provider = new HttpProvider();
    }

    public static HttpProvider getProvider() {
        return HttpProviderHolder.provider;
    }

    private static final String WEB_VIEW_AGENT_PREFIX =
        "Mozilla/5.0 (Linux; Android; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/55.0.2883.91 Mobile Safari/537.36 ";
    private static final String WEB_VIEW_AGENT_SUFFIX = "WeGroup";

    private String webUserAgent;
    private String userAgent;

    private HttpProvider() {
        initUserAgent();
        initWebUserAgent();
    }

    private void initUserAgent() {
        String ua = "okhttp/3.12.2 "
            + WEB_VIEW_AGENT_SUFFIX
            + "/"
            + BuildConfig.VERSION_NAME
            + " "
            + "(Android/"
            + Build.VERSION.SDK_INT
            + ")";
        char[] uaValues = ua.toCharArray();
        for (int i = 0; i < uaValues.length; i++) {
            char c = uaValues[i];
            if ((c <= '\u001f' && c != '\t') || c >= '\u007f') {
                uaValues[i] = ' ';
            }
        }
        userAgent = new String(uaValues);
    }

    private void initWebUserAgent() {
        webUserAgent = WEB_VIEW_AGENT_PREFIX;
        try {
            WebView webView = new WebView(AppController.getAppContext());
            WebSettings webSettings = webView.getSettings();
            webUserAgent = webSettings.getUserAgentString();
            webView.destroy();
        } catch (Throwable e) {
            ULog.show(e.getMessage());
        }
    }

    public String getUserAgent() {
        if (TextUtils.isEmpty(userAgent)) {
            initUserAgent();
        }
        return userAgent;
    }

    public String getWebUserAgent() {
        if (TextUtils.isEmpty(webUserAgent)) {
            initWebUserAgent();
        }
        return webUserAgent;
    }
}
