package org.esfinge.liveprog.monitor;

import org.esfinge.liveprog.instrumentation.ClassInfo;

/**
 * Interface para ser notificado quando arquivos de novas versoes de classes dinamicas forem encontrados.
 */
public interface ILiveClassFileMonitorObserver
{
	/**
	 * Notifica que um arquivo de uma nova versao de classe dinamica foi encontrado.
	 * 
	 * @param classInfo Informações da nova versao da classe dinamica
	 */
	public void classFileUpdated(ClassInfo classInfo);
}
