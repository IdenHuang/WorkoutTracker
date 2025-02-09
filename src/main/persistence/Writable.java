package persistence;

import org.json.JSONObject;

// Returns as a JSON object
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
