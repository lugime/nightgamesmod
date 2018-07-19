package nightgames.characters;

import java.util.Arrays;
import java.util.Optional;

import nightgames.characters.body.AssPart;
import nightgames.characters.body.CockMod;
import nightgames.characters.body.FacePart;
import nightgames.characters.body.mods.CyberneticMod;
import nightgames.characters.custom.CharacterLine;
import nightgames.combat.Combat;
import nightgames.combat.CombatScene;
import nightgames.combat.CombatSceneChoice;
import nightgames.combat.Result;
import nightgames.global.Global;
import nightgames.items.Item;
import nightgames.items.clothing.Clothing;
import nightgames.skills.strategy.FootjobStrategy;
import nightgames.skills.strategy.StraponStrategy;
import nightgames.skills.strategy.TechStrategy;
import nightgames.skills.strategy.UseToyStrategy;
import nightgames.skills.strategy.WindUpStrategy;
import nightgames.start.NpcConfiguration;
import nightgames.status.Hypersensitive;
import nightgames.status.Oiled;
import nightgames.status.addiction.Addiction;
import nightgames.status.addiction.AddictionType;

public class Mara extends BasePersonality {
    /**
     *
     */
    private static final long serialVersionUID = -3812726803607189573L;

    private static final String MARA_GENERAL_TECH_FOCUS = "MaraGeneralTechFocus";
    private static final String MARA_HARPOON_FOCUS = "MaraHarpoonFocus";
    private static final String MARA_OCTO_FOCUS = "MaraOctoFocus";
    private static final String MARA_MIND_CONTROL_FOCUS = "MaraMindControlFocus";

    public Mara() {
        this(Optional.empty(), Optional.empty());
    }

    public Mara(Optional<NpcConfiguration> charConfig, Optional<NpcConfiguration> commonConfig) {
        super("Mara", charConfig, commonConfig, true);
        constructLines();
    }

    private void constructLines () {
        character.addLine(CharacterLine.BB_LINER, (c, self, other) -> { 
            return "{self:SUBJECT} gives you a look of not quite genuine concern. <i>\"That must have really hurt. Sorry for scrambling your eggs. I feel really bad about that. Also for "
                            + "lying just now. I'm not actually that sorry.\"</i>";
        });
        character.addLine(CharacterLine.CHALLENGE, (c, self, other) -> {
            return "{self:SUBJECT} smiles and faces {other:name-do}, practically daring {other:direct-object} to attack.";
        });

        character.addLine(CharacterLine.NAKED_LINER, (c, self, other) -> { 
            return "{self:SUBJECT} gives an exaggerated squeal and covers herself. <i>\"You brute! You rapist! What are you trying to do to a helpless, innocent girl?\"</i>";
         });

        character.addLine(CharacterLine.STUNNED_LINER, (c, self, other) -> { 
            return "{self:SUBJECT} lets out a slightly pained whimper. <i>\"Go easy on me. I'm not really the masochistic type.\"</i>";
         });

        character.addLine(CharacterLine.TAUNT_LINER, (c, self, other) -> { 
            return "<i>\"If you want me to get you off so badly,\"</i> {self:SUBJECT} teases coyly. <i>\"You should have just said so from the start. You don't need to put up this token resistance.\"</i>";
         });

        character.addLine(CharacterLine.TEMPT_LINER, (c, self, other) -> { 
            return "<i>\"If you want me to get you off so badly,\"</i> {self:SUBJECT} teases coyly. <i>\"You should have just said so from the start. You don't need to put up this token resistance.\"</i>";
         });

        character.addLine(CharacterLine.NIGHT_LINER, (c, self, other) -> { 
            return "On your way back to your dorm after the match, you feel a sudden weight on your back that almost knocks you off your feet. It turns out to be {self:name-do}, who jumped "
                            + "on your back in {self:possessive} enthusiasm to spend the night together. You give {self:possessive} a piggyback ride back to the dorm, and per {self:possessive} request, head up to the roof. Unsurprisingly, "
                            + "there's no one here this late at night and there's a good view of the stars. {self:SUBJECT} strips off {self:possessive} clothes and dances naked onto the rooftop. <i>\"There's nothing like "
                            + "being naked in the moonlight. Come on!\"</i> You undress and put your clothes in a neat pile, taking the time to gather up hers as well. You walk up behind {self:possessive} and hold "
                            + "{self:possessive} while enjoying the view. The night air is slightly cool, but {self:possessive} nude body is warm in your arms. {self:PRONOUN} turns {self:possessive} head to give you a tender kiss before stepping out of "
                            + "your embrace. <i>\"Have you ever danced naked under the stars?\"</i> It's a strange question, but {self:pronoun} looks too lovely in this light to refuse. The two of you dance without any "
                            + "hint of style or rhythm, not caring how rediculous you'd look to a third party. When you've both tired, you spend some time just looking at the stars together. You "
                            + "never would have imagined this is how you'd be spending your night, but {self:subject} always finds ways to surprise you. You suddenly realize {self:pronoun}'s no longer standing next to "
                            + "you. You spot {self:possessive} back by the door, holding your clothes. {self:PRONOUN} winks mischeviously and dashes into the building. You give chase, still naked. You manage to catch {self:possessive} just "
                            + "as {self:pronoun} reaches your room. You consider it a minor miracle no one saw the two of you streaking through the dorm building. You're going to have to find a way to pay {self:possessive} back "
                            + "before morning.";
         });

        character.addLine(CharacterLine.ORGASM_LINER, (c, self, other) -> { 
            final String finalLines[] =
                            {"<i>\"NNnnnn..! Oh man I can't take much more!\"</i>", "<i>\"Ngh! Slow down! please!\"</i>",
                                            "{self:SUBJECT} groans as {self:possessive} climax subsides, <i>\"Again! I deman a rematch!\"</i>",};
            switch (self.orgasms) {
                case 0:
                    return "<i>\"Aw man, that one didn't count! Come on, let's go, I'll fuck your brains out!\"</i>";
                case 1:
                    return "<i>\"No fair! I'll get you next time!\"</i>";
                case 2:
                    return "<i>\"AAAHHH!\"</i> {self:subject} tries to catch {self:possessive} breath, \"There's... no way... you can keep this up!\"";
                default:
                    return Global.pickRandom(Arrays.asList(finalLines))
                                 .get();
             }
         });

        character.addLine(CharacterLine.MAKE_ORGASM_LINER, (c, self, other) -> { 
            final String finalLines[] = {"<i>\"Cumming again? You " + other.boyOrGirl() + "s are too easy.\"</i>",
                            "<i>\"You're simply inexhaustible aren't you? Let's test that theory... for science.\"</i>",
                            "{self:SUBJECT} laughs triumphantly, <i>\"Again! Again!\"</i>",};
            switch (other.orgasms) {
                case 0:
                    return "{self:SUBJECT} lets out an impish little smirk, <i>\"Haha, all that talk, but you cum as soon as I touch you.\"</i>";
                case 1:
                    return "<i>\"Looks like {other:MISTER} Happy over here can't help {other:reflective}! That's twice now, how long can you go?\"</i>";
                case 2:
                    return "<i>\"Oh man, not many people lasted to three. I got more coming for you though!\"</i>";
                default:
                    return Global.pickRandom(Arrays.asList(finalLines))
                                 .get();
             }
         });

        character.addLine(CharacterLine.LEVEL_DRAIN_LINER, (c, self, other) -> {
            String part = Global.pickRandom(c.getStance().getPartsFor(c, self, other)).map(bp -> bp.describe(self)).orElse("pussy");
            if (other.getLevel() < self.getLevel() - 5) {
                return "\"<i>Annddd... here it comes!</i>\" {self:SUBJECT} happily squeals as the now familiar sensation of your strength entering {self:possessive} flows through the petite girl. "
                                + "You try struggling out, but {self:subject} simply holds you down with a single hand. \"<i>Now now, don't get antsy! "
                                + "You know you're already much weaker than me right? So don't struggle and just let me take everything!</i>\"";
            } else if (other.getLevel() >= self.getLevel()) {
                return "{self:SUBJECT} narrows {self:possessive} eyes in pleasure as your strength is absorbed by {self:possessive} " + part + " as you cum. <i>\"Oh {other:boy}, that is some GOOD stuff! "
                                + "Don't be stingy, give me some more!\"</i>";
            } else {
                return "\"<i>You know {other:NAME}, I don't know why I bothered working so hard before. "
                                + "Training, studying, preparing took so much time! It's so much <b>easier</b> to just take it from you! "
                                + "And now that I'm stronger than you, you can't say no anymore, can you?</i>\"";
            }
        });

        character.addLine(CharacterLine.DESCRIBE_LINER, (c, self, other) -> {
            if (character.has(Trait.madscientist) && !self.isDemonic()) {
                return "{self:SUBJECT} has gone high tech. {self:PRONOUN} has a rig of equipment on harnesses that seem carefully placed so as not to interfere with clothing removal. The glasses {self:pronoun}'s wearing appear to be "
                                + "computerized rather than prescription. {self:PRONOUN} also has a device of unknown purpose strapped to {self:possessive} arm. Underneath all of that, {self:pronoun} has the same cute, mischievous expression {self:pronoun} "
                                + "you're used to.";
            } else if (character.has(Trait.madscientist) && self.isDemonic()) {
                return "{self:SUBJECT} seems to have gotten too much of the demonic juju. Despite going high tech, {self:PRONOUN}'s rig of equipment  {self:possessive} fancy rig of various gadgets and computerized glasses seem to have "
                                + "something newer and more sinister behind them. {self:POSSESSIVE} eyes seem cruel and evil, bent on serving darker, more demonic powers. {self:POSSESSIVE} body seems a little different, too.</br>"
                                + "Underneath all of the equipment, {self:SUBJECT} the cyberdemon has the same cute, mischievous expression that you're used to...";
            } else {
                return "{self:SUBJECT} is short and slender, with a small heart shaped face. {self:PRONOUN} has dark skin, and short, curly black hair. {self:POSSESSIVE} size and cute features make {self:possessive} look a few years "
                                + "younger than {self:pronoun} actually is, and {self:pronoun} wears a near constant playful smile. {self:PRONOUN}'s far from physically intimidating, but {self:possessive} sharp eyes reveal {self:possessive} exceptional intellect.";
            }
        });
        
        character.addLine(CharacterLine.VICTORY_LINER, (c, self, other) -> {
            return "{self:SUBJECT} shouts in triumph, <i>\"Ha-haaa! Science wins AGAIN!!\"</i>";
        });
        
        character.addLine(CharacterLine.LOSER_LINER, (c, self, other) -> {
            return "{self:SUBJECT} mutters, <i>\"Crap...Need to make adjustments...\"</i>";
        });
        
    }

