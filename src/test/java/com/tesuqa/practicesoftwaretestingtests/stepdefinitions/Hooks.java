package com.tesuqa.practicesoftwaretestingtests.stepdefinitions;

import io.cucumber.java.BeforeAll;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

public class Hooks {

    @BeforeAll
    public static void beforeAll() {
        OnStage.setTheStage(new OnlineCast());
        Actor myActor = OnStage.theActorCalled("myActor");
        myActor.remember("test", "value");
    }
}
