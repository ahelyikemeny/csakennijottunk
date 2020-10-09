package hu.csanyzeg.master.MyBaseClasses.Timers;

public class PermanentTimer extends Timer<PermanentTimerListener> {
    public PermanentTimer(PermanentTimerListener timerListener) {
        setTimerListener(timerListener);
        start();
    }

    @Override
    public void act(float delta) {
        if (enabled && timerListener != null){
            timerListener.onTick(this, delta);
        }
    }
}
