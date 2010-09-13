package org.grails.samplePdfViewer

class PdfService {
    static transactional = true

    def serviceMethod() {}

    Pdf saveFile(String path, byte[] bytes, String fileName) {
        Pdf pdfInstance = new Pdf()
        fileName = fileName.replaceAll(" ", "_")
        Boolean isPdf = isPdf(fileName)
        if (isPdf) {
            try {
                File file = new File(path)
                file.mkdirs()
                File actualFile = new File(file, fileName)
                actualFile.withOutputStream {out ->
                    out.write bytes
                }
                pdfInstance.mimeType = "pdf"
                pdfInstance.fileName = fileName
                pdfInstance.path = path
                pdfInstance.save(flush: true)
            }
            catch (Exception e) {
                e.printStackTrace()
            }
        }
        return pdfInstance
    }

    Boolean isPdf(String fileName) {
        Boolean isPdf = false
        if (fileName.endsWith(".pdf")) {
            isPdf = true
        }
        isPdf
    }

}