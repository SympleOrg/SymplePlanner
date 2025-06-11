package top.symple.sympleplanner.interfaces

interface IDriveTrain {
    /**
     * Applies power to the drive motors.
     *
     * The [power] array must be in this order:
     * 0 = Front Left, 1 = Front Right, 2 = Back Left, 3 = Back Right
     *
     * @param power Array of motor powers.
     */
    fun setPower(power: DoubleArray);
}