package com.zhitian.mybole.entity;

import java.util.List;

/**
 * Created by chenxiaosong on 16/3/11.
 */
public class ConfigInfo {
    /**
     * updateAt : 1335887899
     * url : http://m.bodimall.com/Uploads/regions.xml
     */

    private RegionsEntity regions;
    /**
     * category : ["服饰/时尚","餐饮/食品","互联网/IT","零售/电商/微商","金融/银行","地产/家居","教育/培训","媒体/广告","旅游/休闲","其他行业","个人/工作室"]
     * regions : {"updateAt":"1335887899","url":"http://m.bodimall.com/Uploads/regions.xml"}
     * about : 关于搏乐
     * agreement : 用户协议
     * systemUrl : {"square":"http://m.bodimall.com/Wx/Index/index.html","myActivity":"http://m.bodimall.com/Wx/My/activity.html","myPrize":"http://m.bodimall.com/Wx/My/prize.html"}
     * activityIdRule : ["http://m.bodimall.com/Wx/activity/index/id/%28%5Cd%2B%29.html","http://m.bodimall.com/Wx/activity/playgame/id/%28%5Cd%2B%29.html"]
     * prizeImagesDownloadUrl : http://m.bodimall.com/Public/html/template.html
     */

    private String about;
    private String agreement;
    /**
     * square : http://m.bodimall.com/Wx/Index/index.html
     * myActivity : http://m.bodimall.com/Wx/My/activity.html
     * myPrize : http://m.bodimall.com/Wx/My/prize.html
     */

    private SystemUrlEntity systemUrl;
    private String prizeImagesDownloadUrl;
    private List<String> category;
    private List<String> activityIdRule;

    public void setRegions(RegionsEntity regions) {
        this.regions = regions;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public void setAgreement(String agreement) {
        this.agreement = agreement;
    }

    public void setSystemUrl(SystemUrlEntity systemUrl) {
        this.systemUrl = systemUrl;
    }

    public void setPrizeImagesDownloadUrl(String prizeImagesDownloadUrl) {
        this.prizeImagesDownloadUrl = prizeImagesDownloadUrl;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public void setActivityIdRule(List<String> activityIdRule) {
        this.activityIdRule = activityIdRule;
    }

    public RegionsEntity getRegions() {
        return regions;
    }

    public String getAbout() {
        return about;
    }

    public String getAgreement() {
        return agreement;
    }

    public SystemUrlEntity getSystemUrl() {
        return systemUrl;
    }

    public String getPrizeImagesDownloadUrl() {
        return prizeImagesDownloadUrl;
    }

    public List<String> getCategory() {
        return category;
    }

    public List<String> getActivityIdRule() {
        return activityIdRule;
    }

    public static class RegionsEntity {
        private String updateAt;
        private String url;

        public void setUpdateAt(String updateAt) {
            this.updateAt = updateAt;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUpdateAt() {
            return updateAt;
        }

        public String getUrl() {
            return url;
        }
    }

    public static class SystemUrlEntity {
        private String square;
        private String myActivity;
        private String myPrize;

        public void setSquare(String square) {
            this.square = square;
        }

        public void setMyActivity(String myActivity) {
            this.myActivity = myActivity;
        }

        public void setMyPrize(String myPrize) {
            this.myPrize = myPrize;
        }

        public String getSquare() {
            return square;
        }

        public String getMyActivity() {
            return myActivity;
        }

        public String getMyPrize() {
            return myPrize;
        }
    }
}
