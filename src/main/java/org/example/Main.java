package org.example;

import akka.actor.typed.ActorSystem;




public class Main {
    public static void main(String[] args) {

        ActorSystem<String> firstActorSystem = ActorSystem.create(FirstSimpleBehaviour.create(), "FirstActorSystem");
        firstActorSystem.tell("Yeah this is my firts actor");


    }
}