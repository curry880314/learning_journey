package service.impl;

import bean.Achievement;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AchievementServiceImplTest {

    @Test
    public void executeUpdate() {
        // Creating an instance of AchievementServiceImpl
        AchievementServiceImpl achievementService = new AchievementServiceImpl();

        // Executing the update operation
        achievementService.executeUpdate("2020213301", "1", "Running 5000 meters at night", "Firth place", "2022.05.02", "International College");

        // Retrieving the updated achievement
        Achievement achievement = achievementService.get(1, "2020213301");

        // Asserting that the achievement level is as expected
        assertEquals("Firth place", achievement.getAchLevel());
    }

    @Test
    public void save() {
        // Creating an instance of AchievementServiceImpl
        AchievementServiceImpl achievementService = new AchievementServiceImpl();

        // Saving a new achievement
        achievementService.save("2020213301", new Achievement("21", "Running 1000 meters at night", "First place", "2023.01.02", "International College"));

        // Retrieving the saved achievement
        Achievement achievement = achievementService.get(21, "2020213301");

        // Asserting that the achievement level is as expected
        assertEquals("First place", achievement.getAchLevel());
    }

    @Test
    public void delete() {
        // Creating an instance of AchievementServiceImpl
        AchievementServiceImpl achievementService = new AchievementServiceImpl();

        // Deleting an achievement
        achievementService.delete("2020213301", "21");

        // Verifying that the deleted achievement is not present
        assertNull(achievementService.get(21, "2020213301"));
    }

    @Test
    public void get() {
        // Creating an instance of AchievementServiceImpl
        AchievementServiceImpl achievementService = new AchievementServiceImpl();

        // Retrieving an achievement
        Achievement achievement = achievementService.get(1, "2020213301");

        // Asserting that the achievement level is as expected
        assertEquals("Fifth place", achievement.getAchLevel());
    }

    @Test
    public void getAll() {
        // Creating an instance of AchievementServiceImpl
        AchievementServiceImpl achievementService = new AchievementServiceImpl();

        // Retrieving all achievements for a specific user
        List<Achievement> achievements = achievementService.getAll("2020213301");

        // Asserting that the first achievement level is as expected
        assertEquals("Fifth place", achievements.get(0).getAchLevel());
    }

}