package com.task.taskbackend.Services.Admin;

import com.task.taskbackend.Models.User;
import com.task.taskbackend.Models.UserRole;
import com.task.taskbackend.Repositories.UserRepository;
import com.task.taskbackend.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private  final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public List<UserDto> getUsers() {
        return userRepository.findAll().stream().filter(user->user.getUserRole()== UserRole.EMPLOYEE).map(User::getUserDto)
                .collect(Collectors.toList());
    }





    @Override
    public User createUser(User userDto) {
        // Validate password is not null
        if (userDto.getPassword() == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }

        // Encode password using BCryptPasswordEncoder
        userDto.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));

        // Save user to database
        User createdUser = userRepository.save(userDto);

        return createdUser;
    }

    @Override
    public User getUserDetails(Long userId) {
        return  userRepository.findById(userId).orElse(null);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override

    public void updateUser(Long userId, UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            user.setCompany(userDto.getCompany());
            user.setAddresse(userDto.getAddresse());
            user.setCountry(userDto.getCountry());
            user.setPhone(userDto.getPhone());
            user.setJob(userDto.getJob());
            userRepository.save(user);
        } else {
            // Handle user not found exception
            throw new RuntimeException("User not found with id: " + userId);
        }
    }


}
