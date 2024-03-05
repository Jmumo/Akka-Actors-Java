package org.example;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Random;

public class WorkerBahaviour extends AbstractBehavior<WorkerBahaviour.Command> {

    public static class Command implements Serializable {
        private static final Long serialVersionUUID = 1L;
        private ActorRef<ManagerBahaviour.Command> sender;
        private String message;

        public ActorRef<ManagerBahaviour.Command> getSender() {
            return sender;
        }

        public String getMessage() {
            return message;
        }

        public Command(ActorRef<ManagerBahaviour.Command> sender, String message) {
            this.sender = sender;
            this.message = message;
        }
    }
    private WorkerBahaviour(ActorContext<Command> context) {
        super(context);
    }


    public static Behavior<Command> create(){
       return Behaviors.setup(WorkerBahaviour::new);
    }

    @Override
    public Receive<Command> createReceive() {


        return newReceiveBuilder().
                onAnyMessage(message->{
                    if(message.getMessage().equals("start")){
                        BigInteger bigInteger = new BigInteger(2000, new Random());
                      message.getSender().tell(new ManagerBahaviour.ResultCommand(bigInteger));
                    }
                    return this;
                }).
                build();
    }
}
