package hu.csanyzeg.master.MyBaseClasses.Timers;

public abstract class Timer<Listener> {

    protected Listener timerListener;

    public Listener getTimerListener() {
        return timerListener;
    }

    public void setTimerListener(Listener timerListener) {
        this.timerListener = timerListener;
    }

    protected boolean enabled = true;

    public abstract void act(float delta);

    public void start(){
        enabled = true;
        ((TimerListener)timerListener).onStart(this);
    }

    public void stop(){
        enabled = false;
        ((TimerListener)timerListener).onStop(this);
    }
}