    @Override
    public void applyStrategy(NPC self) {
        self.plan = Plan.hunting;
        self.mood = Emotion.confident;

        self.addPersonalStrategy(new FootjobStrategy());
        self.addPersonalStrategy(new UseToyStrategy());
        self.addPersonalStrategy(new StraponStrategy());
        self.addPersonalStrategy(new WindUpStrategy());
        self.addPersonalStrategy(new TechStrategy());
    }

    @Override
    public void applyBasicStats(Character self) {
        preferredCockMod = CockMod.bionic;
        self.outfitPlan.add(Clothing.getByID("bra"));
        self.outfitPlan.add(Clothing.getByID("Tshirt"));
        self.outfitPlan.add(Clothing.getByID("underwear"));
        self.outfitPlan.add(Clothing.getByID("shorts"));
        self.outfitPlan.add(Clothing.getByID("pantyhose"));
        self.outfitPlan.add(Clothing.getByID("boots"));
        self.change();
        self.modAttributeDontSaveData(Attribute.Cunning, 2);
        self.modAttributeDontSaveData(Attribute.Perception, 2);
        self.getStamina().setMax(80);
        self.getArousal().setMax(80);
        self.getMojo().setMax(120);

        Global.gainSkills(self);
        self.setTrophy(Item.MaraTrophy);
        self.body.add(new FacePart(.1, 1.1));
        self.body.add(AssPart.generateGeneric().upgrade());
        self.initialGender = CharacterSex.female;
    }

    @Override
    public void setGrowth() {
        character.getGrowth().stamina = 2;
        character.getGrowth().arousal = 7;
        character.getGrowth().bonusStamina = 1;
        character.getGrowth().bonusArousal = 2;

        this.addFirstFocusScene();     
       
        this.addSecondFocusScene();     
     
        preferredAttributes.add(c -> c.getRank() >= 4 && c.get(Attribute.Temporal) < 20
                        ? Optional.of(Attribute.Temporal) : Optional.empty());
        preferredAttributes.add(c -> c.get(Attribute.Science) < 15 ? Optional.of(Attribute.Science) : Optional.empty());
        preferredAttributes.add(c -> c.get(Attribute.Science) >= 15 && c.get(Attribute.Fetish) < 50
                        ? Optional.of(Attribute.Fetish) : Optional.empty());
        preferredAttributes.add(
                        c -> c.get(Attribute.Cunning) < 100 ? Optional.of(Attribute.Cunning) : Optional.empty());
        character.getGrowth().addTrait(0, Trait.petite);
        character.getGrowth().addTrait(0, Trait.dexterous);
        character.getGrowth().addTrait(0, Trait.ticklish);
        character.getGrowth().addTrait(3, Trait.cautious);
        character.getGrowth().addTrait(6, Trait.freeSpirit);
        character.getGrowth().addTrait(9, Trait.limbTraining1);
        character.getGrowth().addTrait(12, Trait.dickhandler);
        character.getGrowth().addTrait(15, Trait.sexTraining1);
        character.getGrowth().addTrait(18, Trait.pussyhandler);
        character.getGrowth().addTrait(21, Trait.tongueTraining1);
        character.getGrowth().addTrait(24, Trait.limbTraining2);
        character.getGrowth().addTrait(27, Trait.tight);
        character.getGrowth().addTrait(30, Trait.limbTraining3);
        character.getGrowth().addTrait(33, Trait.defthands);
        character.getGrowth().addTrait(36, Trait.toymaster);
        character.getGrowth().addTrait(39, Trait.calm);
        character.getGrowth().addTrait(42, Trait.nimbletoes);
        character.getGrowth().addTrait(45, Trait.dickhandler);
        character.getGrowth().addTrait(48, Trait.skeptical);
        character.getGrowth().addTrait(51, Trait.desensitized2);
    }

