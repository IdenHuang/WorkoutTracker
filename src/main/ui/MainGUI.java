package ui;

import javax.swing.*;

import model.EventLog;
import model.Exercise;
import model.Workout;
import model.WorkoutList;
import persistence.JsonReader;
import persistence.JsonWriter;
import java.util.List;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

// GUI for WorkoutTracker program
public class MainGUI extends JFrame implements ActionListener {
    private Button addWorkout;
    private JPanel background;
    private JFrame workoutFrame;
    private JTextField exerciseName;
    private JTextField setsInput = new JTextField();
    private JTextField repsInput = new JTextField();
    private JTextField weightInput = new JTextField();
    private JPanel workoutPanel;
    private JFrame summaryFrame;
    private JPanel summaryPanel;
    private JTextField minWeightInput;
    private JTextField minRepsInput;

    private int minweight;
    private int minreps;

    private Button viewWorkouts;
    private Button totalSets;
    private Button save;
    private Button load;
    private Button close;
    private Button weightMin;
    private Button repsMin;
    private Button add;

    private int width = 800;
    private int height = 750;

    private static final String JSON_STORE = "./data/WorkoutList.json";
    private WorkoutList workoutList;
    private Exercise exercise;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Workout workout;
    private ImageIcon dumbellimage = new ImageIcon("dumbell.jpg");
    private ImageIcon thumbsupimage = new ImageIcon("thumbsup.jpg");

    // EFFECT: assigns and initializes
    public MainGUI() {
        workoutList = new WorkoutList();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        initialize();
    }

