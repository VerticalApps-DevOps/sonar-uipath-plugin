package com.uipath.sonar.plugin.checks;

import com.uipath.sonar.plugin.AbstractWorkflowCheck;
import com.uipath.sonar.plugin.uipath.Project;
import com.uipath.sonar.plugin.uipath.Utils;
import com.uipath.sonar.plugin.uipath.Workflow;
import org.dom4j.Element;
import org.dom4j.Node;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Rule(
    key = "InvokeWorkflowFilePathCheck",
    name = "Check 'Invoke Workflow File' path is relative",
    description =  "Checks that the path of an invoked workflow is relative and contained in the project directory.",
    status = "BETA",
    priority = Priority.MAJOR,
    tags = {"activity", "bug"}
)
public class InvokeWorkflowFilePathCheck extends AbstractWorkflowCheck {

    public InvokeWorkflowFilePathCheck(){
        super();
    }

    @Override
    public void execute(Project project, Workflow workflow){

        List<Node> nodes = workflow.getXamlDocument().selectNodes("//ui:InvokeWorkflowFile");

        for(Node node : nodes) {
            Element element = (Element) node;
            String workflowFilename = element.attributeValue("WorkflowFileName");

            if(Utils.nodeIsCode(workflowFilename)){
                continue;
            }

            try{
                URI uri = Utils.getURI(workflowFilename);

                if(uri.isAbsolute()){
                    reportIssue(workflow, "The path the workflow file should be relative and contained in the project.");
                }
            }
            catch(URISyntaxException e){
                reportIssue(workflow, "Could not parse path of '" + workflowFilename + "'. Ensure that it is valid.");
            }
        }
    }
}