    private void useGeneralTech() {
        Global.flag(MARA_GENERAL_TECH_FOCUS);
        character.getGrowth().addTrait(10, Trait.bomber);
        character.getGrowth().addTrait(19, Trait.maglocks);
        character.getGrowth().addTrait(26, Trait.trainingcollar);
        character.getGrowth().addTrait(37, Trait.roboweb);
    }

    private void useHarpoon() {
        Global.flag(MARA_HARPOON_FOCUS);
        character.getGrowth().addTrait(10, Trait.harpoon);
        character.getGrowth().addTrait(19, Trait.yank);
        character.getGrowth().addTrait(26, Trait.conducivetoy);
        character.getGrowth().addTrait(37, Trait.intensesuction);
    }

    private void useOcto() {
        Global.flag(MARA_OCTO_FOCUS);
        character.getGrowth().addTrait(20, Trait.octo); // upgrades itself (in RoboArmManager)
    }

    private void useMindControl() {
        Global.flag(MARA_MIND_CONTROL_FOCUS);
        character.getGrowth().addTrait(20, Trait.magiceyeentrhall);
        character.getGrowth().addTrait(29, Trait.infrasound);
        
        //IF she DOESN'T already have one, add one? or just keep adding them every time she uses it?
        if (character.has(Clothing.getByID("infrasoundnecklace")) == false) {
            
        } else {
            
        }
        character.getGrowth().addClothing(29, Clothing.getByID("infrasoundnecklace"));      //FIXME: Mara keeps getting infrasound necklaces?
        character.getGrowth().addTrait(40, Trait.ControlledRelease);
        if (Global.checkFlag(MARA_GENERAL_TECH_FOCUS)) {
            character.getGrowth().addTrait(53, Trait.RemoteControl);
        } else {
            character.getGrowth().addTrait(53, Trait.EyeOpener);
        }
    }

    @Override
    protected void onLevelUp(Character self) {
        if (self.rank >= 4) {

        }
    }

    @Override
    public void rest(int time) {
        if (character.rank == 1 && !character.has(Trait.madscientist)) {
            advance();
        }
        super.rest(time);
        if (!(character.has(Item.Onahole) || character.has(Item.Onahole2)) && character.money >= 300) {
            character.gain(Item.Onahole);
            character.money -= 300;
        }
        if (!(character.has(Item.Tickler) || character.has(Item.Tickler)) && character.money >= 300) {
            character.gain(Item.Tickler);
            character.money -= 300;
        }
        if (!(character.has(Item.Dildo) || character.has(Item.Dildo)) && character.money >= 250) {
            character.gain(Item.Dildo);
            character.money -= 250;
        }
        if (!(character.has(Item.Crop) || character.has(Item.Crop)) && character.money >= 200) {
            character.gain(Item.Crop);
            character.money -= 200;
        }
        if (!(character.has(Item.Strapon) || character.has(Item.Strapon)) && character.money >= 600) {
            character.gain(Item.Strapon);
            character.money -= 600;
        }
        if (character.money > 0 && character.rank >= 1) {
            Global.getDay()
                  .visit("Body Shop", character, Global.random(character.money));
        }
        if (character.money > 0 && character.rank >= 1) {
            Global.getDay()
                  .visit("Workshop", character, Global.random(character.money));
        }
        if (character.money > 0) {
            Global.getDay()
                  .visit("Hardware Store", character, Global.random(character.money));
        }
        if (character.money > 0) {
            Global.getDay()
                  .visit("Black Market", character, Global.random(character.money));
        }
        if (character.money > 0) {
            Global.getDay()
                  .visit("XXX Store", character, Global.random(character.money));
        }
        if (character.money > 0) {
            Global.getDay()
                  .visit("Bookstore", character, Global.random(character.money));
        }
        Decider.visit(character);
        int r;

        for (int i = 0; i < time; i++) {
            r = Global.random(8);
            if (r == 1) {
                Global.getDay()
                      .visit("Exercise", this.character, 0);
            } else if (r == 0) {
                Global.getDay()
                      .visit("Browse Porn Sites", this.character, 0);
            }
        }
    }

    private void advance() {
        character.getGrowth().addTrait(10, Trait.madscientist);
        if (character.hasPussy()) {
            character.body.addReplace(character.body.getRandomPussy().applyMod(CyberneticMod.INSTANCE), 1);
        }
        if (character.hasDick()) {
            character.body.addReplace(character.body.getRandomCock()
                                                    .applyMod(CockMod.bionic),
                            1);
        }
        character.unequipAllClothing();
        character.outfitPlan.add(Clothing.getByID("bra"));
        character.outfitPlan.add(Clothing.getByID("shirt"));
        character.outfitPlan.add(Clothing.getByID("labcoat"));
        character.outfitPlan.add(Clothing.getByID("underwear"));
        character.outfitPlan.add(Clothing.getByID("pants"));
        character.outfitPlan.add(Clothing.getByID("pantyhose"));
        character.outfitPlan.add(Clothing.getByID("boots"));
        character.modAttributeDontSaveData(Attribute.Science, 1);
        character.getGrowth()
                 .addOrRemoveTraits(character, true);
    }

