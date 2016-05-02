package edu.asu.arpit.assignment3.client;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

import edu.asu.arpit.assignment3.model.Appeal;
import edu.asu.arpit.assignment3.model.Grade;
import edu.asu.arpit.assignment3.representations.AppealRepresentation;
import edu.asu.arpit.assignment3.representations.GradeRepresentations;
import edu.asu.arpit.assignment3.representations.Link;
import edu.asu.arpit.assignment3.representations.RestbucksUri;
import static edu.asu.arpit.assignment3.model.AppealStatus.APPROVED;
import static edu.asu.arpit.assignment3.model.AppealStatus.REJECTED;;

public class Main {
	 private static final Logger LOG = LoggerFactory.getLogger(Main.class);
	    private static final String GRADEAPPEAL_MEDIA_TYPE = "application/vnd.cse564-appeals+xml";
	    private static final String ENTRY_POINT_URI = "http://localhost:8080/HATEOS-Appeals-agupt103-Eclipse-Server/webresources/grades";
	    private static final String ENTRY_POINT_URI1 = "http://localhost:8080/HATEOS-Appeals-agupt103-Eclipse-Server/webresources/appeal";
	     
	    public static void main(String[] args) throws Exception {
	        URI serviceUri = new URI(ENTRY_POINT_URI);
	        URI serviceUri1 = new URI(ENTRY_POINT_URI1);
	        System.out.println("*****Happy Path Test*****");
	        happyPathTest(serviceUri, serviceUri1);
	        System.out.println();
	        System.out.println("*****Abandon Path Test*****");
	        pathAbadonTest(serviceUri1);
	        System.out.println();
	        System.out.println("*****Follow up Appeal Test****");
	        followUpAppeal(serviceUri1);
	    }
	    
	    
	    private static void BadPathTest(URI serviceUri2) {
			// TODO Auto-generated method stub
	    	 System.out.println();			
		}

