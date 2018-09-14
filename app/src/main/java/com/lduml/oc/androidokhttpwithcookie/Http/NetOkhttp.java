package com.lduml.oc.androidokhttpwithcookie.Http;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.prefs.Preferences;

import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.lduml.oc.androidokhttpwithcookie.LoginActivity.mcontext;


/**
 * Author: hebin
 * Time : 2017/1/3 0003
 */

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
    /**
     * 同步Get请求
     */
    static OkHttpClient okHttpClient;

    public static void init_OkHttpClient(){
        PersistentCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(mcontext));
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .cookieJar(cookieJar)
                .build();

    }

    public static void sync_doGet(final Context mcontext,final String URL,final DataReceiverCallBack receiveData) {

        sExecutorService.submit(new Runnable() {
            @Override
            public void run() {
            //    PersistentCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(mcontext));
               // cookieJar.loadForRequest()
                Log.d("001", "run: run");
                try {
                 /*    okHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(20, TimeUnit.SECONDS)
                            .writeTimeout(20, TimeUnit.SECONDS)
                            .readTimeout(30, TimeUnit.SECONDS)
                            .cookieJar(cookieJar)*/
                           /* .cookieJar(new CookieJar() {
                                private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();
                                @Override
                                public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                                    System.out.println("do saveFromResponse");
                                    System.out.println("do saveFromResponse url:  "+url);
                                    cookieStore.put(url, cookies);
                                 //   cookieStore.put(HttpUrl.parse(URL), cookies);

                                    System.out.println("do saveFromResponse  HttpUrl.parse(posturl) : "+HttpUrl.parse(URL));

                                    for(int i=0;i<cookies.size();i++)
                                    {
                                        System.out.println(
                                                "  name : "+cookies.get(i).name()
                                                        +"  value : "+cookies.get(i).value()
                                                        +"  expiresAt : "+cookies.get(i).expiresAt()
                                                        +"  domain  : "+cookies.get(i).domain()
                                                        +"  path  : "+cookies.get(i).path()
                                                        +"  secure  : "+cookies.get(i).secure()
                                                        +"  httpOnly  : "+cookies.get(i).httpOnly()
                                                        +"  hostOnly  : "+cookies.get(i).hostOnly()
                                                        +"  persistent  : "+cookies.get(i).persistent());
                                    }
                                }
                                @Override
                                public List<Cookie> loadForRequest(HttpUrl url) {
                                    System.out.println("do loadForRequest");
                                    List<Cookie> cookies = cookieStore.get(HttpUrl.parse(URL));
                                    if(cookies==null){
                                        System.out.println("没加载到cookie");
                                    }
                                    return cookies != null ? cookies : new ArrayList<Cookie>();
                                }
                            })*/
                          /*  .cookieJar(new CookiesManager(mcontext){
                                @Override
                                public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                                    super.saveFromResponse(url, cookies);
                                    Log.d("001", "run: saveFromResponse");
                                }
                                @Override
                                public List<Cookie> loadForRequest(HttpUrl url) {
                                    Log.d("001", "run: loadForRequest");
                                    return super.loadForRequest(url);
                                }
                            })*/
                   //         .build();
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



    /**
     * 异步Get请求
     */
    public void asyn_doGet(String url, final DataReceiverCallBack receiveData) {
        try {
            //           Logger.i("main thread id is " + Thread.currentThread().getId());
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
//            Logger.i("请求数据：" + request.toString());
            client.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                }

                @Override
                public void onResponse(Call call, Response response) {

                    //可以做一些加密解密的东西
                    // 注：该回调是子线程，非主线程
                    try {
                        //                       Logger.i("callback thread id is " + Thread.currentThread().getId());
                        if (response.isSuccessful()) {
                            String data = response.body().string();
//                            Logger.i("响应数据：" + data);
                            receiveData.netSuccess(data);
                        } else {
//                            Logger.i("响应失败");
                            receiveData.netFail();
                        }
                    } catch ( IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void doPostWithCookie(final Context mcontext,final String posturl, final FormBody formBody, final DataReceiverCallBack receiveData){
        String text = "#";
        sExecutorService.submit(new Runnable() {
            @Override
            public void run() {
               PersistentCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(mcontext));
                try {
                   /* okHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(20, TimeUnit.SECONDS)
                            .writeTimeout(20, TimeUnit.SECONDS)
                            .readTimeout(30, TimeUnit.SECONDS)
                           .cookieJar(cookieJar)
                            .build();*/
                 //   okHttpClient.i
                   /* okHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(20, TimeUnit.SECONDS)
                            .writeTimeout(20, TimeUnit.SECONDS)
                            .readTimeout(30, TimeUnit.SECONDS)
                            .cookieJar(new CookieJar() {
                                private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();
                                @Override
                                public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                                    System.out.println("do saveFromResponse");
                                    System.out.println("do saveFromResponse url:  "+url);
                                    cookieStore.put(url, cookies);
                                    //   cookieStore.put(HttpUrl.parse(URL), cookies);

                                    System.out.println("do saveFromResponse  HttpUrl.parse(posturl) : "+HttpUrl.parse(posturl));

                                    for(int i=0;i<cookies.size();i++)
                                    {
                                        System.out.println(
                                                "  name : "+cookies.get(i).name()
                                                        +"  value : "+cookies.get(i).value()
                                                        +"  expiresAt : "+cookies.get(i).expiresAt()
                                                        +"  domain  : "+cookies.get(i).domain()
                                                        +"  path  : "+cookies.get(i).path()
                                                        +"  secure  : "+cookies.get(i).secure()
                                                        +"  httpOnly  : "+cookies.get(i).httpOnly()
                                                        +"  hostOnly  : "+cookies.get(i).hostOnly()
                                                        +"  persistent  : "+cookies.get(i).persistent());
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
                            *//*.cookieJar(new CookiesManager(mcontext){
                                @Override
                                public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                                    super.saveFromResponse(url, cookies);
                                }
                                @Override
                                public List<Cookie> loadForRequest(HttpUrl url) {
                                    return super.loadForRequest(url);
                                }
                            })*//*
                            .build();*/
                    Request request = new Request.Builder().url(posturl).post(formBody).build();
                    Response response = okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String data = response.body().string();
                        System.out.println("data1: "+data);
                    } else {
                        System.out.println("data: not successful");
                    }
                    // FormBody body = new FormBody.Builder().build();
                    Request request2 = new Request.Builder().url("https://www.showdoc.cc/server/index.php?s=/api/item/myList").get().build();
                    Response response2 = okHttpClient.newCall(request2).execute();
                    if (response2.isSuccessful()) {
                        String data = response2.body().string();
                        System.out.println("data2: "+data);
                        receiveData.netSuccess(data);
                    } else {
                        receiveData.netFail();
                    }
                } catch (SocketTimeoutException e) {
                    receiveData.netFail();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public static void get_show_list_with_cookie(final DataReceiverCallBack receiveData){

        sExecutorService.submit(new Runnable() {
            @Override
            public void run() {
           //     PersistentCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(mcontext));
                try {

                    Request request2 = new Request.Builder().url("https://www.showdoc.cc/server/index.php?s=/api/item/myList").get().build();
                    Response response2 = okHttpClient.newCall(request2).execute();
                    if (response2.isSuccessful()) {
                        String data = response2.body().string();
                        System.out.println("data2: "+data);
                        receiveData.netSuccess(data);
                    } else {
                        receiveData.netFail();
                    }

                } catch (SocketTimeoutException e) {
                    receiveData.netFail();
                }catch (Exception e) {
                    e.printStackTrace();
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

                         /*   List<Cookie> cookies;
                            Cookie ce = new Cookie();
                            cookies.add()*/
                            /*cookieStore.put(url, cookies);
                            cookieStore.put(HttpUrl.parse(posturl), cookies);*/

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
                receiveData.netFail();
            }
        } catch (SocketTimeoutException e) {
            receiveData.netFail();
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
                        receiveData.netFail();
                    }
                } catch (SocketTimeoutException e) {
                    receiveData.netFail();
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
/*

    public class AddCookiesInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();
            HashSet<String> preferences = (HashSet) Preferences.getDefaultPreferences().getStringSet(Preferences.PREF_COOKIES, new HashSet<>());
            for (String cookie : preferences) {
                builder.addHeader("Cookie", cookie);
                Log.v("OkHttp", "Adding Header: " + cookie); // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
            }

            return chain.proceed(builder.build());
        }
    }

    public class ReceivedCookiesInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());

            if (!originalResponse.headers("Set-Cookie").isEmpty()) {
                HashSet<String> cookies = new HashSet<>();

                for (String header : originalResponse.headers("Set-Cookie")) {
                    cookies.add(header);
                }

                *//*SharedPreferences settings = getDefaultSharesPreferences();
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("sourceType", 0);
                editor.commit();*//*

                PreferenceManager.getDefaultSharedPreferences(mcontext).edit()
                        .putStringSet("Preferences.PREF_COOKIES", cookies)
                        .apply();
            }

            return originalResponse;
        }
    }*/

}
