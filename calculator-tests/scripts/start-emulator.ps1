$ErrorActionPreference = 'Stop'

$androidSdk = if ($env:ANDROID_HOME) { $env:ANDROID_HOME } else { Join-Path $env:LOCALAPPDATA 'Android\Sdk' }
$emulatorExecutable = Join-Path $androidSdk 'emulator\emulator.exe'

if (-not (Test-Path -LiteralPath $emulatorExecutable)) {
    throw 'Android Emulator не найден. Установите Android SDK или задайте ANDROID_HOME.'
}

& $emulatorExecutable -avd Google_Calculator_API_34 -port 5554 -gpu host -feature -Vulkan -no-snapshot -memory 2048 -cores 2
exit $LASTEXITCODE
