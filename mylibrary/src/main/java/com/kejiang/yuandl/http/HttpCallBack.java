package com.kejiang.yuandl.http;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.TypeReference;
import com.kejiang.yuandl.bean.JsonBean;
import com.kejiang.yuandl.utils.Tools;
import com.kejiang.yuandl.view.LoadingDialog;
import com.orhanobut.logger.Logger;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.x;

import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.Set;

/**
 * 网络请求回调
 * Created by yuandl on 2016/8/31 0031.
 */
public class HttpCallBack implements Callback.ProgressCallback<String> {
    private Context context;
    private HttpResponse httpResponse;
    private int requestCode = -1;
    private LoadingDialog loadingDialog;

    public HttpCallBack(Context context, int requestCode, HttpResponse httpResponse) {
        this.context = context;
        this.requestCode = requestCode;
        this.httpResponse = httpResponse;
    }

    public HttpCallBack(Context context, int requestCode, HttpResponse httpResponse, LoadingDialog loadingDialog) {
        this.context = context;
        this.requestCode = requestCode;
        this.httpResponse = httpResponse;
        this.loadingDialog = loadingDialog;
    }

    @Override
    public void onWaiting() {

    }

    @Override
    public void onStarted() {
        if (loadingDialog != null) {
            loadingDialog.show("Loading...");
        }

        httpResponse.netOnStart();
        if (requestCode != -1) {
            httpResponse.netOnStart(requestCode);
        }

    }

    @Override
    public void onLoading(long total, long current, boolean isDownloading) {

    }

    @Override
    public void onSuccess(String result) {
        Logger.json(result);
        JsonBean jsonBean = null;
        try {
            jsonBean = jsonParse(result);
            if (jsonBean.getMsg() != null && !jsonBean.getMsg().isEmpty()) {
                Toast.makeText(context, jsonBean.getMsg(), Toast.LENGTH_SHORT).show();
            }
            if (jsonBean.getStatus() == 1) {
                Map<String, Object> data = new ArrayMap<String, Object>();
                if (null != jsonBean.getData() && jsonBean.getData().size() > 0) {
                    data = jsonBean.getData();
                }
                httpResponse.netOnSuccess(data);
                if (requestCode != -1) {
                    httpResponse.netOnSuccess(data, requestCode);
                }
            } else {
                httpResponse.netOnOtherStatus(jsonBean.getStatus(), jsonBean.getMsg());
                if (requestCode != -1) {
                    httpResponse.netOnOtherStatus(jsonBean.getStatus(), jsonBean.getMsg(), requestCode);
                }
            }
        } catch (Exception e) {
            Logger.d(result);
            Tools.show(context, "服务器异常！", Toast.LENGTH_SHORT);
            e.printStackTrace();
        } finally {
        }

    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        httpResponse.netOnFailure(ex);
        if (requestCode != -1) {
            httpResponse.netOnFailure(requestCode, ex);
        }
        Logger.d(ex.getMessage());
        if (ex instanceof HttpException) { // 网络错误
            HttpException httpEx = (HttpException) ex;
            int responseCode = httpEx.getCode();
            String responseMsg = httpEx.getMessage();
            String errorResult = httpEx.getResult();
            Toast.makeText(x.app(), "网络繁忙", Toast.LENGTH_SHORT).show();
            // ...
        } else if (ex instanceof SocketTimeoutException) {
            Toast.makeText(x.app(), "连接服务器超时", Toast.LENGTH_SHORT).show();
        } else if (ex != null && "网络不可用".equals(ex.getMessage())) {
        } else { // 其他错误
            Toast.makeText(x.app(), "连接服务器失败，请稍后再试！", Toast.LENGTH_SHORT).show();
            // ...
        }
    }

    @Override
    public void onCancelled(CancelledException cex) {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void onFinished() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
        httpResponse.netOnFinish();
        if (requestCode != -1) {
            httpResponse.netOnFinish(requestCode);
        }
    }

    private JsonBean jsonParse(String json) throws JSONException {
        ArrayMap<String, Object> arrayMap = JSON.parseObject(json, new TypeReference<ArrayMap<String, Object>>() {
        }.getType());
        JsonBean jsonBean = new JsonBean();
        if (arrayMap.containsKey("data")) {
            Object data = arrayMap.get("data");
//            System.out.println("data.getClass().getName()=" + data.getClass().getName());
            ArrayMap<String, Object> rrData = null;
            if (data instanceof String) {
//                System.out.println("data instanceof String");
                rrData = new ArrayMap<String, Object>();
                rrData.put("data", data.toString());
            } else if (data instanceof JSONArray) {
//                System.out.println("data instanceof JSONArray");
                rrData = new ArrayMap<String, Object>();
                rrData.put("data", data);
            } else if (data instanceof com.alibaba.fastjson.JSONObject) {
//                System.out.println("data instanceof JSONObject");
                rrData = JSON.parseObject(data.toString(), new TypeReference<ArrayMap<String, Object>>() {
                }.getType());
            }
            jsonBean.setData(rrData);
        } else {
            Set<String> keys = arrayMap.keySet();
            ArrayMap<String, Object> rrData = new ArrayMap<>();
            for (String s : keys) {
                if (!s.equals("status")) {
                    rrData.put(s, arrayMap.get(s));
                }
            }
            jsonBean.setData(rrData);
        }
        jsonBean.setStatus(Integer.valueOf(arrayMap.get("status").toString()));
        jsonBean.setMsg(Tools.getValue(arrayMap, "msg"));

        return jsonBean;
    }
}
