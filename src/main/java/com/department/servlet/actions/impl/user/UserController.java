package com.department.servlet.actions.impl.user;

import com.department.exceptions.AppException;
import com.department.models.Department;
import com.department.models.User;
import com.department.services.DepartmentService;
import com.department.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created on 10.05.17.
 */
@Controller
public class UserController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/allUsers/{id}", method = RequestMethod.GET)
    public List<User> allUsers(@PathVariable("id") Integer id) throws AppException {

        List<User> allUsers;
        Department department = departmentService.findOneEntity(id);
        allUsers = department.getUsers();

        return allUsers;
    }


    @ResponseBody
    @PostMapping(value = "/deleteUser")
    public List<User> deleteUser(@RequestParam(required = false) Integer userId) throws AppException {

        userService.deleteEntityWithValidation(userId);
        return userService.findAllEntities();
    }


    @ResponseBody
    @RequestMapping(value = "/createOrUpdateUser", method = RequestMethod.GET)
    public User createOrUpdateUserForm(
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) Integer departmentId
    ) throws AppException {

        User user = new User();
        if (userId == null) {
            user.setDepartment(departmentService.findOneEntity(departmentId));
        } else {
            user = userService.findOneEntity(userId);
        }
        return user;
    }


    @ResponseBody
    @PostMapping(value = "/createOrUpdateUserAction")
    public List<User> createOrUpdateUserAction(
                                               @RequestParam(required = false) Integer userId,
                                               @RequestParam String userName,
                                               @RequestParam String userSurname,
                                               @RequestParam String userEmail,
                                               @RequestParam Double userSalary,
                                               @RequestParam String userBirthday,
                                               @RequestParam Integer departmentId
    ) throws AppException, java.text.ParseException {

        User user = new User();
        user.setId(userId);
        user.setName(userName);
        user.setSurname(userSurname);
        user.setEmail(userEmail);
        user.setBirthday((new SimpleDateFormat("yyyy-mm-dd")).parse(userBirthday));
        user.setSalary(userSalary);
        user.setCreated(new Date());
        user.setDepartment(departmentService.findOneEntity(departmentId));

        userService.saveEntityWithValidation(user);


//        return userService.findAllEntities();

        List<User> allUsers;
        Department department;

        department = departmentService.findOneEntity(departmentId);
        allUsers = department.getUsers();

        return allUsers;

    }
}