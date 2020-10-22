package hu.csakennijottunk.m3m0r1;

import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimer;
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimerListener;
import hu.csanyzeg.master.MyBaseClasses.Timers.Timer;
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;

public class GameStage extends MyStage {
   Exitbuttonright exitbuttonright;
    ExitButton exitButton;
    Aktor aktor;
    Aktor aktor2;
    GameStage firstStage;
    boolean gameOver = false;


    public int[] generateMap(int cardIDNumber) {
        int[] cards = new int[cardIDNumber * 2];
        for (int i = 0; i < cardIDNumber * 2; i++) {
            cards[i] = i / 2;
        }
        addActor(new BackgroundGame(game));
        RandomXS128 randomXS128 = new RandomXS128();

        for (int i = 0; i < cards.length * 2; i++) {
            int from = randomXS128.nextInt(cards.length);
            int to = randomXS128.nextInt(cards.length);

            int tmp = cards[from];
            cards[from] = cards[to];
            cards[to] = tmp;
        }
        return cards;
    }

    public void newGame(int cardIDNumber, int mapWidth) {
        int[] cardsArray = generateMap(cardIDNumber);
        int count = 0;
        int row = 0;
        for (int i = 0; i < cardsArray.length; i++) {
            addActor(new CardActor(game, cardsArray[i], count * 100, getHeight() - 100f - row * 120));

            //System.out.print(cardsArray[i] + " ");
            count++;
            if (count == mapWidth) {
                //System.out.println();
                count = 0;
                row++;
            }
        }
        //System.out.println();
    }

    CardActor firstClick = null;
    boolean clickActive = true;

    public void clickCard(CardActor a) {
        if (gameOver){
            return;
        }
        if (!clickActive){
            return;
        }
        if (firstClick == null) {
            firstClick = a;
            firstClick.showCard();
            return;
        }
        if (firstClick.hashCode() != a.hashCode()) {
            a.showCard();
            if (firstClick.getImageID() == a.getImageID()) {
                firstClick.remove();
                a.remove();
                int count = 0;
                for (Actor ac : getActors()) {
                    if (ac instanceof CardActor) {
                        count++;
                    }
                }
                if (count == 0) {
                    Label.LabelStyle labelStyle = new Label.LabelStyle();
                    labelStyle.font = game.getMyAssetManager().getFont("latinwd.ttf");
                    MyLabel label = new MyLabel(game, "Gratulálok!", labelStyle);
                    addActor(label);
                    label.setPositionCenterOfActorToCenterOfViewport();
                    label.addListener(new ClickListener(){
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            super.clicked(event, x, y);
                            game.setScreenBackByStackPop();
                        }
                    });
                }
                clickActive = true;
                firstClick = null;

            }else{
                TickTimer timer = new TickTimer(2, false, new TickTimerListener(){
                    @Override
                    public void onTick(Timer sender, float correction) {
                        super.onTick(sender, correction);
                        removeTimer(sender);
                        firstClick.hideCard();
                        a.hideCard();
                        firstClick = null;
                        clickActive = true;
                    }
                });
                addTimer(timer);
                clickActive = false;
            }
        }


    }


    public TickTimer secTimer;
    public int sec = 99;
    public MyLabel timerLabel;

    public GameStage(MyGame game) {
        super(new ExtendViewport(640, 480), game);

        addBackButtonScreenBackByStackPopListener();




        newGame(8, 4);

        secTimer = new TickTimer(1, true, new TickTimerListener() {
            @Override
            public void onTick(Timer sender, float correction) {
                super.onTick(sender, correction);
                System.out.println(sec);
                sec--;
                timerLabel.setText(sec+"");
                if (sec == 0){
                    gameOver = true;
                    Label.LabelStyle labelStyle = new Label.LabelStyle();
                    labelStyle.font = game.getMyAssetManager().getFont("latinwd.ttf");
                    MyLabel label = new MyLabel(game, "Game Over", labelStyle);
                    addActor(label);
                    label.setPositionCenterOfActorToCenterOfViewport();
                    label.addListener(new ClickListener(){
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            super.clicked(event, x, y);
                            game.setScreenBackByStackPop();
                        }
                    });
                    removeTimer(sender);
                }

            }
        });
        addTimer(secTimer);

exitbuttonright = new Exitbuttonright(game);
   addActor(exitbuttonright);
exitbuttonright.setX(450);


        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = game.getMyAssetManager().getFont("latinwd.ttf");
        timerLabel = new MyLabel(game, sec + "", labelStyle);
        addActor(timerLabel);
        timerLabel.setPosition(410,300);

    }
}
