package org.urturn.com.urturn;

public class GroupModel {
    String groupName;
    String groupDesc;
     String groupImageUrl;
    String cretedBy;
    String groupId;

    public  GroupModel()
    {

    }
    public String getCretedBy() {
        return cretedBy;
    }

    public void setCretedBy(String cretedBy) {
        this.cretedBy = cretedBy;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDesc() {
        return groupDesc;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }

    public String getGroupImageUrl() {
        return groupImageUrl;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setGroupImageUrl(String groupImageUrl) {
        this.groupImageUrl = groupImageUrl;
    }

    public GroupModel(String groupName, String groupDesc, String groupImageUrl,String cretedBy,String groupId) {
        this.groupName = groupName;
        this.groupDesc = groupDesc;
        this.groupImageUrl = groupImageUrl;
        this.cretedBy=cretedBy;
        this.groupId=groupId;
    }
}
