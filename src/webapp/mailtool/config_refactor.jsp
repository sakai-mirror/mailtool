<%-- HTML JSF tag libary --%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%-- Core JSF tag library --%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%-- Main Sakai tag library --%>
<%@ taglib uri="http://sakaiproject.org/jsf/sakai" prefix="sakai"%>

<f:view>
	<f:loadBundle basename="org.sakaiproject.tool.mailtool.Messages" var="msgs" />
	<f:verbatim>
		<!DOCTYPE html
		 PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
		 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	</f:verbatim>
	<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
			<title>Options</title>
				<%= request.getAttribute("sakai.html.head.css.base") %>
				<%= request.getAttribute("sakai.html.head.css.skin") %>
				<%= request.getAttribute("sakai.html.head.js") %>
		</head>
		<body onload="<%=request.getAttribute("sakai.html.body.onload")%>;">
			<div class="portletBody">
				<h:form id="optionsForm">
					<sakai:tool_bar>
						<sakai:tool_bar_item value="Compose" action="#{Option.processGoToComposeByCancel}" immediate="true" />
					</sakai:tool_bar>
					<sakai:messages />
					<sakai:view_title  value="Options" />
					<h:outputText value="You are currently choosing options for Email. 	Settings chosen on this page will become the default settings for this site."  styleClass="instruction" style="display: block;"/>
					<h:panelGrid columns="2" border="0" cellspacing="0" cellpadding="0"  styleClass="jsfFormTable itemSummary">
						<h:panelGroup style="padding: 0pt; overflow: hidden; display: block; height: 100%; float: right;">
							<h:outputLabel value="Choose selection view:" for="viewChoice"/>
						</h:panelGroup>
						<h:panelGroup>
							<h:selectOneListbox onchange="submit(); return false;" size="1" id="viewChoice" value="#{Option.viewChoice}">
									<f:selectItems value="#{Option.viewChoiceDropdown}" />
							</h:selectOneListbox>
							<f:subview id="selectByUser" rendered="#{Option.selectByUser}"><jsp:include page="selectByUser_option.jsp" /></f:subview>
							<f:subview id="selectByTree" rendered="#{Option.selectByTree}"><jsp:include page="selectByTree_option.jsp" /></f:subview>
							<f:subview id="selectSideBySide" rendered="#{Option.selectSideBySide}"><jsp:include page="selectSideBySide_option.jsp" /></f:subview>
							<f:subview id="selectByFoothill" rendered="#{Option.selectByFoothill}"><jsp:include page="selectByFoothill_option.jsp" /></f:subview>
						</h:panelGroup>
						<h:panelGroup style="padding: 0pt; overflow: hidden; display: block; height: 100%; float: right;">
							<h:outputText value="Copies:" />
						</h:panelGroup>	

						<h:panelGroup styleClass="checkbox">
							<h:selectBooleanCheckbox value="#{Option.sendMeCopy}" id="sendMeACopyID"/>
							<h:outputLabel value="Send me a copy" for="sendMeACopyID"/>
						</h:panelGroup>
						<h:panelGroup>
							<h:outputText value=" " />
						</h:panelGroup>

						<h:panelGroup rendered="#{Option.emailArchiveInSite}"styleClass="checkbox">
							<h:selectBooleanCheckbox value="#{Option.archiveMessage}" id="achiveMessageID"/>
							<h:outputLabel value="Add to Email Archive, visible to all site participants" for="achiveMessageID" />
						</h:panelGroup>
						<h:panelGroup rendered="#{not Option.emailArchiveInSite}" style="height:100%;overflow:hidden;display:block;color:#555 !important" styleClass="checkbox">
							<h:selectBooleanCheckbox disabled="true" value="#{Option.archiveMessage}" id="achiveMessageID2"/>
							<h:outputLabel value="Add to Email Archive, visible to all site participants" for="achiveMessageID2" style="color:#777;white-space:nowrap"/>
						</h:panelGroup>

						<h:panelGroup style="padding: 0pt; overflow: hidden; display: block; height: 100%; float: right;">
							<h:outputText value="Reply-to:" />
						</h:panelGroup>
						<h:panelGroup>
							<h:selectOneRadio layout="pageDirection" value="#{Option.replyToSelected }" onclick="submit()" styleClass="checkbox">
								<f:selectItem itemLabel="Sender" itemValue="yes"/>
								<f:selectItem itemLabel="Do not allow reply" itemValue="no"/> 
							</h:selectOneRadio>

						</h:panelGroup>
						<h:panelGroup style="padding: 0pt; overflow: hidden; display: block; height: 100%; float: right;">
							<h:outputText value="Message format:" />
						</h:panelGroup>
						<h:panelGroup>
							<h:selectOneRadio layout="pageDirection" value="#{Option.textFormat}" onclick="submit()"  styleClass="checkbox">
								<f:selectItem itemLabel="HTML formatting" itemValue="htmltext"/>
								<f:selectItem itemLabel="Plain text" itemValue="plaintext"/>
							</h:selectOneRadio>
						</h:panelGroup>
						<f:facet name="footer">
						  <h:panelGroup rendered="#{Option.showRenamingRoles}">
							<h:panelGroup rendered="#{not Option.showRenamingRolesClicked }">
							  <h:commandLink action="#{Option.toggle_showRemainingRoleClicked}" value=" Show Rename-roles menu" style="text-decoration: underline"/>
							</h:panelGroup>
							<h:panelGrid rendered="#{Option.showRenamingRolesClicked }" columns="1" styleClass="jsfFormTable itemSummary">
							  <h:panelGroup>
								<f:verbatim><h4></f:verbatim>
									<h:outputText value="Rename roles - " />
							  		<h:commandLink action="#{Option.toggle_showRemainingRoleClicked}" value=" Hide Rename-roles menu"  style="text-decoration: underline;font-weight:normal"/>
								<f:verbatim></h4></f:verbatim>
							  </h:panelGroup>
								<h:outputText value="Choose names that will appear in the Roles listing on the To menu. See example above."  styleClass="instruction" style="display: block;"/>
								<h:dataTable value="#{Option.renamedRoles}" var="role"  cellspacing="0" cellpadding="0"  width="100%" >
									<h:column>
										<f:verbatim><h5></f:verbatim>
											<h:outputText value="#{role.roleId}: "/>
										<f:verbatim></h5></f:verbatim>
										<h:outputLabel value="show role as -- singular form" for="rolesingular"/>
										<f:verbatim><br/></f:verbatim>
										<h:inputText size="20" value="#{role.singularNew}" id="rolesingular"/>
										<f:verbatim><br/></f:verbatim>
										<h:outputText value="e.g. #{role.singular }" styleClass="instruction" style="display: block;"/>
										<f:verbatim><br/></f:verbatim>
										<h:outputLabel value="show role as -- plural form" for="roleplural"/>
										<f:verbatim><br/></f:verbatim>
										<h:inputText size="20" value="#{role.pluralNew}" id="roleplural"/>
										<f:verbatim><br/></f:verbatim>
										<h:outputText value="e.g. #{role.plural }" styleClass="instruction" style="display: block;"/>
									</h:column>
								</h:dataTable>
							</h:panelGrid>
						  </h:panelGroup>
						</f:facet>
					</h:panelGrid>
						<sakai:button_bar>
						<sakai:button_bar_item
							action="#{Option.processUpdateOptions}"
							value="Update Defaults"
							styleClass="active"
							rendered="true"
							immediate="false" />
						<sakai:button_bar_item
							action="#{Option.processGoToComposeByCancel}"
							value="Cancel"
							rendered="true"
							immediate="false" />
					</sakai:button_bar>
				</h:form>
			</div>
		</body>
	</html>
</f:view>