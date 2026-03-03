package site.wtfu.framework.rime.reverse;

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
@ToString(exclude = {"items"})
public class Index extends Common<Index> {

    public int size;
    public List<WrapperEntry> items;

    public Index(int size) { this.size = size; }

    @Override
    protected Index doDecodeBytes(Index index, MappedByteBuffer bin) {
        List<WrapperEntry> items = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int id = bin.getInt();
            String key = ReverseMetadata.keyTrieStringTable.lookup(i).Key;
            String value = ReverseMetadata.valueTrieStringTable.lookup(id).Key;
            items.add(WrapperEntry.builder().id(id).key(key).value(value).build());
        }
        return index.setItems(items);
    }
}
