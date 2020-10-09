package hu.csanyzeg.master.MyBaseClasses.Box2dWorld;

import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class MyFixtureDef {

    /** The friction coefficient, usually in the range [0,1]. **/
    public float friction = 0.2f;

    /** The restitution (elasticity) usually in the range [0,1]. **/
    public float restitution = 0.2f;

    /** The density, usually in kg/m^2. **/
    public float density = 5;

    /** A sensor shape collects contact information but never generates a collision response. */
    public boolean isSensor = false;

    public MyFixtureDef(){

    }

    public MyFixtureDef(float friction, float restitution, float density, boolean isSensor) {
        this.friction = friction;
        this.restitution = restitution;
        this.density = density;
        this.isSensor = isSensor;
    }

    public void setFixtureValues(FixtureDef target){
        target.density = density;
        target.friction = friction;
        target.isSensor = isSensor;
        target.restitution = restitution;
    }
}
