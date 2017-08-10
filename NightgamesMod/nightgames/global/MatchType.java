package nightgames.global;

import nightgames.areas.Area;
import nightgames.characters.Character;
import nightgames.combat.Encounter;
import nightgames.combat.IEncounter;
import nightgames.ftc.FTCEncounter;
import nightgames.ftc.PrematchFTC;

public enum MatchType {
    NORMAL,
    FTC;

    public IEncounter buildEncounter(Character first, Character second, Area location) {
        switch (this) {
            case FTC:
                return new FTCEncounter(first, second, location);
            case NORMAL:
                return new Encounter(first, second, location);
            default:
                throw new Error();
        }
    }
}
