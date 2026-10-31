# Lessons 18 — Google Калькулятор и Allure

Мобильный UI-автотест приложения [«Калькулятор» от Google](https://play.google.com/store/apps/details?id=com.google.android.calculator). Тест нажимает кнопки приложения, выполняет вычисление и сравнивает результат на экране с заранее заданным значением.

За основу взят тест из `lessons-17`. В теме 18 добавлены Allure-отчёт, шаги, параметры, сведения об окружении и вложения.

| Действие | Выражение | Ожидаемый результат |
| --- | --- | --- |
| Сложение | 12 + 8 | 20 |
| Вычитание | 7 − 12 | −5 |
| Умножение | 9 × 7 | 63 |
| Деление | 7 ÷ 2 | 3.5 |

Один параметризованный JUnit-тест запускается четырежды. Каждый запуск создаёт отдельную Appium-сессию, очищает текущее выражение, вводит операнды и операцию, нажимает `=` и читает итоговый результат. Ожидание проверяет появление результата, само сравнение выполняется отдельным `assertEquals`. При сравнении допускаются десятичная запятая и типографский знак минуса.

## Allure-отчёт

После подготовки устройства и запуска Appium выполните из корня репозитория:

```powershell
mvn -f calculator-tests/pom.xml test allure:report
```

- Результаты Allure: `calculator-tests/target/allure-results`.
- HTML-отчёт: `calculator-tests/target/allure-report/index.html`.
- Открыть отчёт через Allure CLI: `allure open calculator-tests/target/allure-report`.
- Сформировать отчёт из уже полученных результатов без повторного запуска тестов: `mvn -f calculator-tests/pom.xml allure:report`.

Отчёт формируется одним HTML-файлом со встроенными данными и вложениями. `index.html` можно открыть напрямую в браузере или передать отдельно для просмотра.

В отчёте каждый из четырёх наборов данных отображается отдельно. Есть параметры выражения, ожидаемое значение, прочитанный результат, шаги открытия приложения, вычисления, сравнения и закрытия сессии. Тесты сгруппированы по Epic и Feature.

`AllureReportExtension` снимает экран после выполнения теста, пока Appium-сессия ещё открыта. При исключении в теле теста дополнительно прикладывает XML-иерархию элементов. Ошибка создания вложения сохраняется в отчёт текстом и не подменяет результат проверки. В разделе Environment записываются платформа, пакет приложения, устройство и версия Java.

Шаги и вложения добавляются через Java API Allure, поэтому AspectJ и дополнительный javaagent не нужны. Подключены адаптер `allure-junit5` 2.29.1, Maven-плагин 2.18.0 и Allure Report 2.33.0. Maven-плагин сам скачивает нужную версию генератора; глобальная установка CLI нужна только для команды `allure open`.

Настройки результатов находятся в `src/test/resources/allure.properties`. При запуске Maven каталог дополнительно задаётся абсолютным путём через Surefire, поэтому команда работает и из корня репозитория. Для запуска из IDE установите рабочую папку `calculator-tests`.

При повторных запусках файлы в `allure-results` накапливаются. Для отчёта только по новому прогону предварительно очистите эту папку. Сгенерированные результаты, HTML-отчёт и скачанный генератор `.allure` исключены из Git.

## Структура

- `config/MobileConfig` — пакет приложения, адрес Appium, идентификатор устройства и таймауты.
- `driver/DriverFactory` — создание AndroidDriver с UiAutomator2.
- `base/BaseTest` — открытие и закрытие сессии для каждого набора данных.
- `pages/BasePage` — общие действия с элементами и явные ожидания.
- `pages/CalculatorPage` — локаторы, очистка, ввод выражения и чтение экрана.
- `model/Operation` — четыре арифметические операции и идентификаторы их кнопок.
- `tests/CalculatorTest` — наборы данных и проверка результата.
- `reporting/AllureReportExtension` — снимки экрана, диагностика падений и окружение для Allure.

Локаторы используют resource ID приложения, поэтому названия кнопок не зависят от языка интерфейса. Проверяется финальный результат после `=`, а не предварительный ответ во время ввода.

## Подготовка

Нужны JDK 17+, Maven, Node.js 18+ с npm и Android SDK. Приложение с пакетом `com.google.android.calculator` должно быть установлено на разблокированном устройстве или эмуляторе. На физическом телефоне нужны включённая USB-отладка и подтверждённое подключение к компьютеру.

Для эмулятора в Android Studio откройте **Device Manager → Create Device**, выберите телефон и образ Android с пометкой **Google Play**. Установите приложение из Google Play, если оно отсутствует в образе. На этом компьютере для задания создано устройство **Google_Calculator_API_34** (Pixel 5, Android 14 / API 34).

Созданное устройство можно запустить из корня репозитория:

```powershell
./calculator-tests/scripts/start-emulator.ps1
```

Скрипт включает аппаратную графику и отключает Vulkan только для этого запуска: с этими настройками эмулятор успешно загрузился на данном компьютере. Повторно запускать уже работающий эмулятор не нужно. При первой установке приложения Google Play попросит войти в Google-аккаунт; дождитесь окончания установки и кнопки **Open / Открыть**.

На тестовом эмуляторе установлен Google Калькулятор **9.3 (978998587), versionCode 85022647**. Из-за ошибки входа Google Play использован [APK этой версии с APKMirror](https://www.apkmirror.com/apk/google-inc/google-calculator/google-calculator-9-3-978998587-release/google-calculator-9-3-978998587-2-android-apk-download/). Перед установкой выполнен `apksigner verify --print-certs`; контрольная сумма SHA-256 совпала с опубликованной:

```text
ec133658e784bd90ca6de698be3be85041fe5c1c3c1ce12dc4ddf7b6ae22ad3a
```

APK хранится локально в `target/google-calculator-9.3.apk` и не включается в Git. Для установки уже скачанного и проверенного файла из корня репозитория:

```powershell
adb -s emulator-5554 install calculator-tests/target/google-calculator-9.3.apk
```

Для работы установленного Калькулятора и самого автотеста вход в Google-аккаунт не требуется.

Версии зависимостей закреплены: Appium Server 2.19.0, UiAutomator2 Driver 4.2.9, Appium Java Client 10.1.1, Selenium 4.49.0 и JUnit 5.11.0. Java Client и Selenium выбраны по [таблице совместимости Appium](https://github.com/appium/java-client#compatibility-matrix). Инструкция по окружению: [UiAutomator2 Getting Started](https://appium.github.io/appium-uiautomator2-driver/latest/getting-started/).

В PowerShell из папки `calculator-tests`:

```powershell
$env:ANDROID_HOME = "$env:LOCALAPPDATA\Android\Sdk"
# JAVA_HOME должен указывать на установленный JDK.
$env:APPIUM_HOME = Join-Path $PWD '.appium'
npm ci --no-audit --no-fund
npm run driver:install
npx appium driver doctor uiautomator2
npm run appium
```

Appium слушает только локальный адрес `127.0.0.1:4723`. Папки `node_modules`, `.appium` и `target` исключены из Git.

Если сервер Appium уже работает, повторно запускать его не нужно.

## Запуск тестов

Убедитесь, что `adb devices` показывает устройство в состоянии `device`, затем в другом терминале из корня репозитория:

```powershell
mvn -f calculator-tests/pom.xml test
```

По умолчанию используется `emulator-5554`. Для другого устройства:

```powershell
mvn -f calculator-tests/pom.xml test '-Dandroid.udid=DEVICE_ID'
```

Адрес Appium можно передать через `-Dappium.url=http://127.0.0.1:4723`, activity — через `-Dapp.activity=com.android.calculator2.Calculator`. Для старого сервера с base path `/wd/hub` передайте этот путь в адресе.

Отчёты JUnit находятся в `calculator-tests/target/surefire-reports`. Тесты не пропускаются автоматически при отсутствии устройства или Appium: запуск завершится ошибкой подключения.

Проверено 31.10.2026 на `Google_Calculator_API_34`, Android 14, Google Калькулятор 9.3 (978998587): **4 теста с Allure, 0 ошибок, 0 падений, 0 пропусков**.

После работы эмулятор можно выключить командой `adb -s emulator-5554 emu kill`.
