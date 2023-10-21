package io.github.luxotick.impl;

import java.util.Random;

public class Snowflake {
    public static long generateSnowflakeId() {
        long epoch = 1420070400000L;
        long timestamp = System.currentTimeMillis() - epoch;
        long snowflakeId = (timestamp << 22) | (new Random().nextInt(1 << 12) << 10) | new Random().nextInt(1 << 10);
        return snowflakeId;
    }



}
