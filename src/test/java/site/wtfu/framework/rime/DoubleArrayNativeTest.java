package site.wtfu.framework.rime;

import jp.darts.DoubleArrayNative;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 *
 * Copyright https://wtfu.site Inc. All Rights Reserved.
 *
 * @date 2025/4/16
 *                          @since  1.0
 *                          @author 12302
 *
 */
public class DoubleArrayNativeTest {

    private static final String bin = "target" + File.separator + "darts.bin";

    public DoubleArrayNative build() {
        String[] keys = {"e", "er", "h", "hao", "here", "y", "yi"};
        DoubleArrayNative doubleArrayNative = new DoubleArrayNative();
        int build = doubleArrayNative.build(keys.length, keys);
        if(build == 0){ return doubleArrayNative; }
        return null;
    }

    public DoubleArrayNative save() {
        DoubleArrayNative doubleArrayNative = build();
        if(doubleArrayNative == null) { return null; }
        int save = doubleArrayNative.save(bin.getBytes());
        if(save == 0){ return doubleArrayNative; }
        return null;
    }

    public DoubleArrayNative open() {
        DoubleArrayNative doubleArrayNative = save();
        if(doubleArrayNative == null) {return  null;}
        int open = doubleArrayNative.open(bin.getBytes());
        if(open == 0){ return doubleArrayNative; }
        return null;
    }


    @Test
    public void exactMatchSearch() {
        DoubleArrayNative doubleArrayNative = open();
        Assert.assertNotNull(doubleArrayNative);
        int h = doubleArrayNative.exactMatchSearch("h");
        Assert.assertEquals(2, h);
    }

    @Test
    public void commonPrefixSearch() {
        DoubleArrayNative doubleArrayNative = open();
        Assert.assertNotNull(doubleArrayNative);
        DoubleArrayNative.Match[] hs = doubleArrayNative.commonPrefixSearch("yi", 1);
        System.out.println();
        Assert.assertEquals(6, hs[0].length + hs[0].value);
        Assert.assertEquals(0, hs[1].length + hs[1].value);
    }
}