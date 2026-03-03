package site.wtfu.framework.rime.prism;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import site.wtfu.framework.rime.common.Common;
import site.wtfu.framework.rime.common.SpellingType;
import site.wtfu.framework.rime.table.OffsetPtr;

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
public class SpellingDescriptor extends Common<SpellingDescriptor> {

    /**
     * struct SpellingDescriptor {
     *   SyllableId syllable_id;
     *   int32_t type;
     *   Credibility credibility;   // using Credibility = float;
     *   String tips;               // OffsetPtr<char> data;
     * };
     */

    private int syllable_id;
    private SpellingType type;
    private float credibility;
    private OffsetPtr tips;


    @Override
    protected SpellingDescriptor doDecodeBytes(SpellingDescriptor spellingDescriptor, MappedByteBuffer bin) {
        OffsetPtr tips;
        return spellingDescriptor.setSyllable_id(bin.getInt())
                .setType(SpellingType.index(bin.getInt()))
                .setCredibility(bin.getFloat())
                .setTips((tips = new OffsetPtr(bin.position(),bin.getInt())).getPosition() != 0 ? tips : null);
    }
}
