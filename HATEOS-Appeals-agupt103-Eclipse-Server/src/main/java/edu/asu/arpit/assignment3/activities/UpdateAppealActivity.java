package edu.asu.arpit.assignment3.activities;

import edu.asu.arpit.assignment3.model.Appeal;
import edu.asu.arpit.assignment3.model.AppealStatus;
import edu.asu.arpit.assignment3.model.Identifier;
import edu.asu.arpit.assignment3.repositories.AppealRepository;
import edu.asu.arpit.assignment3.representations.AppealRepresentation;
import edu.asu.arpit.assignment3.representations.RestbucksUri;

public class UpdateAppealActivity {
	public AppealRepresentation update(Appeal appeal, RestbucksUri appealUri) throws Exception {
        Identifier appealIdentifier = appealUri.getId();

        AppealRepository repository = AppealRepository.current();
        
        if (AppealRepository.current().appealNotPlaced(appealIdentifier)) { // Defensive check to see if we have the order
            throw new Exception();
        }

        if (!appealCanBeChanged(appealIdentifier)) {
            throw new Exception();
        }

        Appeal storedAppeal = repository.get(appealIdentifier);
        
        storedAppeal.setStatus(appeal.getStatus());
        //storedOrder.calculateCost();


        return AppealRepresentation.createResponseAppealRepresentation(appeal, appealUri); 
    }
    
    private boolean appealCanBeChanged(Identifier identifier) {
        return AppealRepository.current().get(identifier).getStatus() == AppealStatus.PENDING;
    }
}
