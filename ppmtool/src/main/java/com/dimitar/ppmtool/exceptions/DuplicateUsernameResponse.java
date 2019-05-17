package com.dimitar.ppmtool.exceptions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DuplicateUsernameResponse {
    private String username;

    public DuplicateUsernameResponse(final String message) {
        this.username = message;
    }
}
