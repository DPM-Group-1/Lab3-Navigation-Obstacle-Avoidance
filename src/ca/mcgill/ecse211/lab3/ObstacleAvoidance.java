package ca.mcgill.ecse211.lab3;

//static imports to avoid duplicating variables and make the code easier to read
import static ca.mcgill.ecse211.lab3.Resources.*;
import static ca.mcgill.ecse211.lab3.ObstacleAvoidance.State.*;

/**
 * Avoids obstacles. Implemented using a state machine.
 */
public class ObstacleAvoidance implements Runnable {

  /**
   * The possible states that the robot could be in.
   */
  enum State {
    /** The initial state. */ INIT,
    /** The turning state. */ TURNING, 
    /** The traveling state. */ TRAVELING,
    /** The emergency state. */ EMERGENCY
  };

  /**
   * The current state of the robot.
   */
  static State state;

  /**
   * {@code true} when robot is traveling.
   */
  public static boolean traveling; // booleans are false by default
  
  /**
   * {@code true} when obstacle is avoided.
   */
  public static boolean safe;

  /**
   * The destination x.
   */
  public static double destx;
  
  /**
   * The destination y.
   */
  public static double desty;

  /**
   * The sleep time.
   */
  public static final int SLEEP_TIME = 50;
  
  
  /**
   * Updates the heading.
   */
  public static void updateTravel() {
    Navigation.turnTo(Navigation.getDestAngle(destx, desty), false);
    Navigation.setSpeeds(FAST, FAST);
  }

  public void run() {
    state = INIT;
    while (true) {
      if (state == INIT) {
        if (traveling) {
          state = TURNING;
        }
      } else if (state == TURNING) {
        double destAngle = Navigation.getDestAngle(destx, desty);
        Navigation.turnTo(destAngle, true);
        if (Navigation.facingDest(destAngle)) {
          Navigation.setSpeeds(0, 0);
          state = TRAVELING;
        }
      } else if (state == TRAVELING) {
        checkEmergency();
        if (state == EMERGENCY) { // order matters!
            Log.log(Log.Sender.avoidance,"avoiding obstacle!");
            double theta = odometer.getTheta();
            Navigation.setSpeeds(0, 0);
            Navigation.turnTo(Math.abs(theta-90) % 360,true); // Turn right.
            Navigation.goForward(25, false);
            Navigation.turnTo(theta,true); // Turn left.
            Navigation.goForward(40, false);
            Log.log(Log.Sender.avoidance,"obstacle avoided!");
            safe = true;
        } else if (!Navigation.isDone(destx, desty)) {
          updateTravel();
        } else { // Arrived!
          Navigation.setSpeeds(0, 0);
          traveling = false;
          state = INIT;
        }
      } else if (state == EMERGENCY) {
        if (safe) {
          state = TURNING;
        }
      }
      Log.log(Log.Sender.Navigator, "state: " + state);
      Main.sleepFor(SLEEP_TIME);
    }
  }

  /**
   * Sets emergency state when robot is too close to a wall.
   */
  public static void checkEmergency() {
    if (usPoller.getDistance() < 10) {
      state = EMERGENCY;
    }
  }
  
  
  /**
   * Get the current state of the state machine and returns it as a string.
   * @author Mathieu Bissonnette
   * @return String The current state of the ObstacleAvoidance state machine.
   */
  public static String getState() {
    return new String() + state;
  }

}
