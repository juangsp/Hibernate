package controlador;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

import javax.swing.JCheckBox;
import javax.swing.JTextField;

import org.hibernate.mapping.Collection;

import javabeans.Answer;
import javabeans.Question;
import vista.TabbedWindow;
import model.HibernateHelper;

public class Controller {
	TabbedWindow view;
	HibernateHelper model;
	
	
	Controller() {
		
		view = new TabbedWindow();
		model = new HibernateHelper();	

		launchMainWindow();		
		
		loadCategories();	
		
		actionSave();
		actionBuscar();
		actionUpdate();
		actionDelete();
		actionClean();
		
	}


	private void launchMainWindow() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					view.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	private void actionSave() {
		view.getBtnSaveQuestion().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sQuestion;
				String sCategory;
				int id;
				String answer1;
				String answer2;
				String answer3;
				String answer4;
				boolean a1;
				boolean a2;
				boolean a3;
				boolean a4;
				
				
				
				sQuestion = view.gettQuestion().getText();
				sCategory = (String) view.getCategory().getSelectedItem();
				id = model.getLastQuestion().getIdQuestion();
				id++;
				
				System.out.println("save question:"+id+ " "+sQuestion+ " "+sCategory);
				model.addQuestion(id,sQuestion,sCategory,null);	
				
				answer1=view.gettAnswer1().getText();
				answer2=view.gettAnswer2().getText();
				answer3=view.gettAnswer3().getText();
				answer4=view.gettAnswer4().getText();
				
				a1=view.getChCorrect1().isSelected();
				a2=view.getCheckBox().isSelected();
				a3=view.getCheckBox_1().isSelected();
				a4=view.getCheckBox_2().isSelected();
				id=model.getLastAnswer().getIdAnswer();
				id++;
				Question lastQuestion = model.getLastQuestion();
				model.addAnswer(id, lastQuestion , answer1, a1);
				id++;
				model.addAnswer(id, model.getLastQuestion(), answer2, a2);
				id++;
				model.addAnswer(id, model.getLastQuestion(), answer3, a3);
				id++;
				model.addAnswer(id, model.getLastQuestion(), answer4, a4);
			
			}
		});
	}
	
	private void actionBuscar() {
		view.getBtnBuscar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int id=Integer.parseInt(view.getTextFieldId().getText());
				
				Question question=model.getQuestion(id);
				Set<Answer> sAnswers = question.getAnswers();
				ArrayList<Answer> mAnswers = new ArrayList<>();
				
				for(Answer a: question.getAnswers()){
					mAnswers.add(a);
				}
				
				Collections.sort(mAnswers, new Comparator<Answer>() {
						public int compare(Answer answer1, Answer answer2) {
							return answer1.getIdAnswer()- answer2.getIdAnswer();
						}
				});
				
				view.settQuestion(question.getText().toString());
				view.getCategory().setSelectedItem(question.getCategory());
				view.settAnswer1(mAnswers.get(0).getText().toString());
				view.settAnswer2(mAnswers.get(1).getText().toString());
				view.settAnswer3(mAnswers.get(2).getText().toString());
				view.settAnswer4(mAnswers.get(3).getText().toString());
				view.setChCorrect1(mAnswers.get(0).getIsCorrect());
				view.setCheckBox(mAnswers.get(1).getIsCorrect());
				view.setCheckBox_1(mAnswers.get(2).getIsCorrect());
				view.setCheckBox_2(mAnswers.get(3).getIsCorrect());
				
			
			}
		});
	}
	

	private void actionUpdate() {
		view.getBtnUpdateQuestion().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String answer1;
				String answer2;
				String answer3;
				String answer4;
				boolean a1;
				boolean a2;
				boolean a3;
				boolean a4;
				int id;
				
				id=Integer.parseInt(view.getTextFieldId().getText());
				Question question=model.getQuestion(id);
				Set<Answer> sAnswers = question.getAnswers();
				ArrayList<Answer> mAnswers = new ArrayList<>();
				
				for(Answer a: question.getAnswers()){
					mAnswers.add(a);
				}
				Collections.sort(mAnswers, new Comparator<Answer>() {
					public int compare(Answer answer1, Answer answer2) {
						return answer1.getIdAnswer()- answer2.getIdAnswer();
					}
			});
			
				
				answer1=view.gettAnswer1().getText();
				answer2=view.gettAnswer2().getText();
				answer3=view.gettAnswer3().getText();
				answer4=view.gettAnswer4().getText();
				
				a1=view.getChCorrect1().isSelected();
				a2=view.getCheckBox().isSelected();
				a3=view.getCheckBox_1().isSelected();
				a4=view.getCheckBox_2().isSelected();
				
				model.updateQuestion(question);
				model.updateAnswer(new Answer(mAnswers.get(0).getIdAnswer(),question,answer1,a1));
				model.updateAnswer(new Answer(mAnswers.get(1).getIdAnswer(),question,answer2,a2));
				model.updateAnswer(new Answer(mAnswers.get(2).getIdAnswer(),question,answer3,a3));
				model.updateAnswer(new Answer(mAnswers.get(3).getIdAnswer(),question,answer4,a4));
			
			}
		});
	}
	
	private void actionDelete() {
		view.getBtnDeleteQuestion().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int id;
				
				id=Integer.parseInt(view.getTextFieldId().getText());
				Question question=model.getQuestion(id);
				Set<Answer> sAnswers = question.getAnswers();
				ArrayList<Answer> mAnswers = new ArrayList<>();
				
				for(Answer a: question.getAnswers()){
					mAnswers.add(a);
				}
				Collections.sort(mAnswers, new Comparator<Answer>() {
					public int compare(Answer answer1, Answer answer2) {
						return answer1.getIdAnswer()- answer2.getIdAnswer();
					}
			});
			
				
				
				model.deleteAnswer(mAnswers.get(0).getIdAnswer());
				model.deleteAnswer(mAnswers.get(1).getIdAnswer());
				model.deleteAnswer(mAnswers.get(2).getIdAnswer());
				model.deleteAnswer(mAnswers.get(3).getIdAnswer());
				model.deleteQuestion(id);
				
			
			}
		});
	}
	
	private void actionClean(){
		view.getBtnCleanQuestion().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				view.settQuestion(" ");
				view.settAnswer1(" ");
				view.settAnswer2(" ");
				view.settAnswer3(" ");
				view.settAnswer4(" ");
				view.setChCorrect1(false);
				view.setCheckBox(false);
				view.setCheckBox_1(false);
				view.setCheckBox_2(false);
				
				
			
			}
		});
		
	}
	
	


	private void loadCategories() {
		view.getCategory().addItem("Matemáticas");
		view.getCategory().addItem("Historia");
		view.getCategory().addItem("Fisica");
		view.getCategory().addItem("Biologia");
	}



}
