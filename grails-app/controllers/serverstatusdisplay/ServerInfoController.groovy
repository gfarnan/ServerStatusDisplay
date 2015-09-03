package serverstatusdisplay
import systemstatus.ServerStatus

class ServerInfoController {

    def index() {
		systemstatus.ServerStatus.getStatus()
		def servers = Servers.list()
		[ servers:servers ]
	}
}
