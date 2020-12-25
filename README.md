# PingApp client/server

Console application that launches client or server based on provided parameters.

## Getting Started

Please download PingApp and unzip it locally.

After unzipping the file, folder structure should be like this:
```
 Directory of PingApp

25/12/2020  13:06    <DIR>          .
25/12/2020  13:06    <DIR>          ..
25/12/2020  13:06           660,756 PingApp.jar
20/12/2020  23:40               238 run-catcher.bat
23/12/2020  22:19               214 run-catcher.sh
20/12/2020  23:40               259 run-pitcher.bat
23/12/2020  22:17               236 run-pitcher.sh
```

To run client/pitcher app either use one of the provided scripts, or specify parameters directly. For the provided scripts please adjust JAVA_HOME and PATH to reflect your environment before running.

```
java -classpath PingApp.jar com.company.pingapp.PingApp -p -port 10001 -size 50 -mps 10 -hostname localhost
```

For the server/catcher sample parameters would be:

```
java -classpath PingApp.jar com.company.pingapp.PingApp -c -port 10001 -bind 127.0.0.1
```

Full list of command line options is shown bellow.

```
usage: com.company.pingapp.PingApp
 -bind,--bind <arg>                 Binding address (Catcher). Required.
 -c,--catcher                       Specifies operation mode for Catcher.
 -hostname,--hostname <arg>         Hostname (Pitcher). Required
 -mps,--messages-per-second <arg>   Messages per second rate (Pitcher).
                                    Default is 1.
 -p,--pitcher                       Specifies operation mode for Pitcher.
 -port,--port <arg>                 Port number (Pitcher/Catcher).
                                    Required.
 -size,--size <arg>                 Message size (Pitcher). Default is
                                    3000 bytes.
```

## Built With

* [Dropwizard](http://www.dropwizard.io/1.0.2/docs/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [ROME](https://rometools.github.io/rome/) - Used to generate RSS Feeds


## Authors

* **Vujadin Vidovic** - *Initial work* - [PurpleBooth](https://github.com/PurpleBooth)

