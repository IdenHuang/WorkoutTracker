# Workout Manager

The application that I am planning to code is a **tracker** in which a user will input their gym workouts into. For example, if I did three exercises today which focused on my leg muscles. An example of a workout is included below.

- *Leg Extensions, 3 sets of 12 reps*
- *Leg Squats, 3 sets of 20 reps*
- *Calf Raises, 3 sets of 20 reps*

The focus on this program would to have these inputted into the program such that I could title this workout *leg workout* and set a date. Next I am able to input which exercises and for how many sets and repititions I did those exercises for. Mainly, people who prodominately weight lift will use this program, however, there could be complexity added to it so that it can expand to other athletes apart from those who weight lift. This project is interesting to me because I have gotten into weight lifting in the past year, and being able to track their strength progress by logging it would be very convenient. Additionally, I believe that more complexity could be added to this program later on as well. 


## User Stories
- As a user, I want to be able to add a workout to my record
- As a user, I want to add what exercises I did to todays workout
- As a user, I want to view workouts in a list form
- As a user, I want to be able to calculate my total number of sets done in a workout
- As a user, I want to be able to save my workout history into a file If I chose to
- As a user, I want to be able to load my workout history from a file if I chose to 


# Instructions for End User

- You can generate the first required action related to the user story by inputting workouts and setting a minimum weight to filter the workouts by
- You can generate the second required action related to the user story by inputting workouts and setting a minimum rep count to filter the workouts by
- You can locate my visual component by saving or loading the file in.
- You can save the state of my application by clicking the 'Save Workouts to File' button
- You can reload the state of my application by 'Load Workouts from File' button


# Phase 4: Task 2
Wed Nov 27 21:17:58 PST 2024
Exercise Added to Workout.

Wed Nov 27 21:18:06 PST 2024
Exercise Added to Workout.

Wed Nov 27 21:18:08 PST 2024
Workout added to a list of Workouts.

Wed Nov 27 21:18:09 PST 2024
Viewed List sorted by Weight

Wed Nov 27 21:18:16 PST 2024
Viewed List sorted by Reps

Wed Nov 27 21:18:19 PST 2024
Viewed List sorted by Weight

Wed Nov 27 21:18:24 PST 2024
Exercise Added to Workout.

Wed Nov 27 21:18:24 PST 2024
Exercise Added to Workout.

Wed Nov 27 21:18:24 PST 2024
Workout added to a list of Workouts.

Wed Nov 27 21:18:24 PST 2024
Exercise Added to Workout.

Wed Nov 27 21:18:24 PST 2024
Exercise Added to Workout.

Wed Nov 27 21:18:24 PST 2024
Workout added to a list of Workouts.

Wed Nov 27 21:18:24 PST 2024
Exercise Added to Workout.

Wed Nov 27 21:18:24 PST 2024
Workout added to a list of Workouts.

Wed Nov 27 21:18:26 PST 2024
Viewed List sorted by Weight


# Phase 4: Task 3
In my UML diagram, there is a lot of coupling involved between the classes that are included within the program. One way I could improve this is to maybe abolish the exercise class to simply have a workout class that stores strings as exercises or even abolish the WorkoutList class such that there are only Workout and Exercises. The WorkoutList class is not needed and there could have simply have been a list of workouts elsewhere in the code which could have stored the workouts done. Of course, this would have to be refactored in a way where persistence would also have to be presevered. The WorkoutList class only consisted of an arraylist with the workouts and filtering which could have been done externally from the class anyway.  
