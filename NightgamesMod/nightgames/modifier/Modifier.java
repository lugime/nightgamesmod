package nightgames.modifier;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import nightgames.actions.Action;
import nightgames.characters.Character;
import nightgames.global.DebugFlags;
import nightgames.global.Global;
import nightgames.global.Match;
import nightgames.json.JsonUtils;
import nightgames.modifier.skill.SkillModifier;
import nightgames.modifier.standard.*;

@SuppressWarnings("unused")
public interface Modifier {

    Set<Modifier> modifierPool;

    static void buildModifierPool() {
        modifierPool = new HashSet<>();
        modifierPool.add(new NoModifier());
        modifierPool.add(new NoItemsModifier());
        modifierPool.add(new NoToysModifier());
        modifierPool.add(new NoRecoveryModifier());
        modifierPool.add(new NudistModifier());
        modifierPool.add(new PacifistModifier());
        modifierPool.add(new UnderwearOnlyModifier());
        modifierPool.add(new VibrationModifier());
        modifierPool.add(new VulnerableModifier());
        modifierPool.add(new LevelDrainModifier());

        File customModFile = new File("data/customModifiers.json");
        if (customModFile.canRead()) {
            try {
                JsonArray array = JsonUtils.rootJson(Files.newBufferedReader(customModFile.toPath())).getAsJsonArray();
                for (JsonElement element : array) {
                    JsonObject object;
                    try {
                        object = element.getAsJsonObject();
                    } catch (Exception e) {
                        System.out.println("Error loading custom modifiers: Non-object element in root array");
                        continue;
                    }
                    Modifier mod = CustomModifierLoader.readModifier(object);
                    if (!mod.name().equals("DEMO"))
                        modifierPool.add(mod);
                    if (Global.isDebugOn(DebugFlags.DEBUG_LOADING))
                        System.out.println("Loaded custom modifier: " + mod.name());
                }
            } catch (IOException e) {
                System.out.println("Error loading custom modifiers: " + e);
                e.printStackTrace();
            }
        }
        if (Global.isDebugOn(DebugFlags.DEBUG_LOADING))
            System.out.println("Done loading modifiers");
    }

    static Set<Modifier> getModifierPool() {
        return modifierPool;
    }

    /**
     * Ensure that the character has an appropriate outfit
     */
    void handleOutfit(Character c);

    /**
     * Ensure that the character has a legal inventory
     */
    void handleItems(Character c);

    /**
     * Apply any required statuses
     */
    void handleStatus(Character c);

    /**
     * Get a SkillModifier specific to the current Match
     */
    SkillModifier getSkillModifier();

    /**
     * Process non-combat turn
     */
    void handleTurn(Character c, Match m);

    /**
     * Undo all changes to the character's inventory made by handleItems
     */
    void undoItems(Character c);

    boolean allowAction(Action act, Character c, Match m);

    int bonus();

    boolean isApplicable();

    String name();

    String intro();

    String acceptance();
    
    default void extraWinnings(Character player, int score) {}
}
