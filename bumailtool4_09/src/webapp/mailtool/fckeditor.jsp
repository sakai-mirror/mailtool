<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>

			<h:commandButton value="Insert HTML" onclick="InsertHTML();">
			<h:commandButton value="Set Editor Contents" onclick="SetContents();">
			<h:commandButton value="Get Editor Contents (XHTML)" onclick="GetContents();">

			<h:commandButton value='Execute "Bold" Command' onclick="ExecuteCommand('Bold');">
			<h:commandButton value='Execute "Link" Command' onclick="ExecuteCommand('Link');">

			<h:commandButton value="Interact with the Editor Area DOM" onclick="GetLength();">
			<h:commandButton value="Get innerHTML" onclick="GetInnerHTML();">

			<h:commandButton value="Check IsDirty()" onclick="CheckIsDirty();">
			<h:commandButton value="Reset IsDirty()" onclick="ResetIsDirty();">	