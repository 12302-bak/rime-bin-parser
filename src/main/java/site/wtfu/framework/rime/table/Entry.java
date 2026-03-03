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
public class Entry extends Common<Entry> {

    public String text;
    public float weight;

    @Override
    protected Entry doDecodeBytes(Entry entry, MappedByteBuffer bin) {
        String text = TableMetadata.stringTable.lookup(bin.getInt()).Key;
        float weight = bin.getFloat();
        return entry.setText(text).setWeight(weight);
    }
}
