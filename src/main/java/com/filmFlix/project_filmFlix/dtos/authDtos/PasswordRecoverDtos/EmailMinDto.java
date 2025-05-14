package com.filmFlix.project_filmFlix.dtos.authDtos.PasswordRecoverDtos;

import jakarta.validation.constraints.Email;

public record EmailMinDto(@Email String email) {
}
