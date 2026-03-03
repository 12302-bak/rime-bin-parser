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
@ToString(exclude = {"headIndexNodes"})
public class HeadIndex extends Common<HeadIndex> {

    public int size;
    public List<HeadIndexNode> headIndexNodes;

    private int level;
    public HeadIndex(int level) { this.level = level; }

    @Override
    protected HeadIndex doDecodeBytes(HeadIndex headIndex, MappedByteBuffer bin) {
        int size = bin.getInt();
        List<HeadIndexNode> headIndexNodes = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            String key = TableMetadata.syllabary.items[i];
            HeadIndexNode headIndexNode = new HeadIndexNode(this, key).decodeBytes(bin, bin.position());
            headIndexNodes.add(headIndexNode);
        }
        return headIndex.setSize(size).setHeadIndexNodes(headIndexNodes);
    }
}
