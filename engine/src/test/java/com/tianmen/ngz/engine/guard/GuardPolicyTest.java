package com.tianmen.ngz.engine.guard;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GuardPolicyTest {

    @Test
    public void decidesExitWhenExitOnHit() {
        GuardPolicy policy = new GuardPolicy(new GuardConfig.Builder()
                .checkRoot(true)
                .exitOnHit(true)
                .build());
        assertEquals(GuardPolicy.Decision.EXIT, policy.decide(GuardHit.ROOT));
    }

    @Test
    public void decidesLogWhenExitOnHitFalse() {
        GuardPolicy policy = new GuardPolicy(new GuardConfig.Builder()
                .checkRoot(true)
                .exitOnHit(false)
                .build());
        assertEquals(GuardPolicy.Decision.LOG, policy.decide(GuardHit.ROOT));
    }

    @Test
    public void ignoresDisabledCheck() {
        GuardPolicy policy = new GuardPolicy(new GuardConfig.Builder()
                .checkRoot(false)
                .exitOnHit(true)
                .build());
        assertEquals(GuardPolicy.Decision.IGNORE, policy.decide(GuardHit.ROOT));
    }

    @Test
    public void expiryRespectsCustomTime() {
        long past = System.currentTimeMillis() - 60_000;
        GuardPolicy expired = new GuardPolicy(new GuardConfig.Builder()
                .checkExpiry(true)
                .expiryEpochMillis(past)
                .exitOnHit(true)
                .build());
        assertEquals(GuardPolicy.Decision.EXIT, expired.decide(GuardHit.EXPIRY));

        long future = System.currentTimeMillis() + 60_000;
        GuardPolicy notExpired = new GuardPolicy(new GuardConfig.Builder()
                .checkExpiry(true)
                .expiryEpochMillis(future)
                .exitOnHit(true)
                .build());
        assertEquals(GuardPolicy.Decision.IGNORE, notExpired.decide(GuardHit.EXPIRY));
    }
}
