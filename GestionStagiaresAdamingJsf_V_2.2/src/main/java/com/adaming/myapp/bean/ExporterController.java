
package com.adaming.myapp.bean;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.adaming.myapp.tools.LoggerConfig;
/**
 * 
 * @author adel
 * @date 10/10/2016
 * @version 1.0.0
 * */
@SuppressWarnings("serial")
@Component("exporterController")
@ApplicationScoped
public class ExporterController implements Serializable {

	/**
	 * LOGGER LOG4j 
	 * @see org.apache.log4j.Logger
	 */
  

	private Boolean customExporter;

	public ExporterController() {
		customExporter = false;
		LoggerConfig.logInfo("init ExporterController");
		
	}

	public Boolean getCustomExporter() {
		return customExporter;
	}

	public void setCustomExporter(Boolean customExporter) {
		this.customExporter = customExporter;
	}
}