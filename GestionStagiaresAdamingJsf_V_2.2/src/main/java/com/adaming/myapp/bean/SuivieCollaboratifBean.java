package com.adaming.myapp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;


import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.adaming.myapp.agenda.service.IAgendaService;
import com.adaming.myapp.entities.Agenda;
import com.adaming.myapp.entities.Formateur;
import com.adaming.myapp.formateur.service.IFormateurService;


@SuppressWarnings("serial")
@Component("suivieCollaboratifBean")
@Scope(value = "session")
public class SuivieCollaboratifBean implements Serializable {

	@Inject
	private IAgendaService agendaService;

	@Inject
	private IFormateurService formateurService;

	private ScheduleModel eventModel;

	private ScheduleModel lazyEventModel;

	private ScheduleEvent event = new DefaultScheduleEvent();

	private List<Formateur> formateurs;
	
	private List<Agenda> agendas;

	private String mail;

	public String init() {
		formateurs = formateurService.getFormateursInSessionEncours();
		setAgendas(null);
		mail= "";
		eventModel = new DefaultScheduleModel();
		return "suivieCollab?faces-redirect=true";
	}
	@PostConstruct
	public void initMyModel()
	{
		eventModel = new DefaultScheduleModel();
	}

	public void getAgendaByFormateur() {
        agendas = new ArrayList<Agenda>();
		agendas = agendaService.getAllAgendaByName(mail);

		eventModel = new DefaultScheduleModel();

		for (Agenda a : agendas) {
			eventModel.addEvent(new DefaultScheduleEvent(a.getLabel(), a
					.getDebut(), a.getFin()));
		}
		System.out.println("agends : "+agendas);
		lazyEventModel = new LazyScheduleModel();
		
	}


	public ScheduleModel getEventModel() {
		return eventModel;
	}

	public ScheduleModel getLazyEventModel() {
		return lazyEventModel;
	}

	public ScheduleEvent getEvent() {
		return event;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}

	/**
	 * @return the formateurs
	 */
	public List<Formateur> getFormateurs() {
		return formateurs;
	}

	/**
	 * @param formateurs
	 *            the formateurs to set
	 */
	public void setFormateurs(List<Formateur> formateurs) {
		this.formateurs = formateurs;
	}

	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * @param mail
	 *            the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * @param eventModel
	 *            the eventModel to set
	 */
	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;
	}

	/**
	 * @param lazyEventModel
	 *            the lazyEventModel to set
	 */
	public void setLazyEventModel(ScheduleModel lazyEventModel) {
		this.lazyEventModel = lazyEventModel;
	}

	/**
	 * @return the agendas
	 */
	public List<Agenda> getAgendas() {
		return agendas;
	}

	/**
	 * @param agendas the agendas to set
	 */
	public void setAgendas(List<Agenda> agendas) {
		this.agendas = agendas;
	}

}
