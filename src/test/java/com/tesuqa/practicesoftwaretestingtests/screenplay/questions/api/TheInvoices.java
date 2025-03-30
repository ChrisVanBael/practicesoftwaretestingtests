package com.tesuqa.practicesoftwaretestingtests.screenplay.questions.api;

import com.practicesoftwaretesting.client.v5.model.InvoiceResponse;
import com.tesuqa.practicesoftwaretestingtests.screenplay.abilities.UseInvoicesApi;
import net.serenitybdd.screenplay.Question;

import java.util.List;

public class TheInvoices {

    /**
     * Returns a list of InvoiceResponse known by the system
     *
     * @return list of InvoiceResponse
     */
    public static Question<List<InvoiceResponse>> knownByTheSystem() {
        return Question.about("the invoices known by the system")
                .answeredBy(
                        actor -> UseInvoicesApi.as(actor).setAccessToken(actor.recall("token")).getAllInvoices()
                );
    }
}
