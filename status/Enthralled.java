package status;

import combat.Combat;

import global.Global;
import characters.Attribute;
import characters.Character;
import characters.Emotion;
import characters.State;

public class Enthralled extends Status {

	private int duration;
	public Character master;
	
	public Enthralled(Character self,Character master) {
		super("Enthralled", self);
		duration = Global.random(3) + (self.state == State.combat ? 2 : 5);
		this.master = master;
		flag(Stsflag.enthralled);
	}
	
	@Override
	public String describe() {
		if(affected.human())
		  return "You feel a constant pull on your mind, forcing you to obey"
				+ " the succubus' every command.";
		else{
			return affected.name()+" looks dazed and compliant, ready to follow your orders.";
		}
	}

	@Override
	public boolean mindgames(){
		return true;
	}
	
	@Override
	public float fitnessModifier () {
		return -5;
	}
	
	@Override
	public int mod(Attribute a) {
		if (a == Attribute.Perception)
			return -5;
		return -2;
	}

	@Override
	public int regen(Combat c) {
		duration--;
		affected.spendMojo(5);
		if (duration <= 0|| affected.check(Attribute.Cunning, 10+10*duration)) {
			affected.removelist.add(this);
			affected.addlist.add(new Cynical(affected));
			if (affected.human() && affected.state != State.combat)
				Global.gui().message("Everything around you suddenly seems much clearer,"
						+ " like a lens snapped into focus. You don't really remember why"
						+ " you were heading in the direction you where...");
				
		}
		affected.emote(Emotion.horny,15);
		return 0;
	}

	@Override
	public int damage(Combat c, int paramInt) {
		return 0;
	}

	@Override
	public int pleasure(Combat c, int paramInt) {
		return paramInt/4;
	}

	@Override
	public int weakened(int paramInt) {
		return 0;
	}

	@Override
	public int tempted(int paramInt) {
		return paramInt/4;
	}

	@Override
	public int evade() {
		return -20;
	}

	@Override
	public int escape() {
		return -20;
	}

	@Override
	public int gainmojo(int paramInt) {
		return -paramInt;
	}

	@Override
	public int spendmojo(int paramInt) {
		return 0;
	}

	@Override
	public int counter() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int value() {
		// TODO Auto-generated method stub
		return 0;
	}

}