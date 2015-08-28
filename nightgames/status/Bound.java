package nightgames.status;

import nightgames.characters.Attribute;
import nightgames.characters.Character;
import nightgames.characters.Emotion;
import nightgames.combat.Combat;

public class Bound extends Status {
	private int toughness;
	private String binding;

	public Bound(Character affected, int dc, String binding) {
		super("Bound", affected);
		toughness = dc;
		this.binding = binding;
		flag(Stsflag.bound);
	}

	@Override
	public String initialMessage(Combat c, boolean replaced) {
		return String.format("%s now bound by %s.\n", affected.subjectAction("are", "is"), binding);
	}

	@Override
	public String describe() {
		if(affected.human()){
			return "Your hands are bound by "+binding+".";
		}
		else{
			return "Her hands are restrained by "+binding+".";
		}
	}

	@Override
	public float fitnessModifier () {
		return -(5 + Math.min(20, toughness) / 2);
	}

	@Override
	public int mod(Attribute a) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int regen(Combat c) {
		affected.emote(Emotion.desperate, 10);
		affected.emote(Emotion.nervous, 10);
		return 0;
	}
	@Override
	public int damage(Combat c, int x) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double pleasure(Combat c, double x) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int weakened(int x) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int tempted(int x) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int evade() {
		// TODO Auto-generated method stub
		return -15;
	}

	@Override
	public int escape() {
		return -toughness;
	}
	
	@Override
	public void struggle(Character self) {
		if (toughness > 50) 
			toughness = Math.max(Math.round(toughness * .33f), 25);
		else
			toughness = Math.round(toughness * .5f);
	}

	@Override
	public int gainmojo(int x) {
		return 0;
	}

	@Override
	public int spendmojo(int x) {
		return 0;
	}

	@Override
	public int counter() {
		return -10;
	}
	public String toString(){
		return "Bound by " + binding;
	}

	@Override
	public int value() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Status instance(Character newAffected, Character newOther) {
		return new Bound(newAffected, toughness, binding);
	}
}