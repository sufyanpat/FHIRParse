package application;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class Main extends Application {

	Button btnParse, btnSelectInputFile, btnOutFolder;
	File file;

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("FHIR Parser");

		Label lblHeader = new Label();
		lblHeader.setText("FHIR Parser");
		lblHeader.setFont(new Font("Arial", 30));
		
		MenuBar menuBar = new MenuBar();
		Menu menuAbout = new Menu("About");
		MenuItem menuItemContact = new MenuItem("Contact");
		MenuItem menuItemVersion = new MenuItem("Version");

		menuAbout.getItems().addAll(menuItemContact,menuItemVersion);
		menuBar.getMenus().addAll(menuAbout);
		
		
		menuItemContact.setOnAction(new EventHandler<ActionEvent>() {
		     
	        @Override public void handle(ActionEvent e) {
	        	Alert alert = new Alert(AlertType.INFORMATION);
	        	alert.setTitle("Contact");
	        	alert.setHeaderText(null);
	        	alert.setContentText("Contact your friendly neighbourhood Suf");

	        	alert.showAndWait();
	        }
	    }
				);
		
		menuItemVersion.setOnAction(new EventHandler<ActionEvent>() {
		     
	        @Override public void handle(ActionEvent e) {
	        	Alert alert = new Alert(AlertType.INFORMATION);
	        	alert.setTitle("Version");
	        	alert.setHeaderText(null);
	        	alert.setContentText("FHIRParse v2.1");

	        	alert.showAndWait();
	        }
	    });
		
		
		
		btnParse = new Button();
		btnParse.setText("Parse!");
		btnSelectInputFile = new Button();
		btnSelectInputFile.setText("Input File");
		btnOutFolder = new Button();
		btnOutFolder.setText("Select Output folder");
		
		TextField inputfilename = new TextField ();
		inputfilename.setMinWidth(500);
		inputfilename.setEditable(false);
		
		TextField outputfilename = new TextField ();
		outputfilename.setMinWidth(500);
		outputfilename.setEditable(false);
		
		Label lblconv = new Label("Select Conversion Type");
		Label label1 = new Label("Select Input File");
		Label label3 = new Label("Select destination path");
		Label label4 = new Label("Click 'Parse!'");
		
		Label lblDone = new Label("Done!");

		lblDone.setVisible(false);
		
		ChoiceBox<String> conversionType = new ChoiceBox<>();
		conversionType.getItems().addAll("XML > JSON", "JSON > XML");
		
		ChoiceBox<String> resourceList = new ChoiceBox<>();
		//populate the drop down with all the resources
		resourceList.getItems().addAll("Select Resource Type","Account",
				"ActivityDefinition","AllergyIntolerance","AdverseEvent","Appointment",	"AppointmentResponse","AuditEvent",
				"Basic","Binary","BodySite","Bundle","CapabilityStatement","CarePlan","CareTeam","ChargeItem","Claim",
				"ClaimResponse","ClinicalImpression","CodeSystem","Communication","CommunicationRequest","CompartmentDefinition",
				"Composition","ConceptMap","Condition (aka Problem)","Consent","Contract","Coverage","DataElement","DetectedIssue",
				"Device","DeviceComponent","DeviceMetric","DeviceRequest","DeviceUseStatement","DiagnosticReport","DocumentManifest",
				"DocumentReference","EligibilityRequest","EligibilityResponse","Encounter","Endpoint","EnrollmentRequest",
				"EnrollmentResponse","EpisodeOfCare","ExpansionProfile","ExplanationOfBenefit","FamilyMemberHistory","Flag",
				"Goal","GraphDefinition","Group","GuidanceResponse","HealthcareService","ImagingManifest","ImagingStudy",
				"Immunization","ImmunizationRecommendation","ImplementationGuide","Library","Linkage","List","Location",
				"Measure","MeasureReport","Media","Medication","MedicationAdministration","MedicationDispense","MedicationRequest",	"MedicationStatement",
				"MessageDefinition","MessageHeader","NamingSystem","NutritionOrder","Observation","OperationDefinition","OperationOutcome",
				"Organization",	"Parameters","Patient","PaymentNotice","PaymentReconciliation","Person","PlanDefinition",
				"Practitioner","PractitionerRole","Procedure","ProcedureRequest","ProcessRequest","ProcessResponse",
				"Provenance","Questionnaire","QuestionnaireResponse","ReferralRequest","RelatedPerson","RequestGroup",
				"ResearchStudy","ResearchSubject","RiskAssessment","Schedule","SearchParameter","Sequence","ServiceDefinition",
				"Slot","Specimen","StructureDefinition","StructureMap","Subscription","Substance","SupplyDelivery","SupplyRequest",
				"Task","TestScript","TestReport","ValueSet","VisionPrescription");
		//set default value
		resourceList.setValue("Select Resource Type");
		
/*		inputfilename.setOnMouseClicked(e -> {
			FileChooser fc = new FileChooser();
			
    	    fc.setTitle("Select XML file");
    	    fc.getExtensionFilters().addAll(
    	    new ExtensionFilter("Text Files", "*.xml"),
    	    new ExtensionFilter("Text Files", "*.json"),
    	    new ExtensionFilter("All Files", "*.*"));
    	   	
    	    File phil = fc.showOpenDialog(primaryStage);
    	
    	    inputfilename.setText(phil.toString());
			}
		);*/

		
/*		outputfilename.setOnMouseClicked(e -> {
			DirectoryChooser directoryChooser = new DirectoryChooser(); 
            directoryChooser.setTitle("Select output folder");

            //Show open file dialog
            File file = directoryChooser.showDialog(null);
               if(file!=null){
            	   outputfilename.setText(file.getPath());
            }
		});*/
		
		btnSelectInputFile.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                System.out.println("input file= "+file);

                //Open directory from existing directory
                if(file != null){
                    File existDirectory = file.getParentFile();
                    fileChooser.setInitialDirectory(existDirectory);
                }

                //Set extension filter
                
                //FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("*.xml", "*.json");
                fileChooser.getExtensionFilters().addAll(
                		new ExtensionFilter("All Files", "*.*"),
                		new ExtensionFilter("XML Files", "*.xml"),
                		new ExtensionFilter("JSON Files", "*.json"));
                 
                //Show open file dialog
                file = fileChooser.showOpenDialog(null);
                if (file != null) {
                    inputfilename.setText(file.getPath());
                }
            }
        });
		
		
		btnOutFolder.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
            	DirectoryChooser directoryChooser = new DirectoryChooser(); 
                directoryChooser.setTitle("Select output folder");
                
                //Open directory from existing directory
                System.out.println("Output folder= "+file);
                if(file != null){
                    File existDirectory = file.getParentFile();
                    directoryChooser.setInitialDirectory(existDirectory);
                }

                //Show open file dialog
                file = directoryChooser.showDialog(null);
                
	                if(file!=null){
	             	   outputfilename.setText(file.getPath());
	                }
                }
        });
		
		
		btnParse.setOnAction(
					new EventHandler<ActionEvent>() {
	            	
					@Override
					public void handle(ActionEvent event) {
						
						if (inputfilename.getText().isEmpty() || outputfilename.getText().isEmpty() || (conversionType.getValue()==null)){
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("IOException");
							alert.setHeaderText("Error");
							alert.setContentText("Please check your entries. One or more values may be empty.");
							alert.showAndWait();
							return;
						}
						
						String ext2 = FilenameUtils.getExtension(inputfilename.getText().toLowerCase());
						if ((conversionType.getValue().equals("XML > JSON") && !ext2.equals("xml")) 
								||(conversionType.getValue().equals("JSON > XML") && !ext2.equals("json"))){
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("File type error");
							alert.setHeaderText("Error");
							alert.setContentText("You have specified the incorrect parser for the input file.");
							alert.showAndWait();
							return;
						}
						
						
						String[] ar = new String[2];
						
						//ar[0] = resourceList.getValue();
						ar[0]= inputfilename.getText();
						ar[1] = outputfilename.getText();
						
						
	            	    try {
	            	    	if (conversionType.getValue().equals("XML > JSON")){

	            	    		ParseToJSON.main(ar);
	            	    	}
	            	    	else if (conversionType.getValue().equals("JSON > XML")) {

	            	    		ParseToXML.main(ar);
	            	    	}
	            	    	
	            	    	//pop up box after completion
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setHeaderText("Done");
							alert.setTitle("Complete");
	            	    	alert.showAndWait();

						} catch (IOException e) {
							
							e.printStackTrace();
						}
	            	    
					}
				
		});
		
		BorderPane root = new BorderPane();
        root.setTop(menuBar);
        //Scene scene = new Scene(root);
        		
		final GridPane inputGridPane = new GridPane();
		GridPane.setConstraints(lblHeader, 0, 0);
		
		GridPane.setConstraints(lblconv, 0, 2);
        GridPane.setConstraints(conversionType, 1, 2);
        
		GridPane.setConstraints(label1, 0, 3);
        GridPane.setConstraints(inputfilename, 1, 3);
        GridPane.setConstraints(btnSelectInputFile, 2, 3);

        GridPane.setConstraints(label3, 0, 4);
        GridPane.setConstraints(outputfilename, 1,4);
        GridPane.setConstraints(btnOutFolder, 2, 4);

        GridPane.setConstraints(label4, 0, 7);
        GridPane.setConstraints(btnParse, 1, 7);
        

        GridPane.setConstraints(lblDone, 1, 8);
        
        inputGridPane.setHgap(10);
        inputGridPane.setVgap(10);
        
        inputGridPane.getChildren().addAll(lblDone,inputfilename, btnParse, label1, label3, 
            		label4, outputfilename, lblHeader, conversionType,lblconv,btnSelectInputFile,btnOutFolder);
        
        final Pane rootGroup = new VBox(12);
        rootGroup.getChildren().addAll(root,inputGridPane);
        rootGroup.setPadding(new Insets(0, 12, 12, 12));
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(rootGroup));
//        primaryStage.setScene(scene);
        primaryStage.show();
	}
}