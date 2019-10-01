package ca.mcgill.ecse211.lab3;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;

/**
 * This class is used to define static resources in one place for easy access and to avoid 
 * cluttering the rest of the codebase. All resources can be imported at once like this:
 * 
 * <p>{@code import static ca.mcgill.ecse211.lab3.Resources.*;}
 */
public class Resources {
  
  /**
   * NUMERIC CONSTANTS
   */

  /**
   * The wheel radius in centimeters.
   */
  public static final double WHEEL_RADIUS = 2.130;
  
  /**
   * The robot width in centimeters.
   */
  public static final double TRACK = 15.6;
  
  /**
   * The left wheel radius in centimeters.
   */
  public static final double LEFT_RADIUS = WHEEL_RADIUS;
  
  /**
   * The right wheel radius in centimeters.
   */
  public static final double RIGHT_RADIUS = WHEEL_RADIUS;
  
  /**
   * The odometer timeout period in milliseconds.
   */
  public static final int TIMEOUT_PERIOD = 30;
  
  /**
   * The fast speed in degrees/seconds.
   */
  public static final int FAST = 100;
  
  /**
   * The slow speed in degrees/seconds.
   */
  public static final int SLOW = 100;
  
  /**
   * The acceleration in degrees/seconds.
   */
  public static final int ACCELERATION = 1500;
  
  /**
   * The degree error.
   */
  public static final double DEG_ERR = 2.0;
  
  /**
   * The cm error.
   */
  public static final double CM_ERR = 1.0;
  
  /**
   * The tile size in centimeters.
   */
  public static final double TILE_SIZE = 30.48;
  
  /**
   * Starting position in centimeters.
   */
  public static final double[] STARTING_POSITION = {TILE_SIZE, TILE_SIZE, 90};
  
  /**
   * Minimum intensity differential between dark lines and light floor.
   */
  public static final double MIN_INTENSITY_DIFF = -0.020;
  
  /**
   * Color sensor offset with wheel base in centimeters.
   */
  public static final double COLOR_SENSOR_OFFSET = 1.5;
  
  /**
   * Waypoints map arrays (arrays containing the waypoints in coordinates X,Y in square unit).
   */
  public static final double[][][] WAYPOINTS_MAPS = {{{1,3},{2,2},{3,3},{3,2},{2,1}}, {{2,2},{1,3},{3,3},{3,2},{2,1}}, {{2,1},{3,2},{3,3},{1,3},{2,2}}, {{1,2},{2,3},{2,1},{3,2},{3,3}}};
  
  /**
   * Selected map index.
   */
  public static final int MAP_INDEX = 3;
  
  /**
   * Selected waypoints (X,Y square units)
   */
  public static final double[][] WAYPOINTS = WAYPOINTS_MAPS[MAP_INDEX];
  
  /**
   * Odometry correction angle in degree (Maximum of 22).
   */
  public static final int ODOMETRY_CORRECTION_ANGLE = 15;
  
  /**
   * HARDWARE CONSTANTS
   */
  
  /**
   * The left motor.
   */
  public static final EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
  
  /**
   * The right motor.
   */
  public static final EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.D);
  
  /**
   * The ultrasonic sensor.
   */
  public static final EV3UltrasonicSensor usSensor = new EV3UltrasonicSensor(SensorPort.S1);
  
  /**
   * The color sensor.
   */
  public static final EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S4);
  
  /**
   * The LCD.
   */
  public static final TextLCD LCD = LocalEV3.get().getTextLCD();
  
  /**
   * THREAD CONSTANTS
   */
  
  /**
   * The LCD printer thread.
   */
  public static final LCDInfo lcdInfo = new LCDInfo();
  
  /**
   * The ultrasonic poller.
   */
  public static UltrasonicPoller usPoller = new UltrasonicPoller();
  
  /**
   * The odometer.
   */
  public static Odometer odometer = new Odometer();
  
  /**
   * The odometry correction.
   */
  public static OdometryCorrection odometryCorrection = new OdometryCorrection();
  
  /**
   * The obstacle avoidance.
   */
  public static ObstacleAvoidance obstacleAvoidance = new ObstacleAvoidance();

}
