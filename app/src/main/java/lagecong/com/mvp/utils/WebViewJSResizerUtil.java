package lagecong.com.mvp.utils;

import android.app.Activity;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

/**
 * Created by Anwar on 10/25/2018.
 */
public class WebViewJSResizerUtil {

    public static final String JS_RESIZER_NAME = "WebViewJSResizer";

    private Activity mActivity;
    private WebView mWebView;

    public WebViewJSResizerUtil(Activity activity, WebView webView){
        mActivity = activity;
        mWebView = webView;
    }

    @JavascriptInterface
    public void changeHeight(final String height){
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams layoutParams = mWebView.getLayoutParams();
                layoutParams.height = Integer.valueOf(height);
                mWebView.setLayoutParams(layoutParams);
            }
        });
    }
}
