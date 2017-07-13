package com.adaming.myapp.bean;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import org.primefaces.model.DualListModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.adaming.myapp.dto.QuizDTO;
import com.adaming.myapp.examen.service.IExamenService;
import com.adaming.myapp.tools.LoggerConfig;
import com.adaming.myapp.tools.Utilitaire;

@SuppressWarnings("serial")
@Component("quizEntrainementBean")
@Scope(value = "session")
public class QuizEntrainementBean implements Serializable {
    
	@Inject
	private IExamenService examenService;

	
	private List<Object[]> modules;
	private DualListModel<String> dualListModules;
	private List<String> modulesTarget ;
	private List<String> modulesSources ;
	private List<QuizDTO> modulesTargetFormatted;
	private Map<String, String> modulesSelected;
	private Set<Object[]> questions;
	private QuizDTO quizDto;
	private boolean validateEditNumbreQuestion = false;
	private final int min = 1;
	
	
	
	public String init(){
		validateEditNumbreQuestion = false;
		modulesTarget  = new ArrayList<>();
		modulesSources = new ArrayList<>();
		modulesTargetFormatted = new ArrayList<>();
		modulesSelected = new HashMap<String, String>();
		modules = examenService.getAllQuizEntrainement();
		for(Object[] m:modules){
			BigInteger nbrQuestions       = (BigInteger) m[0];
			String nomModule        = (String) m[2];
			modulesSources.add(nomModule + ", Nombre de Questions : "+nbrQuestions);
		}
		dualListModules = new DualListModel<String>(modulesSources, modulesTarget);
        
		LoggerConfig.logInfo("init"+dualListModules);
		LoggerConfig.logInfo("init source"+dualListModules.getSource());
		LoggerConfig.logInfo("init target"+dualListModules.getTarget());
		return "quiz_entrainement?faces-redirect=true";
	}
	
	public void validate(){
		validateEditNumbreQuestion = true;
	}
	
	
	public String selectedModule(){
		modulesTargetFormatted.clear();
		modulesSelected.clear();
		modulesTarget = dualListModules.getTarget();
		if(modulesTarget.isEmpty())
		{
			 Utilitaire.displayMessageError("Vuillez sélectionnez un ou plusieurs modules");
			 return null;
		}
		else
		{
			for(String item:modulesTarget)
			{
				quizDto = new QuizDTO();
				quizDto.setModule((item.split(",")[0]));
				quizDto.setNbrQuestion(item.split(" Nombre de Questions : ")[1]);
				modulesTargetFormatted.add(quizDto);
				modulesSelected.put((item.split(",")[0]),item.split(" Nombre de Questions : ")[1]);
			}

			LoggerConfig.logInfo("init source"+dualListModules.getSource());
			LoggerConfig.logInfo("init target"+dualListModules.getTarget());
			return "quiz_start_entrainement?faces-redirect=true";
		}

	}
	
	public void verify(String module,String nbrQuestions){
		modulesSelected.put(module, nbrQuestions);
	}
	
	public void startQuiz(){
		System.out.println(modulesSelected.keySet() + "mo"+modulesSelected.values());
		questions =  new LinkedHashSet<Object[]>();
		questions.clear();
		for (Map.Entry<String, String> entry : modulesSelected.entrySet()) {
		    String module       = entry.getKey();
		    Integer nbrQuestions = (Integer.parseInt(entry.getValue()));
		    questions.addAll(examenService.getAllQuestionsQuizByModule(module, nbrQuestions));
		    System.out.println(questions.size());
		    // ...
		}
		
		for(Object[] o:questions){
			System.out.println("module "+o[0] + "question "+o[1]+"reponse "+o[2]);
		}
		
	}
	
	public void getNumberOfQuestion(){
		System.out.println("number selected");
	}
	
	
	
	public List<Object[]> getModules() {
		return modules;
	}
	public void setModules(List<Object[]> modules) {
		this.modules = modules;
	}


	public DualListModel<String> getDualListModules() {
		return dualListModules;
	}


	public void setDualListModules(DualListModel<String> dualListModules) {
		this.dualListModules = dualListModules;
	}


	public List<String> getModulesTarget() {
		return modulesTarget;
	}


	public void setModulesTarget(List<String> modulesTarget) {
		this.modulesTarget = modulesTarget;
	}


	public List<String> getModulesSources() {
		return modulesSources;
	}


	public void setModulesSources(List<String> modulesSources) {
		this.modulesSources = modulesSources;
	}


	

	public List<QuizDTO> getModulesTargetFormatted() {
		return modulesTargetFormatted;
	}

	public void setModulesTargetFormatted(List<QuizDTO> modulesTargetFormatted) {
		this.modulesTargetFormatted = modulesTargetFormatted;
	}

	public boolean isValidateEditNumbreQuestion() {
		return validateEditNumbreQuestion;
	}

	public void setValidateEditNumbreQuestion(boolean validateEditNumbreQuestion) {
		this.validateEditNumbreQuestion = validateEditNumbreQuestion;
	}

	public QuizDTO getQuizDto() {
		return quizDto;
	}

	public void setQuizDto(QuizDTO quizDto) {
		this.quizDto = quizDto;
	}

	public Map<String, String> getModulesSelected() {
		return modulesSelected;
	}

	public void setModulesSelected(Map<String, String> modulesSelected) {
		this.modulesSelected = modulesSelected;
	}

	public Set<Object[]> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<Object[]> questions) {
		this.questions = questions;
	}

	public int getMin() {
		return min;
	}


	

	


	


	
	
	
	
}
