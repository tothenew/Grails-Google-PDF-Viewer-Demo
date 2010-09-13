dataSource {
    pooled = true
    driverClassName = "com.mysql.jdbc.Driver"
    username = "root"
    password = "igdefault"
    dialect = 'org.hibernate.dialect.MySQL5InnoDBDialect'
}

hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "create-drop" // one of 'create', 'create-drop','update'
            url = "jdbc:mysql://localhost:3306/pdfApp?zeroDateTimeBehavior=convertToNull"
        }
    }

    test {
        dataSource {
            dbCreate = "create-drop" // one of 'create', 'create-drop','update'
            url = "jdbc:mysql://localhost:3306/pdfApp?zeroDateTimeBehavior=convertToNull"        }
    }
    production {
        dataSource {
            dbCreate = "create-drop" // one of 'create', 'create-drop','update'
            username = "root"
            password = "igdefault"
            url = "jdbc:mysql://this:3306/pdfApp?zeroDateTimeBehavior=convertToNull"
        }
    }
}
