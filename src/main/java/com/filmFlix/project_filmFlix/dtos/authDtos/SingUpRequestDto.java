package com.filmFlix.project_filmFlix.dtos.authDtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SingUpRequestDto(@NotBlank String name, @Size(min = 5, max = 15) @NotBlank String password, @Email @NotBlank String email ){

}
