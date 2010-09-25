package org.grails.plugins.pdfviewer

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class PdfViewerController {
    def index = { }
    def addDocumentUrl = {
        String chrome=""
        if(params.chrome){
            chrome = """&chrome=true"""
        }
        GoogleDocPdf googleDocInstance = new GoogleDocPdf()
        googleDocInstance.path = params.fullPath
        googleDocInstance.token = UUID.randomUUID().toString()
        googleDocInstance.save(flush: true)

        String googleDocUrl = "http://docs.google.com/viewer?embedded=true&url="
        String absoluteUrl = ConfigurationHolder.config.grails.serverURL.toString()
        String url = googleDocUrl + absoluteUrl + "/pdfViewer/viewPdf/" + googleDocInstance.token + chrome
        redirect(url: url)
    }

    def viewPdf = {
        GoogleDocPdf googleDocPdf = GoogleDocPdf.findWhere('token': params.id)
        try {
            File file = new File(googleDocPdf.path)
            String name = googleDocPdf.path.replaceAll(" ", "_")
            response.setHeader("Content-disposition", "attachment; filename=" + name)
            response.setContentType("pdf")
            response.setContentLength(file.size().toInteger());
            OutputStream out = response.getOutputStream();
            out.write(file.readBytes());
            out.flush()
            out.close();
        } catch (Exception e) {
            e.printStackTrace()
        }

        if (googleDocPdf) {
            googleDocPdf.delete(flush: true)
        }
    }
}





