# iplFantasy League

Final project for INFO 6250 - Web Development Tools and Methods course at Northeastern University's College of Engineering as a part of it's Masters in Science Information Systems coursework. The course aims at strengthening students knowledge of Spring MVC framework and integration with Hibernate which is an Object Relational Mapping tool for Java developers, helping access database using Java objects.  

You can find more about the course at http://msis.neu.edu/spring2019/31237.pdf


## Description 

• iplFantasyLeague was built to create a virtual gaming environment where users could compete on their knowledge of a players performance for each match and compare against others within the game.

• To make it a feature-rich application and more real time we implemented JavaMail for Email using SMTP, BCrypt for password hashing of all user and system actions.

• Apart from this, the system had 2 roles, giving the admin controls to scale up or sclae down the application by adding/deleting teams and players.

• The project utilized Bootstrap, CSS and some online templating for screens. The front-end was kept to a minimum since the main goal of the project was to be able to understand the MVC framework and get hands-on experience with different APIs to access database objects.

## Design & Considerations

• The project was built upon MVC framework(mainly Spring MVC) allowing for complete abstraction between the data model, the controller/business logic and the view accessible to users.

• The admin had complete control over adding any team and players. A major responsibility for admin was to update player scores after every match. Once completed, it would automatically reflect on all necessary tables and users would be able to see their for that fixture.

• An assumption made was admin will not be a user ie, he will not have access to creating and registering for any fixtures.

## Running the application locally

Once you have sucessfully downloaded the project, you can directly open and run the file in your [Eclipse IDE](https://www.eclipse.org/downloads/) 

To ensure a clean build, also install the [Spring Tool Suite plugin](https://download.springsource.com/release/STS/3.9.8.RELEASE/dist/e4.11/spring-tool-suite-3.9.8.RELEASE-e4.11.0-win32-x86_64.zip) 
