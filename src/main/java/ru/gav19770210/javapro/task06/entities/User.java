package ru.gav19770210.javapro.task06.entities;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class User {
    private Long id;
    @NotBlank(message = "name должно быть задано")
    private String name;
}
