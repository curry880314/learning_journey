package bean;

import javafx.beans.property.SimpleStringProperty;

public class Course {
    /** ID **/
    private final SimpleStringProperty cID;
    /** Name **/
    private final SimpleStringProperty cName;
    /** Score **/
    private final SimpleStringProperty cScore;
    /** Grade Point **/
    private final SimpleStringProperty cGradePoint;
    /** Start Term **/
    private final SimpleStringProperty cStartTerm;
    /** Period **/
    private final SimpleStringProperty cPeriod;
    /** Credit **/
    private final SimpleStringProperty cCredit;
    /** Username **/
    private final SimpleStringProperty username;

    public Course(String cID, String cName, String cScore, String cGradePoint, String cStartTerm, String cPeriod, String cCredit, String username) {
        this.cID = new SimpleStringProperty(cID);
        this.cScore = new SimpleStringProperty(cScore);
        this.cName = new SimpleStringProperty(cName);
        this.cGradePoint = new SimpleStringProperty(cGradePoint);
        this.cStartTerm = new SimpleStringProperty(cStartTerm);
        this.cPeriod = new SimpleStringProperty(cPeriod);
        this.cCredit = new SimpleStringProperty(cCredit);
        this.username = new SimpleStringProperty(username);
    }

    // Getter and Setter methods for each property

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getcID() {
        return cID.get();
    }

    public SimpleStringProperty cIDProperty() {
        return cID;
    }

    public void setcID(String cID) {
        this.cID.set(cID);
    }

    public String getcScore() {
        return cScore.get();
    }

    public SimpleStringProperty cScoreProperty() {
        return cScore;
    }

    public void setcScore(String cScore) {
        this.cScore.set(cScore);
    }

    public String getcName() {
        return cName.get();
    }

    public SimpleStringProperty cNameProperty() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName.set(cName);
    }

    public String getcGradePoint() {
        return cGradePoint.get();
    }

    public SimpleStringProperty cGradePointProperty() {
        return cGradePoint;
    }

    public void setcGradePoint(String cGradePoint) {
        this.cGradePoint.set(cGradePoint);
    }

    public String getcStartTerm() {
        return cStartTerm.get();
    }

    public SimpleStringProperty cStartTermProperty() {
        return cStartTerm;
    }

    public void setcStartTerm(String cStartTerm) {
        this.cStartTerm.set(cStartTerm);
    }

    public String getcPeriod() {
        return cPeriod.get();
    }

    public SimpleStringProperty cPeriodProperty() {
        return cPeriod;
    }

    public void setcPeriod(String cPeriod) {
        this.cPeriod.set(cPeriod);
    }

    public String getcCredit() {
        return cCredit.get();
    }

    public SimpleStringProperty cCreditProperty() {
        return cCredit;
    }

    public void setcCredit(String cCredit) {
        this.cCredit.set(cCredit);
    }

    @Override
    public String toString() {
        return "Course{" +
                "cID=" + cID +
                ", cName=" + cName +
                ", cScore=" + cScore +
                ", cGradePoint=" + cGradePoint +
                ", cStartTerm=" + cStartTerm +
                ", cPeriod=" + cPeriod +
                ", cCredit=" + cCredit +
                ", username=" + username +
                '}';
    }
}
