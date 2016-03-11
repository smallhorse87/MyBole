package com.zhitian.mybole;

import android.os.Environment;

/**
 * Created by sven on 2016/1/6.

 */
public class Constants {
    public static final String EXTERNAL_STORAGE_DIRECTORY = Environment.getExternalStorageDirectory().getPath();
    /**

     */
    public static final String FILENAME = EXTERNAL_STORAGE_DIRECTORY + "/crop.png";

    public static final String PHOTONAME = EXTERNAL_STORAGE_DIRECTORY+ "/aa.png";

    public static final int PICK_PHOTO = 2;
    public static final int CROP_BEAUTY = 3;
}
