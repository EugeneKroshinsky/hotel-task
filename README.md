# Hotel task #

[Макет базы данных](https://github.com/EugeneKroshinsky/hotel-task/blob/main/diagrams/database-diagram.png)

[План выполнения](#plan)



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
2) Создание моделей для этих сущностей (Entities) 
3) Подключение Liquibase, создание миграций для создания сущностей и заполнения их данными.
4) Создание контроллеров:
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
5) Реализация DTO
6) Создание классов Repository

