package org.example;

import akka.actor.typed.ActorSystem;




public class Main {
//    public static void main(String[] args) {
//
//        ActorSystem<String> firstActorSystem = ActorSystem.create(FirstSimpleBehaviour.create(), "FirstActorSystem");
//        firstActorSystem.tell("Yeah this is my firts actor");
//
//
//    }

    public static void main(String[] args) {

        ActorSystem<String> bigPrimes = ActorSystem.create(ManagerBahaviour.create(),"Manager");
        bigPrimes.tell("start");

    }


//    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
}