package com.uipath.sonar.plugin.checks;

import com.uipath.sonar.plugin.AbstractWorkflowCheck;
import com.uipath.sonar.plugin.uipath.Project;
import com.uipath.sonar.plugin.uipath.Workflow;
import org.dom4j.Node;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

import java.util.List;

@Rule(
    key = "AvoidGetPasswordCheck",
    name = "'Get Password' activity should be avoided",
    description =  "Checks if the Get Password Activity is used. It should be avoided in favor of Orchestrator assets.",
    status = "BETA",
    priority = Priority.MAJOR,
    tags = {"activity"}
)
public class AvoidGetPasswordCheck extends AbstractWorkflowCheck{

    public AvoidGetPasswordCheck(){
        super();
    }

    public void execute(Project project, Workflow workflow){
        List<Node> nodes = workflow.getXamlDocument().selectNodes("//ui:GetPassword");

        if(nodes.size() > 0){
            reportIssue(workflow, "Avoid using the 'GetPassword' activity. Use Orchestrator assets instead.");
        }
    }
}
