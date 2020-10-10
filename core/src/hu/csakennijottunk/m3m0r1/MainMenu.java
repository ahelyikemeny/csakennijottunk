package hu.csakennijottunk.m3m0r1;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import javax.sound.sampled.Line;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
public class MainMenu extends MyStage {
StartButton startButton;
OptionsButton optionsButton;
ExitButton exitButton;
InfoButton infoButton;


public MainMenu(MyGame game) {

    super(new ExtendViewport(640, 480), game);
startButton = new StartButton(game);
addActor(startButton);
startButton.setY(380);
startButton.setX(240);
optionsButton = new OptionsButton(game);
addActor(optionsButton);
optionsButton.setY(180);
optionsButton.setX(240);
exitButton = new ExitButton(game);
addActor(exitButton);
exitButton.setY(0);
exitButton.setX(240);
infoButton = new InfoButton(game);
addActor(infoButton);
infoButton.setY(0);
infoButton.setX(0);












setCameraResetToLeftBottomOfScreen();
}







}
