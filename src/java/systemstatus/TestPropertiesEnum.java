package systemstatus;


/**
 * Enum to represent the fields in the test properties file
 * @author slenihan
 *
 */
public enum TestPropertiesEnum {
	
	// Any properties that tests may require should be listed here. Some examples below
	HADOOP_NAME_NODE("namenode.server", true, "Hadoop name node server", ""),
	HADOOP_DATA_NODE("datanode.server", true, "Hadoop data node server", ""),	
	TEST_LOG_DIRECTORY("test.log.dir", true, "The directory where test logs will be stored", ".\\resources\\logs"),
	BROWSER("browser", true, "The browser to run UI tests with. "
			+ "Set this to overwrite browser parameters set in suite files", ""),
	
	// DBM REST API Settings
    DBM_REST_API_LOG_PATH("dbm.rest.api.log.path", true, "The full path to the log file.", ""),
    DBM_REST_API_LOG_NAME("dbm.rest.api.log.name", true, "The name of the log file.", ""),
    DBM_REST_API_SERVER("dbm.rest.api.server", true, "The server hosting the dbm rest api service", ""),
    DBM_REST_API_PORT("dbm.rest.api.port", true, "The port used by dbm rest api service", ""),
    DBM_REST_API_VERSION("dbm.rest.api.version", true, "The version of dbm rest api to use", ""),
	
    // ICWB REST API Settings
    ICWB_REST_API_SERVER("icwb.rest.api.server", true, "The server hosting the icwb rest api service", ""),
    ICWB_REST_API_PORT("icwb.rest.api.port", true, "The port used by icwb rest api service", ""),
    ICWB_REST_API_VERSION("icwb.rest.api.version", true, "The version of icwb rest api to use", ""),
    
	//IRIS Settings
    IRIS_SSH_USER_NAME("iris.ssh.user", true, "IRIS ssh user", ""),
    IRIS_SSH_USER_PASS("iris.ssh.pass", true, "IRIS ssh pass", ""),
	IRIS_APPLICATION_SERVER("iris.application.server", true, "IRIS application server", ""),
	IRIS_DB_SERVER("iris.db.server", true, "Iris application server", ""),
    IRIS_USER_NAME("iris.user", true, "Iris user", ""),
    IRIS_USER_PASS("iris.pass", true, "Iris pass", ""),
    IRISGEN_HOST("irisgen.host", true, "irisGen host server", ""),
    IRISGEN_TRAFFIC_DIR("irisgen.traffic.dir", true, "irisGen traffic dir", ""),
    IRISGEN_TRAFFIC_FILE("irisgen.script", true, "irisGen traffic script", ""),

	
	//DISCOVERY UI
    DISCOVERY_UI_SERVER("discovery.ui.server",true,"Discovery Front End URL",""),
	DISCOVERY_UI_USER_NAME("discovery.ui.user", true, "Discovery user", ""),
	DISCOVERY_UI_USER_PASS("discovery.ui.pass", true, "Discovery password", ""),
	
	// Zephyr settings
	ENABLE_ZEPHYR_REPORTING("enable.zephyr.reporting", true, "Enable/disable test result reporting "
			+ "integration with Zephyr", "false"),
	ZEPHYR_URL("zephyr.url", true, "The base URL of the zephyr rest api", "http://tekcomms-jira.global.tektronix.net:8085"),
	ZEPHYR_AUTH_TOKEN("zephyr.auth.token", true, "Authentication token to log in to zephyr", ""),
	ZEPHYR_PROJECT_ID("zephyr.project.id", true, "The id of the zephyr project", ""),


	// Service Modeling  - CLI settings
	MM_SSH_USER_NAME("mm.ssh.user", true, "Model manager CLI ssh user", ""),
	MM_SSH_USER_PASS("mm.ssh.pass", true, "Model manager CLI  ssh pass", ""),
	MM_APPLICATION_HOME("mm.application.home", true, "Model manager executable home directory", ""),
	MM_MODEL_LOCATION_HOME("mm.model.file.location", true, "Location of model files to be uploaded", ""),
	MM_SCRIPT_LOCATION_HOME("mm.script.location", true, "Location of script files to be uploaded", ""),
	MM_APPLICATION_SERVER("mm.application.server", true, "Model manager CLI application server", ""),

	// Model Manager Server
	MODEL_MANAGER_LOG_PATH("mm.server.log.path", true, "Model Manager Server log path", ""),
	MODEL_MANAGER_LOG_NAME("mm.server.log.name", true, "Model Manager Server log name", ""),
	
	// DBM ILM
	DBM_ILM_LOG_PATH("dbm.ilm.log.path", true, "DBM ILM log path", ""),
	DBM_ILM_LOG_NAME("dbm.ilm.log.name", true, "DBM ILM log name", ""),

	// DBM Bulk Loading
	DBM_BULK_LOADING_LOG_PATH("dbm.bulk.loading.log.path", true, "DBM Bulk Loading log path", ""),
	DBM_BULK_LOADING_LOG_NAME("dbm.bulk.loading.log.name", true, "DBM Bulk Loading log name", ""),
	
	
	// Service Modeling  - CLI settings
	DB_JDBC_DRIVER("db.jdbc.driver", true, "", ""),
	DB_URL("db.db.url", true, "", ""),
	DB_USER("db.user", true, "", ""),
	DB_PASSWORD("db.password", true, "", "");
	
	
    private String propertyName = null;
    private boolean optional = false;
    private String description = null;
    private String defaultValue = null;
    
    private TestPropertiesEnum(String propertyName, boolean optional, String description, String defaultValue){
    	this.propertyName = propertyName;
    	this.optional = optional;
    	this.description = description;
    	this.defaultValue = defaultValue;
    }

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public boolean isOptional() {
		return optional;
	}

	public void setOptional(boolean optional) {
		this.optional = optional;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
    
    

}
