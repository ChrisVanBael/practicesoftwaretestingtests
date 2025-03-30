package com.tesuqa.practicesoftwaretestingtests.screenplay.tasks.api;

import com.practicesoftwaretesting.client.model.UsersLoginBody;
import com.tesuqa.practicesoftwaretestingtests.screenplay.abilities.UseUsersApi;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;


public class Login {

    /**
     * Login with email and password
     * @param email
     * @param password
     * @return the task
     */
    public static Performable withEmailAndPassword(String email, String password) {
        return Task.where(
                actor -> {
                    UsersLoginBody login = new UsersLoginBody();
                    login.setEmail(email);
                    login.setPassword(password);
                    actor.remember("token",  UseUsersApi.as(actor).login(login));
                }
        );
    }
}
