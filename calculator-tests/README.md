# Lessons 17 — Google Калькулятор

Мобильный UI-автотест приложения [«Калькулятор» от Google](https://play.google.com/store/apps/details?id=com.google.android.calculator). Тест нажимает кнопки приложения, выполняет вычисление и сравнивает результат на экране с заранее заданным значением.

| Действие | Выражение | Ожидаемый результат |
| --- | --- | --- |
| Сложение | 12 + 8 | 20 |
| Вычитание | 7 − 12 | −5 |
| Умножение | 9 × 7 | 63 |
| Деление | 7 ÷ 2 | 3.5 |

Один параметризованный JUnit-тест запускается четырежды. Каждый запуск создаёт отдельную Appium-сессию, очищает текущее выражение, вводит операнды и операцию, нажимает `=` и читает итоговый результат. Ожидание проверяет появление результата, само сравнение выполняется отдельным `assertEquals`. При сравнении допускаются десятичная запятая и типографский знак минуса.

## Структура

- `config/MobileConfig` — пакет приложения, адрес Appium, идентификатор устройства и таймауты.
- `driver/DriverFactory` — создание AndroidDriver с UiAutomator2.
- `base/BaseTest` — открытие и закрытие сессии для каждого набора данных.
- `pages/BasePage` — общие действия с элементами и явные ожидания.
- `pages/CalculatorPage` — локаторы, очистка, ввод выражения и чтение экрана.
- `model/Operation` — четыре арифметические операции и идентификаторы их кнопок.
- `tests/CalculatorTest` — наборы данных и проверка результата.

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

Проверено 31.10.2026 на `Google_Calculator_API_34`, Android 14, Google Калькулятор 9.3 (978998587): **4 теста, 0 ошибок, 0 падений, 0 пропусков**.

После работы эмулятор можно выключить командой `adb -s emulator-5554 emu kill`.
