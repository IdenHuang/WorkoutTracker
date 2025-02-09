package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WorkoutListTest {
    private Exercise testExercise1;
    private Exercise testExercise2;
    private Exercise testExercise3;
    private Workout testWorkout1;
    private Workout testWorkout2;
    private WorkoutList testWorkoutList;
    
    @BeforeEach
    void runBefore() {
        testExercise1 = new Exercise(5, 4, "Bench Press", 135);
        testExercise2 = new Exercise(4, 3, "Squats", 224);
        testExercise3 = new Exercise(4, 3, "Leg Press", 225);
        testWorkout1 = new Workout();
        testWorkout1.addExercise(testExercise1);
        testWorkout1.addExercise(testExercise2);
        testWorkout2 = new Workout();
        testWorkout2.addExercise(testExercise2);
        testWorkout2.addExercise(testExercise3);
        testWorkoutList = new WorkoutList();
    }

    @Test
    public void testConstructor() {
        assertEquals(0, testWorkoutList.getNumWorkouts());
    }

    @Test
    public void testAddWorkout() {
        testWorkoutList.addWorkout(testWorkout1);
        assertEquals(1, testWorkoutList.getNumWorkouts());
        assertTrue(testWorkoutList.getWorkoutList().contains(testWorkout1));
    }

    @Test
    public void testAddMultipleWorkouts() {
        testWorkoutList.addWorkout(testWorkout1);
        assertEquals(1, testWorkoutList.getNumWorkouts());
        assertTrue(testWorkoutList.getWorkoutList().contains(testWorkout1));
        testWorkoutList.addWorkout(testWorkout2);
        assertEquals(2, testWorkoutList.getNumWorkouts());
        assertTrue(testWorkoutList.getWorkoutList().contains(testWorkout2));
    }

    @Test
    public void testSortByWeight() {
        testWorkoutList.addWorkout(testWorkout1);
        testWorkoutList.addWorkout(testWorkout2);
    
        assertEquals(testWorkoutList.sortByWeight(10000).size(), 0);
        assertEquals(testWorkoutList.sortByWeight(200).size(), 2);
        assertEquals(testWorkoutList.sortByWeight(225).size(), 1);
    }

    @Test
    public void testSortByReps() {
        testWorkoutList.addWorkout(testWorkout1);
        testWorkoutList.addWorkout(testWorkout2);
    
        assertEquals(testWorkoutList.sortByReps(10000).size(), 0);
        assertEquals(testWorkoutList.sortByReps(0).size(), 2);
        assertEquals(testWorkoutList.sortByReps(4).size(), 1);
    }
}
