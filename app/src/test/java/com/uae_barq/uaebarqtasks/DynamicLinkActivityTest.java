package com.uae_barq.uaebarqtasks;

import com.uae_barq.uaebarqtasks.java.main.DynamicLinkActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DynamicLinkActivityTest {

    private static final String TAG = "DynamicLinkActivityTest";

    //java.lang.Exception: Method onSetup() should be public
    @Before
    public void onSetup() throws Exception {
//        It invokes the static method MockitoAnnotations.initMocks(this) to populate the annotated fields.
//        Alternatively you can use @RunWith(MockitoJUnitRunner.class).
//        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testingFetchingDataFromDynamicLink() {
        //java.lang.RuntimeException: Stub!
//        Log.e(TAG, "givenValidInput_whenAdd_shouldCallAddOperator: " );
    }

    @Test
    public void testingDynamicLinkActivity() {
        DynamicLinkActivity dynamicMockObject = Mockito.mock(DynamicLinkActivity.class);
        when(dynamicMockObject.getLocalClassName()).thenReturn("DynamicLinkActivity");//true
//        when(dynamicMockObject.getLocalClassName()).thenReturn("DynamicLinkActivit");//false
        assertThat(dynamicMockObject.getLocalClassName(), is("DynamicLinkActivity"));
    }

}
