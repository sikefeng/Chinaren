package com.sikefeng.chinaren.utils;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.sikefeng.chinaren.MyApplication;

/**
 * Created by Administrator on 2018/7/16.
 */

public class FileUploadUtils {

    public static void uploadFile(String path) {
        // 构造上传请求
        PutObjectRequest put = new PutObjectRequest("aiznsh", "first.jpg", path);
// 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                System.out.println("PutObject=" + "currentSize: " + currentSize + " totalSize: " + totalSize);
            }
        });
        OSSAsyncTask task = MyApplication.getOSSClient().asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                System.out.println("PutObject=" + "UploadSuccess");
                System.out.println("ETag=" + result.getETag());
                System.out.println("RequestId=" + result.getRequestId());
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    System.out.println("ErrorCode=" + serviceException.getErrorCode());
                    System.out.println("RequestId=" + serviceException.getRequestId());
                    System.out.println("HostId=" + serviceException.getHostId());
                    System.out.println("RawMessage=" + serviceException.getRawMessage());
                }
            }
        });

    }




}