    @Override
    public String victory(Combat c, Result flag) {
        Character target = c.getOpponent(character);
        character.arousal.empty();
        if (c.getStance()
             .anallyPenetrated(c, target)) {
            return "The sensations coming from your prostate are too much as your arms give out below you. Mara doesn't let up either, grinding the head of the strap on over your "
                            + "prostate. <i>\"I've read that the prostate is the male equivalent of a g-spot,\"</i> she pants as she continues her assault on your ass. <i>\"I'd like to see if I can "
                            + "make you come without stimulating your penis.\"</i> she continues. You don't really listen as your brain is about to short circuit and your dick is about to give "
                            + "up the ghost. One final thrust from Mara sends you over the edge and you shudder as you come hard. <i>\"Looks like men really can come from prostate stimulation alone.\"</i> "
                            + "Mara states, apparently more interested in the biology of the act than anything else. Mara turns you over revealing that your dick is still hard despite the "
                            + "mind numbing orgasm you just went through. <i>\"Once again you never disappoint.\"</i> she smiles down to you as she slowly lowers herself onto you. <i>\"Hope you "
                            + "know how to give as well as take.\"</i> She jokes as she begins to work up a steady rhythm. Not to be outdone you quickly shift yourself and begin to thrust into her "
                            + "earnestly, intent on showing just how well you can 'give'. Soon Mara is collapsed on top of you breathing hard and her eyes unfocused. You stand and take your leave. You may have lost the battle but the war is far from over.";
        }
        if (character.has(Trait.madscientist) && c.getStance().vaginallyPenetrated(c,target)) {
            if (target instanceof Player) Global.getPlayer().addict(c, AddictionType.MIND_CONTROL, character, Addiction.HIGH_INCREASE);
            return "You raise your hands and try to cover your eyes. At this point you know you've lost- you're seconds from orgams and Mara is still pounding away wantonly "
                            + "at your cunt- and you just want to evade Mara's hypnotic eyes. Mara responds by grabbing your hips and thrusting her bionic cock as deep as it "
                            + "can go- and then you feel a thin tube brushing past your cervix. As aphrodesiac sprays from the tube into your depths, your entire body convulses "
                            + "with a massive orgasm, and your distraction lets Mara easily pull your hands apart and you feel everything else falling away as you sink into Mara's "
                            + "hypnotic eyes.<p> An unknowable length of time later, you lie on the ground gasping for breath, still staring at Mara with her cock still deep in your "
                            + "pussy. She smirks. \"You're so lucky I can't just squirt out that stuff constantly and turn off the nerves in my cock.\" Mara snorts, slowly beginning"
                            + " to rock her dick in and out of your abused pussy as you whimer from the stimulation.<p> \"I'm kind of disturbed that the benefactor sent me a "
                            + "massive set of rules literally minutes after I finished it, but I have to admit they're not keeping me from winning- though that may have less to do "
                            + "with them being balanced and more to do with you being a massive slut.\" She grins and leans down to whisper in your ear \"'Cause you are a massive "
                            + "slut. Tell me what a massive slut you are.\" At this point, she has control of so much of your mind that you find yourself immediately babbling out "
                            + "declarations of your sluttiness and and how much you want to be held down and fucked. Mara shivers in obvious ecstacy, pausing in her penetration. "
                            + "\"You know what? Beg for my dick. You want it, you feel so achingly empty inside without it, it makes you come instantly and constantly, like you "
                            + "were custom-designed for me to fuck.\"<p> Immediately you feel a yawning chams in your depths that you <i>know</i> only Mara's cock can fill. You "
                            + "weakly try to grind your hips against Mara as supplications and implausible promises pour from your lips. With a smile that seems 20% adoring pet "
                            + "owner, 30% dominatrix, and 50% exultant mad scientist, Mara resumes pounding at your pussy and your first orgam comes within a second, and feels "
                            + "like it will never stop. Your last thought falls apart half-formed as the patterns on Mara's irises push everything else out of your brain.";
        }
        if (character.has(Trait.madscientist) && character.has(Item.Lubricant)) {
            target.add(c, new Oiled(target));
            return "You've fallen completely into Mara's hands now. Her nimble fingers dance over your dick and balls, playing you like an instrument. You grit your teeth and "
                            + "try to endure her touch until you can finger her to orgasm. It's a lost cause though, and you groan as you inevitably feel your pleasure building to a peak. Just before "
                            + "you hit the point of no return, her wonderful fingers release you. Mara grins impishly as your dick twitches in frustration at being left on edge. As soon as you've "
                            + "let your guard down, she kisses you forcefully and pumps your cock rapidly. Your orgasm rocks you as she milks as much of your cum as she can get.<br/><br/>You slump to the "
                            + "floor, spent, but Mara isn't finished with you. She pulls out a bottle of lubricant and starts to grease you up. She takes her time with it, teasing and tickling you "
                            + "as she goes, stopping from time to time to place light kisses. Between her enticing behavior and her naked body pressed against you, your erection recovers in record "
                            + "time.<br/><br/>Mara makes herself comfortable sitting on your lap and slides your lubed up dick between her thighs. As she leans against your chest, you can feel her hot slit "
                            + "pressing against your member. Her finger teases the head of your penis, which is poking out of her lap. <i>\"It's a good thing you're such a horny "
                            + c.getOpponent(character)
                               .boyOrGirl()
                            + ". If you couldn't get it "
                            + "up again, I would have to settle for grinding on your leg.\"</i> As she says this, she starts to rub her clit along the length of your penis. She keeps her legs clamped "
                            + "tightly together so that her movements stimulate your entire shaft. You contribute by licking and sucking the side of her neck to draw out soft moans of pleasure. You "
                            + "support your upper body with your left arm, which leaves your right hand free to play with Mara's small breasts and nipples. Her grinding becomes more needy as she "
                            + "quickly approaches her climax and you can feel that your ejaculation is not far off. You recently come once however, and you're still not ready to cum when Mara starts "
                            + "trembling and gasping in orgasm. You kiss her cheek and embrace her with your free arm until you feel her slump limply against you.<br/><br/>Mara turns her head to meet your "
                            + "eyes and smiles gently. <i>\"Sorry, I came first. But you came alone earlier, so maybe we're even.\"</i> You groan softly as her legs squeeze your painfully hard dick. Her "
                            + "smile turns a bit more playful. <i>\"Are you worried I'm going to leave you with blue balls? I wouldn't do something that cruel.\"</i> She bats her eyes at you cutely and "
                            + "wets her lips with her tongue. <i>\"Kiss me?\"</i> You press your lips against hers and she immediately starts rubbing your lubricated glans with her palm. Your hips buck "
                            + "involuntarily, but she manages to maintain both the kiss and her grip on your cock. The intense stimulation blows away your endurance and your head goes blank as you "
                            + "cover her hands with your seed. Mara breaks the kiss and leaves you completely exhausted.";
        }
        if (c.getStance()
             .vaginallyPenetrated(c, character)) {
            if (character.has(Item.ShockGlove) && Global.random(2) == 0) {
                return "You've got Mara just where you want her. Your arms are wrapped around her, holding her in place as you thrust your cock into her tight pussy over and over. Her moans are getting louder and louder, and you can feel her breath "
                                + "quickening. You're getting close to cumming, but she's definitely closer. She returns your embrace, squeezing her body against yours, stroking your back with her hands. Her hands creep down to grasp your buttocks. "
                                + "All of a sudden, she grins deviously, and she whispers...<br/><br/>"
                                + "<i>\"Time for an experiment. Surprise!\"</i><br/>"
                                + "Suddenly, you feel a poking sensation in your ass. You feel the pressure of her fingers touching your prostate. Wait... is that the hand that she's wearing her shock glove on...?<br/><br/>"
                                + "<b>BZZT!</b> A sharp jolt of pain tears through you as Mara forces electricity through her shock glove and into your ass. You thrash around desperately, but somehow the lithe girl is "
                                + "able to keep her finger pressed against your prostate. You feel an intense pressure welling in your abdomen.<br/><br/>"
                                + "Your orgasm hits you like a brick wall. The pain in your rear gives way to pleasure as the pressure in your abdomen releases. Your cock twitches over and over, and you can feel your seed filling up Mara's insides.<br/><br/>"
                                + "When your orgasm finally subsides, Mara stands. Thick globs of white cum drip out of her. <i>\"Wow, you came a LOT!\"</i> she remarks happily. <i>\"Just like my research indicated.\"</i> She reaches for her soaked flower. "
                                + "<i>\"Now, I can't go into my next fight this horny. You just hold that sexy, defeated pose. I'll handle myself.\"</i> Exhausted, you can do little more than lie there as Mara masturbates over you.<br/><br/>"
                                + "After a few moments of pleasuring herself, Mara suddenly has a revelation. She spreads her pussy lips open and brings her dangerous, gloved hand near her exposed clit. She takes a deep breath to bolster "
                                + "her courage and giggles nervously. <i>\"This is probably either the best or worst idea I've even had. It looked like it felt great on your prostate... What's good for the goose, right..?\"</i> Before you can respond, "
                                + "she touches an electrified finger to her sensitive love bud. Her whole body goes rigid and she lets out a scream of... probably pleasure?... as she shudders in orgasm.<br/><br/>"
                                + "Finally, it's over. The reckless minx collapses next to you, panting. She rolls over and rests her head on your shoulder, then says, <i>\"That felt scary good, but I bet I don't need to tell you that... "
                                + "I'm just worried I might get addicted to that kind of stimulation.\"</i> "
                                + "She pecks your lips with a light kiss, then stands. <i>\"Come visit me every once in a while, okay? I'm working on some new tools that need... testing.\"</i>";
            } else {
                return "Mara's pussy is so tight and wet. She skillfully rides your dick, overwhelming you with pleasure. <i>\"Are you going to cum before me?\"</i> She's panting with pleasure, "
                                + "but still sounds confident. <i>\"Go ahead and fill me up. I don't mind.\"</i> Her permission is irrelevant. She's making you cum and there's nothing you can do about it. "
                                + "You throw your head back and moan as you shoot your load into her tight womb.<br/><br/>"
                                + "Mara slides off your cock as your seed slowly leaks out of her. <i>\"Was I too good "
                                + "for you to hold back? You made quite a mess down here.\"</i> She stirs her entrance with her finger, making a wet sound. She still looks pretty horny and you recall that "
                                + "she hasn't climaxed yet. She smiles and gives you a quick kiss. <i>\"Don't worry, I'll give myself a hand.\"</i> She inserts a second finger, using your ejaculate as a "
                                + "lubricant. <i>\"Playing with semen would probably be a little gross to you, right? It's actually turning me on.\"</i> She lets out a quiet moan and gives you a needy "
                                + "look. <i>\"Just don't leave, ok? I'd feel lonely masturbating on my own.\"</i> You hug Mara's petite body, feeling her tremble while she continues to play with herself. "
                                + "You kiss her neck and stroke her body, which seems to heighten her pleasure. You judge she is on the verge of orgasm and kiss her passionately, while playing with "
                                + "her nipples. She moans against your mouth and shudders in your arms as she climaxes.";
            }
        } else {
            target.arousal.set(target.arousal.max() * 2 / 3);
            return "You're completely at Mara's mercy, but she refuses to finish you off. She teases and caresses you, keeping you too on-edge to fight back, but avoids your "
                            + "painfully hard cock. She brings her face close to yours with a cat-like grin and whispers, <i>\"You look so desperate. Tell me you surrender and I'll "
                            + "let you cum.\"</i> You've already lost. There's no point resisting. You practically beg Mara to finish you, causing her amusement to increase tenfold. "
                            + "She straddles your waist and rubs her wet pussy back and forth along the length of you dick, quickly bringing you to an explosive and messy climax. <br/><br/>She wets her fingers with your spunk "
                            + "and plays with her pussy and clit in front of you. Dominating and teasing you obviously got her worked up. As she begins to moan and shiver on top of you, her free "
                            + "hand teases and toys with your spent dick. By the time Mara reaches her own orgasm, she's worked you into a fresh erection. She plants a light kiss on the tip of your "
                            + "dick and leaves you naked and frustrated.";
        }
    }

