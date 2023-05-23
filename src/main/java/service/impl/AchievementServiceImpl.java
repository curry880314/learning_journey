package service.impl;

import bean.Achievement;
import service.AchievementService;


import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AchievementServiceImpl implements AchievementService {
    String filename="information/achievement.txt";
    /**
     * Executes an update operation.
     *
     * @param ID       User ID
     * @param achID    Achievement ID
     * @param achName  Achievement name
     * @param achLevel Achievement level
     * @param achTime  Achievement time
     * @param achMajor Achievement major
     */
    public void executeUpdate(String ID, String achID,String achName, String achLevel,String achTime,String achMajor) {
        try {
            BufferedReader in=new BufferedReader(new FileReader(filename));
            StringBuilder st=new StringBuilder();
            String line;

            while ((line=in.readLine())!=null){
                String []parts=line.split(",");
                if((achID.equals(parts[0]))&&(ID.equals(parts[5]))){
                    String objStr= String.join(",",achID+","+achName
                            +","+achLevel+"," +achTime+","+
                            achMajor+","+ID);
                    st.append(objStr);
                }
                else{
                    st.append(line);
                }
                st.append("\n");
            }
            st.deleteCharAt(st.length()-1);
            FileWriter writer = new FileWriter(filename);
            PrintWriter printWriter = new PrintWriter(writer);
            printWriter.println(st);
            in.close();
            printWriter.close();
        }catch (Exception e){

        }

    }

    /**
     * Adds an achievement based on the ID.
     *
     * @param ID  User ID
     * @param ach Achievement object to be saved
     */
    @Override
    public void save(String ID, Achievement ach) {
        try{
            FileWriter writer = new FileWriter(filename,true);
            PrintWriter printWriter = new PrintWriter(writer);
            String objStr= String.join(",",ach.getAchID()+","+ach.getAchName()+","
                    +ach.getAchLevel()+","+ach.getAchTime()+","+
                   ach.getAchMajor()+"," +ID);
            printWriter.println(objStr);
            printWriter.close();
        }catch (Exception e){
            System.err.println("没有找到文件"+e.getMessage());
        }
    }
    /**
     * Deletes an achievement based on adminID and achID.
     *
     * @param adminID Admin ID
     * @param achID   Achievement ID
     */
    @Override
    public void delete(String adminID, String achID ){
        try {
            BufferedReader in=new BufferedReader(new FileReader(filename));
            StringBuilder st=new StringBuilder();
            String line;
            while ((line=in.readLine())!=null){
                String []parts=line.split(",");
                if((achID.equals(parts[0]))&&(adminID.equals(parts[5]))){
                }
                else{
                    st.append(line);
                    st.append("\n");
                }

            }
            st.deleteCharAt(st.length()-1);
            FileWriter writer = new FileWriter(filename);
            PrintWriter printWriter = new PrintWriter(writer);
            printWriter.println(st);
            in.close();
            printWriter.close();
        }catch (Exception e){

        }
    }
    /**
     * Updates an achievement based on the achievement ID.
     *
     * @param achID Achievement ID
     * @param ach   Updated Achievement object
     */
    @Override
    public void update(String achID, Achievement ach) {
        executeUpdate(achID,ach.getAchID(),ach.getAchName(),ach.getAchLevel(),
                ach.getAchTime(), ach.getAchMajor());
    }
    /**
     * Retrieves an achievement based on the achievement ID and admin ID.
     *
     * @param achID Achievement ID
     * @param admin Admin ID
     * @return Achievement object if found, null otherwise
     */
    @Override
    public Achievement get(int achID, String admin) {
        try {
            try {
                BufferedReader re = new BufferedReader(new FileReader(filename));
                String line;
                while ((line= re.readLine())!=null){
                    String[]parts= line.split(",");
                    String tnub="";
                    tnub=String.valueOf(achID);
                    if(tnub.equals(parts[0])&&admin.equals(parts[5])){
                        Achievement achievement=new Achievement(parts[0],parts[1],parts[2],parts[3],parts[4]);
                        return achievement;
                    }

                }
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Retrieves a list of achievements based on the admin ID.
     *
     * @param adminID Admin ID
     * @return List of Achievement objects
     */
    @Override
    public List<Achievement> getAll(String adminID) {
        List<Achievement> achievement = new ArrayList<>();
        try (BufferedReader re = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = re.readLine()) != null) {
                String[] parts = line.split(",");
                Achievement achieve = new Achievement(parts[0], parts[1], parts[2], parts[3],parts[4]);
                if (parts[5].equals(adminID)) {
                    achievement.add(achieve);
                }
            }
            re.close();
            return achievement;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
