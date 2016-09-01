package com.kejiang.yuandl.http;

import android.content.Context;
import android.widget.Toast;

import com.kejiang.yuandl.utils.CheckNetwork;
import com.kejiang.yuandl.utils.SharedPreferencesUtils;
import com.kejiang.yuandl.view.LoadingDialog;
import com.orhanobut.logger.Logger;

import org.xutils.common.Callback;
import org.xutils.common.util.KeyValue;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * 网络请求工具类
 * Created by yuandl on 2016/8/31 0031.
 */
public class HttpUtils implements HttpRequest {
    private volatile static HttpUtils httpUtils;


    private HttpUtils() {
    }

    public static HttpUtils getInstance() {
        if (httpUtils == null) {
            synchronized (HttpUtils.class) {
                if (httpUtils == null) {
                    httpUtils = new HttpUtils();
                }
            }
        }
        return httpUtils;
    }

    @Override
    public Callback.Cancelable ajax(Context context, RequestParams requestParams, HttpResponse httpResponse) {
        return ajax(context, requestParams, -1, true, httpResponse);
    }

    @Override
    public Callback.Cancelable ajax(Context context, RequestParams requestParams, boolean isShowLoadingDialog, HttpResponse httpResponse) {
        return ajax(context, requestParams, -1, isShowLoadingDialog, httpResponse);
    }

    @Override
    public Callback.Cancelable ajax(Context context, RequestParams requestParams, int requestCode, HttpResponse httpResponse) {
        return ajax(context, requestParams, -1, true, httpResponse);
    }

    @Override
    public Callback.Cancelable ajax(Context context, RequestParams requestParams, int requestCode, boolean isShowLoadingDialog, HttpResponse httpResponse) {
        if (!CheckNetwork.isNetworkAvailable(context)) {
            Toast.makeText(context, "网络不可用，请检查网络连接！", Toast.LENGTH_SHORT).show();
            httpResponse.netOnFailure(requestCode, new Exception("网络不可用"));
            return null;
        }
        LoadingDialog loadingDialog = null;
        if (isShowLoadingDialog) {
            if (loadingDialog == null) {
                loadingDialog = new LoadingDialog(context);
            }
        } else {
            loadingDialog = null;
        }
        SharedPreferencesUtils sp = new SharedPreferencesUtils(x.app());
        boolean isLogin = (boolean) sp.getParam("login", false);
        if (isLogin) {
            requestParams.addBodyParameter("mId", (String) sp.getParam("mId", ""));
        }
        boolean hasLocation = (boolean) sp.getParam("hasLocation", false);
        if (hasLocation) {
            requestParams.addBodyParameter("lng", (String) sp.getParam("lng", ""));
            requestParams.addBodyParameter("lat", (String) sp.getParam("lat", ""));
        }
        Logger.d("url=" + requestParams.getUri() + "\nrequestParams=" + requestParams.getStringParams().toString());
        List<KeyValue> params = requestParams.getStringParams();
        String requestParamstr ="url=" + requestParams.getUri();
        for (KeyValue keyValue : params) {
            if (keyValue.key.contains(":")) {
                throw new RuntimeException("参数异常！");
            }
            requestParamstr += "\n" + keyValue.key + "=" + keyValue.getValueStr();

        }
        Logger.d(requestParamstr);
        HttpCallBack httpCallBack = new HttpCallBack(context, requestCode, httpResponse, loadingDialog);
        Callback.Cancelable cancelable = x.http().post(requestParams, httpCallBack);
        return cancelable;
    }
}
