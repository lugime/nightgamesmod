package nightgames.trap;

import nightgames.areas.Deployable;
import nightgames.characters.Character;
import nightgames.combat.IEncounter;
import nightgames.global.Global;

import java.util.HashSet;

public abstract class Trap implements Deployable {
    
    protected Character owner;
    private final String name;
    private int strength;
    protected Trap(String name, Character owner) {
        this.name = name;
        this.owner = owner;
        this.setStrength(0);
    }

    public static void buildTrapPool() {
        Global.trapPool = new HashSet<>();
        Global.trapPool.add(new Alarm());
        Global.trapPool.add(new Tripline());
        Global.trapPool.add(new Snare());
        Global.trapPool.add(new SpringTrap());
        Global.trapPool.add(new AphrodisiacTrap());
        Global.trapPool.add(new DissolvingTrap());
        Global.trapPool.add(new Decoy());
        Global.trapPool.add(new Spiderweb());
        Global.trapPool.add(new EnthrallingTrap());
        Global.trapPool.add(new IllusionTrap());
        Global.trapPool.add(new StripMine());
        Global.trapPool.add(new TentacleTrap());
        Global.trapPool.add(new RoboWeb());
    }

    protected abstract void trigger(Character target);

    public boolean decoy() {
        return false;
    }

    public abstract boolean recipe(Character owner);

    public abstract boolean requirements(Character owner);

    public abstract String setup(Character owner);

    public boolean resolve(Character active) {
        if (active != owner) {
            trigger(active);
            return true;
        }
        return false;
    }

    public void setStrength(Character user) {
        this.strength = user.getLevel();
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getStrength() {
        return strength;
    }

    @Override
    public final Character owner() {
        return owner;
    }

    @Override
    public final String toString() {
        return getName();
    }

    @Override
    public final boolean equals(Object obj) {
        return obj != null && getName().equals(obj.toString());
    }
    
    public void capitalize(Character attacker, Character victim, IEncounter enc) {
        // NOP
    }

    public String getName() {
        return name;
    }

}
