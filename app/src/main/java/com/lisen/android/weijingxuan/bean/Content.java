package com.lisen.android.weijingxuan.bean;

/**
 * Created by Administrator on 2016/7/30.
 */
public class Content {
    /**
     *  {
     "id": "579c1f5f6e36b8ec34f58379",
     "typeName": "推荐",
     "title": "2016年7月25～29日喊单盈利102点",
     "contentImg": "http://mmbiz.qpic.cn/mmbiz/AOmAicF4dKuibsASGI28TbgTGmLFIITKr24hgTjvhG3ibuFicOicUbiam0KagPno78ibRv0hPxY8QeaEOoOn83N5aWmoA/0?wx_fmt=jpeg",
     "userLogo": "http://mmbiz.qpic.cn/mmbiz/AOmAicF4dKuibBQXr7OMvmT3RQL6bCh1R6NOj0GKbbia9WsZeLUKkGOoeRicn0pyb4Th3B4D9w5hdFbF9hHWMiaicsuQ/0?wx_fmt=png",
     "userName": "fmforex",
     "date": "2016-07-30 03:11:34",
     "typeId": "1",
     "url": "http://mp.weixin.qq.com/s?__biz=MzA4NTMwODQxOQ==&mid=2650328371&idx=1&sn=8ca4505456386a2bb4d46ef21a032509#rd",
     "weixinNum": "fmforexcn",
     "userLogo_code": "http://open.weixin.qq.com/qr/code/?username=fmforexcn"
     }
     */

    private String id;
    private String typeName;
    private String title;
    private String contentImg;
    private String userLogo;
    private String userName;
    private String date;
    private String typeId;
    private String url;
    private String weixinNum;
    private String userLogo_code;

    public String getId() {
        return id;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getTitle() {
        return title;
    }

    public String getContentImg() {
        return contentImg;
    }

    public String getUserLogo() {
        return userLogo;
    }

    public String getUserName() {
        return userName;
    }

    public String getDate() {
        return date;
    }

    public String getTypeId() {
        return typeId;
    }

    public String getUrl() {
        return url;
    }

    public String getWeixinNum() {
        return weixinNum;
    }

    public String getUserLogo_code() {
        return userLogo_code;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContentImg(String contentImg) {
        this.contentImg = contentImg;
    }

    public void setUserLogo(String userLogo) {
        this.userLogo = userLogo;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setWeixinNum(String weixinNum) {
        this.weixinNum = weixinNum;
    }

    public void setUserLogo_code(String userLogo_code) {
        this.userLogo_code = userLogo_code;
    }

    @Override
    public String toString() {
        return "Content{" +
                "id='" + id + '\'' +
                ", typeName='" + typeName + '\'' +
                ", title='" + title + '\'' +
                ", contentImg='" + contentImg + '\'' +
                ", userLogo='" + userLogo + '\'' +
                ", userName='" + userName + '\'' +
                ", date='" + date + '\'' +
                ", typeId='" + typeId + '\'' +
                ", url='" + url + '\'' +
                ", weixinNum='" + weixinNum + '\'' +
                ", userLogo_code='" + userLogo_code + '\'' +
                '}';
    }
}
