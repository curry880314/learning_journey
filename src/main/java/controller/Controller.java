package controller;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import service.impl.*;
import bean.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller implements Initializable {

    @FXML
    Tab tab_course;
    @FXML
    Tab tab_achievement;
    @FXML
    Pane pane_personal;
    @FXML
    Tab tab_information;
    @FXML
    Tab tab_role;
    @FXML
    Tab tab_module;
    @FXML
    TableView ModuleTableView;
    @FXML
    GridPane grid_pane_information;
    @FXML
    Button loginButton;
    @FXML
    Button logOutButton;
    @FXML
    TableView achievementTableView;
    @FXML
    TableView courseTableView;
    @FXML
    TableView informationview;
    @FXML
    TableView RoleTableView;
    @FXML
    TableColumn cIdCol;
    @FXML
    TableColumn cNameCol;
    @FXML
    TableColumn cScoreCol;
    @FXML
    TableColumn cGradePointCol;
    @FXML
    TableColumn cStartTremCol;
    @FXML
    TableColumn cPeriodCol;
    @FXML
    TableColumn cCreditCol;

    @FXML
    TableColumn achIDCol;
    @FXML
    TableColumn achNameCol;
    @FXML
    TableColumn achLevelCol;
    @FXML
    TableColumn achTimeCol;
    @FXML
    TableColumn achMajorCol;
    @FXML
    TableColumn roIDCol;
    @FXML
    TableColumn roNameCol;
    @FXML
    TableColumn roTimeStartCol;
    @FXML
    TableColumn roTimeEndCol;
    @FXML
    TableColumn moIDCol;
    @FXML
    TableColumn moNameCol;
    @FXML
    TableColumn moTimeCol;
    @FXML
    TableColumn moPositionCol;

    // Default admin ID
    String adminID = "0";

    // User's username
    String username = "";

    // List to store courses
    List<Course> courses = new ArrayList<>();

    // ImageView for displaying an image
    ImageView imageView = new ImageView();

    // Regular expression for email validation
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    // Regular expression for phone number validation
    private static final String PHONE_NUMBER_REGEX = "^1[3456789]\\d{9}$";
    /**
     * Add Course.
     * This method allows the user to add a course to the system.
     * If the admin ID is "0", it displays an error message to log in first.
     * It opens a dialog window for the user to enter the course information.
     * The user needs to provide the course number, course name, score, opening semester, duration, and credit.
     * After validating the input, it creates a CourseResults object with the entered information.
     * If any required field is empty or the input is invalid, it displays an error message.
     * Otherwise, it saves the course using the CourseServiceImpl and displays a success message.
     * Finally, it refreshes the course table.
     */
    public void addCourse(){
        if(adminID.equals("0")) {
            alert("Hint","Please log in first",null, Alert.AlertType.ERROR);
            return;
        }
        Dialog<CourseResults> dialog = new Dialog<>();
        dialog.setTitle("Add Course");
        dialog.setHeaderText("Enter the course information to be added：");

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20,60,10,10));

        TextField cID = new TextField();
        TextField cName = new TextField();
        TextField cScore = new TextField();
        TextField cStartTerm = new TextField();
        TextField cPeriod = new TextField();
        TextField cCredit = new TextField();

        grid.add(new Label("Course Number:"), 0, 0);
        grid.add(cID, 1, 0);
        grid.add(new Label("Course Name:"), 0, 1);
        grid.add(cName, 1, 1);
        grid.add(new Label("Score:"), 0, 2);
        grid.add(cScore, 1, 2);
        grid.add(new Label("Opening Semester:"), 0, 3);
        grid.add(cStartTerm, 1, 3);
        grid.add(new Label("Duration:"), 0, 4);
        grid.add(cPeriod, 1, 4);
        grid.add(new Label("Credit:"), 0, 5);
        grid.add(cCredit, 1, 5);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {

                return new CourseResults(cID.getText(),cName.getText(),
                        cScore.getText(),cScore.getText(),
                        cStartTerm.getText(),cPeriod.getText(), cCredit.getText());
            }
            return null;
        });
        Optional<CourseResults> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent((CourseResults results) -> {
            if (results.cID.isEmpty() || results.cName.isEmpty() || results.cScore.isEmpty() ||
                    results.cStartTerm.isEmpty() || results.cPeriod.isEmpty() || results.cCredit.isEmpty()) {
                alert("Hint", "Please enter all required information.", null, Alert.AlertType.ERROR);
                return;
            }
            if(!isValidCode(results.cID)){
                alert("Hint", "ID must be EBU+4(0-9).", null, Alert.AlertType.ERROR);
                return;
            }
            int score = Integer.parseInt(results.cScore);
            if (score < 0 || score > 100) {
                alert("Hint", "Score must be between 0 and 100.", null, Alert.AlertType.ERROR);
                return;
            }
            Course course = new CourseServiceImpl().get(results.cID);
            if(course != null){
                alert("Hint","Course number is【" + results.cID + "】data which is exist，unable to add！",null, Alert.AlertType.INFORMATION);
            }else{
                new CourseServiceImpl().save(new Course(results.cID, results.cName, results.cScore,
                        results.cGradePoint, results.cStartTerm, results.cPeriod, results.cCredit,username));

                alert("Hint","Successfully saved course number is【" + results.cID + "】course data！",null, Alert.AlertType.INFORMATION);
                refreshCourseTable();
            }
        });
    }

    /**
     * Modify the course information based on the entered course number.
     * If the course exists, a dialog will be displayed to enter the modified information.
     * If the course does not exist, an error message will be displayed.
     */
    public void changeCourse(){
        if(adminID.equals("0")) {
            alert("Hint","Please log in first",null, Alert.AlertType.ERROR);
            return;
        }

        TextInputDialog d = new TextInputDialog();
        d.setTitle("Modify Course Information");
        d.setHeaderText("Enter the course number to modify the information：");
        d.setContentText("Course Number:");
        Optional<String> result = d.showAndWait();

        if(result.isPresent()){
            if(checkIdIllegal(result.get())){
                return;
            }
            Course course = new CourseServiceImpl().get(result.get());
            if(null != course){

                Dialog<CourseResults> dialog = new Dialog<>();
                dialog.setTitle("Course Data");
                dialog.setHeaderText(null);

                DialogPane dialogPane = dialog.getDialogPane();
                dialogPane.getButtonTypes().addAll(ButtonType.OK);

                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20,60,10,10));

                TextField cID = new TextField(course.getcID());
                TextField cName = new TextField(course.getcName());
                TextField cScore = new TextField(course.getcScore());
                TextField cStartTerm = new TextField(course.getcStartTerm());
                TextField cPeriod = new TextField(course.getcPeriod());
                TextField cCredit = new TextField(course.getcCredit());

                grid.add(new Label("Course Number:"), 0, 0);
                grid.add(cID, 1, 0);
                grid.add(new Label("Score:"), 0, 1);
                grid.add(cScore, 1, 1);
                grid.add(new Label("Course Name:"), 0, 2);
                grid.add(cName, 1, 2);
                grid.add(new Label("Opening Semester:"), 0, 3);
                grid.add(cStartTerm, 1, 3);
                grid.add(new Label("Duration:"), 0, 4);
                grid.add(cPeriod, 1, 4);
                grid.add(new Label("Credit:"), 0, 5);
                grid.add(cCredit, 1, 5);

                dialog.getDialogPane().setContent(grid);


                dialog.setResultConverter((ButtonType button) -> {
                    if (button == ButtonType.OK) {

                        return new CourseResults(cID.getText(),cName.getText(),
                                cScore.getText(),cScore.getText(),
                                cStartTerm.getText(),cPeriod.getText(), cCredit.getText());
                    }
                    return null;
                });
                Optional<CourseResults> optionalResult = dialog.showAndWait();
                optionalResult.ifPresent((CourseResults results) ->{
                    Course courseadd = new Course(cID.getText(),cName.getText(),cScore.getText(),cGradePointCol.getText(),
                            cStartTerm.getText(),cPeriod.getText(), cCredit.getText(), username);
                    if (results.cID.isEmpty() || results.cName.isEmpty() || results.cScore.isEmpty() ||
                            results.cStartTerm.isEmpty() || results.cPeriod.isEmpty() || results.cCredit.isEmpty()) {
                        alert("Hint", "Please enter all required information.", null, Alert.AlertType.ERROR);
                        return;
                    }
                    if(!isValidCode(results.cID)){
                        alert("Hint", "ID must be EBU+4(0-9).", null, Alert.AlertType.ERROR);
                        return;
                    }
                    int score = Integer.parseInt(results.cScore);
                    if (score < 0 || score > 100) {
                        alert("Hint", "Score must be between 0 and 100.", null, Alert.AlertType.ERROR);
                        return;
                    }
                    new CourseServiceImpl().update(result.get(), courseadd);
                    alert("Hint","Successfully modified course number is【" + course.getcID() + "】course data！",null, Alert.AlertType.INFORMATION);
                    refreshCourseTable();
                    });
            }else{
                alert("Hint","There is no record of this course and it cannot be modified！",null, Alert.AlertType.ERROR);
            }
        }
    }

    /**
     * Deletes a course based on the given course number.
     * If the user is not logged in as an admin, an error alert will be displayed.
     * A dialog will prompt the user to enter the course number to delete.
     * If the course number is valid and exists in the database, it will be deleted.
     * After successful deletion, an information alert will be displayed, and the course table will be refreshed.
     */
    public void deleteCourse(){
        if(adminID.equals("0")) {
            alert("Hint","Please log in first",null, Alert.AlertType.ERROR);
            return;
        }

        TextInputDialog d = new TextInputDialog();
        d.setTitle("Delete Course");
        d.setHeaderText("Enter the course number to delete：");
        d.setContentText("Course Number:");
        Optional<String> result = d.showAndWait();

        if (result.isPresent()){
            if(checkIdIllegal(result.get())){
                return;
            }
            Course course = new CourseServiceImpl().get(result.get());
            if(null != course){
                new CourseServiceImpl().delete(course.getcID(),adminID);
                alert("Hint","Successfully delete with number is【" + course.getcID() + "】Course Data！",null, Alert.AlertType.INFORMATION);
                refreshCourseTable();
            }else {
                alert("Hint","There is no record of this course and it cannot be deleted！",null, Alert.AlertType.ERROR);
            }
        }
    }

    /**
     * Calculate the GPA (Grade Point Average) based on the courses' scores and credits.
     *
     * @return True if the GPA calculation is successful.
     */
    public  boolean calculateGPA(){
        float sum = 0,creditSum = 0;
        for (Course score:courses) {
            sum = sum +Float.parseFloat(score.getcCredit())*Float.parseFloat(score.getcGradePoint());
            creditSum = creditSum + Float.parseFloat(score.getcCredit());
        }
        alert("GPA","GPA:"+String.valueOf(sum/creditSum),null, Alert.AlertType.INFORMATION);
        return true;

    }


    /**

     Add Achievement.

     This method allows the user to add an achievement to the system.

     If the admin ID is "0", it displays an error message to log in first.

     It opens a dialog window for the user to enter the achievement information.

     The user needs to provide the achievement number, achievement name, level, obtained time, and college.

     After validating the input, it creates an AchievementResult object with the entered information.

     If the achievement with the same number already exists, it displays an error message.

     Otherwise, it saves the achievement using the AchievementServiceImpl and displays a success message.

     Finally, it refreshes the achievement table.
     */
    public void addAchievement() {
        if(adminID.equals("0")) {
            alert("Hint","Please log in first",null, Alert.AlertType.ERROR);
            return;
        }
        Dialog<AchievementResult> dialog = new Dialog<>();
        dialog.setTitle("Add Achievement");
        dialog.setHeaderText("Enter the achievement information to be added below:：");

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20,60,10,10));

        TextField achID = new TextField();
        TextField achName = new TextField();
        TextField achLevel = new TextField();
        TextField achTime = new TextField();
        TextField achMajor = new TextField();

        grid.add(new Label("Number:"), 0, 0);
        grid.add(achID, 1, 0);
        grid.add(new Label("Achievement:"), 0, 1);
        grid.add(achName, 1, 1);
        grid.add(new Label("Level:"), 0, 2);
        grid.add(achLevel, 1, 2);
        grid.add(new Label("Obtained time:"), 0, 3);
        grid.add(achTime, 1, 3);
        grid.add(new Label("College:"), 0, 4);
        grid.add(achMajor, 1, 4);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                String achTimeInput = achTime.getText();
                String achIDInput = achID.getText();
                String achNameInput = achName.getText();
                String achLevelInput = achLevel.getText();
                //String achTimeInput = achTime.getText();
                String achMajorInput = achMajor.getText();

                if (isEmpty(achIDInput) || isEmpty(achNameInput) || isEmpty(achLevelInput) || isEmpty(achTimeInput) || isEmpty(achMajorInput)) {
                    alert("Hint", "Please fill in all the fields", null, Alert.AlertType.ERROR);
                    return null;
                }
                if (!isValidTime(achTimeInput)) {
                    alert("Hint", "Invalid achievement time format. Please use the format yyyy.MM.dd", null, Alert.AlertType.ERROR);
                    return null;
                }

                return new AchievementResult(achID.getText(),
                        achName.getText(),achLevel.getText(),
                        achTime.getText(),achMajor.getText());
            }
            return null;
        });

        Optional<AchievementResult> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent((AchievementResult results) -> {

            Achievement achievement = new AchievementServiceImpl().get(Integer.parseInt(achID.getText()),adminID);
            if(achievement != null){
                alert("Hint","Number is 【" + results.tID + "】achievement data is exist，unable to add！",null, Alert.AlertType.ERROR);
            }else{
                new AchievementServiceImpl().save(adminID,new Achievement(results.tID, results.tName,
                        results.tSex, results.tBirth, results.tMajor));

                alert("Hint","Successfully saved with number【" + results.tID + "】Achievement Data！",null, Alert.AlertType.INFORMATION);
                refreshAchTable();
            }
        });
    }
    /**
     * Check if the given value is empty or consists only of whitespace characters.
     *
     * @param value The value to check for emptiness.
     * @return True if the value is empty or consists only of whitespace characters, false otherwise.
     */
    public boolean isEmpty(String value) {//判断内部是否为空
        return value == null || value.trim().isEmpty();
    }
    /**
     * Validate the format of the input time.
     *
     * @param achTime The time to validate.
     * @return True if the time has a valid format, false otherwise.
     */
    public boolean isValidTime(String achTime) {//对输入的时间格式进行验证
        String pattern = "^\\d{4}\\.\\d{2}\\.\\d{2}$";
        return achTime.matches(pattern);
    }
    /**
     * Modify the achievement information based on the entered achievement number.
     * If the achievement exists, a dialog will be displayed to enter the modified information.
     * If the achievement does not exist, an error message will be displayed.
     */
    public void changeAch(){
        if(adminID.equals("0")) {
            alert("Hint","Please log in first!",null, Alert.AlertType.ERROR);
            return;
        }
        TextInputDialog d = new TextInputDialog();
        d.setTitle("Modify Achievement Information");
        d.setHeaderText("Enter the achievement number to modify the information：");
        d.setContentText("Number:");
        Optional<String> result = d.showAndWait();

        if(result.isPresent()){
            if(checkIdIllegal(result.get())){
                return;
            }

            Achievement achievement = new AchievementServiceImpl().get(Integer.parseInt(result.get()),adminID);
            if(null != achievement){
                Dialog<AchievementResult> dialog = new Dialog<>();
                dialog.setTitle("Achievement Data");
                dialog.setHeaderText(null);

                DialogPane dialogPane = dialog.getDialogPane();
                dialogPane.getButtonTypes().addAll(ButtonType.OK);

                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20,60,10,10));

                TextField tID = new TextField(achievement.getAchID());
                TextField tName = new TextField(achievement.getAchName());
                TextField tLevel = new TextField(achievement.getAchLevel());
                TextField tBirth = new TextField(achievement.getAchTime());
                TextField tMajor = new TextField(achievement.getAchMajor());

                grid.add(new Label("Number:"), 0, 0);
                grid.add(tID, 1, 0);
                grid.add(new Label("Achievement:"), 0, 1);
                grid.add(tName, 1, 1);
                grid.add(new Label("Level:"), 0, 2);
                grid.add(tLevel, 1, 2);
                grid.add(new Label("Obtained time:"), 0, 3);
                grid.add(tBirth, 1, 3);
                grid.add(new Label("College:"), 0, 4);
                grid.add(tMajor, 1, 4);

                dialog.getDialogPane().setContent(grid);
                dialog.setResultConverter((ButtonType button) -> {
                    if (button == ButtonType.OK) {
                        String achTimeInput = tBirth.getText();
                        String achIDInput = tID.getText();
                        String achNameInput = tName.getText();
                        String achLevelInput = tLevel.getText();
                        String achMajorInput = tMajor.getText();
                        Achievement achievement1=new AchievementServiceImpl().get(Integer.parseInt(achIDInput),adminID);

                        if(achievement1!=null&&!achievement1.getAchID().equals(achievement.getAchID())){
                            System.out.println(achievement1.getAchID());
                            System.out.println(achievement.getAchID());
                            alert("Hint", "ID duplication.", null, Alert.AlertType.ERROR);
                            return null;
                        }
                        if (isEmpty(achIDInput) || isEmpty(achNameInput) || isEmpty(achLevelInput) || isEmpty(achTimeInput) || isEmpty(achMajorInput)) {
                            alert("Hint", "Please fill in all the fields", null, Alert.AlertType.ERROR);
                            return null;
                        }
                        if (!isValidTime(achTimeInput)) {
                            alert("Hint", "Invalid achievement time format. Please use the format yyyy.MM.dd", null, Alert.AlertType.ERROR);
                            return null;
                        }

                        return new AchievementResult(tID.getText(),
                                tName.getText(),tLevel.getText(),
                                tBirth.getText(),tMajor.getText());
                    }
                    return null;
                });
                Optional<AchievementResult> results = dialog.showAndWait();

                if(results.isPresent()){

                    Achievement ach = new Achievement(tID.getText(),
                            tName.getText(),tLevel.getText(),
                            tBirth.getText(),tMajor.getText());
                    new AchievementServiceImpl().update(adminID, ach);
                    alert("Hint","Successfully modified the number is【" + achievement.getAchID() + "】 achievement data！",null, Alert.AlertType.INFORMATION);
                    refreshAchTable();
                }
            }else{
                alert("Hint","There is no record of this achievement and cannot be modified！",null, Alert.AlertType.ERROR);
            }
        }}

    /**
     * Deletes an achievement based on the given achievement number.
     * If the user is not logged in as an admin, an error alert will be displayed.
     * A dialog will prompt the user to enter the achievement number to delete.
     * If the achievement number is valid and exists in the database, it will be deleted.
     * After successful deletion, an information alert will be displayed, and the achievement table will be refreshed.
     */
    public void deleteAch(){
        if(adminID.equals("0")) {
            alert("Hint","Please log in first",null, Alert.AlertType.ERROR);
            return;
        }
        TextInputDialog d = new TextInputDialog();
        d.setTitle("Delete achievements");
        d.setHeaderText("Enter achievement number to delete:");
        d.setContentText("Number:");
        Optional<String> result = d.showAndWait();

        if (result.isPresent()){
            if(checkIdIllegal(result.get())){
                return;
            }
            Achievement achievement = new AchievementServiceImpl().get(Integer.parseInt(result.get()),adminID);
            if(null != achievement){
                new AchievementServiceImpl().delete(adminID,result.get());
                alert("Hint","Successfully deleted number is【" + achievement.getAchID() + "】's achievement data！",null, Alert.AlertType.INFORMATION);
                refreshAchTable();
            }else {
                alert("Hint","There is no record of this achievement and it cannot be deleted！",null, Alert.AlertType.ERROR);
            }
        }
    }

    public void addRole(){
        if(adminID.equals("0")) {
            alert("Hint","Please log in first",null, Alert.AlertType.ERROR);
            return;
        }
        Dialog<RoleResult> dialog = new Dialog<>();
        dialog.setTitle("Add Role");
        dialog.setHeaderText("Enter the role information to be added below:：");

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20,60,10,10));

        TextField roID = new TextField();
        TextField roName = new TextField();
        TextField roTimeStart = new TextField();
        TextField roTimeEnd = new TextField();

        grid.add(new Label("Number:"), 0, 0);
        grid.add(roID, 1, 0);
        grid.add(new Label("Role:"), 0, 1);
        grid.add(roName, 1, 1);
        grid.add(new Label("TimeStart:"), 0, 2);
        grid.add(roTimeStart, 1, 2);
        grid.add(new Label("TimeEnd:"), 0, 3);
        grid.add(roTimeEnd, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                String roIDInput = roID.getText();
                String roNameInput = roName.getText();
                String roTimeStartInput = roTimeStart.getText();
                String roTimeEndInput = roTimeEnd.getText();

                if (isEmpty(roIDInput) || isEmpty(roNameInput) || isEmpty(roTimeStartInput) || isEmpty(roTimeEndInput)) {
                    alert("Hint", "Please fill in all the fields", null, Alert.AlertType.ERROR);
                    return null;
                }
                if (!isValidTime(roTimeStartInput) || !isValidTime(roTimeEndInput)) {
                    alert("Hint", "Invalid time format. Please use the format yyyy.MM.dd", null, Alert.AlertType.ERROR);
                    return null;
                }
                return new RoleResult(roID.getText(),
                        roName.getText(),roTimeStart.getText(),
                        roTimeEnd.getText());
            }
            return null;
        });

        Optional<RoleResult> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent((RoleResult results) -> {
            Role role = new RoleServiceimpl().get(results.rID,adminID);
            if(role != null){
                alert("Hint","Role number is【" + results.rID + "】data which is exist，unable to add！",null, Alert.AlertType.INFORMATION);
            }else{
                new RoleServiceimpl().save(new Role(results.rID, results.rName, results.rTimeStart,
                        results.rTimeEnd,username));

                alert("Hint","Successfully saved role number is【" + results.rID + "】role data！",null, Alert.AlertType.INFORMATION);
                refreshRoleTable();
            }
        });
    }

    public void changeRole(){
        if(adminID.equals("0")) {
            alert("Hint","Please log in first",null, Alert.AlertType.ERROR);
            return;
        }

        TextInputDialog d = new TextInputDialog();
        d.setTitle("Modify Role Information");
        d.setHeaderText("Enter the role number to modify the information：");
        d.setContentText("Role Number:");
        Optional<String> result = d.showAndWait();

        if(result.isPresent()){
            if(checkIdIllegal(result.get())){
                return;
            }
            Role role = new RoleServiceimpl().get(result.get(),adminID);
            if(null != role){
                Dialog<RoleResult> dialog = new Dialog<>();
                dialog.setTitle("Role Data");
                dialog.setHeaderText(null);

                DialogPane dialogPane = dialog.getDialogPane();
                dialogPane.getButtonTypes().addAll(ButtonType.OK);

                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20,60,10,10));

                TextField rID = new TextField(role.getRoID());
                TextField rName = new TextField(role.getRoName());
                TextField rTimeStart = new TextField(role.getRoTimeStart());
                TextField rTimeEnd = new TextField(role.getRoTimeEnd());

                grid.add(new Label("Role Number:"), 0, 0);
                grid.add(rID, 1, 0);
                grid.add(new Label("Role Name:"), 0, 1);
                grid.add(rName, 1, 1);
                grid.add(new Label("TimeStart:"), 0, 2);
                grid.add(rTimeStart, 1, 2);
                grid.add(new Label("TimeEnd:"), 0, 3);
                grid.add(rTimeEnd, 1, 3);

                dialog.getDialogPane().setContent(grid);

                dialog.setResultConverter((ButtonType button) -> {
                    if (button == ButtonType.OK) {
                        String roIDInput = rID.getText();
                        String roNameInput = rName.getText();
                        String roTimeStartInput = rTimeStart.getText();
                        String roTimeEndInput = rTimeEnd.getText();

                        if (isEmpty(roIDInput) || isEmpty(roNameInput) || isEmpty(roTimeStartInput) || isEmpty(roTimeEndInput)) {
                            alert("Hint", "Please fill in all the fields", null, Alert.AlertType.ERROR);
                            return null;
                        }
                        if (!isValidTime(roTimeStartInput) || !isValidTime(roTimeEndInput)) {
                            alert("Hint", "Invalid time format. Please use the format yyyy.MM.dd", null, Alert.AlertType.ERROR);
                            return null;
                        }
                        return new RoleResult(rID.getText(),
                                rName.getText(),rTimeStart.getText(),
                                rTimeEnd.getText());
                    }
                    return null;
                });
                Optional<RoleResult> results = dialog.showAndWait();

                if(results.isPresent()){
                    role = new Role(rID.getText(),rName.getText(),rTimeStart.getText(),rTimeEnd.getText(),username);
                    new RoleServiceimpl().update(result.get(), role);
                    alert("Hint","Successfully modified role number is【" + role.getRoID() + "】role data！",null, Alert.AlertType.INFORMATION);
                    refreshRoleTable();
                }
            }else{
                alert("Hint","There is no record of this role and it cannot be modified！",null, Alert.AlertType.ERROR);
            }
        }

    }

    public void deleteRole(){
        if(adminID.equals("0")) {
            alert("Hint","Please log in first",null, Alert.AlertType.ERROR);
            return;
        }

        TextInputDialog d = new TextInputDialog();
        d.setTitle("Delete Role");
        d.setHeaderText("Enter the role number to delete：");
        d.setContentText("Role Number:");
        Optional<String> result = d.showAndWait();

        if (result.isPresent()){
            if(checkIdIllegal(result.get())){
                return;
            }
            Role role = new RoleServiceimpl().get(result.get(),adminID);
            if(null != role){
                new RoleServiceimpl().delete(role.getRoID(),adminID);
                alert("Hint","Successfully delete with number is【" + role.getRoID() + "】Role Data！",null, Alert.AlertType.INFORMATION);
                refreshRoleTable();
            }else {
                alert("Hint","There is no record of this role and it cannot be deleted！",null, Alert.AlertType.ERROR);
            }
        }

    }

    /**
     * Updates the phone number for the logged-in user.
     * Prompts the user to enter the old phone number and the new phone number.
     * Validates the phone number format before updating.
     */
    public void UpdatePhoneNum(){
        if(adminID.equals("0")) {
            alert("Hint","Please log in first",null, Alert.AlertType.ERROR);
            return;
        }

        Dialog<PersonalInformation> dialog = new Dialog<>();
        dialog.setTitle("Phone Number");
        dialog.setHeaderText(null);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20,60,10,10));
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK,ButtonType.CANCEL);
        Label OldPhoneNumber = new Label(new PersonalInformationImpl().getInformation(adminID).phoneNumber);
        TextField NewPhoneNumber = new TextField();
        grid.add(new Label("Old Phone Number:"), 0, 0);
        grid.add(OldPhoneNumber, 1, 0);
        grid.add(new Label("New Phone Number:"), 0, 1);
        grid.add(NewPhoneNumber, 1, 1);
        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                String Phonenumber=NewPhoneNumber.getText();
                if(!isValidPhoneNumber(Phonenumber)){
                    alert("Hint","Phone number is wrong format",null, Alert.AlertType.WARNING);
                    return null;
                }
                return new PersonalInformation(adminID,"","","","","","","");
            }
            return null;
        });
        Optional<PersonalInformation> results = dialog.showAndWait();

        if(results.isPresent()){
            PersonalInformationImpl PII = new PersonalInformationImpl();
            PII.executeUpdate(adminID,NewPhoneNumber.getText(),PII.getInformation(adminID).email);
            refreshPersonalInformation();
            alert("Hint","Successfully modified Phone number is【" + NewPhoneNumber.getText() + "】！",null, Alert.AlertType.INFORMATION);
        }
    }

    /**
     * Updates the email number for the logged-in user.
     * Prompts the user to enter the old email number and the new email number.
     * Validates the email format before updating.
     */
    public void UpdateEmail(){
        if(adminID.equals("0")) {
            alert("Hint","Please log in first",null, Alert.AlertType.ERROR);
            return;
        }

        Dialog<PersonalInformation> dialog = new Dialog<>();
        dialog.setTitle("E-mail Number");
        dialog.setHeaderText(null);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20,60,10,10));
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK,ButtonType.CANCEL);
        Label OldEmailNumber = new Label(new PersonalInformationImpl().getInformation(adminID).email);
        TextField NewEmailNumber = new TextField();
        grid.add(new Label("Old E-mail Number:"), 0, 0);
        grid.add(OldEmailNumber, 1, 0);
        grid.add(new Label("New E-mail Number:"), 0, 1);
        grid.add(NewEmailNumber, 1, 1);
        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                String email=NewEmailNumber.getText();
                if(!isValidEmail(email)){
                    alert("Hint","Email is wrong format",null, Alert.AlertType.WARNING);
                    return null;
                }
                return new PersonalInformation(adminID,"","","","","","","");
            }
            return null;
        });
        Optional<PersonalInformation> results = dialog.showAndWait();

        if(results.isPresent()){
            PersonalInformationImpl PII = new PersonalInformationImpl();
            PII.executeUpdate(adminID,PII.getInformation(adminID).phoneNumber,NewEmailNumber.getText());
            refreshPersonalInformation();
            alert("Hint","Successfully modified E-mail number is【" + NewEmailNumber.getText() + "】！",null, Alert.AlertType.INFORMATION);
        }
        else {

        }

    }
    /**
     * Checks if the given code is a valid code according to the specified pattern.
     *
     * @param code The code to be validated.
     * @return {@code true} if the code is valid, {@code false} otherwise.
     */
    public static boolean isValidCode(String code) {
        String pattern = "^EBU\\d{4}$";
        return code.matches(pattern);
    }
    /**
     * Checks if the given email address is valid according to the specified email pattern.
     *
     * @param email The email address to be validated.
     * @return {@code true} if the email address is valid, {@code false} otherwise.
     */
    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    /**
     * Checks if the given phone number is valid according to the specified phone number pattern.
     *
     * @param phoneNumber The phone number to be validated.
     * @return {@code true} if the phone number is valid, {@code false} otherwise.
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile(PHONE_NUMBER_REGEX);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
    public void addModule(){
        if(adminID.equals("0")) {
            alert("Hint","Please log in first",null, Alert.AlertType.ERROR);
            return;
        }
        Dialog<ModuleResult> dialog = new Dialog<>();
        dialog.setTitle("Add Module");
        dialog.setHeaderText("Enter the module information to be added below:：");

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20,60,10,10));

        TextField moID = new TextField();
        TextField moName = new TextField();
        TextField moTime = new TextField();
        TextField moPosition = new TextField();

        grid.add(new Label("Number:"), 0, 0);
        grid.add(moID, 1, 0);
        grid.add(new Label("Role:"), 0, 1);
        grid.add(moName, 1, 1);
        grid.add(new Label("Time:"), 0, 2);
        grid.add(moTime, 1, 2);
        grid.add(new Label("Position:"), 0, 3);
        grid.add(moPosition, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                String moIDInput = moID.getText();
                String moNameInput = moName.getText();
                String moTimeInput = moTime.getText();
                String moPositionInput = moPosition.getText();

                if (isEmpty(moIDInput) || isEmpty(moNameInput) || isEmpty(moTimeInput) || isEmpty(moPositionInput)) {
                    alert("Hint", "Please fill in all the fields", null, Alert.AlertType.ERROR);
                    return null;
                }

                if (!isValidTime(moTimeInput)) {
                    alert("Hint", "Invalid time format. Please use the format yyyy.MM.dd", null, Alert.AlertType.ERROR);
                    return null;
                }
                return new ModuleResult(moID.getText(),
                        moName.getText(),moTime.getText(),
                        moPosition.getText());
            }
            return null;
        });
        Optional<ModuleResult> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent((ModuleResult results) -> {

            Module module = new ModuleServicelmpl().get(Integer.parseInt(results.moID),adminID);

            if(module != null){
                alert("Hint","Module number is" +
                        "【" + results.moID + "】data which is exist，unable to add！",null, Alert.AlertType.INFORMATION);
            }else{
                new ModuleServicelmpl().save(new Module(results.moID, results.moName, results.moTime,
                               results.moPosition,adminID));

                alert("Hint","Successfully saved module number is【" + results.moID + "】role data！",null, Alert.AlertType.INFORMATION);
                refreshModuleTable();

            }
        });
    }
    public void deleteModule(){
        if(adminID.equals("0")) {
            alert("Hint","Please log in first",null, Alert.AlertType.ERROR);
            return;
        }
        TextInputDialog d = new TextInputDialog();
        d.setTitle("Delete module");
        d.setHeaderText("Enter module Id to delete:");
        d.setContentText("ID:");
        Optional<String> result = d.showAndWait();

        if (result.isPresent()){
            if(checkIdIllegal(result.get())){
                return;
            }
            Module module = new ModuleServicelmpl().get(Integer.parseInt(result.get()),adminID);
            if(null != module){
                new ModuleServicelmpl().delete(adminID,result.get());
                alert("Hint","Successfully deleted number is【" + module.getMoID() + "】's module data！",null, Alert.AlertType.INFORMATION);
                refreshModuleTable();
            }else {
                alert("Hint","There is no record of this module and it cannot be deleted！",null, Alert.AlertType.ERROR);
            }
        }
    }
    public void changeModule(){
        if(adminID.equals("0")) {
            alert("Hint","Please log in first!",null, Alert.AlertType.ERROR);
            return;
        }
        TextInputDialog d = new TextInputDialog();
        d.setTitle("Modify Module Information");
        d.setHeaderText("Enter the Module ID to modify the information：");
        d.setContentText("ID:");
        Optional<String> result = d.showAndWait();

        if(result.isPresent()){
            if(checkIdIllegal(result.get())){
                return;
            }

            Module module = new ModuleServicelmpl().get(Integer.parseInt(result.get()),adminID);
            if(null != module){
                Dialog<ModuleResult> dialog = new Dialog<>();
                dialog.setTitle("Module Data");
                dialog.setHeaderText(null);

                DialogPane dialogPane = dialog.getDialogPane();
                dialogPane.getButtonTypes().addAll(ButtonType.OK);

                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20,60,10,10));

                TextField moID = new TextField(module.getMoID());
                TextField moName = new TextField(module.getMoName());
                TextField moTime = new TextField(module.getMoTime());
                TextField moPosition = new TextField(module.getMoPosition());


                grid.add(new Label("Number:"), 0, 0);
                grid.add(moID, 1, 0);
                grid.add(new Label("Achievement:"), 0, 1);
                grid.add(moName, 1, 1);
                grid.add(new Label("Level:"), 0, 2);
                grid.add(moTime, 1, 2);
                grid.add(new Label("Obtained time:"), 0, 3);
                grid.add(moPosition, 1, 3);


                dialog.getDialogPane().setContent(grid);

                dialog.setResultConverter((ButtonType button) -> {
                    if (button == ButtonType.OK) {
                        String moIDInput = moID.getText();
                        String moNameInput = moName.getText();
                        String moTimeInput = moTime.getText();
                        String moPositionInput = moPosition.getText();

                        if (isEmpty(moIDInput) || isEmpty(moNameInput) || isEmpty(moTimeInput) || isEmpty(moPositionInput)) {
                            alert("Hint", "Please fill in all the fields", null, Alert.AlertType.ERROR);
                            return null;
                        }

                        if (!isValidTime(moTimeInput)) {
                            alert("Hint", "Invalid time format. Please use the format yyyy.MM.dd", null, Alert.AlertType.ERROR);
                            return null;
                        }
                        return new ModuleResult(moIDInput, moNameInput, moTimeInput, moPositionInput);
                    }

                    return null;
                });
                Optional<ModuleResult> results = dialog.showAndWait();

                if(results.isPresent()){

                    Module mod=new Module(moID.getText()
                            ,moName.getText(),moTime.getText()
                    ,moPosition.getText(),adminID);
                    new ModuleServicelmpl().update(mod);
                    alert("Hint","Successfully modified the number is【" +
                            mod.getMoID() + "】 module data！",null, Alert.AlertType.INFORMATION);
                    refreshModuleTable();
                }
            }else{
                alert("Hint","There is no record of this module and cannot be modified！"
                        ,null, Alert.AlertType.ERROR);
            }
        }
    }

    /**
     * Performs the logout functionality. Displays a confirmation dialog to confirm the logout action.
     * Clears the displayed data and resets the UI to the initial state upon successful logout.
     */
    public  void logOut(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Please confirm if you want to log out:");
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get().getButtonData().equals(ButtonBar.ButtonData.OK_DONE)) {
            courseTableView.setItems(null);
            achievementTableView.setItems(null);
            RoleTableView.setItems(null);
            ModuleTableView.setItems(null);
            grid_pane_information.getChildren().clear();
            imageView.setImage(null);
            loginButton.setVisible(true);
            logOutButton.setVisible(false);
            pane_personal.getChildren().remove(imageView);
            alert("Hint","Successfully Logged Out.",null, Alert.AlertType.INFORMATION);

        } else {
            alert("Hint","Canceled Successfully",null, Alert.AlertType.INFORMATION);
        }
    }

    /**
     * Performs the login functionality. Displays a dialog for the user to enter their account and password.
     * Validates the input and logs in the user if the credentials are correct.
     */
    public void login(){
        Dialog<LoginResults> dialog = new Dialog<>();
        dialog.setTitle("Login");
        dialog.setHeaderText("Please enter your account and password:");

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20,60,10,10));

        TextField ID = new TextField();
        PasswordField password = new PasswordField();

        grid.add(new Label("Account:"), 0, 0);
        grid.add(ID, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                adminID=ID.getText();
                if (ID.getText().equals("")||password.getText().equals("")){
                    alert("Hint","Account or password cannot be empty！",null, Alert.AlertType.WARNING);
                    return null;
                }else {
                    return new LoginResults(ID.getText(), password.getText());
                }
            }
            return null;
        });
        Optional<LoginResults> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent((LoginResults results) -> {
            String login = new LoginServiceImpl().getUser(Integer.parseInt(results.stuID),Integer.parseInt(results.password));
            if(!Objects.equals(login, "")){
                alert("Hint","Login Successfully",null, Alert.AlertType.INFORMATION);
                username=login;
                loginButton.setVisible(false);
                logOutButton.setVisible(true);

                pane_personal.getChildren().add(imageView);
                refreshCourseTable();
                refreshAchTable();
                refreshPersonalInformation();
                refreshRoleTable();
                refreshModuleTable();
            }else{
                alert("Hint","Account or password is error!",null, Alert.AlertType.ERROR);
            }
        });
    }

    /**
     * Initializes the controller after its root element has been completely processed.
     * If the user is logged in as an admin, it refreshes the tables, personal information, and sets the visibility of tabs.
     * If the user is not logged in as an admin, it sets the visibility of specific tabs.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(adminID.equals("0")) {
            setTabVisible(tab_course, courseTableView);
            setTabVisible(tab_achievement,achievementTableView);
            setTabVisible(tab_information,informationview);
            setTabVisible(tab_role,RoleTableView);
            setTabVisible(tab_module,ModuleTableView);
            return;
        }
        refreshCourseTable();
        refreshAchTable();
        refreshPersonalInformation();
        refreshRoleTable();
        refreshModuleTable();

        setTabVisible(tab_course, courseTableView);
        setTabVisible(tab_information,informationview);
        setTabVisible(tab_achievement,achievementTableView);
        setTabVisible(tab_role,RoleTableView);
        setTabVisible(tab_module,ModuleTableView);
    }
    /**
     * Creates and returns a TableCell with a Text graphic for displaying MediaPlayer.Status as a String value in a TableColumn.
     * The TableCell is centered, and its height is set to USE_COMPUTED_SIZE.
     * The Text's wrapping width is bound to the widthProperty of the given TableColumn.
     * The Text's textProperty is bound to the itemProperty of the TableCell.
     *
     * @param tc The TableColumn to which the TableCell will be added.
     * @return The created TableCell with a Text graphic.
     */
    public TableCell<MediaPlayer.Status,String> getCell(TableColumn tc){
        TableCell<MediaPlayer.Status,String> cell = new TableCell<>();
        Text text = new Text();
        cell.setGraphic(text);
        cell.setAlignment(Pos.CENTER);
        cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
        text.wrappingWidthProperty().bind(tc.widthProperty());
        text.textProperty().bind(cell.itemProperty());
        return cell;
    }
    /**
     * Refreshes the Course table view with updated data.
     * Retrieves the courses from the service layer and populates the table view with the data.
     * Configures cell factories for specific columns to customize their appearance.
     */
    private void refreshCourseTable(){
        cIdCol.setCellValueFactory(new PropertyValueFactory<>("cID"));
        cNameCol.setCellValueFactory(new PropertyValueFactory<>("cName"));
        cScoreCol.setCellValueFactory(new PropertyValueFactory<>("cScore"));
        cGradePointCol.setCellValueFactory(new PropertyValueFactory<>("cGradePoint"));
        cStartTremCol.setCellValueFactory(new PropertyValueFactory<>("cStartTerm"));
        cPeriodCol.setCellValueFactory(new PropertyValueFactory<>("cPeriod"));
        cCreditCol.setCellValueFactory(new PropertyValueFactory<>("cCredit"));

        cGradePointCol.setCellFactory(tc->{return getCell(cGradePointCol);});
        cNameCol.setCellFactory(tc -> {return getCell(cNameCol);});
        cStartTremCol.setCellFactory(tc -> {return getCell(cStartTremCol);});
        courses = new CourseServiceImpl().getAll(username);
        ObservableList<Course> data = FXCollections.observableArrayList();
        for (Course course : courses) {
            data.add(course);
        }
        courseTableView.setItems(data);
    }
    /**
     * Refreshes the Achievement table view with updated data.
     * Retrieves the achievements from the service layer and populates the table view with the data.
     * Configures cell factories for specific columns to customize their appearance.
     */
    private void refreshAchTable(){
        achIDCol.setCellValueFactory(new PropertyValueFactory<>("achID"));
        achNameCol.setCellValueFactory(new PropertyValueFactory<>("achName"));
        achLevelCol.setCellValueFactory(new PropertyValueFactory<>("achLevel"));
        achTimeCol.setCellValueFactory(new PropertyValueFactory<>("achTime"));
        achMajorCol.setCellValueFactory(new PropertyValueFactory<>("achMajor"));

        achLevelCol.setCellFactory(tc->{return getCell(achLevelCol);});
        achNameCol.setCellFactory(tc -> {return getCell(achNameCol);});
        achMajorCol.setCellFactory(tc -> {return getCell(achMajorCol);});

        List<Achievement> achievements = new AchievementServiceImpl().getAll(adminID);
        ObservableList<Achievement> data = FXCollections.observableArrayList();
        for (Achievement achievement : achievements) {
            data.add(achievement);
        }
        achievementTableView.setItems(data);


    }

    /**
     * The function of updating personal information
     */
    private void refreshPersonalInformation(){
        grid_pane_information.getChildren().clear();
        String url = new PersonalInformationImpl().getInformation(adminID).imageUrl;
        Image image = new Image(url);
        imageView.setImage(image);
        imageView.setX(450);
        imageView.setY(30);
        imageView.setFitWidth(150);
        imageView.setFitHeight(128);
        imageView.setVisible(true);
        grid_pane_information.add(new Label("Student   ID:"),0,1);
        grid_pane_information.add(new Label("Student Name:"),0,0);
        grid_pane_information.add(new Label("Major  Name:"),0,2);
        grid_pane_information.add(new Label("Phone Number:"),0,3);
        grid_pane_information.add(new Label("E-mail Number:"),0,4);
        grid_pane_information.add(new Label("College Name:"),0,5);
        PersonalInformation PI = new PersonalInformationImpl().getInformation(adminID);
        grid_pane_information.add(new Label(PI.id),1,1);
        grid_pane_information.add(new Label(PI.name),1,0);
        grid_pane_information.add(new Label(PI.phoneNumber),1,3);
        grid_pane_information.add(new Label(PI.email),1,4);
        grid_pane_information.add(new Label(PI.major),1,2);
        grid_pane_information.add(new Label(PI.college),1,5);
    }
    private void refreshRoleTable(){
        roIDCol.setCellValueFactory(new PropertyValueFactory<>("roID"));
        roNameCol.setCellValueFactory(new PropertyValueFactory<>("roName"));
        roTimeStartCol.setCellValueFactory(new PropertyValueFactory<>("roTimeStart"));
        roTimeEndCol.setCellValueFactory(new PropertyValueFactory<>("roTimeEnd"));

        roNameCol.setCellFactory(tc->{return getCell(roNameCol);});
        roTimeStartCol.setCellFactory(tc->{return getCell(roTimeStartCol);});
        roTimeEndCol.setCellFactory(tc->{return getCell(roTimeEndCol);});
        List<Role>roles=new RoleServiceimpl().getAll(adminID);
        ObservableList<Role>data=FXCollections.observableArrayList();
        for (Role role:roles){
            data.add(role);
        }
        RoleTableView.setItems(data);
    }
    private void refreshModuleTable(){
        moIDCol.setCellValueFactory(new PropertyValueFactory<>("moID"));
        moNameCol.setCellValueFactory(new PropertyValueFactory<>("moName"));
        moTimeCol.setCellValueFactory(new PropertyValueFactory<>("moTime"));
        moPositionCol.setCellValueFactory(new PropertyValueFactory<>("moPosition"));

        moNameCol.setCellFactory(tc->{return getCell(moNameCol);});
        moTimeCol.setCellFactory(tc->{return getCell(moTimeCol);});
        moPositionCol.setCellFactory(tc->{return getCell(moPositionCol);});
        List<Module>modules=new ModuleServicelmpl().getAll(adminID);
        ObservableList<Module>data=FXCollections.observableArrayList();
        for (Module module:modules){
            data.add(module);
        }
        ModuleTableView.setItems(data);
    }
    /**
     * Checks if the given ID is illegal.
     * The ID is considered illegal if its length is greater than or equal to 10 characters.
     * If the ID is illegal, an error alert will be displayed.
     * @param sID the ID to be checked
     * @return true if the ID is illegal, false otherwise
     */
    private boolean checkIdIllegal(String sID){
        if(sID.length() >= 10){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("The input data is illegal!");
            alert.showAndWait();
            return true;
        }
        return false;
    }
    /**
     * Display an alert dialog.
     *
     * @param title   The title of the alert dialog.
     * @param content The content text of the alert dialog.
     * @param header  The header text of the alert dialog.
     * @param type    The type of the alert dialog.
     */
    private void alert(String title, String content, String header, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    /**

     Represents a result of a course.

     Contains information such as the course ID, course name, course score,

     course grade point, course start term, course period, and course credit.
     */
    private static class CourseResults{
        private String cID;
        private String cName;
        private String cScore;
        private String cGradePoint;
        private String cStartTerm;
        private String cPeriod;
        private String cCredit;
        /**

         Constructs a new CourseResults object with the specified details.
         @param cID the ID of the course
         @param cMajor the major of the course
         @param cName the name of the course
         @param cType the type of the course
         @param cStartTerm the start term of the course
         @param cPeriod the period of the course
         @param cCredit the credit of the course
         */
         public CourseResults(String cID, String cMajor, String cName, String cType, String cStartTerm, String cPeriod, String cCredit) {
            this.cID = cID;
            this.cName = cMajor;
            this.cScore = cName;
            this.cGradePoint = cType;
            this.cStartTerm = cStartTerm;
            this.cPeriod = cPeriod;
            this.cCredit = cCredit;
        }
    }
    private static class LogOutResults{
        private boolean confirm;
        public LogOutResults(boolean confirm){
            this.confirm = confirm;
        }
    }
    /**

     Represents the result of a login operation.

     Contains the student ID and password.
     */
    private static class LoginResults{
        private String stuID;
        private String password;
        /**

         Represents the result of a login operation.

         Contains the student ID and password.
         */
        public LoginResults(String stuID,String password){
            this.stuID=stuID;
            this.password=password;
        }
    }
    /**

     Represents a result of an achievement.

     Contains information such as the ID, name, sex, birthdate, and major of the achiever.
     */
    private static class AchievementResult {
        String tID;
        String tName;
        String tSex;
        String tBirth;
        String tMajor;
        /**

         Constructs a new AchievementResult object with the specified details.
         @param tID the ID of the achiever
         @param tName the name of the achiever
         @param tSex the sex of the achiever
         @param tBirth the birthdate of the achiever
         @param tMajor the major of the achiever
         */
        public AchievementResult(String tID, String tName, String tSex, String tBirth, String tMajor) {
            this.tID = tID;
            this.tName = tName;
            this.tSex = tSex;
            this.tBirth = tBirth;
            this.tMajor = tMajor;
        }
    }
    public static class RoleResult{
        private String rID;
        private String rName;
        private String rTimeStart;
        private String rTimeEnd;
        public RoleResult(String rID,String rName,String rTimeStart,String rTimeEnd){
            this.rID=rID;
            this.rName=rName;
            this.rTimeStart=rTimeStart;
            this.rTimeEnd=rTimeEnd;
        }
    }
    public static class ModuleResult{
        private String moID;
        private String moName;
        private String moTime;
        private String moPosition;
        public ModuleResult(String moID,String moName,String moTime,String moPosition){
            this.moID=moID;
            this.moName=moName;
            this.moTime =moTime;
            this.moPosition=moPosition;
        }
    }
    private interface Task {
        void execute();
    }
    /**
     * Sets the visibility of a specific tab and corresponding table view.
     * The visibility is determined based on the selected table view.
     *
     * @param tab        The tab to set the visibility for.
     * @param tableView  The table view associated with the tab.
     */
    private void setTabVisible(Tab tab, TableView tableView){
        setTabAction(tab, new Task() {
            @Override
            public void execute() {
                if(tableView.equals(courseTableView)){
                    courseTableView.setVisible(true);
                    achievementTableView.setVisible(false);
                    pane_personal.setVisible(false);
                    RoleTableView.setVisible(false);
                    ModuleTableView.setVisible(false);
                }
                else if(tableView.equals(achievementTableView)){
                    courseTableView.setVisible(false);
                    achievementTableView.setVisible(true);
                    pane_personal.setVisible(false);
                    RoleTableView.setVisible(false);
                    ModuleTableView.setVisible(false);
                } else if (tableView.equals(informationview)) {
                    pane_personal.setVisible(true);
                    courseTableView.setVisible(false);
                    achievementTableView.setVisible(false);
                    RoleTableView.setVisible(false);
                    ModuleTableView.setVisible(false);
                } else if(tableView.equals(RoleTableView)) {
                    pane_personal.setVisible(false);
                    courseTableView.setVisible(false);
                    achievementTableView.setVisible(false);
                    RoleTableView.setVisible(true);
                    ModuleTableView.setVisible(false);
                }else if(tableView.equals(ModuleTableView)){
                    pane_personal.setVisible(false);
                    courseTableView.setVisible(false);
                    achievementTableView.setVisible(false);
                    RoleTableView.setVisible(false);
                    ModuleTableView.setVisible(true);
                }
            }
        });
    }
    /**
     * Sets an action to be executed when a specific tab is selected.
     * The action is defined by the provided task.
     *
     * @param tab   The tab to set the action for.
     * @param task  The task to be executed when the tab is selected.
     */
    private void setTabAction(Tab tab, Task task) {
        tab.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                task.execute();
            }
        });
    }
}