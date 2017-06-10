package com.department.servlet.actions.impl.department;

import com.department.exceptions.AppException;
import com.department.exceptions.AppValidationException;
import com.department.models.Department;
import com.department.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created on 02.04.2017.
 */
@Controller
public class DepartmentController {   //implements Action {

    @Autowired
    private DepartmentService departmentService;    // = new DepartmentServiceImpl();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView allDepartments() throws AppException {
        List<Department> allDepartments = departmentService.findAllEntities();
        ModelAndView model = new ModelAndView("index");
        model.addObject("allDepartments", allDepartments);
        return model;

    }

    @RequestMapping(value = "/deleteDepartment", method = RequestMethod.POST)
    public String deleteDepartment(@RequestParam Integer departmentId) throws AppException {

        departmentService.deleteEntityWithValidation(departmentId);

        return "redirect:/";
    }

    @RequestMapping(value = "/createOrUpdateDepartment", method = RequestMethod.GET)
    public ModelAndView createOrUpdateDepartmentForm(@RequestParam(required = false) Integer departmentId) throws AppException {

        ModelAndView model = new ModelAndView("createOrUpdateDepartmentForm");
        if (departmentId != null) {
            model.addObject("departmentId", departmentId);
            model.addObject("department", departmentService.findOneEntity(departmentId));
        }
        return model;
    }

    @RequestMapping(value = "/createOrUpdateDepartmentAction", method = RequestMethod.POST)
    public ModelAndView createOrUpdateDepartmentAction(@RequestParam Integer departmentId,
                                                       @RequestParam String departmentName) throws AppException {

        Department department = new Department();
        department.setId(departmentId);
        department.setName(departmentName);
        department.setCreated(new Date());

    try {
        // saving and redirect to main page
        departmentService.saveEntityWithValidation(department);

        return new ModelAndView("redirect:" + "/");

    } catch (AppValidationException e) {

        ModelAndView model = new ModelAndView("createOrUpdateDepartmentForm");

        Map<String, String> mapErr = e.getMapErr();

        model.addObject("department", department);

        // show error message on saving page
        model.addObject("mapErr", mapErr);

        return model;
    }

}
}
