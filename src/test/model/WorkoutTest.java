package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WorkoutTest {
    private Exercise testExercise1;
    private Exercise testExercise2;
    private Workout testWorkout;

    @BeforeEach
    void runBefore() {
        testExercise1 = new Exercise(5, 4, "Bench Press", 135);
        testExercise2 = new Exercise(4, 3, "Squats", 225);
        testWorkout = new Workout();
    }

    @Test
    public void testConstructor() {
        assertEquals(testWorkout.getNumExercises(), 0);
    }

    @Test
    public void testTotalSets() {
        assertEquals(testWorkout.totalSets(), 0);
        testWorkout.addExercise(testExercise1);
        assertEquals(testWorkout.totalSets(), 5);
    }

    @Test
    public void testAddExercise() {
        assertEquals(testWorkout.getNumExercises(), 0);
        testWorkout.addExercise(testExercise1);
        assertEquals(testWorkout.getNumExercises(), 1);
        assertTrue(testWorkout.getExercisesList().contains(testExercise1));
    }

    @Test
    public void testAddMultipleExercises() {
        testWorkout.addExercise(testExercise1);
        assertTrue(testWorkout.getExercisesList().contains(testExercise1));
        assertEquals(testWorkout.getNumExercises(), 1);
        testWorkout.addExercise(testExercise2);
        assertTrue(testWorkout.getExercisesList().contains(testExercise2));
        assertEquals(testWorkout.getNumExercises(), 2);
    }
}
