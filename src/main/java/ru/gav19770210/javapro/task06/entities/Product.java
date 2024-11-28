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
    @NotNull(message = "Поле <userId> должно быть заполнено")
    private Long userId;
    @NotBlank(message = "Поле <accountNumber> должно быть заполнено")
    private String accountNumber;
    @NotNull(message = "Поле <balance> должно быть заполнено")
    @Min(value = 0, message = "Значение поля <balance> должно быть больше нуля")
    private BigDecimal balance;
    @NotNull(message = "Поле <type> должно быть заполнено")
    private ProductType type;
}
