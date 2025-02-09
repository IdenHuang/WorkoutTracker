package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// The list of workouts that are recorded, this is the `Y` variable that is in arbitrary in length
public class WorkoutList implements Writable {

    ArrayList<Workout> workouts;

    // EFFECTS: constructor for the list of workouts
    public WorkoutList() {
        workouts = new ArrayList<>();
    }

    // EFFECTS: adds a workout to the end of the list of workouts
    public void addWorkout(Workout workout) {
        workouts.add(workout);
        EventLog.getInstance().logEvent(new Event("Workout added to a list of Workouts."));
    }

    // EFFECTS: returns a sorted list with the minimum weight
    public List<List<Exercise>> sortByWeight(int minweight) {
        List<List<Exercise>> sortedList = new ArrayList<>();
        for (Workout workout : workouts) {
            List<Exercise> sortedworkout = new ArrayList<>();
            for (Exercise e : workout.getExercisesList()) {
                if (e.getWeight() >= minweight) {
                    sortedworkout.add(e);
                }
            }
            if (!sortedworkout.isEmpty()) {
                sortedList.add(sortedworkout);
            }
        }

        EventLog.getInstance().logEvent(new Event("Viewed List sorted by Weight"));

        return sortedList;
    }

    // EFFECTS: returns a sorted list with the minimum reps
    public List<List<Exercise>> sortByReps(int minreps) {
        List<List<Exercise>> sortedList = new ArrayList<>();
        for (Workout workout : workouts) {
            List<Exercise> sortedworkout = new ArrayList<>();
            for (Exercise e : workout.getExercisesList()) {
                if (e.getReps() >= minreps) {
                    sortedworkout.add(e);
                }
            }
            if (!sortedworkout.isEmpty()) {
                sortedList.add(sortedworkout);
            }
        }

        EventLog.getInstance().logEvent(new Event("Viewed List sorted by Reps"));

        return sortedList;
    }

    // Getters
    public ArrayList<Workout> getWorkoutList() {
        return workouts;
    }

    public int getNumWorkouts() {
        return workouts.size();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Workout", workoutToJson());
        return json;
    }

    // EFFECT: returns workout in this workoutList in a JSON array
    private JSONArray workoutToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Workout w : workouts) {
            jsonArray.put(w.toJson());
        }

        return jsonArray;
    }
}
