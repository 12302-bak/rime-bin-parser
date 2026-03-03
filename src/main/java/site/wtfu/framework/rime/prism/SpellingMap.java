package site.wtfu.framework.rime.prism;

import lombok.Data;
import lombok.EqualsAndHashCode;
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
 *                          @since 1.0
 *                          @author 12302
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SpellingMap extends Common<SpellingMap> {

    /**
     * using SpellingMap = Array<SpellingMapItem>;
     */

    private int size;
    private List<SpellingMapItem> spellingMapItems;


    @Override
    protected SpellingMap doDecodeBytes(SpellingMap spellingMap, MappedByteBuffer bin) {
        int size = bin.getInt();
        List<SpellingMapItem> spellingMaps = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            SpellingMapItem spellingMapItem = new SpellingMapItem().decodeBytes(bin, bin.position());
            spellingMaps.add(spellingMapItem);
        }
        return spellingMap.setSize(size).setSpellingMapItems(spellingMaps);
    }
}
