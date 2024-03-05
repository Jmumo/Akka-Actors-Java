package org.example;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;
import scala.runtime.Static;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.SortedSet;
import java.util.TreeSet;

public class ManagerBahaviour extends AbstractBehavior<ManagerBahaviour.Command> {


    private SortedSet<BigInteger> primes = new TreeSet<>();


    public static interface Command extends Serializable {}

    public static class IntructionCommand implements Command{
        public IntructionCommand(String message) {
            this.message = message;
        }



        public String getMessage() {
            return message;
        }

        private static final long SerialVersionUID = 1L;
        private String message;
    }



    public static class ResultCommand implements Command{
        private static final long SerialVersionUID = 1L;
        private BigInteger prime;

        public BigInteger getPrime() {
            return prime;
        }

        public ResultCommand(BigInteger prime) {
            this.prime = prime;
        }
    }




    private ManagerBahaviour(ActorContext<ManagerBahaviour.Command> context) {
        super(context);
    }

    public static Behavior<Command> create(){
        return Behaviors.setup(ManagerBahaviour::new);
    }

    @Override
    public Receive<Command> createReceive() {
        return newReceiveBuilder().
                onMessage(IntructionCommand.class,command->{

                    if(command.getMessage().equals("start")){
                        for(int i = 0; i < 20; i++){
                            ActorRef<WorkerBahaviour.Command> worker =  getContext().spawn(WorkerBahaviour.create(),"worker"+i);
                            worker.tell(new WorkerBahaviour.Command(getContext().getSelf(),"start"));
                        }
                    }
                    return this;
                })

                .onMessage(ResultCommand.class,command->{
                    primes.add(command.getPrime());

                    System.out.println(" i have Received " + primes.size() + "numbers");

                    if(primes.size() == 20){
                        primes.forEach(System.out::println);
                    }
                    return this;
                }).

                build();
    }
}
