package site.wtfu.framework.rime.reverse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

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
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class WrapperEntry {

    private int id;
    private String key;
    private String value;
}
