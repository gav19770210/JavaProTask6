package ru.gav19770210.javapro.task06.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.gav19770210.javapro.task05.entities.Product;
import ru.gav19770210.javapro.task06.dto.PaymentRequest;
import ru.gav19770210.javapro.task06.dto.PaymentResponse;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final RestClient restClient;
    private final String productBaseUrl;
    private final String productGetByIdUri;
    private final String productGetByUserUri;
    private final String productUpdateUri;

    public PaymentServiceImpl(@Value("${service_url.product.base-url}") String productBaseUrl,
                              @Value("${service_url.product.get-by-user}") String productGetByUserUri,
                              @Value("${service_url.product.get-by-id}") String productGetByIdUri,
                              @Value("${service_url.product.update}") String productUpdateUri) {
        this.productBaseUrl = productBaseUrl;
        this.productGetByIdUri = productGetByIdUri;
        this.productGetByUserUri = productGetByUserUri;
        this.productUpdateUri = productUpdateUri;
        this.restClient = RestClient.builder().baseUrl(this.productBaseUrl).build();
    }

    @Override
    public List<Product> getUserProducts(Long userId) {
        var url = productGetByUserUri.replace("{user_id}", userId.toString());
        var response = restClient
                .get()
                .uri(url)
                .retrieve()
                .body(List.class);

        return response;
    }

    @Override
    public Product getProductById(Long id) {
        var url = productGetByIdUri.replace("{id}", id.toString());
        var response = restClient
                .get()
                .uri(url)
                .retrieve()
                .toEntity(Product.class);

        return response.getBody();
    }

    @Override
    public void availableDebit(Product product, BigDecimal summa) {
        if (product.getBalance().subtract(summa).compareTo(BigDecimal.valueOf(0L)) < 0) {
            throw new IllegalArgumentException("Недостаточно средств на счёте для списания");
        }
    }

    private Product updateProduct(Product product) {
        var response = restClient
                .post()
                .uri(productUpdateUri)
                .body(product)
                .retrieve()
                .toEntity(Product.class);

        return response.getBody();
    }

    @Override
    public PaymentResponse debit(PaymentRequest paymentRequest) {
        var product = getProductById(paymentRequest.getProductId());
        availableDebit(product, paymentRequest.getSumma());
        product.setBalance(product.getBalance().subtract(paymentRequest.getSumma()));
        var productUpdate = updateProduct(product);

        return new PaymentResponse("0", "Платёж выполнен успешно");
    }

    @Override
    public PaymentResponse credit(PaymentRequest paymentRequest) {
        var product = getProductById(paymentRequest.getProductId());
        product.setBalance(product.getBalance().add(paymentRequest.getSumma()));
        var productUpdate = updateProduct(product);

        return new PaymentResponse("0", "Платёж выполнен успешно");
    }
}
