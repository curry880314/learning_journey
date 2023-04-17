package service.impl;

import bean.Role;
import service.RoleService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RoleServiceimpl implements RoleService {
    String fileName = "information/role.txt";
    public void executeUpdate(String rID,String rName,String rTimeStart,String rTimeEnd,String username){
        try {
            FileWriter writer = new FileWriter(fileName,true);
            PrintWriter printWriter = new PrintWriter(writer);
            String objStr= String.join(",",rID+","+rName+","+rTimeStart+","
                    + rTimeEnd+","+username);
            printWriter.println(objStr);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void save(Role role){
        executeUpdate(role.getRoID(),role.getRoName(),role.getRoTimeStart(),role.getRoTimeEnd(),role.getUsername());
    }
    @Override
    public void delete(String rId){
        String[]role;
        String[]role1;
        List<String> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String tempString;
            while ((tempString = reader.readLine()) != null) {
                list.add(tempString);
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        for (String delrole:list){
            role=delrole.split(",");
            if(rId.equals(role[0])){
                try {
                    list.remove(delrole);
                    FileWriter writer = new FileWriter(fileName);
                    writer.close();
                    break;
                }catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
        for (String uprole:list){
            role1=uprole.split(",");
            try {
                FileWriter writer = new FileWriter(fileName,true);
                writer.write(role1[0] + "," + role1[1] +
                        "," + role1[2] + "," + role1[3] +
                        "," + role1[4]);
                writer.write(System.getProperty("line.separator"));
                writer.close();
            }catch(IOException e) {
                e.printStackTrace();
            }
        }

    }
    @Override
    public void update(String rId, Role role) {
        delete(rId);
        executeUpdate(role.getRoID(),role.getRoName(),role.getRoTimeStart(),
                role.getRoTimeEnd(),role.getUsername());
    }
    @Override
    public Role get(String rID){
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if(rID.equals(parts[0].trim())){
                    Role newRole=new Role(parts[0],parts[1],parts[2],parts[3],parts[4]);
                    return newRole;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Role> getAll(String username){
        List<Role> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Role role = new Role(parts[0],parts[1],parts[2],parts[3],username);
                if(parts[4].equals(username)){
                    list.add(role);
                }
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
