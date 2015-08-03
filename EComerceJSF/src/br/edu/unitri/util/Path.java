package br.edu.unitri.util;

import java.io.Serializable;

public final class Path implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public static final String workingDir = UtilBean.getAbsolutePathApp();	
	public static final String PATH_FOTOS = UtilBean.getAbsolutePathImages();	
	public static final String PATH_Raiz  = workingDir;;


}
