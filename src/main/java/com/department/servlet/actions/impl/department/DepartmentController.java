package com.department.servlet.actions.impl.department;

import com.department.exceptions.AppException;
import com.department.models.Department;
import com.department.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * Created on 02.04.2017.
 */
@Controller
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping(value = "/")
    public String indexPage() {
        return "index";
    }

    @ResponseBody
//  Garantee that HTTP-request GET will execute in method "allDepartments"
    @RequestMapping(value = "/allDepartments", method = RequestMethod.GET)
    public List<Department> allDepartments() throws AppException {

        return departmentService.findAllEntities();
    }

    @ResponseBody
    @PostMapping(value = "/deleteDepartment")
    public List<Department> deleteDepartment(@RequestParam(required = false) Integer departmentId) throws AppException {

        departmentService.deleteEntityWithValidation(departmentId);
        return departmentService.findAllEntities();
//        return "redirect:/";
    }


    @ResponseBody
    @RequestMapping(value = "/createOrUpdateDepartment", method = RequestMethod.GET)
    public Department createOrUpdateDepartmentForm(@RequestParam(required = false) Integer departmentId) throws AppException {

        return departmentService.findOneEntity(departmentId);

    }

    @ResponseBody
    @PostMapping(value = "/createOrUpdateDepartmentAction")
    public List<Department> createOrUpdateDepartmentAction(@RequestParam Integer departmentId,
                                                           @RequestParam String departmentName) throws AppException {

        Department department = new Department();
        department.setId(departmentId);
        department.setName(departmentName);
        department.setCreated(new Date());

            departmentService.saveEntityWithValidation(department);
            return departmentService.findAllEntities();


    }
}
