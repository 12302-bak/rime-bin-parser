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
 * List<SyllableId>;
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Code extends Common<Code> {


    public int size;
    public String[] syllables;


    @Override
    protected Code doDecodeBytes(Code code, MappedByteBuffer bin) {
        int size = bin.getInt();
        String[] items = new String[size];

        OffsetPtr ep = new OffsetPtr(bin.position(), bin.getInt());
        int ptr = bin.position();
        bin.position(ep.getPosition());
        for (int i = 0; i < size; i++) {
            items[i] = TableMetadata.syllabary.items[bin.getInt()];
        }
        bin.position(ptr);
        return code.setSize(size).setSyllables(items);
    }
}
