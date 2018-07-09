package servlet;

import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SignInServletTest {

    @Test
    public void signInSuccess() throws ServletException, IOException {
        SignInServlet signInServlet = new SignInServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session  = mock(HttpSession.class);
        when(request.getParameter("login")).thenReturn("root");
        when(request.getParameter("password")).thenReturn("root");
        when(request.getSession()).thenReturn(session);
        signInServlet.doPost(request, response);
        verify(request, times(1)).getParameter("login");
        verify(request, times(1)).getParameter("password");
        verify(session, times(1)).setAttribute("login", "root");
        verify(session, times(1)).setAttribute("role", 1);

    }

    @Test
    public void signInErrorFakeLogin() throws ServletException, IOException {
        SignInServlet signInServlet = new SignInServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session  = mock(HttpSession.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getParameter("login")).thenReturn("fake_login");
        when(request.getParameter("password")).thenReturn("root");
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("/WEB-INF/views/SignIn.jsp")).thenReturn(dispatcher);
        signInServlet.doPost(request, response);
        verify(request, times(1)).getParameter("login");
        verify(request, times(1)).getParameter("password");
        verify(request, times(1)).setAttribute("error", "Incorrect login or password! Try again.");
    }

    @Test
    public void signInErrorFakePassword() throws ServletException, IOException {
        SignInServlet signInServlet = new SignInServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session  = mock(HttpSession.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getParameter("login")).thenReturn("root");
        when(request.getParameter("password")).thenReturn("fake_password");
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("/WEB-INF/views/SignIn.jsp")).thenReturn(dispatcher);
        signInServlet.doPost(request, response);
        verify(request, times(1)).getParameter("login");
        verify(request, times(1)).getParameter("password");
        verify(request, times(1)).setAttribute("error", "Incorrect login or password! Try again.");
    }

    @Test
    public void signInErrorFakeLoginAndPassword() throws ServletException, IOException {
        SignInServlet signInServlet = new SignInServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session  = mock(HttpSession.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getParameter("login")).thenReturn("fake_login");
        when(request.getParameter("password")).thenReturn("fake_password");
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("/WEB-INF/views/SignIn.jsp")).thenReturn(dispatcher);
        signInServlet.doPost(request, response);
        verify(request, times(1)).getParameter("login");
        verify(request, times(1)).getParameter("password");
        verify(request, times(1)).setAttribute("error", "Incorrect login or password! Try again.");
    }
}