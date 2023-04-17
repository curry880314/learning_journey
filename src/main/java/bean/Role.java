package bean;

import javafx.beans.property.SimpleStringProperty;

public class Role {
    private final SimpleStringProperty roID;
    private final SimpleStringProperty roName;
    private final SimpleStringProperty roTimeStart;
    private final SimpleStringProperty roTimeEnd;
    private final SimpleStringProperty username;


    public Role(String roID,
                String roName,
                String roTimeStart,
                String roTimeEnd,
                String username) {
        this.roID =new SimpleStringProperty(roID);
        this.roName = new SimpleStringProperty(roName);
        this.roTimeStart = new SimpleStringProperty(roTimeStart);
        this.roTimeEnd = new SimpleStringProperty(roTimeEnd);
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

    public String getRoID() {
        return roID.get();
    }

    public SimpleStringProperty roIDProperty() {
        return roID;
    }

    public void setRoID(String roID) {
        this.roID.set(roID);
    }

    public String getRoTimeStart() {
        return roTimeStart.get();
    }

    public SimpleStringProperty roTimeStartProperty() {
        return roTimeStart;
    }

    public void setRoTimeStart(String roTimeStart) {
        this.roTimeStart.set(roTimeStart);
    }

    public String getRoName() {
        return roName.get();
    }

    public SimpleStringProperty roNameProperty() {
        return roName;
    }

    public void setRoName(String roName) {
        this.roName.set(roName);
    }

    public String getRoTimeEnd() {
        return roTimeEnd.get();
    }

    public SimpleStringProperty roTimeEndProperty() {
        return roTimeEnd;
    }

    public void setRoTimeEnd(String roTimeEnd) {
        this.roTimeEnd.set(roTimeEnd);
    }

    @Override
    public String toString() {
        return "Role{" +
                "roID=" + roID +
                ", roName=" + roName +
                ", roTimeStart=" + roTimeStart +
                ", roTimeEnd=" + roTimeEnd +
                '}';
    }
}
