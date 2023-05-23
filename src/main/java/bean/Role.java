package bean;

import javafx.beans.property.SimpleStringProperty;

/**
 * Represents a role.
 */
public class Role {
    private final SimpleStringProperty roID;
    private final SimpleStringProperty roName;
    private final SimpleStringProperty roTimeStart;
    private final SimpleStringProperty roTimeEnd;
    private final SimpleStringProperty username;

    /**
     * Constructs a role with the specified information.
     *
     * @param roID         the ID of the role
     * @param roName       the name of the role
     * @param roTimeStart  the start time of the role
     * @param roTimeEnd    the end time of the role
     * @param username     the username associated with the role
     */
    public Role(String roID, String roName, String roTimeStart, String roTimeEnd, String username) {
        this.roID = new SimpleStringProperty(roID);
        this.roName = new SimpleStringProperty(roName);
        this.roTimeStart = new SimpleStringProperty(roTimeStart);
        this.roTimeEnd = new SimpleStringProperty(roTimeEnd);
        this.username = new SimpleStringProperty(username);
    }

    /**
     * Returns the username associated with the role.
     *
     * @return the username
     */
    public String getUsername() {
        return username.get();
    }

    /**
     * Returns the username property.
     *
     * @return the username property
     */
    public SimpleStringProperty usernameProperty() {
        return username;
    }

    /**
     * Returns the ID of the role.
     *
     * @return the role ID
     */
    public String getRoID() {
        return roID.get();
    }

    /**
     * Returns the role ID property.
     *
     * @return the role ID property
     */
    public SimpleStringProperty roIDProperty() {
        return roID;
    }

    /**
     * Returns the start time of the role.
     *
     * @return the role start time
     */
    public String getRoTimeStart() {
        return roTimeStart.get();
    }

    /**
     * Returns the role start time property.
     *
     * @return the role start time property
     */
    public SimpleStringProperty roTimeStartProperty() {
        return roTimeStart;
    }

    /**
     * Returns the name of the role.
     *
     * @return the role name
     */
    public String getRoName() {
        return roName.get();
    }

    /**
     * Returns the role name property.
     *
     * @return the role name property
     */
    public SimpleStringProperty roNameProperty() {
        return roName;
    }

    /**
     * Returns the end time of the role.
     *
     * @return the role end time
     */
    public String getRoTimeEnd() {
        return roTimeEnd.get();
    }

    /**
     * Returns the role end time property.
     *
     * @return the role end time property
     */
    public SimpleStringProperty roTimeEndProperty() {
        return roTimeEnd;
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

