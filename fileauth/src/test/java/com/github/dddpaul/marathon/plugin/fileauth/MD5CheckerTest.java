package com.github.dddpaul.marathon.plugin.fileauth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MD5CheckerTest {

    @Test
    void test() {
        MD5Checker checker = MD5Checker.getInstance();
        assertTrue(checker.check("abc", "qwe"));
        assertFalse(checker.check("abc", "qwe1"));
        assertFalse(checker.check("abc1", "qwe"));
    }
}
