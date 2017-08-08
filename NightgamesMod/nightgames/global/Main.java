package nightgames.global;

import nightgames.Resources.ResourceLoader;
import nightgames.actions.Action;
import nightgames.characters.CharacterPool;
import nightgames.characters.NPC;
import nightgames.characters.Trait;
import nightgames.characters.TraitTree;
import nightgames.gui.GUI;
import nightgames.items.clothing.Clothing;
import nightgames.modifier.ModifierPool;
import nightgames.requirements.TraitRequirement;
import nightgames.skills.Skill;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Program entry point
 */
public class Main {
    public static void main(String[] args) {
        new Logwriter();
        for (String arg : args) {
            try {
                DebugFlags flag = DebugFlags.valueOf(arg);
                DebugFlags.debug[flag.ordinal()] = true;
            } catch (IllegalArgumentException e) {
                // pass
            }
        }
        initialize();
        new GUI();
    }

    public static void initialize() {
        Random.rng = new java.util.Random();
        Flag.flags = new HashSet<>();
        CharacterPool.players = new HashSet<>();
        CharacterPool.debugChars = new HashSet<>();
        Match.resting = new HashSet<>();
        Flag.counters = new HashMap<>();
        Flag.counters.put(Flag.malePref.name(), 0.f);
        Clothing.buildClothingTable();
        Logwriter.makeLogger(new Date());
        TraitRequirement.setTraitRequirements(new TraitTree(ResourceLoader.getFileResourceAsStream("data/TraitRequirements.xml")));
        Formatter.buildParser();
        Action.buildActionPool();
        Trait.buildFeatPool();
        Skill.buildSkillPool(NPC.noneCharacter);
        ModifierPool.buildModifierPool();
    }
}

