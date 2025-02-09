package persistence;

import model.Exercise;
import model.Workout;
import model.WorkoutList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;


// Referenced from Json Serialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a reader that reads in a WorkoutList from a JSON data file
public class     JsonReader {
    private String source;

    // EFFECTS: constructs JsonReader to read from a source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: Reads WorkutList and returns, throws an IOException if an error occurs with reading the file
    public WorkoutList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorkoutList(jsonObject);
    }

    // EFFECTS: Reads source file as a string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: adds WorkoutList from JSON and returns it
    private WorkoutList parseWorkoutList(JSONObject jsonObject) {
        WorkoutList workoutList = new WorkoutList();
        addWorkoutList(workoutList, jsonObject);
        return workoutList;
    }

    // MODIFIES: workoutlist
    // EFFECTS: adds workouts from JSON and returns it
    private void addWorkoutList(WorkoutList workoutList, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Workout");
        for (Object json : jsonArray) {
            JSONObject nextWorkout = (JSONObject) json;
            addWorkout(workoutList, nextWorkout);
        }
    }

    // MODIFIES: workoutlist
    // EFFECTS: adds exercises from JSON and returns it
    private void addWorkout(WorkoutList workoutList, JSONObject jsonObect) {
        Workout workout = new Workout();

        JSONArray jsonArray = jsonObect.getJSONArray("Exercise");
        for (Object json : jsonArray) {
            JSONObject nextExercise = (JSONObject) json;
            addExercise(workout, nextExercise);
        }

        workoutList.addWorkout(workout);
    }

    // MODIFIES: workoutlist
    // EFFECTSL adds exercises to a workout from JSON and add it to workoutlist
    private void addExercise(Workout workout, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int sets = jsonObject.getInt("sets");
        int reps = jsonObject.getInt("reps");
        int weight = jsonObject.getInt("weight");

        Exercise exercise = new Exercise(sets, reps, name, weight);

        workout.addExercise(exercise);
    }
}
