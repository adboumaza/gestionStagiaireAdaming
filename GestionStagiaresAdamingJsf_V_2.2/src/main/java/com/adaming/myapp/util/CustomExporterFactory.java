package com.adaming.myapp.util;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;

import org.primefaces.extensions.component.exporter.ExcelExporter;
import org.primefaces.extensions.component.exporter.Exporter;
import org.primefaces.extensions.component.exporter.ExporterFactory;
import org.primefaces.extensions.component.exporter.PDFExporter;

import com.adaming.myapp.bean.ExporterController;
import com.adaming.myapp.tools.LoggerConfig;

/**
 * 
 * 
 * @author adel
 * @date 10/10/2016
 * @version 1.0.0
 * 
 * Accessor for objects stored in several scopes via faces context
 * {@link javax.faces.context.FacesContext}.
 */
public class CustomExporterFactory implements ExporterFactory {

	static public enum ExporterType {
		PDF, XLSX
	}

	public Exporter getExporterForType(String type) {

		Exporter exporter = null;

		FacesContext context = FacesContext.getCurrentInstance();
		ExporterController bean = (ExporterController) context.getApplication()
				.evaluateExpressionGet(context, "#{exporterController}",
						ExporterController.class);
		Boolean customExport = bean.getCustomExporter();

		try {
			ExporterType exporterType = ExporterType
					.valueOf(type.toUpperCase());

			switch (exporterType) {

			case PDF:
				if (customExport) {
					exporter = new PDFCustomExporter();
				} else {
					exporter = new PDFExporter();
				}
				break;

			case XLSX:
				if (customExport) {
					exporter = new ExcelCustomExporter();
					LoggerConfig.logInfo(exporter+"Exporter : if");
				} else {
					exporter = new ExcelExporter();
					LoggerConfig.logInfo(exporter+"Exporter : else");
				}
				break;

			default: {
				if (customExport) {
					exporter = new PDFCustomExporter();
				} else {
					exporter = new PDFExporter();
				}
				break;
			}

			}
		} catch (IllegalArgumentException e) {
			throw new FacesException(e);
		}

		return exporter;
	}

}