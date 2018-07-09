package servlet;

import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SignOutServletTest {
    @Test
    public void whenSignOutThenInvalidate() throws ServletException, IOException {
        SignOutServlet signOutServlet = new SignOutServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session  = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        signOutServlet.doPost(request, response);
        verify(session, times(1)).invalidate();
    }
}