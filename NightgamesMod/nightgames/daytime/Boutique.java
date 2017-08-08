package nightgames.daytime;

import nightgames.characters.Character;
import nightgames.global.Flag;
import nightgames.global.GameState;
import nightgames.items.clothing.Clothing;

public class Boutique extends Store {
    public Boutique(Character player) {
        super("Boutique", player);
        Clothing.getAllBuyableFrom("Boutique").forEach(article -> add(article));
    }

    @Override
    public boolean known() {
        if (player.useFemalePronouns()) {
            return Flag.checkFlag(Flag.basicStores);
        }
        return false;
    }

    @Override
    public void visit(String choice) {
        GameState.gui().clearText();
        GameState.gui().clearCommand();
        if (choice.equals("Start")) {
            acted = false;
        }
        if (choice.equals("Leave")) {
            done(acted);
            return;
        }
        checkSale(choice);
        if (player.human()) {
            GameState.gui().message(
                            "This is a higher end store for women's clothing. Things may get a bit expensive here.");
            for (Clothing i : clothing().keySet()) {
                GameState.gui().message(i.getName() + ": " + i.getPrice() + (player.has(i) ? " (Owned)" : ""));
            }
            GameState.gui().message("You have: $" + player.money + " available to spend.");
            displayGoods();
            choose("Leave", GameState.gui());
        }
    }

    @Override
    public void shop(Character npc, int budget) {}

}
