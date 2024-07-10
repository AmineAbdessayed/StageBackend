package com.task.taskbackend.Services.Admin;

import com.task.taskbackend.Models.User;
import com.task.taskbackend.dto.UserDto;

import java.util.List;

public interface AdminService {

    List<UserDto> getUsers();
    public User createUser(User userDto);
    public User getUserDetails(Long userId);
    void deleteUser(Long userId);
    void updateUser(Long userId, UserDto userDto);
}
