package site.wtfu.framework.rime.reverse;

import jp.marisa.Marisa;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import site.wtfu.framework.rime.common.Common;
import site.wtfu.framework.rime.common.ConstVal;
import site.wtfu.framework.rime.table.OffsetPtr;
import site.wtfu.framework.rime.table.Syllabary;
import site.wtfu.framework.rime.utils.IOUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;

/**
 *
 * Copyright https://wtfu.site Inc. All Rights Reserved.
 *
 * @date 2025/4/1
 *                          @since  1.0
 *                          @author 12302
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ReverseMetadata extends Common<ReverseMetadata> {

    public static Marisa keyTrieStringTable = null;
    public static Marisa valueTrieStringTable = null;
    public static Syllabary syllabary = null;

    public char[] format;
    public int dict_file_checksum;
    public OffsetPtr dict_settings;
    // List<StringId> index;
    public int index_size;
    public OffsetPtr index_offset;
    public OffsetPtr key_trie;
    public int key_trie_size;
    public OffsetPtr value_trie;
    public int value_trie_size;


    @Override
    protected ReverseMetadata doDecodeBytes(ReverseMetadata metadata, MappedByteBuffer bin) {
        byte[] bytes = new byte[ConstVal.METADATA_SIZE];
        bin.get(bytes);
        ByteBuffer buffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);

        byte[] formatByte = new byte[ConstVal.FORMAT_MAX_LENGTH]; buffer.get(formatByte);
        format = new String(formatByte).toCharArray();
        dict_file_checksum = buffer.getInt();
        dict_settings = new OffsetPtr(buffer.position(), buffer.getInt());
        index_size = buffer.getInt();
        index_offset = new OffsetPtr(buffer.position(), buffer.getInt());
        key_trie = new OffsetPtr(buffer.position(), buffer.getInt());
        key_trie_size = buffer.getInt();
        value_trie = new OffsetPtr(buffer.position(), buffer.getInt());
        value_trie_size = buffer.getInt();



        bin.position(key_trie.getPosition());
        byte[] keyStringTableByte = new byte[key_trie_size];
        bin.get(keyStringTableByte);
        Marisa marisa = new Marisa();
        marisa.load(IOUtils.writeByteToFile(keyStringTableByte));
        keyTrieStringTable = marisa;


        bin.position(value_trie.getPosition());
        byte[] valueStringTableByte = new byte[value_trie_size];
        bin.get(valueStringTableByte);
        marisa = new Marisa();
        marisa.load(IOUtils.writeByteToFile(valueStringTableByte));
        valueTrieStringTable = marisa;

        return metadata;
    }


}
