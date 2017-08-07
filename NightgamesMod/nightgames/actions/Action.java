package nightgames.actions;

import java.io.Serializable;
import java.util.HashSet;

import nightgames.characters.Character;
import nightgames.global.Global;
import nightgames.gui.GUI;
import nightgames.gui.RunnableButton;
import nightgames.items.Item;
import nightgames.trap.Trap;

public abstract class Action implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 4981682001213276175L;
    protected String name;

    public Action(String name) {
        this.name = name;
    }

    public static void buildActionPool() {
        Global.actionPool = new HashSet<>();
        Global.actionPool.add(new Resupply());
        Global.actionPool.add(new Wait());
        Global.actionPool.add(new Hide());
        Global.actionPool.add(new Bathe());
        Global.actionPool.add(new Scavenge());
        Global.actionPool.add(new Craft());
        Global.actionPool.add(new Use(Item.Lubricant));
        Global.actionPool.add(new Use(Item.EnergyDrink));
        Global.actionPool.add(new Use(Item.Beer));
        Global.actionPool.add(new Recharge());
        Global.actionPool.add(new Locate());
        Global.actionPool.add(new MasturbateAction());
        Global.actionPool.add(new Energize());
        Global.actionPool.add(new Disguise());
        Global.actionPool.add(new BushAmbush());
        Global.actionPool.add(new PassAmbush());
        Global.actionPool.add(new TreeAmbush());
        Global.actionPool.add(new Struggle());
        Trap.buildTrapPool();
        for (Trap t : Global.trapPool) {
            Global.actionPool.add(new SetTrap(t));
        }
    }

    public abstract boolean usable(Character user);

    public abstract Movement execute(Character user);

    @Override
    public String toString() {
        return name;
    }

    public abstract Movement consider();

    public boolean freeAction() {
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (name == null ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Action other = (Action) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    public void choose(String choice, Character self, GUI gui) {
        RunnableButton button = new RunnableButton(choice, () -> {
            ((Locate) this).handleEvent(self, choice);
        });
        gui.commandPanel.add(button);
        gui.commandPanel.refresh();
    }
}
