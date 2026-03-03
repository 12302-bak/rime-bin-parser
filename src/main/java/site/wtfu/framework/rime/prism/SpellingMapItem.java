package site.wtfu.framework.rime.prism;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import site.wtfu.framework.rime.common.Common;
import site.wtfu.framework.rime.table.OffsetPtr;

import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Copyright https://wtfu.site Inc. All Rights Reserved.
 *
 * @date 2025/4/1
 *                          @since 1.0
 *                          @author 12302
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SpellingMapItem extends Common<SpellingMapItem> {

    /**
     * using SpellingMapItem = List<SpellingDescriptor>;
     */

    private int size;
    private List<SpellingDescriptor> spellingDescriptors;


    @Override
    protected SpellingMapItem doDecodeBytes(SpellingMapItem spellingMapItem, MappedByteBuffer bin) {
        int size = bin.getInt();
        int op = new OffsetPtr(bin.position(), bin.getInt()).getPosition();
        List<SpellingDescriptor> spellingDescriptors = new ArrayList<>();
        int next = bin.position();
        bin.position(op);
        for (int i = 0; i < size; i++) {
            SpellingDescriptor spellingDescriptor = new SpellingDescriptor().decodeBytes(bin, bin.position());
            spellingDescriptors.add(spellingDescriptor);
        }
        bin.position(next);
        return spellingMapItem.setSize(size).setSpellingDescriptors(spellingDescriptors);
    }
}
