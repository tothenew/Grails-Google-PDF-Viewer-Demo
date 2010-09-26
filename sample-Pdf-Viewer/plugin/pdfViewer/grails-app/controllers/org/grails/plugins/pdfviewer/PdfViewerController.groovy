package org.grails.plugins.pdfviewer

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class PdfViewerController {
    def index = { }
    static Boolean expires = true
	/**
	 * This action generates token for pdf, which in turn is used by Google for accessing the file.
	 * After first hit from Google this token gets expired
	 */
    def addDocumentUrl = {
        String chrome=""
        if(params.chrome){
            chrome = """&chrome=true"""
            expires =false
        }else{
            println "Not Chrome mode"
            expires =true
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

	/**
	 * This action must be public, so that it can be accessed by Google.
	 * It generates bytes for Google to access pdf. And makes sure that token gives to google expires
     * if not opened in chrome mode
	 */

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

        if (googleDocPdf && expires) {
            googleDocPdf.delete(flush: true)
        }
    }
}





