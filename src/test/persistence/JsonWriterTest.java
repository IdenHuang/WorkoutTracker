package persistence;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import model.Exercise;
import model.Workout;
import model.WorkoutList;

public class JsonWriterTest extends JsonTest {

    @Test
    public void testWriterInvalidFile() {
        try {
            WorkoutList workoutList = new WorkoutList();
            JsonWriter writer = new JsonWriter(".data/illegal\0:aaaafileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testWriterEmptyWorkoutList() {
        try {
            WorkoutList workoutList = new WorkoutList();
            JsonWriter writer = new JsonWriter("./data/testEmptyWorkoutListFile.json");
            writer.open();
            writer.write(workoutList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testEmptyWorkoutListFile.json");
            workoutList = reader.read();
            assertEquals(workoutList.getWorkoutList().size(), 0);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testWriteFullWorkout() {
        try {
            Exercise testExercise1 = new Exercise(6, 12, "Leg Extensions", 80);
            Exercise testExercise2 = new Exercise(4, 12, "Leg Press", 225);

            Workout testWorkout1 = new Workout();
            Workout testWorkout2 = new Workout();

            testWorkout1.addExercise(testExercise1);
            testWorkout2.addExercise(testExercise2);

            WorkoutList testWorkoutList = new WorkoutList();

            testWorkoutList.addWorkout(testWorkout1);
            testWorkoutList.addWorkout(testWorkout2);

            JsonWriter writer = new JsonWriter("./dataWriterFullWorkoutListFile.json");
            writer.open();
            writer.write(testWorkoutList);
            writer.close();

            JsonReader reader = new JsonReader("./dataWriterFullWorkoutListFile.json");
            testWorkoutList = reader.read();
            assertEquals(testWorkoutList.getNumWorkouts(), 2);
            checkWorkout(testWorkoutList.getWorkoutList().get(0).getExercisesList().get(0), testExercise1);
            checkWorkout(testWorkoutList.getWorkoutList().get(1).getExercisesList().get(0), testExercise2);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
