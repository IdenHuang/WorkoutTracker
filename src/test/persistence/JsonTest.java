package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import model.Exercise;

public class JsonTest {

    protected void checkWorkout(Exercise e1, Exercise e2) {

        assertEquals(e2.getName(), e1.getName());
        assertEquals(e2.getSets(), e1.getSets());
        assertEquals(e2.getReps(), e1.getReps());
        assertEquals(e2.getWeight(), e2.getWeight());
    }

}
