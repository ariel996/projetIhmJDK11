package presentation ;

import java.util.List ;

import controle.PatientsRecord_C ;
import javafx.beans.value.ChangeListener ;
import javafx.beans.value.ObservableValue ;
import javafx.collections.FXCollections ;
import javafx.collections.ObservableList ;
import javafx.event.ActionEvent ;
import javafx.event.EventHandler ;
import javafx.scene.control.Button ;
import javafx.scene.control.ListView ;
import javafx.scene.control.Tab ;
import javafx.scene.layout.BorderPane ;
import javafx.scene.layout.FlowPane ;
import javafx.stage.Stage ;

//-------------------------------------------------------------------------------------
// classe de présentation de la liste des patients : c'est un onglet
//-------------------------------------------------------------------------------------

public class PatientsRecord_P extends Tab {

   private ObservableList<String> patientsNames ;
   private ListView<String> patientsList ;
   private int currentIndex ;
   private BorderPane globalPane ;
   private Button editPatientButton ;
   private Button deletePatientButton ;
   private Stage stage ;
 
   //-------------------------------------------------------------------------------------
   // constructeur : on mémorise seulement le stage principal
   //-------------------------------------------------------------------------------------
   public PatientsRecord_P (Stage stage) {
	   this.stage = stage ;	   
   }
   
   //-------------------------------------------------------------------------------------
   // initialisation : on organise la présentation en accord avec le contenu à gérer
   //-------------------------------------------------------------------------------------   
   public void initialize (PatientsRecord_C control, List<String> patients) {
      // liste des noms des patients à lister
      patientsNames = FXCollections.observableArrayList (patients) ;
      // remplissage du widget de présentation des noms avec la liste des noms des docteurs
      patientsList = new ListView<String> (patientsNames) ;
      // ajout d'un listener sur la liste pour savoir quand un patient est sélectionné ou pas
      patientsList.getSelectionModel().selectedItemProperty().addListener (new ChangeListener<String> () {
         @Override
         public void changed (ObservableValue < ? extends String> ov, String old_val, String new_val) {
            if (! patientsList.getSelectionModel ().isEmpty ()) {
               // on essaie de trouver l'index de l'élément sélectionné
               currentIndex = patientsList.getSelectionModel ().getSelectedIndex () ;
               // on le transfère au contrôle pour mémorisation
               control.selectPatient (currentIndex) ;
            } else {
               // aucun docteur n'est plus sélectionné : on le signale au contrôle
               control.selectNoPatient () ;
            }
         }
      }) ;
      
      // ajout de boutons New/Edit/Delete et des gestionnaires d'événements associés
      // c'est à chaque fois le controleur associé qui va gérer l'action à déclencher
      FlowPane buttons = new FlowPane () ;
      Button newPatientButton = new Button () ;
      newPatientButton.setText ("New Patient...") ;
      newPatientButton.setOnAction (new EventHandler<ActionEvent> () {
         @Override
         public void handle (ActionEvent event) {
            control.createPatient () ;
         }
      }) ;
      editPatientButton = new Button () ;
      editPatientButton.setText ("Edit Patient...") ;
      editPatientButton.setOnAction (new EventHandler<ActionEvent> () {
         @Override
         public void handle (ActionEvent event) {
            control.editPatient () ;
         }
      }) ;
      deletePatientButton = new Button () ;
      deletePatientButton.setText ("Delete") ;
      deletePatientButton.setOnAction (new EventHandler<ActionEvent> () {
         @Override
         public void handle (ActionEvent event) {
            control.deletePatient () ;
         }
      }) ;
      disableActionOnSelection () ;
      buttons.getChildren ().addAll (newPatientButton, editPatientButton, deletePatientButton) ;
      globalPane = new BorderPane () ;
      globalPane.setLeft (patientsList) ;
      globalPane.setBottom (buttons);

      setText ("Patients") ;
      setContent (globalPane) ;
      setClosable (false) ;
   }
   
   public Stage getStage () {
      return stage ;
   }


   //-------------------------------------------------------------------------------------
   // méthodes appelées par le contrôle associé suite à des actions de l'utilisateur ou à des changements d'états
   //-------------------------------------------------------------------------------------

   //-------------------------------------------------------------------------------------
   // désélection d'un patient
   //-------------------------------------------------------------------------------------

   public void selectNoPatient () {
      patientsList.getSelectionModel ().clearSelection () ;
      globalPane.setCenter (null) ;
      globalPane.autosize () ;
   }

   //-------------------------------------------------------------------------------------
   // sélection d'un patient : deux méthodes pour être plus précis dans ce que l'on demande
   //-------------------------------------------------------------------------------------

   public void selectPatient (Patient_P currentPatient) {
      globalPane.setCenter (currentPatient) ;
   }

   public void selectPatient (String currentPatientName) {
      patientsList.getSelectionModel ().select (currentPatientName) ;
   }

   //-------------------------------------------------------------------------------------
   // activation des fonctions d'édition et de suppression
   //-------------------------------------------------------------------------------------
   public void enableActionOnSelection () {
      editPatientButton.setDisable (false) ;
      deletePatientButton.setDisable (false) ;      
   }

   //-------------------------------------------------------------------------------------
   // désactivation des fonctions d'édition et de suppression
   //-------------------------------------------------------------------------------------
   public void disableActionOnSelection () {
      editPatientButton.setDisable (true) ;
      deletePatientButton.setDisable (true) ;                        
   }

   //-------------------------------------------------------------------------------------
   // désactivation de la fonction de suppression
   //-------------------------------------------------------------------------------------
   public void disableDeleteOnSelection () {
      deletePatientButton.setDisable (true) ;      
   }


}
