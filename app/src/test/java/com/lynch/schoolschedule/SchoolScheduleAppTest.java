package com.lynch.schoolschedule;

import android.content.Context;

import com.lynch.schoolschedule.Helpers.SessionManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link SchoolScheduleApp}.
 */
public class SchoolScheduleAppTest {

    @Mock
    private SessionManager mockSessionManager;

    private SchoolScheduleApp app;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        app = new SchoolScheduleApp();
        app.setSessionManager(mockSessionManager);
    }

    @Test
    public void testOnCreate_RememberMeDisabled_ShouldLogout() {
        when(mockSessionManager.isRememberMeEnabled()).thenReturn(false);
        app.onCreate();
        verify(mockSessionManager).logout();
    }

    @Test
    public void testOnCreate_RememberMeEnabled_ShouldNotLogout() {
        when(mockSessionManager.isRememberMeEnabled()).thenReturn(true);
        app.onCreate();
        verify(mockSessionManager, never()).logout();
    }
}
