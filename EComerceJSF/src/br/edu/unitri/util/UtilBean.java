package br.edu.unitri.util;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@SessionScoped
@ManagedBean
public class UtilBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String imgBanner;

	public static String getAbsolutePathApp(){
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();	
		int porta = request.getServerPort();
		String port = porta != 0 ? ":"+porta : ""; 
		String contextPath = request.getContextPath() != null ? request.getContextPath() : "";		
		String absolutePathApp = request.getServerName()+port+contextPath;		
		return absolutePathApp;
	}
	
	public static String getAbsolutePathImages(){
		return "../images/";
	}

	public static String getAbsolutePathReports(){
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();	
		int porta = request.getServerPort();
		String port = porta != 0 ? ":"+porta : ""; 
		String absolutePathApp ="http://" + request.getServerName()+port+"//EcomerceJSF//reports//";		
		return absolutePathApp;
	}

	public static String getImageResource(){
		return FacesContext.getCurrentInstance().getExternalContext().getRealPath("//pages//images//");
	}

	public static String getReportResource(){
		return FacesContext.getCurrentInstance().getExternalContext().getRealPath("//reports//");
	}

	public String getImgBanner() {
		return imgBanner;
	}

	public void setImgBanner(String imgBanner) {
		this.imgBanner = getImageResource() + "banner.png";
	}
	
}
