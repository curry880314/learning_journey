package bean;

import javafx.beans.property.SimpleStringProperty;

public class Login {
    /** ID **/
    private final SimpleStringProperty ID;
    /** Password **/
    private final SimpleStringProperty password;

    public Login(String ID, String password) {
        this.ID = new SimpleStringProperty(ID);
        this.password = new SimpleStringProperty(password);
    }

    // Getter methods

    public String getID() {
        return ID.get();
    }

    public String getPassword() {
        return password.get();
    }

    // Setter methods

    public void setID(String ID) {
        this.ID.set(ID);
    }

    public void setPassword(String password) {
        this.password.set(password);
    }
}
