package edu.asu.arpit.assignment3.activities;

import edu.asu.arpit.assignment3.model.Grade;
import edu.asu.arpit.assignment3.model.Identifier;
import edu.asu.arpit.assignment3.repositories.GradeRepository;
import edu.asu.arpit.assignment3.representations.GradeRepresentations;
import edu.asu.arpit.assignment3.representations.RestbucksUri;

public class ReadGradeActivity {
	 public GradeRepresentations retrieveByUri(RestbucksUri gradeUri) throws Exception {
	        Identifier identifier  = gradeUri.getId();
	        
	        Grade grade = GradeRepository.current().get(identifier);
	        
	        if(grade == null) {
	            throw new Exception();
	        }
	        
	        return GradeRepresentations.createResponseGradeRepresentation(grade, gradeUri);
}
}
