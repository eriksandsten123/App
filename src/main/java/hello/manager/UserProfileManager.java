package hello.manager;

import hello.domain.User;
import hello.repository.UserRepository;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Service
public class UserProfileManager {
    private UserRepository userRepository;

    @Autowired
    public UserProfileManager(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @NotBlank
    @Value("/resources/userprofile/")
    private String userProfilesPath;

    public void storeProfilePicture(final User userProfile, final MultipartFile profilePicture) {
        final String username = userProfile.getName();

        System.out.println("Storing profile pic...! with name " + username);
        String fileName = userProfilesPath + username + "/profilepic." + FilenameUtils.getExtension(profilePicture.getOriginalFilename());

        try {
            Files.copy(profilePicture.getInputStream(), FileSystems.getDefault().getPath(fileName), StandardCopyOption.REPLACE_EXISTING);
            userProfile.setProfilePicture(fileName);
            userRepository.save(userProfile);
        } catch (final IOException e) {
            System.out.println("Could not save image!!!");
        }
    }

    public User getUserById(final Long id) {
        return userRepository.findOne(id);
    }

    public User save(final User user) {
        return userRepository.save(user);
    }
}