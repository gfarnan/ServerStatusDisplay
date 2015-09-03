dataSource {
    pooled = true
    driverClassName = "oracle.jdbc.driver.OracleDriver"
    username = "Enter User Name Here"
    password = "Enter Password Here"
}

/*
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
//    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory' // Hibernate 3
    cache.region.factory_class = 'org.hibernate.cache.ehcache.EhCacheRegionFactory' // Hibernate 4
    singleSession = true // configure OSIV singleSession mode
    flush.mode = 'manual' // OSIV session flush mode outside of transactional context
}
*/
environments {
    development {
        dataSource {
            dbCreate = "update" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:oracle:thin:@oak:1522:SUSTAIN"
            username = "serverstatus"
            password = "serverstatus"
        }
    }
	test {
        dataSource {
            dbCreate = "update" // one of 'create', 'create-drop','update'
            url = "jdbc:oracle:thin:@oak:1522:SUSTAIN"
			username = "serverstatus"
			password = "serverstatus"

        }
    }
    production {
        dataSource {
            dbCreate = "update"
            url = "jdbc:oracle:thin:@oak:1522:SUSTAIN"
            username = "serverstatus"
            password = "serverstatus"
        }
    }
}

dialect = "org.hibernate.dialect.Oracle10gDialect"