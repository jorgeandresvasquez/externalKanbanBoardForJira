package com.stw.kanban.soap;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import org.apache.axis.AxisProperties;
import com.atlassian.jira.rpc.soap.beans.RemoteIssue;
import com.atlassian.jira.rpc.soap.beans.RemoteIssueType;
import com.atlassian.jira.rpc.soap.beans.RemotePriority;
import com.atlassian.jira.rpc.soap.beans.RemoteStatus;
import com.atlassian.jira.rpc.soap.client.JiraSoapService;
import com.atlassian.jira.rpc.soap.client.JiraSoapServiceService;
import com.atlassian.jira.rpc.soap.client.JiraSoapServiceServiceLocator;

public class TestSoapCalls {

	public static void main(String[] args) throws Exception {
		InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("default.properties");
		Properties appProperties = new Properties();
		appProperties.load(stream);
		boolean skipSslValidation = Boolean.parseBoolean(appProperties.getProperty("jira.soap.skipSslValidation"));
		String jiraSoapUrl = appProperties.getProperty("jira.soap.url");
		String username = appProperties.getProperty("jira.soap.userName");
		String password = appProperties.getProperty("jira.soap.password");
		if (skipSslValidation) {
			AxisProperties.setProperty("axis.socketSecureFactory","org.apache.axis.components.net.SunFakeTrustSocketFactory");
		}
		JiraSoapServiceService jiraSoapServiceGetter = new JiraSoapServiceServiceLocator();
		JiraSoapService soapService = jiraSoapServiceGetter.getJirasoapserviceV2(new URL(jiraSoapUrl));
		String authenticationToken = soapService.login(username, password);
		
		RemotePriority[] ps= soapService.getPriorities(authenticationToken);
		RemoteIssueType[] ts = soapService.getIssueTypes(authenticationToken);
		RemoteStatus[] rs = soapService.getStatuses(authenticationToken);
		
		RemoteIssue[] issues = soapService.getIssuesFromJqlSearch(authenticationToken, "\"Reproduce Steps\" ~ PlayerServices" , 10);
		
		for (RemoteIssue issue : issues) {
			System.out.println(issue.getKey());
			System.out.println(issue.getAssignee());
		}	
	}
	
	
}
