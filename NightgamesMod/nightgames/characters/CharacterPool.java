package nightgames.characters;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import nightgames.Resources.ResourceLoader;
import nightgames.characters.custom.CustomNPC;
import nightgames.characters.custom.JsonSourceNPCDataLoader;
import nightgames.characters.custom.NPCData;
import nightgames.json.JsonUtils;
import nightgames.start.NpcConfiguration;
import nightgames.start.StartConfiguration;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Tracks Characters in current game.
 */
public class CharacterPool {
    public static Map<String, NPC> characterPool;   // All starting and unlockable characters
    public static Set<Character> players = new HashSet<>();           // All currently unlocked characters
    public static Set<Character> debugChars = new HashSet<>();
    public static Player human;

    /**
     * WARNING DO NOT USE THIS IN ANY COMBAT RELATED CODE.
     * IT DOES NOT TAKE INTO ACCOUNT THAT THE PLAYER GETS CLONED. WARNING. WARNING.
     * @return
     */
    public static Player getPlayer() {
        return human;
    }

    public static List<Character> getInAffectionOrder(List<Character> viableList) {
        List<Character> results = new ArrayList<>(viableList);
        results.sort((a, b) -> a.getAffection(getPlayer()) - b.getAffection(getPlayer()));
        return results;
    }

    public static NPC getNPCByType(String type) {
        NPC results = characterPool.get(type);
        if (results == null) {
            System.err.println("failed to find NPC for type " + type);
        }
        return results;
    }

    public static Character getCharacterByType(String type) {
        if (type.equals(human.getType())) {
            return human;
        }
        return getNPCByType(type);
    }

    private static Optional<NpcConfiguration> findNpcConfig(String type, Optional<StartConfiguration> startConfig) {
        return startConfig.isPresent() ? startConfig.get().findNpcConfig(type) : Optional.empty();
    }

    public static void rebuildCharacterPool(Optional<StartConfiguration> startConfig) {
        characterPool = new HashMap<>();
        debugChars.clear();

        Optional<NpcConfiguration> commonConfig =
                        startConfig.isPresent() ? Optional.of(startConfig.get().npcCommon) : Optional.empty();

        try (InputStreamReader reader = new InputStreamReader(
                        ResourceLoader.getFileResourceAsStream("characters/included.json"))) {
            JsonArray characterSet = JsonUtils.rootJson(reader).getAsJsonArray();
            for (JsonElement element : characterSet) {
                String name = element.getAsString();
                try {
                    NPCData data = JsonSourceNPCDataLoader
                                    .load(ResourceLoader.getFileResourceAsStream("characters/" + name));
                    Optional<NpcConfiguration> npcConfig = findNpcConfig(CustomNPC.TYPE_PREFIX + data.getName(), startConfig);
                    Personality npc = new CustomNPC(data, npcConfig, commonConfig);
                    characterPool.put(npc.getCharacter().getType(), npc.getCharacter());
                    System.out.println("Loaded " + name);
                } catch (JsonParseException e1) {
                    System.err.println("Failed to load NPC " + name);
                    e1.printStackTrace();
                }
            }
        } catch (JsonParseException | IOException e1) {
            System.err.println("Failed to load character set");
            e1.printStackTrace();
        }

        // TODO: Refactor into function and unify with CustomNPC handling.
        Personality cassie = new Cassie(findNpcConfig("Cassie", startConfig), commonConfig);
        Personality angel = new Angel(findNpcConfig("Angel", startConfig), commonConfig);
        Personality reyka = new Reyka(findNpcConfig("Reyka", startConfig), commonConfig);
        Personality kat = new Kat(findNpcConfig("Kat", startConfig), commonConfig);
        Personality mara = new Mara(findNpcConfig("Mara", startConfig), commonConfig);
        Personality jewel = new Jewel(findNpcConfig("Jewel", startConfig), commonConfig);
        Personality airi = new Airi(findNpcConfig("Airi", startConfig), commonConfig);
        Personality eve = new Eve(findNpcConfig("Eve", startConfig), commonConfig);
        Personality maya = new Maya(1, findNpcConfig("Maya", startConfig), commonConfig);
        Personality yui = new Yui(findNpcConfig("Yui", startConfig), commonConfig);
        characterPool.put(cassie.getCharacter().getType(), cassie.getCharacter());
        characterPool.put(angel.getCharacter().getType(), angel.getCharacter());
        characterPool.put(reyka.getCharacter().getType(), reyka.getCharacter());
        characterPool.put(kat.getCharacter().getType(), kat.getCharacter());
        characterPool.put(mara.getCharacter().getType(), mara.getCharacter());
        characterPool.put(jewel.getCharacter().getType(), jewel.getCharacter());
        characterPool.put(airi.getCharacter().getType(), airi.getCharacter());
        characterPool.put(eve.getCharacter().getType(), eve.getCharacter());
        characterPool.put(maya.getCharacter().getType(), maya.getCharacter());
        characterPool.put(yui.getCharacter().getType(), yui.getCharacter());
        debugChars.add(reyka.getCharacter());
    }

    public static Set<Character> everyone() {
        return players;
    }

    public static boolean newChallenger(Personality challenger) {
        if (!players.contains(challenger.getCharacter())) {
            int targetLevel = human.getLevel();
            if (challenger.getCharacter().has(Trait.leveldrainer)) {
                targetLevel -= 4;
            }
            while (challenger.getCharacter().getLevel() <= targetLevel) {
                challenger.getCharacter().ding(null);
            }
            players.add(challenger.getCharacter());
            return true;
        } else {
            return false;
        }
    }

    public static NPC getNPC(String name) {
        for (Character c : allNPCs()) {
            if (c.getType().equalsIgnoreCase(name)) {
                return (NPC) c;
            }
        }
        System.err.println("NPC \"" + name + "\" is not loaded.");
        return null;
    }

    public static boolean characterTypeInGame(String type) {
        return players.stream().anyMatch(c -> type.equals(c.getType()));
    }

    public static Collection<NPC> allNPCs() {
        return characterPool.values();
    }

    public static Character getParticipantsByName(String name) {
        return players.stream().filter(c -> c.getTrueName().equals(name)).findAny().get();
    }

    public static Set<Character> pickCharacters(Collection<Character> avail, Collection<Character> added, int size) {
        List<Character> randomizer = avail.stream()
                        .filter(c -> !c.human())
                        .filter(c -> !c.has(Trait.event))
                        .filter(c -> !added.contains(c))
                        .collect(Collectors.toList());
        Collections.shuffle(randomizer);
        Set<Character> results = new HashSet<>(added);
        results.addAll(randomizer.subList(0, Math.min(Math.max(0, size - results.size())+1, randomizer.size())));
        return results;
    }
}
