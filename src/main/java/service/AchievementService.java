package service;

import bean.Achievement;

import java.util.List;

public interface AchievementService {

    /**
     * Saves an achievement associated with the given ID.
     *
     * @param ID  User ID
     * @param ach Achievement object to be saved
     */
    public void save(String ID, Achievement ach);

    /**
     * Deletes an achievement with the specified admin ID and achievement ID.
     *
     * @param adminID Admin ID
     * @param achID   Achievement ID
     */
    public void delete(String adminID, String achID);

    /**
     * Updates an achievement with the specified achievement ID.
     *
     * @param achID Achievement ID
     * @param ach  Updated Achievement object
     */
    public void update(String achID, Achievement ach);

    /**
     * Retrieves an achievement with the specified achievement ID and admin.
     *
     * @param achID Achievement ID
     * @param admin Admin name
     * @return Achievement object if found, null otherwise
     */
    public Achievement get(int achID, String admin);

    /**
     * Retrieves all achievements associated with the specified admin ID.
     *
     * @param adminID Admin ID
     * @return List of Achievement objects associated with the admin ID
     */
    public List<Achievement> getAll(String adminID);
}
