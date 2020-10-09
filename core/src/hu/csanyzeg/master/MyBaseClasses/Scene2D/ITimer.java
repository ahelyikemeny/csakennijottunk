package hu.csanyzeg.master.MyBaseClasses.Scene2D;

import com.badlogic.gdx.utils.Array;

import hu.csanyzeg.master.MyBaseClasses.Timers.Timer;

public interface ITimer {
    public Array<Timer> getTimers();

    public default void addTimer(Timer timer){
        getTimers().add(timer);
    }

    public default void removeTimer(Timer timer){
        getTimers().removeValue(timer, true);
    }


    public default void act(float delta){
        for(Timer t : getTimers()){
            t.act(delta);
        }
    }

}
