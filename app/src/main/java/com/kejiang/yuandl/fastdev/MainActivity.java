package com.kejiang.yuandl.fastdev;

import com.kejiang.yuandl.base.BaseActivity;

import org.xutils.http.RequestParams;

import java.util.Map;

public class MainActivity extends BaseActivity {

    @Override
    public void initTitleBar() {
    }

    @Override
    public void initViews() {

        setContentView(R.layout.activity_main);

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
