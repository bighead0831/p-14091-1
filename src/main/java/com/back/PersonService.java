
package com.back;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service // 생성하는 객체가 단순할때는 Component를 사용하자. (Bean은 생성 객체가 복잡할 때 유용하다)
@RequiredArgsConstructor
@Validated
public class PersonService {
    private final PersonRepository personRepository;

    @Transactional
    public long count() {
        return personRepository.count();
    }
}