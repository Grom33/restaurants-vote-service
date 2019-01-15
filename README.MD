[![Build Status](https://travis-ci.org/Grom33/restaurants-vote-service.svg?branch=master)](https://travis-ci.org/Grom33/restaurants-vote-service)
[![codecov](https://codecov.io/gh/Grom33/restaurants-vote-service/branch/master/graph/badge.svg)](https://codecov.io/gh/Grom33/restaurants-vote-service)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/8f850660161e439eba36748c2aadc531)](https://www.codacy.com/app/Grom33/restaurants-vote-service?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Grom33/restaurants-vote-service&amp;utm_campaign=Badge_Grade)

# **Graduation project: "Restaurant vote system"**
    
### Graduation task
Build a voting system for deciding where to have lunch.
Design and implement a REST API using Hibernate/Spring/Spring MVC (or Spring-Boot) **without frontend** 

    - 2 types of users: admin and regular users
    - Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
    - Menu changes each day (admins do the updates)
    - Users can vote on which restaurant they want to have lunch at
    - Only one vote counted per user
    - If user votes again the same day:
        - If it is before 11:00 we asume that he changed his mind.
        - If it is after 11:00 then it is too late, vote can't be changed
    
##### Tech stack
> Spring Frameworks (Boot, DATA JPA, MVC); Hibernate;
> Embedded DB (H2);
> Swagger API Documetation 
     
## API documentation
    This documentation is available at URL: http://localhost:8787/swagger-ui.html, 
    Also there is a API client. To use the API client, use the following data for 
    authorization: 1) regular user ivan@mail.ru:password 2) admin: admin@mail.ru:password
![restaurant-rest-controller](https://user-images.githubusercontent.com/16654366/51172893-4e223780-18c5-11e9-9a7b-82c757350aee.jpg)
![dish-rest-controller](https://user-images.githubusercontent.com/16654366/51135095-48d3d700-184a-11e9-8f75-5515f8c34193.jpg)
![vote-rest-controller](https://user-images.githubusercontent.com/16654366/51135154-70c33a80-184a-11e9-980e-0952667c2aad.jpg)
![users-rest-controller](https://user-images.githubusercontent.com/16654366/51135136-66a13c00-184a-11e9-9ba8-a1e524048e6c.jpg)
![admin-rest-controller](https://user-images.githubusercontent.com/16654366/51135037-28a41800-184a-11e9-9cbd-e702cbbc62a5.jpg)

### cURL commands:
1.Get restaurants rating, today: 
 > curl -s http://localhost:8787/api/v1/restaurants/vote --user ivan@mail.ru:password
 
1.1 Get restaurants rating for 2019-01-03 : 
  
  > curl -s 'http://localhost:8787/api/v1/restaurants/vote?date=2019-01-03' --user ivan@mail.ru:password

2.Get all restaurants:

 > curl -s http://localhost:8787/api/v1/restaurants --user ivan@mail.ru:password
 
2.1 Get all restaurants paginated:

 > curl -s 'http://localhost:8787/api/v1/restaurants/?page=0&size=3' --user ivan@mail.ru:password
 
3.Create restaurant:

> curl -s -X POST -d '{"name": "TEST RESTAURANT"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8787/api/v1/restaurants --user admin@mail.ru:password

4.Get restaurant with ID #1
 
> curl -s http://localhost:8787/api/v1/restaurants/1 --user ivan@mail.ru:password 

5.Update restaurant with ID #1

> curl -s -X PUT -d '{"id": "1", "name": "TEST RESTAURANT"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8787/api/v1/restaurants/1 --user admin@mail.ru:password

6.Delete restaurant with ID #1

> curl -s -X DELETE http://localhost:8787/api/v1/restaurants/1 --user admin@mail.ru:password

7.Get restaurant with dishes

> curl -s http://localhost:8787/api/v1/restaurants/dishes --user ivan@mail.ru:password

7.1 Get restaurant with dishes paginated

> curl -s 'http://localhost:8787/api/v1/restaurants/dishes?page=0&size=3' --user ivan@mail.ru:password

8.Get dishes of restaurant with ID #2

> curl -s http://localhost:8787/api/v1/restaurants/2/dishes --user ivan@mail.ru:password

9.Add dishes to restaurant with ID #2

>curl -s -X POST -d '[{"name": "Test dish 1", "price": 99.11},{"name": "Test dish 2","price": 0.11}]' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8787/api/v1/restaurants/2/dishes --user admin@mail.ru:password

10.Get dish with id #10

> curl -s http://localhost:8787/api/v1/restaurants/dishes/10 --user ivan@mail.ru:password

11.Update dish with ID #10

> curl -s -X PUT -d '{ "id": 10, "name": "Edited dish", "date": "2019-01-03", "price": 23.9}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8787/api/v1/restaurants/dishes/10 --user admin@mail.ru:password

12.Delete dish with ID #10

> curl -s -X DELETE http://localhost:8787/api/v1/restaurants/dishes/10 --user admin@mail.ru:password

13.Get list voters of restaurant with ID #3 for today

> curl -s 'http://localhost:8787/api/v1/restaurants/3/vote' --user ivan@mail.ru:password

13.1 Get list voters of restaurant with ID #3 for 2019-01-04

> curl -s 'http://localhost:8787/api/v1/restaurants/3/vote?date=2019-01-04' --user ivan@mail.ru:password

14.Make vote for restaurant by logged user
> curl -s -X POST 'http://localhost:8787/api/v1/restaurants/3/vote' --user ivan@mail.ru:password

15.Change vote of logged user(Attention! If the current server time is over 11.00, you will get an exception "You can't vote after 11.00!")

> curl -s -X POST 'http://localhost:8787/api/v1/restaurants/3/vote' --user petr@mail.ru:password

16.Delete vote of logged user

> curl -s -X DELETE http://localhost:8787/api/v1/restaurants/vote --user irina@ya.ru:password

17.Get profile of logged user

> curl -s 'http://localhost:8787/api/v1/users' --user ivan@mail.ru:password

18.Update logged user profile

> curl -s -X PUT -d '{"id": 2,"name": "TEST TEST","email": "ivan@mail.ru", "password": "password"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8787/api/v1/users --user ivan@mail.ru:password

19.Get list of users

> curl -s 'http://localhost:8787/api/v1/admin/users' --user admin@mail.ru:password

20.Create user

> curl -s -X POST -d '{"name": "TEST USER","email": "test@mail.ru","password": "password", "roles": [ "ROLE_USER" ]}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8787/api/v1/admin/users --user admin@mail.ru:password

21.Update user with ID #2

> curl -s -X PUT -d '{"id": 2,"name": "TEST TEST","email": "ivan@mail.ru", "password": "password"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8787/api/v1/admin/users --user admin@mail.ru:password

22.Get user with ID #2

> curl -s 'http://localhost:8787/api/v1/admin/users/2' --user admin@mail.ru:password

23.Delete user with ID #2

> curl -s -X DELETE http://localhost:8787/api/v1/admin/users/2 --user admin@mail.ru:password