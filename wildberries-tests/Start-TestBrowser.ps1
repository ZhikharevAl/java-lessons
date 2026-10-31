param(
    [int]$Port = 9222
)

$ErrorActionPreference = 'Stop'
$chromePath = Join-Path $env:ProgramFiles 'Google\Chrome\Application\chrome.exe'
if (-not (Test-Path -LiteralPath $chromePath)) {
    throw 'Google Chrome не найден. Укажите его путь в переменной chromePath этого скрипта.'
}

$testProfilePath = Join-Path $PSScriptRoot '.browser-profile'
$browserArguments = @(
    '--remote-debugging-address=127.0.0.1'
    "--remote-debugging-port=$Port"
    "--user-data-dir=`"$testProfilePath`""
    '--window-size=1920,1080'
    '--no-first-run'
    'https://www.wildberries.ru/'
)

# Окно нужно для ручной проверки сайта перед подключением Selenium.
Start-Process -FilePath $chromePath -ArgumentList $browserArguments -WindowStyle Normal
Write-Host "Откройте главную Wildberries в этом отдельном профиле и оставьте корзину пустой."
Write-Host "Запуск тестов: mvn test `"-DdebuggerAddress=127.0.0.1:$Port`""
