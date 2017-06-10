package com.department.servlet.actions.impl.user;

import com.department.exceptions.AppException;
import com.department.exceptions.AppValidationException;
import com.department.models.Department;
import com.department.models.User;
import com.department.repository.DepartmentRepository;
import com.department.repository.UserRepository;
import com.department.services.DepartmentService;
import com.department.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created on 10.05.17.
 */
@Controller
public class UserController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/allUsers", method = RequestMethod.GET)
    public ModelAndView allUsers(@RequestParam(required = false) Integer id) throws AppException {
        List<User> allUsers;
        Department department;

        department = departmentService.findOneEntity(id);
        allUsers = department.getUsers();
        ModelAndView model = new ModelAndView("allUsers");
        model.addObject("allUsers", allUsers);
        model.addObject("department_id", id);

        return model;
    }


    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public String deleteUser(@RequestParam(required = false) Integer userId) throws AppException {
        userService.deleteEntityWithValidation(userId);
        return "redirect:/";
    }


    @RequestMapping(value = "/createOrUpdateUser", method = RequestMethod.GET)
    public ModelAndView createOrUpdateUserForm(@RequestParam(required = false) Integer userId
    ) throws AppException {

        ModelAndView model = new ModelAndView("createOrUpdateUserForm");
//        fill the forms to update user
        model.addObject("allDepartments", departmentService.findAllEntities());
        model.addObject("user", userService.findOneEntity(userId));

        return model;
    }


    @RequestMapping(value = "/createOrUpdateUserAction", method = RequestMethod.POST)
    public ModelAndView createOrUpdateUserAction(@RequestParam(required = false) Integer userId,
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

        try {
            userService.saveEntityWithValidation(user);

            return new ModelAndView("redirect:/allUsers?id=" + departmentId);

        } catch (AppValidationException e) {
            ModelAndView model = new ModelAndView("createOrUpdateUserForm");

            Map<String, String> mapErr = e.getMapErr();

            model.addObject("allDepartments", departmentService.findAllEntities());
            model.addObject("user", user);
            model.addObject("mapErr", mapErr);

            return model;
        }
    }
}