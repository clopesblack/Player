package com.challenge.player.controller.filter;

import com.challenge.player.model.Player;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;


@Component
@RequiredArgsConstructor
public class MDCLogFilter implements Filter {

    private final Player selfPlayer;

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        MDC.put("playerID", selfPlayer.getId());
        chain.doFilter(request, response);
    }
}
