package persistence;

import org.json.JSONObject;

// Credit: JSonSerializationDemo
public interface Writable {
    // EFFECTS: returns this as a JSON object
    JSONObject toJson();
}
