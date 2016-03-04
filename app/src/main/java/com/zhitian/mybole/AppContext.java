package com.zhitian.mybole;

import android.content.SharedPreferences.Editor;

import java.util.Set;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;

/**
 * 全局应用程序类：用于保存和调用全局应用配置及访问网络数据
 *
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class AppContext extends BaseApplication {

    private static final String KEY_SOFTKEYBOARD_HEIGHT = "KEY_SOFTKEYBOARD_HEIGHT";
    private static final String KEY_LOAD_IMAGE = "KEY_LOAD_IMAGE";
    private static final String KEY_NOTIFICATION_SOUND = "KEY_NOTIFICATION_SOUND";
    private static final String LAST_QUESTION_CATEGORY_IDX = "LAST_QUESTION_CATEGORY_IDX";
    private static final String KEY_DAILY_ENGLISH = "KEY_DAILY_ENGLISH";
    private static final String KEY_GET_LAST_DAILY_ENG = "KEY_GET_LAST_DAILY_ENG";
    private static final String KEY_NOTIFICATION_DISABLE_WHEN_EXIT = "KEY_NOTIFICATION_DISABLE_WHEN_EXIT";
    private static final String KEY_TWEET_DRAFT = "key_tweet_draft";
    private static final String KEY_QUESTION_TITLE_DRAFT = "key_question_title_draft";
    private static final String KEY_QUESTION_CONTENT_DRAFT = "key_question_content_draft";
    private static final String KEY_QUESTION_TYPE_DRAFT = "key_question_type_draft";
    private static final String KEY_QUESTION_LMK_DRAFT = "key_question_lmk_draft";
    private static final String KEY_NEWS_READED = "key_readed_news_2";
    private static final String KEY_QUESTION_READED = "key_readed_question_2";
    private static final String KEY_BLOG_READED = "key_readed_blog_2";
    private static final String KEY_NOTICE_ATME_COUNT = "key_notice_atme_count";
    private static final String KEY_NOTICE_MESSAGE_COUNT = "key_notice_message_count";
    private static final String KEY_NOTICE_REVIEW_COUNT = "key_notice_review_count";
    private static final String KEY_NOTICE_NEWFANS_COUNT = "key_notice_newfans_count";
    private static final String KEY_LOGIN_ID = "key_login_id_2";
    private static final String KEY_COOKIE = "key_cookie";
    private static final String KEY_APP_ID = "key_app_id";
    private static final String KEY_DETAIL_FONT_SIZE = "key_font_size";

    private static Set<String> mReadedNewsIds, mReadedQuestionIds, mReadedBlogIds; //已读IDS

    private static AppContext instance;

    @Override
    public void onCreate() {
        super.onCreate();
        // 注册App异常崩溃处理器
        // Thread.setDefaultUncaughtExceptionHandler(AppException.getAppExceptionHandler());

        instance = this;

        AsyncHttpClient client = new AsyncHttpClient();
//        PersistentCookieStore myCookieStore = new PersistentCookieStore(this);
//        client.setCookieStore(myCookieStore);
//        ApiHttpClient.setHttpClient(client);
//        ApiHttpClient.setCookie(ApiHttpClient.getCookie(this));

    }

    /**
     * 获取登录用户id
     *
     * @return
     */
    public static int getLoginUid() {
        return getPreferences().getInt(KEY_LOGIN_ID, 0);
    }

    public static void setLoginUid(int uid) {
        Editor editor = getPreferences().edit();
        editor.putInt(KEY_LOGIN_ID, uid);
        apply(editor);
    }


    public static void setProperty(String key, String value) {
        Editor editor = getPreferences().edit();
        editor.putString(key, value);
        apply(editor);
    }

    public static String getProperty(String key) {
        return getPreferences().getString(key, null);
    }

    public static void removeProperty(String... keys) {
        for (String key : keys) {
            Editor editor = getPreferences().edit();
            editor.putString(key, null);
            apply(editor);
        }
    }

    public static AppContext instance() {
        return instance;
    }

    //stony debug
    public String getPackageInfo(){
        return "stony";
    }

    //stony debug
    public String getAppId(){
        return "stony";
    }

}
