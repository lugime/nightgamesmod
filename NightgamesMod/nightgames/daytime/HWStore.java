package nightgames.daytime;

import java.util.Map;

import nightgames.characters.Character;
import nightgames.characters.Trait;
import nightgames.global.Flag;
import nightgames.gui.GUI;
import nightgames.items.Item;

public class HWStore extends Store {
    public HWStore(Character player) {
        super("Hardware Store", player);
        add(Item.Tripwire);
        add(Item.Rope);
        add(Item.Spring);
        add(Item.Sprayer);
        add(Item.EmptyBottle);
    }

    @Override
    public boolean known() {
        return Flag.checkFlag(Flag.basicStores);
    }

    @Override
    public void visit(String choice) {
        GUI.gui.clearText();
        GUI.gui.clearCommand();
        if (choice.equals("Start")) {
            acted = false;
        }
        if (choice.equals("Leave")) {
            done(acted);
            return;
        }
        checkSale(choice);
        if (player.human()) {
            GUI.gui.message(
                            "Nothing at the hardware store is designed for the sort of activities you have in mind, but there are components you could use to make some "
                                            + "effective traps.");
            Map<Item, Integer> MyInventory = this.player.getInventory();
            for (Item i : stock.keySet()) {
                if (MyInventory.get(i) == null || MyInventory.get(i) == 0) {
                    GUI.gui.message(i.getName() + ": $" + i.getPrice());
                } else {
                    GUI.gui.message(
                                    i.getName() + ": $" + i.getPrice() + " (you have: " + MyInventory.get(i) + ")");
                }
            }
            GUI.gui.message("You have : $" + player.money + " to spend.");
            displayGoods();
            choose("Leave", GUI.gui);
        }
    }

    @Override
    protected void displayItems() {
        for (Item i : stock.keySet()) {
            if (i != Item.EmptyBottle || player.getRank() > 0) {
                sale(i, GUI.gui);
            }
        }
    }

    @Override
    public void shop(Character npc, int budget) {
        int remaining = budget;
        int bored = 0;
        while (remaining > 10 && bored < 10) {
            for (Item i : stock.keySet()) {
                boolean emptyBottleCheck = npc.has(Trait.madscientist) || i != Item.EmptyBottle;
                if (remaining > i.getPrice() && !npc.has(i, 20) && emptyBottleCheck) {
                    npc.gain(i);
                    npc.money -= i.getPrice();
                    remaining -= i.getPrice();
                } else {
                    bored++;
                }
            }
        }
    }
}
