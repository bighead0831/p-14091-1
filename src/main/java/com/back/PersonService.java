
package com.back;

import org.springframework.stereotype.Service;

@Service  // 생성하는 객체가 단순할때는 Component를 사용하자. (Bean은 생성 객체가 복잡할 때 유용하다)
public class PersonService {
    public long count() {
        return 3;
    }
}