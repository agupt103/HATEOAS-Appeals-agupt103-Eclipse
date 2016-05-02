package edu.asu.arpit.assignment3.activities;

import edu.asu.arpit.assignment3.model.Appeal;
import edu.asu.arpit.assignment3.model.Identifier;
import edu.asu.arpit.assignment3.repositories.AppealRepository;
import edu.asu.arpit.assignment3.representations.AppealRepresentation;
import edu.asu.arpit.assignment3.representations.RestbucksUri;

public class ReadAppealActivity {
	 public AppealRepresentation retrieveByUri(RestbucksUri appealUri) throws Exception {
	        Identifier identifier  = appealUri.getId();
	        
	        Appeal appeal = AppealRepository.current().get(identifier);
	        
	        if(appeal == null) {
	            throw new Exception();
	        }
	        
	        return AppealRepresentation.createResponseAppealRepresentation(appeal, appealUri);
}
}
