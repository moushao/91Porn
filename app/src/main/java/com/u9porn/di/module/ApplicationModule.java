package com.u9porn.di.module;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.danikula.videocache.HttpProxyCacheServer;
import com.danikula.videocache.headers.HeaderInjector;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.Logger;
import com.u9porn.constants.Constants;
import com.u9porn.cookie.AppCookieManager;
import com.u9porn.cookie.CookieManager;
import com.u9porn.data.AppDataManager;
import com.u9porn.data.DataManager;
import com.u9porn.data.cache.CacheProviders;
import com.u9porn.data.db.AppDbHelper;
import com.u9porn.data.db.DbHelper;
import com.u9porn.data.network.ApiHelper;
import com.u9porn.data.network.AppApiHelper;
import com.u9porn.data.prefs.AppPreferencesHelper;
import com.u9porn.data.prefs.PreferencesHelper;
import com.u9porn.di.ApplicationContext;
import com.u9porn.di.DatabaseInfo;
import com.u9porn.di.PreferenceInfo;
import com.u9porn.utils.AddressHelper;
import com.u9porn.utils.AppCacheUtils;
import com.u9porn.utils.MyHeaderInjector;
import com.u9porn.utils.VideoCacheFileNameGenerator;

import java.io.File;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;

/**
 * @author flymegoc
 * @date 2018/2/4
 */
@Module
public abstract class ApplicationModule {

    private static final String TAG = ApplicationModule.class.getSimpleName();

    @Binds
    @ApplicationContext
    abstract Context bindContext(Application application);

    @Singleton
    @Provides
    static HttpProxyCacheServer providesHttpProxyCacheServer(@ApplicationContext Context context,HeaderInjector headerInjector) {
        return new HttpProxyCacheServer.Builder(context)
                // 1 Gb for cache
                .headerInjector(headerInjector)
                .maxCacheSize(AppCacheUtils.MAX_VIDEO_CACHE_SIZE)
                .cacheDirectory(AppCacheUtils.getVideoCacheDir(context))
                .fileNameGenerator(new VideoCacheFileNameGenerator())
                .build();
    }

    @Singleton
    static @Provides HeaderInjector providesHeaderInjector(MyHeaderInjector myHeaderInjector){
        return myHeaderInjector;
    }

    @Singleton
    @Provides
    static CacheProviders providesCacheProviders(@ApplicationContext Context context) {
        File cacheDir = AppCacheUtils.getRxCacheDir(context);
        return new RxCache.Builder()
                .persistence(cacheDir, new GsonSpeaker())
                .using(CacheProviders.class);
    }

    @Singleton
    @Provides
    static Gson providesGson() {
        return new GsonBuilder().create();
    }

    @Singleton
    @Provides
    static AddressHelper providesAddressHelper(PreferencesHelper preferencesHelper) {
        return new AddressHelper(preferencesHelper);
    }

    @Provides
    @DatabaseInfo
    static String providesDatabaseName() {
        return Constants.DB_NAME;
    }

    @Provides
    @PreferenceInfo
    static String providePreferenceName(@ApplicationContext Context context) {
        return context.getPackageName() + "_preferences";
    }

    @Provides
    @Singleton
    static DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    static DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    @Singleton
    static PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    @Singleton
    static ApiHelper providesApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }

    @Provides
    @Singleton
    static CookieManager providesCookieManager(AppCookieManager appCookieManager) {
        return appCookieManager;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Provides
    @Singleton
    static WebView providesWebView(@ApplicationContext Context context){
        Logger.t(TAG).d("初始化");
        WebView mWebView = new WebView(context);

        WebSettings mWebSettings = mWebView.getSettings();

        //启用JavaScript。
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        mWebView.loadUrl("file:///android_asset/web/index.html"); //js文件路径
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                Logger.t(TAG).d("加载完成..:" + url);
            }
        });
        return mWebView;
    }
}
