package edu.asu.arpit.assignment3.model;

public class Grade {
public char letterGrade;
    
    public Grade(char g)
    {
    //final String alphabet = "ABCDE";
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
