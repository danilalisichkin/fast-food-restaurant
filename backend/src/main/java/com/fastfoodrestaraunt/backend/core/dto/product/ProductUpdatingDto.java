package com.fastfoodrestaraunt.backend.core.dto.product;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductUpdatingDto(
        @NotEmpty
        @Size(min = 2, max = 50)
        String name,

        @NotNull
        @Positive
        BigDecimal price,

        @NotNull
        @Positive
        Integer weight,

        String imageUrl,

        @NotEmpty
        @Size(max = 100)
        String description,

        @NotNull
        Long categoryId
) {
}
