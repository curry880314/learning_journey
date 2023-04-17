package bean;
import javafx.beans.property.SimpleStringProperty;

public class Module {
    private final SimpleStringProperty moID;
    private final SimpleStringProperty moName;
    private final SimpleStringProperty moTime;
    private final SimpleStringProperty moPosition;
    private final SimpleStringProperty username;
    public Module(String moID,
                  String moName,
                  String moTime,
                  String moPosition,
                  String username) {
        this.moID =new SimpleStringProperty(moID);
        this.moName = new SimpleStringProperty(moName);
        this.moTime = new SimpleStringProperty(moTime);
        this.moPosition = new SimpleStringProperty(moPosition);
        this.username=new SimpleStringProperty(username);
    }

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getMoID() {
        return moID.get();
    }

    public SimpleStringProperty MoIDProperty() {
        return moID;
    }

    public void setMoID(String moID) {
        this.moID.set(moID);
    }

    public String getMoTime() {
        return moTime.get();
    }

    public SimpleStringProperty roTimeStartProperty() {
        return moTime;
    }

    public void setMoTime(String moTime) {
        this.moTime.set(moTime);
    }

    public String getMoName() {
        return moName.get();
    }

    public SimpleStringProperty MoNameProperty() {
        return moName;
    }

    public void setMoName(String moName) {
        this.moName.set(moName);
    }

    public String getMoPosition() {
        return moPosition.get();
    }

    public SimpleStringProperty MoTimeEndProperty() {
        return moPosition;
    }

    public void setMoPosition(String moPosition) {
        this.moPosition.set(moPosition);
    }

    @Override
    public String toString() {
        return "Module{" +
                "moID=" + moID +
                ", moName=" + moName +
                ", moTime=" + moTime +
                ", moPosition=" + moPosition +
                '}';
    }
}
