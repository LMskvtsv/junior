package servlet;

import dbcontrol.ValidateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import persistent.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(User.class)
public class UserAddServletTest {

    private final ValidateService validateService = ValidateService.getValidateService();
    @Test
    public void userAdd() throws ServletException, IOException {
        UserAddServlet userAddServlet = new UserAddServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(request.getParameter("name")).thenReturn("test_name");
        when(request.getParameter("login")).thenReturn("test_login");
        when(request.getParameter("email")).thenReturn("test_email");
        when(request.getParameter("password")).thenReturn("test_pass");
        when(request.getParameter("role_id")).thenReturn("2");
        when(request.getParameter("country")).thenReturn("Russia");
        when(request.getParameter("city")).thenReturn("SPB");
        when(request.getRequestDispatcher("/WEB-INF/views/AddingError.jsp")).thenReturn(requestDispatcher);
        userAddServlet.doPost(request, response);
        verify(request, times(1)).getParameter("login");
        verify(request, times(1)).getParameter("login");
        verify(request, times(1)).getParameter("email");
        verify(request, times(1)).getParameter("password");
        verify(request, times(1)).getParameter("role_id");
        verify(response, times(1)).sendRedirect(String.format("%s/", request.getContextPath()));
        verify(request, times(0)).getRequestDispatcher("/WEB-INF/views/AddingError.jsp");
        assertThat(validateService.getUserByCredentials("test_login", "test_pass").getName(), is("test_name"));
    }

    @Test
    public void userAddDoGet() throws ServletException, IOException {
        UserAddServlet userAddServlet = new UserAddServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(request.getParameter("role_id")).thenReturn("2");
        when(request.getRequestDispatcher("/WEB-INF/views/UserAdd.jsp")).thenReturn(requestDispatcher);
        userAddServlet.doGet(request, response);
        verify(request, times(1)).getRequestDispatcher("/WEB-INF/views/UserAdd.jsp");
    }
}