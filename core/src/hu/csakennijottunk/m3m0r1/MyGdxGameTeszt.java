package hu.csakennijottunk.m3m0r1;

import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;

public class MyGdxGameTeszt extends MyGame {

    @Override
    public void create() {
        super.create();
        setScreen(new GameScreen(this));
    }
}

	
