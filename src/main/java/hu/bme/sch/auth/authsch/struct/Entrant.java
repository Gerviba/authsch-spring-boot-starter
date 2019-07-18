package hu.bme.sch.auth.authsch.struct;

import java.io.Serializable;

public class Entrant implements Serializable {

    private static final long serialVersionUID = 461763126385555164L;
    
    private final int groupId;
    private final String groupName;
    private final String entrantType;

    public Entrant(int groupId, String groupName, String entrantType) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.entrantType = entrantType;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getEntrantType() {
        return entrantType;
    }

    @Override
    public String toString() {
        return "Entrant [groupId=" + groupId + ", groupName=" + groupName + ", entrantType=" + entrantType + "]";
    }

}
