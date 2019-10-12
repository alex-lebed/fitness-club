package com.epam.fitness.command.impl.assignment;

import com.epam.fitness.command.Command;
import com.epam.fitness.command.CommandResult;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.service.api.AssignmentService;
import com.epam.fitness.service.api.ExerciseService;
import com.epam.fitness.entity.assignment.Assignment;
import com.epam.fitness.entity.assignment.Exercise;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowAssignmentsCommand implements Command {

    private static final String ASSIGNMENTS_PAGE = "/assignments";
    private static final String ORDER_ID = "order_id";
    private static final String ASSIGNMENTS_ATTRIBUTE = "assignments";
    private static final String NUTRITION_TYPE_ATTRIBUTE = "nutrition_type";
    private static final String EXERCISES_ATTRIBUTE = "exercises";

    private AssignmentService assignmentService;
    private ExerciseService exerciseService;

    public ShowAssignmentsCommand(AssignmentService assignmentService, ExerciseService exerciseService){
        this.assignmentService = assignmentService;
        this.exerciseService = exerciseService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response)
            throws ServiceException {
        String orderIdStr = request.getParameter(ORDER_ID);
        int orderId = Integer.parseInt(orderIdStr);
        HttpSession session = request.getSession();
        List<Assignment> assignments = assignmentService.getAllByOrderId(orderId);
        session.setAttribute(ASSIGNMENTS_ATTRIBUTE, assignments);
        String nutritionType = assignmentService.getNutritionTypeByOrderId(orderId);
        session.setAttribute(NUTRITION_TYPE_ATTRIBUTE, nutritionType);
        List<Exercise> exercises = exerciseService.getAll();
        session.setAttribute(EXERCISES_ATTRIBUTE, exercises);
        return CommandResult.redirect(ASSIGNMENTS_PAGE);
    }
}