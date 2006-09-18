--------
versions
--------
2.2.1_provisional: Sakai 2.2.1-compliant version with gmail-like attachment and Wysiwyg editor
2.2.1_AutoConfig: 2.2.1_provisional + auto configuration (for course & project site-type, Old configurations is also available.)

-----------
Change logs
-----------
1) Gmail-like attachment
- use <sakai:inputFileUpload>
- remove link for each attachment
- check if there's duplicate file among attachment list (If so, ignore)

2) EmailService.sendMail() is refactored for attachment
- Support multipart message along with Attachment(s)

------------
Installation
------------

	Requirements:same as Sakai 2.2 (http://source.sakaiproject.org/release/2.2.1/#overview)

	svn: http://skdev1.bu.edu:8888/svn/sakai/branches/bumailtool/2.2.0/bumailtool5

	How to build/deploy
		1) Check out the source from the above url (using Subversion or TortoiseSVN) to the sakai source directory.
		2) Go to the source directory.
		3) Stop tomcat.
		4) Run "maven sakai". (You may need to edit build.properties for reflecting the location of your tomcat home)
		4) Restart tomcat.
-------------
Configuration
-------------

--- /sakai/sakai.properties
		Mailtool uses the following four sakai-wide setting
			smtp@org.sakaiproject.email.api.EmailService=localhost
			smtp.port=25
			content.upload.max=5
			wysiwyg.editor=

		There are two optional settings. If these do not exist, Mailtool will use default values.

		mailtool.max.num.attachment=(default: unlimited)
		mailtool.upload.directory=(default: /tmp/)

--- /mailtool/WEB-INF/web.xml (Sakai/Tomcat Admin can modify)
		...
		<!-- This will override the setting "content.upload.max" in sakai.properties
				<init-param>
					<param-name>upload.max</param-name>
					<param-value>5</param-value>
				</init-param>
		-->
		...
		
		***** This value will override sakai.properties' content.upload.max *****

--- /mailtool/tools/sakai.mailtool.xml (Site's maintainer can modify)
		<configuration name="wysiwygeditor" value="" />

		This setting also can be changed in
		My Workspace->Site->"_____siteid_____"->"Add Edit/Pages"->"Mailtool"->"Tools"->"_____toolid_____"

		***** This value will override sakai.properties' wysiwyg.editor. *****
		
--- Use sakaiscript for setup
		See http://bugs.sakaiproject.org/confluence/display/ENTR/2005/07/19/Steve+Githens'+Web+Services


-----
Usage
-----
When create a new site:
	Just choose Mailtool from the list of the tools
	
When add Mailtool to the existing site
	My Workspace -> Worksite Setup -> Check box next to "_____siteid_____" -> Click "Revise" -> "Edit tools" -> Check "Mailtool"
	
------
Future
------
Next version will do automatic configuration.
- Add participants by roles
- Set up emailarchive
- Set up prefix of the mail subject

----------------------
Known bugs/limitations
----------------------
- When "Attach a file" link is clicked, Sakai RequestFilter's warning is printed: This one not affect any part of the Mailtool.(Being inspected)
- When HTMLArea wysiwyg editor is used and user change the recipients view, the contents in HTMLArea will be lost.
  (Not happens in FCKeditor or Plain text editor)
- When the message with attachment(s) is archived, the archived message will not contain attachment. Instead, it have the info(filename and size) of the attachment(s)

-------
Contact
-------
SOO IL KIM
kimsooil@bu.edu

