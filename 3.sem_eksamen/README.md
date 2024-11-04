Back exam fall 2024 - Trip Planning Application

Task 1: 
-.
Task 2:
- Established hibernateconfig class, with method returning EntityManagerFactory.
- Set up entity classes in their packages.
- Implemented DAO classes in their packages.
- Implemented DTO classes and enum.
- Generic IDAO interface and ITripGuideDAO created.

Task 3:
- Controllers created in their packages.
- TripRoutes created matching the methods in the controllers.
- Ran into issues with endpoints giving a 404 error.
- The requests in dev.http are passing, but all give a 404 error, which makes me think the problem is not with the methods themselves, but something else.
- The endpoints cannot be reached.
3.3.5: A reason to use PUT instead of POST here is that every time we use PUT, the data gets updated and will have the same effect every time, and will not create duplicates. In fancier words, it is idempotent.

Task 4:
- Method returns exceptions now but no way to test.

Task 5:
- Task 5.1 has been created but no way to test, since error with endpoints.
- 5.2 is not finished.

Task 6:
- .
