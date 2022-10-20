package j2kb.ponicon.scrap.response.validationSequence;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

import static j2kb.ponicon.scrap.response.validationSequence.ValidationGroup.*;

// 왼 -> 오 순서로 적용됨
@GroupSequence({Default.class, NotNullGroup.class, NotEmptyGroup.class, PatternGroup.class, EmailGroup.class})
public interface ValidationSequence {
}
