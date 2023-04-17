package service.impl;

import service.CourseService;
import bean.Course;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CourseServiceImpl implements CourseService {
    String fileName = "information/course.txt";
    public String CalculateGradePoint(String score) {
        float courseScore = Float.parseFloat(score);
        if(courseScore>=90)
            return "4.0";
        else if(courseScore<90&&courseScore>=87)
            return "3.7";
        else if(courseScore<87&&courseScore>=84)
            return "3.3";
        else if(courseScore<84&&courseScore>=80)
            return "3.0";
        else if(courseScore<80&&courseScore>=77)
            return "2.7";
        else if(courseScore<77&&courseScore>=74)
            return "2.3";
        else if(courseScore<74&&courseScore>=70)
            return "2.0";
        else if(courseScore<70&&courseScore>=67)
            return "1.7";
        else if(courseScore<67&&courseScore>=64)
            return "1.3";
        else if(courseScore<64&&courseScore>=60)
            return "1.0";
        else
            return "0";
    }
    public void executeUpdate(String cID, String cName, String cScore, String cStartTerm,
                             String cPeriod, String cCredit,String username) {
                    try {
                        FileWriter writer = new FileWriter(fileName,true);
                        PrintWriter printWriter = new PrintWriter(writer);
                        String objStr= String.join(",",cID+","+cName+","+cScore+","+
                                CalculateGradePoint(cScore)+"," +cStartTerm+","+
                                cPeriod+","+cCredit+","+username);
                        printWriter.println(objStr);
                        printWriter.close();
                    } catch (IOException e) {
                            e.printStackTrace();
                    }

        }
    @Override
    public void save(Course course) {
        executeUpdate(course.getcID(),course.getcName(),course.getcScore(),
                course.getcStartTerm(), course.getcPeriod(), course.getcCredit(),course.getUsername());
    }

    @Override
    public void delete(String cId) {
        String[]course;
        String[]course1;
        List<String> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String tempString;
            while ((tempString = reader.readLine()) != null) {
            list.add(tempString);
            }
        } catch (IOException e) {
           e.printStackTrace();

        }
            for (String delcourse:list){
             course=delcourse.split(",");
            if(cId.equals(course[0])){
                try {
                    list.remove(delcourse);
                    FileWriter writer = new FileWriter(fileName);
                    writer.close();
                    break;
                }catch(IOException e) {
                    e.printStackTrace();
                    }
                }
            }
            for (String upcourse:list){
                course1=upcourse.split(",");
                try {
                    FileWriter writer = new FileWriter(fileName,true);
                    writer.write(course1[0] + "," + course1[1] +
                                    "," + course1[2] + "," + course1[3] +
                                    "," + course1[4] + "," + course1[5] +
                                    "," + course1[6]+","+course1[7]);
                    writer.write(System.getProperty("line.separator"));
                    writer.close();
                }catch(IOException e) {
                    e.printStackTrace();
                }
            }
    }

    @Override
    public void update(String cId, Course course) {
        delete(cId);
        executeUpdate(course.getcID(),course.getcName(),course.getcScore(),
                course.getcStartTerm(), course.getcPeriod(), course.getcCredit(),course.getUsername());
    }

    @Override
    public Course get(String cId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if(cId.equals(parts[0].trim())){
                    Course newCourse=new Course(parts[0],parts[1],parts[2],parts[3],parts[4],parts[5],parts[6], parts[7]);
                    return newCourse;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
       return null;
    }

    @Override
    public List<Course> getAll(String username) {
        List<Course> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Course course = new Course(parts[0],parts[1],parts[2],parts[3],parts[4],parts[5],parts[6], username);
                if(parts[7].equals(username)){
                    list.add(course);
                }
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}