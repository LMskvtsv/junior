package servlet;

import logic.ValidateService;
import org.junit.Test;
import persistent.Role;
import persistent.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserListServletTest {

    private  final ValidateService validateService = ValidateService.getValidateService();
    @Test
    public void listGet() throws ServletException, IOException {
        UserListServlet userListServlet = new UserListServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/WEB-INF/views/UsersList.jsp")).thenReturn(requestDispatcher);
        userListServlet.doGet(request, response);
        verify(request, times(1)).setAttribute("users", validateService.findAll());
        verify(request, times(1)).getRequestDispatcher("/WEB-INF/views/UsersList.jsp");
    }

    @Test
    public void userDeleteSuccess() throws ServletException, IOException {
        UserListServlet userListServlet = new UserListServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        User user = new User("ivan", "ivan", "iv@Tyt.ru", "123", new Role(2));
        user.setCountry("Russia");
        user.setCity("SBP");
        assertThat(validateService.add(user), is(true));
        when(request.getParameter("action")).thenReturn("delete");
        when(request.getParameter("id")).thenReturn(user.getId());
        when(request.getRequestDispatcher("/WEB-INF/views/DeletingError.jsp")).thenReturn(requestDispatcher);
        userListServlet.doPost(request, response);
        verify(response, times(1)).sendRedirect(String.format("%s/", request.getContextPath()));
        verify(request, times(0)).setAttribute("users", validateService.findAll());
        verify(request, times(0)).getRequestDispatcher("/WEB-INF/views/DeletingError.jsp");
        assertThat(validateService.findByID(user.getId()), is(nullValue()));
    }

    @Test
    public void userDeleteError() throws ServletException, IOException {
        UserListServlet userListServlet = new UserListServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(request.getParameter("action")).thenReturn("delete");
        when(request.getParameter("id")).thenReturn("fake_id");
        when(request.getRequestDispatcher("/WEB-INF/views/DeletingError.jsp")).thenReturn(requestDispatcher);
        userListServlet.doPost(request, response);
        verify(response, times(0)).sendRedirect(String.format("%s/", request.getContextPath()));
        verify(request, times(0)).setAttribute("users", validateService.findAll());
        verify(request, times(1)).getRequestDispatcher("/WEB-INF/views/DeletingError.jsp");
    }

    @Test
    public void whenOtherActionThenNothing() throws ServletException, IOException {
        UserListServlet userListServlet = new UserListServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(request.getParameter("action")).thenReturn("update");
        when(request.getParameter("id")).thenReturn("fake_id");
        when(request.getRequestDispatcher("/WEB-INF/views/DeletingError.jsp")).thenReturn(requestDispatcher);
        userListServlet.doPost(request, response);
        verify(response, times(0)).sendRedirect(String.format("%s/", request.getContextPath()));
        verify(request, times(0)).setAttribute("users", validateService.findAll());
        verify(request, times(0)).getRequestDispatcher("/WEB-INF/views/DeletingError.jsp");
    }

}