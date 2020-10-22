package hu.csakennijottunk.m3m0r1;

import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

public class Creditbackground extends OneSpriteStaticActor {
    public Creditbackground(MyGame game) {
        super(game,"creditsbackground.png");
        setPositionCenter();
    }
}
