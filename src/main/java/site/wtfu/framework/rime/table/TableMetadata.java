package site.wtfu.framework.rime.table;

import jp.marisa.Marisa;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import site.wtfu.framework.rime.common.Common;
import site.wtfu.framework.rime.common.ConstVal;
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
public class TableMetadata extends Common<TableMetadata> {

    public static Marisa stringTable = null;
    public static Syllabary syllabary = null;




    public char[] format = new char[ConstVal.FORMAT_MAX_LENGTH];
    public int dict_file_checksum;
    public int num_syllables;
    public int num_entries;
    public OffsetPtr syllabary_offset;
    public OffsetPtr index_offset;
    public int reserved_1;
    public int reserved_2;
    public OffsetPtr string_table_offset;
    public int string_table_size;


    @Override
    protected TableMetadata doDecodeBytes(TableMetadata metadata, MappedByteBuffer bin) {
        byte[] bytes = new byte[ConstVal.METADATA_SIZE];
        bin.get(bytes);
        ByteBuffer buffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);

        byte[] formatByte = new byte[ConstVal.FORMAT_MAX_LENGTH]; buffer.get(formatByte);
        format = new String(formatByte).toCharArray();
        dict_file_checksum = buffer.getInt();
        num_syllables = buffer.getInt();
        num_entries = buffer.getInt();
        syllabary_offset = new OffsetPtr(buffer.position(), buffer.getInt());
        index_offset = new OffsetPtr(buffer.position(), buffer.getInt());
        reserved_1 = buffer.getInt();
        reserved_2 = buffer.getInt();
        string_table_offset = new OffsetPtr(buffer.position(), buffer.getInt());
        string_table_size = buffer.getInt();

        bin.position(string_table_offset.getPosition());
        byte[] stringTableByte = new byte[string_table_size];
        bin.get(stringTableByte);
        Marisa marisa = new Marisa();
        marisa.load(IOUtils.writeByteToFile(stringTableByte));
        stringTable = marisa;
        return metadata;
    }
}
