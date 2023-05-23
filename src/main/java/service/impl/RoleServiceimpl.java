package service.impl;

import bean.Role;
import service.RoleService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the RoleService interface.
 */
public class RoleServiceimpl implements RoleService {
    String fileName = "information/role.txt";

    /**
     * Executes an update by appending a new role entry to the role file.
     *
     * @param rID        the ID of the role
     * @param rName      the name of the role
     * @param rTimeStart the start time of the role
     * @param rTimeEnd   the end time of the role
     * @param username   the username associated with the role
     */
    public void executeUpdate(String rID, String rName, String rTimeStart, String rTimeEnd, String username) {
        try {
            FileWriter writer = new FileWriter(fileName, true);
            PrintWriter printWriter = new PrintWriter(writer);
            String objStr = String.join(",", rID + "," + rName + "," + rTimeStart + "," + rTimeEnd + "," + username);
            printWriter.println(objStr);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves a role by executing an update with the role's information.
     *
     * @param role the role to save
     */
    @Override
    public void save(Role role) {
        executeUpdate(role.getRoID(), role.getRoName(), role.getRoTimeStart(), role.getRoTimeEnd(), role.getUsername());
    }

    /**
     * Deletes a role based on the provided role ID and username.
     *
     * @param rId      the ID of the role to delete
     * @param username the username associated with the role
     */
    @Override
    public void delete(String rId, String username) {
        String[] role;
        String[] role1;
        List<String> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String tempString;
            while ((tempString = reader.readLine()) != null) {
                list.add(tempString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String delrole : list) {
            role = delrole.split(",");
            if (rId.equals(role[0]) && username.equals(role[4])) {
                try {
                    list.remove(delrole);
                    FileWriter writer = new FileWriter(fileName);
                    writer.close();
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        for (String uprole : list) {
            role1 = uprole.split(",");
            try {
                FileWriter writer = new FileWriter(fileName, true);
                writer.write(role1[0] + "," + role1[1] +
                        "," + role1[2] + "," + role1[3] +
                        "," + role1[4]);
                writer.write(System.getProperty("line.separator"));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Updates a role by deleting the existing role and executing an update with the new role information.
     *
     * @param rId   the ID of the role to update
     * @param role  the new role information
     */
    @Override
    public void update(String rId, Role role) {
        delete(rId, role.getUsername());
        executeUpdate(role.getRoID(), role.getRoName(), role.getRoTimeStart(), role.getRoTimeEnd(), role.getUsername());
    }

    /**
     * Get a role based on the provided role ID and username.
     *
     * @param rID    the ID of the role to retrieve
     * @param radmin the username associated with the role
     * @return the Role object matching the ID and username, or null if not found
     */
    @Override
    public Role get(String rID, String radmin) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (rID.equals(parts[0].trim()) && radmin.equals(parts[4])) {
                    Role newRole = new Role(parts[0], parts[1], parts[2], parts[3], parts[4]);
                    return newRole;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get all roles associated with the provided username.
     *
     * @param username the username to search for
     * @return a list of Role objects associated with the username, or null if not found
     */
    @Override
    public List<Role> getAll(String username) {
        List<Role> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Role role = new Role(parts[0], parts[1], parts[2], parts[3], username);
                if (parts[4].equals(username)) {
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
