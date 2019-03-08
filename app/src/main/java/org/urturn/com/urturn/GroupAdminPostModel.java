package org.urturn.com.urturn;

public class GroupAdminPostModel {
    String url,desc,timestamp;

    public GroupAdminPostModel()
    {

    }
    public GroupAdminPostModel(String url, String desc, String timestamp) {
        this.url = url;
        this.desc = desc;
        this.timestamp = timestamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
