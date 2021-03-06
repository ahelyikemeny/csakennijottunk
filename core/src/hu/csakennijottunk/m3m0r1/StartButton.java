package hu.csakennijottunk.m3m0r1;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

public class StartButton extends OneSpriteStaticActor {
    GameStage firstStage;

    public void setMainStage(GameStage firstStage) {
        super.setStage(firstStage);
    }

    public StartButton(MyGame game) {
        super(game, "startgomb1.0.png");
        this.setSize(200, 100);
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(new GameScreen(game));


            }

        });

    }


}