    @Override
    public String defeat(Combat c, Result flag) {
        Character other = c.getOpponent(character);
        if (character.has(Trait.madscientist) && character.has(Item.SPotion)) {
            character.add(c, new Hypersensitive(character));
            return "Mara begins to panic as she realizes she's on the verge of defeat. She grabs a small bottle of liquid from pouch on her belt, but it slips from her fingers "
                            + "as she shudders in orgasm. You finger her pussy until she goes limp. While you're waiting for her to recover, you take a look at the bottle she dropped. "
                            + "You recognize this stuff, it greatly heightens sensitivity when absorbed into skin. It seems like a shame not to use it.<br/><br/>Mara sits up, looking at you "
                            + "nervously, but doesn't protest or resist as you open up the bottle and dab a small amount on her nipples. She bites her lip to keep from moaning, but can't "
                            + "supress her shivers when you start to play with her breasts. You capture her lips and focus on her hypersensitive nipples until you feel her trembling in "
                            + "orgasm again. This seems pretty effective, but you're just getting started.<br/><br/>While Mara is still shaking in pleasure, you take the opportunity to coat "
                            + "your fingers in the liquid. As she comes to her senses, you slips those fingers into her pussy and rub the potion into her vaginal walls. She writhes against "
                            + "your hand and moans loudly, no longer able to control her voice. You continue playing with her pussy and lick her hard nipples. As she starts building to another "
                            + "climax, you let up momentarily and use your fingers to peel the hood off her love button. <i>\"Wait! Are you going to put that stuff on my-KYA!\"</i> Her hips jump as "
                            + "you rub the liquid directly into her little bud. Her love juice floods out and you realize she's cumming again.<br/><br/>You're worried that Mara's love juice will wash "
                            + "away the potion, so you pour more onto her pussy lips. She clutches at you desperately even as her orgasm dies down. <i>\"You sexy, wonderful, evil, sexy man! I've "
                            + "never needed a penis in me this much! Pleasepleasepleasepleaseplease! Just do me!\"</i> You have been toying with her for awhile. Even if you wanted to be be more "
                            + "sadistic, you can't deny your own needs. You thrust your dick into her and she screams in pleasure. She may be climaxing again, but you're not going to slow down "
                            + "to check. Her pussy feels amazing! Some of the sensitivity potion is probably soaking into your cock, making it feel better than usual. Between that and the length "
                            + "of time you've been playing with her without relief, it doesn't take long for you to shoot your load into her. Her pussy spasms and this time there's no question "
                            + "she's cumming. You lean in and kiss her gently as she catches her breath.<br/><br/>After you've both recovered enough to get back to your feet, Mara punches you weakly "
                            + "in the chest. <i>\"You jerk! Do you have any idea how long this stuff lasts? How am I suppose to win my next fight when I'm this sensitive?\"</i> She pulls your head down "
                            + "to her height and kisses you passionately before storming off.";
        } else if (c.getStance()
                    .vaginallyPenetrated(c, character)) {
            return "You bury yourself deep into Mara's tight pussy as she screams in pleasure. Her hot folds shudder and squeeze your cock, confirming she's reached her climax. "
                            + "The sensation is amazing, but you're not in danger of cumming with her. You gently stroke her head while spasms of pleasure continue to run through her small body. "
                            + "It occurs to you -not for the first time- that she's really cute, even when she's not trying to be.<br/><br/>As Mara catches her breath, you see realization slowly dawn "
                            + "on her. <i>\"You didn't cum? Why not?\"</i> She actually looks a little hurt. <i>\"Every "
                            + c.getOpponent(character)
                               .boyOrGirl()
                            + " I've been with said it feels really good and tight inside me. They never outlast "
                            + "me.\"</i> Every " + c.getOpponent(character)
                                                    .boyOrGirl()
                            + " she's been with? Mara struck you as a bit of an introvert. How many "
                            + c.getOpponent(character)
                               .guyOrGirl()
                            + "s has she been with? <br/>She gives you a flick on the forehead. <i>\"Don't be mean. "
                            + "I've only slept with a few " + c.getOpponent(character)
                                                               .boyOrGirl()
                            + "s. It's not like you're a virgin either.\"</i> Fair enough, but if she's upset that you didn't cum inside her, you're eager to remedy that. "
                            + "\n\nYou pull most of the way out in preparation for a big thrust, but Mara yelps in alarm. <i>\"Wait!\"</i> She slides her butt backward, causing your dick to fall out completely, "
                            + "and curls up protectively. <i>\"I get really sensitive down there after I orgasm. Give me a minute or two to recover before we continue. In the meantime we can always chat, "
                            + "or maybe kiss?\"</i> You'd feel pretty silly trying to have a conversation while kneeling over her with a painfully hard boner. You lean down and press your lips against "
                            + "hers and she makes a soft noise of approval. <br/><br/>After several minutes of kissing and caressing her tiny breasts, Mara looks euphoric, but it's certainly done nothing to "
                            + "quench your desire. Hasn't she recovered yet? <i>\"Maybe some more of this first...\"</i> She whispers happily. You've been on edge so long that pre-cum is dripping from your "
                            + "cock. You're pretty sure she's teasing you or worse, stalling until someone else interrupts and finishes you off. Mara giggles and prods your penis with her feet. "
                            + "<i>\"If you really can't endure any more, I'm sure I can find a few ways to get you off, but think about how much happier we'll both be if you wait until you can cum "
                            + "inside.\"</i> <br/><br/>She's right about everything except the waiting. You kiss her fiercely, which catches her off guard long enough for you to easily thrust into her soaked "
                            + "entrance. She coos softly against your lips. She's clearly not in any discomfort from the sudden penetration. Right now you're too horny to be annoyed at her deception. "
                            + "Her pussy is tight, wet, and feels heavenly. You pound your hips against hers for your mutual pleasure. Mara lets out breathy moans in time to your thrusts and when "
                            + "you finally reach your sweet release, she tenses in orgasm under you.<br/><br/>She softly strokes your cheek as you both relish the afterglow. <i>\"You were just like a wild "
                            + "animal there,\"</i> She whispers with a grin. <i>\"You never would have been that intense if I hadn't made you wait.\"</i>";
        } else {
            String message = "You've managed to reduce the ever arrogant and composed Mara to a puddle of whimpering pleasure. You caress and finger her to a shuddering climax. You don't "
                            + "let up, prolonging her orgasm as long as possible. <i>\"Wait! wait! I already came! I'm really sensitive right now!\"</i> You continue toying with her twitching pussy "
                            + "while she moans and pleads for mercy, but you are enjoying this rare opportunity to have the scheming minx at your mercy. Soon she arches her back, hitting her "
                            + "second peak, and you finally let her catch her breath.<br/><br/>After Mara recovers enough to talk, she tells you to lie back and she goes to work";
            if (other.hasDick()) {
                message += " pleasuring your straining erection. You've given her two orgasms with no relief yourself, so it only takes a bit of stroking and sucking to bring you to the brink. Just as "
                                + "you feel your ejaculation building, she grips your shaft tightly enough to deny your release. She gives you her most impish smile as you look at her questioningly. "
                                + "<i>\"I never said I was going to get you off, but feel free to continue on your own.\"</i> She lets go of you and sits back in front of you smugly. <i>\"Just a bit of petty "
                                + "revenge for taking advantage of me.\"</i><br/><br/>It seems unfair that she's the one setting the terms when you won the fight, but she's not actually obliged to do "
                                + "anything for you. You can't just leave in your current state. If someone else caught you this frustrated and aroused, you wouldn't stand a chance. "
                                + "You grudgingly take matters into your own hands and begin masturbating while Mara smiles in amusement. Soon you're past your limit and you ejaculate "
                                + "onto her face and breasts. She giggles and licks the cum from her lips as you claim her clothes as a trophy and walk away.";
            } else {
                message += " brushing near your throbbing clit. You've given her two orgasms with no relief yourself, so it only takes a bit of teasing to bring you to the brink. Just as "
                                + "you feel your orgasm building, she eases away, denying you your release. She gives you her most impish smile as you look at her questioningly. "
                                + "<i>\"I never said I was going to get you off, but feel free to continue on your own.\"</i> She lets go of you and sits back in front of you smugly. <i>\"Just a bit of petty "
                                + "revenge for taking advantage of me.\"</i><br/><br/>It seems unfair that she's the one setting the terms when you won the fight, but she's not actually obliged to do "
                                + "anything for you. But you can't just leave in your current state. If someone else caught you this frustrated and aroused, you wouldn't stand a chance. "
                                + "You grudgingly take matters into your own hands and bury your hand in your pussy while Mara smiles in amusement. Soon you're past your limit and you shake "
                                + "with pleasure. She giggles as you claim her clothes as a trophy and walk away.";
            }
            return message;
        }
    }

