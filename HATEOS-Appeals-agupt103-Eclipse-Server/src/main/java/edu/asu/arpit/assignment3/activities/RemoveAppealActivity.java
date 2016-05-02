package edu.asu.arpit.assignment3.activities;

import edu.asu.arpit.assignment3.model.Appeal;
import edu.asu.arpit.assignment3.model.AppealStatus;
import static edu.asu.arpit.assignment3.model.AppealStatus.DELETED;
import edu.asu.arpit.assignment3.model.Identifier;
import edu.asu.arpit.assignment3.repositories.AppealRepository;
import edu.asu.arpit.assignment3.representations.AppealRepresentation;
import edu.asu.arpit.assignment3.representations.RestbucksUri;

public class RemoveAppealActivity {
	 public AppealRepresentation delete(RestbucksUri appealUri) throws Exception {
	        // Discover the URI of the order that has been cancelled
	        
	        Identifier identifier = appealUri.getId();

	        AppealRepository appealRepository = AppealRepository.current();

	        if (appealRepository.appealNotPlaced(identifier)) {
	            throw new Exception();
	        }

	        Appeal appeal = appealRepository.get(identifier);

	        // Can't delete a ready or preparing order
	        if (appeal.getStatus() == AppealStatus.APPROVED ) {
	            throw new Exception();
	        }

	        if(appeal.getStatus() == AppealStatus.PENDING) { // A pending appeal is deleted 
	            appealRepository.remove(identifier);
	        }
            appeal.setStatus(DELETED);
	        return new AppealRepresentation(appeal);
	    }
}
