
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
![restaurant-rest-controller](https://user-images.githubusercontent.com/16654366/51317143-d8a29c80-1a67-11e9-8ed1-8a611f7af045.jpg)
![dish-rest-controller](https://user-images.githubusercontent.com/16654366/51317170-ebb56c80-1a67-11e9-8816-3593c4f01de8.jpg)
![vote-rest-controller](https://user-images.githubusercontent.com/16654366/51317186-f7a12e80-1a67-11e9-8327-c5896b83a67b.jpg)
![users-rest-controller](https://user-images.githubusercontent.com/16654366/51317198-025bc380-1a68-11e9-931f-7feb0faf815d.jpg)
![admin-rest-controller](https://user-images.githubusercontent.com/16654366/51135037-28a41800-184a-11e9-9cbd-e702cbbc62a5.jpg)

### cURL commands:
***
1 Get restaurant with dishes paginated

> curl -s 'http://localhost:8787/api/v1/restaurants/dishes?page=0&size=3' 

2.Get dishes of restaurant with ID #2

> curl -s http://localhost:8787/api/v1/restaurants/2/dishes

3.Make vote for restaurant by logged user

> curl -s -X POST 'http://localhost:8787/api/v1/restaurants/2/vote' --user ivan@mail.ru:password

4.Get restaurants rating for today: 
 
 > curl -s http://localhost:8787/api/v1/restaurants/vote
 
 ***

5.Change vote of logged user(Attention! If the current server time is over 11.00, you will get an exception "You can't change your decision after 11.00 AM!")

> curl -s -X POST 'http://localhost:8787/api/v1/restaurants/3/vote' --user ivan@mail.ru:password

6.Delete vote of logged user

> curl -s -X DELETE http://localhost:8787/api/v1/restaurants/vote --user irina@ya.ru:password

7.Get all restaurants:

 > curl -s http://localhost:8787/api/v1/restaurants
 
8 Get all restaurants paginated:

 > curl -s 'http://localhost:8787/api/v1/restaurants?page=0&size=3'

9 Get restaurants rating for 2019-01-03 : 
  
  > curl -s 'http://localhost:8787/api/v1/restaurants/vote?date=2019-01-03'
 
10.Create restaurant:

> curl -s -X POST -d '{"name": "TEST RESTAURANT"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8787/api/v1/restaurants --user admin@mail.ru:password

11.Get restaurant with ID #1
 
> curl -s http://localhost:8787/api/v1/restaurants/1 

12.Get restaurant with ID# 1  with dishes for 2019-01-04

> curl -s 'http://localhost:8787/api/v1/restaurants/1/detail?date=2019-01-04' 

13.Update restaurant with ID #1

> curl -s -X PUT -d '{"id": "1", "name": "TEST RESTAURANT"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8787/api/v1/restaurants/1 --user admin@mail.ru:password

14.Delete restaurant with ID #1

> curl -s -X DELETE http://localhost:8787/api/v1/restaurants/1 --user admin@mail.ru:password

15.Get restaurant with dishes

> curl -s http://localhost:8787/api/v1/restaurants/dishes


16.Add dishes to restaurant with ID #2

>curl -s -X POST -d '[{"name": "Test dish 1", "price": 99.11},{"name": "Test dish 2","price": 0.11}]' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8787/api/v1/restaurants/2/dishes --user admin@mail.ru:password

17.Get dish with id #10

> curl -s http://localhost:8787/api/v1/restaurants/dishes/10

18.Update dish with ID #10

> curl -s -X PUT -d '{ "id": 10, "name": "Edited dish", "date": "2019-01-03", "price": 23.9}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8787/api/v1/restaurants/dishes/10 --user admin@mail.ru:password

19.Delete dish with ID #10

> curl -s -X DELETE http://localhost:8787/api/v1/restaurants/dishes/10 --user admin@mail.ru:password

20.Get list voters of restaurant with ID #3 for today

> curl -s 'http://localhost:8787/api/v1/restaurants/3/vote'

21 Get list voters of restaurant with ID #3 for 2019-01-04

> curl -s 'http://localhost:8787/api/v1/restaurants/3/vote?date=2019-01-04'

22.Get profile of logged user

> curl -s 'http://localhost:8787/api/v1/users' --user ivan@mail.ru:password

23.Update logged user profile

> curl -s -X PUT -d '{"id": 2,"name": "TEST TEST","email": "ivan@mail.ru", "password": "password"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8787/api/v1/users --user ivan@mail.ru:password

24.Get list of users

> curl -s 'http://localhost:8787/api/v1/admin/users' --user admin@mail.ru:password

25.Create user by admin

> curl -s -X POST -d '{"name": "TEST USER","email": "test@mail.ru","password": "password", "roles": [ "ROLE_USER" ]}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8787/api/v1/admin/users --user admin@mail.ru:password

26 New user registration by anonymous

> curl -s -X POST -d '{"name": "mike","email": "mike@mail.ru","password": "password"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8787/api/v1/users/registration

27.Update user with ID #2

> curl -s -X PUT -d '{"id": 2,"name": "TEST TEST","email": "ivan@mail.ru", "password": "password"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8787/api/v1/admin/users --user admin@mail.ru:password

28.Get user with ID #2

> curl -s 'http://localhost:8787/api/v1/admin/users/2' --user admin@mail.ru:password

29.Delete user with ID #2

> curl -s -X DELETE http://localhost:8787/api/v1/admin/users/2 --user admin@mail.ru:password