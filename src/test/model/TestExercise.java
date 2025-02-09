package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestExercise {
    private Exercise testExercise1;
    
    @BeforeEach
    void runBefore() {
        testExercise1 = new Exercise(5, 4, "Bench Press", 135);
    }

    @Test
    void testConstructor() {
        assertEquals(5, testExercise1.getSets());
        assertEquals(4, testExercise1.getReps());
        assertEquals("Bench Press", testExercise1.getName());
        assertEquals(135, testExercise1.getWeight());
    }
}
