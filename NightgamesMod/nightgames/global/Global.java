package nightgames.global;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import nightgames.requirements.TraitRequirement;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;

import nightgames.Resources.ResourceLoader;
import nightgames.actions.Action;
import nightgames.characters.Airi;
import nightgames.characters.Angel;
import nightgames.characters.Attribute;
import nightgames.characters.Cassie;
import nightgames.characters.Character;
import nightgames.characters.CharacterSex;
import nightgames.characters.Eve;
import nightgames.characters.Jewel;
import nightgames.characters.Kat;
import nightgames.characters.Mara;
import nightgames.characters.Maya;
import nightgames.characters.NPC;
import nightgames.characters.Personality;
import nightgames.characters.Player;
import nightgames.characters.Reyka;
import nightgames.characters.Trait;
import nightgames.characters.TraitTree;
import nightgames.characters.Yui;
import nightgames.characters.custom.CustomNPC;
import nightgames.characters.custom.JsonSourceNPCDataLoader;
import nightgames.characters.custom.NPCData;
import nightgames.daytime.Daytime;
import nightgames.gui.GUI;
import nightgames.gui.HeadlessGui;
import nightgames.items.clothing.Clothing;
import nightgames.json.JsonUtils;
import nightgames.modifier.Modifier;
import nightgames.modifier.standard.NoModifier;
import nightgames.skills.*;
import nightgames.start.NpcConfiguration;
import nightgames.start.PlayerConfiguration;
import nightgames.start.StartConfiguration;
import nightgames.trap.Trap;

public class Global {
    public static Random rng;
    private static GUI gui;
    static HashMap<String, Match.MatchAction> matchActions = null;
    private static Set<Skill> skillPool = new HashSet<>();
    private static Map<String, NPC> characterPool;
    public static Set<Action> actionPool;
    public static Set<Trap> trapPool;
    public static Set<Trait> featPool;
    public static Set<Modifier> modifierPool;
    public static Set<Character> players;
    public static Set<Character> debugChars;
    public static Set<Character> resting;
    private static Set<String> flags;
    private static Map<String, Float> counters;
    public static Player human;
    public static Match match;
    public static Daytime day;
    protected static int date;
    private static Time time;
    private Date jdate;
    public static TraitTree traitRequirements;
    public static Scene current;
    public static boolean debug[] = new boolean[DebugFlags.values().length];
    public static int debugSimulation = 0;
    public static double moneyRate = 1.0;
    public static double xpRate = 1.0;
    public static ContextFactory factory;
    public static Context cx;

    public static final Path COMBAT_LOG_DIR = new File("combatlogs").toPath();

    public Global(boolean headless) {
        rng = new Random();
        flags = new HashSet<>();
        players = new HashSet<>();
        debugChars = new HashSet<>();
        resting = new HashSet<>();
        counters = new HashMap<>();
        jdate = new Date();
        counters.put(Flag.malePref.name(), 0.f);
        Clothing.buildClothingTable();
        Logwriter.makeLogger(jdate);
        TraitRequirement.setTraitRequirements(new TraitTree(ResourceLoader.getFileResourceAsStream("data/TraitRequirements.xml")));
        current = null;
        factory = new ContextFactory();
        cx = factory.enterContext();
        Formatter.buildParser();
        Action.buildActionPool();
        Trait.buildFeatPool();
        Skill.buildSkillPool(noneCharacter);
        Modifier.buildModifierPool();
        gui = makeGUI(headless);
    }

    protected GUI makeGUI(boolean headless) {
        return headless ? new HeadlessGui() : new GUI();
    }

    public static boolean isDebugOn(DebugFlags flag) {
        return debug[flag.ordinal()] && debugSimulation == 0;
    }

