package org.esfinge.liveprog.monitor;

import java.util.ArrayList;
import java.util.List;

import org.esfinge.liveprog.instrumentation.ClassInfo;

/**
 * Estrutura base para monitores de arquivos de classes dinamicas.
 */
public abstract class AbstractLiveClassFileMonitor implements ILiveClassFileMonitor
{
	// lista de observadores a serem notificados dos arquivos 
	// de novas versoes de classes dinamicas encontrados
	protected List<ILiveClassFileMonitorObserver> observers;
	
	// filtro dos tipos de arquivos a serem monitorados
	protected ILiveClassFileFilter fileFilter;
	
	// validador dos arquivos monitorados
	protected ILiveClassFileValidator fileValidator;
	
	
	/**
	 * Construtor padrao.
	 */
	public AbstractLiveClassFileMonitor()
	{
		this.observers = new ArrayList<ILiveClassFileMonitorObserver>();
	}
	
	@Override
	public void setFileFilter(ILiveClassFileFilter filter)
	{
		this.fileFilter = filter;
	}

	@Override
	public void setFileValidator(ILiveClassFileValidator validator)
	{
		this.fileValidator = validator;
	}

	@Override
	public void addObserver(ILiveClassFileMonitorObserver observer)
	{
		this.observers.add(observer);
	}

	@Override
	public void removeObserver(ILiveClassFileMonitorObserver observer)
	{
		this.observers.remove(observer);
	}
	
	/**
	 * Notifica os observadores que um arquivo de uma nova versao de classe dinamica
	 * foi encontrado.
	 * 
	 * @param classInfo Informações sobre a nova versao da classe dinamica
	 */
	protected void notifyObservers(ClassInfo classInfo)
	{
		this.observers.forEach(obs -> obs.classFileUpdated(classInfo));
	}
}
