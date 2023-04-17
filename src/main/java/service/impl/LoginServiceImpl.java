package service.impl;

import service.LoginService;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginServiceImpl implements LoginService {
    @Override
    public String getUser(int ID,int password) {
        String username="";
        String fileName = "information/user.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
               if(ID==Integer.parseInt(parts[1].trim())&&password==Integer.parseInt(parts[2].trim())){
                   username=parts[1];
               }
            }
        } catch (IOException e) {
           e.printStackTrace();
        }
        return username;
    }

}