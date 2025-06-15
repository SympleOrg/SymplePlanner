package top.symple.sympleplanner.controller

import kotlin.math.abs

/**
 * A PID (Proportional–Integral–Derivative) controller implementation.
 * This controller calculates an output based on the error between a set point and a measured value.
 *
 * @property kP Proportional gain
 * @property kI Integral gain
 * @property kD Derivative gain
 * @property setPoint Desired target value the controller will try to achieve
 * @property tolerance Acceptable error range for determining if at set point
 */
class PIDController(var kP: Double, var kI: Double, var kD: Double, setPoint: Double, tolerance: Double = 1e-3) {

    /**
     * The target value for the controller.
     */
    var setPoint: Double = setPoint
        set(value) {
            lastError = value - lastMeasure;
            field = value;
        };

    var tolerance: Double = tolerance
        set(value) {
            if (value < 0) {
                throw RuntimeException("[PIDController] Tolerance must be equal to or greater than zero.")
            }
            field = value;
        };

    init {
        if (tolerance < 0) {
            throw RuntimeException("[PIDController] Tolerance must be equal to or greater than zero.")
        }
    }

    private var lastCalcTime: Long = 0;
    private var lastError: Double = 0.0;
    private var lastMeasure: Double = 0.0;
    private var integral: Double = 0.0;

    /** Max and min values the integral term is allowed to reach, to prevent integral windup. */
    var maxIntegral: Double = 1.0;
    var minIntegral: Double = -1.0;

    constructor(kP: Double, setPoint: Double, tolerance: Double = 1e-3) : this(kP, 0.0, 0.0, setPoint, tolerance)
    constructor(kP: Double, kD: Double, setPoint: Double, tolerance: Double = 1e-3) : this(kP, 0.0, kD, setPoint, tolerance)

    /**
     * Calculates the control output based on the current measured value.
     *
     * @param measured The current process variable (measurement)
     * @return The computed PID output
     */
    fun calculate(measured: Double): Double {
        var currentTime = System.nanoTime();
        if(lastCalcTime == 0L) {
            lastCalcTime = currentTime;
        }
        var deltaTime = (currentTime - lastCalcTime) / 1E9;
        lastCalcTime = currentTime;


        var error = setPoint - measured;
        integral += error * deltaTime;
        integral = integral.coerceIn(minIntegral, maxIntegral)

        var derivative = 0.0;
        if(deltaTime > 1E-9) {
            derivative = (error - lastError) / deltaTime;
        }

        lastError = error;
        lastMeasure = measured;

        return kP * error + kI * integral + kD * derivative;
    }

    /**
     * Calculates the PID output using the provided set point and measured value.
     *
     * @param measured The current measured value.
     * @param setPoint The new desired set point.
     * @return The calculated PID output.
     */
    fun calculate(measured: Double, setPoint: Double): Double {
        this.setPoint = setPoint;
        return calculate(measured);
    }

    /**
     * Resets the PID controller's internal state (integral, last error, timing).
     * Useful when the system being controlled has changed or restarted.
     */
    fun reset() {
        lastCalcTime = 0;
        lastError = 0.0;
        lastMeasure = 0.0;
        integral = 0.0;
    }

    /**
     * Checks whether the current error is within the specified tolerance.
     *
     * @return True if the absolute error is less than tolerance, false otherwise
     */
    fun atSetPoint(): Boolean = abs(lastError) < tolerance;

    /**
     * Returns the most recent error value (set point - measured value).
     *
     * @return The last computed error.
     */
    fun getError() = lastError;

    /**
     * Sets the allowable range for the integral term to avoid windup.
     *
     * @param minValue Minimum value for the integral term
     * @param maxValue Maximum value for the integral term
     */
    fun setIntegralRange(minValue: Double, maxValue: Double) {
        maxIntegral = maxValue;
        minIntegral = minValue;
    }
}