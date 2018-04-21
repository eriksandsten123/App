package app.controller;

import app.domain.User;
import app.manager.UserProfileManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class TestFavoritesController {

    private MockMvc mockMvc;

    @MockBean
    private UserProfileManager userProfileManager;

    @Before
    public void setup() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/src/main/resources/templates/");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders.standaloneSetup(new FavoritesController(userProfileManager)).setViewResolvers(viewResolver).build();
    }

    @Test
    public void testViewFavorites() throws Exception {
        mockMvc.perform(get("/favorites")).andExpect(status().isOk()).andExpect(view().name("favorites"));
    }

    @Test
    public void testAddFavorite() throws Exception {
        User user = new User();
        user.setId(3L);
        when(userProfileManager.getUserById(3L)).thenReturn(user);

        mockMvc.perform(get("/favorites/add").param("userId", "3")).andExpect(status().isOk());
    }

    @Test
    public void testAddFavorite_userAlreadyAdded() throws Exception {

    }

    @Test
    public void testAddFavorite_addSelfAsFavorite() throws Exception {

    }

    @Test
    public void testRemoveFavorite() throws Exception {
        User user = new User();
        user.setId(3L);
        when(userProfileManager.getUserById(3L)).thenReturn(user);

        mockMvc.perform(get("/favorites/remove").param("userId", "3")).andExpect(status().isOk());
    }

    @Test
    public void testRemoveFavorite_userNotAFavorite() throws Exception {

    }
}