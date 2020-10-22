package hu.csakennijottunk.m3m0r1;

import com.badlogic.gdx.utils.viewport.ExtendViewport;

import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
public class MainMenu extends MyStage {
    StartButton startButton;
    OptionsButton optionsButton;
    ExitButton exitButton;
    CreditsButton infoButton;
background backGround;

    public MainMenu(MyGame game) {

        super(new ExtendViewport(640, 480), game);
        backGround = new background(game);
        addActor(backGround);
        startButton = new StartButton(game);
        addActor(startButton);
        startButton.setY(380);
        startButton.setX(240);
/*        optionsButton = new OptionsButton(game);
        addActor(optionsButton);
        optionsButton.setY(180);
        optionsButton.setX(240);*/
        CreditsButton infoButton;
        infoButton = new CreditsButton(game);
        addActor(infoButton);
        infoButton.setY(180);
        infoButton.setX(240);

        exitButton = new ExitButton(game);
        addActor(exitButton);
        exitButton.setY(0);
        exitButton.setX(240);
        //infoButton = new InfoButton(game);
/*        addActor(infoButton);
        infoButton.setY(0);
        infoButton.setX(0);*/




        setCameraResetToLeftBottomOfScreen();
        addBackButtonScreenBackByStackPopListener();
    }


}
