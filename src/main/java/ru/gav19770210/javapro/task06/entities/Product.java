package ru.gav19770210.javapro.task06.entities;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Product {
    private Long id;
    @NotNull(message = "userId должен быть задан")
    private Long userId;
    @NotBlank(message = "accountNumber должен быть задан")
    private String accountNumber;
    @NotNull
    @Min(value = 0, message = "Сумма должна быть больше нуля")
    private BigDecimal balance;
    @NotNull(message = "type должен быть задан")
    private ProductType type;
}
