package hu.csanyzeg.master.MyBaseClasses.Timers;

public class IntervalTimer extends Timer<IntervalTimerListener> {
    protected float elapsedTime = 0;

    protected float startTime;
    protected float stopTime;
    protected float repeatTime;
    protected boolean repeat;


    public IntervalTimer(float stopTime, IntervalTimerListener timerListener) {
        this.startTime = 0;
        this.stopTime = stopTime;
        this.repeatTime = 0;
        this.repeat = false;
        setTimerListener(timerListener);
        start();
    }


    public IntervalTimer(float startTime, float stopTime, IntervalTimerListener timerListener) {
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.repeatTime = 0;
        this.repeat = false;
        setTimerListener(timerListener);
        start();
    }


    public IntervalTimer(float startTime, float stopTime, float repeatTime, IntervalTimerListener timerListener) {
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.repeatTime = repeatTime;
        this.repeat = true;
        setTimerListener(timerListener);
        start();
    }

    public float getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(float elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public float getStartTime() {
        return startTime;
    }

    public void setStartTime(float startTime) {
        this.startTime = startTime;
    }

    public float getStopTime() {
        return stopTime;
    }

    public void setStopTime(float stopTime) {
        this.stopTime = stopTime;
    }

    public float getRepeatTime() {
        return repeatTime;
    }

    public void setRepeatTime(float repeatTime) {
        this.repeatTime = repeatTime;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    @Override
    public void act(float delta) {
        elapsedTime += delta;
        if (enabled && timerListener!=null){
            if (elapsedTime >= startTime && elapsedTime <= stopTime){
                timerListener.onTick(this, delta);
            }
            if (elapsedTime > repeatTime && elapsedTime > stopTime){
                if(repeat) {
                    timerListener.onRepeat(this);
                    reset();
                }else{
                    stop();
                }
            }
        }
    }

    public void reset(){
        elapsedTime = 0;
    }

    @Override
    public void start() {
        super.start();
        elapsedTime = 0f;
    }
}
