package nightgames.skills;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import nightgames.characters.Attribute;
import nightgames.characters.Character;
import nightgames.characters.Trait;
import nightgames.combat.Combat;
import nightgames.combat.Result;
import nightgames.global.DebugFlags;
import nightgames.global.Formatter;
import nightgames.global.Global;
import nightgames.global.Random;
import nightgames.nskills.tags.SkillTag;
import nightgames.pet.Ptype;
import nightgames.skills.damage.Staleness;
import nightgames.status.FiredUp;
import nightgames.status.Status;
import nightgames.status.Stsflag;

public abstract class Skill {
    public static Set<Skill> skillPool = new HashSet<>();
    /**
     *
     */
    private String name;
    private Character self;
    private int cooldown;
    private Set<SkillTag> tags;
    public String choice;
    private Staleness staleness;

    public Skill(String name, Character self) {
        this(name, self, 0);
    }
    public Skill(String name, Character self, int cooldown) {
        this(name, self, cooldown, Staleness.build().withDecay(.1).withFloor(.5).withRecovery(.05));
    }

    public Skill(String name, Character self, int cooldown, Staleness staleness) {
        this.name = name;
        setSelf(self);
        this.cooldown = cooldown;
        this.staleness = staleness;
        choice = "";
        tags = new HashSet<>();
    }