    public static void newGame(String playerName, Optional<StartConfiguration> config, List<Trait> pickedTraits,
                    CharacterSex pickedGender, Map<Attribute, Integer> selectedAttributes) {
        Optional<PlayerConfiguration> playerConfig = config.map(c -> c.player);
        Collection<String> cfgFlags = config.map(StartConfiguration::getFlags).orElse(new ArrayList<>());
        human = new Player(playerName, pickedGender, playerConfig, pickedTraits, selectedAttributes);
        if(human.has(Trait.largereserves)) {
            human.getWillpower().gain(20);
        }
        players.add(human);
        if (gui != null) {
            gui.populatePlayer(human);
        }
        Skill.buildSkillPool(human);
        Clothing.buildClothingTable();
        learnSkills(human);
        rebuildCharacterPool(config);
        // Add starting characters to players
        players.addAll(characterPool.values().stream().filter(npc -> npc.isStartCharacter).collect(Collectors.toList()));
        if (!cfgFlags.isEmpty()) {
            flags = cfgFlags.stream().collect(Collectors.toSet());
        }
        Map<String, Boolean> configurationFlags = JsonUtils.mapFromJson(JsonUtils.rootJson(new InputStreamReader(ResourceLoader.getFileResourceAsStream("data/globalflags.json"))).getAsJsonObject(), String.class, Boolean.class);
        configurationFlags.forEach((flag, val) -> Global.setFlag(flag, val));
        time = Time.NIGHT;
        date = 1;
        Formatter.setCharacterDisabledFlag(getNPCByType("Yui"));
        setFlag(Flag.systemMessages, true);
        Match.setUpMatch(new NoModifier());
    }

    public static GUI gui() {
        return gui;
    }

    /**
     * WARNING DO NOT USE THIS IN ANY COMBAT RELATED CODE.
     * IT DOES NOT TAKE INTO ACCOUNT THAT THE PLAYER GETS CLONED. WARNING. WARNING.
     * @return
     */
    public static Player getPlayer() {
        return human;
    }

    public static Set<Action> getActions() {
        return actionPool;
    }

    public static List<Trait> getFeats(Character c) {
        List<Trait> a = getTraitRequirements().availTraits(c);
        a.sort((first, second) -> first.toString().compareTo(second.toString()));
        return a;
    }

    public static Time getTime() {
        return time;
    }

    public static Match getMatch() {
        return match;
    }

    public static Daytime getDay() {
        return day;
    }

    public static void startDay() {
        match = null;
        day = new Daytime(human);
        day.plan();
    }

    /**
     * Sets the time to DAY, since the order of operations changed and manual end-of-match
     * saves got flagged as NIGHT instead.
     */
    public static void endNightForSave() {
        time = Time.DAY;
    }
    
    public static void endNight() {
        double level = 0;
        int maxLevelTracker = 0;

        for (Character player : players) {
            player.getStamina().fill();
            player.getArousal().empty();
            player.getMojo().empty();
            player.change();
            level += player.getLevel();
            if (!player.has(Trait.unnaturalgrowth) && !player.has(Trait.naturalgrowth)) {
                maxLevelTracker = Math.max(player.getLevel(), maxLevelTracker);
            }
        }
        final int maxLevel = maxLevelTracker / players.size();
        players.stream().filter(c -> c.has(Trait.naturalgrowth)).filter(c -> c.getLevel() < maxLevel + 2).forEach(c -> {
            while (c.getLevel() < maxLevel + 2) {
                c.ding(null);
            }
        });
        players.stream().filter(c -> c.has(Trait.unnaturalgrowth)).filter(c -> c.getLevel() < maxLevel + 5)
                        .forEach(c -> {
                            while (c.getLevel() < maxLevel + 5) {
                                c.ding(null);
                            }
                        });

        level /= players.size();

        for (Character rested : resting) {
            rested.gainXP(100 + Math.max(0, (int) Math.round(10 * (level - rested.getLevel()))));
        }
        date++;
        time = Time.DAY;
        if (Global.checkFlag(Flag.autosave)) {
            Global.autoSave();
        }
        Match.endMatch(Global.gui());
    }

    public static void endDay() {
        day = null;
        time = Time.NIGHT;
        if (checkFlag(Flag.autosave)) {
            autoSave();
        }
        startNight();
    }

    public static void startNight() {
        Match.decideMatchType().buildPrematch(human);
    }

    public static List<Character> getInAffectionOrder(List<Character> viableList) {
        List<Character> results = new ArrayList<>(viableList);
        results.sort((a, b) -> a.getAffection(getPlayer()) - b.getAffection(getPlayer()));
        return results;
    }

