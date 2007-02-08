<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
 
<%--
Mailtool.userTree returns DataModel from SelectByTree.getRows
--%>

<%-- Tree Table --%>

	<h:panelGroup rendered="#{Mailtool.currentMode=='options' }" style="height:100%;overflow:hidden;display:block;margin:.5em 0;padding:0;color:#555 !important" styleClass="inopPanel" >
		<h:outputText  value="Preview (inactive)"  style="padding:.5em"/>
		<f:verbatim><br/><br/></f:verbatim>
		<h:panelGroup style="margin-left:1em;font-weight:normal">
			<h:panelGroup rendered="#{not Mailtool.groupAwareRoleviewClicked}">
				<h:selectBooleanCheckbox disabled="true" id="selectAllGroupAwareRoleCheckbox1" value="#{Mailtool.allGroupAwareRoleSelected}" />
				<h:outputLabel for="selectAllGroupAwareRoleCheckbox1" value=" All #{Mailtool.groupAwareRole}s" style="color:#777;white-space:nowrap"/>
				<h:outputText value=" - "  />
				<h:commandLink action="#{Mailtool.toggle_groupAwareRoleviewClicked}" value=" Select Individuals" style="text-decoration: underline"/>
			</h:panelGroup>
			<h:panelGroup rendered="#{Mailtool.groupAwareRoleviewClicked}">
				<h:selectBooleanCheckbox disabled="true" id="selectAllGroupAwareRoleCheckbox2" value="#{Mailtool.allGroupAwareRoleSelected}" />
				<h:outputLabel for="selectAllGroupAwareRoleCheckbox2" value=" All #{Mailtool.groupAwareRole}s" style="color:#777;white-space:nowrap"/>
				<h:outputText value=" - "  />			
				<h:commandLink action="#{Mailtool.toggle_groupAwareRoleviewClicked}" value=" Collapse Individuals"/>
			</h:panelGroup>			
			<h:outputText value=" - "  />
			<h:panelGroup rendered="#{not Mailtool.groupviewClicked}">
				<h:commandLink action="#{Mailtool.toggle_groupviewClicked}" value=" Select Groups" style="text-decoration: underline"/>
			</h:panelGroup>
			<h:panelGroup rendered="#{Mailtool.groupviewClicked}">
				<h:commandLink action="#{Mailtool.toggle_groupviewClicked}" value=" Collapse Groups"/>
			</h:panelGroup>			
			<h:outputText value=" - "  />
			<h:panelGroup rendered="#{not Mailtool.sectionviewClicked}">
				<h:commandLink action="#{Mailtool.toggle_sectionviewClicked}" value=" Select Sections" style="text-decoration: underline"/>
			</h:panelGroup>	
			<h:panelGroup rendered="#{Mailtool.sectionviewClicked}">
				<h:commandLink action="#{Mailtool.toggle_sectionviewClicked}" value=" Collapse Sections"/>
			</h:panelGroup>	
		</h:panelGroup>		

	<h:dataTable rendered="#{Mailtool.groupAwareRoleviewClicked}" value="#{Mailtool.recipientSelector_GroupAwareRole.dataModel}" var="row"  border="0" cellpadding="0" cellspacing="0">
			<h:column>
					<h:dataTable value="#{row.userTable}" var="user_row"  styleClass="listHier lines nolines hierItemBlockWrapper" columnClasses="attach,,attach,"  cellpadding="0" cellspacing="0" style="margin-left:2em;width:auto;font-weight:normal">
						<h:column>
							<h:selectBooleanCheckbox disabled="true" id="indCol1Checkbox11" value="#{user_row.selected1}"  />
						</h:column>
						<h:column>
							<h:outputLabel for="indCol1Checkbox11" value="#{user_row.user1.displayname}" style="color:#777;white-space:nowrap"/>
						</h:column>
						<h:column>
							<h:selectBooleanCheckbox disabled="true" id="indCol2Checkbox22" value="#{user_row.selected2}" rendered="#{user_row.render2}" style="margin-left:2em;"/>
						</h:column>
						<h:column>
						<h:outputLabel for="indCol2Checkbox22" rendered="#{user_row.render2}" value="#{user_row.user2.displayname}" style="color:#777;white-space:nowrap" />
						</h:column>
					</h:dataTable>
				</h:column>
	</h:dataTable>
			
	<h:panelGrid rendered="#{Mailtool.groupviewClicked }" columns="1" styleClass="ListHier lines nolines hierItemBlockWrapper" style="margin-left:3em;width:auto">
				<h:dataTable value="#{Mailtool.recipientSelector_Group.dataModel}" var="grouprow"  border="0"  style="margin:0;width:auto" cellpadding="0" cellspacing="0">
					<h:column>
						<h:panelGroup rendered="#{grouprow.collapsed}">
							<h:selectBooleanCheckbox disabled="true" id="selectAllGroupCheckbox" value="#{grouprow.allSelected}" />
							<h:outputLabel for="selectAllGroupCheckbox" value="#{grouprow.pluralRolename} Group" style="color:#777;white-space:nowrap"/>
							<h:outputText value=" - "  />
							<h:commandLink actionListener="#{grouprow.actionExpand}" value="#{msgs.select_individuals_button}s" style="text-decoration: underline"/>
						</h:panelGroup>
						<h:panelGroup rendered="#{not grouprow.collapsed}">
							<h:selectBooleanCheckbox disabled="true" id="selectAllGroupCheckbox2" value="#{grouprow.allSelected}" />
							<h:outputText value="#{grouprow.pluralRolename} "  />
							<h:outputText value=" - "  />
							<h:commandLink actionListener="#{grouprow.actionCollapse}" value="#{msgs.collapse_button}"/>
							<h:dataTable value="#{grouprow.userTable}" var="guser_row"  styleClass="listHier lines nolines" columnClasses="attach,,attach,"  cellpadding="0" cellspacing="0" style="margin-left:2em;width:auto;font-weight:normal">
								<h:column>
									<h:selectBooleanCheckbox disabled="true" id="indCol1Checkbox3" value="#{guser_row.selected1}"  />
								</h:column>
								<h:column>
									<h:outputLabel for="indCol1Checkbox3" value="#{guser_row.user1.displayname}" style="color:#777;white-space:nowrap" />
								</h:column>
								<h:column>
									<h:selectBooleanCheckbox disabled="true" id="indCol2Checkbox4" value="#{guser_row.selected2}" rendered="#{guser_row.render2}" style="margin-left:2em;"/>
								</h:column>
								<h:column>
									<h:outputLabel for="indCol2Checkbox4" rendered="#{guser_row.render2}" value="#{guser_row.user2.displayname}" style="color:#777;white-space:nowrap" />
								</h:column>
							</h:dataTable>
						</h:panelGroup>
					</h:column>
				</h:dataTable>	
	</h:panelGrid>
			
	<h:panelGrid rendered="#{Mailtool.sectionviewClicked}" columns="1" styleClass="listHier lines nolines hierItemBlockWrapper" style="margin-left:3em;width:auto">
				<h:dataTable value="#{Mailtool.recipientSelector_Section.dataModel}" var="sectionrow"  border="0"  style="margin:0;width:auto" cellpadding="0" cellspacing="0">
					<h:column>
						<h:panelGroup rendered="#{sectionrow.collapsed}">
							<h:selectBooleanCheckbox disabled="true" id="selectAllSectionCheckbox" value="#{sectionrow.allSelected}" />
							<h:outputLabel for="selectAllSectionCheckbox" value="#{sectionrow.pluralRolename} Section" style="color:#777;white-space:nowrap"/>
							<h:outputText value=" - "  />
							<h:commandLink actionListener="#{sectionrow.actionExpand}" value="#{msgs.select_individuals_button}s" style="text-decoration: underline"/>
						</h:panelGroup>
						<h:panelGroup rendered="#{not sectionrow.collapsed}">
							<h:selectBooleanCheckbox id="selectAllSectionCheckbox2" value="#{sectionrow.allSelected}" />			
							<h:outputText value="#{sectionrow.pluralRolename} "  />
							<h:outputText value=" - "  />
							<h:commandLink actionListener="#{sectionrow.actionCollapse}" value="#{msgs.collapse_button}"/>
							<h:dataTable value="#{sectionrow.userTable}" var="suser_row"  styleClass="listHier lines nolines" columnClasses="attach,,attach,"  cellpadding="0" cellspacing="0" style="margin-left:2em;width:auto;font-weight:normal">
								<h:column>
									<h:selectBooleanCheckbox disabled="true" id="indCol1Checkbox5" value="#{suser_row.selected1}"  />
								</h:column>
								<h:column>
									<h:outputLabel for="indCol1Checkbox5" value="#{suser_row.user1.displayname}" style="color:#777;white-space:nowrap" />
								</h:column>
								<h:column>
									<h:selectBooleanCheckbox disabled="true" id="indCol2Checkbox6" value="#{suser_row.selected2}" rendered="#{suser_row.render2}" style="margin-left:2em;"/>
								</h:column>
								<h:column>
									<h:outputLabel for="indCol2Checkbox6" rendered="#{suser_row.render2}" value="#{suser_row.user2.displayname}" style="color:#777;white-space:nowrap" />
								</h:column>
							</h:dataTable>
						</h:panelGroup>
					</h:column>
				</h:dataTable>	
	</h:panelGrid>

	<h:panelGroup>
		<h:dataTable value="#{Mailtool.recipientSelector.dataModel}" var="row"  border="0" style="margin:0;width:100%" cellpadding="0" cellspacing="0">
			<h:column>
				<h:panelGroup rendered="#{row.collapsed}">
					<h:selectBooleanCheckbox disabled="true" id="selectAllCheckbox" value="#{row.allSelected}" />
					<h:outputLabel for="selectAllCheckbox" value="#{msgs.all_prefix} #{row.pluralRolename} " style="color:#777;white-space:nowrap"/>
					<h:outputText value=" - "  />
					<h:commandLink actionListener="#{row.actionExpand}" value="Select #{row.pluralRolename} " style="text-decoration: underline"/>
				</h:panelGroup>
				<h:panelGroup rendered="#{not row.collapsed}">
					<h:selectBooleanCheckbox disabled="true" id="selectAllCheckbox2" value="#{row.allSelected}" />
					<h:outputLabel for="selectAllCheckbox2" value="#{msgs.all_prefix} #{row.pluralRolename} " style="color:#777;white-space:nowrap"/>
					<h:outputText value=" - "  />
					<h:commandLink actionListener="#{row.actionCollapse}" value="Collapse #{row.pluralRolename} " />
				</h:panelGroup>
	
				<h:panelGroup rendered="#{not row.collapsed}">
					<h:dataTable value="#{row.userTable}" var="user_row"  styleClass="listHier lines nolines hierItemBlockWrapper" columnClasses="attach,,attach,"  cellpadding="0" cellspacing="0" style="margin-left:2em;width:auto;font-weight:normal">
						<h:column>
							<h:selectBooleanCheckbox  disabled="true" id="indCol1Checkbox1" value="#{user_row.selected1}"  />
						</h:column>
						<h:column>
							<h:outputLabel for="indCol1Checkbox1" value="#{user_row.user1.displayname}" style="color:#777;white-space:nowrap" />
						</h:column>
						<h:column>
							<h:selectBooleanCheckbox disabled="true" id="indCol2Checkbox2" value="#{user_row.selected2}" rendered="#{user_row.render2}" style="margin-left:2em;"/>
						</h:column>
						<h:column>
							<h:outputLabel for="indCol2Checkbox2" rendered="#{user_row.render2}" value="#{user_row.user2.displayname}" style="color:#777;white-space:nowrap" />
						</h:column>
					</h:dataTable>
				</h:panelGroup>
				
			</h:column>
		</h:dataTable>
	</h:panelGroup>
