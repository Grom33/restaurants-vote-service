# **Restaurant vote system**
    
### Voiting system for deciding where to have lunch
## Features
    - 2 types of users: admin and regular users
    - Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
    - Menu changes each day (admins do the updates)
    - Users can vote on which restaurant they want to have lunch at
    - Only one vote counted per user
    - If user votes again the same day:
        - If it is before 11:00 we asume that he changed his mind.
        - If it is after 11:00 then it is too late, vote can't be changed
    
### Technology stack:
    - Spring Frameworks (Boot, DATA JPA, WEB)
    - Embeded DB (H2)
    - Swagger API Documetation  
 ## Getting Started

### Prerequisites
    - Java 8
    - Docker 
### Quickstart
    1. git clone https://github.com/Grom33/.....git 
    2. cd ......
    3. mvn clean package docker:build
    4. docker-compose -f src/main/docker/docker-compose.yml up
###  quick guide 
1. ......
2. ......