    public static String gainSkills(Character c) {
        String message = "";
        if (c.getPure(Attribute.Dark) >= 6 && !c.has(Trait.darkpromises)) {
            c.add(Trait.darkpromises);
        } else if (!(c.getPure(Attribute.Dark) >= 6) && c.has(Trait.darkpromises)) {
            c.remove(Trait.darkpromises);
        }
        boolean pheromonesRequirements = c.getPure(Attribute.Animism) >= 2 || c.has(Trait.augmentedPheromones);
        if (pheromonesRequirements && !c.has(Trait.pheromones)) {
            c.add(Trait.pheromones);
        } else if (!pheromonesRequirements && c.has(Trait.pheromones)) {
            c.remove(Trait.pheromones);
        }
        return message;
    }

    public static void learnSkills(Character c) {
        for (Skill skill : getSkillPool()) {
            c.learn(skill);
        }
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

    public static void flag(String f) {
        flags.add(f);
    }

    public static void unflag(String f) {
        flags.remove(f);
    }

    public static void flag(Flag f) {
        flags.add(f.name());
    }

    public static void unflag(Flag f) {
        flags.remove(f.name());
    }

    public static void setFlag(String f, boolean value) {
        if (value) { 
            flag(f);
        } else {
            unflag(f);
        }
    }

    public static void setFlag(Flag f, boolean value) {
        if (value) { 
            flags.add(f.name()); 
        } else { 
            flags.remove(f.name()); 
        }
    }

    public static boolean checkFlag(Flag f) {
        return flags.contains(f.name());
    }

    public static boolean checkFlag(String key) {
        return flags.contains(key);
    }

    public static float getValue(Flag f) {
        if (!counters.containsKey(f.name())) {
            return 0;
        } else {
            return counters.get(f.name());
        }
    }

    public static void modCounter(Flag f, float inc) {
        counters.put(f.name(), getValue(f) + inc);
    }

    public static void setCounter(Flag f, float val) {
        counters.put(f.name(), val);
    }

    public static void autoSave() {
        save(new File("./auto.ngs"));
    }

    public static void saveWithDialog() {
        Optional<File> file = gui().askForSaveFile();
        if (file.isPresent()) {
            save(file.get());
        }
    }

    protected static SaveData saveData() {
        SaveData data = new SaveData();
        data.players.addAll(players);
        data.flags.addAll(flags);
        data.counters.putAll(counters);
        data.time = time;
        data.date = date;
        data.fontsize = gui.fontsize;
        return data;
    }

    public static void save(File file) {
        SaveData data = saveData();
        JsonObject saveJson = data.toJson();

        try (JsonWriter saver = new JsonWriter(new FileWriter(file))) {
            saver.setIndent("  ");
            JsonUtils.getGson().toJson(saveJson, saver);
        } catch (IOException | JsonIOException e) {
            System.err.println("Could not save file " + file + ": " + e.getMessage());
            e.printStackTrace();
        }
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
    
    public static void loadWithDialog() {
        JFileChooser dialog = new JFileChooser("./");
        FileFilter savesFilter = new FileNameExtensionFilter("Nightgame Saves", "ngs");
        dialog.addChoosableFileFilter(savesFilter);
        dialog.setFileFilter(savesFilter);
        dialog.setMultiSelectionEnabled(false);
        int rv = dialog.showOpenDialog(gui);
        if (rv != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File file = dialog.getSelectedFile();
        if (!file.isFile()) {
            file = new File(dialog.getSelectedFile().getAbsolutePath() + ".ngs");
            if (!file.isFile()) {
                // not a valid save, abort
                JOptionPane.showMessageDialog(gui, "Nightgames save file not found", "File not found",
                                JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        load(file);
    }

    protected static void resetForLoad() {
        players.clear();
        flags.clear();
        gui.clearText();
        human = new Player("Dummy");
        gui.purgePlayer();
        Skill.buildSkillPool(human);
        Clothing.buildClothingTable();
        rebuildCharacterPool(Optional.empty());
        day = null;
    }

    public static void load(File file) {
        resetForLoad();

        JsonObject object;
        try (Reader loader = new InputStreamReader(new FileInputStream(file))) {
            object = new JsonParser().parse(loader).getAsJsonObject();

        } catch (IOException e) {
            e.printStackTrace();
            // Couldn't load data; just get out
            return;
        }
        SaveData data = new SaveData(object);
        loadData(data);
        gui.populatePlayer(human);
        if (time == Time.DAY) {
            startDay();
        } else {
            startNight();
        }
    }

    /**
     * Loads game state data into static fields from SaveData object.
     *
     * @param data A SaveData object, as loaded from save files.
     */
    protected static void loadData(SaveData data) {
        players.addAll(data.players);
        players.stream().filter(c -> c instanceof NPC).forEach(
                        c -> characterPool.put(c.getType(), (NPC) c));
        flags.addAll(data.flags);
        counters.putAll(data.counters);
        date = data.date;
        time = data.time;
        gui.fontsize = data.fontsize;
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



    public static String getIntro() {
        return "You don't really know why you're going to the Student Union in the middle of the night."
                        + " You'd have to be insane to accept the invitation you received this afternoon."
                        + " Seriously, someone is offering you money to sexfight a bunch of other students?"
                        + " You're more likely to get mugged (though you're not carrying any money) or murdered if you show up."
                        + " Best case scenario, it's probably a prank for gullible freshmen."
                        + " You have no good reason to believe the invitation is on the level, but here you are, walking into the empty Student Union."
                        + "\n\n" + "Not quite empty, it turns out."
                        + " The same woman who approached you this afternoon greets you and brings you to a room near the back of the building."
                        + " Inside, you're surprised to find three quite attractive girls."
                        + " After comparing notes, you confirm they're all freshmen like you and received the same invitation today."
                        + " You're surprised, both that these girls would agree to such an invitation."
                        + " For the first time, you start to believe that this might actually happen."
                        + " After a few minutes of awkward small talk (though none of these girls seem self-conscious about being here), the woman walks in again leading another girl."
                        + " Embarrassingly you recognize the girl, named Cassie, who is a classmate of yours, and who you've become friends with over the past couple weeks."
                        + " She blushes when she sees you and the two of you consciously avoid eye contact while the woman explains the rules of the competition."
                        + "\n\n" + "There are a lot of specific points, but the rules basically boil down to this: "
                        + " competitors move around the empty areas of the campus and engage each other in sexfights."
                        + " When one competitor orgasms and doesn't have the will to go on, the other gets a point and can claim the loser's clothes."
                        + " Those two players are forbidden to engage again until the loser gets a replacement set of clothes at either the Student Union or the first floor of the dorm building."
                        + " It seems to be customary, but not required, for the loser to get the winner off after a fight, when it doesn't count."
                        + " After three hours, the match ends and each player is paid for each opponent they defeat, each set of clothes turned in, and a bonus for whoever scores the most points."
                        + "\n\n"
                        + "After the explanation, she confirms with each participant whether they are still interested in participating."
                        + " Everyone agrees." + " The first match starts at exactly 10:00.";
    }

    public static void reset() {
        players.clear();
        flags.clear();
        day = null;
        match = null;
        human = new Player("Dummy");
        gui.purgePlayer();
        xpRate = 1.0;
        gui.createCharacter();
    }

    public static boolean inGame() {
        return !players.isEmpty();
    }

    public static boolean characterTypeInGame(String type) {
        return players.stream().anyMatch(c -> type.equals(c.getType()));
    }

    public static int getDate() {
        return date;
    }


    private static Character noneCharacter = new NPC("none", 1, null);

    public static Character noneCharacter() {
        return noneCharacter;
    }

    public static String prependPrefix(String prefix, String fullDescribe) {
        if (prefix.equals("a ") && "aeiou".contains(fullDescribe.substring(0, 1).toLowerCase())) {
            return "an " + fullDescribe;
        }
        return prefix + fullDescribe;
    }

    public static Collection<NPC> allNPCs() {
        return characterPool.values();
    }

    public static Set<Skill> getSkillPool() {
        return skillPool;
    }

    public static Set<Modifier> getModifierPool() {
        return modifierPool;
    }

    public static int clamp(int number, int min, int max) {
        return Math.min(Math.max(number, min), max);
    }

    public static double clamp(double number, double min, double max) {
        return Math.min(Math.max(number, min), max);
    }

    public static Character getParticipantsByName(String name) {
        return players.stream().filter(c -> c.getTrueName().equals(name)).findAny().get();
    }

    public static Random FROZEN_RNG = new Random();

    public static TraitTree getTraitRequirements() {
        return traitRequirements;
    }

    public static void main(String[] args) {
        new Logwriter();
        for (String arg : args) {
            try {
                DebugFlags flag = DebugFlags.valueOf(arg);
                debug[flag.ordinal()] = true;
            } catch (IllegalArgumentException e) {
                // pass
            }
        }
        new Global(false);
    }
}
