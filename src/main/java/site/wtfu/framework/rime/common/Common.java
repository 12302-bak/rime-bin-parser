package site.wtfu.framework.rime.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

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
@Setter
public abstract class Common <T extends Common<T>> {


    @Data
    @Accessors(chain = true)
    @AllArgsConstructor
    private static class Section{
        private int start;
        private int stop;
    }

    private Section _section;

    public T decodeBytes(MappedByteBuffer bin, int from){
        return decodeBytes(bin, false, from, from);
    }

    public T decodeBytes(MappedByteBuffer bin, boolean reset, int from){
        return decodeBytes(bin, reset, from, from);
    }

    public T decodeBytes(MappedByteBuffer bin, boolean reset, int resetPosition, int from){
        bin.position(from);
        
        @SuppressWarnings("unchecked") T rst = (T)this;
        doDecodeBytes(rst, bin);
        this._section = new Section(from, bin.position());

        if(reset){ bin.position(resetPosition);}
        return rst;
    }

    protected abstract T doDecodeBytes(T t, MappedByteBuffer bin);
}
