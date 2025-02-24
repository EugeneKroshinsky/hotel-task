# Hotel task #

[Макет базы данных](https://github.com/EugeneKroshinsky/hotel-task/blob/main/diagrams/database-diagram.png)

[План выполнения](#plan)

[Инструкции по запуску](#instructions)

[swagger-documentation.json](https://github.com/EugeneKroshinsky/hotel-task/blob/feature/swagger/swagger-documentation.json)



### Задание ###
Необходимо разработать RESTful API приложение для работы с отелями со следующими методами:

- GET /hotels - получение списка всех отелей с их краткой информацией
- GET /hotels/{id} - получение расширенной информации по конкретному отелю
- GET /search - поиск получение списка всех отелей с их краткой информацией по следующим параметрам: name, brand, city, county, amenities. Например: /search?city=minsk
- POST /hotels - создание нового отеля
- POST /hotels/{id}/amenities - добавление списка amenities к отелю
- GET /histogram/{param} - получение количества отелей сгруппированных по каждому значению указанного параметра. Параметр: brand, city, county, amenities.

Приложение должно запускаться из консоли при помощи команды mvn spring-boot:run
Порт для запуска: 8092

У всех методов должен быть общий префикс "property-view". Например: GET /property-view/hotels

Используемые технологии:
Maven, Java 17+, Spring Boot, Spring JPA, Liquibase

В качестве базы данных:
H2

<a id="plan"></a>
### План выполнения: ###
1) Создание макета базы данных для сущностей:
    - hotel
    - address
    - contact
    - arrival_time
    - amenity
2) Создание моделей для этих сущностей (Entities) [feature/entities-creation](https://github.com/EugeneKroshinsky/hotel-task/tree/feature/entities-creation)
3) Подключение Liquibase, создание миграций для создания сущностей и заполнения их данными. [feature/adding-liquibase](https://github.com/EugeneKroshinsky/hotel-task/tree/feature/adding-liquibase)
4) Создание контроллеров: [feature/controller-creation](https://github.com/EugeneKroshinsky/hotel-task/tree/feature/controller-creation)
   - HotelController
     - GET /property-view/hotels
     - GET /property-view/hotels/{id}
     - POST /property-view/hotels
     - POST /property-view/hotels/{id}/amenities
   - SearchController
     - GET /property-view/search
   - HistogramController
     - GET /property-view/histogram/{param}
     
   Создание сервиса HotelService с методами:
   - getAllHotels()
   - getHotelById(Long id) 
   - addAmenities(Long id, List<String> amenities)
   - createHotel(HotelCreateDtoRequest hotelCreateDtoRequest)
   - getHistogram(String param)
   - search(Map<String, String> params)
   
   Создание DTO:
   - HotelCreateDtoRequest
   - HotelDetailsDtoResponse
   - HotelDtoResponse
5) Реализация DTO [feature/dto-implementation](https://github.com/EugeneKroshinsky/hotel-task/tree/feature/dto-implementation)
   - Добавление ModelMapper
   - Добавление Hibernate Validation
6) Создание классов Repository [feature/repository-creation](https://github.com/EugeneKroshinsky/hotel-task/tree/feature/repository-creation)
7) Обработка всех исключений
   - Создание класса @RestControllerAdvice
8) Тестирование через Postman
9) Написание модульных тестов  [feature/tests](https://github.com/EugeneKroshinsky/hotel-task/tree/feature/tests)
   - Модульное тестирование сервисов
   - Модульное тестирование контроллеров
   - Модульное тестирование репозиториев
10) Добавление документации Swagger [feature/swagger](https://github.com/EugeneKroshinsky/hotel-task/tree/feature/swagger)
<a id="instructions"></a>
### Инструкции по запуску ###

Запуск приложения через консоль:
```
    mvn spring-boot:run
```

Запуск тестов через консоль:
```
    mvn test
```

Url для получения документации swagger:
```
    http://localhost:8092/v3/api-docs
```

Url для получения UI документации swagger:
```
   http://localhost:8092/swagger-ui/index.html
```

Все остальные Url:
```
   Получить все отели:
      GET http://localhost:8092/property-view/hotels 
   Получить отель по id:
      GET http://localhost:8092/property-view/hotels/{id}
   Получить поиск по фильтрам:
      GET http://localhost:8092/property-view/search
   Получить данные о количестве отелей по параметрам:
      GET http://localhost:8092/property-view/histogram/{param}
   Создать отель:
      POST http://localhost:8092/property-view/hotels
   Добавить amenities:
      POST http://localhost:8092/property-view/hotels/{id}/amenities
```