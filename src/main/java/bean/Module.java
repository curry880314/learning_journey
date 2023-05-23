package bean;
import javafx.beans.property.SimpleStringProperty;

/**
 * Represents a module.
 */
public class Module {
    private final SimpleStringProperty moID;
    private final SimpleStringProperty moName;
    private final SimpleStringProperty moTime;
    private final SimpleStringProperty moPosition;
    private final SimpleStringProperty username;

    /**
     * Constructs a module with the specified information.
     *
     * @param moID      the ID of the module
     * @param moName    the name of the module
     * @param moTime    the time of the module
     * @param moPosition the position of the module
     * @param username  the username associated with the module
     */
    public Module(String moID, String moName, String moTime, String moPosition, String username) {
        this.moID = new SimpleStringProperty(moID);
        this.moName = new SimpleStringProperty(moName);
        this.moTime = new SimpleStringProperty(moTime);
        this.moPosition = new SimpleStringProperty(moPosition);
        this.username = new SimpleStringProperty(username);
    }

    /**
     * Returns the username associated with the module.
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
     * Returns the ID of the module.
     *
     * @return the module ID
     */
    public String getMoID() {
        return moID.get();
    }

    /**
     * Returns the module ID property.
     *
     * @return the module ID property
     */
    public SimpleStringProperty MoIDProperty() {
        return moID;
    }

    /**
     * Returns the time of the module.
     *
     * @return the module time
     */
    public String getMoTime() {
        return moTime.get();
    }

    /**
     * Returns the module time property.
     *
     * @return the module time property
     */
    public SimpleStringProperty moTimeProperty() {
        return moTime;
    }

    /**
     * Returns the name of the module.
     *
     * @return the module name
     */
    public String getMoName() {
        return moName.get();
    }

    /**
     * Returns the module name property.
     *
     * @return the module name property
     */
    public SimpleStringProperty MoNameProperty() {
        return moName;
    }

    /**
     * Returns the position of the module.
     *
     * @return the module position
     */
    public String getMoPosition() {
        return moPosition.get();
    }

    /**
     * Returns the module position property.
     *
     * @return the module position property
     */
    public SimpleStringProperty moPositionProperty() {
        return moPosition;
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

