package site.wtfu.framework.rime.table;

import lombok.Data;

/**
 *
 * Copyright https://wtfu.site Inc. All Rights Reserved.
 *
 * @date 2025/4/12
 *                          @since  1.0
 *                          @author 12302
 *
 */
@Data
public class OffsetPtr {

    private int cursor;
    private int offset;
    private int position;

    public OffsetPtr(int cursor, int offset) {
        this.cursor = cursor;
        this.offset = offset;
        this.position = cursor + offset;

        if(offset == 0){ this.position = 0;}
    }
}
