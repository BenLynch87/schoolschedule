package com.lynch.schoolschedule;

import android.content.Context;

import com.lynch.schoolschedule.Database.Repository;
import com.lynch.schoolschedule.Assessments.Assessment;
import com.lynch.schoolschedule.Helpers.AssessmentHelper;
import com.lynch.schoolschedule.Helpers.ClassHelper;
import com.lynch.schoolschedule.Helpers.TermHelper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class RepositoryTest {

    @Mock
    private Context mockContext;

    @Mock
    private AssessmentHelper mockAssessmentHelper;

    @Mock
    private TermHelper mockTermHelper;

    @Mock
    private ClassHelper mockClassHelper;

    private Repository repository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        repository = Repository.getInstance(mockContext);

        // Inject mocks using reflection
        Field assessmentHelperField = Repository.class.getDeclaredField("assessmentHelper");
        assessmentHelperField.setAccessible(true);
        assessmentHelperField.set(repository, mockAssessmentHelper);

        Field termHelperField = Repository.class.getDeclaredField("termHelper");
        termHelperField.setAccessible(true);
        termHelperField.set(repository, mockTermHelper);

        Field classHelperField = Repository.class.getDeclaredField("classHelper");
        classHelperField.setAccessible(true);
        classHelperField.set(repository, mockClassHelper);
    }

    @Test
    public void testGetAssessmentsDueInNextWeek_ShouldCallHelper() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextWeek = now.plusWeeks(1);

        List<Assessment> expected = Arrays.asList(new Assessment(), new Assessment());
        when(mockAssessmentHelper.getAssessmentsDueBetween(any(), any())).thenReturn(expected);

        List<Assessment> result = repository.getAssessmentsDueInNextWeek();

        verify(mockAssessmentHelper).getAssessmentsDueBetween(any(), any());
        assertEquals(expected, result);
    }

    @Test
    public void testGetAssessmentsDueInNextMonth_ShouldCallHelper() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextMonth = now.plusMonths(1);

        List<Assessment> expected = Arrays.asList(new Assessment());
        when(mockAssessmentHelper.getAssessmentsDueBetween(any(), any())).thenReturn(expected);

        List<Assessment> result = repository.getAssessmentsDueInNextMonth();

        verify(mockAssessmentHelper).getAssessmentsDueBetween(any(), any());
        assertEquals(expected, result);
    }
}
