//package com.sikefeng.chinaren.utils;
//
//import android.content.Context;
//
//import com.hss01248.dialog.StyledDialog;
//import com.neovisionaries.ws.client.WebSocket;
//import com.neovisionaries.ws.client.WebSocketAdapter;
//import com.neovisionaries.ws.client.WebSocketException;
//import com.neovisionaries.ws.client.WebSocketExtension;
//import com.neovisionaries.ws.client.WebSocketFactory;
//import com.neovisionaries.ws.client.WebSocketState;
//import com.sikefeng.chinaren.R;
//import com.sikefeng.chinaren.api.APIConfig;
//import com.sikefeng.mvpvmlib.utils.LogUtils;
//
//import java.io.IOException;
//import java.security.KeyManagementException;
//import java.security.NoSuchAlgorithmException;
//import java.security.cert.CertificateException;
//import java.util.List;
//import java.util.Locale;
//import java.util.Map;
//
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.TrustManager;
//import javax.net.ssl.X509TrustManager;
//
//
///**
// * 文件名：WebSocketUtils <br>
// * 创建时间： 2017/7/19 下午22:09 <br>
// * 文件描述：<br>
// * WebSocket客户端
// *
// * @version v0.1  <br>
// * @since JDK 1.8
// */
//public class WebSocketUtils {
//
//    /**
//     * 连接超时时间
//     */
//    private static final int TIME_OUT = 5000;
//    /**
//     * 定义WebSocket
//     */
//    private static WebSocket ws;
//    /**
//     * 定义类
//     */
//    private static WebSocketUtils instance;
//
////    private WebSocketClient client;// 连接客户端
//
//    /**
//     * Gets instance.
//     *
//     * @return the instance
//     */
//    public static WebSocketUtils getInstance() {
//        if (instance == null) {
//            instance = new WebSocketUtils();
//        }
//        return instance;
//    }
//
//    /**
//     * 来接WebSocket
//     *
//     * @param context   本地上下文环境
//     * @param showDialog 是否显示等待框
//     * @return WebSocket
//     */
//    public WebSocket connectWS(final Context context, final boolean showDialog) {
//
//        try {
//            if (showDialog) {
//                StyledDialog.buildLoading(context.getString(R.string.connectting)).show();
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        try {
//            //获取系统当前使用的语言
//            String lan = Locale.getDefault().getLanguage();
//
////            SSLContext ctx = SSLContext.getInstance("SSL");
//            SSLContext sslcontext = createIgnoreVerifySSL();
//            this.ws = new WebSocketFactory()
//                    .setSSLContext(sslcontext)
//                    .setConnectionTimeout(TIME_OUT)
//                    .createSocket(APIConfig.WS_URL)
//                    .addListener(new WebSocketAdapter() {
//                        @Override
//                        public void onTextMessage(WebSocket websocket, String message) throws Exception {
//                            LogUtils.i(" >>>>>>>>>> message :: " + message);
//
//
//                        }
//
//                        @Override
//                        public void onStateChanged(WebSocket websocket, WebSocketState newState) throws Exception {
//                            LogUtils.i(" >>>>>>>>>> onChanged: " + newState);
//
//                        }
//
//
//                        @Override
//                        public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
//                            LogUtils.i(" >>>>>>>>>> connecd.");
//                            //连接成功
//                            LogUtils.i("服务器连接成功");
//
//                            StyledDialog.dismissLoading();
//                        }
//
//
//                        @Override
//                        public void onConnectError(WebSocket websocket, WebSocketException exception) throws Exception {
//                            LogUtils.i(" >>>>>>>>>> exception: " + exception.getMessage());
//
//                            StyledDialog.dismissLoading();
//                        }
//                    })
//                    .addExtension(WebSocketExtension.PERMESSAGE_DEFLATE)
//                    .connectAsynchronously();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (KeyManagementException e) {
//            e.printStackTrace();
//        }
//
//        return ws;
//
//    }
//
//    public WebSocket getWebSocket() {
//        return ws;
//    }
//
//    /**
//     * 发送消息
//     *
//     * @param text 消息内容
//     */
//    public void sendText(String text) {
//        if (ws != null) {
//            ws.sendText(text);
//        }
//    }
//
//    /**
//     * 绕过验证
//     *
//     * @return SSLContext
//     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
//     * @throws KeyManagementException   KeyManagementException
//     */
//    public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
//        SSLContext sc = SSLContext.getInstance("SSLv3");
//
//        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
//        X509TrustManager trustManager = new X509TrustManager() {
//            @Override
//            public void checkClientTrusted(
//                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
//                    String paramString) throws CertificateException {
//            }
//
//            @Override
//            public void checkServerTrusted(
//                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
//                    String paramString) throws CertificateException {
//            }
//
//            @Override
//            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                return null;
//            }
//        };
//
//        sc.init(null, new TrustManager[]{trustManager}, null);
//        return sc;
//    }
//}
