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
 * struct LongEntry {
 *   Code extra_code;
 *   Entry entry;
 * };
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LongEntry extends Common<LongEntry> {


    /*
        Code extra_code;
            size:
            OffsetPtr<int>;
        Entry entry;
            int text;
            float weight;
    */
    public Code extra_code;
    public Entry entry;


    @Override
    protected LongEntry doDecodeBytes(LongEntry longEntry, MappedByteBuffer bin) {
        Code extraCode = new Code().decodeBytes(bin, bin.position());
        Entry entry = new Entry().decodeBytes(bin, bin.position());
        return longEntry.setExtra_code(extraCode).setEntry(entry);
    }
}
