class UrlMappings {

	static mappings = {
		"/$controller/$action?/$token?"{
			constraints {
				// apply constraints here
			}
		}

		"/"(view:"/index")
		"500"(view:'/error')
	}
}
