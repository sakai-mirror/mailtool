window.onload = function()
{
	// Automatically calculates the editor base path based on the _samples directory.
	// This is usefull only for these samples. A real application should use something like this:
	// oFCKeditor.BasePath = '/fckeditor/' ;	// '/fckeditor/' is the default value.
//	var sBasePath = document.location.pathname.substring(0,document.location.pathname.lastIndexOf('_samples')) ;

	var oFCKeditor = new FCKeditor( '_id4:FCKeditor1' ) ;
	oFCKeditor.BasePath	= '/mailtool/FCKeditor/' ;
	oFCKeditor.Config["CustomConfigurationsPath"] = oFCKeditor.BasePath+ "config.js"  ;
	oFCKeditor.ReplaceTextarea() ;
}
