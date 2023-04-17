package bean;

import javafx.beans.property.SimpleStringProperty;

public class Achievement {
    /** ID（主键） **/
    private final SimpleStringProperty achID;
    /** 姓名 **/
    private final SimpleStringProperty achName;
    /** 性别**/
    private final SimpleStringProperty achLevel;
    /** 出生日期**/
    private final SimpleStringProperty achTime;
    /** 所在专业**/
    private final SimpleStringProperty achMajor;

    public Achievement(String achID,
                       String achName,
                       String achLevel,
                       String achTime,
                       String achMajor) {
        this.achID = new SimpleStringProperty(achID);
        this.achName = new SimpleStringProperty(achName);
        this.achLevel = new SimpleStringProperty(achLevel);
        this.achTime = new SimpleStringProperty(achTime);
        this.achMajor = new SimpleStringProperty(achMajor);
    }


    public String getAchID() {
        return achID.get();
    }

    public SimpleStringProperty achIDProperty() {
        return achID;
    }

    public void setAchID(String achID) {
        this.achID.set(achID);
    }

    public String getAchName() {
        return achName.get();
    }

    public SimpleStringProperty achNameProperty() {
        return achName;
    }

    public void setAchName(String achName) {
        this.achName.set(achName);
    }

    public String getAchLevel() {
        return achLevel.get();
    }

    public SimpleStringProperty achLevelProperty() {
        return achLevel;
    }

    public void setAchLevel(String achLevel) {
        this.achLevel.set(achLevel);
    }

    public String getAchTime() {
        return achTime.get();
    }

    public SimpleStringProperty achTimeProperty() {
        return achTime;
    }

    public void setAchTime(String achTime) {
        this.achTime.set(achTime);
    }

    public String getAchMajor() {
        return achMajor.get();
    }

    public SimpleStringProperty achMajorProperty() {
        return achMajor;
    }

    public void setAchMajor(String achMajor) {
        this.achMajor.set(achMajor);
    }

    @Override
    public String toString() {
        return "achievement{" +
                "achID=" + achID +
                ", achName=" + achName +
                ", achLevel=" + achLevel +
                ", achTime=" + achTime +
                ", achMajor=" + achMajor +
                '}';
    }
}
