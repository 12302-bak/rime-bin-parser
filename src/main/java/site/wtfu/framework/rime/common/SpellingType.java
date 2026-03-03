package site.wtfu.framework.rime.common;
/**
 *
 * Copyright https://wtfu.site Inc. All Rights Reserved.
 *
 * @date 2025/4/1
 *                          @since  1.0
 *                          @author 12302
 *
 */
public enum SpellingType {

    // "-ac?!"[s.properties.type]
    kNormalSpelling,    // -
    kFuzzySpelling,     // a
    kAbbreviation,      // c
    kCompletion,        // ?
    kAmbiguousSpelling, // !
    kInvalidSpelling;

    public static SpellingType index(int index){
        return values()[index];
    }
}