    @Override
    public String draw(Combat c, Result flag) {
        Character target;
        if (c.p1 == character) {
            target = c.p2;
        } else {
            target = c.p1;
        }
        if (flag == Result.intercourse) {
            return "You thrust your dick into Mara's tight pussy while she writhes under you. You tease and pinch her nipples to increase her pleasure, but it's not really necessary. "
                            + "She's obviously already on the brink of climax. Thanks to the pleasurable tightness of her entrance, you're not far behind her, but you should be able to endure "
                            + "if you're careful with the pace. You've barely finished forming the thought when Mara catches you by surprise with an aggressive kiss and rolls on top of you. She "
                            + "rides you passionately, too aroused to prevent her own orgasm, but eager to take you with her. It works. Her already tight pussy squeezes your cock as she orgasms, "
                            + "milking you. Pleasure overwhelms you as you fill her womb with your seed.<br/><br/>Mara absentmindedly rubs her abdomen as you both enjoy the afterglow. <i>\"You came so much "
                            + "inside me. It'll be a wonder if I'm not knocked up. If we have a boy, should we name him after you? "
                            + target.getName() + " jr. has a nice ring to it.\"</i> Pregnant?! You "
                            + "feel a cold panic grip you as you try to imagine balancing college and caring for a baby. Mara, on the other hand lets out a relaxed giggle. <i>\"You don't need to look "
                            + "so nervous. I was just making a joke. Do you think they'd let us do this without birth control?\"</i> That's right. You do vaguely remember that being mentioned when the "
                            + "competition was explained to you.<br/><br/>You relax, but Mara has gone quiet as she stares at your face thoughtfully. <i>\"You might actually make a good father.\"</i> "
                            + "She leans down to give you a quick kiss before hopping to her feet. <i>\"But not yet. There are a lot of things I want to accomplish before I settle down to start a family.\"</i>";
        }
        return "Mara is already trembling in impending orgasm and for a moment it seems like you've beaten her, but in her last second desperation, she redoubles her efforts. She manages to find "
                        + "and stimulate all the most sensitive spots on your dick and gently squeezes your balls, pulling you over the edge with her. The two of you stay locked in "
                        + "an embrace as you shudder through your simultaneous orgasms, your spurting cock making a sticky mess on her stomach. You enjoy your afterglow, holding the "
                        + "lovely, petite girl, who for just a moment is too tired to put up her normal facade. <br/><br/>Soon, however, Mara has recovered. She gives you her usual, mischievous grin "
                        + "and opens her mouth for what will surely be a snarky and insincere quip that ruins this genuine moment. You interrupt her by pressing your mouth against hers in a deep kiss. "
                        + "To your surprise, she practically melts in your arms. Encouraged by her reaction, you run your hands down her body and gently probe her nethers. Despite having "
                        + "just cum, you find a fresh flood of moisture leaking from her pussy. You eventually break the kiss and Mara looks up at you silently, eyes wet with desire. The "
                        + "sight revives your spent erection. You kiss her again and slide your dick into her tight depths. She moans softly against your lips and moves her hips in time with yours. "
                        + "Soon you're thrusting against each other in earnest, no longer competing, but just trying to bring each other pleasure. You cum inside her, setting off her second "
                        + "shivering orgasm. <br/><br/>This time, when you both recover Mara just smiles contently and gives you a peck on the lips. If only she was always this honest and cute. Somewhat "
                        + "regretfully, you collect her clothes and go on your way.";
    }

