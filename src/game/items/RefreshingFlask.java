package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.items.Item;
import game.actions.ConsumeAction;

/**
 * class representing refreshing flask
 */
public class RefreshingFlask extends Item implements Consumable{


    /**
     * Constructor
     * It has the ability to increase stamina point after consumption
     */
    public RefreshingFlask() {
        super("Refreshing flask", 'u', true);
    }


    /**
     * A refreshing flask item can have a special skill that can increase the current actor's stamina points.
     * @param owner the actor that owns the item
     * @return an unmodifiable list of Actions
     */
    @Override
    public ActionList allowableActions(Actor owner) {
        return new ActionList( new ConsumeAction(this) );
    }


    @Override
    public String consume(Actor actor) {
        int value = (int)(0.2 * actor.getAttributeMaximum(BaseActorAttributes.STAMINA));
        actor.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.INCREASE, value);
        actor.removeItemFromInventory(this);
        return actor + " consumes " + this + ", and it restores the stamina by " + value + " points.";
    }
}