package hu.csanyzeg.master.MyBaseClasses.Scene2D;

public interface IElapsedTime {

    public default void resetElapsedTime()
    {
        setElapsedTime(0);
    }

    public float getElapsedTime();

    public void setElapsedTime(float elapsedTime);

    public default void act(float delta){
        setElapsedTime(getElapsedTime() + delta);
    }
}
