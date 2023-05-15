package service.impl;

import bean.Achievement;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AchievementServiceImplTest {

    @Test
    public void executeUpdate() {
        AchievementServiceImpl achievementService = new AchievementServiceImpl();
        achievementService.executeUpdate("2020213301","1","Running 5000 meters at night","Firth place","2022.05.02","International College");
        Achievement achievement = achievementService.get(1,"2020213301");
        assertEquals("Firth place",achievement.getAchLevel());
    }

    @Test
    public void save() {
        AchievementServiceImpl achievementService = new AchievementServiceImpl();
        achievementService.save("2020213301",new Achievement("21","Running 1000 meters at night","First place","2023.01.02","International College"));
        Achievement achievement = achievementService.get(21,"2020213301");
        assertEquals("First place",achievement.getAchLevel());
    }

    @Test
    public void delete() {
        AchievementServiceImpl achievementService = new AchievementServiceImpl();
        achievementService.delete("2020213301","21");
        assertNull(achievementService.get(21, "2020213301"));
    }

    @Test
    public void get() {
        AchievementServiceImpl achievementService = new AchievementServiceImpl();
        Achievement achievement = achievementService.get(1,"2020213301");
        assertEquals("Fifth place",achievement.getAchLevel());
    }

    @Test
    public void getAll() {
        AchievementServiceImpl achievementService = new AchievementServiceImpl();
        List<Achievement> achievements = achievementService.getAll("2020213301");
        assertEquals("Fifth place", achievements.get(0).getAchLevel());
    }
}