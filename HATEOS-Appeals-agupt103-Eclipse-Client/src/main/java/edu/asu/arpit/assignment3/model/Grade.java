package edu.asu.arpit.assignment3.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import edu.asu.arpit.assignment3.representations.Representations;

@XmlRootElement(name="grade", namespace = Representations.RESTBUCKS_NAMESPACE)
public class Grade {
	@XmlElement(name = "gradeLetter", namespace = Representations.RESTBUCKS_NAMESPACE)
    public char letterGrade;
    
    public Grade(){
        
    }
    
    public Grade(char g)
    {
   // final String alphabet = "ABCDE";
    //final int N = alphabet.length();
    //Random r = new Random();
    //letterGrade = alphabet.charAt(r.nextInt(N));
     this.letterGrade = g;
}
   
    public void setValue(char gradeLetter)
    {
        this.letterGrade=gradeLetter;
    }
    
   public char getValue()
    {
        return letterGrade;
    }
}
