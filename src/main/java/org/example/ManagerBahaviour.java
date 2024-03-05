package org.example;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;

public class ManagerBahaviour extends AbstractBehavior<String> {
    private ManagerBahaviour(ActorContext<String> context) {
        super(context);
    }

    public static Behavior<String> create(){
        return Behaviors.setup(ManagerBahaviour::new);
    }

    @Override
    public Receive<String> createReceive() {
        return newReceiveBuilder().
                onMessageEquals("start",()->{
                    for(int i = 0; i < 20; i++){
                      ActorRef<String> worker =  getContext().spawn(WorkerBahaviour.create(),"worker"+i);
                      worker.tell("start");
                    }
                    return this;
                }).
                build();
    }
}
