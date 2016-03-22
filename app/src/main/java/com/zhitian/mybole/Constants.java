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

    public static final int APPCFG_ACTIVIT_RULE_CHAR_COUNT = 1000;
    public static final int APPCFG_ACTIVIT_NAME_CHAR_COUNT = 40;
    public static final int APPCFG_ACTIVIT_MAX_PEORID = 7;
    public static final int APPCFG_ACTIVIT_DEFAULT_PEORID = 3;
}
