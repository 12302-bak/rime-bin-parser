package site.wtfu.framework.rime.table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import site.wtfu.framework.rime.common.Common;
import site.wtfu.framework.rime.common.ConstVal;

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
 *
 *
 * struct TrunkIndexNode {
 *   SyllableId key;
 *   List<Entry> entries;
 *   OffsetPtr<PhraseIndex> next_level;
 * };
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString(exclude = {"next_level", "parent"})
public class TrunkIndexNode extends Common<TrunkIndexNode> {


    /*
        SyllableId key;
            int key;
        List<Entry> entries;
            size:
            OffsetPtr<Entry>;
        OffsetPtr<PhraseIndex> next_level;
    */
    public String key;
    public List<Entry> entries;
    public PhraseIndex next_level;


    private TrunkIndex parent;
    public TrunkIndexNode(TrunkIndex parent){ this.parent = parent; }

    @Override
    protected TrunkIndexNode doDecodeBytes(TrunkIndexNode trunkIndexNode, MappedByteBuffer bin) {
        String key = TableMetadata.syllabary.items[bin.getInt()];
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
        PhraseIndex phraseIndex;
        if (parent.getLevel() < ConstVal.kIndexCodeMaxLength){
            phraseIndex = np.getPosition() != 0 ? new TrunkIndex(parent.getLevel() + 1).decodeBytes(bin,true, bin.position(), np.getPosition()) : null;
        }else{
            phraseIndex = np.getPosition() != 0 ? new TailIndex(parent.getLevel() + 1).decodeBytes(bin, true,bin.position(), np.getPosition()) : null;
        }
        return trunkIndexNode.setKey(key).setEntries(entries).setNext_level(phraseIndex);
    }
}