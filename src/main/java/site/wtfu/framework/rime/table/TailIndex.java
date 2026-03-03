package site.wtfu.framework.rime.table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import site.wtfu.framework.rime.common.Common;

import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Copyright https://wtfu.site Inc. All Rights Reserved.
 *
 * @date 2025/4/1
 *                          @since  1.0
 *                          @author 12302
 * Array<LongEntry>;
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString(exclude = {"nodes"})
public class TailIndex extends Common<TailIndex> implements PhraseIndex {


    public int size;
    public List<LongEntry> nodes = new ArrayList<>();

    private int level;
    public TailIndex(int level){ this.level = level; }


    @Override
    protected TailIndex doDecodeBytes(TailIndex tailIndex, MappedByteBuffer bin) {
        int size = bin.getInt();
        List<LongEntry> nodes = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            LongEntry longEntry = new LongEntry().decodeBytes(bin, bin.position());
            nodes.add(longEntry);
        }
        return tailIndex.setSize(size).setNodes(nodes);
    }
}
