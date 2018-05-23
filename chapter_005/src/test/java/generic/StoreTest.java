package generic;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class StoreTest {
    @Test
    public void whenAddUserThanUserExists() {
        UserStore userStore = new UserStore(5);
        User user1 = new User("Ivan");
        User user2 = new User("Tolya");
        userStore.add(user1);
        userStore.add(user2);
        assertThat(userStore.findById("Tolya"), is(user2));
    }

    @Test
    public void whenAddRoleThanRoleExists() {
        RoleStore roleStore = new RoleStore(5);
        Role role1 = new Role("admin");
        Role role2 = new Role("common");
        roleStore.add(role1);
        roleStore.add(role2);
        assertThat(roleStore.findById("common"), is(role2));
    }

    @Test
    public void whenReplaceUserThanNewUser() {
        UserStore userStore = new UserStore(5);
        User user1 = new User("Ivan");
        User user2 = new User("Tolya");
        userStore.add(user1);
        userStore.replace("Ivan", user2);
        assertThat(userStore.findById("Tolya"), is(user2));
        assertNull(userStore.findById("Ivan"));
    }

    @Test
    public void whenDeleteUserThanUserHasGone() {
        UserStore userStore = new UserStore(5);
        User user1 = new User("Ivan");
        userStore.add(user1);
        assertThat(userStore.delete("Ivan"), is(true));
        assertNull(userStore.findById("Ivan"));
    }
}
