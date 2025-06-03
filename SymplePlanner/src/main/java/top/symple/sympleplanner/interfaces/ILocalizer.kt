package top.symple.sympleplanner.interfaces

import top.symple.sympleplanner.geometry.Pose2d

interface ILocalizer {
    fun getPose(): Pose2d;
    fun update();
}