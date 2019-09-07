package io.graversen.requestbin.util;

import java.util.UUID;

public class BinUtil
{
    private BinUtil()
    {

    }

    public static String generateBinIdentifier()
    {
        return UUID.randomUUID().toString();
    }
}
