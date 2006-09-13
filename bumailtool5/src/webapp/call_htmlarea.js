/*

var editor = new HTMLArea("mainForm:HTMLArea1");
var cfg = editor.config;
cfg.toolbar = [
				["fontname","space","fontsize","space","formatblock","space","bold","italic","underline",
				"strikethrough","subscript","superscript","linebreak","copy","cut","paste","space",
				"justifyleft","justifycenter","justifyright","justifyfull","orderedlist","unorderedlist",
				"outdent","indent","forecolor","hilitecolor","inserthorizontalrule",
				"createlink","insertimage","inserttable","htmlmode","about"]
];

cfg.width = 520;
cfg.height = 250;
cfg.statusBar = false;

editor.generate();
editor._iframe.style.border = "0px solid #ffffff";

document.write("</td></tr></table>");

*/

var cfg=new HTMLArea.Config();
sakaiRegisterButtons(cfg);
cfg.toolbar = [
				["fontname","space","fontsize","space","formatblock","space","bold","italic","underline",
				"strikethrough","subscript","superscript","linebreak","copy","cut","paste","space",
				"justifyleft","justifycenter","justifyright","justifyfull","orderedlist","unorderedlist",
				"outdent","indent","forecolor","hilitecolor","inserthorizontalrule",
				"createlink","insertimage","inserttable","htmlmode","about", "sakai_createlink"]
];
cfg.width = 520;
cfg.height = 250;
cfg.statusBar = false;

sakaiSetupRichTextarea("mainForm:HTMLArea1", cfg);
document.write("</td></tr></table>");