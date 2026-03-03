package site.wtfu.framework.rime;

import jp.darts.DoubleArrayNative;
import jp.marisa.Marisa;
import org.junit.Test;
import site.wtfu.framework.rime.prism.PrismMetadata;
import site.wtfu.framework.rime.prism.SpellingMap;
import site.wtfu.framework.rime.reverse.Index;
import site.wtfu.framework.rime.reverse.ReverseMetadata;
import site.wtfu.framework.rime.table.HeadIndex;
import site.wtfu.framework.rime.table.Syllabary;
import site.wtfu.framework.rime.table.TableMetadata;
import site.wtfu.framework.rime.utils.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class BinTest {

    @Test
    public void testCommon() {
        Marisa marisa = new Marisa();
        try {
            List<String> keyset = new ArrayList<String>();
            keyset.add("a");
            keyset.add("app");
            keyset.add("apple");
            marisa.build(keyset);
            final String query = "apple";
            List<Marisa.IdKeyPair> results = marisa.commonPrefixSearch(query);
            if (results == null) {
                System.out.println("not found: " + query);
            } else {
                for (Marisa.IdKeyPair result : results) {
                    System.out.println(result.Key + ": " + result.Id);
                }
            }
        } finally {
            marisa.close();
        }
    }

    @Test
    public void testSave() {
        File workFile = new File("/Users/stevenobelia/Documents/project_idea_test/marisa-java/src/test/resources/numbers.table.bin");
        //workFile = new File("/Users/stevenobelia/Documents/project_idea_test/marisa-java/src/test/resources/luna_pinyin.table.bin");

        Marisa marisa2 = new Marisa();
        marisa2.load(workFile.getAbsolutePath());
        System.out.println(marisa2.size());

        for (long i = 0; i < marisa2.size(); i++) {
            System.out.println(marisa2.lookup(i));
        }
    }


    @Test
    public void testParseTable() throws Exception {
        String binFilePath = "/Users/stevenobelia/Documents/project_clion_test/librime/cmake-build-debug/bin/build/numbers.table.bin";
        //binFilePath = "/Users/stevenobelia/Documents/project_clion_test/librime/cmake-build-debug/bin/build/luna_pinyin.table.bin";
        FileChannel fileChannel = new FileInputStream(binFilePath).getChannel();
        MappedByteBuffer bin = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());
        bin.order(ByteOrder.LITTLE_ENDIAN);


        TableMetadata metadata = new TableMetadata().decodeBytes(bin, 0);
        Syllabary syllabary = new Syllabary().decodeBytes(bin, metadata.syllabary_offset.getPosition());
        HeadIndex headIndex = new HeadIndex(1).decodeBytes(bin, metadata.index_offset.getPosition());
        System.out.println();
    }


    @Test
    public void testParseReverse() throws Exception {
        String binFilePath = "/Users/stevenobelia/Documents/project_clion_test/librime/cmake-build-debug/bin/build/numbers.reverse.bin";
        //binFilePath = "/Users/stevenobelia/Documents/project_clion_test/librime/cmake-build-debug/bin/build/luna_pinyin.reverse.bin";
        FileChannel fileChannel = new FileInputStream(binFilePath).getChannel();
        MappedByteBuffer bin = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());
        bin.order(ByteOrder.LITTLE_ENDIAN);


        ReverseMetadata metadata = new ReverseMetadata().decodeBytes(bin, 0);
        Index indies = new Index(metadata.index_size).decodeBytes(bin, metadata.getIndex_offset().getPosition());
        System.out.println();
    }


    @Test
    public void testParsePrism() throws Exception {
        String binFilePath = "/Users/stevenobelia/Documents/project_clion_test/librime/cmake-build-debug/bin/build/numbers.prism.bin";
        //binFilePath = "/Users/stevenobelia/Documents/project_clion_test/librime/cmake-build-debug/bin/build/luna_pinyin.reverse.bin";
        FileChannel fileChannel = new FileInputStream(binFilePath).getChannel();
        MappedByteBuffer bin = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());
        bin.order(ByteOrder.LITTLE_ENDIAN);


        PrismMetadata metadata = new PrismMetadata().decodeBytes(bin, 0);
        SpellingMap spellingMap = new SpellingMap().decodeBytes(bin, metadata.spelling_map.getPosition());


        bin.position(metadata.double_array.getPosition());
        byte[] doubleArrayByte = new byte[metadata.getDouble_array_size() * 4];
        bin.get(doubleArrayByte);
        DoubleArrayNative trie = new DoubleArrayNative();
        int open = trie.open(IOUtils.writeByteToFile(doubleArrayByte).getBytes());
        int yi = trie.exactMatchSearch("yi");
        System.out.println("exact match search result: " + yi);
    }
}
