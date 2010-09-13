package org.grails.samplePdfViewer

class Pdf {
    String path
    String fileName
    String mimeType

    static constraints = {
        path(nullable:false)
        fileName(nullable:false)
        mimeType(nullable:false)
    }
}