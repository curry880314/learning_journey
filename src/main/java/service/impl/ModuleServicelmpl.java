package service.impl;

import bean.Achievement;
import bean.Module;
import service.ModuleService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class ModuleServicelmpl implements ModuleService {
    String fileName = "information/module.txt";
    @Override
    public void save(Module mod) {
        try{
            FileWriter writer = new FileWriter(fileName,true);
            PrintWriter printWriter = new PrintWriter(writer);
            String objStr= String.join(",",mod.getMoID()+","+mod.getMoName()+","
                    +mod.getMoTime()+","+
                    mod.getMoPosition()+"," +mod.getUsername());
            printWriter.println(objStr);
            printWriter.close();
        }catch (Exception e){
            System.err.println("Not find file"+e.getMessage());
        }
    }

    @Override
    public void delete(String adminID, String modID) {
        try {
            BufferedReader in=new BufferedReader(new FileReader(fileName));
            StringBuilder st=new StringBuilder();
            String line;
            while ((line=in.readLine())!=null){
                String []parts=line.split(",");
                if((modID.equals(parts[0]))&&(adminID.equals(parts[4]))){
                }
                else{
                    st.append(line);
                    st.append("\n");
                }

            }
            st.deleteCharAt(st.length()-1);
            FileWriter writer = new FileWriter(fileName);
            PrintWriter printWriter = new PrintWriter(writer);
            printWriter.println(st);
            in.close();
            printWriter.close();
        }catch (Exception e){

        }
    }

    @Override
    public void update( Module mod) {
        try {
            BufferedReader in=new BufferedReader(new FileReader(fileName));
            StringBuilder st=new StringBuilder();
            String line;
            while ((line=in.readLine())!=null){
                String []parts=line.split(",");
                if((mod.getMoID().equals(parts[0]))&&(mod.getUsername().equals(parts[4]))){
                    String objStr= String.join(",",mod.getMoID()+","+mod.getMoName()
                            +","+mod.getMoTime()+"," +mod.getMoPosition()+","+mod.getUsername());
                    st.append(objStr);
                }
                else{
                    st.append(line);

                }
                st.append("\n");
            }
            st.deleteCharAt(st.length()-1);
            FileWriter writer = new FileWriter(fileName);
            PrintWriter printWriter = new PrintWriter(writer);
            printWriter.println(st);
            in.close();
            printWriter.close();
        }catch (Exception e){

        }
    }

    @Override
    public Module get(int modID, String admin) {
        try {
            try {
                BufferedReader re = new BufferedReader(new FileReader(fileName));
                String line;
                String mod="";
                mod=String.valueOf(modID);
                while ((line= re.readLine())!=null){
                    String[]parts= line.split(",");
                    if(mod.equals(parts[0])&&admin.equals(parts[4])){
                        Module module=new Module(parts[0],parts[1],parts[2],parts[3],parts[4]);
                        return module;

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

    @Override
    public List<Module> getAll(String username){
        List<Module> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Module module = new Module(parts[0],parts[1],parts[2],parts[3],username);
                if(parts[4].equals(username)){
                    list.add(module);
                }
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
