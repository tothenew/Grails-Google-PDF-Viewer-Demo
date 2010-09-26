package org.grails.plugins.pdfviewer

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class PdfViewerController {
    def index = { }
    static Boolean expires = true
	/**
	 *Pdf taglib's tag isSupportedByGoogleDocs calls this method. And this makes Google access the application
     * with the specified token.
	 * After first access attemp  from Google this token gets expired only if its not opened in Chrome mode.
	 */
    def addDocumentUrl = {
        String chrome=""
        if(params.chrome){
            chrome = """&chrome=true"""
            expires =false
        }else{
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





