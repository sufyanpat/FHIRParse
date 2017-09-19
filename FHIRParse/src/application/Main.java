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

	Button btnParse;

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("XML JSON Parser");

		Label lblHeader = new Label();
		lblHeader.setText("FHIR Parser");
		lblHeader.setFont(new Font("Arial", 30));
		
		MenuBar menuBar = new MenuBar();
		Menu menuAbout = new Menu("About");
		MenuItem menuItemContact = new MenuItem("Contact");
		menuAbout.getItems().addAll(menuItemContact);
		menuBar.getMenus().addAll(menuAbout);
		
		
		menuItemContact.setOnAction(new EventHandler<ActionEvent>() {
		     
	        @Override public void handle(ActionEvent e) {
	        	Alert alert = new Alert(AlertType.INFORMATION);
	        	alert.setTitle("Contact");
	        	alert.setHeaderText(null);
	        	alert.setContentText("Contact your friendly neighbourhood Suf");

	        	alert.showAndWait();
	        }
	    });
		
		btnParse = new Button();
		btnParse.setText("Parse!");
		
		TextField inputfilename = new TextField ();
		inputfilename.setMinWidth(500);
		inputfilename.setEditable(false);
		
		TextField outputfilename = new TextField ();
		outputfilename.setMinWidth(500);
		outputfilename.setEditable(false);
		
		Label lblconv = new Label("Step 1 - Select Conversion Type");
		Label label1 = new Label("Step 2 - Select Input File");
		Label label2 = new Label("Step 3 - Select the type of resource");
		Label label3 = new Label("Step 4 - Select destination path");
		Label label4 = new Label("Step 5 - Click 'Parse!'");
		
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
		
		inputfilename.setOnMouseClicked(e -> {
			FileChooser fc = new FileChooser();
    	    fc.setTitle("Select XML file");
    	    fc.getExtensionFilters().addAll(
    	    new ExtensionFilter("Text Files", "*.xml"),
    	    new ExtensionFilter("Text Files", "*.json"),
    	    new ExtensionFilter("All Files", "*.*"));
    	   	File phil = fc.showOpenDialog(primaryStage);
    	   	inputfilename.setText(phil.toString());
			}
		);

		
		outputfilename.setOnMouseClicked(e -> {
			DirectoryChooser directoryChooser = new DirectoryChooser(); 
            directoryChooser.setTitle("Select output folder");

            //Show open file dialog
            File file = directoryChooser.showDialog(null);
               if(file!=null){
            	   outputfilename.setText(file.getPath());
            }
		});
		
		
		btnParse.setOnAction(
					new EventHandler<ActionEvent>() {
	            	
					@Override
					public void handle(ActionEvent event) {
						System.out.println("type= "+conversionType.getValue());
						if (inputfilename.getText().isEmpty() || resourceList.getValue()=="Select Resource Type"
								|| outputfilename.getText().isEmpty() || (conversionType.getValue()==null)){
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
						
						
						String[] ar = new String[3];
						
//						System.out.println("resourceList.getValue()= "+resourceList.getValue());
//						System.out.println("xmlfilename.getText()= "+xmlfilename.getText());
//						System.out.println("outputfilename.getText()= "+outputfilename.getText());
//						
						ar[0] = resourceList.getValue();
						ar[1]= inputfilename.getText();
						ar[2] = outputfilename.getText();
						
						
	            	    try {
	            	    	
	            	    	if (conversionType.getValue().equals("XML > JSON")){
	    						System.out.println("type= "+conversionType.getValue());

	            	    		ParseToJSON.main(ar);
	            	    	}
	            	    	else if (conversionType.getValue().equals("JSON > XML")) {
	    						System.out.println("type= "+conversionType.getValue());

	            	    		ParseToXML.main(ar);
	            	    	}
							lblDone.setVisible(true);
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
        
        GridPane.setConstraints(label2, 0, 5);
        GridPane.setConstraints(resourceList, 1, 5);
        
        GridPane.setConstraints(label3, 0, 6);
        GridPane.setConstraints(outputfilename, 1,6);
        
        GridPane.setConstraints(label4, 0, 7);
        GridPane.setConstraints(btnParse, 1, 7);
        
        GridPane.setConstraints(lblDone, 1, 8);
        
        inputGridPane.setHgap(10);
        inputGridPane.setVgap(10);
        inputGridPane.getChildren().addAll(lblDone,inputfilename, btnParse, label1, label2, label3, 
        		label4, resourceList, outputfilename, lblHeader, conversionType,lblconv);
 
        final Pane rootGroup = new VBox(12);
        rootGroup.getChildren().addAll(root,inputGridPane);
        rootGroup.setPadding(new Insets(0, 12, 12, 12));
 
        primaryStage.setScene(new Scene(rootGroup));
//        primaryStage.setScene(scene);
        primaryStage.show();
	}



}