package nightgames.global;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import nightgames.characters.CharacterPool;
import nightgames.characters.NPC;
import nightgames.json.JsonUtils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.Optional;

/**
 * Read and write SaveData to and from save files.
 */
public class SaveFile {
    public static void autoSave() {
        save(new File("./auto.ngs"));
    }

    public static void saveWithDialog() {
        Optional<File> file = Global.gui().askForSaveFile();
        if (file.isPresent()) {
            save(file.get());
        }
    }

    public static void save(File file) {
        SaveData data = Global.saveData();
        JsonObject saveJson = data.toJson();

        try (JsonWriter saver = new JsonWriter(new FileWriter(file))) {
            saver.setIndent("  ");
            JsonUtils.getGson().toJson(saveJson, saver);
        } catch (IOException | JsonIOException e) {
            System.err.println("Could not save file " + file + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void loadWithDialog() {
        JFileChooser dialog = new JFileChooser("./");
        FileFilter savesFilter = new FileNameExtensionFilter("Nightgame Saves", "ngs");
        dialog.addChoosableFileFilter(savesFilter);
        dialog.setFileFilter(savesFilter);
        dialog.setMultiSelectionEnabled(false);
        int rv = dialog.showOpenDialog(Global.gui());
        if (rv != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File file = dialog.getSelectedFile();
        if (!file.isFile()) {
            file = new File(dialog.getSelectedFile().getAbsolutePath() + ".ngs");
            if (!file.isFile()) {
                // not a valid save, abort
                JOptionPane.showMessageDialog(Global.gui(), "Nightgames save file not found", "File not found",
                                JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        load(file);
    }

    public static void load(File file) {
        Global.resetForLoad();

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
        Global.gui().populatePlayer(CharacterPool.human);
        if (Global.time == Time.DAY) {
            Global.startDay();
        } else {
            Global.startNight();
        }
    }

    /**
     * Loads game state data into static fields from SaveData object.
     *
     * @param data A SaveData object, as loaded from save files.
     */
    protected static void loadData(SaveData data) {
        CharacterPool.players.addAll(data.players);
        CharacterPool.players.stream().filter(c -> c instanceof NPC).forEach(
                        c -> CharacterPool.characterPool.put(c.getType(), (NPC) c));
        Flag.flags.addAll(data.flags);
        Flag.counters.putAll(data.counters);
        Global.date = data.date;
        Global.time = data.time;
        Global.gui().fontsize = data.fontsize;
    }
}
