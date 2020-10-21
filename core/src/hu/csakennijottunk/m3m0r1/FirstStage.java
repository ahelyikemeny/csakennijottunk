package hu.csakennijottunk.m3m0r1;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Timers.IntervalTimerListener;
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimer;
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimerListener;
import hu.csanyzeg.master.MyBaseClasses.Timers.Timer;

public class FirstStage extends MyStage {
    Aktor aktor;
    Aktor aktor2;
    FirstStage firstStage;


    public int[] generateMap(int cardIDNumber) {
        int[] cards = new int[cardIDNumber * 2];
        for (int i = 0; i < cardIDNumber * 2; i++) {
            cards[i] = i / 2;
        }

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

    public void clickCard(CardActor a) {
        if (firstClick == null) {
            firstClick = a;
            firstClick.setColor(Color.RED);
            return;
        }
        if (firstClick.hashCode() != a.hashCode()) {
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
                    System.out.println("Gratulálok!");
                }
            }
        }
        firstClick.setColor(Color.WHITE);
        firstClick = null;
    }


    public TickTimer secTimer;
    public int sec = 99;

    public FirstStage(MyGame game) {
        super(new ExtendViewport(640, 480), game);
        /*aktor=new Aktor(game);
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

         */
        addBackButtonScreenBackByStackPopListener();

        newGame(4, 3);
/*
        for(Actor a : getActors()) {
            System.out.println(a);
        }

 */
        secTimer = new TickTimer(1, true, new TickTimerListener() {
            @Override
            public void onTick(Timer sender, float correction) {
                super.onTick(sender, correction);
                System.out.println(sec);
                sec--;
            }
        });
        addTimer(secTimer);

    }
}
