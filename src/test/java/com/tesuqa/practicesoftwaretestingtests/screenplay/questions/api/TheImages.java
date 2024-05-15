package com.tesuqa.practicesoftwaretestingtests.screenplay.questions.api;

import com.practicesoftwaretesting.client.model.ImageResponse;
import com.tesuqa.practicesoftwaretestingtests.screenplay.abilities.UseImagesApi;
import net.serenitybdd.screenplay.Question;

import java.util.List;

public class TheImages {

    /**
     * Returns a list of Images known by the system
     * @return list of Images
     */
    public static Question<List<ImageResponse>> knownByTheSystem() {
        return Question.about("the images known by the system")
                .answeredBy(
                        actor -> UseImagesApi.as(actor).getAllImages()
                );
    }
}
