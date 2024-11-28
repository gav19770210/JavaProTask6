package ru.gav19770210.javapro.task06.services;

import ru.gav19770210.javapro.task06.dto.PaymentRequest;
import ru.gav19770210.javapro.task06.dto.PaymentResponse;
import ru.gav19770210.javapro.task06.entities.Product;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentService {
    List<Product> getUserProducts(Long userId);

    Product getProductById(Long id);

    void availableDebit(Product product, BigDecimal summa);

    PaymentResponse debit(PaymentRequest paymentRequest);

    PaymentResponse credit(PaymentRequest paymentRequest);
}
