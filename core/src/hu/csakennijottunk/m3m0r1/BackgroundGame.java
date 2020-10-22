package hu.csakennijottunk.m3m0r1;

import com.badlogic.gdx.Game;

import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

public class BackgroundGame extends OneSpriteStaticActor {
    public BackgroundGame(MyGame game) {
        super(game, "gamebackground.png");
        this.setSize(640,480);
        this.zIndex= 0;
    }


}

