package com.lynch.schoolschedule;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.lynch.schoolschedule.Entities.TermEntity;
import com.lynch.schoolschedule.Entities.UserEntity;
import com.lynch.schoolschedule.Entities.ClassEntity;
import com.lynch.schoolschedule.Entities.ChecklistItemEntity;
import com.lynch.schoolschedule.Assessments.Assessment;
import com.lynch.schoolschedule.Assessments.PerformanceAssessment;
import com.lynch.schoolschedule.Assessments.ObjectiveAssessment;

public class EntitiesTest {

    private UserEntity user;
    private TermEntity term;
    private ClassEntity classEntity;
    private ChecklistItemEntity checklistItem;
    private Assessment assessment;
    private ObjectiveAssessment objectiveAssessment;
    private PerformanceAssessment performanceAssessment;

    @Before
    public void setUp() {
        user = new UserEntity();
        user.setId(1);
        user.setName("testuser");
        user.setPassword("password");

        term = new TermEntity();
        term.setId(2);
        term.setTermName("Spring 2025");
        term.setStartDate("2025-01-15");
        term.setEndDate("2025-05-20");

        classEntity = new ClassEntity();
        classEntity.setId(3);
        classEntity.setClassName("Math 101");
        classEntity.setInstructor("Dr. Smith");

        checklistItem = new ChecklistItemEntity();
        checklistItem.setId(4);
        checklistItem.setAssessmentId(5);
        checklistItem.setContent("Review Chapter 1");
        checklistItem.setDone(true);

        assessment = new Assessment();
        assessment.setId(6);
        assessment.setTitle("Final Exam");
        assessment.setDueDate("2025-05-25");
        assessment.setType("Exam");

        objectiveAssessment = new ObjectiveAssessment();
        objectiveAssessment.setId(7);
        objectiveAssessment.setTitle("Quiz 1");
        objectiveAssessment.setDueDate("2025-02-05");
        objectiveAssessment.setType("Objective");

        performanceAssessment = new PerformanceAssessment();
        performanceAssessment.setId(8);
        performanceAssessment.setTitle("Presentation");
        performanceAssessment.setDueDate("2025-03-15");
        performanceAssessment.setType("Performance");
    }

    @Test
    public void testUserEntity() {
        assertEquals(1, user.getId());
        assertEquals("testuser", user.getName());
        assertEquals("password", user.getPassword());
    }

    @Test
    public void testTermEntity() {
        assertEquals(2, term.getId());
        assertEquals("Spring 2025", term.getTermName());
        assertEquals("2025-01-15", term.getStartDate());
        assertEquals("2025-05-20", term.getEndDate());
    }

    @Test
    public void testClassEntity() {
        assertEquals(3, classEntity.getId());
        assertEquals("Math 101", classEntity.getClassName());
        assertEquals("Dr. Smith", classEntity.getInstructor());
    }

    @Test
    public void testChecklistEntity() {
        assertEquals(4, checklistItem.getId());
        assertEquals(5, checklistItem.getAssessmentId());
        assertEquals("Review Chapter 1", checklistItem.getContent());
        assertTrue(checklistItem.isDone());
    }
    @Test
    public void testAssessment() {
        assertEquals(6, assessment.getId());
        assertEquals("Final Exam", assessment.getTitle());
        assertEquals("2025-05-25", assessment.getDueDate());
        assertEquals("Exam", assessment.getType());
    }

    @Test
    public void testObjectiveAssessment() {
        assertEquals(7, objectiveAssessment.getId());
        assertEquals("Quiz 1", objectiveAssessment.getTitle());
        assertEquals("2025-02-05", objectiveAssessment.getDueDate());
        assertEquals("Objective", objectiveAssessment.getType());
    }

    @Test
    public void testPerformanceAssessment() {
        assertEquals(8, performanceAssessment.getId());
        assertEquals("Presentation", performanceAssessment.getTitle());
        assertEquals("2025-03-15", performanceAssessment.getDueDate());
        assertEquals("Performance", performanceAssessment.getType());
    }
}


