package org.grails.plugins.pdfviewer

class GoogleDocPdf {
    String path
    String token
    Date dateCreated


    static constraints = {
        path(nullable: false)
        token(nullable: false)
    }
}