</h:panelGroup>
<%--

		<h:panelGroup>
		<h:dataTable value="#{Mailtool.recipientSelector.dataModel}" var="row"  border="0" style="margin:1em 0" cellpadding="0" cellspacing="0" >
			<h:column>
				<h:panelGroup rendered="#{row.collapsed}">
					<h:selectBooleanCheckbox disabled="true" id="selectAllCheckbox2" value="#{row.allSelected}" />
					<h:outputLabel for="selectAllCheckbox2" value="#{msgs.all_prefix} #{row.pluralRolename} " />
					<h:outputText value=" -  " />
					<h:commandLink actionListener="#{row.actionExpand}" value="#{msgs.select_individuals_button} #{row.pluralRolename}" />
				</h:panelGroup>
				<h:panelGroup rendered="#{not row.collapsed}">
					<h:outputText value="#{msgs.select_individual_prefix} " />
					<h:outputText value="#{row.pluralRolename} " />
					<h:outputText value=" -  " />
					<h:commandLink actionListener="#{row.actionCollapse}" value="#{msgs.collapse_button}" />
					<h:dataTable value="#{row.userTable}" var="user_row" columnClasses="attach,,attach,"  cellpadding="0" cellspacing="0" style="width:auto;font-weight:normal" border="0" styleClass="listHier lines nolines hierItemBlockWrapper">
						<h:column>
							<h:selectBooleanCheckbox disabled="true" id="indCol1Checkbox" value="#{user_row.selected1}" />
						</h:column>
						<h:column>
							<h:outputLabel for="indCol1Checkbox" value="#{user_row.user1.displayname}"  style="color:#777;white-space:nowrap" />
						</h:column>
						<h:column>
							<h:selectBooleanCheckbox disabled="true" id="indCol2Checkbox" value="#{user_row.selected2}" rendered="#{user_row.render2}" />
						</h:column>
						<h:column>
							<h:outputLabel for="indCol2Checkbox" rendered="#{user_row.render2}" value="#{user_row.user2.displayname}"  style="color:#777;white-space:nowrap"  />
						</h:column>
					</h:dataTable>
				</h:panelGroup>
			</h:column>
		</h:dataTable>
