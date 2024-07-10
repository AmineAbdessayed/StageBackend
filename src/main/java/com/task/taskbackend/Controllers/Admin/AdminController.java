package com.task.taskbackend.Controllers.Admin;

import com.task.taskbackend.Models.User;
import com.task.taskbackend.Services.Admin.AdminService;
import com.task.taskbackend.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminController {

    private  final AdminService adminService;


    @GetMapping("/users")
    public ResponseEntity<?> getUsers(){
        return   ResponseEntity.ok(adminService.getUsers());
    }

    @PostMapping("/addUser")
    public ResponseEntity<UserDto> createUser(@RequestBody User user) {
        User createdUser = adminService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser.getUserDto());
    }
    @PutMapping("/users/{userId}")
    public ResponseEntity<Void> updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        adminService.updateUser(userId, userDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        adminService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/getUser/{userId}")
    public User getUser(@PathVariable Long userId) {
        return  adminService.getUserDetails(userId);
    }
}
