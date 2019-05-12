package com.lex4hex.kalah.game_chain;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class KalahChainTest {

    @Autowired
    private final List<KalahChain> kalahChains = new ArrayList<>();

    @Test
    void testOrder() {
        Assert.assertThat(kalahChains.get(0), CoreMatchers.instanceOf(GameConstraints.class));
        Assert.assertThat(kalahChains.get(1), CoreMatchers.instanceOf(MoveStones.class));
        Assert.assertThat(kalahChains.get(2), CoreMatchers.instanceOf(LastPit.class));
        Assert.assertThat(kalahChains.get(3), CoreMatchers.instanceOf(NextPlayer.class));
        Assert.assertThat(kalahChains.get(4), CoreMatchers.instanceOf(EndGame.class));
    }
}