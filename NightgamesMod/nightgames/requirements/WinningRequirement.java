package nightgames.requirements;

import nightgames.characters.Character;
import nightgames.combat.Combat;

/**
 * Returns true based on an estimation of whether character self is winning.
 */
public class WinningRequirement extends BaseRequirement {

    @Override
    public boolean meets(Combat c, Character self, Character other) {
        if (c == null)
            return false;
        int self_fitness = estimatedFitness(c, self);
        int other_fitness = estimatedFitness(c, other);
        int score = self_fitness - other_fitness;
        //int score = (estimatedFitness(c, self) - estimatedFitness(c, other));
        if (c.getStance().dom(self))
            score += 10;
        if (c.getStance().mobile(self))
            score += 5;
        if (c.getStance().mobile(other))
            score -= 5;
        // > instead of >= because if fitnesses are equal we aren't winning or losing
        return score > 0;
    }

    /**
     * Estimate of how much fight character self has left in them.
     * @param self Character to estimate fitness of.
     * @return An integer between 0 and 100, for comparison with another character's fitness.
     */
    private int estimatedFitness(Combat c, Character self) {
        // Willpower good; arousal bad.
        // Willpower percentage counts for twice as much as arousal percentage.
        return (self.getWillpower().percent() * 2 - self.getArousal().percent()) / 2;
    }
}
