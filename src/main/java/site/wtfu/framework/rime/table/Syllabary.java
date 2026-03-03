package site.wtfu.framework.rime.table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import site.wtfu.framework.rime.common.Common;

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
public class Syllabary extends Common<Syllabary> {

    public int size;
    public String[] items;

    @Override
    protected Syllabary doDecodeBytes(Syllabary syllabary, MappedByteBuffer bin) {
        int size = bin.getInt();
        String[] items = new String[size];
        for (int i = 0; i < size; i++) {
            items[i] = TableMetadata.stringTable.lookup(bin.getInt()).Key;
        }
        syllabary.setSize(size).setItems(items);
        TableMetadata.syllabary = syllabary;
        return syllabary;
    }
}
