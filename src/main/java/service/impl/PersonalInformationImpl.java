package service.impl;

import bean.Course;
import bean.PersonalInformation;
import service.PersonalInformationService;

import java.awt.print.PrinterJob;
import java.io.*;
import java.util.ArrayList;


/**
 * Implementation of the PersonalInformationService interface.
 */
public class PersonalInformationImpl implements PersonalInformationService {
    String fileName = "information/user.txt";

    /**
     * Get personal information based on the provided ID.
     *
     * @param ID the ID to search for
     * @return the PersonalInformation object matching the ID, or null if not found
     */
    public PersonalInformation getInformation(String ID){
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if(ID.equals(parts[1].trim())){
                    return new PersonalInformation(parts[1],parts[0],parts[2],parts[3],parts[4],parts[5],parts[6],parts[7]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Updates the phone number and email of the personal information with the provided ID.
     *
     * @param ID          the ID of the personal information to update
     * @param PhoneNumber the new phone number
     * @param Email       the new email
     */
    public void executeUpdate(String ID, String PhoneNumber, String Email) {
        ArrayList<PersonalInformation> list = new ArrayList<>();
        try {
            PersonalInformation pi = getInformation(ID);
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String part;
                while ((part = reader.readLine()) != null) {
                    String[] parts = part.split(",");
                    if(ID.equals(parts[1].trim())){
                        list.add(new PersonalInformation(parts[1],parts[0],parts[2],PhoneNumber,Email,parts[5],parts[6],parts[7]));
                    }else {
                        list.add(new PersonalInformation(parts[1], parts[0], parts[2], parts[3], parts[4], parts[5], parts[6],parts[7]));
                    }
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            FileWriter writerClear = new FileWriter(fileName);
            PrintWriter printWriterClear = new PrintWriter(writerClear);
            printWriterClear.write("");
            writerClear.close();
            FileWriter writer = new FileWriter(fileName,true);
            PrintWriter printWriter = new PrintWriter(writer);
            for (PersonalInformation PI:list) {
                printWriter.write(PI.name+","+PI.id+","+PI.password+","+PI.phoneNumber+","+PI.email+","+PI.major+","+PI.college+","+PI.imageUrl+"\r\n");
            }
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

