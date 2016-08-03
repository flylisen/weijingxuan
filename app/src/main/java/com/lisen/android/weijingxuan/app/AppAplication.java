package com.lisen.android.weijingxuan.app;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.lisen.android.weijingxuan.db.MySqliteDatabeHelper;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by Administrator on 2016/7/29.
 */
public class AppAplication extends Application {

    private static AppAplication mAplication;
    private static MySqliteDatabeHelper helper;

    @Override
    public void onCreate() {
        super.onCreate();
        mAplication = this;
        initImageLoader(getApplicationContext());
    }

    public static AppAplication getAppAplication() {
        return mAplication;
    }

    public static MySqliteDatabeHelper getSqliteHelper() {
        if (helper == null) {
            helper = new MySqliteDatabeHelper(mAplication);
        }

        return helper;
    }

    @Override
    public void onTerminate() {
        if (helper != null) {
            helper.close();
        }
        super.onTerminate();
    }

    /**
     * 初始化异步加载图片框架
     * @param context
     */

    public static void initImageLoader(Context context) {
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, "weijingxuan/cache");
        Log.d("cacheDir", cacheDir.getPath());
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(context)
                .threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .diskCache(new UnlimitedDiskCache(cacheDir))
                .writeDebugLogs() /// TODO: 2016/7/31 在发布时将此选项取消 
                .build();
        ImageLoader.getInstance().init(configuration);
    }
}
