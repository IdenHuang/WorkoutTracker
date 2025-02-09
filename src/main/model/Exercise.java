package model;

import org.json.JSONObject;

import persistence.Writable;

// Exercises include the name of the specifications of the exercise including the number of sets and how many reps
// with the weight that was used
public class Exercise implements Writable {

    int sets;
    int reps;
    String name;
    int weight;

    // REQUIRES: non-zero number of sets and positive weight, as well as an
    // non-empty string
    // EFFECTS: the name of the exercise is set to the name of the exercise with the
    // inputted number of sets done
    // as well as the number of repitions done with a specified weight

    public Exercise(int sets, int reps, String name, int weight) {
        this.sets = sets;
        this.reps = reps;
        this.name = name;
        this.weight = weight;
    }

    // Getters
    public int getSets() {
        return sets;
    }

    public int getReps() {
        return reps;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("sets", sets);
        json.put("reps", reps);
        json.put("name", name);
        json.put("weight", weight);
        return json;
    }

}
