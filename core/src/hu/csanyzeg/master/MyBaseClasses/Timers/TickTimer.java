package hu.csanyzeg.master.MyBaseClasses.Timers;

public class TickTimer extends Timer<TickTimerListener> {


    protected boolean repeat;
    protected float interval;
    public float elapsedTime = 0;


    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public float getInterval() {
        return interval;
    }

    public void setInterval(float interval) {
        this.interval = interval;
        elapsedTime = 0;
    }


    public TickTimer(float interval, boolean repeat, TickTimerListener timerListener) {
        this.timerListener = timerListener;
        this.repeat = repeat;
        this.interval = interval;
        start();
    }

    public void act(float delta){
        if (!enabled) return;
        elapsedTime += delta;
        if (elapsedTime >= interval){
            if (timerListener !=null){
                float correction = elapsedTime-interval;
                timerListener.onTick(this, correction);
                if (!repeat){
                    stop();
                }else{
                    elapsedTime = correction;
                    timerListener.onRepeat(this);
                }
            }
        }
    }

    public void start(){
        super.start();
        elapsedTime = 0;
    }

    public void stop(){
        super.stop();
        elapsedTime = 0;
    }
}