</h:panelGroup>


<h:panelGrid rendered="#{not Mailtool.groupviewClicked}" columns="1" border="0" style="margin:0;width:100%" cellpadding="0" cellspacing="0">
<h:panelGroup>
<h:selectBooleanCheckbox disabled="true" id="selectGroupCheckbox2"  value="#{Mailtool.allGroupSelected}" />
<h:outputLabel for="selectGroupCheckbox2" value="All Groups " />
<h:outputText value=" - "  />
<h:commandLink action="#{Mailtool.toggle_groupviewClicked}" value="Select Individual Groups" />
</h:panelGroup>
</h:panelGrid>
<h:panelGrid rendered="#{Mailtool.groupviewClicked }" columns="1" styleClass="ListHier lines nolines">
<h:panelGroup>
	<h:outputText value="#{msgs.select_individual_prefix} Groups" />
	<h:outputText value=" - "  />
	<h:commandLink action="#{Mailtool.toggle_groupviewClicked}" value="#{msgs.collapse_button}" />

		<h:dataTable value="#{Mailtool.recipientSelector_Group.dataModel}" var="row"  border="0" style="margin:1em 0" cellpadding="0" cellspacing="0" >
			<h:column>
				<h:panelGroup rendered="#{row.collapsed}">
					<h:selectBooleanCheckbox disabled="true" id="selectAllGroupCheckbox" value="#{row.allSelected}" />
					<h:outputLabel for="selectAllGroupCheckbox" value="#{msgs.all_prefix} #{row.pluralRolename} " />
					<h:outputText value=" -  " />
					<h:commandLink actionListener="#{row.actionExpand}" value="#{msgs.select_individuals_button} #{row.pluralRolename}" />
				</h:panelGroup>
				<h:panelGroup rendered="#{not row.collapsed}">
					<h:outputText value="#{msgs.select_individual_prefix} " />
					<h:outputText value="#{row.pluralRolename} " />
					<h:outputText value=" -  " />
					<h:commandLink actionListener="#{row.actionCollapse}" value="#{msgs.collapse_button}" />
					<h:dataTable value="#{row.userTable}" var="user_row" columnClasses="attach,,attach,"  cellpadding="0" cellspacing="0" style="width:auto;font-weight:normal" border="0" styleClass="listHier lines nolines hierItemBlockWrapper">
						<h:column>
							<h:selectBooleanCheckbox disabled="true" id="indCol1Checkbox" value="#{user_row.selected1}" />
						</h:column>
						<h:column>
							<h:outputLabel for="indCol1Checkbox" value="#{user_row.user1.displayname}"  style="color:#777;white-space:nowrap" />
						</h:column>
						<h:column>
							<h:selectBooleanCheckbox disabled="true" id="indCol2Checkbox" value="#{user_row.selected2}" rendered="#{user_row.render2}" />
						</h:column>
						<h:column>
							<h:outputLabel for="indCol2Checkbox" rendered="#{user_row.render2}" value="#{user_row.user2.displayname}"  style="color:#777;white-space:nowrap"  />
						</h:column>
					</h:dataTable>
				</h:panelGroup>
			</h:column>
		</h:dataTable>
