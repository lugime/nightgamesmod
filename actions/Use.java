package actions;

import items.Item;
import status.Buzzed;
import status.Oiled;
import global.Global;
import global.Modifier;

import characters.Character;

public class Use extends Action {
	private Item item;
	
	public Use(Item item) {	
		super("Use "+item.getName());
		if(item==Item.Lubricant){
			name="Oil up";
		}
		else if(item==Item.EnergyDrink){
			name="Energy Drink";
		}
		else if(item==Item.Beer){
			name="Beer";
		}
		this.item=item;
	}

	@Override
	public boolean usable(Character user) {
		return user.has(item)&&(!user.human()||Global.getMatch().condition!=Modifier.noitems);
	}

	@Override
	public Movement execute(Character user) {
		if(item==Item.Lubricant){
			if(user.human()){
				Global.gui().message("You cover yourself in slick oil. It's a weird feeling, but it should make it easier to escape from a hold.");
			}
			user.add(new Oiled(user));
			user.consume(Item.Lubricant, 1);
			return Movement.oil;
		}
		else if(item==Item.EnergyDrink){
			if(user.human()){
				Global.gui().message("You chug down the unpleasant drink. Your tiredness immediately starts to recede.");
			}		
			user.heal(null, 10+Global.random(10));
			user.consume(Item.EnergyDrink, 1);
			return Movement.enerydrink;
		}
		else if(item==Item.Beer){
			if(user.human()){
				Global.gui().message("You pop open a beer and chug it down, feeling buzzed and a bit slugish.");
			}		
			user.add(new Buzzed(user));
			user.consume(Item.Beer, 1);
			return Movement.beer;
		}
		return Movement.wait;
	}

	@Override
	public Movement consider() {
		if(item==Item.Lubricant){
			return Movement.oil;
		}
		else if(item==Item.EnergyDrink){
			return Movement.enerydrink;
		}
		else if(item==Item.Beer){
			return Movement.beer;
		}
		return Movement.wait;
	}

}