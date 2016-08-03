package com.lisen.android.weijingxuan.util;

import com.lisen.android.weijingxuan.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * Created by Administrator on 2016/7/31.
 */
public class Options {

    /**
     * 加载列表时用到的图片配置
     * @return
     */
    public static DisplayImageOptions getContentListOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .showImageOnLoading(R.drawable.downloading_content_list)
                .showImageForEmptyUri(R.drawable.error_content_list)
                .showImageOnFail(R.drawable.error_content_list)
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .resetViewBeforeLoading(true)
                .displayer(new FadeInBitmapDisplayer(100))
                .build();
        return options;
    }
}