		private static void happyPathTest(URI serviceUri, URI serviceUri1) throws Exception {
	        // Place the appeal
	        LOG.info("Step 1. Professors posts the grades");
	   
	        Client client = Client.create(); //initialize client - from jersey
	        Grade grade = new Grade('B');
	        GradeRepresentations gradeRepresentation = client.resource(serviceUri).accept(GRADEAPPEAL_MEDIA_TYPE).type(GRADEAPPEAL_MEDIA_TYPE).post(GradeRepresentations.class, grade);
	        LOG.debug("Created grade representation {} by the URI {}", gradeRepresentation, gradeRepresentation.getSelfLink().getUri().toString());
	        System.out.println(String.format("Grades posted at [%s]", gradeRepresentation.getSelfLink().getUri().toString()));
	          
	        System.out.println();
	        //compose the appeal
	        LOG.info("Step 2. Students posts the appeal");
	        StringBuilder AppealRequest = new StringBuilder();
	        AppealRequest.append("I am posting this appeal to update my grades");
	        Appeal appeal = new Appeal(AppealRequest);
	        AppealRepresentation appealRepresentation = client.resource(serviceUri1).accept(GRADEAPPEAL_MEDIA_TYPE).type(GRADEAPPEAL_MEDIA_TYPE).post(AppealRepresentation.class, appeal);
	        LOG.debug("Created AppealRepresentation {} denoted by the URI {}", appealRepresentation, appealRepresentation.getSelfLink().getUri().toString());
	        System.out.println(String.format("Appeals posted at [%s]", appealRepresentation.getSelfLink().getUri().toString())); 
	       
	        System.out.println();
	        // Get the appeal
	        LOG.debug("\n\nStep 3. Get the appeal");
	        System.out.println(String.format("Starting to request appeal from [%s] via GET", appealRepresentation.getSelfLink().getUri().toString()));
	        Link appealLink = appealRepresentation.getSelfLink();
	        AppealRepresentation postAppealRepresentation = client.resource(appealLink.getUri()).accept(GRADEAPPEAL_MEDIA_TYPE).get(AppealRepresentation.class);
	        System.out.println(String.format("Appeal successfully placed with current status [%s] and placed at %s", postAppealRepresentation.getStatus(), postAppealRepresentation.getSelfLink())); 
	    
	        System.out.println(); 
	        LOG.debug("\n\nStep 4. Professor gets the appeal, reviews and updates the appeal to approve");
	        System.out.println(String.format("Starting to update appeal at [%s] via POST", postAppealRepresentation.getUpdateLink().getUri().toString()));
	        Appeal ap = new Appeal(postAppealRepresentation.getAppeal().getStatus());
	        Link upLink = postAppealRepresentation.getUpdateLink();
	        ap.setStatus(APPROVED);
	        AppealRepresentation upRepresentation = client.resource(upLink.getUri()).accept(upLink.getMediaType()).type(upLink.getMediaType()).post(AppealRepresentation.class, ap );
	        System.out.println(String.format("Appeal successfully updated by Professor at [%s] and status is %s", upRepresentation.getSelfLink().getUri().toString(),ap.getStatus()));
	        
	        System.out.println();
	        LOG.debug("\n\n Step 5. Professors successfully approves the appeal and gets the grade increases the grade");
	        LOG.debug("\n\n Get the grade");
	        System.out.println(String.format("Starting to request grade from [%s] via GET", gradeRepresentation.getSelfLink().getUri().toString()));
	        Link gradeLink = gradeRepresentation.getSelfLink();
	        
	        GradeRepresentations postGradeRepresentation = client.resource(gradeLink.getUri()).accept(GRADEAPPEAL_MEDIA_TYPE).get(GradeRepresentations.class);
	        System.out.println(String.format("Final grade placed successfully at [%s]", postGradeRepresentation.getSelfLink().getUri().toString())); 
	     
	        System.out.println("Yahoo");
	        Link newLink = postGradeRepresentation.getUpdateLink();
	        System.out.println("Grade is successfully updated"); 
	                
	        //Try to update a grades with bad path
	        System.out.println();
	        System.out.println("bad path test");
	        LOG.info("\n\nStep 6. Updating an appeal with a BAD Path");
	        System.out.println(String.format("Starting to update an grades with bad URI [%s] via POST", appealRepresentation.getUpdateLink().getUri().toString() + "/bad-uri"));
	        StringBuilder AppealRequest1 = new StringBuilder();
	        AppealRequest1.append("I am posting this appeal to update my grades");
	        Link badLink = new Link("bad", new RestbucksUri(appealRepresentation.getSelfLink().getUri().toString() + "/bad-uri"), GRADEAPPEAL_MEDIA_TYPE);
	        ClientResponse badUpdateResponse = client.resource(badLink.getUri()).accept(badLink.getMediaType()).type(badLink.getMediaType()).post(ClientResponse.class, new AppealRepresentation(appeal));
	        LOG.debug("Created Bad Update Response {}", badUpdateResponse);
	        System.out.println(String.format("Tried to update appeal with bad URI at [%s] via POST, outcome [%d]", badLink.getUri().toString(), badUpdateResponse.getStatus()));
	        
	        //Appeal updation by student
	        System.out.println();
	        LOG.debug("\n\nStep 7. Update appeal with good ID");
	        System.out.println(String.format("Starting to update appeal at [%s] via POST", postAppealRepresentation.getUpdateLink().getUri().toString()));
	        StringBuilder AppealRequest2 = new StringBuilder();
	        AppealRequest2.append("I am posting this appeal to update my grades");
	        Appeal appeal3 = new Appeal(AppealRequest2);        

	        Link updateLink = postAppealRepresentation.getUpdateLink();
	        AppealRepresentation updatedRepresentation = client.resource(updateLink.getUri()).accept(updateLink.getMediaType()).type(updateLink.getMediaType()).post(AppealRepresentation.class, appeal3);
	        LOG.debug("Created updated Appeal representation link {}", updatedRepresentation);
	        System.out.println(String.format("Appeal updated at [%s]", updatedRepresentation.getSelfLink().getUri().toString()));
	        
	        System.out.println();
	        //Try to update a grades with bad ID
	        System.out.println("Bad ID test");
	        LOG.info("\n\nStep 8. Updating an appeal with a BAD ID");
	        System.out.println(String.format("About to update an grades with bad ID [%s] via POST", appealRepresentation.getUpdateLink().getUri().toString() + "/bad-id"));
	        StringBuilder AppealRequest3 = new StringBuilder();
	        AppealRequest3.append("Please update my grades after reviewing my appeal");
	        LOG.debug("Created base appealID {}", appeal);
	        try
	        {
	        Link badID = new Link("bad", new RestbucksUri(appealRepresentation.getSelfLink().getUri().toString() + "/bad-id"), GRADEAPPEAL_MEDIA_TYPE);
	        LOG.debug("Create bad ID {}", badID);
	        AppealRepresentation badUpdateResponseID = client.resource(serviceUri).path("assignmentAppeal/badURI").accept(GRADEAPPEAL_MEDIA_TYPE).type(GRADEAPPEAL_MEDIA_TYPE).post(AppealRepresentation.class, badID);
	        LOG.debug("Created Bad Update Response {}", badUpdateResponseID);
	        System.out.println(String.format("Tried to update grades with bad ID at [%s] via POST, outcome [%d]", badID.getUri().toString(), badUpdateResponse.getStatus()));
	        }
	        catch(Exception e)
	        {
	        	System.out.println("Bad ID given");
	        }

	    }
	    
