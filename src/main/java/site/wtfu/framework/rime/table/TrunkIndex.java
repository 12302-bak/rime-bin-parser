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
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString(exclude = {"nodes"})
public class TrunkIndex extends Common<TrunkIndex> implements PhraseIndex{


    public int size;
    public List<TrunkIndexNode> nodes = new ArrayList<>();


    private int level;
    public TrunkIndex(int level){ this.level = level; }


    @Override
    protected TrunkIndex doDecodeBytes(TrunkIndex trunkIndex, MappedByteBuffer bin) {
        int size = bin.getInt();
        List<TrunkIndexNode> nodes = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            TrunkIndexNode trunkIndexNode = new TrunkIndexNode(this).decodeBytes(bin, bin.position());
            nodes.add(trunkIndexNode);
        }
        return trunkIndex.setSize(size).setNodes(nodes);
    }
}