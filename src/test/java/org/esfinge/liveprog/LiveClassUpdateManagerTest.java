package org.esfinge.liveprog;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;

import org.esfinge.liveprog.instrumentation.ClassInfo;
import org.esfinge.liveprog.instrumentation.inspector.InspectorHelper;
import org.junit.Test;

public class LiveClassUpdateManagerTest {


  @Test
  public void notifyOnClassFileUpdatedTest(){
    ILiveClassUpdateObserver observerMock = mock(ILiveClassUpdateObserver.class);

    LiveClassUpdateManager updateManager = new LiveClassUpdateManager();
    ClassInfo bInfo = (new InspectorHelper()).inspect(ClassB.class);

    // update with no observer registered
    updateManager.classFileUpdated(bInfo);

    verify(observerMock, never()).liveClassUpdated(any(), any());
    reset(observerMock);

    // update with observer registered
    updateManager.addObserver(observerMock);
    updateManager.classFileUpdated(bInfo);

    verify(observerMock, times(1)).liveClassUpdated(eq(ClassB.class.getName()), any());
    reset(observerMock);

    // update with no observer registered
    updateManager.removeObserver(observerMock);
    updateManager.classFileUpdated(bInfo);

    verify(observerMock, never()).liveClassUpdated(any(), any());
    reset(observerMock);
  }
}