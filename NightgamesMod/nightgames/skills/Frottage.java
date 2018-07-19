package nightgames.skills;

import nightgames.characters.Attribute;
import nightgames.characters.Character;
import nightgames.characters.Emotion;
import nightgames.characters.Trait;
import nightgames.characters.body.BodyPart;
import nightgames.characters.body.StraponPart;
import nightgames.combat.Combat;
import nightgames.combat.Result;
import nightgames.global.Global;
import nightgames.nskills.tags.SkillTag;
import nightgames.stance.Stance;
import nightgames.status.BodyFetish;

public class Frottage extends Skill {

    public Frottage(Character self) {
        super("Frottage", self);
        addTag(SkillTag.pleasureSelf);
    }

    @Override
    public boolean requirements(Combat c, Character user, Character target) {
        return user.get(Attribute.Seduction) >= 26;
    }

    @Override
    public boolean usable(Combat c, Character target) {
        return getSelf().canAct() && c.getStance().mobile(getSelf()) && !c.getStance().sub(getSelf())
                        && !c.getStance().havingSex(c) && target.crotchAvailable()
                        && (getSelf().hasDick() && getSelf().crotchAvailable() || getSelf().has(Trait.strapped))
                        && c.getStance().reachBottom(getSelf())
                        && c.getStance().en != Stance.facesitting;
    }

    @Override
    public String describe(Combat c) {
        return "Rub yourself against your opponent";
    }

    @Override
    public int getMojoBuilt(Combat c) {
        return 10;
    }

    @Override
    public boolean resolve(Combat c, Character target) {
        int m = 6 + Global.random(8);
        BodyPart receiver = target.hasDick() ? target.body.getRandomCock() : target.body.getRandomPussy();
        BodyPart dealer = getSelf().hasDick() ? getSelf().body.getRandomCock() : getSelf().has(Trait.strapped) ? StraponPart.generic : getSelf().body.getRandomPussy();
        if (getSelf().human()) {
            if (target.hasDick()) {
                c.write(getSelf(), deal(c, m, Result.special, target));
            } else {
                c.write(getSelf(), deal(c, m, Result.normal, target));
            }
        } else if (getSelf().has(Trait.strapped)) {
            c.write(getSelf(), receive(c, m, Result.special, target));
            target.loseMojo(c, 10);
            dealer = null;
        } else {
            c.write(getSelf(), receive(c, m, Result.normal, target));
        }

        if (dealer != null) {
            getSelf().body.pleasure(target, receiver, dealer, m / 2, c, this);
        }
        target.body.pleasure(getSelf(), dealer, receiver, m, c, this);
        if (Global.random(100) < 20 + 2 * getSelf().get(Attribute.Fetish)) {
            target.add(c, new BodyFetish(target, getSelf(), "cock", .25));
        }
        getSelf().emote(Emotion.horny, 15);
        target.emote(Emotion.horny, 15);
        return true;
    }

    @Override
    public Skill copy(Character user) {
        return new Frottage(user);
    }

    @Override
    public Tactics type(Combat c) {
        return Tactics.pleasure;
    }

    @Override
    public String deal(Combat c, int damage, Result modifier, Character target) {
        if (modifier == Result.special) {
            return "You tease " + target.getName() + "'s penis with your own, dueling her like a pair of fencers.";
        } else {
            return "You press your hips against " + target.getName()
                            + "'s legs, rubbing her nether lips and clit with your dick.";
        }
    }

    @Override
    public String receive(Combat c, int damage, Result modifier, Character target) {
        if (modifier == Result.special) {
            return String.format("%s thrusts %s hips to prod %s delicate jewels with %s strapon dildo. "
                            + "As %s and %s %s hips back, %s presses the toy against %s cock, "
                            + "teasing %s sensitive parts.",
                            getSelf().subject(), getSelf().possessiveAdjective(), target.nameOrPossessivePronoun(),
                            getSelf().possessiveAdjective(),
                            target.subjectAction("flinch", "flinches"), target.action("pull"), target.possessiveAdjective(),
                            getSelf().subject(), target.possessiveAdjective(), target.possessiveAdjective());
             } else if (modifier == Result.special) {
            // Eve
            return String.format(
                            "Firmly gripping your shoulders, Eve pushes her penis against yours. <i>\"I think your little cock should"
                                            + " greet its senpai, %s. Play nice now.\"</i> She then proceed to mash her hipsagainst yours,"
                                            + " grinding your shafts together. Completely outmatched by her %s, you try to disengage"
                                            + " but it only makes you rub her harder. Finally, you manage to stumble away, your crotch quite a"
                                            + " bbit wet from the precum you released. <i>\"Uh uh, seems like you'll be toasted soon, my little cumslut-to-be."
                                            + " Now get ready to take it.\"</i>,
target.getName(), getSelf().body.getRandomCock().describe(getSelf()));
        } else if (getSelf().hasDick() && target.hasDick()) {
            return String.format("%s pushes %s %s against the sensitive head of %s member, "
                            + "dominating %s manhood.", getSelf().subject(), getSelf().possessiveAdjective(),
                            getSelf().body.getRandomCock().describe(getSelf()), target.nameOrPossessivePronoun(),
                            target.possessiveAdjective());
        } else {
            return String.format("%s pushes %s cock against her soft thighs, rubbing %s shaft up"
                            + " against %s nether lips.", getSelf().subject(),
                            target.nameOrPossessivePronoun(), target.possessiveAdjective(),
                            getSelf().possessiveAdjective());
        }
    }

    @Override
    public boolean makesContact(Combat c) {
        return true;
    }
}
