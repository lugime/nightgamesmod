package nightgames.characters;

import nightgames.gui.GUI;
import nightgames.gui.KeyableButton;
import nightgames.gui.RunnableButton;

import java.io.Serializable;

public class AttributeButton implements Serializable {
    public static KeyableButton attributeButton(GUI gui, Attribute att) {
        return new RunnableButton(att.name(), () -> {
            gui.clearTextIfNeeded();
            CharacterPool.getPlayer().mod(att, 1);
            CharacterPool.getPlayer().availableAttributePoints -= 1;
            gui.refresh();
            Player.ding(gui);
        });
    }
}
