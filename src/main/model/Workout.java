package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// A workout is essentially a list of exercises done in a day
public class Workout implements Writable {

    ArrayList<Exercise> exerciseList;

    // EFFECT: records the exercises into a workout list of a number of exercises
    public Workout() {
        exerciseList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECT: adds an exercise into the workout done
    public void addExercise(Exercise exercise) {
        exerciseList.add(exercise);
        EventLog.getInstance().logEvent(new Event("Exercise Added to Workout."));
    }

    // REQUIRES: non-empty array
    // EFFECTS: sums up the total amount of sets that were in the workout
    public int totalSets() {
        int sets;
        int total = 0;

        for (Exercise e : this.exerciseList) {
            sets = e.getSets();
            total = total + sets;
        }
        return total;
    }

    // Getters
    public ArrayList<Exercise> getExercisesList() {
        return exerciseList;
    }

    public int getNumExercises() {
        return exerciseList.size();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Exercise", exerciseToJson());
        return json;
    }

    // EFFECT: returns exercise in this workout as a JSON Array
    private JSONArray exerciseToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Exercise e : exerciseList) {
            jsonArray.put(e.toJson());
        }

        return jsonArray;
    }
}