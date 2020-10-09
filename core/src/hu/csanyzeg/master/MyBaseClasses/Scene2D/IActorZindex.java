package hu.csanyzeg.master.MyBaseClasses.Scene2D;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;

public interface IActorZindex extends IZindex{

    @Override
    public default boolean setZIndex(int index) {
        //System.out.println("asd");
        Actor actor = (Actor)this;
        //this.zIndex = index;
        Group parent = actor.getParent();
        if (parent == null) return false;
        Array<Actor> children = parent.getChildren();
        if (children.size == 1) return false;
        if (actor.getStage() != null){
            if (actor.getStage() instanceof MyStage){
                ((MyStage)(actor.getStage())).sortActorsByZindex();
                return true;
            }
        }
        return false;
    }

}
