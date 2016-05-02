package edu.asu.arpit.assignment3.activities;

import edu.asu.arpit.assignment3.model.Appeal;
import edu.asu.arpit.assignment3.model.AppealStatus;
import edu.asu.arpit.assignment3.model.Identifier;
import edu.asu.arpit.assignment3.repositories.AppealRepository;
import edu.asu.arpit.assignment3.representations.AppealRepresentation;
import edu.asu.arpit.assignment3.representations.Link;
import edu.asu.arpit.assignment3.representations.Representations;
import edu.asu.arpit.assignment3.representations.RestbucksUri;

public class CreateAppealActivity {
	public AppealRepresentation create(Appeal appeal, RestbucksUri requestUri) {
        appeal.setStatus(AppealStatus.PENDING);
                
        Identifier identifier = AppealRepository.current().store(appeal);
        
        RestbucksUri appealUri = new RestbucksUri(requestUri.getBaseUri() + "/appeal/" + identifier.toString());
        //RestbucksUri paymentUri = new RestbucksUri(requestUri.getBaseUri() + "/payment/" + identifier.toString());
        return new AppealRepresentation(appeal, 
                new Link(Representations.RELATIONS_URI + "Delete", appealUri), 
                //new Link(Representations.RELATIONS_URI + "payment", paymentUri), 
                new Link(Representations.RELATIONS_URI + "update", appealUri),
                new Link(Representations.SELF_REL_VALUE, appealUri));
    }
}
