
# Software Choices Frontend


### Overview

This is a Scala/Play Framework microservice that provides a UI to search a database of MTD VAT Software Providers. 
Having suitable software to submit VAT returns is a requirement of MTD VAT.

The list of software providers is loaded from the `SofwareProviders.json` file.
To add new providers, update this file and the `TestSoftwareProviders.json` file then run the tests. 

### Running the service locally

```
sbt "run 9590 -Dapplication.router=testOnlyDoNotUseInAppConf.Routes"
```

#### Using Service Manager

```
sm --start SOFTWARE_CHOICES_ALL -r
```
The above command will run all the dependent services and start software choices frontend. 

Link to app running locally:

http://localhost:9590/making-tax-digital-software

### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
