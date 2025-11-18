package com.schedule.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schedule.common.enums.ExceptionCode;
import com.schedule.common.exception.CustomException;
import com.schedule.common.model.CommonResponse;
import com.schedule.domain.user.model.dto.SessionUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    /**
     * http 요청마다 호출되는 필터
     * @param request HttpServletRequest 요청
     * @param response HttpServletResponse 응답
     * @param filterChain 다음 필터로 전달할 체인
     * @throws ServletException 서블릿 예외
     * @throws IOException 입출력 예외
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException { //요청과 응답을

        //1. 세션 검사가 필요없는 경로 확인 false면 세션 검사 필요
        boolean requiredSession = requiredSession(request);

        //2. 세션 검사가 필요한 경우
        if (requiredSession) {

            //3. 세션 조회
            HttpSession session = request.getSession(false);

            if (session != null) {

                //4. 세션에서 loginUser 속성을 꺼냄
                SessionUser loginUser = (SessionUser) session.getAttribute("loginUser");

                if (loginUser != null) {

                    log.info("CustomFilter doFilterInternal login  id : {} email {}", loginUser.getId(), loginUser.getEmail());

                    //다음 요청으로 넘어갈 수 있게 사용하는 메서드
                    filterChain.doFilter(request, response);
                    return;
                }
            }

            //5. 세션이 없거나 loginUser가 없으면 응답을 만들어서 줘야함
            CustomException exception = new CustomException(ExceptionCode.FORBIDDEN);
            CommonResponse<String> result = new CommonResponse<>(exception.getExceptionCode().getStatus(), exception.getMessage());

            //6. 응답 설정 : HttpStatus, ContentType 및 인코딩
            response.setStatus(result.getCode());
            response.setContentType("application/json; charset=UTF-8");

            //7. object -> json으로 직렬화
            String json = objectMapper.writeValueAsString(result);

            //8. 응답에 json 작성
            response.getWriter().write(json);
            return;
        }

        //다음 요청으로 넘어갈 수 있게 사용하는 메서드
        filterChain.doFilter(request, response);
    }

    /**
     * 요청 uri에서 세션이 필요한지 확인하는 기능
     * @param request HttpServletRequest 요청
     * @return 필요하면 true, 필요없으면 false
     */
    private boolean requiredSession(HttpServletRequest request) {

        //요청 URI
        String uri = request.getRequestURI();
        String method = request.getMethod();

        //세션 확인이 필요없는 곳
        if (uri.startsWith("/signup") || uri.startsWith("/login") ||
                ("GET".equals(method))) return false;

        return true;
    }
}
