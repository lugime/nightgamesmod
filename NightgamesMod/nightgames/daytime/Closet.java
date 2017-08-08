package nightgames.daytime;

import nightgames.characters.Character;
import nightgames.gui.GUI;

public class Closet extends Activity {

    public Closet(Character player) {
        super("Change Clothes", player);
    }

    @Override
    public boolean known() {
        return true;
    }

    @Override
    public void visit(String choice) {
        GUI.gui.clearText();
        GUI.gui.clearCommand();
        if (choice.equals("Start")) {
            GUI.gui.changeClothes(player, this, "Back");
        } else {
            done(false);
        }
    }

    @Override
    public void shop(Character npc, int budget) {
        // TODO Auto-generated method stub

    }

}
