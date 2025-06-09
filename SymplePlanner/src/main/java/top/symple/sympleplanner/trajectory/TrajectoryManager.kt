package top.symple.sympleplanner.trajectory

abstract class TrajectoryManager(val name: String) {
    var trajectory: List<TrajectoryState> = emptyList()
        private set;

    init {
        initialize();
    }

    /**
     * This is the initialize method for this class.
     * Call this to initialize the trajectory
     */
    fun initialize() {
        this.trajectory = setTrajectory();
    }

    /**
     * Returns the trajectory states defined by the user.
     */
    protected abstract fun setTrajectory(): List<TrajectoryState>;
}