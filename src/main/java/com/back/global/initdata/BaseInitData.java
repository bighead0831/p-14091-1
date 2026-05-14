package com.back.global.initdata;

import com.back.domain.wiseSaying.wiseSaying.service.WiseSayingService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BaseInitData {
    private final WiseSayingService wiseSayingService;

    @Bean
    ApplicationRunner baseInitDataApplicationRunner() {
        return args -> {
            if (wiseSayingService.count() > 0) return;

            wiseSayingService.write("명언 1", "작가 1");
            wiseSayingService.write("명언 2", "작가 2");
            wiseSayingService.write("명언 3", "작가 3");
            wiseSayingService.write("명언 4", "작가 4");
            wiseSayingService.write("""
                    - 규칙적인 식사를 해라
                    - 규칙적인 운동을 해라
                    
                    ### 귀여운 강아지 사진
                    ![img](https://picsum.photos/id/237/200/300)
                    """.stripIndent(), "작가 5");
        };
    }
}