package com.zhitian.mybole;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import com.zhitian.mybole.api.ApiHttpClient;
import com.zhitian.mybole.base.BaseApplication;

/**
 * 全局应用程序类：用于保存和调用全局应用配置及访问网络数据
 *
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class AppContext extends BaseApplication {
    private static String lastToast = "";
    private static long lastToastTime;

    private static AppContext instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        AsyncHttpClient client = new AsyncHttpClient();
        PersistentCookieStore myCookieStore = new PersistentCookieStore(this);
        client.setCookieStore(myCookieStore);
        ApiHttpClient.setHttpClient(client);
    }

    public static AppContext instance() {
        return instance;
    }

    public static void showToast(int message) {
        showToast(message, Toast.LENGTH_LONG, 0);
    }

    public static void showToast(String message) {
        showToast(message, Toast.LENGTH_LONG, 0, Gravity.FILL_HORIZONTAL
                | Gravity.TOP);
    }

    public static void showToast(int message, int icon) {
        showToast(message, Toast.LENGTH_LONG, icon);
    }

    public static void showToast(String message, int icon) {
        showToast(message, Toast.LENGTH_LONG, icon, Gravity.FILL_HORIZONTAL
                | Gravity.TOP);
    }

    public static void showToastShort(int message) {
        showToast(message, Toast.LENGTH_SHORT, 0);
    }

    public static void showToastShort(String message) {
        showToast(message, Toast.LENGTH_SHORT, 0, Gravity.FILL_HORIZONTAL
                | Gravity.TOP);
    }

    public static void showToastShort(int message, Object... args) {
        showToast(message, Toast.LENGTH_SHORT, 0, Gravity.FILL_HORIZONTAL
                | Gravity.TOP, args);
    }

    public static void showToast(int message, int duration, int icon) {
        showToast(message, duration, icon, Gravity.FILL_HORIZONTAL
                | Gravity.TOP);
    }

    public static void showToast(int message, int duration, int icon,
                                 int gravity) {
        showToast(context().getString(message), duration, icon, gravity);
    }

    public static void showToast(int message, int duration, int icon,
                                 int gravity, Object... args) {
        showToast(context().getString(message, args), duration, icon, gravity);
    }

    public static void showToast(String message, int duration, int icon,
                                 int gravity) {
        if (message != null && !message.equalsIgnoreCase("")) {
            long time = System.currentTimeMillis();
            if (!message.equalsIgnoreCase(lastToast)
                    || Math.abs(time - lastToastTime) > 2000) {

                View view = LayoutInflater.from(context()).inflate(
                        R.layout.v2_view_toast, null);
                ((TextView) view.findViewById(R.id.title_tv)).setText(message);
                if (icon != 0) {
                    ((ImageView) view.findViewById(R.id.icon_iv))
                            .setImageResource(icon);
                    ((ImageView) view.findViewById(R.id.icon_iv))
                            .setVisibility(View.VISIBLE);
                }
                Toast toast = new Toast(context());
                toast.setView(view);
                //toast.setGravity(gravity, 0 , TDevice.getActionBarHeight(context()));
                // getToastMarignBottom()
                // toast.setGravity(Gravity.TOP|Gravity.LEFT,0 ,0);
                toast.setDuration(duration);
                toast.show();

                lastToast = message;
                lastToastTime = System.currentTimeMillis();
            }
        }
    }
}
