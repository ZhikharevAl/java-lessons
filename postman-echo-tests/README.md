# Lessons 16 — Postman Echo

Автотесты всех шести запросов из папки **Request Methods** официальной коллекции Postman Echo. Для каждого запроса проверяются код ответа `200` и значения всех полей тела ответа.

## Коллекции

- `collections/Postman Echo.postman_collection.json` — исходная коллекция, скачанная после входа в Postman 31.10.2026, Collection Format v2.1.
- `collections/Request Methods.tests.postman_collection.json` — отдельная коллекция с шестью запросами и автотестами для импорта в Postman. У каждого запроса две проверки: код ответа и полное равенство JSON.

Источник: [Postman Echo в Published Postman Templates](https://www.postman.com/postman/published-postman-templates/collection/631643-f695cab7-6878-eb55-7943-ad88e1ccfd65).

## Покрытие

| Запрос в коллекции | Метод и адрес | Данные |
| --- | --- | --- |
| GET Request | GET `/get` | Query: `foo1=bar1&foo2=bar2` |
| POST Raw Text | POST `/post` | Raw text: `{\n    "test": "value"\n}` |
| POST Form Data | POST `/post` | URL-encoded: `foo1=bar1&foo2=bar2` |
| PUT Request | PUT `/put` | `This is expected to be sent back as part of response body.` |
| PATCH Request | PATCH `/patch` | То же текстовое тело |
| DELETE Request | DELETE `/delete` | То же текстовое тело |

Тела и параметры взяты из скачанной коллекции. У `POST Raw Text` явно задан `Content-Type: text/plain`, поэтому JSON-подобный текст возвращается строкой в `data`, а поле `json` равно `null`. У формы проверяются и `form`, и `json`.

Ожидаемые ответы хранятся отдельно в `src/test/resources/expected`. Проверяется весь JSON: набор полей, значения, типы, вложенные объекты, пустые объекты и `null`. Порядок ключей объектов не влияет на результат. Дополнительное или пропавшее поле вызывает падение теста; поля из ответа не копируются в ожидаемый результат.

## Запуск Java-тестов

Нужны JDK 17+ и Maven, а также доступ к `https://postman-echo.com`. Команда из корня репозитория:

```shell
mvn -f postman-echo-tests/pom.xml test
```

Стек: JUnit 5, REST Assured, Jackson. Отчёты: `postman-echo-tests/target/surefire-reports`.

Структура Java-кода:

- `config/ApiConstants` — адрес сервиса, пути, заголовки, таймауты и ожидаемый статус.
- `data/EchoTestData` — параметры и тела запросов.
- `client/EchoClient` — настройка и отправка запросов.
- `base/BaseApiTest` — загрузка ожидаемого JSON и общие проверки.
- `tests/RequestMethodsTest` — шесть независимых тестов.

## Запуск в Postman и Newman

В Postman импортируйте `collections/Request Methods.tests.postman_collection.json`, откройте Runner и запустите папку **Request Methods**. Ожидаемый результат — 6 запросов и 12 успешных проверок. Для Echo авторизация и переменные окружения не нужны.

Для запуска из командной строки установите Newman (`npm install -g newman`), затем из корня репозитория:

```shell
newman run "postman-echo-tests/collections/Request Methods.tests.postman_collection.json" --folder "Request Methods" --timeout-request 20000
```

Обе реализации используют одинаковые ожидаемые ответы. После изменения JSON-файлов тестовую коллекцию можно пересобрать с помощью Node.js:

```shell
node postman-echo-tests/scripts/build-postman-collection.cjs
```

Для полного сравнения вложенного объекта `headers` запросы отправляются с постоянными `User-Agent`, `Accept` и `Content-Type`. В Postman-коллекции отключены автоматические `Postman-Token`, `Cache-Control` и cookie jar только для этих тестовых запросов. Запрошенный `Accept-Encoding: identity` сервис Echo отражает как `gzip, br`; это значение зафиксировано в ожидаемых ответах. Если поведение публичного сервиса изменится, тесты покажут расхождение.

Проверено на реальном сервисе: 6/6 Java-тестов, 12/12 проверок Newman.
