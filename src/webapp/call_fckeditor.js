/*
function InsertHTML()
{
	// Get the editor instance that we want to interact with.
	var oEditor = FCKeditorAPI.GetInstance('FCKeditor1') ;

	// Check the active editing mode.
	if ( oEditor.EditMode == FCK_EDITMODE_WYSIWYG )
	{
		// Insert the desired HTML.
		oEditor.InsertHtml( '- This is some <a href="/Test1.html">sample</a> HTML -' ) ;
	}
	else
		alert( 'You must be on WYSIWYG mode!' ) ;
}

function SetContents()
{
	// Get the editor instance that we want to interact with.
	var oEditor = FCKeditorAPI.GetInstance('FCKeditor1') ;

	// Set the editor contents (replace the actual one).
	oEditor.SetHTML( 'This is the <b>new content</b> I want in the editor.' ) ;
}

function GetContents()
{
	// Get the editor instance that we want to interact with.
	var oEditor = FCKeditorAPI.GetInstance('FCKeditor1') ;

	// Get the editor contents in XHTML.
	alert( oEditor.GetXHTML( true ) ) ;		// "true" means you want it formatted.
}

function ExecuteCommand( commandName )
{
	// Get the editor instance that we want to interact with.
	var oEditor = FCKeditorAPI.GetInstance('FCKeditor1') ;

	// Execute the command.
	oEditor.Commands.GetCommand( commandName ).Execute() ;
}

function GetLength()
{
	// This functions shows that you can interact directly with the editor area
	// DOM. In this way you have the freedom to do anything you want with it.

	// Get the editor instance that we want to interact with.
	var oEditor = FCKeditorAPI.GetInstance('FCKeditor1') ;

	// Get the Editor Area DOM (Document object).
	var oDOM = oEditor.EditorDocument ;

	var iLength ;

	// The are two diffent ways to get the text (without HTML markups).
	// It is browser specific.

	if ( document.all )		// If Internet Explorer.
	{
		iLength = oDOM.body.innerText.length ;
	}
	else					// If Gecko.
	{
		var r = oDOM.createRange() ;
		r.selectNodeContents( oDOM.body ) ;
		iLength = r.toString().length ;
	}

	alert( 'Actual text length (without HTML markups): ' + iLength + ' characters' ) ;
}

function GetInnerHTML()
{
	// Get the editor instance that we want to interact with.
	var oEditor = FCKeditorAPI.GetInstance('FCKeditor1') ;

	alert( oEditor.EditorDocument.body.innerHTML ) ;
}

function CheckIsDirty()
{
	// Get the editor instance that we want to interact with.
	var oEditor = FCKeditorAPI.GetInstance('FCKeditor1') ;
	alert( oEditor.IsDirty() ) ;	
}

function ResetIsDirty()
{
	// Get the editor instance that we want to interact with.
	var oEditor = FCKeditorAPI.GetInstance('FCKeditor1') ;
	oEditor.ResetIsDirty() ;	
	alert( 'The "IsDirty" status has been reset' ) ;
}
*/
// Automatically calculates the editor base path based on the _samples directory.
// This is usefull only for these samples. A real application should use something like this:
// oFCKeditor.BasePath = '/fckeditor/' ;	// '/fckeditor/' is the default value.
//var sBasePath = document.location.pathname.substring(0,document.location.pathname.lastIndexOf('_samples')) ;

var oFCKeditor = new FCKeditor( 'mainForm:FCKeditor1' ) ;
oFCKeditor.BasePath	= '/mailtool/FCKeditor/' ;
oFCKeditor.Config["CustomConfigurationsPath"] = oFCKeditor.BasePath+ "config.js"  ;
//oFCKeditor.ToolbarSet = 'Basic';
//oFCKeditor.Height	= 300 ;
//oFCKeditor.Width	= 700 ;
//oFCKeditor.Value	= '' ;
//oFCKeditor.Create() ;

var host=window.parent.document.URL;
var parts = host.split('/'); // parts[2]=ipnum, parts[5]=get siteid
var siteid=parts[5];
//alert("siteid="+parts[5]);
//alert(documents.forms.mainForm["mainForm:FCKeditor1"].value)
oFCKeditor.ReplaceTextarea() ;

//for (i=0;i<window.document.forms[0].elements.length;i++)
//{
//	alert(window.document.forms[0].elements[i].type);
//}
