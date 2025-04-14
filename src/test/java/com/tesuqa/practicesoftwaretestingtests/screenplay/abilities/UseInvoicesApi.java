package com.tesuqa.practicesoftwaretestingtests.screenplay.abilities;

import com.practicesoftwaretesting.client.v5.ApiClient;
import com.practicesoftwaretesting.client.v5.api.InvoiceApi;
import com.practicesoftwaretesting.client.v5.model.InvoiceRequest;
import com.practicesoftwaretesting.client.v5.model.InvoiceResponse;
import com.practicesoftwaretesting.client.v5.model.InvoiceStatusRequest;
import com.practicesoftwaretesting.client.v5.model.PaginatedInvoiceResponse;
import com.practicesoftwaretesting.client.v5.model.UpdateResponse;
import net.serenitybdd.screenplay.Actor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Ability to interact with the Invoices API
 */
public class UseInvoicesApi extends BaseApiAbility {

    private InvoiceApi invoiceApi;

    private UseInvoicesApi(String baseUrl) {
        super(baseUrl);
        this.invoiceApi = ApiClientManager.getInstance(baseUrl).getApiClient().invoice();
    }

    public static UseInvoicesApi at(String baseUrl) {
        return new UseInvoicesApi(baseUrl);
    }

    public static UseInvoicesApi as(Actor actor) {
        return actor.abilityTo(UseInvoicesApi.class);
    }

    @Override
    public String toString() {
        return "call the Invoices API at " + baseUrl;
    }

    @Override
    public void refreshApiClient(ApiClient apiClient) {
        this.invoiceApi = apiClient.invoice();
    }

    /**
     * Retrieves all invoices by paginating through all available pages
     *
     * @return A list of all invoice responses
     */
    public List<InvoiceResponse> getAllInvoices() {
        List<InvoiceResponse> allInvoices = new ArrayList<>();
        Integer currentPage = 1;
        Integer lastPage = 1;

        do {
            Integer finalCurrentPage = currentPage;
            PaginatedInvoiceResponse paginatedResponse = executeApiCall(
                "retrieve invoices page " + currentPage,
                () -> invoiceApi.getInvoices().pageQuery(finalCurrentPage).execute(r -> r),
                PaginatedInvoiceResponse.class
            );

            allInvoices.addAll(paginatedResponse.getData());
            lastPage = paginatedResponse.getLastPage();
            currentPage++;
        } while (currentPage <= lastPage);

        return allInvoices;
    }

    /**
     * Retrieves a specific page of invoices
     *
     * @param page The page number to retrieve
     * @return A paginated invoice response
     */
    public PaginatedInvoiceResponse getInvoicesPage(Integer page) {
        Objects.requireNonNull(page, "Page number cannot be null");

        return executeApiCall(
            "retrieve invoices page " + page,
            () -> invoiceApi.getInvoices().pageQuery(page).execute(r -> r),
            PaginatedInvoiceResponse.class
        );
    }

    /**
     * Retrieves a specific invoice by ID
     *
     * @param invoiceId The ID of the invoice to retrieve
     * @return The invoice response
     */
    public InvoiceResponse getInvoice(String invoiceId) {
        Objects.requireNonNull(invoiceId, "Invoice ID cannot be null");

        return executeApiCall(
            "get invoice with ID " + invoiceId,
            () -> invoiceApi.getInvoice().invoiceIdPath(invoiceId).execute(r -> r),
            InvoiceResponse.class
        );
    }

    /**
     * Creates a new invoice
     *
     * @param invoice The invoice request data
     * @return The created invoice response
     */
    public InvoiceResponse createInvoice(InvoiceRequest invoice) {
        Objects.requireNonNull(invoice, "Invoice request cannot be null");

        return executeApiCall(
            "create invoice",
            () -> invoiceApi.storeInvoice().body(invoice).execute(r -> r),
            InvoiceResponse.class
        );
    }

    /**
     * Updates an existing invoice
     *
     * @param invoice The updated invoice data
     * @param invoiceId The ID of the invoice to update
     * @return The update response
     */
    public UpdateResponse updateInvoice(InvoiceRequest invoice, String invoiceId) {
        Objects.requireNonNull(invoice, "Invoice request cannot be null");
        Objects.requireNonNull(invoiceId, "Invoice ID cannot be null");

        return executeApiCall(
            "update invoice with ID " + invoiceId,
            () -> invoiceApi.updateInvoice().body(invoice).invoiceIdPath(invoiceId).execute(r -> r),
            UpdateResponse.class
        );
    }

    /**
     * Partially updates an existing invoice
     *
     * @param invoice The partial invoice data
     * @param invoiceId The ID of the invoice to update
     * @return The update response
     */
    public UpdateResponse patchInvoice(InvoiceRequest invoice, String invoiceId) {
        Objects.requireNonNull(invoice, "Invoice request cannot be null");
        Objects.requireNonNull(invoiceId, "Invoice ID cannot be null");

        return executeApiCall(
            "partially update invoice with ID " + invoiceId,
            () -> invoiceApi.patchInvoice().body(invoice).invoiceIdPath(invoiceId).execute(r -> r),
            UpdateResponse.class
        );
    }

    /**
     * Updates the status of an invoice
     *
     * @param status The new status
     * @param invoiceId The ID of the invoice to update
     * @return The update response
     */
    public UpdateResponse updateInvoiceStatus(InvoiceStatusRequest status, String invoiceId) {
        Objects.requireNonNull(status, "Status request cannot be null");
        Objects.requireNonNull(invoiceId, "Invoice ID cannot be null");

        return executeApiCall(
            "update status of invoice with ID " + invoiceId,
            () -> invoiceApi.updateInvoiceStatus().body(status).invoiceIdPath(invoiceId).execute(r -> r),
            UpdateResponse.class
        );
    }

    /**
     * Searches for invoices matching a query
     *
     * @param query The search query
     * @param page The page number to retrieve
     * @return A paginated invoice response
     */
    public PaginatedInvoiceResponse searchInvoices(String query, Integer page) {
        Objects.requireNonNull(query, "Search query cannot be null");

        return executeApiCall(
            "search invoices with query '" + query + "'",
            () -> invoiceApi.searchInvoice().qQuery(query).pageQuery(page).execute(r -> r),
            PaginatedInvoiceResponse.class
        );
    }

    /**
     * Downloads a PDF for an invoice
     *
     * @param invoiceNumber The invoice number
     * @return The invoice response
     */
    public InvoiceResponse downloadPDF(String invoiceNumber) {
        Objects.requireNonNull(invoiceNumber, "Invoice number cannot be null");

        return executeApiCall(
            "download PDF for invoice " + invoiceNumber,
            () -> invoiceApi.downloadPDF().invoiceNumberPath(invoiceNumber).execute(r -> r),
            InvoiceResponse.class
        );
    }

    /**
     * Gets the PDF generation status for an invoice
     *
     * @param invoiceNumber The invoice number
     * @return The invoice response with PDF status
     */
    public InvoiceResponse getPDFStatus(String invoiceNumber) {
        Objects.requireNonNull(invoiceNumber, "Invoice number cannot be null");

        return executeApiCall(
            "get PDF status for invoice " + invoiceNumber,
            () -> invoiceApi.downloadPDFStatus().invoiceNumberPath(invoiceNumber).execute(r -> r),
            InvoiceResponse.class
        );
    }
}
