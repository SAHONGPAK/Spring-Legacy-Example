package com.example.springlegacy.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * 루트 스프링 컨테이너의 설정 클래스입니다.
 * 웹 계층을 제외한 나머지 계층의 빈들을 스캔하고 등록합니다.
 *
 * <p>다음과 같은 계층의 빈들이 스캔됩니다:</p>
 * <ul>
 *     <li>서비스 계층: 비즈니스 로직을 처리하는 서비스 컴포넌트</li>
 *     <li>레포지토리 계층: 데이터 액세스를 담당하는 레포지토리 컴포넌트</li>
 *     <li>AOP 계층: 공통 관심사를 처리하는 AOP 컴포넌트</li>
 *     <li>유틸리티: 공통 기능을 제공하는 유틸리티 컴포넌트</li>
 * </ul>
 *
 * <p>웹 관련 컴포넌트(Controller, Interceptor, Advice 등)는
 * {@link WebConfig}에서 스캔됩니다.</p>
 *
 * @author SAHONG PAK
 * @version 1.0
 * @see Configuration
 * @see ComponentScan
 * @see WebConfig
 */
@Configuration
@ComponentScan(basePackages = {
        "com.example.springlegacy.*.service",
        "com.example.springlegacy.*.repository",
        "com.example.springlegacy.common.aop",
        "com.example.springlegacy.common.util"
})
public class RootConfig {
}
