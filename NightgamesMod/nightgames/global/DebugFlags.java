package nightgames.global;

public enum DebugFlags {
    DEBUG_SCENE,
    DEBUG_SKILLS,
    DEBUG_STRATEGIES,
    DEBUG_PET,
    DEBUG_SKILLS_RATING,
    DEBUG_MOOD,
    DEBUG_IMAGES,
    DEBUG_DAMAGE,
    DEBUG_PLANNING,
    DEBUG_SKILL_CHOICES,
    DEBUG_LOADING,
    DEBUG_CLOTHING,
    DEBUG_FTC,
    DEBUG_GUI,
    DEBUG_ADDICTION,
    DEBUG_SPECTATE,
    NO_GUI;
    public static boolean[] debug = new boolean[values().length];
    public static int debugSimulation = 0;

    public static boolean isDebugOn(DebugFlags flag) {
        return debug[flag.ordinal()] && debugSimulation == 0;
    }
}
