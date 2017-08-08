package nightgames.modifier;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import nightgames.global.DebugFlags;
import nightgames.json.JsonUtils;
import nightgames.modifier.standard.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

/**
 * Tracks available match modifiers.
 */
public class ModifierPool {
    public static Set<Modifier> modifierPool;
    static {
        buildModifierPool();
    }

    public static void buildModifierPool() {
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
                    if (DebugFlags.isDebugOn(DebugFlags.DEBUG_LOADING))
                        System.out.println("Loaded custom modifier: " + mod.name());
                }
            } catch (IOException e) {
                System.out.println("Error loading custom modifiers: " + e);
                e.printStackTrace();
            }
        }
        if (DebugFlags.isDebugOn(DebugFlags.DEBUG_LOADING))
            System.out.println("Done loading modifiers");
    }

    public static Set<Modifier> getModifierPool() {
        return modifierPool;
    }
}