    @Override
    public boolean fightFlight(Character opponent) {
        return !character.mostlyNude() || opponent.mostlyNude();
    }

    @Override
    public boolean attack(Character opponent) {
        return true;
    }

    public double dickPreference() {
        return 3;
    }

    @Override
    public String victory3p(Combat c, Character target, Character assist) {
        if (target.human()) {
            if (target.hasDick()) {
                return "Mara settles between your legs, holding your dick between her bare feet. Her soft, smooth soles begin to stroke the length of your shaft. She frequently "
                                + "uses her toes to tease your balls and the head of your penis. As you start to leak pre-cum, she smears it all over your dick, using the lubricant to give "
                                + "you a more intense footjob. Mindful of her own needs, she reaches between her own legs and starts rubbing her pussy and clit, giving you a sexy show. "
                                + "For a moment, it crosses your mind that if you can hold out long enough, she may slip up and climax before you. The moment passes, however, when Mara "
                                + "accelerates her stroking and you realize you won't be able to last more than a few more seconds. Sure enough, your jizz soon shoots into the air like "
                                + "a fountain and paints her legs and feet. Mara continues to stimulate your oversensitized dick and balls while she finishes herself off, apparently too "
                                + "caught up in her own enjoyment to notice your discomfort.";
            } else {
                return "Mara runs her fingers down the length of your body, tickling you mercilessly. Her probing fingers avoid your nipples and pussy, "
                                + "focusing instead on the ticklish areas under your arms, behind your knees and on your inner thighs. You squirm against "
                                + assist.getName()
                                + " pleading for mercy. After a few minutes of this, sweet electric shivers wrack your body when Mara's "
                                + "dancing fingers tease your dripping pussy and your clit. You are unable to decide whether you are being tickled into "
                                + "submission or fingered to ecstasy. Your oversensitized pussy finally can't take any more and trys clamping onto her "
                                + "teasing fingers. But she keeps you on the edge until you collapse in exhaustion. You dimly hear Mara talking but you "
                                + "feel too worn out to understand her.";
            }
        }
        if (target.hasDick()) {
            return String.format(
                            "Mara approaches %s like a panther claiming its prey. "
                                            + "She runs her fingers down the length of %s's body, eliciting a shiver "
                                            + "each time she hits a ticklish spot. Her probing fingers avoid %s's "
                                            + "nipples and genitals, focusing instead on the ticklish areas under %s "
                                            + "arms, behind %s knees and on %s inner thighs. You struggle to hold "
                                            + "onto %s as %s squirms and pleads for mercy. After a few minutes, %s "
                                            + "pleas shift in tone and you realise Mara's dancing fingers have "
                                            + "moved to %s dick and balls. %s entire body trembles as if unable to "
                                            + "decide whether it's being tickled into submission or stroked to "
                                            + "ejaculation. You finally hear a breathless gasp as %s hits %s climax"
                                            + " and shudders in your arms. You release %s and %s collapses, completely"
                                            + " exhausted. Mara grins at you mischievously. <i>\"%s obviously enjoyed "
                                            + "that. Do you want to be next?\"</i>",
                            target.getName(), target.getName(), target.getName(), target.possessiveAdjective(),
                            target.possessiveAdjective(), target.possessiveAdjective(), target.getName(), target.pronoun(),
                            target.possessiveAdjective(), target.possessiveAdjective(),
                            Global.capitalizeFirstLetter(target.possessiveAdjective()), target.getName(),
                            target.possessiveAdjective(), target.directObject(), target.pronoun(),
                            Global.capitalizeFirstLetter(target.possessiveAdjective()));
        }
        return "Mara approaches " + target.getName()
                        + " like a panther claiming its prey. She runs her fingers down the length of " + target.getName()
                        + "'s body, eliciting a shiver "
                        + "each time she hits a ticklish spot. Her probing fingers avoid " + target.getName()
                        + "'s nipples and pussy, focusing instead on the ticklish areas under her arms, "
                        + "behind her knees and on her inner thighs. You struggle to hold onto " + target.getName()
                        + " as she squirms and pleads for mercy. After a few minutes, her pleas "
                        + "shift in tone and you realise Mara's dancing fingers have moved to her pussy and clit. Her entire body trembles as if unable to decide whether it's being "
                        + "tickled into submission or fingered to ecstasy. You finally hear a breathless gasp as "
                        + target.getName() + " hits her climax and shudders in your arms. You release "
                        + "her and she collapses, completely exhausted. Mara grins at you mischeviously. <i>\"She obviously enjoyed that. Do you want to be next?\"</i>";
    }

    @Override
    public String intervene3p(Combat c, Character target, Character assist) {
        if (target.human()) {
            return "You face off with " + assist.getName()
                            + ", looking for any opening. Her eyes dart momentarily past you, but before you can decide if her distraction is "
                            + "real or a feint, a small hand reaches between your legs and grabs your nutsack tightly. You can't get a good look at your attacker, clinging to your back, "
                            + "but her small size and mocha skin give away Mara's identity. This information doesn't really help you much, as it's too late to defend yourself."
                            + " She yanks on your jewels, forcing you to your knees. Both girls work to restrain your arms, but it's "
                            + "not really necessary since Mara literally has you by the balls. She releases your abused jewels once the fight has left you and focuses on holding your arms, "
                            + "while " + assist.getName() + " moves to your front.<br/>";
        } else {
            return "So far this hasn't been your proudest fight. " + target.getName()
                            + " was able to pin you early on and is currently rubbing your dick between her thighs. "
                            + "You've almost given up hope of victory, until you spot Mara creeping up behind her. She seems thoroughly amused by your predicament and makes no "
                            + "move to help you, despite being easily in reach. You give her your best puppy-dog eyes, silently pleading while trying not to give away her presence. "
                            + "Mara lets you squirm a little longer before winking at you and tickling " + target.getName()
                            + " under her arms. " + target.getName() + " lets out a startled yelp "
                            + "and jumps in surprise. You use the moment of distraction to push her off balance and Mara immediately secures her arms.<br/>";
        }
    }

    @Override
    public boolean fit() {
        return character.getStamina()
                        .percent() >= 75
                        && character.getArousal()
                                    .percent() <= 10
                        && !character.mostlyNude();
    }

    @Override
    public boolean checkMood(Combat c, Emotion mood, int value) {
        switch (mood) {
            case confident:
                return value >= 50;
            case desperate:
                return value >= 75;
            default:
                return value >= 100;
        }
    }
    
