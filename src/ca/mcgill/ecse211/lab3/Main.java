package ca.mcgill.ecse211.lab3;

import static ca.mcgill.ecse211.lab3.Resources.*;
import java.io.FileNotFoundException;
import lejos.hardware.Button;

/**
 * The main class.
 */
public class Main {
  
  /**
   * Set this to true to print to a file.
   */
  public static final boolean WRITE_TO_FILE = true;

  /**
   * Main entry point.
   * 
   * @param args
   */
  public static void main(String[] args) {

    Log.setLogging(true, true, false, false);

    if (WRITE_TO_FILE) {
      setupLogWriter();
    }
    
    // Wait for a button press to start the navigation.
    LCD.drawString("NaviBot MK-I", 0, 0);
    LCD.drawString("Press to start.", 0, 1);
    Button.waitForAnyPress();
    
    new Thread(usPoller).start();
    new Thread(odometer).start();
    new Thread(obstacleAvoidance).start();
    new Thread(lcdInfo).start();
    
    // Initialize starting position of the odometer.
    boolean[] updateTruthTable = {true, true, true};
    odometer.setPosition(STARTING_POSITION, updateTruthTable);
    
    completeCourse();

    while (Button.waitForAnyPress() != Button.ID_ESCAPE)
      ; // do nothing
    
    System.exit(0);
  }

  public static void setupLogWriter() {
    try {
      Log.setLogWriter(System.currentTimeMillis() + ".log");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Completes a course.
   */
  
  private static void completeCourse() {
    // Convert from square coordinates to real coordinates.
    int[][] waypoints = new int[WAYPOINTS.length][WAYPOINTS[0].length];
    for (int i = 0; i < WAYPOINTS.length; i++) {
      for (int j = 0; j < WAYPOINTS[i].length; j++) {
        waypoints[i][j] = (int) (WAYPOINTS[i][j] * TILE_SIZE);
      }
    }

    // Travel to each waypoints.
    for (int[] point : waypoints) {
      Navigation.travelTo(point[0], point[1], true);
      while (ObstacleAvoidance.traveling) {
        Main.sleepFor(500);
      }
    }
  }
  
  /**
   * Sleeps current thread for the specified duration.
   * 
   * @param duration sleep duration in milliseconds
   */
  public static void sleepFor(long duration) {
    try {
      Thread.sleep(duration);
    } catch (InterruptedException e) {
      // There is nothing to be done here
    }
  }
  
}
