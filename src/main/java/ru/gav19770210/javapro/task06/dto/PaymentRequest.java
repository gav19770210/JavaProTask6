package ru.gav19770210.javapro.task06.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentRequest {
    @NotNull(message = "productId должен быть задан")
    private Long productId;
    @NotNull
    @Min(value = 0, message = "Сумма добжна быть больше нуля")
    private BigDecimal summa;
}
