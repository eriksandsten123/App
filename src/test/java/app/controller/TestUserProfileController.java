package app.controller;

import app.manager.UserAccountManager;
import app.manager.UserProfileManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class TestUserProfileController {
    private MockMvc mockMvc;
    private UserProfileController userProfileController;
    private UserProfileManager userProfileManager;
    private UserAccountManager userAccountManager;

    @Before
    public void setup() {
        userProfileManager = Mockito.mock(UserProfileManager.class);
        userAccountManager = Mockito.mock(UserAccountManager.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new UserProfileController(userProfileManager, userAccountManager, null)).build();
    }

    @Test
    public void testViewUserProfile() throws Exception {
        mockMvc.perform(get("/profile/view").param("id", "5"))
                .andExpect(status().isOk()).andExpect(view().name("view-profile"));
    }
}