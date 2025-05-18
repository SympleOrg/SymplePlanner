package top.symple.sympleplanner.interfaces

import top.symple.sympleplanner.util.MotorPowerSet

interface Chassis {
    /**
     * @param power Contains the power values for all four motors.
     */
    fun setPower(power: MotorPowerSet);
}