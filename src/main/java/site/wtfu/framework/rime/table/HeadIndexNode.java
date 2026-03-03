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
@ToString(exclude = {"parent"})
public class HeadIndexNode extends Common<HeadIndexNode> {


    /*
        List<Entry> entries;
            int size,
            OffsetPtr<Entry>
        OffsetPtr<PhraseIndex> next_level;
    */
    public List<Entry> entries;
    public PhraseIndex next_level;


    /**
     * dummy field
     */
    private String key;
    private HeadIndex parent;
    public HeadIndexNode(HeadIndex parent, String key){
        this.parent = parent;
        this.key = key;
    }


    @Override
    protected HeadIndexNode doDecodeBytes(HeadIndexNode headIndexNode, MappedByteBuffer bin) {
        int size = bin.getInt();
        List<Entry> entries = new ArrayList<>();

        OffsetPtr ep = new OffsetPtr(bin.position(), bin.getInt());
        int next_level = bin.position();
        bin.position(ep.getPosition());
        for (int i = 0; i < size; i++) {
            Entry entry = new Entry().decodeBytes(bin, bin.position());
            entries.add(entry);
        }

        bin.position(next_level);
        OffsetPtr np = new OffsetPtr(bin.position(), bin.getInt());
        PhraseIndex phraseIndex = null;
        if (np.getPosition() != 0){
            phraseIndex = new TrunkIndex(parent.getLevel()+ 1).decodeBytes(bin, true, bin.position(), np.getPosition());
        }

        return headIndexNode.setEntries(entries).setNext_level(phraseIndex);
    }
}
