package nightgames.skills;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
        Global.getSkillPool().clear();
        Global.getSkillPool().add(new Slap(ch));
        Global.getSkillPool().add(new Tribadism(ch));
        Global.getSkillPool().add(new PussyGrind(ch));
        Global.getSkillPool().add(new Slap(ch));
        Global.getSkillPool().add(new ArmBar(ch));
        Global.getSkillPool().add(new Blowjob(ch));
        Global.getSkillPool().add(new Cunnilingus(ch));
        Global.getSkillPool().add(new Escape(ch));
        Global.getSkillPool().add(new Flick(ch));
        Global.getSkillPool().add(new ToggleKnot(ch));
        Global.getSkillPool().add(new LivingClothing(ch));
        Global.getSkillPool().add(new LivingClothingOther(ch));
        Global.getSkillPool().add(new Engulf(ch));
        Global.getSkillPool().add(new CounterFlower(ch));
        Global.getSkillPool().add(new Knee(ch));
        Global.getSkillPool().add(new LegLock(ch));
        Global.getSkillPool().add(new LickNipples(ch));
        Global.getSkillPool().add(new Maneuver(ch));
        Global.getSkillPool().add(new Paizuri(ch));
        Global.getSkillPool().add(new PerfectTouch(ch));
        Global.getSkillPool().add(new Restrain(ch));
        Global.getSkillPool().add(new Reversal(ch));
        Global.getSkillPool().add(new LeechEnergy(ch));
        Global.getSkillPool().add(new SweetScent(ch));
        Global.getSkillPool().add(new Spank(ch));
        Global.getSkillPool().add(new Stomp(ch));
        Global.getSkillPool().add(new StandUp(ch));
        Global.getSkillPool().add(new WildThrust(ch));
        Global.getSkillPool().add(new SuckNeck(ch));
        Global.getSkillPool().add(new Tackle(ch));
        Global.getSkillPool().add(new Taunt(ch));
        Global.getSkillPool().add(new Trip(ch));
        Global.getSkillPool().add(new Whisper(ch));
        Global.getSkillPool().add(new Kick(ch));
        Global.getSkillPool().add(new PinAndBlow(ch));
        Global.getSkillPool().add(new PinningPaizuri(ch));
        Global.getSkillPool().add(new Footjob(ch));
        Global.getSkillPool().add(new FootPump(ch));
        Global.getSkillPool().add(new HeelGrind(ch));
        Global.getSkillPool().add(new Handjob(ch));
        Global.getSkillPool().add(new Squeeze(ch));
        Global.getSkillPool().add(new Nurple(ch));
        Global.getSkillPool().add(new Finger(ch));
        Global.getSkillPool().add(new Aphrodisiac(ch));
        Global.getSkillPool().add(new Lubricate(ch));
        Global.getSkillPool().add(new Dissolve(ch));
        Global.getSkillPool().add(new Sedate(ch));
        Global.getSkillPool().add(new Tie(ch));
        Global.getSkillPool().add(new Masturbate(ch));
        Global.getSkillPool().add(new Piston(ch));
        Global.getSkillPool().add(new Grind(ch));
        Global.getSkillPool().add(new Thrust(ch));
        Global.getSkillPool().add(new UseDildo(ch));
        Global.getSkillPool().add(new UseOnahole(ch));
        Global.getSkillPool().add(new UseCrop(ch));
        Global.getSkillPool().add(new Carry(ch));
        Global.getSkillPool().add(new Tighten(ch));
        Global.getSkillPool().add(new ViceGrip(ch));
        Global.getSkillPool().add(new HipThrow(ch));
        Global.getSkillPool().add(new SpiralThrust(ch));
        Global.getSkillPool().add(new Bravado(ch));
        Global.getSkillPool().add(new Diversion(ch));
        Global.getSkillPool().add(new Undress(ch));
        Global.getSkillPool().add(new StripSelf(ch));
        Global.getSkillPool().add(new StripTease(ch));
        Global.getSkillPool().add(new Sensitize(ch));
        Global.getSkillPool().add(new EnergyDrink(ch));
        Global.getSkillPool().add(new Strapon(ch));
        Global.getSkillPool().add(new AssFuck(ch));
        Global.getSkillPool().add(new Turnover(ch));
        Global.getSkillPool().add(new Tear(ch));
        Global.getSkillPool().add(new Binding(ch));
        Global.getSkillPool().add(new Bondage(ch));
        Global.getSkillPool().add(new WaterForm(ch));
        Global.getSkillPool().add(new DarkTendrils(ch));
        Global.getSkillPool().add(new Dominate(ch));
        Global.getSkillPool().add(new Illusions(ch));
        Global.getSkillPool().add(new Glamour(ch));
        Global.getSkillPool().add(new LustAura(ch));
        Global.getSkillPool().add(new MagicMissile(ch));
        Global.getSkillPool().add(new Masochism(ch));
        Global.getSkillPool().add(new NakedBloom(ch));
        Global.getSkillPool().add(new ShrinkRay(ch));
        Global.getSkillPool().add(new SpawnFaerie(ch, Ptype.fairyfem));
        Global.getSkillPool().add(new SpawnImp(ch, Ptype.impfem));
        Global.getSkillPool().add(new SpawnFaerie(ch, Ptype.fairymale));
        Global.getSkillPool().add(new SpawnImp(ch, Ptype.impmale));
        Global.getSkillPool().add(new SpawnFGoblin(ch, Ptype.fgoblin));
        Global.getSkillPool().add(new SpawnSlime(ch));
        Global.getSkillPool().add(new StunBlast(ch));
        Global.getSkillPool().add(new Fly(ch));
        Global.getSkillPool().add(new Command(ch));
        Global.getSkillPool().add(new Obey(ch));
        Global.getSkillPool().add(new OrgasmSeal(ch));
        Global.getSkillPool().add(new DenyOrgasm(ch));
        Global.getSkillPool().add(new Drain(ch));
        Global.getSkillPool().add(new LevelDrain(ch));
        Global.getSkillPool().add(new StoneForm(ch));
        Global.getSkillPool().add(new FireForm(ch));
        Global.getSkillPool().add(new Defabricator(ch));
        Global.getSkillPool().add(new TentaclePorn(ch));
        Global.getSkillPool().add(new Sacrifice(ch));
        Global.getSkillPool().add(new Frottage(ch));
        Global.getSkillPool().add(new FaceFuck(ch));
        Global.getSkillPool().add(new VibroTease(ch));
        Global.getSkillPool().add(new TailPeg(ch));
        Global.getSkillPool().add(new CommandDismiss(ch));
        Global.getSkillPool().add(new CommandDown(ch));
        Global.getSkillPool().add(new CommandGive(ch));
        Global.getSkillPool().add(new CommandHurt(ch));
        Global.getSkillPool().add(new CommandInsult(ch));
        Global.getSkillPool().add(new CommandMasturbate(ch));
        Global.getSkillPool().add(new CommandOral(ch));
        Global.getSkillPool().add(new CommandStrip(ch));
        Global.getSkillPool().add(new CommandStripPlayer(ch));
        Global.getSkillPool().add(new CommandUse(ch));
        Global.getSkillPool().add(new ShortCircuit(ch));
        Global.getSkillPool().add(new IceForm(ch));
        Global.getSkillPool().add(new Barrier(ch));
        Global.getSkillPool().add(new CatsGrace(ch));
        Global.getSkillPool().add(new Charm(ch));
        Global.getSkillPool().add(new Tempt(ch));
        Global.getSkillPool().add(new EyesOfTemptation(ch));
        Global.getSkillPool().add(new TailJob(ch));
        Global.getSkillPool().add(new FaceSit(ch));
        Global.getSkillPool().add(new Smother(ch));
        Global.getSkillPool().add(new BreastSmother(ch));
        Global.getSkillPool().add(new MutualUndress(ch));
        Global.getSkillPool().add(new Surrender(ch));
        Global.getSkillPool().add(new ReverseFuck(ch));
        Global.getSkillPool().add(new ReverseCarry(ch));
        Global.getSkillPool().add(new ReverseFly(ch));
        Global.getSkillPool().add(new CounterDrain(ch));
        Global.getSkillPool().add(new CounterRide(ch));
        Global.getSkillPool().add(new CounterPin(ch));
        Global.getSkillPool().add(new ReverseAssFuck(ch));
        Global.getSkillPool().add(new Nurse(ch));
        Global.getSkillPool().add(new Suckle(ch));
        Global.getSkillPool().add(new UseDraft(ch));
        Global.getSkillPool().add(new ThrowDraft(ch));
        Global.getSkillPool().add(new ReverseAssFuck(ch));
        Global.getSkillPool().add(new FondleBreasts(ch));
        Global.getSkillPool().add(new Fuck(ch));
        Global.getSkillPool().add(new Kiss(ch));
        Global.getSkillPool().add(new Struggle(ch));
        Global.getSkillPool().add(new Tickle(ch));
        Global.getSkillPool().add(new Wait(ch));
        Global.getSkillPool().add(new Bluff(ch));
        Global.getSkillPool().add(new StripTop(ch));
        Global.getSkillPool().add(new StripBottom(ch));
        Global.getSkillPool().add(new Shove(ch));
        Global.getSkillPool().add(new Recover(ch));
        Global.getSkillPool().add(new Straddle(ch));
        Global.getSkillPool().add(new ReverseStraddle(ch));
        Global.getSkillPool().add(new Stunned(ch));
        Global.getSkillPool().add(new Distracted(ch));
        Global.getSkillPool().add(new PullOut(ch));
        Global.getSkillPool().add(new ThrowDraft(ch));
        Global.getSkillPool().add(new UseDraft(ch));
        Global.getSkillPool().add(new TentacleRape(ch));
        Global.getSkillPool().add(new Anilingus(ch));
        Global.getSkillPool().add(new UseSemen(ch));
        Global.getSkillPool().add(new Invitation(ch));
        Global.getSkillPool().add(new SubmissiveHold(ch));
        Global.getSkillPool().add(new BreastGrowth(ch));
        Global.getSkillPool().add(new CockGrowth(ch));
        Global.getSkillPool().add(new BreastRay(ch));
        Global.getSkillPool().add(new FootSmother(ch));
        Global.getSkillPool().add(new FootWorship(ch));
        Global.getSkillPool().add(new BreastWorship(ch));
        Global.getSkillPool().add(new CockWorship(ch));
        Global.getSkillPool().add(new PussyWorship(ch));
        Global.getSkillPool().add(new SuccubusSurprise(ch));
        Global.getSkillPool().add(new TemptressHandjob(ch));
        Global.getSkillPool().add(new TemptressBlowjob(ch));
        Global.getSkillPool().add(new TemptressTitfuck(ch));
        Global.getSkillPool().add(new TemptressRide(ch));
        Global.getSkillPool().add(new TemptressStripTease(ch));
        Global.getSkillPool().add(new Blindside(ch));
        Global.getSkillPool().add(new LeechSeed(ch));
        Global.getSkillPool().add(new Beg(ch));
        Global.getSkillPool().add(new Cowardice(ch));
        Global.getSkillPool().add(new Dive(ch));
        Global.getSkillPool().add(new Offer(ch));
        Global.getSkillPool().add(new ShamefulDisplay(ch));
        Global.getSkillPool().add(new Stumble(ch));
        Global.getSkillPool().add(new TortoiseWrap(ch));
        Global.getSkillPool().add(new FaerieSwarm(ch));
        Global.getSkillPool().add(new DarkTalisman(ch));
        Global.getSkillPool().add(new HeightenSenses(ch));
        Global.getSkillPool().add(new LewdSuggestion(ch));
        Global.getSkillPool().add(new Suggestion(ch));
        Global.getSkillPool().add(new ImbueFetish(ch));
        Global.getSkillPool().add(new AssJob(ch));
        Global.getSkillPool().add(new TailSuck(ch));
        Global.getSkillPool().add(new ToggleSlimeCock(ch));
        Global.getSkillPool().add(new ToggleSlimePussy(ch));
        Global.getSkillPool().add(new Spores(ch));
        Global.getSkillPool().add(new EngulfedFuck(ch));
        Global.getSkillPool().add(new Pray(ch));
        Global.getSkillPool().add(new Prostrate(ch));
        Global.getSkillPool().add(new DarkKiss(ch));
        Global.getSkillPool().add(new SlimeMimicry(ch));
        Global.getSkillPool().add(new MimicAngel(ch));
        Global.getSkillPool().add(new MimicCat(ch));
        Global.getSkillPool().add(new MimicDryad(ch));
        Global.getSkillPool().add(new MimicSuccubus(ch));
        Global.getSkillPool().add(new MimicWitch(ch));
        Global.getSkillPool().add(new Parasite(ch));
        Global.getSkillPool().add(new Bite(ch));
        Global.getSkillPool().add(new PlaceBlindfold(ch));
        Global.getSkillPool().add(new RipBlindfold(ch));
        Global.getSkillPool().add(new ToggleBlindfold(ch));
        Global.getSkillPool().add(new BunshinAssault(ch));
        Global.getSkillPool().add(new BunshinService(ch));
        Global.getSkillPool().add(new GoodnightKiss(ch));
        Global.getSkillPool().add(new NeedleThrow(ch));
        Global.getSkillPool().add(new StealClothes(ch));
        Global.getSkillPool().add(new Substitute(ch));
        Global.getSkillPool().add(new AttireShift(ch));
        Global.getSkillPool().add(new CheapShot(ch));
        Global.getSkillPool().add(new EmergencyJump(ch));
        Global.getSkillPool().add(new Haste(ch));
        Global.getSkillPool().add(new Rewind(ch));
        Global.getSkillPool().add(new Unstrip(ch));
        Global.getSkillPool().add(new WindUp(ch));
        Global.getSkillPool().add(new ThrowSlime(ch));
        Global.getSkillPool().add(new Edge(ch));
        Global.getSkillPool().add(new SummonYui(ch));
        Global.getSkillPool().add(new Simulacrum(ch));
        Global.getSkillPool().add(new Divide(ch));
        Global.getSkillPool().add(new PetThreesome(ch));
        Global.getSkillPool().add(new ReversePetThreesome(ch));
        Global.getSkillPool().add(new PetInitiatedThreesome(ch));
        Global.getSkillPool().add(new PetInitiatedReverseThreesome(ch));
        Global.getSkillPool().add(new FlyCatcher(ch));
        Global.getSkillPool().add(new Honeypot(ch));
        Global.getSkillPool().add(new TakeOffShoes(ch));
        Global.getSkillPool().add(new LaunchHarpoon(ch));
        Global.getSkillPool().add(new ThrowBomb(ch));
        Global.getSkillPool().add(new RemoveBomb(ch));
        Global.getSkillPool().add(new MagLock(ch));
        Global.getSkillPool().add(new Collar(ch));
        Global.getSkillPool().add(new HypnoVisorPlace(ch));
        Global.getSkillPool().add(new HypnoVisorRemove(ch));
        Global.getSkillPool().add(new StripMinor(ch));
        Global.getSkillPool().add(new DemandArousal(ch));
        Global.getSkillPool().add(new Embrace(ch));
        Global.getSkillPool().add(new SuccubusNurse(ch));
        Global.getSkillPool().add(new WingWrap(ch));
        Global.getSkillPool().add(new ComeHither(ch));
        Global.getSkillPool().add(new KiShout(ch));
        Global.getSkillPool().add(new PressurePoint(ch));
        Global.getSkillPool().add(new Deepen(ch));

        if (Global.isDebugOn(DebugFlags.DEBUG_SKILLS)) {
            Global.getSkillPool().add(new SelfStun(ch));
        }
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