    public static void buildSkillPool(Character ch) {
        getSkillPool().clear();
        getSkillPool().add(new Slap(ch));
        getSkillPool().add(new Tribadism(ch));
        getSkillPool().add(new PussyGrind(ch));
        getSkillPool().add(new Slap(ch));
        getSkillPool().add(new ArmBar(ch));
        getSkillPool().add(new Blowjob(ch));
        getSkillPool().add(new Cunnilingus(ch));
        getSkillPool().add(new Escape(ch));
        getSkillPool().add(new Flick(ch));
        getSkillPool().add(new ToggleKnot(ch));
        getSkillPool().add(new LivingClothing(ch));
        getSkillPool().add(new LivingClothingOther(ch));
        getSkillPool().add(new Engulf(ch));
        getSkillPool().add(new CounterFlower(ch));
        getSkillPool().add(new Knee(ch));
        getSkillPool().add(new LegLock(ch));
        getSkillPool().add(new LickNipples(ch));
        getSkillPool().add(new Maneuver(ch));
        getSkillPool().add(new Paizuri(ch));
        getSkillPool().add(new PerfectTouch(ch));
        getSkillPool().add(new Restrain(ch));
        getSkillPool().add(new Reversal(ch));
        getSkillPool().add(new LeechEnergy(ch));
        getSkillPool().add(new SweetScent(ch));
        getSkillPool().add(new Spank(ch));
        getSkillPool().add(new Stomp(ch));
        getSkillPool().add(new StandUp(ch));
        getSkillPool().add(new WildThrust(ch));
        getSkillPool().add(new SuckNeck(ch));
        getSkillPool().add(new Tackle(ch));
        getSkillPool().add(new Taunt(ch));
        getSkillPool().add(new Trip(ch));
        getSkillPool().add(new Whisper(ch));
        getSkillPool().add(new Kick(ch));
        getSkillPool().add(new PinAndBlow(ch));
        getSkillPool().add(new PinningPaizuri(ch));
        getSkillPool().add(new Footjob(ch));
        getSkillPool().add(new FootPump(ch));
        getSkillPool().add(new HeelGrind(ch));
        getSkillPool().add(new Handjob(ch));
        getSkillPool().add(new Squeeze(ch));
        getSkillPool().add(new Nurple(ch));
        getSkillPool().add(new Finger(ch));
        getSkillPool().add(new Aphrodisiac(ch));
        getSkillPool().add(new Lubricate(ch));
        getSkillPool().add(new Dissolve(ch));
        getSkillPool().add(new Sedate(ch));
        getSkillPool().add(new Tie(ch));
        getSkillPool().add(new Masturbate(ch));
        getSkillPool().add(new Piston(ch));
        getSkillPool().add(new Grind(ch));
        getSkillPool().add(new Thrust(ch));
        getSkillPool().add(new UseDildo(ch));
        getSkillPool().add(new UseOnahole(ch));
        getSkillPool().add(new UseCrop(ch));
        getSkillPool().add(new Carry(ch));
        getSkillPool().add(new Tighten(ch));
        getSkillPool().add(new ViceGrip(ch));
        getSkillPool().add(new HipThrow(ch));
        getSkillPool().add(new SpiralThrust(ch));
        getSkillPool().add(new Bravado(ch));
        getSkillPool().add(new Diversion(ch));
        getSkillPool().add(new Undress(ch));
        getSkillPool().add(new StripSelf(ch));
        getSkillPool().add(new StripTease(ch));
        getSkillPool().add(new Sensitize(ch));
        getSkillPool().add(new EnergyDrink(ch));
        getSkillPool().add(new Strapon(ch));
        getSkillPool().add(new AssFuck(ch));
        getSkillPool().add(new Turnover(ch));
        getSkillPool().add(new Tear(ch));
        getSkillPool().add(new Binding(ch));
        getSkillPool().add(new Bondage(ch));
        getSkillPool().add(new WaterForm(ch));
        getSkillPool().add(new DarkTendrils(ch));
        getSkillPool().add(new Dominate(ch));
        getSkillPool().add(new Illusions(ch));
        getSkillPool().add(new Glamour(ch));
        getSkillPool().add(new LustAura(ch));
        getSkillPool().add(new MagicMissile(ch));
        getSkillPool().add(new Masochism(ch));
        getSkillPool().add(new NakedBloom(ch));
        getSkillPool().add(new ShrinkRay(ch));
        getSkillPool().add(new SpawnFaerie(ch, Ptype.fairyfem));
        getSkillPool().add(new SpawnImp(ch, Ptype.impfem));
        getSkillPool().add(new SpawnFaerie(ch, Ptype.fairymale));
        getSkillPool().add(new SpawnImp(ch, Ptype.impmale));
        getSkillPool().add(new SpawnFGoblin(ch, Ptype.fgoblin));
        getSkillPool().add(new SpawnSlime(ch));
        getSkillPool().add(new StunBlast(ch));
        getSkillPool().add(new Fly(ch));
        getSkillPool().add(new Command(ch));
        getSkillPool().add(new Obey(ch));
        getSkillPool().add(new OrgasmSeal(ch));
        getSkillPool().add(new DenyOrgasm(ch));
        getSkillPool().add(new Drain(ch));
        getSkillPool().add(new LevelDrain(ch));
        getSkillPool().add(new StoneForm(ch));
        getSkillPool().add(new FireForm(ch));
        getSkillPool().add(new Defabricator(ch));
        getSkillPool().add(new TentaclePorn(ch));
        getSkillPool().add(new Sacrifice(ch));
        getSkillPool().add(new Frottage(ch));
        getSkillPool().add(new FaceFuck(ch));
        getSkillPool().add(new VibroTease(ch));
        getSkillPool().add(new TailPeg(ch));
        getSkillPool().add(new CommandDismiss(ch));
        getSkillPool().add(new CommandDown(ch));
        getSkillPool().add(new CommandGive(ch));
        getSkillPool().add(new CommandHurt(ch));
        getSkillPool().add(new CommandInsult(ch));
        getSkillPool().add(new CommandMasturbate(ch));
        getSkillPool().add(new CommandOral(ch));
        getSkillPool().add(new CommandStrip(ch));
        getSkillPool().add(new CommandStripPlayer(ch));
        getSkillPool().add(new CommandUse(ch));
        getSkillPool().add(new ShortCircuit(ch));
        getSkillPool().add(new IceForm(ch));
        getSkillPool().add(new Barrier(ch));
        getSkillPool().add(new CatsGrace(ch));
        getSkillPool().add(new Charm(ch));
        getSkillPool().add(new Tempt(ch));
        getSkillPool().add(new EyesOfTemptation(ch));
        getSkillPool().add(new TailJob(ch));
        getSkillPool().add(new FaceSit(ch));
        getSkillPool().add(new Smother(ch));
        getSkillPool().add(new BreastSmother(ch));
        getSkillPool().add(new MutualUndress(ch));
        getSkillPool().add(new Surrender(ch));
        getSkillPool().add(new ReverseFuck(ch));
        getSkillPool().add(new ReverseCarry(ch));
        getSkillPool().add(new ReverseFly(ch));
        getSkillPool().add(new CounterDrain(ch));
        getSkillPool().add(new CounterRide(ch));
        getSkillPool().add(new CounterPin(ch));
        getSkillPool().add(new ReverseAssFuck(ch));
        getSkillPool().add(new Nurse(ch));
        getSkillPool().add(new Suckle(ch));
        getSkillPool().add(new UseDraft(ch));
        getSkillPool().add(new ThrowDraft(ch));
        getSkillPool().add(new ReverseAssFuck(ch));
        getSkillPool().add(new FondleBreasts(ch));
        getSkillPool().add(new Fuck(ch));
        getSkillPool().add(new Kiss(ch));
        getSkillPool().add(new Struggle(ch));
        getSkillPool().add(new Tickle(ch));
        getSkillPool().add(new Wait(ch));
        getSkillPool().add(new Bluff(ch));
        getSkillPool().add(new StripTop(ch));
        getSkillPool().add(new StripBottom(ch));
        getSkillPool().add(new Shove(ch));
        getSkillPool().add(new Recover(ch));
        getSkillPool().add(new Straddle(ch));
        getSkillPool().add(new ReverseStraddle(ch));
        getSkillPool().add(new Stunned(ch));
        getSkillPool().add(new Distracted(ch));
        getSkillPool().add(new PullOut(ch));
        getSkillPool().add(new ThrowDraft(ch));
        getSkillPool().add(new UseDraft(ch));
        getSkillPool().add(new TentacleRape(ch));
        getSkillPool().add(new Anilingus(ch));
        getSkillPool().add(new UseSemen(ch));
        getSkillPool().add(new Invitation(ch));
        getSkillPool().add(new SubmissiveHold(ch));
        getSkillPool().add(new BreastGrowth(ch));
        getSkillPool().add(new CockGrowth(ch));
        getSkillPool().add(new BreastRay(ch));
        getSkillPool().add(new FootSmother(ch));
        getSkillPool().add(new FootWorship(ch));
        getSkillPool().add(new BreastWorship(ch));
        getSkillPool().add(new CockWorship(ch));
        getSkillPool().add(new PussyWorship(ch));
        getSkillPool().add(new SuccubusSurprise(ch));
        getSkillPool().add(new TemptressHandjob(ch));
        getSkillPool().add(new TemptressBlowjob(ch));
        getSkillPool().add(new TemptressTitfuck(ch));
        getSkillPool().add(new TemptressRide(ch));
        getSkillPool().add(new TemptressStripTease(ch));
        getSkillPool().add(new Blindside(ch));
        getSkillPool().add(new LeechSeed(ch));
        getSkillPool().add(new Beg(ch));
        getSkillPool().add(new Cowardice(ch));
        getSkillPool().add(new Dive(ch));
        getSkillPool().add(new Offer(ch));
        getSkillPool().add(new ShamefulDisplay(ch));
        getSkillPool().add(new Stumble(ch));
        getSkillPool().add(new TortoiseWrap(ch));
        getSkillPool().add(new FaerieSwarm(ch));
        getSkillPool().add(new DarkTalisman(ch));
        getSkillPool().add(new HeightenSenses(ch));
        getSkillPool().add(new LewdSuggestion(ch));
        getSkillPool().add(new Suggestion(ch));
        getSkillPool().add(new ImbueFetish(ch));
        getSkillPool().add(new AssJob(ch));
        getSkillPool().add(new TailSuck(ch));
        getSkillPool().add(new ToggleSlimeCock(ch));
        getSkillPool().add(new ToggleSlimePussy(ch));
        getSkillPool().add(new Spores(ch));
        getSkillPool().add(new EngulfedFuck(ch));
        getSkillPool().add(new Pray(ch));
        getSkillPool().add(new Prostrate(ch));
        getSkillPool().add(new DarkKiss(ch));
        getSkillPool().add(new SlimeMimicry(ch));
        getSkillPool().add(new MimicAngel(ch));
        getSkillPool().add(new MimicCat(ch));
        getSkillPool().add(new MimicDryad(ch));
        getSkillPool().add(new MimicSuccubus(ch));
        getSkillPool().add(new MimicWitch(ch));
        getSkillPool().add(new Parasite(ch));
        getSkillPool().add(new Bite(ch));
        getSkillPool().add(new PlaceBlindfold(ch));
        getSkillPool().add(new RipBlindfold(ch));
        getSkillPool().add(new ToggleBlindfold(ch));
        getSkillPool().add(new BunshinAssault(ch));
        getSkillPool().add(new BunshinService(ch));
        getSkillPool().add(new GoodnightKiss(ch));
        getSkillPool().add(new NeedleThrow(ch));
        getSkillPool().add(new StealClothes(ch));
        getSkillPool().add(new Substitute(ch));
        getSkillPool().add(new AttireShift(ch));
        getSkillPool().add(new CheapShot(ch));
        getSkillPool().add(new EmergencyJump(ch));
        getSkillPool().add(new Haste(ch));
        getSkillPool().add(new Rewind(ch));
        getSkillPool().add(new Unstrip(ch));
        getSkillPool().add(new WindUp(ch));
        getSkillPool().add(new ThrowSlime(ch));
        getSkillPool().add(new Edge(ch));
        getSkillPool().add(new SummonYui(ch));
        getSkillPool().add(new Simulacrum(ch));
        getSkillPool().add(new Divide(ch));
        getSkillPool().add(new PetThreesome(ch));
        getSkillPool().add(new ReversePetThreesome(ch));
        getSkillPool().add(new PetInitiatedThreesome(ch));
        getSkillPool().add(new PetInitiatedReverseThreesome(ch));
        getSkillPool().add(new FlyCatcher(ch));
        getSkillPool().add(new Honeypot(ch));
        getSkillPool().add(new TakeOffShoes(ch));
        getSkillPool().add(new LaunchHarpoon(ch));
        getSkillPool().add(new ThrowBomb(ch));
        getSkillPool().add(new RemoveBomb(ch));
        getSkillPool().add(new MagLock(ch));
        getSkillPool().add(new Collar(ch));
        getSkillPool().add(new HypnoVisorPlace(ch));
        getSkillPool().add(new HypnoVisorRemove(ch));
        getSkillPool().add(new StripMinor(ch));
        getSkillPool().add(new DemandArousal(ch));
        getSkillPool().add(new Embrace(ch));
        getSkillPool().add(new SuccubusNurse(ch));
        getSkillPool().add(new WingWrap(ch));
        getSkillPool().add(new ComeHither(ch));
        getSkillPool().add(new KiShout(ch));
        getSkillPool().add(new PressurePoint(ch));
        getSkillPool().add(new Deepen(ch));

        if (Global.isDebugOn(DebugFlags.DEBUG_SKILLS)) {
            getSkillPool().add(new SelfStun(ch));
        }
    }

