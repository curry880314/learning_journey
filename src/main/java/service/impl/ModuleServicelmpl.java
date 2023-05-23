package service.impl;

import bean.Achievement;
import bean.Module;
import service.ModuleService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Implementation of the ModuleService interface.
 */
public class ModuleServicelmpl implements ModuleService {
    String fileName = "information/module.txt";

    /**
     * Saves a module by appending a new module entry to the module file.
     *
     * @param mod the module to save
     */
    @Override
    public void save(Module mod) {
        try {
            FileWriter writer = new FileWriter(fileName, true);
            PrintWriter printWriter = new PrintWriter(writer);
            String objStr = String.join(",", mod.getMoID() + "," + mod.getMoName() + ","
                    + mod.getMoTime() + "," + mod.getMoPosition() + "," + mod.getUsername());
            printWriter.println(objStr);
            printWriter.close();
        } catch (Exception e) {
            System.err.println("Not find file" + e.getMessage());
        }
    }

    /**
     * Deletes a module based on the provided admin ID and module ID.
     *
     * @param adminID the admin ID associated with the module
     * @param modID   the ID of the module to delete
     */
    @Override
    public void delete(String adminID, String modID) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            StringBuilder st = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                String[] parts = line.split(",");
                if ((modID.equals(parts[0])) && (adminID.equals(parts[4]))) {
                } else {
                    st.append(line);
                    st.append("\n");
                }
            }
            st.deleteCharAt(st.length() - 1);
            FileWriter writer = new FileWriter(fileName);
            PrintWriter printWriter = new PrintWriter(writer);
            printWriter.println(st);
            in.close();
            printWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates a module based on the provided module information.
     *
     * @param mod the updated module information
     */
    @Override
    public void update(Module mod) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            StringBuilder st = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                String[] parts = line.split(",");
                if ((mod.getMoID().equals(parts[0])) && (mod.getUsername().equals(parts[4]))) {
                    String objStr = String.join(",", mod.getMoID() + "," + mod.getMoName()
                            + "," + mod.getMoTime() + "," + mod.getMoPosition() + "," + mod.getUsername());
                    st.append(objStr);
                } else {
                    st.append(line);
                }
                st.append("\n");
            }
            st.deleteCharAt(st.length() - 1);
            FileWriter writer = new FileWriter(fileName);
            PrintWriter printWriter = new PrintWriter(writer);
            printWriter.println(st);
            in.close();
            printWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get a module based on the provided module ID and admin username.
     *
     * @param modID the ID of the module to retrieve
     * @param admin the admin username associated with the module
     * @return the Module object matching the ID and admin username, or null if not found
     */
    @Override
    public Module get(int modID, String admin) {
        try {
            try {
                BufferedReader re = new BufferedReader(new FileReader(fileName));
                String line;
                String mod = String.valueOf(modID);
                while ((line = re.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (mod.equals(parts[0]) && admin.equals(parts[4])) {
                        Module module = new Module(parts[0], parts[1], parts[2], parts[3], parts[4]);
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

    /**
     * Get all modules associated with the provided username.
     *
     * @param username the username to search for
     * @return a list of Module objects associated with the username, or null if not found
     */
    @Override
    public List<Module> getAll(String username) {
        List<Module> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Module module = new Module(parts[0], parts[1], parts[2], parts[3], username);
                if (parts[4].equals(username)) {
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

