package pdfviewer

class PdfTagLib {
	static namespace = "pdf"
	def g = new org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib()

	/**
	 * This tag checks whether the file is supported by Google Docs, If it does
	 * it generates the  token for Google , using which Google  access the application.
	 */
	def isSupportedByGoogleDocs = {attrs, body ->
		String path
		attrs['controller'] = "pdfViewer"
		attrs['action'] = "addDocumentUrl"
		path = attrs['params'].'fullPath'
		String extension = path.split("\\.").last()
		if (extension.equalsIgnoreCase("pdf") || extension.equalsIgnoreCase("ppt")) {
			out << g.link(attrs, body)
		} else {
			out << body()
		}
	}
}
