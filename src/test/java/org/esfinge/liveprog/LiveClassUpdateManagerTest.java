package org.esfinge.liveprog;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.esfinge.liveprog.instrumentation.ClassInfo;
import org.esfinge.liveprog.instrumentation.inspector.InspectorHelper;
import org.junit.Test;

public class LiveClassUpdateManagerTest {


  @Test
  public void notifyOnClassFileUpdatedTest(){
    ILiveClassUpdateObserver observerMock = mock(ILiveClassUpdateObserver.class);

    LiveClassUpdateManager updateManager = new LiveClassUpdateManager();
    ClassInfo bInfo = (new InspectorHelper()).inspect(ClassB.class);

    updateManager.addObserver(observerMock);
    updateManager.classFileUpdated(bInfo);

    verify(observerMock, times(1)).liveClassUpdated(eq(ClassB.class.getName()), any());
    reset(observerMock);
  }
}