# Server Upgrader Servlet

Small http servlet that allows easier deploying and upgrading of the backend server.

## Service File Commands

After installing:

`$ systemctl daemon-reload`

Service Enable:

`$ systemctl enable election-upgrader`

Service Start:

`$ systemctl start election-upgrader`

Service status:

`$ systemctl status election-upgrader`

## Servlet Commands

### Building servlet

`$ go build ElectionDataQualityUpgrader.go`

### Running servlet

`$ ./ElectionDataQualityUpgrader`

**NOTE:** You shouldn't have to run the servlet yourself - the service file will run the latest compiled version.
