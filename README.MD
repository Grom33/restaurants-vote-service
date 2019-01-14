[![Build Status](https://travis-ci.org/Grom33/restaurants-vote-service.svg?branch=master)](https://travis-ci.org/Grom33/restaurants-vote-service)
[![codecov](https://codecov.io/gh/Grom33/restaurants-vote-service/branch/master/graph/badge.svg)](https://codecov.io/gh/Grom33/restaurants-vote-service)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/8f850660161e439eba36748c2aadc531)](https://www.codacy.com/app/Grom33/restaurants-vote-service?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Grom33/restaurants-vote-service&amp;utm_campaign=Badge_Grade)

# **Restaurant vote system**
    
## Voiting system for deciding where to have lunch
### Grdauation task
Build a voiting system for deciding where to have lunch.

    - 2 types of users: admin and regular users
    - Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
    - Menu changes each day (admins do the updates)
    - Users can vote on which restaurant they want to have lunch at
    - Only one vote counted per user
    - If user votes again the same day:
        - If it is before 11:00 we asume that he changed his mind.
        - If it is after 11:00 then it is too late, vote can't be changed
    
### Tech stack
    - Spring Frameworks (Boot, DATA JPA, WEB)
    - Embedded DB (H2)
    - Swagger API Documetation 
     
## API documentation
    This documentation is available at URL: http://localhost:8787/swagger-ui.html, 
    Also there is a API client.
![restaurant-rest-controller](https://user-images.githubusercontent.com/16654366/51135114-56895c80-184a-11e9-8658-6114f5ec3dc8.jpg)
![dish-rest-controller](https://user-images.githubusercontent.com/16654366/51135095-48d3d700-184a-11e9-8f75-5515f8c34193.jpg)
![vote-rest-controller](https://user-images.githubusercontent.com/16654366/51135154-70c33a80-184a-11e9-980e-0952667c2aad.jpg)
![users-rest-controller](https://user-images.githubusercontent.com/16654366/51135136-66a13c00-184a-11e9-9ba8-a1e524048e6c.jpg)
![admin-rest-controller](https://user-images.githubusercontent.com/16654366/51135037-28a41800-184a-11e9-9cbd-e702cbbc62a5.jpg)

### Quickstart
    1. git clone https://github.com/Grom33/.....git 
    2. cd ......
    3. mvn clean package docker:build
    4. docker-compose -f src/main/docker/docker-compose.yml up
### CURL command



 1.
 2.
 
