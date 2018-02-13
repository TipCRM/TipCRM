# Tip CRM
**The project of Tip CRM**

* DEV environment: http://www.potafish.com/swagger-ui.html
* Production environment: coming soon
### Maintainer
**Backend:**
 * Neil Wan (1051750377@qq.com) 
 
**Frontend:**
 * Moses Cheng (1285823170@qq.com)
 
**Test:**
 * need you 
### How to run?
Prerequisite:
* Install and set environment variables for: jdk1.8, git, maven
* Send email to backend maintainers for database account
* Fork TipCRM project and clone to your local
* Fill your database account to `application.yml` (note: **DO NOT COMMIT YOUR DATABASE ACCOUNT**)
1. Run in your IDE:
    * Import project to your IDE
    * Run with WebApplication.java
2. Run with a .jar package
    * Change work directory to root of the project
    * Use package command
        ```
        $ mvn clean package
        ```
    * Change work directory to web/target
    * Run jar package with command
        ```
        $ java -jar tipcrm*.jar 
        ```