    /**Helper method to Add this character's first Combat focus scene 
     * MARA: General or Harpoon
     * 
     * */
    private void addFirstFocusScene(){
        character.addCombatScene(new CombatScene(
                        (c, self, other) -> self.getLevel() >= 10 && !Global.checkFlag(MARA_GENERAL_TECH_FOCUS)
                                        && !Global.checkFlag(MARA_HARPOON_FOCUS),
                        (c, self, other) -> Global.format(
                                        "You see {self:name} fiddling with the device on her arm"
                                                        + " as you both recover from your match. <i>\"Damn thing is on the fritz"
                                                        + " again... Let me just...\"</i> A shrill beep sounds, and something"
                                                        + " whirrs to life inside. <i>\"There we go. I really should work on the"
                                                        + " reliability. That will leave me with plenty of time to add new goodies."
                                                        + " Mmm... What do you think, {other:name}? Should I invest resources into"
                                                        + " one really fancy toy, or should I divide my time over several simpeler ones?",
                                        self, other),
                        Arrays.asList(new CombatSceneChoice("Quality", (c, self, other) -> {
                            c.write(Global.format(
                                            "You tell {self:direct-object} there would be no point to slavering"
                                                            + " away on mediocre devices. Instead, {self:pronoun} should devote {self:reflective}"
                                                            + " to one, truly inspired invention to show {self:possessive} true prowess."
                                                            + " <i>\"Yes... You're right!\"</i> %s You yelp and stumble backward, asking {self:possessive}"
                                                            + " what she's doing. <i>\"I'm going to create the best sex toy this world will"
                                                            + " ever see, of course! And since you convinced me to do so, it's only fair"
                                                            + " that I get it tailored to suit you </i>just<i> right. Trust me, {other:name},"
                                                            + " you're going to enjoy this!\"</i>",
                                            self, other,
                                            other.hasDick() ? "{self:PRONOUN} whips out a tape measure from... somewhere..."
                                                            + " and starts measuring up your {other:body-part:cock}."
                                                            : "{self:PRONOUN} reaches down and sticks a finger into"
                                                                            + " your still-bare, still-wet {other:body-part:pussy},"
                                                                            + " wriggling it around a bit inside."));
                            useHarpoon();
                            return true;
                        }), new CombatSceneChoice("Quantity", (c, self, other) -> {
                            c.write(Global.format(
                                            "You weigh in that one toy, no matter how good it is,"
                                                            + " is bound to get boring eventually. Instead, {self:pronoun}"
                                                            + " should create a more diverse arsenal. \"<i>Interesting... I"
                                                            + " do have plenty of ideas to keep things fresh, that's for sure.</i>\""
                                                            + " {self:PRONOUN} grins at you mischievously. <i>\"All of them are"
                                                            + " delightfully naughty. You'll help me beta-test, right? Ah,"
                                                            + " who am I kidding. Like you have any choice in the matter!</i>\"",
                                            self, other));
                            useGeneralTech();
                            return true;
                        }), new CombatSceneChoice("You have faith she can do both [Hard Mode]", (c, self, other) -> {
                            c.write(Global.format(
                                            "{self:name} is a genius. Surely, if anyone"
                                                            + " could create a true masterpiece and still have time"
                                                            + " left for side projects, it would be {self:possessive}. <i>\"Aww, flatterer!"
                                                            + " If you hadn't already gotten in my pants just now, I'd think"
                                                            + " you were trying really hard to get there. I'll just have "
                                                            + "to work extra hard to live up to your expectations, then. It's"
                                                            + " unhealthy to work on one thing all the time anyway. Just make"
                                                            + " sure you do your part, {other:name}. I will expect a good, driven"
                                                            + " test subject for my creations, and you just volunteered yourself.\"</i>"
                                                            + " As she turns and walks away, mumbling ideas to herself, you say that"
                                                            + " you did no such thing. Your words fall on deaf ears, though. Have"
                                                            + " you perhaps shot yourself in the foot there?",
                                            self, other));
                            useGeneralTech();
                            useHarpoon();
                            character.getGrowth().extraAttributes += 1;
                            Global.getPlayer()
                                  .getGrowth().addTraitPoints(new int[] {12, 39}, Global.getPlayer());
                            return true;
                        }))));

    }
    
    /**Helper method to Add this character's second Combat focus scene 
     * MARA: Octo or Mind Control. 
     * 
     * */
    private void addSecondFocusScene(){
        character.addCombatScene(new CombatScene(
                        (c, self, other) -> self.getLevel() >= 20 && !Global.checkFlag(MARA_OCTO_FOCUS)
                                        && !Global.checkFlag(MARA_MIND_CONTROL_FOCUS),
                        (c, self, other) -> Global.format(
                                        "You and {self:name} are both getting back up after your match,"
                                                        + " cleaning yourselves as much as possible before moving on. <i>\"Hey, {other:name}."
                                                        + " Perhaps you can help settle a little dispute I've been having with a friend."
                                                        + " Who's better: Mesmero, or Doctor Octopus?\"</i>",
                                        self, other),
                        Arrays.asList(new CombatSceneChoice("Mesmero", (c, self, other) -> {
                            c.write(Global.format(
                                            "You don't know why, but the idea of mind control has always"
                                                            + " turned you on. You answer {self:name} confidently, and are met"
                                                            + " with a wide grin. <i>\"Ohhh, just what I thought. You want someone"
                                                            + " pulling your strings, don't you. Among other parts of your anatomy,"
                                                            + " of course... Well, I'll make sure to be ready to oblige next time we"
                                                            + " meet. Maybe I'll even let you remember the occasion! Then again, I'm"
                                                            + " sure your surprised look will be something I want to see more than once..."
                                                            + " Decisions, decisions.\"</i> What... exactly did you just sign up for?",
                                            self, other));
                            useMindControl();
                            return true;
                        }), new CombatSceneChoice("Doctor Octopus", (c, self, other) -> {
                            c.write(Global.format(
                                            "Oh, that's no contest! The good doctor could probably engineer"
                                                            + " one of his arms to block mind control, and then still have more"
                                                            + " left to deal with his enemies. <i>\"Interesting. Yes, I can see"
                                                            + " your point. Extra arms to hold you in place. Extra arms to"
                                                            + " remove your clothes. Extra arms with sex toys mounted at the end."
                                                            + " The possibilities are limitless! You've been a good test subject"
                                                            + " so far, {other:name}, so I'll let you test all my new arm designs"
                                                            + " as well! See you soon!</i>\" Well, shit. As if the two hands she"
                                                            + " has right now weren't dangerous enough...",
                                            self, other));
                            useOcto();
                            return true;
                        }), new CombatSceneChoice("What are these strange things you speak of? [Hard Mode]",
                                        (c, self, other) -> {
                                            c.write(Global.format(
                                                            "<i>\"{other:name}. Seriously. You are going to need a crash course"
                                                                            + " in comic books, my friend. This cannot stand. But since your feeble"
                                                                            + " mind obviously cannot comprehend this most fascinating art form, I'm"
                                                                            + " going to have to give practical demonstrations if I am to have any"
                                                                            + " hope of properly educating you. Maybe once the Games are over, I'll"
                                                                            + " ask Cassie to draw a comic about me. This moment will be forever recorded"
                                                                            + " as the moment when I transformed from {self:name}, the brilliant scientist,"
                                                                            + " into {self:name} the supervillain%s! Bask in my presence and weep!</i>\""
                                                                            + " Uhhh. Ok. That was weird. {self:name} walks away, arms raised over"
                                                                            + " {self:possessive} head and laughing maniacally. This is going to"
                                                                            + " be interesting, that's for sure.",
                                                            self, other, character.useFemalePronouns() ? "ess" : ""));
                                            useMindControl();
                                            useOcto();
                                            character.getGrowth().extraAttributes += 1;
                                            Global.getPlayer()
                                                  .getGrowth().addTraitPoints(new int[] {21, 48}, Global.getPlayer());
                                            return true;
                                        }))));
    }
    
}
