package com.kejiang.yuandl.fastdev;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.TextView;

import com.kejiang.yuandl.base.BaseActivity;
import com.kejiang.yuandl.base.BaseWebViewActivity;
import com.kejiang.yuandl.utils.CommonAdapter;
import com.kejiang.yuandl.utils.CommonViewHolder;

import org.xutils.http.RequestParams;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseWebViewActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commonAdapterTest();

    }

    private void commonAdapterTest() {
        ListView listView=new ListView(context);
        List<String> datas=new ArrayList<>();
        datas.add("万能适配器测试1");
        datas.add("万能适配器测试2");
        datas.add("万能适配器测试3");
        datas.add("万能适配器测试4");
        listView.setAdapter(new CommonAdapter<String>(context,datas,R.layout.item) {

            @Override
            protected void convertView(View item, String s) {
                TextView textView=  CommonViewHolder.get(item,R.id.textView);
                textView.setText(s);
            }
        });
        setContentView(listView);
    }

    /**
     * 网页测试
     */
    private void webViewTest() {
        webView = new WebView(this);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
//        webView.getSettings().setDomStorageEnabled(true);
//
//
//        WebSettings ws = webView.getSettings();
//        /**
//         *
//         * setAllowFileAccess 启用或禁止WebView访问文件数据 setBlockNetworkImage 是否显示网络图像
//         * setBuiltInZoomControls 设置是否支持缩放 setCacheMode 设置缓冲的模式
//         * setDefaultFontSize 设置默认的字体大小 setDefaultTextEncodingName 设置在解码时使用的默认编码
//         * setFixedFontFamily 设置固定使用的字体 setJavaSciptEnabled 设置是否支持Javascript
//         * setLayoutAlgorithm 设置布局方式 setLightTouchEnabled 设置用鼠标激活被选项
//         * setSupportZoom 设置是否支持变焦
//         * */
//        ws.setBuiltInZoomControls(true);// 隐藏缩放按钮
//        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);// 排版适应屏幕
//        ws.setUseWideViewPort(true);// 可任意比例缩放
//        ws.setLoadWithOverviewMode(true);// setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
//        ws.setSavePassword(true);
//        ws.setSaveFormData(true);// 保存表单数据
//        ws.setJavaScriptEnabled(true);
//        ws.setGeolocationEnabled(true);// 启用地理定位
//        ws.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");// 设置定位的数据库路径
//        ws.setDomStorageEnabled(true);
//        ws.setPluginState(WebSettings.PluginState.ON); //设置webview支持插件
//        webView.setWebChromeClient(new WebChromeClient());
        setContentView(webView);
//        webView.loadUrl("http://www.wzjgswj.gov.cn/wap/NewCont.aspx?ColumnID=34&ID=4107");
//        loadUrl(webView,"http://116.236.217.38:7679/V4.0_WebApp/Area/WebApp/Hospital/OnlineConsultation/Main.html?deviceType=3&machineCode=5c101e60-ca96-424e-8328-b9fa8b3daab8&womanId=42493&requestStartTime=636116568969480000&os=Android5.1&network=WIFI");
        loadUrl(webView, "http://54.222.212.189/v_graph/v_graph.html?from_portal=1&_v=1462845840614");
    }

    @Override
    protected void onResume() {
        super.onResume();
//        try {
//            webView.getClass().getMethod("onResume").invoke(webView, (Object[]) null);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//        try {
//            webView.getClass().getMethod("onPause").invoke(webView, (Object[]) null);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void initTitleBar() {
    }

    @Override
    public void initViews() {

//        setContentView(R.layout.activity_main);

    }

    @Override
    public void initData() {
        RequestParams requestParams = new RequestParams("http://10.58.187.40:8080/WebDemo/login?userName=zhangsan&passWord=45678");
        ajax(requestParams);
    }

    @Override
    public void netOnSuccess(Map<String, Object> data) {
        super.netOnSuccess(data);
    }

    @Override
    public void addListener() {

    }

}
