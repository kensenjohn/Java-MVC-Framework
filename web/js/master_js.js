

// Found this here
// http://www.chapter31.com/2006/12/07/including-js-files-from-within-js-files/
function scriptinclude( file_path )
{
	var script = document.createElement('script');
	script.type = "text/javascript";
	script.src = file_path;
	
	
	document.getElementByTagName('body').item(0).appendChild( script );
}
