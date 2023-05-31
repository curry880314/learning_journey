package controller;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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

import java.io.*;
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
    List<Achievement> achievements = new ArrayList<>();
    PersonalInformation PI = null;
    List<Module> modules = new ArrayList<>();
    List<Role> roles = new ArrayList<>();

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

    /**
     * Displays a dialog box to enter role information and adds a new role.
     * If the admin is not logged in, an error message is shown.
     * The role information includes role number, name, start time, and end time.
     * Validates the entered information and performs necessary checks before adding the role.
     * Displays appropriate messages based on the outcome of the operation.
     * Refreshes the role table after adding a new role.
     */
    public void addRole() {
        // Check if the admin is logged in
        if (adminID.equals("0")) {
            alert("Hint", "Please log in first", null, Alert.AlertType.ERROR);
            return;
        }

        // Create a dialog box to enter role information
        Dialog<RoleResult> dialog = new Dialog<>();
        dialog.setTitle("Add Role");
        dialog.setHeaderText("Enter the role information to be added below:");

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Create a grid to layout the input fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 60, 10, 10));

        // Create text fields for role information
        TextField roID = new TextField();
        TextField roName = new TextField();
        TextField roTimeStart = new TextField();
        TextField roTimeEnd = new TextField();

        // Add labels and text fields to the grid
        grid.add(new Label("Number:"), 0, 0);
        grid.add(roID, 1, 0);
        grid.add(new Label("Role:"), 0, 1);
        grid.add(roName, 1, 1);
        grid.add(new Label("TimeStart:"), 0, 2);
        grid.add(roTimeStart, 1, 2);
        grid.add(new Label("TimeEnd:"), 0, 3);
        grid.add(roTimeEnd, 1, 3);

        // Set the grid as the content of the dialog
        dialog.getDialogPane().setContent(grid);

        // Define how the dialog results are converted
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
                return new RoleResult(roID.getText(), roName.getText(), roTimeStart.getText(), roTimeEnd.getText());
            }
            return null;
        });

        // Show the dialog and wait for user input
        Optional<RoleResult> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent((RoleResult results) -> {
            Role role = new RoleServiceimpl().get(results.rID, adminID);
            if (role != null) {
                alert("Hint", "Role number is【" + results.rID + "】data which is exist, unable to add!", null, Alert.AlertType.INFORMATION);
            } else {
                new RoleServiceimpl().save(new Role(results.rID, results.rName, results.rTimeStart, results.rTimeEnd, username));

                alert("Hint", "Successfully saved role number is【" + results.rID + "】role data!", null, Alert.AlertType.INFORMATION);
                refreshRoleTable();
            }
        });
    }

    /**
     * Displays a dialog box to modify role information.
     * If the admin is not logged in, an error message is shown.
     * Prompts the user to enter the role number to be modified.
     * Validates the entered role number and performs necessary checks before modifying the role.
     * Displays appropriate messages based on the outcome of the operation.
     * Refreshes the role table after modifying a role.
     */
    public void changeRole() {
        // Check if the admin is logged in
        if (adminID.equals("0")) {
            alert("Hint", "Please log in first", null, Alert.AlertType.ERROR);
            return;
        }

        // Create a text input dialog to enter the role number
        TextInputDialog d = new TextInputDialog();
        d.setTitle("Modify Role Information");
        d.setHeaderText("Enter the role number to modify the information:");
        d.setContentText("Role Number:");
        Optional<String> result = d.showAndWait();

        if (result.isPresent()) {
            if (checkIdIllegal(result.get())) {
                return;
            }
            Role role = new RoleServiceimpl().get(result.get(), adminID);
            if (null != role) {
                // Create a dialog box to display and modify role information
                Dialog<RoleResult> dialog = new Dialog<>();
                dialog.setTitle("Role Data");
                dialog.setHeaderText(null);

                DialogPane dialogPane = dialog.getDialogPane();
                dialogPane.getButtonTypes().addAll(ButtonType.OK);

                // Create a grid to layout the input fields
                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 60, 10, 10));

                // Create text fields for role information
                TextField rID = new TextField(role.getRoID());
                TextField rName = new TextField(role.getRoName());
                TextField rTimeStart = new TextField(role.getRoTimeStart());
                TextField rTimeEnd = new TextField(role.getRoTimeEnd());

                // Add labels and text fields to the grid
                grid.add(new Label("Role Number:"), 0, 0);
                grid.add(rID, 1, 0);
                grid.add(new Label("Role Name:"), 0, 1);
                grid.add(rName, 1, 1);
                grid.add(new Label("TimeStart:"), 0, 2);
                grid.add(rTimeStart, 1, 2);
                grid.add(new Label("TimeEnd:"), 0, 3);
                grid.add(rTimeEnd, 1, 3);

                // Set the grid as the content of the dialog
                dialog.getDialogPane().setContent(grid);

                // Define how the dialog results are converted
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
                        return new RoleResult(rID.getText(), rName.getText(), rTimeStart.getText(), rTimeEnd.getText());
                    }
                    return null;
                });

                // Show the dialog and wait for user input
                Optional<RoleResult> results = dialog.showAndWait();

                if (results.isPresent()) {
                    role = new Role(rID.getText(), rName.getText(), rTimeStart.getText(), rTimeEnd.getText(), username);
                    new RoleServiceimpl().update(result.get(), role);
                    alert("Hint", "Successfully modified role number is【" + role.getRoID() + "】role data!", null, Alert.AlertType.INFORMATION);
                    refreshRoleTable();
                }
            } else {
                alert("Hint", "There is no record of this role and it cannot be modified!", null, Alert.AlertType.ERROR);
            }
        }
    }


    /**
     * Displays a dialog box to delete a role.
     * If the admin is not logged in, an error message is shown.
     * Prompts the user to enter the role number to be deleted.
     * Validates the entered role number and performs necessary checks before deleting the role.
     * Displays appropriate messages based on the outcome of the operation.
     * Refreshes the role table after deleting a role.
     */
    public void deleteRole() {
        // Check if the admin is logged in
        if (adminID.equals("0")) {
            alert("Hint", "Please log in first", null, Alert.AlertType.ERROR);
            return;
        }

        // Create a text input dialog to enter the role number
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
     * Displays a dialog box to update the phone number.
     * If the admin is not logged in, an error message is shown.
     * Prompts the user to enter the new phone number.
     * Validates the entered phone number and performs necessary checks before updating it.
     * Displays appropriate messages based on the outcome of the operation.
     * Refreshes the personal information after updating the phone number.
     */
    public void UpdatePhoneNum() {
        if (adminID.equals("0")) {
            alert("Hint", "Please log in first", null, Alert.AlertType.ERROR);
            return;
        }

        // Create a dialog box to display and update the phone number
        Dialog<PersonalInformation> dialog = new Dialog<>();
        dialog.setTitle("Phone Number");
        dialog.setHeaderText(null);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 60, 10, 10));
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Create labels and text fields for old and new phone numbers
        Label OldPhoneNumber = new Label(new PersonalInformationImpl().getInformation(adminID).phoneNumber);
        TextField NewPhoneNumber = new TextField();

        // Add labels and text fields to the grid
        grid.add(new Label("Old Phone Number:"), 0, 0);
        grid.add(OldPhoneNumber, 1, 0);
        grid.add(new Label("New Phone Number:"), 0, 1);
        grid.add(NewPhoneNumber, 1, 1);

        // Set the grid as the content of the dialog
        dialog.getDialogPane().setContent(grid);

        // Define how the dialog results are converted
        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                String phoneNumber = NewPhoneNumber.getText();
                if (!isValidPhoneNumber(phoneNumber)) {
                    alert("Hint", "Phone number is in the wrong format", null, Alert.AlertType.WARNING);
                    return null;
                }
                return new PersonalInformation(adminID, "", "", "", "", "", "", phoneNumber);
            }
            return null;
        });

        // Show the dialog and wait for user input
        Optional<PersonalInformation> results = dialog.showAndWait();

        if (results.isPresent()) {
            PersonalInformationImpl PII = new PersonalInformationImpl();
            PII.executeUpdate(adminID, NewPhoneNumber.getText(), PII.getInformation(adminID).email);
            refreshPersonalInformation();
            alert("Hint", "Successfully modified Phone number is【" + NewPhoneNumber.getText() + "】!", null, Alert.AlertType.INFORMATION);
        }
    }



    /**
     * Displays a dialog box to update the email address.
     * If the admin is not logged in, an error message is shown.
     * Prompts the user to enter the new email address.
     * Validates the entered email address and performs necessary checks before updating it.
     * Displays appropriate messages based on the outcome of the operation.
     * Refreshes the personal information after updating the email address.
     */
    public void UpdateEmail() {
        if (adminID.equals("0")) {
            alert("Hint", "Please log in first", null, Alert.AlertType.ERROR);
            return;
        }

        // Create a dialog box to display and update the email address
        Dialog<PersonalInformation> dialog = new Dialog<>();
        dialog.setTitle("E-mail Number");
        dialog.setHeaderText(null);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 60, 10, 10));
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Create labels and text fields for old and new email addresses
        Label OldEmailNumber = new Label(new PersonalInformationImpl().getInformation(adminID).email);
        TextField NewEmailNumber = new TextField();

        // Add labels and text fields to the grid
        grid.add(new Label("Old E-mail Number:"), 0, 0);
        grid.add(OldEmailNumber, 1, 0);
        grid.add(new Label("New E-mail Number:"), 0, 1);
        grid.add(NewEmailNumber, 1, 1);

        // Set the grid as the content of the dialog
        dialog.getDialogPane().setContent(grid);

        // Define how the dialog results are converted
        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                String email = NewEmailNumber.getText();
                if (!isValidEmail(email)) {
                    alert("Hint", "Email is in the wrong format", null, Alert.AlertType.WARNING);
                    return null;
                }
                return new PersonalInformation(adminID, "", "", "", "", "", "", email);
            }
            return null;
        });

        // Show the dialog and wait for user input
        Optional<PersonalInformation> results = dialog.showAndWait();

        if (results.isPresent()) {
            PersonalInformationImpl PII = new PersonalInformationImpl();
            PII.executeUpdate(adminID, PII.getInformation(adminID).phoneNumber, NewEmailNumber.getText());
            refreshPersonalInformation();
            alert("Hint", "Successfully modified E-mail number is【" + NewEmailNumber.getText() + "】!", null, Alert.AlertType.INFORMATION);
        }
    }



    /**
     * Checks if a code is valid.
     *
     * @param code The code to be validated.
     * @return {@code true} if the code is valid, {@code false} otherwise.
     */
    public static boolean isValidCode(String code) {
        String pattern = "^EBU\\d{4}$";
        return code.matches(pattern);
    }




    /**
     * Checks if an email address is valid based on the defined regular expression pattern.
     *
     * @param email The email address to be validated.
     * @return {@code true} if the email address is valid, {@code false} otherwise.
     */
    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void exportCourseData() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Data");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );

        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file,true))) {

                for (Course c:courses) {
                    String data =c.getcID()+","+c.getcName()+","+c.getcScore()+","+c.getcStartTerm()+","+c.getcPeriod()+","+ c.getcCredit()+"\r\n";
                    writer.write(data);
                }

                System.out.println("Data exported to: " + file.getAbsolutePath());
            } catch (IOException e) {
                System.out.println("Failed to export data: " + e.getMessage());
            }
        }
    }
    public void exportAchievementData() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Data");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );

        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file,true))) {

                for (Achievement a:achievements) {
                    String data = a.getAchID()+","+a.getAchName()+","+a.getAchLevel()+","+a.getAchTime()+","+a.getAchMajor()+"\r\n";
                    writer.write(data);
                }

                System.out.println("Data exported to: " + file.getAbsolutePath());
            } catch (IOException e) {
                System.out.println("Failed to export data: " + e.getMessage());
            }
        }
    }
    public void exportModuleData() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Data");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );

        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file,true))) {

                for (Module m:modules) {
                    String data = m.getMoID()+","+m.getMoName()+","+m.getMoTime()+","+m.getMoPosition()+"\r\n";
                    writer.write(data);
                }
            } catch (IOException e) {
            }
        }
    }
    public void exportPersonalData() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Data");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );

        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file,true))) {
                    String data = PI.getName()+","+PI.getId()+","+PI.password+","+PI.phoneNumber+","+PI.email+","+PI.major+","+PI.college+"\r\n";
                    writer.write(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void exportRoleData() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Data");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );

        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file,true))) {
                for (Role r:roles) {
                    String data = r.getRoID()+","+r.getRoName()+","+r.getRoTimeStart()+","+r.getRoTimeEnd()+"\r\n";
                    writer.write(data);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




    /**
     * Checks if a phone number is valid based on the defined regular expression pattern.
     *
     * @param phoneNumber The phone number to be validated.
     * @return {@code true} if the phone number is valid, {@code false} otherwise.
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile(PHONE_NUMBER_REGEX);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }



    /**
     * Displays a dialog box to add a module with the provided information.
     * If the user is not logged in as an admin, an error message is displayed.
     * The user is prompted to enter the module information, and validation is performed
     * to ensure all fields are filled and the time format is valid.
     * If the module number already exists, an information message is shown.
     * Otherwise, the module is saved and a success message is displayed.
     * The module table is then refreshed.
     */
    public void addModule(){
        if(adminID.equals("0")) {
            alert("Hint","Please log in first",null, Alert.AlertType.ERROR);
            return;
        }

        // Create and configure the dialog
        Dialog<ModuleResult> dialog = new Dialog<>();
        dialog.setTitle("Add Module");
        dialog.setHeaderText("Enter the module information to be added below:：");

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20,60,10,10));

        // Create input fields for module information
        TextField moID = new TextField();
        TextField moName = new TextField();
        TextField moTime = new TextField();
        TextField moPosition = new TextField();

        // Add labels and input fields to the grid
        grid.add(new Label("Number:"), 0, 0);
        grid.add(moID, 1, 0);
        grid.add(new Label("Role:"), 0, 1);
        grid.add(moName, 1, 1);
        grid.add(new Label("Time:"), 0, 2);
        grid.add(moTime, 1, 2);
        grid.add(new Label("Position:"), 0, 3);
        grid.add(moPosition, 1, 3);

        dialog.getDialogPane().setContent(grid);

        // Set the result converter for the dialog
        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                // Retrieve input values from the text fields
                String moIDInput = moID.getText();
                String moNameInput = moName.getText();
                String moTimeInput = moTime.getText();
                String moPositionInput = moPosition.getText();

                // Perform validation checks
                if (isEmpty(moIDInput) || isEmpty(moNameInput) || isEmpty(moTimeInput) || isEmpty(moPositionInput)) {
                    alert("Hint", "Please fill in all the fields", null, Alert.AlertType.ERROR);
                    return null;
                }

                if (!isValidTime(moTimeInput)) {
                    alert("Hint", "Invalid time format. Please use the format yyyy.MM.dd", null, Alert.AlertType.ERROR);
                    return null;
                }

                // Return the module information as a ModuleResult object
                return new ModuleResult(moID.getText(), moName.getText(), moTime.getText(), moPosition.getText());
            }

            return null;
        });

        // Display the dialog and capture the result
        Optional<ModuleResult> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent((ModuleResult results) -> {
            // Retrieve the module with the specified ID
            Module module = new ModuleServicelmpl().get(Integer.parseInt(results.moID), adminID);

            if (module != null) {
                // Display an information message if the module already exists
                alert("Hint","Module number is " + "【" + results.moID + "】data which is exist, unable to add!", null, Alert.AlertType.INFORMATION);
            } else {
                // Save the new module and display a success message
                new ModuleServicelmpl().save(new Module(results.moID, results.moName, results.moTime, results.moPosition, adminID));
                alert("Hint","Successfully saved module number is【" + results.moID + "】role data!", null, Alert.AlertType.INFORMATION);
                refreshModuleTable();
            }
        });
    }

    /**
     * Prompts the user to enter a module ID and deletes the corresponding module if it exists.
     * If the user is not logged in as an admin, an error message is displayed.
     * The user is prompted to enter a module ID.
     * If the ID is valid and the module exists, it is deleted and a success message is shown.
     * If the module does not exist, an error message is displayed.
     * The module table is then refreshed.
     */
    public void deleteModule(){
        if(adminID.equals("0")) {
            alert("Hint","Please log in first",null, Alert.AlertType.ERROR);
            return;
        }

        // Create and configure the input dialog
        TextInputDialog d = new TextInputDialog();
        d.setTitle("Delete module");
        d.setHeaderText("Enter module Id to delete:");
        d.setContentText("ID:");
        Optional<String> result = d.showAndWait();

        // Check if the user entered a module ID
        if (result.isPresent()){
            // Validate the module ID
            if(checkIdIllegal(result.get())){
                return;
            }

            // Retrieve the module with the specified ID
            Module module = new ModuleServicelmpl().get(Integer.parseInt(result.get()),adminID);

            if(null != module){
                // Delete the module and display a success message
                new ModuleServicelmpl().delete(adminID,result.get());
                alert("Hint","Successfully deleted number is【" + module.getMoID() + "】's module data！",null, Alert.AlertType.INFORMATION);
                refreshModuleTable();
            } else {
                // Display an error message if the module does not exist
                alert("Hint","There is no record of this module and it cannot be deleted！",null, Alert.AlertType.ERROR);
            }
        }
    }

    /**
     * Prompts the user to enter a module ID and allows them to modify the corresponding module's information if it exists.
     * If the user is not logged in as an admin, an error message is displayed.
     * The user is prompted to enter a module ID.
     * If the ID is valid and the module exists, a dialog is displayed with the current module information.
     * The user can modify the module information and save the changes by clicking the OK button.
     * If all fields are filled and the time format is valid, the changes are applied and a success message is shown.
     * The module table is then refreshed.
     */
    public void changeModule(){
        if(adminID.equals("0")) {
            alert("Hint","Please log in first!",null, Alert.AlertType.ERROR);
            return;
        }

        // Create and configure the input dialog
        TextInputDialog d = new TextInputDialog();
        d.setTitle("Modify Module Information");
        d.setHeaderText("Enter the Module ID to modify the information：");
        d.setContentText("ID:");
        Optional<String> result = d.showAndWait();

        // Check if the user entered a module ID
        if(result.isPresent()){
            // Validate the module ID
            if(checkIdIllegal(result.get())){
                return;
            }

            // Retrieve the module with the specified ID
            Module module = new ModuleServicelmpl().get(Integer.parseInt(result.get()),adminID);
            if(null != module){
                // Create and configure the dialog to display the module information
                Dialog<ModuleResult> dialog = new Dialog<>();
                dialog.setTitle("Module Data");
                dialog.setHeaderText(null);

                DialogPane dialogPane = dialog.getDialogPane();
                dialogPane.getButtonTypes().addAll(ButtonType.OK);

                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20,60,10,10));

                // Create input fields and populate them with the current module information
                TextField moID = new TextField(module.getMoID());
                TextField moName = new TextField(module.getMoName());
                TextField moTime = new TextField(module.getMoTime());
                TextField moPosition = new TextField(module.getMoPosition());

                // Add labels and input fields to the grid
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
                        // Retrieve modified values from the input fields
                        String moIDInput = moID.getText();
                        String moNameInput = moName.getText();
                        String moTimeInput = moTime.getText();
                        String moPositionInput = moPosition.getText();

                        // Perform validation checks
                        if (isEmpty(moIDInput) || isEmpty(moNameInput) || isEmpty(moTimeInput) || isEmpty(moPositionInput)) {
                            alert("Hint", "Please fill in all the fields", null, Alert.AlertType.ERROR);
                            return null;
                        }

                        if (!isValidTime(moTimeInput)) {
                            alert("Hint", "Invalid time format. Please use the format yyyy.MM.dd", null, Alert.AlertType.ERROR);
                            return null;
                        }

                        // Return the modified module information
                        return new ModuleResult(moIDInput, moNameInput, moTimeInput, moPositionInput);
                    }

                    return null;
                });

                Optional<ModuleResult> results = dialog.showAndWait();

                if(results.isPresent()){
                    // Update the module with the modified information
                    Module mod=new Module(moID.getText(), moName.getText(), moTime.getText(), moPosition.getText(), adminID);
                    new ModuleServicelmpl().update(mod);
                    alert("Hint","Successfully modified the number is【" + mod.getMoID() + "】 module data！",null, Alert.AlertType.INFORMATION);
                    refreshModuleTable();
                }
            } else {
                // Display an error message if the module does not exist
                alert("Hint","There is no record of this module and cannot be modified！",null, Alert.AlertType.ERROR);
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
     * Initializes the controller and its associated UI elements when the corresponding view is loaded.
     * If the user is not logged in as an admin (adminID equals "0"), certain tabs and their associated tables are set to be visible.
     * Otherwise, the course table, achievement table, personal information, role table, and module table are refreshed and set to be visible.
     * This method is called automatically when the view is loaded.
     *
     * @param location   The URL of the FXML file for the view.
     * @param resources  The ResourceBundle for the view.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(adminID.equals("0")) {
            // Set certain tabs and their associated tables to be visible for non-admin users
            setTabVisible(tab_course, courseTableView);
            setTabVisible(tab_achievement, achievementTableView);
            setTabVisible(tab_information, informationview);
            setTabVisible(tab_role, RoleTableView);
            setTabVisible(tab_module, ModuleTableView);
            return;
        }

        // Refresh and set visible the course table, achievement table, personal information, role table, and module table
        refreshCourseTable();
        refreshAchTable();
        refreshPersonalInformation();
        refreshRoleTable();
        refreshModuleTable();

        setTabVisible(tab_course, courseTableView);
        setTabVisible(tab_information, informationview);
        setTabVisible(tab_achievement, achievementTableView);
        setTabVisible(tab_role, RoleTableView);
        setTabVisible(tab_module, ModuleTableView);
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

        achievements = new AchievementServiceImpl().getAll(adminID);
        ObservableList<Achievement> data = FXCollections.observableArrayList();
        for (Achievement achievement : achievements) {
            data.add(achievement);
        }
        achievementTableView.setItems(data);


    }


    /**
     * Refreshes the personal information displayed on the grid pane.
     */
    private void refreshPersonalInformation() {
        grid_pane_information.getChildren().clear();

        // Load and set the image
        String url = new PersonalInformationImpl().getInformation(adminID).imageUrl;
        Image image = new Image(url);
        imageView.setImage(image);
        imageView.setX(450);
        imageView.setY(30);
        imageView.setFitWidth(150);
        imageView.setFitHeight(128);
        imageView.setVisible(true);

        // Add labels to display personal information
        grid_pane_information.add(new Label("Student ID:"), 0, 1);
        grid_pane_information.add(new Label("Student Name:"), 0, 0);
        grid_pane_information.add(new Label("Major Name:"), 0, 2);
        grid_pane_information.add(new Label("Phone Number:"), 0, 3);
        grid_pane_information.add(new Label("E-mail Number:"), 0, 4);
        grid_pane_information.add(new Label("College Name:"), 0, 5);

        // Retrieve personal information
         PI = new PersonalInformationImpl().getInformation(adminID);

        // Add personal information labels to the grid pane
        grid_pane_information.add(new Label(PI.id), 1, 1);
        grid_pane_information.add(new Label(PI.name), 1, 0);
        grid_pane_information.add(new Label(PI.phoneNumber), 1, 3);
        grid_pane_information.add(new Label(PI.email), 1, 4);
        grid_pane_information.add(new Label(PI.major), 1, 2);
        grid_pane_information.add(new Label(PI.college), 1, 5);
    }

    /**
     * Refreshes the role table by populating it with the roles associated with the given admin ID.
     */
    private void refreshRoleTable() {
        // Set cell value factories for table columns
        roIDCol.setCellValueFactory(new PropertyValueFactory<>("roID"));
        roNameCol.setCellValueFactory(new PropertyValueFactory<>("roName"));
        roTimeStartCol.setCellValueFactory(new PropertyValueFactory<>("roTimeStart"));
        roTimeEndCol.setCellValueFactory(new PropertyValueFactory<>("roTimeEnd"));

        // Set custom cell factories for specific columns
        roNameCol.setCellFactory(tc -> {
            return getCell(roNameCol);
        });
        roTimeStartCol.setCellFactory(tc -> {
            return getCell(roTimeStartCol);
        });
        roTimeEndCol.setCellFactory(tc -> {
            return getCell(roTimeEndCol);
        });

        // Retrieve all roles associated with the admin ID
         roles = new RoleServiceimpl().getAll(adminID);

        // Create an observable list and add roles to it
        ObservableList<Role> data = FXCollections.observableArrayList();
        for (Role role : roles) {
            data.add(role);
        }

        // Set the data to the RoleTableView
        RoleTableView.setItems(data);
    }

    /**
     * Refreshes the module table by populating it with the modules associated with the given admin ID.
     */
    private void refreshModuleTable() {
        // Set cell value factories for table columns
        moIDCol.setCellValueFactory(new PropertyValueFactory<>("moID"));
        moNameCol.setCellValueFactory(new PropertyValueFactory<>("moName"));
        moTimeCol.setCellValueFactory(new PropertyValueFactory<>("moTime"));
        moPositionCol.setCellValueFactory(new PropertyValueFactory<>("moPosition"));

        // Set custom cell factories for specific columns
        moNameCol.setCellFactory(tc -> {
            return getCell(moNameCol);
        });
        moTimeCol.setCellFactory(tc -> {
            return getCell(moTimeCol);
        });
        moPositionCol.setCellFactory(tc -> {
            return getCell(moPositionCol);
        });

        // Retrieve all modules associated with the admin ID
        modules = new ModuleServicelmpl().getAll(adminID);

        // Create an observable list and add modules to it
        ObservableList<Module> data = FXCollections.observableArrayList();
        for (Module module : modules) {
            data.add(module);
        }

        // Set the data to the ModuleTableView
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

    /**
     * Represents a role result, containing information such as role ID, role name, start time, and end time.
     */
    public static class RoleResult {
        private String rID;
        private String rName;
        private String rTimeStart;
        private String rTimeEnd;

        /**
         * Constructs a RoleResult object with the specified role ID, role name, start time, and end time.
         *
         * @param rID         The role ID.
         * @param rName       The role name.
         * @param rTimeStart  The start time of the role.
         * @param rTimeEnd    The end time of the role.
         */
        public RoleResult(String rID, String rName, String rTimeStart, String rTimeEnd) {
            this.rID = rID;
            this.rName = rName;
            this.rTimeStart = rTimeStart;
            this.rTimeEnd = rTimeEnd;
        }
    }

    /**
     * Represents a module result, containing information such as module ID, module name, time, and position.
     */
    public static class ModuleResult {
        private String moID;
        private String moName;
        private String moTime;
        private String moPosition;

        /**
         * Constructs a ModuleResult object with the specified module ID, module name, time, and position.
         *
         * @param moID       The module ID.
         * @param moName     The module name.
         * @param moTime     The time of the module.
         * @param moPosition The position of the module.
         */
        public ModuleResult(String moID, String moName, String moTime, String moPosition) {
            this.moID = moID;
            this.moName = moName;
            this.moTime = moTime;
            this.moPosition = moPosition;
        }
    }

    private interface Task {
        void execute();
    }

    /**
     * Sets the visibility of a tab and corresponding table view.
     *
     * @param tab       The tab to set visibility for.
     * @param tableView The table view associated with the tab.
     */
    private void setTabVisible(Tab tab, TableView tableView) {
        setTabAction(tab, new Task() {
            @Override
            public void execute() {
                if (tableView.equals(courseTableView)) {
                    courseTableView.setVisible(true);
                    achievementTableView.setVisible(false);
                    pane_personal.setVisible(false);
                    RoleTableView.setVisible(false);
                    ModuleTableView.setVisible(false);
                } else if (tableView.equals(achievementTableView)) {
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
                } else if (tableView.equals(RoleTableView)) {
                    pane_personal.setVisible(false);
                    courseTableView.setVisible(false);
                    achievementTableView.setVisible(false);
                    RoleTableView.setVisible(true);
                    ModuleTableView.setVisible(false);
                } else if (tableView.equals(ModuleTableView)) {
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
     * Sets the action to be executed when a tab is selected.
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