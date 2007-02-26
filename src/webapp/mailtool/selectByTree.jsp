<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>

<%--
Mailtool.userTree returns DataModel from SelectByTree.getRows
--%>
                                              
<%-- Tree Table --%>

	<h:panelGroup rendered="#{Mailtool.groupAwareRoleExist}">
		<h:panelGroup rendered="#{not Mailtool.groupAwareRoleviewClicked}"  style="font-weight:normal">
			<h:selectBooleanCheckbox id="selectAllGroupAwareRoleCheckbox1" value="#{Mailtool.allGroupAwareRoleSelected}" />
			<h:outputLabel for="selectAllGroupAwareRoleCheckbox1" value=" All #{Mailtool.groupAwareRole}s" />
			<h:outputText value=" - "  />
			<h:commandLink action="#{Mailtool.toggle_groupAwareRoleviewClicked}" value=" Select Individuals" style="text-decoration: underline"/>
		</h:panelGroup>
		<h:panelGroup rendered="#{Mailtool.groupAwareRoleviewClicked}" style="font-weight:normal">
			<h:selectBooleanCheckbox id="selectAllGroupAwareRoleCheckbox2" value="#{Mailtool.allGroupAwareRoleSelected}" />
			<h:outputLabel for="selectAllGroupAwareRoleCheckbox2" value=" All #{Mailtool.groupAwareRole}s" />
			<h:outputText value=" - "  />			
			<h:commandLink action="#{Mailtool.toggle_groupAwareRoleviewClicked}" value=" Collapse Individuals" style="text-decoration: underline"/>
		</h:panelGroup>
	</h:panelGroup>
	<h:panelGroup rendered="#{Mailtool.groupAwareRoleExist and Mailtool.num_groups > 0}">	
		<h:outputText value=" | "  />
		<h:panelGroup rendered="#{not Mailtool.groupviewClicked}" style="font-weight:normal">
			<h:commandLink action="#{Mailtool.toggle_groupviewClicked}" value=" Select Groups" style="text-decoration: underline"/>
		</h:panelGroup>
		<h:panelGroup rendered="#{Mailtool.groupviewClicked}" style="font-weight:normal">
			<h:commandLink action="#{Mailtool.toggle_groupviewClicked}" value=" Collapse Groups" style="text-decoration: underline"/>
		</h:panelGroup>
	</h:panelGroup>	
	<h:panelGroup rendered="#{Mailtool.groupAwareRoleExist and Mailtool.num_sections > 0}">
		<h:outputText value=" | "  />
		<h:panelGroup rendered="#{not Mailtool.sectionviewClicked}" style="font-weight:normal">
			<h:commandLink action="#{Mailtool.toggle_sectionviewClicked}" value=" Select Sections" style="text-decoration: underline"/>
		</h:panelGroup>	
		<h:panelGroup rendered="#{Mailtool.sectionviewClicked}" style="font-weight:normal">
			<h:commandLink action="#{Mailtool.toggle_sectionviewClicked}" value=" Collapse Sections" style="text-decoration: underline"/>
		</h:panelGroup>			
	</h:panelGroup>
	<h:dataTable rendered="#{Mailtool.groupAwareRoleviewClicked}" value="#{Mailtool.recipientSelector_GroupAwareRole.dataModel}" var="row"  border="0" cellpadding="0" cellspacing="0">
			<h:column>
					<h:dataTable value="#{row.userTable}" var="user_row"  styleClass="listHier nolines hierItemBlockWrapper" columnClasses="attach,,attach,"  cellpadding="0" cellspacing="0" style="margin-left:2em;width:auto;font-weight:normal">
						<h:column>
							<h:selectBooleanCheckbox id="indCol1Checkbox11" value="#{user_row.selected1}"  />
						</h:column>
						<h:column>
							<h:outputLabel for="indCol1Checkbox11" value="#{user_row.user1.displayname}" style="white-space:nowrap" />
						</h:column>
						<h:column>
							<h:selectBooleanCheckbox id="indCol2Checkbox22" value="#{user_row.selected2}" rendered="#{user_row.render2}" style="margin-left:2em;"/>
						</h:column>
						<h:column>
						<h:outputLabel for="indCol2Checkbox22" rendered="#{user_row.render2}" value="#{user_row.user2.displayname}" style="white-space:nowrap" />
						</h:column>
					</h:dataTable>
				</h:column>
	</h:dataTable>

	<h:dataTable rendered="#{Mailtool.groupviewClicked }" value="#{Mailtool.recipientSelector_Group.dataModel}" var="grouprow"  border="0" cellpadding="0" cellspacing="0" style="margin-left:2em;width:auto;font-weight:normal" styleClass="listHier nolines  hierItemBlockWrapper">
		<h:column>
			<h:panelGroup rendered="#{grouprow.collapsed}">
				<h:selectBooleanCheckbox id="selectAllGroupCheckbox" value="#{grouprow.allSelected}" />
				<h:outputLabel for="selectAllGroupCheckbox" value="#{grouprow.pluralRolename} Group" />
				<h:outputText value=" - "  />
				<h:commandLink actionListener="#{grouprow.actionExpand}" value="#{msgs.select_individuals_button}s" style="text-decoration: underline"/>
			</h:panelGroup>
			<h:panelGroup rendered="#{not grouprow.collapsed}">
				<h:selectBooleanCheckbox id="selectAllGroupCheckbox2" value="#{grouprow.allSelected}" />
				<h:outputText value="#{grouprow.pluralRolename} "  />
				<h:outputText value=" - "  />
				<h:commandLink actionListener="#{grouprow.actionCollapse}" value="#{msgs.collapse_button}" style="text-decoration: underline"/>
				<h:dataTable value="#{grouprow.userTable}" var="guser_row"  styleClass="listHier nolines" columnClasses="attach,,attach,"  cellpadding="0" cellspacing="0" style="margin-left:2em;width:auto;font-weight:normal">
					<h:column>
						<h:selectBooleanCheckbox id="indCol1Checkbox3" value="#{guser_row.selected1}"  />
					</h:column>
					<h:column>
						<h:outputLabel for="indCol1Checkbox3" value="#{guser_row.user1.displayname}" style="white-space:nowrap" />
					</h:column>
					<h:column>
						<h:selectBooleanCheckbox id="indCol2Checkbox4" value="#{guser_row.selected2}" rendered="#{guser_row.render2}" style="margin-left:2em;"/>
					</h:column>
					<h:column>
						<h:outputLabel for="indCol2Checkbox4" rendered="#{guser_row.render2}" value="#{guser_row.user2.displayname}" style="white-space:nowrap" />
					</h:column>
				</h:dataTable>
			</h:panelGroup>
		</h:column>
	</h:dataTable>	

	<h:dataTable rendered="#{Mailtool.sectionviewClicked}" value="#{Mailtool.recipientSelector_Section.dataModel}" var="sectionrow"  border="0" cellpadding="0" cellspacing="0" style="margin-left:2em;width:auto;font-weight:normal" styleClass="listHier nolines  hierItemBlockWrapper">
		<h:column>
			<h:panelGroup rendered="#{sectionrow.collapsed}">
				<h:selectBooleanCheckbox id="selectAllSectionCheckbox" value="#{sectionrow.allSelected}" />
				<h:outputLabel for="selectAllSectionCheckbox" value="#{sectionrow.pluralRolename} Section" />
				<h:outputText value=" - "  />
				<h:commandLink actionListener="#{sectionrow.actionExpand}" value="#{msgs.select_individuals_button}s" style="text-decoration: underline"/>
			</h:panelGroup>
			<h:panelGroup rendered="#{not sectionrow.collapsed}">
				<h:selectBooleanCheckbox id="selectAllSectionCheckbox2" value="#{sectionrow.allSelected}" />			
				<h:outputText value="#{sectionrow.pluralRolename} "  />
				<h:outputText value=" - "  />
				<h:commandLink actionListener="#{sectionrow.actionCollapse}" value="#{msgs.collapse_button}" style="text-decoration: underline"/>
				<h:dataTable value="#{sectionrow.userTable}" var="suser_row"  styleClass="listHier nolines" columnClasses="attach,,attach,"  cellpadding="0" cellspacing="0" style="margin-left:2em;width:auto;font-weight:normal">
					<h:column>
						<h:selectBooleanCheckbox id="indCol1Checkbox5" value="#{suser_row.selected1}"  />
					</h:column>
					<h:column>
						<h:outputLabel for="indCol1Checkbox5" value="#{suser_row.user1.displayname}" style="white-space:nowrap" />
					</h:column>
					<h:column>
						<h:selectBooleanCheckbox id="indCol2Checkbox6" value="#{suser_row.selected2}" rendered="#{suser_row.render2}" style="margin-left:2em;"/>
					</h:column>
					<h:column>
						<h:outputLabel for="indCol2Checkbox6" rendered="#{suser_row.render2}" value="#{suser_row.user2.displayname}" style="white-space:nowrap" />
					</h:column>
				</h:dataTable>
			</h:panelGroup>
		</h:column>
	</h:dataTable>	

	<h:panelGroup>
		<h:dataTable value="#{Mailtool.recipientSelector.dataModel}" var="row"  border="0" style="margin:0;width:100%" cellpadding="0" cellspacing="0">
			<h:column>
				<h:panelGroup rendered="#{row.collapsed}">
					<h:selectBooleanCheckbox id="selectAllCheckbox" value="#{row.allSelected}" />
					<h:outputLabel for="selectAllCheckbox" value="#{msgs.all_prefix} #{row.pluralRolename} " />
					<h:outputText value=" - "  />
					<h:commandLink actionListener="#{row.actionExpand}" value="Select #{row.pluralRolename} " style="text-decoration: underline"/>
				</h:panelGroup>
				<h:panelGroup rendered="#{not row.collapsed}">
					<h:selectBooleanCheckbox id="selectAllCheckbox2" value="#{row.allSelected}" />
					<h:outputLabel for="selectAllCheckbox2" value="#{msgs.all_prefix} #{row.pluralRolename} " />
					<h:outputText value=" - "  />
					<h:commandLink actionListener="#{row.actionCollapse}" value="Collapse #{row.pluralRolename} " style="text-decoration: underline"/>
				</h:panelGroup>
	
				<h:panelGroup rendered="#{not row.collapsed}">
					<h:dataTable value="#{row.userTable}" var="user_row"  styleClass="listHier nolines hierItemBlockWrapper" columnClasses="attach,,attach,"  cellpadding="0" cellspacing="0" style="margin-left:2em;width:auto;font-weight:normal">
						<h:column>
							<h:selectBooleanCheckbox id="indCol1Checkbox1" value="#{user_row.selected1}"  />
						</h:column>
						<h:column>
							<h:outputLabel for="indCol1Checkbox1" value="#{user_row.user1.displayname}" style="white-space:nowrap" />
						</h:column>
						<h:column>
							<h:selectBooleanCheckbox id="indCol2Checkbox2" value="#{user_row.selected2}" rendered="#{user_row.render2}" style="margin-left:2em;"/>
						</h:column>
						<h:column>
							<h:outputLabel for="indCol2Checkbox2" rendered="#{user_row.render2}" value="#{user_row.user2.displayname}" style="white-space:nowrap" />
						</h:column>
					</h:dataTable>
				</h:panelGroup>
				
			</h:column>
		</h:dataTable>
	</h:panelGroup>
