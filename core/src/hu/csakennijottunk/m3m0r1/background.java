package hu.csakennijottunk.m3m0r1;

import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

public class background extends OneSpriteStaticActor {
    public background(MyGame game) {
        super(game, "background.png");
        this.setSize(640, 480);


    }


}
