package hu.csakennijottunk.m3m0r1;

import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;

public class MyGdxGame extends MyGame {

    @Override
    public void create() {
        super.create();
        setScreen(new MenuScreen(this));
    }
}

	
