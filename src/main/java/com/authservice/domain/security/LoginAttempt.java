package com.authservice.domain.security;

public class LoginAttempt {
    private int attempts;
    private boolean locked;

    public void increment() {
        this.attempts++;
        if (this.attempts >= 3) {
            this.locked = true;
        }
    }

    public void reset() {
        this.attempts = 0;
        this.locked = false;
    }
}
