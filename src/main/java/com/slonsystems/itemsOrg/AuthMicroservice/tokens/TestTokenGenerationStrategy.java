package com.slonsystems.itemsOrg.AuthMicroservice.tokens;

import java.util.concurrent.atomic.AtomicLong;

public class TestTokenGenerationStrategy implements TokenGenerationStrategy {
    private AtomicLong counter = new AtomicLong(0);
    @Override
    public String getUniqToken() {
        return String.valueOf(counter.incrementAndGet());
    }
}
