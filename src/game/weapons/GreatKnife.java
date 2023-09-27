package game.weapons;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.actions.AttackAction;
import game.actions.SellAction;
import game.actions.StabAndStepAction;
import game.items.Sellable;
import game.utils.Ability;
import game.utils.Status;

/**
 * Class representing the great knife weapon that is sold by Traveller
 *
 * @author yangdan
 */

public class GreatKnife extends WeaponItem implements Sellable {


    /* Default constants for the stamina used to activate the skill of great knife */
    private static final float REDUCED_STAMINA_RATE = 0.25f;


    /**
     * Constructor of Great Knife
     */
    public GreatKnife() {
        super("Great Knife", '>', 75, "stabs", 70);
        this.addCapability(Ability.SKILL);
    }


    /**
     * Player can "use" this weapon to otherActor that is around
     * For example, player can attack otherActor with this weapon
     * For example, player can apply skilled attack to otherActor with this weapon
     * For example, player can sell this weapon to the otherActor if it is TRADER
     * @param otherActor the other actor
     * @param location the location of the other actor
     * @return actions a list of Actions that the player can perform to otherActor with this weapon
     */
    @Override
    public ActionList allowableActions(Actor otherActor, Location location) {
        ActionList actions = new ActionList();
        // When otherActor is enemy, player can attack with this weapon
        if (otherActor.hasCapability(Status.HOSTILE_TO_PLAYER)) {
            actions.add(new AttackAction(otherActor, location.toString(), this));
            actions.add(new StabAndStepAction(otherActor, REDUCED_STAMINA_RATE, this));
        }
        // When otherActor is trader, player can sell this item
        if (otherActor.hasCapability(Status.TRADER))
            actions.add( new SellAction(this, this.toString(), otherActor) );
        return actions;

    }

    /**
     * Great knife can be sold at 175 runes
     * @return an integer value of 175
     */
    @Override
    public int getSellingPrice() {
        return 175;
    }


    /**
     * Sell great knife to the trader and there is a 10% chance of the traveller taking the runes from the player instead.
     * @param actor Actor who sells items at the sale stage
     * @param trader Actor who takes items at the sale stage
     * @return a sting showing the result of selling this item
     */
    @Override
    public String sell(Actor actor, Actor trader) {
        int price = getSellingPrice();
        actor.removeItemFromInventory( this ); // remove this item from the player's inventory
        if (Math.random() < 0.1){ // there is 10% chance to take at most 175 runes from the player
            actor.deductBalance( Math.min(actor.getBalance(), price) );
            return trader + " takes the runes from " + actor + ".";
        }
        actor.addBalance( price ); // successfully sold it, so add balance
        return actor + " successfully sold " + this + " for " + price + " runes to " + trader + ".";
    }

}