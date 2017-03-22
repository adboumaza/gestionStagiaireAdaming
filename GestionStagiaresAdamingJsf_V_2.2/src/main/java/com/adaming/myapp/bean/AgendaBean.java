package com.adaming.myapp.bean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.adaming.myapp.agenda.service.IAgendaService;
import com.adaming.myapp.entities.Agenda;
import com.adaming.myapp.entities.User;
import com.adaming.myapp.exception.GetUserException;
import com.adaming.myapp.tools.LoggerConfig;
import com.adaming.myapp.user.service.IUserService;


@SuppressWarnings("serial")
@Component("agendaBean")
@Scope(value = "session")
public class AgendaBean implements Serializable{
    
	@Inject
	private IAgendaService serviceAgenda;
	@Inject
	private IUserService userService;
	/* get the name of user (formateur) for evenement */
	@Inject
	private UserAuthentificationBean userAuthentificationBean;
	
	private User user;
	
	private Agenda agenda;
	
	private ScheduleModel eventModel;
    
    private ScheduleModel lazyEventModel;
 
    private ScheduleEvent event = new DefaultScheduleEvent();
    

    
    public String init() throws GetUserException {
        eventModel = new DefaultScheduleModel();
        user = userService.getUser(userAuthentificationBean.getName());
        List<Agenda> agenda = serviceAgenda.getAllAgendaByName(userAuthentificationBean.getName());
        
        for(Agenda a:agenda){
        	 eventModel.addEvent(new DefaultScheduleEvent(a.getLabel(), a.getDebut(), a.getFin()));
        }
       
        lazyEventModel = new LazyScheduleModel();
        
        return "agenda?faces-redirect=true";
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
     
    public void addEvent(ActionEvent actionEvent) {
        if(event.getId() == null)
        {
        	
            eventModel.addEvent(event);
            serviceAgenda.addAgenda(new Agenda(event.getTitle(),event.getStartDate(), event.getEndDate()), user.getIdUser());
           
        }
      
        else
        {
        	 eventModel.updateEvent(event);
            // Agenda agenda = serviceAgenda.getAgendaByUserAndDate(user.getName(), event.getStartDate(), event.getEndDate(), event.getTitle());
        	 serviceAgenda.updateAgenda(agenda, user.getIdUser());
        }
           
         
        event = new DefaultScheduleEvent();
    }
    
    public void updEvent(ActionEvent actionEvent) {
        if(event.getId() != null)
        {
        	
        	eventModel.updateEvent(event);
        	agenda.setDebut(event.getStartDate());
        	agenda.setFin(event.getEndDate());
        	agenda.setLabel(event.getTitle());
        	serviceAgenda.updateAgenda(agenda, user.getIdUser());
           
        }
         
        event = new DefaultScheduleEvent();
    }
    
    public void deleteEvent(ActionEvent actionEvent) {
        if(event.getId() != null)
        {
            eventModel.deleteEvent(event);
            serviceAgenda.deleteAgenda(agenda.getIdAgenda());
            
        }

         
        event = new DefaultScheduleEvent();
    }
     
    public void onEventSelect(SelectEvent selectEvent) {
        event = (ScheduleEvent) selectEvent.getObject();
        agenda = new Agenda();
        agenda = serviceAgenda.getAgendaByUserAndDate(user.getName(), event.getStartDate(), event.getEndDate(), event.getTitle());
       LoggerConfig.logInfo("agenda récupérer");
    }
     
    public void onDateSelect(SelectEvent selectEvent) {
        event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
    }
     
   

	/**
	 * @return the userAuthentificationBean
	 */
	public UserAuthentificationBean getUserAuthentificationBean() {
		return userAuthentificationBean;
	}


	/**
	 * @param userAuthentificationBean the userAuthentificationBean to set
	 */
	public void setUserAuthentificationBean(
			UserAuthentificationBean userAuthentificationBean) {
		this.userAuthentificationBean = userAuthentificationBean;
	}


	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}


	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}


	/**
	 * @return the agenda
	 */
	public Agenda getAgenda() {
		return agenda;
	}

	/**
	 * @param agenda the agenda to set
	 */
	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}
    
    
	
}