    public static String gainSkills(Character c) {
        String message = "";
        if (c.getPure(Attribute.Dark) >= 6 && !c.has(Trait.darkpromises)) {
            c.add(Trait.darkpromises);
        } else if (!(c.getPure(Attribute.Dark) >= 6) && c.has(Trait.darkpromises)) {
            c.remove(Trait.darkpromises);
        }
        boolean pheromonesRequirements = c.getPure(Attribute.Animism) >= 2 || c.has(Trait.augmentedPheromones);
        if (pheromonesRequirements && !c.has(Trait.pheromones)) {
            c.add(Trait.pheromones);
        } else if (!pheromonesRequirements && c.has(Trait.pheromones)) {
            c.remove(Trait.pheromones);
        }
        return message;
    }

    public static void learnSkills(Character c) {
        for (Skill skill : getSkillPool()) {
            c.learn(skill);
        }
    }

    public static Set<Skill> getSkillPool() {
        return skillPool;
    }

    public final boolean requirements(Combat c, Character target) {
        return requirements(c, getSelf(), target);
    }

    public abstract boolean requirements(Combat c, Character user, Character target);

    public static void filterAllowedSkills(Combat c, Collection<Skill> skills, Character user) {
        filterAllowedSkills(c, skills, user, null);
    }
    public static void filterAllowedSkills(Combat c, Collection<Skill> skills, Character user, Character target) {
        boolean filtered = false;
        Set<Skill> stanceSkills = new HashSet<Skill>(c.getStance().availSkills(c, user));

        if (stanceSkills.size() > 0) {
            skills.retainAll(stanceSkills);
            filtered = true;
        }
        Set<Skill> availSkills = new HashSet<Skill>();
        for (Status st : user.status) {
            for (Skill sk : st.allowedSkills(c)) {
                if ((target != null && skillIsUsable(c, sk, target)) || skillIsUsable(c, sk)) {
                    availSkills.add(sk);
                }
            }
        }
        if (availSkills.size() > 0) {
            skills.retainAll(availSkills);
            filtered = true;
        }
        Set<Skill> noReqs = new HashSet<Skill>();
        if (!filtered) {
            // if the skill is restricted by status/stance, do not check for
            // requirements
            for (Skill sk : skills) {
                if (sk.getTags(c).contains(SkillTag.mean) && user.has(Trait.softheart)) {
                    continue;
                }
                if (!sk.requirements(c, target != null? target : sk.getDefaultTarget(c))) {
                    noReqs.add(sk);
                }
            }
            skills.removeAll(noReqs);
        }
    }

