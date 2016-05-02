package edu.asu.arpit.assignment3.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import edu.asu.arpit.assignment3.representations.*;

@XmlRootElement(name="appeal", namespace = Representations.RESTBUCKS_NAMESPACE)
public class Appeal {
	@XmlElement(name = "AppealComments", namespace = Representations.RESTBUCKS_NAMESPACE)
    private StringBuilder AppealComments = new StringBuilder();
    
    @XmlElement(name = "status", namespace = Representations.RESTBUCKS_NAMESPACE)
    private AppealStatus appStatus = AppealStatus.PENDING;
    
         
     public StringBuilder getComments()
     {
         return AppealComments;
     }
     Appeal()
     { }
      public void setComments(StringBuilder com)
     {
         AppealComments.append(com);
     }
      public Appeal(StringBuilder app)
      {
          this.AppealComments.append(app);
           this.appStatus = AppealStatus.PENDING;
      }
      public Appeal(AppealStatus status)
    {
          //this.AppealComments.append(app);
       
           this.appStatus = status;
      }
      public void setStatus(AppealStatus s)
      {
          this.appStatus= s;
      }
      public AppealStatus getStatus()
      {
          return appStatus;
      }
}
