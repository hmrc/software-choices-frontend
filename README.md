# Software Choices Frontend

### Overview
This is a Scala/Play Framework microservice that provides a UI to search a database of MTD VAT Software Providers. 
Having suitable software to submit VAT returns is a requirement of MTD VAT.

The list of software providers is loaded from the `SoftwareProviders.json` file. To add new providers, update this file and the `TestSoftwareProviders.json` file then run the tests.

### Running the service locally

#### Using Service Manager
To run all the dependent services and start software choices frontend, you can either use `sm --start SOFTWARE_CHOICES_ALL -r` or `sm2 --start SOFTWARE_CHOICES_ALL -r` if you are using sm2.

Alternatively, if you want to start the service individually, you can either use `sm --start SOFTWARE_CHOICES_FRONTEND -r` or `sm2 --start SOFTWARE_CHOICES_FRONTEND -r` if you are using sm2.

Prior to starting the service locally, make sure the instance running in service manager is stopped. This can be done by running either `sm --stop SOFTWARE_CHOICES_FRONTEND`, or `sm2 --stop SOFTWARE_CHOICES_FRONTEND` if you are using sm2.

### From source code on your local machine
1. Clone this repository into the development environment on your machine.
2. Open a terminal and navigate to the folder you cloned the service into.
3. Run either `./run.sh` or `sbt "run 9590" -Dapplication.router=testOnlyDoNotUseInAppConf.Routes` to start the service locally.
4. Ensure all dependent services are started, using the `SOFTWARE_CHOICES_ALL` service manager profile.
5. In a browser, navigate to `http://localhost:9590/making-tax-digital-software`.

This service can be used by both JavaScript users and non-Javascript users. If you want to test the service with no JavaScript enabled, disable it in your browser site settings and reload the page.

### Feature flags and their UI labels
Each feature flag controls the corresponding functionality on the UI, as shown below:
1. `business`: Businesses
2. `agent`: Agents
3. `accounting`: VAT record keeping
4. `spreadsheets`: Bridging
5. `viewReturn`: View submitted VAT returns
6. `viewLiabilities`: Check what VAT you owe
7. `viewPayments`: View VAT payment history
8. `cognitive`: Cognitive impairments
9. `visual`: Blindness or impaired vision 
10. `hearing`: Deafness or impaired hearing 
11. `motor`: Motor or physical difficulties
12. `free`: Free version
13. `welsh`: Welsh

### Set feature switches
The service has two feature switches: 
1. To show the software provider details section for each software provider.
2. Enable Welsh language

To set the feature switches, navigate to `http://localhost:9590/making-tax-digital-software/test-only/feature-switch`.

### Test the application
To run the tests, run `sbt test`.

### License
This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").