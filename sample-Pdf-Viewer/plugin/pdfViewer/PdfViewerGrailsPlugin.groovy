class PdfViewerGrailsPlugin {
    // the plugin version
    def version = "0.2"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.1 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def author = "Hitesh,Ankur"
    def authorEmail = "hitesh@intelligrape.com,ankur@intelligrape.com"
    def title = "Google Doc Pdf Viewer"
    def description = '''\\
    This plugin integrates power of Google doc pdf viewer to your application , though this plugin has been created using Grails-1.3.4 but it supports all version from Grails-1.1.
    Example - Whenever pdf is attached as attachment to gmail , it gives two options 1) download 2) View When user selects view is opens pdf in GoogleDoc , similar effect is provided by this plugin , all user needs to do is give full path to tag provided by taglib.

    Documentation available at
    http://github.com/IntelliGrape/Grails-Google-PDF-Viewer-Demo/wiki
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/pdf-viewer"

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before 
    }

    def doWithSpring = {
        // TODO Implement runtime spring config (optional)
    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def doWithApplicationContext = { applicationContext ->
        // TODO Implement post initialization spring config (optional)
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }
}
