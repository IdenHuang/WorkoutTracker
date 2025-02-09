package persistence;

import java.io.*;

import org.json.JSONObject;

import model.WorkoutList;

// JsosWriter writes data from Json into the program
public class JsonWriter {
    private PrintWriter writer;
    private static final int TAB = 4;
    private String destination;

    // EFFECTS: constructs a writer to write data from JSON file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens the writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of workoutlist to file
    public void write(WorkoutList workoutList) {
        JSONObject json = workoutList.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
