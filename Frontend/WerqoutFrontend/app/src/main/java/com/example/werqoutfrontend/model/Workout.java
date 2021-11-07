package com.example.werqoutfrontend.model;

/**
 * Model for the Workout object. Stores data for the workout name, date, and time.
 * Also includes methods to create a new workout and set the name, date, and time.
 * @author Angelo Bellizzi
 */
public class Workout {
    /**
     * The name of the workout.
     */
    private String name;

    /**
     * The date of the workout.
     */
    private String date;

    /**
     * The time of the workout.
     */
    private String time;

    /**
     * Constructor for a new workout. Workout name, date, and time needed.
     * @param name
     * the name of the workout (example: Chest/Triceps lift)
     * @param date
     * the date of the workout (in the format mm/dd/yy)
     * @param time
     * the time of the workout (in the format 00:00AM)
     */
    public Workout(String name, String date, String time)
    {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    /**
     * Gets the name of the workout.
     * @return
     * returns the name of the workout.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the workout name to the given parameter.
     * @param name
     * the new workout name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the date of the workout.
     * @return
     * returns the date of the workout.
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the workout date to the given parameter
     * @param date
     * the new workout date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gets the time of the workout.
     * @return
     * returns the time of the workout
     */
    public String getTime() {
        return time;
    }

    /**
     * Sets the workout time to the given parameter
     * @param time
     * the new workout time
     */
    public void setTime(String time) {
        this.time = time;
    }
}