    // MODIFIES: this
    // EFFECT: initializes background and sets layout and adds elements to the page
    public void initialize() {
        setTitle("Workout Tracker");

        background = new JPanel();
        background.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        Image dumbellimage2 = dumbellimage.getImage().getScaledInstance(150, 150, Image.SCALE_AREA_AVERAGING);
        ImageIcon dumbellImageIcon = new ImageIcon(dumbellimage2);

        JLabel pic = new JLabel(dumbellImageIcon);

        background.add(new JLabel("<html><h1> Workout Tracker </h1></html>"));

        background.add(pic);

        initializeButtons(background);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                WorkoutApp.printLog(EventLog.getInstance());
            }
        });
        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(false);
        add(background, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: adds the buttons to background panel
    public void initializeButtons(JPanel background) {
        addWorkout = new Button("Add Workout");
        addWorkout.setPreferredSize(new Dimension(width, 100));
        viewWorkouts = new Button("View Workouts");
        viewWorkouts.setPreferredSize(new Dimension(width, 100));
        totalSets = new Button("Find Total Sets");
        totalSets.setPreferredSize(new Dimension(width, 100));
        save = new Button("Save Workouts to File");
        save.setPreferredSize(new Dimension(width, 100));
        load = new Button("Load Workouts from File");
        load.setPreferredSize(new Dimension(width, 100));

        background.add(addWorkout, BorderLayout.SOUTH);
        background.add(viewWorkouts, BorderLayout.SOUTH);
        background.add(totalSets, BorderLayout.SOUTH);
        background.add(save, BorderLayout.SOUTH);
        background.add(load, BorderLayout.SOUTH);

        addWorkout.addActionListener(this);
        viewWorkouts.addActionListener(this);
        totalSets.addActionListener(this);
        save.addActionListener(this);
        load.addActionListener(this);
    }

    // EFFECTS: action listener for the implemented buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == addWorkout) {
                workout = new Workout();
                workoutWindow();
            } else if (e.getSource() == viewWorkouts) {
                minweight = 0;
                minreps = 0;
                viewWorkoutsByWeight(minweight);
            } else if (e.getSource() == weightMin) {
                minweight = Integer.parseInt(minWeightInput.getText());
                summaryFrame.dispose();
                summaryPanel.removeAll();
                viewWorkoutsByWeight(minweight);
            } else {
                initializeRestButtons(e);
            }
        } catch (NumberFormatException e3) {
            JOptionPane.showMessageDialog(null, "Invalid: Cannot leave fields blank", "Alert!",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates the action listener for the rest of the buttons
    public void initializeRestButtons(ActionEvent e) {
        if (e.getSource() == repsMin) {
            minreps = Integer.parseInt(minRepsInput.getText());
            summaryFrame.dispose();
            summaryPanel.removeAll();
            viewWorkoutsByReps(minreps);
        } else if (e.getSource() == totalSets) {
            calcTotal();
        } else if (e.getSource() == save) {
            saveWorkoutList();
        } else if (e.getSource() == load) {
            loadWorkoutList();
        } else if (e.getSource() == add) {
            processWorkoutInput();
        } else if (e.getSource() == close) {
            if (!workout.getExercisesList().isEmpty()) {
                workoutList.addWorkout(workout);
            }
            workoutFrame.dispose();
        }
    }

    // MODIFIES: this
    // EFFECTS: processes the inputs for the text boxes to create exercise and adds
    // it to a workout
    public void processWorkoutInput() {
        try {
            String name = exerciseName.getText();
            int sets = Integer.parseInt(setsInput.getText());
            int reps = Integer.parseInt(repsInput.getText());
            int weight = Integer.parseInt(weightInput.getText());

            exercise = new Exercise(sets, reps, name, weight);
            workout.addExercise(exercise);

            JLabel exerciseLabel = new JLabel(
                    "Exercise: " + name + "  Sets: " + sets + "  Reps: " + reps + "  Weight: " + weight);
            exerciseLabel.setPreferredSize(new Dimension(800, 30));
            exerciseLabel.setHorizontalAlignment(JLabel.CENTER);
            workoutPanel.add(exerciseLabel);
            workoutFrame.repaint();
            workoutFrame.revalidate();
            exerciseName.setText("");
            setsInput.setText("");
            repsInput.setText("");
            weightInput.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid: Cannot leave fields blank" + JSON_STORE, "Alert!",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates the workout frame and panel and adds elements to it
    public void workoutWindow() {
        add = new Button("Add Exercise");
        add.addActionListener(this);
        close = new Button("Save Workout");
        close.addActionListener(this);

        workoutFrame = new JFrame("Workout Window");
        workoutPanel = new JPanel();

        exerciseName = new JTextField();
        exerciseName.setPreferredSize(new Dimension(100, 30));
        setsInput = new JTextField();
        setsInput.setPreferredSize(new Dimension(50, 30));
        repsInput = new JTextField();
        repsInput.setPreferredSize(new Dimension(50, 30));
        weightInput = new JTextField();
        weightInput.setPreferredSize(new Dimension(50, 30));

        updateWorkoutFramePanel();

        workoutFrame.add(workoutPanel, BorderLayout.CENTER);

    }

    // MODIFIES: this
    // EFFECTS: updates the workout panel and frame with the updated workout with
    // the new inputted exercises
    public void updateWorkoutFramePanel() {
        workoutPanel.add(new JLabel("Exercise Name: "));
        workoutPanel.add(exerciseName);
        workoutPanel.add(new JLabel("Sets: "));
        workoutPanel.add(setsInput);
        workoutPanel.add(new JLabel("Reps: "));
        workoutPanel.add(repsInput);
        workoutPanel.add(new JLabel("Weight: "));
        workoutPanel.add(weightInput);
        workoutPanel.add(add);
        workoutPanel.add(close);

        workoutFrame.setVisible(true);
        workoutFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        workoutFrame.setSize(800, 600);
        workoutFrame.setLocationRelativeTo(null);
        workoutFrame.setResizable(false);
    }

    // EFFECTS: prints all the workouts
    private void viewWorkoutsByWeight(int minimum) {
        weightMin = new Button("Set Min Weight");
        summaryFrame = new JFrame();
        summaryPanel = new JPanel();
        weightMin.addActionListener(this);

        repsMin = new Button("Set Min Reps");
        repsMin.addActionListener(this);

        summaryPanel.add(new JLabel("Sort by Minimum Weight: "));
        summaryPanel.add(minWeightInput = new JTextField());
        minWeightInput.setPreferredSize(new Dimension(50, 30));
        summaryPanel.add(weightMin);
        summaryPanel.add(new JLabel("Sort by Minimum Reps: "));
        summaryPanel.add(minRepsInput = new JTextField());
        minRepsInput.setPreferredSize(new Dimension(50, 30));
        summaryPanel.add(repsMin);

        updateSummaryFrameByWeight(minimum);

        summaryFrame.add(summaryPanel);
        summaryFrame.setVisible(true);
        summaryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        summaryFrame.setSize(600, 600);
        summaryFrame.setLocationRelativeTo(null);
        summaryFrame.setResizable(false);
    }

    // MODIFIES: THIS
    // EFFECT: adds all the workouts and exercises into the list, also filters the
    // list with a minimum weight
    public void updateSummaryFrameByWeight(int minimum) {
        List<List<Exercise>> sortedList = workoutList.sortByWeight(minimum);

        if (!sortedList.isEmpty()) {
            for (List<Exercise> workout : sortedList) {
                JLabel workoutNum = new JLabel(
                        "<html><br>_______________________________________________</br></html>");
                workoutNum.setPreferredSize(new Dimension(400, 50));
                summaryPanel.add(workoutNum, BorderLayout.SOUTH);
                for (Exercise exercise : workout) {
                    JLabel exerciseSummary = new JLabel("<html>Name: " + exercise.getName() + "Sets: "
                            + exercise.getSets()
                            + " Reps: " + exercise.getReps() + " Weight: " + exercise.getWeight()
                            + "</html>");
                    exerciseSummary.setPreferredSize(new Dimension(400, 50));
                    summaryPanel.add(exerciseSummary, BorderLayout.WEST);
                }
            }
        } else {
            JLabel noWorkouts = new JLabel("No Workouts Recorded");
            summaryPanel.add(noWorkouts);
        }
    }

    // EFFECTS: prints all the workouts
    private void viewWorkoutsByReps(int minimum) {
        weightMin = new Button("Set Min Weight");
        summaryFrame = new JFrame();
        summaryPanel = new JPanel();
        weightMin.addActionListener(this);

        repsMin = new Button("Set Min Reps");
        repsMin.addActionListener(this);

        summaryPanel.add(new JLabel("Sort by Minimum Weight: "));
        summaryPanel.add(minWeightInput = new JTextField());
        minWeightInput.setPreferredSize(new Dimension(50, 30));
        summaryPanel.add(weightMin);
        summaryPanel.add(new JLabel("Sort by Minimum Reps: "));
        summaryPanel.add(minRepsInput = new JTextField());
        minRepsInput.setPreferredSize(new Dimension(50, 30));
        summaryPanel.add(repsMin);

        updateSummaryFrameByReps(minimum);

        summaryFrame.add(summaryPanel);
        summaryFrame.setVisible(true);
        summaryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        summaryFrame.setSize(600, 600);
        summaryFrame.setLocationRelativeTo(null);
        summaryFrame.setResizable(false);
    }

    // MODIFIES: THIS
    // EFFECT: adds all the workouts and exercises into the list, also filters the
    // list with a minimum reps
    public void updateSummaryFrameByReps(int minimum) {
        List<List<Exercise>> sortedList = workoutList.sortByReps(minimum);

        if (!sortedList.isEmpty()) {
            for (List<Exercise> workout : sortedList) {
                JLabel workoutNum = new JLabel("<html><br>_______________________________________________</br></html>");
                workoutNum.setPreferredSize(new Dimension(400, 50));
                summaryPanel.add(workoutNum, BorderLayout.SOUTH);
                for (Exercise exercise : workout) {
                    JLabel exerciseSummary = new JLabel("<html>Name: " + exercise.getName() + "Sets: "
                            + exercise.getSets()
                            + " Reps: " + exercise.getReps() + " Weight: " + exercise.getWeight()
                            + "</html>");
                    exerciseSummary.setPreferredSize(new Dimension(400, 50));
                    summaryPanel.add(exerciseSummary, BorderLayout.WEST);
                }
            }
        } else {
            JLabel noWorkouts = new JLabel("No Workouts Recorded");
            summaryPanel.add(noWorkouts);
        }
    }

    // EFFECTS: returns true if there is an exercise with the minimum reps
    public boolean containsMinReps(Workout workout, int minreps) {
        for (Exercise e : workout.getExercisesList()) {
            int curreps = e.getReps();
            if (curreps < minreps) {
                return false;
            }
        }

        return true;
    }

    // EFFECTS: returns true if there is an exercise with the inputted minimum
    // weight
    public boolean containsMinWeight(Workout workout, int minweight) {
        for (Exercise e : workout.getExercisesList()) {
            int currweight = e.getWeight();
            return currweight >= minweight;
        }

        return false;
    }

    // EFFECTS: prints out the volume/sets of all of the workouts done in each week.
    private void calcTotal() {
        int total = 0;
        int sets;
        if (!(workoutList.getWorkoutList().isEmpty())) {
            for (Workout workout : workoutList.getWorkoutList()) {
                sets = workout.totalSets();
                total = total + sets;
            }
        }

        JOptionPane.showMessageDialog(null, "Total Number of Sets: " + total);
    }

    // MODIFIES: this
    // EFFECTS: saves the workouts to file
    private void saveWorkoutList() {
        Image thumbsupImage2 = thumbsupimage.getImage().getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING);
        ImageIcon thumbsupImageIcon = new ImageIcon(thumbsupImage2);

        try {
            jsonWriter.open();
            jsonWriter.write(workoutList);
            jsonWriter.close();
            JOptionPane.showMessageDialog(null, "Saved Workouts from " + JSON_STORE, "Message",
                    JOptionPane.INFORMATION_MESSAGE, thumbsupImageIcon);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Unable to write file to " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workouts from file
    private void loadWorkoutList() {
        Image thumbsupImage2 = thumbsupimage.getImage().getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING);
        ImageIcon thumbsupImageIcon = new ImageIcon(thumbsupImage2);

        try {
            workoutList = jsonReader.read();
            JOptionPane.showMessageDialog(null, "Loaded Workouts from " + JSON_STORE, "Message",
                    JOptionPane.INFORMATION_MESSAGE, thumbsupImageIcon);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to read file from " + JSON_STORE, "Message",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

}
