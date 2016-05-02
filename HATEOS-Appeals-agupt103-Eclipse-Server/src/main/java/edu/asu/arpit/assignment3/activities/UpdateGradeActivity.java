package edu.asu.arpit.assignment3.activities;

import edu.asu.arpit.assignment3.model.Grade;
import edu.asu.arpit.assignment3.model.Identifier;
import edu.asu.arpit.assignment3.repositories.GradeRepository;
import edu.asu.arpit.assignment3.representations.GradeRepresentations;
import edu.asu.arpit.assignment3.representations.RestbucksUri;

public class UpdateGradeActivity {
	public GradeRepresentations update(Grade grade, RestbucksUri gradeUri) throws Exception {
        Identifier gradeIdentifier = gradeUri.getId();

        GradeRepository repository = GradeRepository.current();
        
        if (GradeRepository.current().gradeNotPlaced(gradeIdentifier)) { // Defensive check to see if we have the order
            throw new Exception();
        }

        if (!gradeCanBeChanged(gradeIdentifier)) {
            throw new Exception();
        }

        Grade storedGrade = repository.get(gradeIdentifier);
        
       
        //storedOrder.calculateCost();


        return GradeRepresentations.createResponseGradeRepresentation(storedGrade, gradeUri); 
    }
    
    private boolean gradeCanBeChanged(Identifier identifier) {
        return true;
    }
    
}
