package edu.asu.arpit.assignment3.activities;

import edu.asu.arpit.assignment3.model.Grade;
import edu.asu.arpit.assignment3.model.Identifier;
import edu.asu.arpit.assignment3.repositories.GradeRepository;
import edu.asu.arpit.assignment3.representations.GradeRepresentations;
import edu.asu.arpit.assignment3.representations.Link;
import edu.asu.arpit.assignment3.representations.Representations;
import edu.asu.arpit.assignment3.representations.RestbucksUri;

public class CreateGradeActivity {
	public GradeRepresentations create(Grade grade, RestbucksUri requestUri) {
        //grade.setStatus(AppealStatus.PENDING);
                
        Identifier identifier = GradeRepository.current().store(grade);
        
        RestbucksUri gradeUri = new RestbucksUri(requestUri.getBaseUri() + "/grades/" + identifier.toString());
        //RestbucksUri paymentUri = new RestbucksUri(requestUri.getBaseUri() + "/payment/" + identifier.toString());
        return new GradeRepresentations(grade,
        		new Link(Representations.RELATIONS_URI + "update", gradeUri),
                new Link(Representations.SELF_REL_VALUE, gradeUri));
    }
}