</h:panelGroup>
</h:panelGrid>



<h:panelGrid rendered="#{not Mailtool.sectionviewClicked}" columns="1" border="0" style="margin:0;width:100%" cellpadding="0" cellspacing="0">
<h:panelGroup>
<h:selectBooleanCheckbox disabled="true" id="selectSectionCheckbox2"  value="#{Mailtool.allSectionSelected}" />
<h:outputLabel for="selectSectionCheckbox2" value="All Sections " />
<h:outputText value=" - "  />
<h:commandLink action="#{Mailtool.toggle_sectionviewClicked}" value="Select Individual Sections" />
</h:panelGroup>
</h:panelGrid>
<h:panelGrid rendered="#{Mailtool.sectionviewClicked}" columns="1" styleClass="listHier lines nolines">
<h:panelGroup>
	<h:outputText value="#{msgs.select_individual_prefix} Sections" />
	<h:outputText value=" - "  />
	<h:commandLink action="#{Mailtool.toggle_sectionviewClicked}" value="#{msgs.collapse_button}" />

		<h:dataTable value="#{Mailtool.recipientSelector_Section.dataModel}" var="row"  border="0" style="margin:1em 0" cellpadding="0" cellspacing="0" >
			<h:column>
				<h:panelGroup rendered="#{row.collapsed}">
					<h:selectBooleanCheckbox disabled="true" id="selectAllSectionCheckbox" value="#{row.allSelected}" />
					<h:outputLabel for="selectAllSectionCheckbox" value="#{msgs.all_prefix} #{row.pluralRolename} " />
					<h:outputText value=" -  " />
					<h:commandLink actionListener="#{row.actionExpand}" value="#{msgs.select_individuals_button} #{row.pluralRolename}" />
				</h:panelGroup>
				<h:panelGroup rendered="#{not row.collapsed}">
					<h:outputText value="#{msgs.select_individual_prefix} " />
					<h:outputText value="#{row.pluralRolename} " />
					<h:outputText value=" -  " />
					<h:commandLink actionListener="#{row.actionCollapse}" value="#{msgs.collapse_button}" />
					<h:dataTable value="#{row.userTable}" var="user_row" columnClasses="attach,,attach,"  cellpadding="0" cellspacing="0" style="width:auto;font-weight:normal" border="0" styleClass="listHier lines nolines hierItemBlockWrapper">
						<h:column>
							<h:selectBooleanCheckbox disabled="true" id="indCol1Checkbox" value="#{user_row.selected1}" />
						</h:column>
						<h:column>
							<h:outputLabel for="indCol1Checkbox" value="#{user_row.user1.displayname}"  style="color:#777;white-space:nowrap" />
						</h:column>
						<h:column>
							<h:selectBooleanCheckbox disabled="true" id="indCol2Checkbox" value="#{user_row.selected2}" rendered="#{user_row.render2}" />
						</h:column>
						<h:column>
							<h:outputLabel for="indCol2Checkbox" rendered="#{user_row.render2}" value="#{user_row.user2.displayname}"  style="color:#777;white-space:nowrap"  />
						</h:column>
					</h:dataTable>
				</h:panelGroup>
			</h:column>
		</h:dataTable>
</h:panelGroup>
</h:panelGrid>
--%>		


