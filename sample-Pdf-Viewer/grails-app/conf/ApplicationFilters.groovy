class ApplicationFilters {

    def filters = {
        debug(controller: '*', action: '*') {
            before = {
                println("GrailsAccessLog:${new Date()}:--${params}");
            }
        }
    }
}