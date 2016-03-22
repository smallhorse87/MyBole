package com.zhitian.mybole.api;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import com.zhitian.mybole.entity.ActivityInfo;
import com.zhitian.mybole.entity.MerchantInfo;
import com.zhitian.mybole.utils.TDevice;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class BoleApi {
    public static final int DEF_PAGE_SIZE = TDevice.getPageSize();

    public static void getTxtPasscodeForLogin(String telnum, JsonHttpResponseHandler jsonhandler) {
        RequestParams params = new RequestParams();
        params.put("mob", telnum);
        params.put("type", "merchantLogin");
        params.put("captchaType", "sms");
        ApiHttpClient.post("system/logincaptcha", params, jsonhandler);
    }

    public static void getVoicePasscodeForLogin(String telnum, JsonHttpResponseHandler jsonhandler) {
        RequestParams params = new RequestParams();
        params.put("mob", telnum);
        params.put("type", "merchantLogin");
        params.put("captchaType", "voice");
        ApiHttpClient.post("system/logincaptcha", params, jsonhandler);
    }

    public static void loginWithCaptcha(String telnum, String captcha, JsonHttpResponseHandler jsonhandler) {
        RequestParams params = new RequestParams();
        params.put("mob", telnum);
        params.put("captcha", captcha);
        ApiHttpClient.post("merchant/login", params, jsonhandler);
    }

    public static void submitMerchantInfo(MerchantInfo info, JsonHttpResponseHandler jsonhandler) {
        RequestParams params = new RequestParams();

        params.put("avatar",       info.getAvatar().getImgId());
        params.put("name",         info.getName());
        params.put("category",     info.getCategory());

        String districtRegionId =  info.getRegionIds().get(info.getRegionIds().size() - 1);
        params.put("region",       districtRegionId);
        params.put("address",      info.getAddress());
        params.put("tel",          info.getTel());
        params.put("wechat",       info.getWechat());
        params.put("wechatQrcode", info.getWechatQrcode().getImgId());
        params.put("longtitude",   info.getLongtitude());
        params.put("latitude",     info.getLatitude());

        ApiHttpClient.post("merchant/edit", params, jsonhandler);
    }

    public static void updateImage(Uri uri, JsonHttpResponseHandler jsonhandler) {
        File file = new File(uri.getPath());

        if(file.exists() && file.length() > 0)
        {
            try{
                RequestParams params = new RequestParams();
                params.put("FileData", file);
                ApiHttpClient.post("system/upimg", params, jsonhandler);
            } catch (Exception e) {

            }
        }
    }

    public static void getSystemConfig(JsonHttpResponseHandler jsonhandler) {
        ApiHttpClient.get("system/config", null, jsonhandler);
    }

    public static void downloadRegionXml(String downloadUrl, BinaryHttpResponseHandler binhandler) {
        ApiHttpClient.getWithFullUrl(downloadUrl, null, binhandler);
    }

    public static void getActivityList(int pagenum, JsonHttpResponseHandler jsonhandler) {
        RequestParams params = new RequestParams();

        params.put("p",   Integer.valueOf(pagenum).toString());
        params.put("sz",  "15");//stony

        ApiHttpClient.post("merchant/myActivity", params, jsonhandler);
    }

    public static void getTotalStat(JsonHttpResponseHandler jsonhandler) {
        ApiHttpClient.get("merchant/myStatistics", null, jsonhandler);
    }

    public static void getDetailedStat(String activityId, JsonHttpResponseHandler jsonhandler) {
        RequestParams params = new RequestParams();
        params.put("activityId",  activityId);

        ApiHttpClient.get("merchant/activityStatistics", params, jsonhandler);
    }

    public static void getPlanlist(String activityId, JsonHttpResponseHandler jsonhandler) {
        RequestParams params = new RequestParams();
        params.put("activityId",  activityId);

        ApiHttpClient.get("merchant/planList", params, jsonhandler);
    }

    public static void getRankList(String activityId, JsonHttpResponseHandler jsonhandler) {
        RequestParams params = new RequestParams();
        params.put("activityId",  activityId);

        ApiHttpClient.get("merchant/activityRanking", params, jsonhandler);
    }

    public static void getGameList(JsonHttpResponseHandler jsonhandler) {
        ApiHttpClient.get("merchant/gameList", null, jsonhandler);
    }

    /**
     * 获取新闻列表
     *
     * @param catalog 类别 （1，2，3）
     * @param page    第几页
     * @param handler
     */
    public static void getNewsList(int catalog, int page,
                                   AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        // params.put("access_token", "");
        params.put("catalog", catalog);
        params.put("pageIndex", page);
        params.put("pageSize", DEF_PAGE_SIZE);
        params.put("dataType", "json");
        ApiHttpClient.get("action/api/news_list", params, handler);
    }

    public static void getBlogList(String type, int pageIndex,
                                   AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("type", type);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", DEF_PAGE_SIZE);
        ApiHttpClient.get("action/api/blog_list", params, handler);
    }

    public static void getPostList(int catalog, int page,
                                   AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        // params.put("access_token", "");
        params.put("catalog", catalog);
        params.put("pageIndex", page);
        params.put("pageSize", DEF_PAGE_SIZE);
        params.put("dataType", "json");
        ApiHttpClient.get("action/api/post_list", params, handler);
    }

    public static void getPostListByTag(String tag, int page,
                                        AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("tag", tag);
        params.put("pageIndex", page);
        params.put("pageSize", DEF_PAGE_SIZE);
        ApiHttpClient.get("action/api/post_list", params, handler);
    }

    public static void getTweetList(int uid, int page,
                                    AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        // params.put("access_token", "");
        params.put("uid", uid);
        params.put("pageIndex", page);
        params.put("pageSize", DEF_PAGE_SIZE);
        // params.put("dataType", "json");
        ApiHttpClient.get("action/api/tweet_list", params, handler);
    }

    public static void pubLikeTweet(int uid, int tweetId, int authorId,
                                    AsyncHttpResponseHandler handler) {

        RequestParams params = new RequestParams();
        params.put("tweetid", tweetId);
        params.put("uid", uid);
        params.put("ownerOfTweet", authorId);
        ApiHttpClient.post("action/api/tweet_like", params, handler);
    }

    public static void pubUnLikeTweet(int uid, int tweetId, int authorId,
                                      AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("tweetid", tweetId);
        params.put("uid", uid);
        params.put("ownerOfTweet", authorId);
        ApiHttpClient.post("action/api/tweet_unlike", params, handler);
    }

    public static void getTweetLikeList(int tweetId, int page,
                                        AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("tweetid", tweetId);
        params.put("pageIndex", page);
        params.put("pageSize", DEF_PAGE_SIZE);
        ApiHttpClient.get("action/api/tweet_like_list", params, handler);

    }

    public static void getActiveList(int uid, int catalog, int page,
                                     AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("catalog", catalog);
        params.put("pageIndex", page);
        params.put("pageSize", DEF_PAGE_SIZE);
        ApiHttpClient.get("action/api/active_list", params, handler);
    }

    public static void getFriendList(int uid, int relation, int page,
                                     AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("relation", relation);
        params.put("pageIndex", page);
        params.put("pageSize", DEF_PAGE_SIZE);
        ApiHttpClient.get("action/api/friends_list", params, handler);
    }

    public static void getAllFriends(int uid, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("all", 1);
        ApiHttpClient.get("action/api/friends_list", params, handler);
    }

    public static void getFavoriteList(int uid, int type, int page,
                                       AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("type", type);
        params.put("pageIndex", page);
        params.put("pageSize", DEF_PAGE_SIZE);
        ApiHttpClient.get("action/api/favorite_list", params, handler);
    }

    public static void getSoftwareCatalogList(int tag,
                                              AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams("tag", tag);
        ApiHttpClient.get("action/api/softwarecatalog_list", params, handler);
    }

    public static void getSoftwareTagList(int searchTag, int page,
                                          AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("searchTag", searchTag);
        params.put("pageIndex", page);
        params.put("pageSize", DEF_PAGE_SIZE);
        ApiHttpClient.get("action/api/softwaretag_list", params, handler);
    }

    public static void getSoftwareList(String searchTag, int page,
                                       AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("searchTag", searchTag);
        params.put("pageIndex", page);
        params.put("pageSize", DEF_PAGE_SIZE);
        ApiHttpClient.get("action/api/software_list", params, handler);
    }

    /**
     * 获取评论列表
     *
     * @param id
     * @param catalog 1新闻 2帖子 3动弹 4动态
     * @param page
     * @param handler
     */
    public static void getCommentList(int id, int catalog, int page,
                                      AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("catalog", catalog);
        params.put("id", id);
        params.put("pageIndex", page);
        params.put("pageSize", DEF_PAGE_SIZE);
        ApiHttpClient.get("action/api/comment_list", params, handler);
    }

    public static void getBlogCommentList(int id, int page,
                                          AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("id", id);
        params.put("pageIndex", page);
        params.put("pageSize", DEF_PAGE_SIZE);
        ApiHttpClient.get("action/api/blogcomment_list", params, handler);
    }

    public static void getUserInformation(int uid, int hisuid, String hisname,
                                          int pageIndex, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("hisuid", hisuid);
        params.put("hisname", hisname);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", DEF_PAGE_SIZE);
        ApiHttpClient.get("action/api/user_information", params, handler);
    }

    @SuppressWarnings("deprecation")
    public static void getUserBlogList(int authoruid, final String authorname,
                                       final int uid, final int pageIndex, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("authoruid", authoruid);
        params.put("authorname", URLEncoder.encode(authorname));
        params.put("uid", uid);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", DEF_PAGE_SIZE);
        ApiHttpClient.get("action/api/userblog_list", params, handler);
    }

    public static void updateRelation(int uid, int hisuid, int newrelation,
                                      AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("hisuid", hisuid);
        params.put("newrelation", newrelation);
        ApiHttpClient.post("action/api/user_updaterelation", params, handler);
    }

    public static void getMyInformation(int uid,
                                        AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        ApiHttpClient.post("action/api/my_information", params, handler);
    }

    public static void getNewsDetail(int id, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams("id", id);
        ApiHttpClient.get("action/api/news_detail", params, handler);
    }

    public static void getBlogDetail(int id, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams("id", id);
        ApiHttpClient.get("action/api/blog_detail", params, handler);
    }

    public static void getSoftwareDetail(String ident,
                                         AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams("ident", ident);
        ApiHttpClient.get("action/api/software_detail", params, handler);
    }

    public static void getPostDetail(int id, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams("id", id);
        ApiHttpClient.get("action/api/post_detail", params, handler);
    }

    public static void getTweetDetail(int id, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams("id", id);
        ApiHttpClient.get("action/api/tweet_detail", params, handler);
    }

    public static void publicComment(int catalog, int id, int uid,
                                     String content, int isPostToMyZone, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("catalog", catalog);
        params.put("id", id);
        params.put("uid", uid);
        params.put("content", content);
        params.put("isPostToMyZone", isPostToMyZone);
        ApiHttpClient.post("action/api/comment_pub", params, handler);
    }

    public static void replyComment(int id, int catalog, int replyid,
                                    int authorid, int uid, String content,
                                    AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("catalog", catalog);
        params.put("id", id);
        params.put("uid", uid);
        params.put("content", content);
        params.put("replyid", replyid);
        params.put("authorid", authorid);
        ApiHttpClient.post("action/api/comment_reply", params, handler);
    }

    public static void publicBlogComment(int blog, int uid, String content,
                                         AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("blog", blog);
        params.put("uid", uid);
        params.put("content", content);
        ApiHttpClient.post("action/api/blogcomment_pub", params, handler);
    }

    public static void replyBlogComment(int blog, int uid, String content,
                                        int reply_id, int objuid, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("blog", blog);
        params.put("uid", uid);
        params.put("content", content);
        params.put("reply_id", reply_id);
        params.put("objuid", objuid);
        ApiHttpClient.post("action/api/blogcomment_pub", params, handler);
    }

    public static void deleteTweet(int uid, int tweetid,
                                   AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("tweetid", tweetid);
        ApiHttpClient.post("action/api/tweet_delete", params, handler);
    }

    public static void deleteComment(int id, int catalog, int replyid,
                                     int authorid, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("id", id);
        params.put("catalog", catalog);
        params.put("replyid", replyid);
        params.put("authorid", authorid);
        ApiHttpClient.post("action/api/comment_delete", params, handler);
    }

    public static void deleteBlog(int uid, int authoruid, int id,
                                  AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("authoruid", authoruid);
        params.put("id", id);
        ApiHttpClient.post("action/api/userblog_delete", params, handler);
    }

    public static void deleteBlogComment(int uid, int blogid, int replyid,
                                         int authorid, int owneruid, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("blogid", blogid);
        params.put("replyid", replyid);
        params.put("authorid", authorid);
        params.put("owneruid", owneruid);
        ApiHttpClient.post("action/api/blogcomment_delete", params, handler);
    }

    /**
     * 用户添加收藏
     *
     * @param uid   用户UID
     * @param objid 比如是新闻ID 或者问答ID 或者动弹ID
     * @param type  1:软件 2:话题 3:博客 4:新闻 5:代码
     */
    public static void addFavorite(int uid, int objid, int type,
                                   AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("objid", objid);
        params.put("type", type);
        ApiHttpClient.post("action/api/favorite_add", params, handler);
    }

    public static void delFavorite(int uid, int objid, int type,
                                   AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("objid", objid);
        params.put("type", type);
        ApiHttpClient.post("action/api/favorite_delete", params, handler);
    }

    public static void getSearchList(String catalog, String content,
                                     int pageIndex, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("catalog", catalog);
        params.put("content", content);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", DEF_PAGE_SIZE);
        ApiHttpClient.get("action/api/search_list", params, handler);
    }


    public static void publicMessage(int uid, int receiver, String content,
                                     AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("receiver", receiver);
        params.put("content", content);
        ApiHttpClient.post("action/api/message_pub", params, handler);
    }

    public static void deleteMessage(int uid, int friendid,
                                     AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("friendid", friendid);
        ApiHttpClient.post("action/api/message_delete", params, handler);
    }

    public static void forwardMessage(int uid, String receiverName,
                                      String content, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("receiverName", receiverName);
        params.put("content", content);
        ApiHttpClient.post("action/api/message_pub", params, handler);
    }

    public static void getMessageList(int uid, int pageIndex,
                                      AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", DEF_PAGE_SIZE);
        ApiHttpClient.get("action/api/message_list", params, handler);
    }

    public static void updatePortrait(int uid, File portrait,
                                      AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        Map<String, File> files = new HashMap<String, File>();
        files.put("portrait", portrait);
        ApiHttpClient.post("action/api/portrait_update", params, handler);
    }

    public static void getNotices(int uid, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        ApiHttpClient.post("action/api/user_notice", params, handler);
    }

    public static void clearNotice(int uid, int type,
                                   AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("type", type);
        ApiHttpClient.post("action/api/notice_clear", params, handler);
    }

    public static void getUserNotice(int uid, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        ApiHttpClient.post("action/api/user_notice", params, handler);
    }
}
