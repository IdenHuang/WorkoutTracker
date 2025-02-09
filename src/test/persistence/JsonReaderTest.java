package persistence;

import model.Exercise;
import model.Workout;
import model.WorkoutList;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noFile.json");
        try {
            WorkoutList workoutlist = reader.read();
            fail("IOException Expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReadEmptyWorkoutList() {
        JsonReader reader = new JsonReader("./data/testEmptyWorkoutListFile.json");
        try {
            WorkoutList workoutList = reader.read();
            assertEquals(0, workoutList.getWorkoutList().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderFullWorkoutList() {
        JsonReader reader = new JsonReader("./data/testReaderFullWorkoutListFile.json");
        Exercise testExercise1 = new Exercise(4, 12, "Leg Press", 225);
        Exercise testExercise2 = new Exercise(6, 12, "Leg Extensions", 80);

        Workout testWorkout1 = new Workout();
        testWorkout1.addExercise(testExercise1);
        Workout testWorkout2 = new Workout();
        testWorkout2.addExercise(testExercise2);
        try {
            WorkoutList workoutList = reader.read();
            ArrayList<Workout> workouts = workoutList.getWorkoutList();
            assertEquals(workouts.size(), 2);
            checkWorkout(testExercise1, workouts.get(0).getExercisesList().get(0));
            checkWorkout(testExercise2, workouts.get(1).getExercisesList().get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
