package bean;

import javafx.beans.property.SimpleStringProperty;

public class Course {
    private final SimpleStringProperty cID;
    private final SimpleStringProperty cName;
    private final SimpleStringProperty cScore;
    private final SimpleStringProperty cGradePoint;
    private final SimpleStringProperty cStartTerm;
    private final SimpleStringProperty cPeriod;
    private final SimpleStringProperty cCredit;
    private final SimpleStringProperty username;

    public Course(String cID, String cName,String cScore, String cGradePoint, String cStartTerm, String cPeriod, String cCredit, String username) {
        this.cID = new SimpleStringProperty(cID);
        this.cScore = new SimpleStringProperty(cScore);
        this.cName = new SimpleStringProperty(cName);
        this.cGradePoint = new SimpleStringProperty(cGradePoint);
        this.cStartTerm = new SimpleStringProperty(cStartTerm);
        this.cPeriod = new SimpleStringProperty(cPeriod);
        this.cCredit = new SimpleStringProperty(cCredit);
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

}