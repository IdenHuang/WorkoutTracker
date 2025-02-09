package ui;

import model.EventLog;
import model.Exercise;
import model.Event;
import model.Workout;
import model.WorkoutList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Workout Tracker Application
public class WorkoutApp {
    private static final String JSON_STORE = "./data/WorkoutList.json";
    private WorkoutList workoutList;
    private Scanner input;
    Exercise exercise;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the workout app
    public WorkoutApp() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: proccesses user input from the terminal
    public void runApp() {
        boolean keepGoing = true;
        String command = null;
        workoutList = new WorkoutList();

        input = new Scanner(System.in);

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("d")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\n See you next time!");

    }

    // EFFECTS: displays the main menu options
    private void displayMenu() {
        System.out.println("------------------------------");
        System.out.println("Welcome to Workout Tracker");
        System.out.println("q -> Log a Workout");
        System.out.println("w -> View Workouts");
        System.out.println("r -> Find Total Sets");
        System.out.println("s -> Save Workouts to File");
        System.out.println("l -> Load Workouts from File");
        System.out.println("d -> Quit");
        System.out.println("------------------------------");
    }

    // MODIFIES: this
    // EFFECTS: procceses the user input
    private void processCommand(String command) {
        if (command.equals("q")) {
            logWorkout();
        } else if (command.equals("w")) {
            viewWorkouts();
        } else if (command.equals("r")) {
            calcTotal();
        } else if (command.equals("s")) {
            saveWorkoutList();
        } else if (command.equals("l")) {
            loadWorkoutList();
        } else {
            System.out.println("Invalid Selection...");
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a workout and logs exercises
    private void logWorkout() {
        Workout workout = new Workout();
        Boolean keepGoing = true;
        String command = null;

        while (keepGoing) {
            displayWorkoutOptions();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("n")) {
                keepGoing = false;
            } else if (command.equals("y")) {
                workout.addExercise(processWorkoutOptions(command));
                System.out.println("Exercise Added!");
            } else {
                System.out.println("Invalid Selection...");
            }
        }

        if (!workout.getExercisesList().isEmpty()) {
            workoutList.addWorkout(workout);
        }
        displayMenu();
    }

    // MODIFIES: this
    // EFFECTS: processes the workout options, if the inputted integer values for
    // sets, reps or weight is negaitve
    // then 0 will be assigned instead
    private Exercise processWorkoutOptions(String commandString) {
        String name;
        int sets;
        int reps;
        int weight;
        Exercise exercise;

        System.out.println("What is the name of the exercise:");
        input.nextLine();
        name = input.nextLine();

        System.out.println("How many sets?:");
        sets = checkNegative(input.nextInt());
        System.out.println("How many reps:");
        reps = checkNegative(input.nextInt());
        System.out.println("What was the weight:");
        weight = checkNegative(input.nextInt());

        exercise = new Exercise(sets, reps, name, weight);
        return exercise;
    }

    // EFFECST: check if the integer is negative, returns original value if it
    // positive, otherwise returns 0
    private int checkNegative(int value) {
        if (value < 0) {
            return 0;
        } else {
            return value;
        }
    }

    // EFFECTS: displays the options to add exercises to a workout
    private void displayWorkoutOptions() {
        System.out.println("Would you like to log an exercise?");
        System.out.println("y/n");
    }

    // EFFECTS: prints all the workouts
    private void viewWorkouts() {
        int i = 1;
        for (Workout workout : workoutList.getWorkoutList()) {
            System.out.println("\nWorkout Number: " + i);
            i++;
            for (Exercise exercise : workout.getExercisesList()) {
                System.out.println("Name:" + exercise.getName() + " Sets:" + exercise.getSets()
                        + " Reps:" + exercise.getReps() + " Weight:" + exercise.getWeight());
            }
        }
    }

    // EFFECTS: prints out the volume/sets of all of the workouts done in each week.
    private void calcTotal() {
        int total = 0;
        int sets;
        for (Workout workout : workoutList.getWorkoutList()) {
            sets = workout.totalSets();
            total = total + sets;
        }

        System.out.println("Total Number of Sets: " + total);
    }

    // EFFECTS: saves the workouts to file
    private void saveWorkoutList() {
        try {
            jsonWriter.open();
            jsonWriter.write(workoutList);
            jsonWriter.close();
            System.out.println("Saved Workouts to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write file to " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workouts from file
    private void loadWorkoutList() {
        try {
            workoutList = jsonReader.read();
            System.out.println("Loaded Workouts from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read file from " + JSON_STORE);
        }
    }

    // EFFECTS: prints the list of events
    static void printLog(EventLog eventLog) {
        for (Event e : eventLog) { 
            System.out.println(e.toString());
        }
    }
}
