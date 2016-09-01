package com.kejiang.yuandl.fastdev;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kejiang.yuandl.base.BaseActivity;

import org.xutils.http.RequestParams;

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
        RequestParams requestParams = new RequestParams("http://218.18.102.45:9988/SvrCardInfo.ashx?MethodName=RegisterSystem");
        requestParams.setAsJsonContent(true);
        requestParams.setBodyContent("{\"PhoneNO\":\"13609284854\",\"EmpPWD\":\"Gn9R\\/fkBHAo=\",\"Token\":\"BOMHo06T138pUcbY8P28Md2WL5exk63zfxWRUFrIrKxf5xsz7Q0Qtd5aEsNqUFmAVGfCsxHdaG1\\/AfkIxnGxVmY\\/xDmZNaNWb3ou7qi+S9a3L\\/EnGs9j\\/KQmy7kbSfOAL7S3TVHb8rUZPiTY9uNLdhqTnrfmhjxM8ozN\\/rdI5bI=\",\"TimeStamp\":\"1472713084\",\"SignString\":\"83a212f78e8ace39caac0da4f05d3a87\"}");
        ajax(requestParams);
    }

    @Override
    public void addListener() {

    }
}
