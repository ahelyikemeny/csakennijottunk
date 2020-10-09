package hu.csakennijottunk.m3m0r1;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;

public class FirstStage extends MyStage {
    Aktor aktor;
    Aktor aktor2;

    public FirstStage(MyGame game) {
        super(new ExtendViewport(640, 480), game);
        aktor=new Aktor(game);
        addActor(aktor);
        aktor.setX(10);
        aktor.setY(51);
        aktor2 = new Aktor(game);
        addActor(aktor2);
        aktor2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println("klikk");
            }
        });
    }
}