	    private static void pathAbadonTest(URI serviceUri1) throws Exception{
	      Client client = Client.create(); //initialize client - from jersey
	      LOG.info("Step 1. Students Post an appeal");
	      StringBuilder AppealRequest = new StringBuilder();
	      AppealRequest.append("I am posting this appeal to update my grades");
	      Appeal appeal = new Appeal(AppealRequest);
	      AppealRepresentation appealRepresentation = client.resource(serviceUri1).accept(GRADEAPPEAL_MEDIA_TYPE).type(GRADEAPPEAL_MEDIA_TYPE).post(AppealRepresentation.class, appeal);
	      LOG.debug("Created AppealRepresentation {} denoted by the URI {}", appealRepresentation, appealRepresentation.getSelfLink().getUri().toString());
	      System.out.println(String.format("Appeals posted at [%s]", appealRepresentation.getSelfLink().getUri().toString())); 
	       
	      // Get  appeal
	      System.out.println();
	      LOG.debug("\n\nStep 2. Get the appeal");
	      System.out.println(String.format("About to request appeal from [%s] via GET", appealRepresentation.getSelfLink().getUri().toString()));
	      Link appealLink = appealRepresentation.getSelfLink();
	      AppealRepresentation postAppealRepresentation = client.resource(appealLink.getUri()).accept(GRADEAPPEAL_MEDIA_TYPE).get(AppealRepresentation.class);
	      System.out.println(String.format("Appeal successfully placed, current status [%s], placed at %s", postAppealRepresentation.getStatus(), postAppealRepresentation.getSelfLink())); 
	      
	      //Abandons the appeal
	      System.out.println();  
	      LOG.debug("\n\nStep 3. Student abandons his or her appeal");
	      System.out.println(String.format("Trying to remove the pending appeal from [%s] via DELETE. ", postAppealRepresentation.getDeleteLink().getUri().toString()));
	      Link deleteLink = postAppealRepresentation.getSelfLink();
	      ClientResponse finalResponse = client.resource(deleteLink.getUri()).delete(ClientResponse.class);
	      System.out.println(String.format("Tried to delete appeal, HTTP status [%d]", finalResponse.getStatus()));
	      if(finalResponse.getStatus() == 200) {
	            System.out.println(String.format("Appeal status [%s],", finalResponse.getStatus()));
	        }
	    }
	    
	    private static void followUpAppeal(URI serviceUri1) throws Exception {
	       Client client = Client.create(); 
	       LOG.info("Step 1. Students Post an appeal");
	       StringBuilder AppealRequest = new StringBuilder();
	       AppealRequest.append("I am posting this appeal to update my grades");
	       Appeal appeal = new Appeal(AppealRequest);
	       AppealRepresentation appealRepresentation = client.resource(serviceUri1).accept(GRADEAPPEAL_MEDIA_TYPE).type(GRADEAPPEAL_MEDIA_TYPE).post(AppealRepresentation.class, appeal);
	       System.out.println(String.format("Appeals posted at [%s]", appealRepresentation.getSelfLink().getUri().toString())); 
           
	       System.out.println();
	       LOG.info("Student updates the appeal - with follow up comments");
	       System.out.println(String.format("About to update appeal at [%s] via POST", appealRepresentation.getUpdateLink().getUri().toString()));
	       StringBuilder AppealRequest2 = new StringBuilder();
	       AppealRequest2.append("I am posting this corrected appeal to update my grades");
	       Appeal appeal3 = new Appeal(AppealRequest2);        

	       Link updateLink = appealRepresentation.getUpdateLink();
	       AppealRepresentation updatedRepresentation = client.resource(updateLink.getUri()).accept(updateLink.getMediaType()).type(updateLink.getMediaType()).post(AppealRepresentation.class, appeal3);
	       LOG.debug("Updated Appeal representation link {}", updatedRepresentation);
	       System.out.println(String.format("Appeal updated at [%s]", updatedRepresentation.getSelfLink().getUri().toString()));
	       LOG.info("Appeal updated with follow up comments");
	        
	    }
	    
}
