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
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserEditServletTest {
    private  final ValidateService validateService = ValidateService.getValidateService();

    @Test
    public void editDoGet() throws ServletException, IOException {
        UserEditServlet userEditServlet = new UserEditServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        User user = new User("ivan", "ivan", "iv@Tyt.ru", "123", new Role(2));
        user.setCountry("Russia");
        user.setCity("Moscow");
        assertThat(validateService.add(user), is(true));
        when(request.getParameter("id")).thenReturn(user.getId());
        when(request.getRequestDispatcher("/WEB-INF/views/UserEdit.jsp")).thenReturn(requestDispatcher);
        userEditServlet.doGet(request, response);
        verify(request, times(1)).getParameter("id");
        verify(request, times(1)).setAttribute("name", user.getName());
        verify(request, times(1)).setAttribute("login", user.getLogin());
        verify(request, times(1)).setAttribute("email", user.getEmail());
        verify(request, times(1)).setAttribute("role_id", user.getRole().getId());
        verify(request, times(1)).getRequestDispatcher("/WEB-INF/views/UserEdit.jsp");
    }

    @Test
    public void editDoGetError() throws ServletException, IOException {
        UserEditServlet userEditServlet = new UserEditServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(request.getParameter("id")).thenReturn("fake_id");
        when(request.getRequestDispatcher("/WEB-INF/views/UpdatingError.jsp")).thenReturn(requestDispatcher);
        userEditServlet.doGet(request, response);
        verify(request, times(1)).getRequestDispatcher("/WEB-INF/views/UpdatingError.jsp");
    }


    @Test
    public void editDoPost() throws ServletException, IOException {
        UserEditServlet userEditServlet = new UserEditServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        User user = new User("ivan", "ivan", "iv@Tyt.ru", "123", new Role(2));
        user.setCountry("Russia");
        user.setCity("Moscow");
        assertThat(validateService.add(user), is(true));
        when(request.getParameter("id")).thenReturn(user.getId());
        when(request.getParameter("name")).thenReturn("new_name");
        when(request.getParameter("login")).thenReturn("new_login");
        when(request.getParameter("email")).thenReturn("new_email");
        when(request.getParameter("role_id")).thenReturn("1");
        userEditServlet.doPost(request, response);
        verify(response, times(1)).sendRedirect(String.format("%s/", request.getContextPath()));
        assertThat(validateService.findByID(user.getId()).getName(), is("new_name"));
        assertThat(validateService.findByID(user.getId()).getLogin(), is("new_login"));
        assertThat(validateService.findByID(user.getId()).getEmail(), is("new_email"));
        assertThat(validateService.findByID(user.getId()).getRole().getId(), is(1));
    }

    @Test
    public void editDoPostNullFields() throws ServletException, IOException {
        UserEditServlet userEditServlet = new UserEditServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        User user = new User("ivan", "ivan", "iv@Tyt.ru", "123", new Role(2));
        user.setCountry("Russia");
        user.setCity("Moscow");
        assertThat(validateService.add(user), is(true));
        when(request.getParameter("id")).thenReturn(user.getId());
        userEditServlet.doPost(request, response);
        verify(response, times(1)).sendRedirect(String.format("%s/", request.getContextPath()));
        assertThat(validateService.findByID(user.getId()).getName(), is("ivan"));
        assertThat(validateService.findByID(user.getId()).getLogin(), is("ivan"));
        assertThat(validateService.findByID(user.getId()).getEmail(), is("iv@Tyt.ru"));
        assertThat(validateService.findByID(user.getId()).getRole().getId(), is(2));
    }

    @Test
    public void editDoPostError() throws ServletException, IOException {
        UserEditServlet userEditServlet = new UserEditServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(request.getParameter("id")).thenReturn("fake_id");
        when(request.getParameter("name")).thenReturn("fake_name");
        when(request.getParameter("login")).thenReturn("fake_login");
        when(request.getParameter("email")).thenReturn("fake_email");
        when(request.getParameter("role_id")).thenReturn("3");
        when(request.getRequestDispatcher("/WEB-INF/views/UpdatingError.jsp")).thenReturn(requestDispatcher);
        userEditServlet.doPost(request, response);
        verify(response, times(0)).sendRedirect(String.format("%s/", request.getContextPath()));
        verify(request, times(1)).getRequestDispatcher("/WEB-INF/views/UpdatingError.jsp");
    }
}