package org.grails.samplePdfViewer

import org.grails.plugins.pdfviewer.GoogleDocPdf
import org.codehaus.groovy.grails.commons.ConfigurationHolder




class PdfController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def pdfService

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [pdfInstanceList: Pdf.list(params), pdfInstanceTotal: Pdf.count()]
    }

    def create = {
        def pdfInstance = new Pdf()
        pdfInstance.properties = params
        return [pdfInstance: pdfInstance]
    }

    def save = {
        Pdf pdfInstance
        String fileName = params.file.getOriginalFilename()
        String path = ConfigurationHolder.config.pdf.filePath + "pdf"
        pdfInstance = pdfService.saveFile(path, params?.file?.getBytes(), fileName)
        if (pdfInstance) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'pdf.label', default: 'Pdf'), pdfInstance.id])}"
            redirect(action: "show", id: pdfInstance.id)
        }
        else {
            render(view: "create", model: [pdfInstance: pdfInstance])
        }
    }

    def show = {
        def pdfInstance = Pdf.get(params.id)
        if (!pdfInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'pdf.label', default: 'Pdf'), params.token])}"
            redirect(action: "list")
        }
        else {
            [pdfInstance: pdfInstance]
        }
    }

    def edit = {
        def pdfInstance = Pdf.get(params.token)
        if (!pdfInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'pdf.label', default: 'Pdf'), params.token])}"
            redirect(action: "list")
        }
        else {
            return [pdfInstance: pdfInstance]
        }
    }

    def update = {
        def pdfInstance = Pdf.get(params.token)
        if (pdfInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (pdfInstance.version > version) {

                    pdfInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'pdf.label', default: 'Pdf')] as Object[], "Another user has updated this Pdf while you were editing")
                    render(view: "edit", model: [pdfInstance: pdfInstance])
                    return
                }
            }
            pdfInstance.properties = params
            if (!pdfInstance.hasErrors() && pdfInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'pdf.label', default: 'Pdf'), pdfInstance.id])}"
                redirect(action: "show", id: pdfInstance.id)
            }
            else {
                render(view: "edit", model: [pdfInstance: pdfInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'pdf.label', default: 'Pdf'), params.token])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def pdfInstance = Pdf.get(params.token)
        if (pdfInstance) {
            try {
                pdfInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'pdf.label', default: 'Pdf'), params.token])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'pdf.label', default: 'Pdf'), params.token])}"
                redirect(action: "show", id: params.token)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'pdf.label', default: 'Pdf'), params.token])}"
            redirect(action: "list")
        }
    }

    def download = {
        def pdfInstance = Pdf.get(params.id)
        try {
            File file = new File(pdfInstance.path + "/" + pdfInstance.fileName)
            response.setHeader("Content-disposition", "attachment; filename=" + pdfInstance.fileName)
            response.setContentType(pdfInstance.mimeType)
            response.setContentLength(file.size().toInteger());
            OutputStream out = response.getOutputStream();
            out.write(file.readBytes());
            out.flush()
            out.close();
        } catch (Exception e) {
            e.printStackTrace()
        }

    }
}