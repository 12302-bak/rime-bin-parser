package site.wtfu.framework.rime.prism;

import jp.darts.DoubleArrayNative;
//import jp.dartsclone.DoubleArray;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import site.wtfu.framework.rime.common.Common;
import site.wtfu.framework.rime.common.ConstVal;
import site.wtfu.framework.rime.table.OffsetPtr;
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
public class PrismMetadata extends Common<PrismMetadata> {

    //public static DoubleArray trie = null;
    public static DoubleArrayNative trie = null;

    /*
        struct Metadata {
          static const int kFormatMaxLength = 32;
          char format[kFormatMaxLength];
          uint32_t dict_file_checksum;
          uint32_t schema_file_checksum;
          uint32_t num_syllables;
          uint32_t num_spellings;
          uint32_t double_array_size;
          OffsetPtr<char> double_array;
          // v1.0
          OffsetPtr<SpellingMap> spelling_map;
          char alphabet[256];
        };
    */

    public char[] format;
    public int dict_file_checksum;
    public int schema_file_checksum;
    public int num_syllables;
    public int num_spellings;
    public int double_array_size;
    public OffsetPtr double_array;
    public OffsetPtr spelling_map;
    public char[] alphabet;


    @Override
    protected PrismMetadata doDecodeBytes(PrismMetadata metadata, MappedByteBuffer bin) {
        byte[] bytes = new byte[316];
        bin.get(bytes);
        ByteBuffer buffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);

        byte[] formatByte = new byte[ConstVal.FORMAT_MAX_LENGTH]; buffer.get(formatByte);
        format = new String(formatByte).toCharArray();
        dict_file_checksum = buffer.getInt();
        schema_file_checksum = buffer.getInt();
        num_syllables = buffer.getInt();
        num_spellings = buffer.getInt();
        double_array_size = buffer.getInt();
        double_array = new OffsetPtr(buffer.position(), buffer.getInt());
        spelling_map = new OffsetPtr(buffer.position(), buffer.getInt());
        byte[] alphabetByte = new byte[256]; buffer.get(alphabetByte);
        alphabet = new String(alphabetByte).toCharArray();

        bin.position(double_array.getPosition());
        byte[] doubleArrayByte = new byte[double_array_size * 4];
        bin.get(doubleArrayByte);
        //trie = new DoubleArray();
        trie = new DoubleArrayNative();
        //trie.open(ByteBuffer.wrap(doubleArrayByte).order(ByteOrder.LITTLE_ENDIAN));
        trie.open(IOUtils.writeByteToFile(doubleArrayByte).getBytes());

        return metadata;
    }
}
