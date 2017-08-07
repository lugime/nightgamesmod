package nightgames.characters;

import nightgames.global.Flag;
import nightgames.global.Formatter;

public enum CharacterSex {
    male("male"),
    female("female"),
    shemale("shemale"),
    trap("trap"),
    herm("hermaphrodite"),
    asexual("asexual");

    private String desc;

    CharacterSex(String desc) {
        this.desc = desc;
    }

    public boolean hasPussy() {
        return this == female || this == herm;
    }

    public boolean hasCock() {
        return this == male || this == trap || this == herm || this == shemale;
    }

    public boolean hasBalls() {
        return this == male || this == trap || (this == herm && Flag
                        .checkFlag(Flag.hermHasBalls))|| (this == shemale && !Flag.checkFlag(Flag.shemaleNoBalls));
    }

    @Override
    public String toString() {
        return Formatter.capitalizeFirstLetter(desc);
    }

    public boolean considersItselfFeminine() {
        return this == female || this == herm || this == shemale || this == trap;
    }
}
