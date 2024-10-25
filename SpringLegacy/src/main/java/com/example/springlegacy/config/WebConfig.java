package com.example.springlegacy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * 서블릿 스프링 컨테이너의 설정 클래스입니다.
 * 웹 계층의 빈들을 스캔하고 Spring MVC 관련 설정을 담당합니다.
 *
 * <p>다음과 같은 웹 계층의 빈들이 스캔됩니다:</p>
 * <ul>
 *     <li>컨트롤러: 웹 요청을 처리하는 컨트롤러 컴포넌트</li>
 *     <li>인터셉터: 요청/응답을 가로채서 처리하는 인터셉터 컴포넌트</li>
 *     <li>어드바이스: 예외 처리 등을 담당하는 어드바이스 컴포넌트</li>
 * </ul>
 *
 * <p>비즈니스 계층의 컴포넌트(Service, Repository 등)는
 * {@link RootConfig}에서 스캔됩니다.</p>
 *
 * @author SAHONG PAK
 * @version 1.0
 * @see Configuration
 * @see EnableWebMvc
 * @see ComponentScan
 * @see RootConfig
 */
@Configuration
@EnableWebMvc  // Spring MVC 활성화
@ComponentScan(basePackages = {
        "com.example.springlegacy.*.controller",
        "com.example.springlegacy.common.advice",
        "com.example.springlegacy.common.interceptor"
})
public class WebConfig implements WebMvcConfigurer {

    /**
     * JSP를 뷰로 사용하기 위한 ViewResolver를 설정합니다.
     *
     * @return JSP 뷰 리졸버
     */
    @Bean
    public InternalResourceViewResolver viewResolver() {

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }

    /**
     * 특정 URL에 대한 기본 뷰 컨트롤러를 설정합니다.
     * "/" 경로를 "index"로 매핑하여 index.jsp를 표시합니다.
     * MainController를 사용하지 않아도 됩니다.
     *
     * @param registry ViewControllerRegistry 객체
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
    }

    /**
     * 정적 리소스(이미지, CSS, JS 등)를 처리하기 위한 핸들러를 설정합니다.
     * /resources/** 패턴의 요청을 /resources/ 디렉토리에서 처리합니다.
     *
     * @param registry ResourceHandlerRegistry 객체
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }

    /**
     * 파일 업로드를 처리하기 위한 MultipartResolver를 설정합니다.
     * StandardServletMultipartResolver를 사용하여 서블릿 3.0의 파일 업로드를 지원합니다.
     *
     * @return 멀티파트 리졸버
     */
    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }
}
