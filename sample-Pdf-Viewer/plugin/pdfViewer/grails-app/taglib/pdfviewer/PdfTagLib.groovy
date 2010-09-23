package pdfviewer

class PdfTagLib {
    static namespace = "pdf"
    def g = new org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib()


    def isSupportedByGoogleDocs = {attrs, body ->        
        String path        
        attrs['controller']="pdfViewer"
        attrs['action']="addDocumentUrl"
        path = attrs['params'].'fullPath'
        String extension = path.split("\\.").last()
        if (extension.equalsIgnoreCase("pdf") || extension.equalsIgnoreCase("ppt")) {
            out << g.link(attrs, body)
        } else {
            out << body()
        }
    }
}
