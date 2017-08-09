package nightgames.global;

import nightgames.characters.*;
import nightgames.characters.Character;
import nightgames.daytime.Daytime;
import nightgames.ftc.FTCMatch;
import nightgames.items.Item;
import nightgames.modifier.Modifier;
import nightgames.modifier.standard.FTCModifier;
import nightgames.modifier.standard.NoModifier;

import java.util.*;

/**
 * Base class for match setup.
 */
public abstract class Prematch implements Scene {
    protected Prematch() {
        GameState.current = this;
        Flag.unflag(Flag.victory);
    }

    /**
     * Chooses and creates an instance of the Prematch of one of the available Match types.
     * @return A new Prematch of the chosen Match type.
     */
    public static Prematch decideMatchType() {
        return new PreMatchSchool();
        /*
         * TODO Lots of FTC bugs right now, will disable it for the time being.
         * Enable again once some of the bugs are sorted out.
        MatchType type;
        if (Flag.checkFlag(Flag.NoFTC) || CharacterPool.human.getLevel() < 15) {
            type = MatchType.NORMAL;
        } else if (!Flag.checkFlag(Flag.didFTC) || DebugFlags.isDebugOn(DebugFlags.DEBUG_FTC)) {
            type = MatchType.FTC;
        } else {
            type = DebugFlags.isDebugOn(DebugFlags.DEBUG_FTC) || Random.random(10) == 0 ?
                            MatchType.FTC :
                            MatchType.NORMAL;
        }
        return type.buildPrematch();
        */
    }

    private Match buildMatch(Collection<Character> combatants, Modifier mod) {
        if (mod.name().equals("ftc")) {
            if (combatants.size() < 5) {
                return new Match(combatants, new NoModifier());
            }
            Flag.flag(Flag.FTC);
            return new FTCMatch(combatants, ((FTCModifier) mod).getPrey());
        } else {
            return new Match(combatants, mod);
        }
    }

    public void setUpMatch(Modifier matchmod) {
        assert Daytime.day == null;
        Set<Character> lineup = new HashSet<>(CharacterPool.debugChars);
        Character lover = null;
        int maxaffection = 0;
        Flag.unflag(Flag.FTC);
        for (Character player : CharacterPool.players) {
            player.getStamina().fill();
            player.getArousal().empty();
            player.getMojo().empty();
            player.getWillpower().fill();
            if (player.getPure(Attribute.Science) > 0) {
                player.chargeBattery();
            }
            if (CharacterPool.human.getAffection(player) > maxaffection && !player.has(Trait.event) && !Flag
                            .checkCharacterDisabledFlag(player)) {
                maxaffection = CharacterPool.human.getAffection(player);
                lover = player;
            }
        }
        List<Character> participants = new ArrayList<>();
        // Disable characters flagged as disabled
        for (Character c : CharacterPool.players) {
            // Disabling the player wouldn't make much sense, and there's no PlayerDisabled flag.
            if (c.getType().equals("Player") || !Flag.checkCharacterDisabledFlag(c)) {
                participants.add(c);
            }
        }
        if (lover != null) {
            lineup.add(lover);
        }
        lineup.add(CharacterPool.human);
        if (matchmod.name().equals("maya")) {
            if (!Flag.checkFlag(Flag.Maya)) {
                CharacterPool.newChallenger(new Maya(CharacterPool.human.getLevel()));
                Flag.flag(Flag.Maya);
            }
            NPC maya = Optional.ofNullable(CharacterPool.getNPC("Maya")).orElseThrow(() -> new IllegalStateException(
                            "Maya data unavailable when attempting to add her to lineup."));
            lineup.add(maya);
            lineup = CharacterPool.pickCharacters(participants, lineup, 5);
            Match.resting = new HashSet<>(CharacterPool.players);
            Match.resting.removeAll(lineup);
            maya.gain(Item.Aphrodisiac, 10);
            maya.gain(Item.DisSol, 10);
            maya.gain(Item.Sedative, 10);
            maya.gain(Item.Lubricant, 10);
            maya.gain(Item.BewitchingDraught, 5);
            maya.gain(Item.FeralMusk, 10);
            maya.gain(Item.ExtremeAphrodisiac, 5);
            maya.gain(Item.ZipTie, 10);
            maya.gain(Item.SuccubusDraft, 10);
            maya.gain(Item.Lactaid, 5);
            maya.gain(Item.Handcuffs, 5);
            maya.gain(Item.Onahole2);
            maya.gain(Item.Dildo2);
            maya.gain(Item.Strapon2);
            Match.match = new Match(lineup, matchmod);
        } else if (matchmod.name().equals("ftc")) {
            Character prey = ((FTCModifier) matchmod).getPrey();
            if (!prey.human()) {
                lineup.add(prey);
            }
            lineup = CharacterPool.pickCharacters(participants, lineup, 5);
            Match.resting = new HashSet<>(CharacterPool.players);
            Match.resting.removeAll(lineup);
            Match.match = buildMatch(lineup, matchmod);
        } else if (participants.size() > 5) {
            lineup = CharacterPool.pickCharacters(participants, lineup, 5);
            Match.resting = new HashSet<>(CharacterPool.players);
            Match.resting.removeAll(lineup);
            Match.match = buildMatch(lineup, matchmod);
        } else {
            Match.match = buildMatch(participants, matchmod);
        }
        Match.startMatch();
    }

    public abstract void prompt(Player player);

    @Override public abstract void respond(String response);
}
