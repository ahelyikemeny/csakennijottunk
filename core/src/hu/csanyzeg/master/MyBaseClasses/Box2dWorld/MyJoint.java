package hu.csanyzeg.master.MyBaseClasses.Box2dWorld;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;

import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyActor;

public class MyJoint {
    public final MyActor bActor;
    public final MyActor aActor;
    public final Joint joint;
    private boolean removed = false;

    public boolean isRemoved() {
        return removed;
    }

    public void remove(){
        removeJoint(this);
    }

    private MyJoint(MyActor bActor, MyActor aActor, Joint joint) {
        this.bActor = bActor;
        this.aActor = aActor;
        this.joint = joint;
    }




    public static void createWeldJoint(MyActor a, MyActor b){
        WeldJointDef jointDef = new WeldJointDef();
        Body bodyA = (Body) a.getActorWorldHelper().getBody();
        Body bodyB = (Body) b.getActorWorldHelper().getBody();
        Vector2 anchor = new Vector2();
        anchor.add(bodyA.getPosition());
        anchor.add(bodyB.getPosition());
        anchor.scl(0.5f);
        jointDef.initialize(bodyA, bodyB, anchor);
        createJoint(a,b,jointDef);
    }


    public static void createPrismaticJoint(MyActor a, MyActor b){
        PrismaticJointDef jointDef = new PrismaticJointDef();
        jointDef.bodyA = (Body) a.getActorWorldHelper().getBody();
        jointDef.bodyB = (Body) b.getActorWorldHelper().getBody();
        jointDef.localAnchorA.set(jointDef.bodyA.getMassData().center);
        jointDef.localAnchorB.set(jointDef.bodyB.getMassData().center);
        jointDef.collideConnected = true;
        jointDef.maxMotorForce = 100f;
        jointDef.enableMotor = true;
        jointDef.motorSpeed = -1f;
        jointDef.localAxisA.set(jointDef.bodyA.getLocalVector(new Vector2(0,1)));
        createJoint(a,b,jointDef);
    }


    public static void createRevoluteJoint(MyActor a, MyActor b){
        RevoluteJointDef jointDef = new RevoluteJointDef();
        Body bodyA = (Body) a.getActorWorldHelper().getBody();
        Body bodyB = (Body) b.getActorWorldHelper().getBody();
        Vector2 anchor = new Vector2();
        anchor.add(bodyA.getPosition());
        anchor.add(bodyB.getPosition());
        anchor.scl(0.5f);
        jointDef.initialize(bodyA, bodyB, anchor);
        createJoint(a,b,jointDef);
    }


    public static void createDistanceJoint(MyActor a, MyActor b){
        DistanceJointDef jointDef = new DistanceJointDef();
        Body bodyA = (Body) a.getActorWorldHelper().getBody();
        Body bodyB = (Body) b.getActorWorldHelper().getBody();
        jointDef.initialize(bodyA, bodyB, bodyA.getPosition().add(bodyA.getMassData().center), bodyB.getPosition().add(bodyB.getMassData().center));
        createJoint(a,b,jointDef);
    }



    public static void createMouseJoint(MyActor ground, MyActor target, float maxForce) {
        MouseJointDef jointDef = new MouseJointDef();
        jointDef.bodyA = (Body) ground.getActorWorldHelper().getBody(); //Ground body
        jointDef.bodyB = (Body) target.getActorWorldHelper().getBody(); //Target body
        jointDef.collideConnected = true;
        jointDef.bodyA.setType(BodyDef.BodyType.StaticBody);
        for(Fixture f : jointDef.bodyA.getFixtureList()){
            f.setSensor(true);
        }
        jointDef.maxForce = maxForce;
        jointDef.target.set(jointDef.bodyA.getPosition());
        createJoint(ground,target,jointDef);
    }

    public static void createMouseJoint(MyActor ground, MyActor target){
        createMouseJoint(ground, target, 10000.0f * ((Body) target.getActorWorldHelper().getBody()).getMass());
    }

    public static void removeJoint(final MyJoint joint) {
        ((Box2DWorldHelper) joint.bActor.getActorWorldHelper()).invoke(new Runnable() {
                                                                          @Override
                                                                          public void run() {
                                                                              if (!joint.removed) {
                                                                                  joint.removed = true;
                                                                                  Box2DWorldHelper helper = ((Box2DWorldHelper) joint.bActor.getActorWorldHelper());
                                                                                  helper.getJoints().removeValue(joint, true);
                                                                                  helper.world.destroyJoint(joint.joint);
                                                                                  ((Box2DWorldHelper) joint.aActor.getActorWorldHelper()).getJoints().removeValue(joint, true);
                                                                              }
                                                                          }
                                                                      }
        );
    }

    public static void createRopeJoint(MyActor a, MyActor b) {
        Body bodyA = (Body) a.getActorWorldHelper().getBody();
        Body bodyB = (Body) b.getActorWorldHelper().getBody();
        createRopeJoint(a, b, (new Vector2(bodyA.getPosition()).add(bodyA.getMassData().center).sub(bodyB.getPosition()).sub(bodyB.getMassData().center)).len());
    }

    public static void createRopeJoint(MyActor a, MyActor b, float maxLength) {
        RopeJointDef jointDef = new RopeJointDef();
        jointDef.bodyA = (Body)a.getActorWorldHelper().getBody();
        jointDef.bodyB = (Body)b.getActorWorldHelper().getBody();
        jointDef.localAnchorA.set(jointDef.bodyA.getMassData().center);
        jointDef.localAnchorB.set(jointDef.bodyB.getMassData().center);
        jointDef.collideConnected = true;
        createJoint(a,b,jointDef);
    }

    public static void createJoint(MyActor a, MyActor b, JointDef jointDef){
        Joint w = ((Box2DWorldHelper)a.getActorWorldHelper()).world.createJoint(jointDef);
        MyJoint myJoint = new MyJoint(a,b,w);
        ((Box2DWorldHelper)(a.getActorWorldHelper())).addJoint(myJoint);
        ((Box2DWorldHelper)(b.getActorWorldHelper())).addJoint(myJoint);
    }


}
