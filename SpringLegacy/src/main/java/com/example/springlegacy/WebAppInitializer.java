package com.example.springlegacy;

import com.example.springlegacy.config.RootConfig;
import com.example.springlegacy.config.WebConfig;
import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import java.io.File;

/**
 * Spring Web MVC 애플리케이션의 초기화를 담당하는 클래스입니다.
 * web.xml을 대체하여 Java Config 기반으로 웹 애플리케이션을 초기화합니다.
 *
 * <p>AbstractAnnotationConfigDispatcherServletInitializer를 상속받아
 * DispatcherServlet과 ContextLoaderListener를 생성하고 초기화합니다.</p>
 *
 * @author SAHONG PAK
 * @version 1.0
 * @see AbstractAnnotationConfigDispatcherServletInitializer
 * @see RootConfig
 * @see WebConfig
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * 루트 스프링 컨테이너에서 사용할 설정 클래스들을 지정합니다.
     * 주로 데이터베이스, 트랜잭션 등 비웹 설정을 담당합니다.
     *
     * @return RootConfig.class를 포함하는 설정 클래스 배열
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {RootConfig.class};
    }

    /**
     * 서블릿 스프링 컨테이너에서 사용할 설정 클래스들을 지정합니다.
     * ViewResolver, Interceptor 등 웹 관련 설정을 담당합니다.
     *
     * @return WebConfig.class를 포함하는 설정 클래스 배열
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {WebConfig.class};
    }

    /**
     * DispatcherServlet이 매핑될 URL 패턴을 지정합니다.
     * "/"를 지정하여 모든 요청이 DispatcherServlet으로 전달되도록 설정합니다.
     *
     * @return URL 매핑 패턴 배열
     */
    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

    /**
     * 파일 업로드를 위한 Multipart 설정을 커스터마이징합니다.
     * 업로드 디렉토리 생성 및 파일 크기 제한 등을 설정합니다.
     *
     * @param registration ServletRegistration.Dynamic 객체
     * @see MultipartConfigElement
     */
    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {

        File uploadDirectory = new File("./resources/upload/");
        if (!uploadDirectory.exists()) {
            uploadDirectory.mkdirs();
        }

        MultipartConfigElement multipartConfig = new MultipartConfigElement(
                uploadDirectory.getAbsolutePath(), // 업로드 디렉토리 위치
                20971520,  // 최대 파일 크기 (20MB)
                41943040,  // 최대 요청 크기 (40MB)
                20971520   // 파일 크기 임계값 (20MB)
        );

        registration.setMultipartConfig(multipartConfig);
    }
}
