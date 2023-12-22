# Backend
Backend for ITClinic by Yudin Vasiliy

#Описание
RESTful веб-сервис, предназначенный для клиник. С помощью него можно вести учет расходных материалов, и система автоматически будет создавать заказы.
Все действия ведутся от лица 3 типов пользователей: Admin, Editor, DataProducer.
Предполагается, что в клинике будет 1+ (небольшое количество) Admin на клинику, 1+ Editor на клинику, 1+ DataProducer на 1 отдел клиники

###Admin
Пользователь, регулирующий систему. В частности, он может создавать, изменять, удалять/отключать и просматривать аккаунты пользователей, отделы клиники и записи о поставщиках.

###DataProducer (работа с учетом позиций)
Пользователь, генерирующий данные. В его зоне ответственности находится только один отдел клиники. Он загружает данные об инвентаре в xlsx/csv формате, данные могут быть 3 типов:
ActualData - актуальная информация о каких-то позициях
WriteOffData - информация о списании позиций
AdmissionData - информация о поставке позиций. 
================
Далее информация о каждой позиции распределяется по разным таблицам, а именно, в текущие позиции, если эта позиция встречалась ранее и для нее заданы лимиты по количеству, или в новые позиции, если позиция из файла встречается впервые или для нее нет подтвержденной информации.
================
###Editor (работа с позициями)
Пользователь, способный просматривать информацию о новых, текущих и глобальных позициях, изменять их атрибуты и лимиты, сохранять новые позиции в текущие (только при условии правильно заданных лимитов), отклонять новые позиции, и сохранять промежуточный результат изменений позиций для продолжения работы над ними в будущем.
================
При таком стечении обстоятельств, когда какой-то текущей позиции становится слишком мало по меркам ее лимитов (от изменения лимитов или изменения количества позиции), она добавляется к множеству позиций, который надо добавить для заказа от отдела клиники. Как только в этом множестве становится больше заданного количества позиций, требущих закупки, автоматически создается файл заказа и соответствующая запись о заказе в БД.
С этого момента Editor и DataProducer могут работать над заказом.
================
###Этапы работы над заказом:
1) Он создался, на этом этапе он может отображаться только editor-ам ответсвенным за соответсвующий department
2)(Опционально)Editor редактировал описание/файл заказа, все также order отображается только для editor-ов
3) Editor подтверждает order для отправки data producer, order теперь виден и editor и data producer
4)(Опционально)Data Producer замечает недочет в order, либо его описание и пишет претензию (Complaint Description), после чего оспаривает order
5)(Если Order был оспорен)Editor редактирует order, либо его описание (для устранения претензии), после чего повторно его подтверждает
Циклы жалоб и изменений на заказ могут повторяться несколько раз.
6)Data Producer отправляет order на какой-то из email
###Жалобы
Жалбы доступны всем пользователям, их можно отправлятьв пользователям в рамках отделов клиники юзера на какую-то роль(в таком случае жалобу увидеть смогут все, кто имеет нужную роль), или на определенного пользователя.
Пользователь, имеющий вышеперечисленные права доступа к жалобе, может ответить на нее, и, как только отправитель жалобы будет удовлетворен, он сможет закрыть жалобу.
#Наименование
Модуль учета расходных материалов Vista
#Предметная область
Учет инвентаря, заказов
#Данные
В файле ER
#REST API
в файле API_doc
#Технологии разработки
Spring Boot Framework, Hibernte (ORM), язык Kotlin
СУБД PostgreSQL
#Тестирование
Вручную через Postman
