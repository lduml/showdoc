package com.lduml.oc.androidokhttpwithcookie.Http;

import android.content.Context;
import android.util.Log;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class NetOkhttp {
    public static InputStream is;
    private static NetOkhttp netOkhttp;
    public static synchronized NetOkhttp newInstance() {
        if (netOkhttp==null){
            netOkhttp = new NetOkhttp();
        }
        return netOkhttp;
    }

    //开启线程池
    private static ExecutorService sExecutorService;
    static {
        sExecutorService = Executors.newFixedThreadPool(8);
    }

    static OkHttpClient okHttpClient;

    public static void init_OkHttpClient(Context mcontext){
        PersistentCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(mcontext));
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .cookieJar(cookieJar)
                .build();

    }

    public static void sync_doGet(final String URL,final DataReceiverCallBack receiveData) {

        sExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Log.d("001", "run: run");
                try {
                    Request request = new Request.Builder().url(URL).get().build();
                    Response response = okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()) {
                        Log.d("001", "run: img_success");
                        System.out.println("img_success");
                        try {
                            is = response.body().byteStream();
                            receiveData.netSuccess("get-img-success");
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    } else {
                        Log.d("001", "run: img_fail");
                        System.out.println("img_net---fail");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void HttpPostWithCookie(final String posturl, final FormBody formBody, final DataReceiverCallBack receiveData){
        sExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder().url(posturl).post(formBody).build();
                    Response response = okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String data = response.body().string();
                        System.out.println("data2: "+data);
                        receiveData.netSuccess(data);
                    } else {
                        receiveData.netFail("响应失败");
                    }
                } catch (SocketTimeoutException e) {
                    receiveData.netFail("超时：SocketTimeoutException");
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void HttpLoginGetItemListPostWithCookie(final String login_url, final String item_url,final FormBody formBody, final DataReceiverCallBack receiveData){
        sExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder().url(login_url).post(formBody).build();
                    Response response = okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()) {
                        /*{"error_code":0,"data":[]}*/
                        String data = response.body().string();
                        System.out.println("data1: "+data);
                        receiveData.netSuccess(data);
                    } else {
                        System.out.println("data: not successful");
                    }
                } catch (SocketTimeoutException e) {
                    receiveData.netFail("超时：SocketTimeoutException");
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void http_get_with_cookie(final String url,final DataReceiverCallBack receiveData){

        sExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request2 = new Request.Builder().url(url).get().build();
                    Response response2 = okHttpClient.newCall(request2).execute();
                    if (response2.isSuccessful()) {
                        String data = response2.body().string();
                        try {
                           // is = response2.body().byteStream();
                            receiveData.netSuccess(data);
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        receiveData.netSuccess(data);
                    } else {
                        receiveData.netFail("响应失败");
                    }

                } catch (SocketTimeoutException e) {
                    receiveData.netFail("超时：SocketTimeoutException");
                }catch (Exception e) {
                    e.printStackTrace();
                    Log.d("001", "Exception: "+e);
                }
            }
        });
    }

    public static void doPostNoFromParameters( final DataReceiverCallBack receiveData) {

        final String posturl = "showdoc";
        try {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .cookieJar(new CookieJar() {
                        private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();
                        @Override
                        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                            System.out.println("do saveFromResponse");
                            cookieStore.put(url, cookies);
                            cookieStore.put(HttpUrl.parse(posturl), cookies);
                            for(int i=0;i<cookies.size();i++)
                            {
                                System.out.println(cookies.get(i).name()+": "+cookies.get(i).value()+": "+cookies.get(i).expiresAt()
                                        +": "+cookies.get(i).domain()
                                        +": "+cookies.get(i).path()
                                        +": "+cookies.get(i).secure()
                                        +": "+cookies.get(i).httpOnly()
                                        +": "+cookies.get(i).hostOnly()
                                        +": "+cookies.get(i).persistent());
                            }
                        }
                        @Override
                        public List<Cookie> loadForRequest(HttpUrl url) {
                            System.out.println("do loadForRequest");
                            List<Cookie> cookies = cookieStore.get(HttpUrl.parse(posturl));
                            if(cookies==null){
                                System.out.println("没加载到cookie");
                            }
                            return cookies != null ? cookies : new ArrayList<Cookie>();
                        }
                    })
                    .build();

            FormBody body = new FormBody.Builder().build();
            Request request2 = new Request.Builder().url("http://facejoy.com/admin/addFace/faceList/14").post(body).build();
            Response response2 = okHttpClient.newCall(request2).execute();
            if (response2.isSuccessful()) {
                String data = response2.body().string();
                System.out.println("data2: "+data);
                receiveData.netSuccess(data);
            } else {
                receiveData.netFail("响应失败");
            }
        } catch (SocketTimeoutException e) {
            receiveData.netFail("超时：SocketTimeoutException");
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 带参数POST请求
     */
    public static void doPostFromParameters(final String url, final FormBody formBody, final DataReceiverCallBack receiveData) {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(20, TimeUnit.SECONDS)
                            .writeTimeout(20, TimeUnit.SECONDS)
                            .readTimeout(30, TimeUnit.SECONDS)
                            .build();
                    FormBody body = new FormBody.Builder().build();
                    Request request2 = new Request.Builder().url("http://facejoy.com/admin/addFace/faceList/14").post(body).build();
                    Response response2 = okHttpClient.newCall(request2).execute();
                    if (response2.isSuccessful()) {
                        String data = response2.body().string();
                        System.out.println("data2: "+data);
                        receiveData.netSuccess(data);
                    } else {
                        receiveData.netFail("响应失败");
                    }
                } catch (SocketTimeoutException e) {
                    receiveData.netFail("超时：SocketTimeoutException");
                }catch (Exception e) {
                    e.printStackTrace();
                }

    }
    public static String unicodeToUTF_8(String src) {
        if (null == src) {
            return null;
        }
        System.out.println("src: " + src);
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < src.length();) {
            char c = src.charAt(i);
            if (i + 6 < src.length() && c == '\\' && src.charAt(i + 1) == 'u') {
                String hex = src.substring(i + 2, i + 6);
                try {
                    out.append((char) Integer.parseInt(hex, 16));
                } catch (NumberFormatException nfe) {
                    nfe.fillInStackTrace();
                }
                i = i + 6;
            } else {
                out.append(src.charAt(i));
                ++i;
            }
        }
        return out.toString();
    }
}