    public static boolean skillIsUsable(Combat c, Skill s) {
        return skillIsUsable(c, s, null);
    }
    public static boolean skillIsUsable(Combat c, Skill s, Character target) {
        if (target == null) {
            target = s.getDefaultTarget(c);
        }
        boolean charmRestricted = (s.getSelf().is(Stsflag.charmed))
                        && s.type(c) != Tactics.fucking && s.type(c) != Tactics.pleasure && s.type(c) != Tactics.misc;
        boolean allureRestricted =
                        target.is(Stsflag.alluring) && (s.type(c) == Tactics.damage || s.type(c) == Tactics.debuff);
        boolean modifierRestricted = !Global.getMatch().condition.getSkillModifier().allowedSkill(c,s);
        boolean usable = s.usable(c, target) && s.getSelf().canSpend(s.getMojoCost(c)) && !charmRestricted
                        && !allureRestricted && !modifierRestricted;
        return usable;
    }

    public int getMojoBuilt(Combat c) {
        return 0;
    }

    public int getMojoCost(Combat c) {
        return 0;
    }

    public abstract boolean usable(Combat c, Character target);

    public abstract String describe(Combat c);

    public abstract boolean resolve(Combat c, Character target);

    public abstract Skill copy(Character user);

    public abstract Tactics type(Combat c);

