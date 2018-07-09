package filters;

import org.junit.Test;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthFilterTest {

    @Test
    public void whenURISignInThanContinueFilter() throws ServletException, IOException {
        AuthFilter filter = new AuthFilter();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        when(request.getRequestURI()).thenReturn("/crud/sign_in");
        filter.doFilter(request, response, filterChain);
        verify(filterChain, times(1)).doFilter(request, response);
    }


    @Test
    public void whenURINotSignInAndLoginNotNullThanNoRedirect() throws ServletException, IOException {
        AuthFilter filter = new AuthFilter();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        HttpSession session  = mock(HttpSession.class);
        when(request.getRequestURI()).thenReturn("/crud");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("login")).thenReturn("root");
        filter.doFilter(request, response, filterChain);
        verify(response, times(0)).sendRedirect(String.format("%s/sign_in", request.getContextPath()));
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    public void whenURINotSignInAndLoginNullThanRedirect() throws ServletException, IOException {
        AuthFilter filter = new AuthFilter();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        HttpSession session  = mock(HttpSession.class);
        when(request.getRequestURI()).thenReturn("/crud");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("login")).thenReturn(null);
        filter.doFilter(request, response, filterChain);
        verify(response, times(1)).sendRedirect(String.format("%s/sign_in", request.getContextPath()));
        verify(filterChain, times(0)).doFilter(request, response);
    }

}