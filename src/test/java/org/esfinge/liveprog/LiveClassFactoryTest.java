package org.esfinge.liveprog;

import org.esfinge.liveprog.db.ILiveClassDB;
import org.esfinge.liveprog.instrumentation.ClassInfo;
import org.esfinge.liveprog.instrumentation.inspector.InspectorHelper;
import org.junit.Assert;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;

public class LiveClassFactoryTest {    	

	/**
	 * Creates a live object and verifies if updated
	 */
	@Test
	public void testCreateObjectInTestMode() {    
		try {
			ILiveClassDB dbMock = mock(ILiveClassDB.class);
			
			LiveClassFactory factory = new LiveClassFactory(dbMock);

			ClassA aClass = factory.createLiveObjectInTestMode(ClassA.class);

			verify(dbMock, times(1)).getLiveClass(eq(ClassA.class.getName()), eq(true));
			verify(dbMock, times(1)).saveLiveClass(eq(ClassA.class.getName()), any());
			reset(dbMock);

			Assert.assertEquals("Live object not of ClassA.", (new ClassA()).getName(), aClass.getName());

			ClassInfo bInfo = (new InspectorHelper()).inspect(ClassB.class);
			factory.liveClassUpdated(ClassA.class.getName(), bInfo);

			Assert.assertEquals("Live object not of ClassB.", (new ClassB()).getName(), aClass.getName());

			verify(dbMock, times(1)).saveLiveClass(eq(ClassA.class.getName()), eq(bInfo));
			reset(dbMock);

			ClassA a2Class = factory.createLiveObjectInTestMode(ClassA.class);
			Assert.assertEquals("New live object not of ClassB.", (new ClassB()).getName(), a2Class.getName());

			verify(dbMock, never()).saveLiveClass(any(), any());
			reset(dbMock);

			ClassA a3Class = factory.createLiveObject(ClassA.class);
			Assert.assertEquals("New live object not of ClassA, should not be updated.", (new ClassA()).getName(),
					a3Class.getName());

			verify(dbMock, never()).saveLiveClass(any(), any());
			reset(dbMock);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	/**
	 * Creates a live object not in test mode and verifies if updated
	 */
	@Test
	public void testCreateObjectInProductionMode() {
		try {
			ILiveClassDB dbMock = mock(ILiveClassDB.class);

			LiveClassFactory factory = new LiveClassFactory(dbMock);

			ClassA aClass = factory.createLiveObject(ClassA.class);

			verify(dbMock, times(1)).getLiveClass(eq(ClassA.class.getName()), eq(false));
			verify(dbMock, times(1)).saveLiveClass(eq(ClassA.class.getName()), any());
			reset(dbMock);

			Assert.assertEquals("Live object not of ClassA.", (new ClassA()).getName(), aClass.getName());

			ClassInfo bInfo = (new InspectorHelper()).inspect(ClassB.class);
			factory.liveClassUpdated(ClassA.class.getName(), bInfo);

			Assert.assertEquals("Live object not of ClassA, should not be updated.", (new ClassA()).getName(), aClass.getName());

			verify(dbMock, times(1)).saveLiveClass(eq(ClassA.class.getName()), eq(bInfo));
			reset(dbMock);

			ClassA a2Class = factory.createLiveObject(ClassA.class);
			Assert.assertEquals("New live object not of ClassA, should not be updated.", (new ClassA()).getName(), a2Class.getName());

			verify(dbMock, never()).saveLiveClass(any(), any());
			reset(dbMock);

			ClassA a3Class = factory.createLiveObjectInTestMode(ClassA.class);
			Assert.assertEquals("New live object not of ClassB, should be updated.", (new ClassB()).getName(),
					a3Class.getName());

			verify(dbMock, never()).saveLiveClass(any(), any());
			reset(dbMock);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
}
