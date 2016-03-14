package com.zhitian.mybole.entity;

import java.util.List;

/**
 * Created by chenxiaosong on 16/3/14.
 */
public class GameInfo {
    private String gameId;
    private String name;
    private String previewLink;
    /**
     * imgId : 593
     * thumbnailImg : {"width":"174","height":"180","url":"http://m.bodimall.com/Uploads/Picture/2015-12-11/thumbnail_566a281d92a74.jpg"}
     * middleImg : {"width":"207","height":"215","url":"http://m.bodimall.com/Uploads/Picture/2015-12-11/middle_566a281d92a74.jpg"}
     * largeImg : {"width":"207","height":"215","url":"http://m.bodimall.com/Uploads/Picture/2015-12-11/large_566a281d92a74.jpg"}
     * originalImg : {"width":"207","height":"215","url":"http://m.bodimall.com/Uploads/Picture/2015-12-11/566a281d92a74.jpg"}
     */

    private List<ImageSetInfo> imgs;

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPreviewLink(String previewLink) {
        this.previewLink = previewLink;
    }

    public void setImgs(List<ImageSetInfo> imgs) {
        this.imgs = imgs;
    }

    public String getGameId() {
        return gameId;
    }

    public String getName() {
        return name;
    }

    public String getPreviewLink() {
        return previewLink;
    }

    public List<ImageSetInfo> getImgs() {
        return imgs;
    }

}