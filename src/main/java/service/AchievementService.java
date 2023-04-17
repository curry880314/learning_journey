package service;

import bean.Achievement;

import java.util.List;

public interface AchievementService {

    public void save(String ID, Achievement ach);

    public void delete(String adminID, String achID);

    public void update(String achID, Achievement ach);

    public Achievement get(int achID, String admin);

    public List<Achievement> getAll(String adminID);
}
