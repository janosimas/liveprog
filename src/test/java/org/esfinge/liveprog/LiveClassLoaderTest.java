package org.esfinge.liveprog;

import static org.junit.Assert.fail;

import org.esfinge.liveprog.exception.LiveClassLoadException;
import org.esfinge.liveprog.instrumentation.ClassInfo;
import org.esfinge.liveprog.instrumentation.inspector.InspectorHelper;
import org.esfinge.liveprog.instrumentation.transformer.TransformerHelper;
import org.junit.Assert;
import org.junit.Test;


public class LiveClassLoaderTest {

  @Test
  public void loadLiveClassTest() {
    try {
      LiveClassLoader loader = new LiveClassLoader();

      ClassInfo aInfo = (new InspectorHelper()).inspect(ClassA.class);
      Class<?> aNew = loader.loadLiveClass(aInfo);

      Assert.assertEquals("Loaded class different from original class.", ClassA.class, aNew);
    } catch (LiveClassLoadException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void loadLiveClassTest2() {
    try {
      LiveClassLoader loader = new LiveClassLoader();

      ClassInfo aInfo = (new InspectorHelper()).inspect(ClassA.class);
      String newName = aInfo.getName()+"v2";
      TransformerHelper transformer = new TransformerHelper();
      byte[] bytecodeNew = transformer.transform(aInfo, aInfo.getName(), newName);
      aInfo.setName(newName);
      aInfo.setBytecode(bytecodeNew);

      Class<?> aNew = loader.loadLiveClass(aInfo);

      Assert.assertEquals("Loaded class different from original class.", newName, aNew.getName());
    } catch (LiveClassLoadException e) {
      fail(e.getMessage());
    }
  }
}