    public abstract String deal(Combat c, int damage, Result modifier, Character target);

    public abstract String receive(Combat c, int damage, Result modifier, Character target);

    public boolean isReverseFuck(Character target) {
        return target.hasDick() && getSelf().hasPussy();
    }

    public float priorityMod(Combat c) {
        return 0.0f;
    }

    public int accuracy(Combat c, Character target) {
        return 200;
    }

    public Staleness getStaleness() {
        return this.staleness;
    }

    public int speed() {
        return 5;
    }

    public String getLabel(Combat c) {
        return getName(c);
    }

    public final String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }

    public Character user() {
        return getSelf();
    }

    public void setSelf(Character self) {
        this.self = self;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        return toString().equals(other.toString());
    }

    @Override
    public int hashCode() {
        return ("Skill:" + toString()).hashCode();
    }

    public String getName(Combat c) {
        return toString();
    }

    public boolean makesContact() {
        return false;
    }

    public static boolean resolve(Skill skill, Combat c, Character target) {
        skill.user().addCooldown(skill);
        // save the mojo built of the skill before resolving it (or the status
        // may change)
        int generated = skill.getMojoBuilt(c);

        // Horrendously ugly, I know.
        // But you were the one who removed getWithOrganType...
        if (skill.user().has(Trait.temptress)) {
            FiredUp status = (FiredUp) skill.user().status.stream().filter(s -> s instanceof FiredUp).findAny()
                            .orElse(null);
            if (status != null) {
                if (status.getPart().equals("hands") && skill.getClass() != TemptressHandjob.class
                                || status.getPart().equals("mouth") && skill.getClass() != TemptressBlowjob.class
                                || status.getPart().equals("pussy") && skill.getClass() != TemptressRide.class) {
                    skill.user().removeStatus(Stsflag.firedup);
                }
            }
        }

        boolean success = skill.resolve(c, target);
        skill.user().spendMojo(c, skill.getMojoCost(c));
        if (success) {
            skill.user().buildMojo(c, generated);
        } else if (target.has(Trait.tease) && Random.random(4) == 0) {
            c.write(target, Formatter.format("Dancing just past {other:name-possessive} reach gives {self:name-do} a minor high.", target, skill.getSelf()));
            target.buildMojo(c, 20);
        }
        if (success && c.getCombatantData(skill.getSelf()) != null) {
            c.getCombatantData(skill.getSelf()).decreaseMoveModifier(c, skill);
        }
        if (c.getCombatantData(skill.user()) != null) { 
            c.getCombatantData(skill.user()).setLastUsedSkillName(skill.getName());
        }
        return success;
    }

    public int getCooldown() {
        return cooldown;
    }

    public Collection<String> subChoices(Combat c) {
        return Collections.emptySet();
    }

    public Character getSelf() {
        return self;
    }
    
    protected void printBlinded(Combat c) {
        c.write(getSelf(), "<i>You're sure something is happening, but you can't figure out what it is.</i>");
    }
    
    public Stage getStage() {
        return Stage.REGULAR;
    }

    public Character getDefaultTarget(Combat c) {
        return c.getOpponent(getSelf());
    }

    public final double multiplierForStage(Character target) {
        return getStage().multiplierFor(target);
    }
    
    protected void writeOutput(Combat c, Result result, Character target) {
        writeOutput(c, 0, result, target);
    }
    
    protected void writeOutput(Combat c, int mag, Result result, Character target) {
        if (getSelf().human()) {
            c.write(getSelf(), deal(c, mag, result, target));
        } else if (c.shouldPrintReceive(target, c)) {
            c.write(getSelf(), receive(c, mag, result, target));
        }
    }

    protected void addTag(SkillTag tag) {
        tags.add(tag);
    }

    protected void removeTag(SkillTag tag) {
        tags.remove(tag);
    }
    public final Set<SkillTag> getTags(Combat c) {
        return getTags(c, c.getOpponent(self));
    }

    public Set<SkillTag> getTags(Combat c, Character target) {
        return Collections.unmodifiableSet(tags);
    }
}
