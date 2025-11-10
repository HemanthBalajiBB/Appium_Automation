param(
    [string]$TestClass = "",
    [string]$Platform = "Android",
    [switch]$StartAppium,
    [switch]$CheckSetup
)

function Test-Prerequisites {
    $issues = @()
    
    try {
        $javaVersion = java -version 2>&1 | Select-String "version"
        Write-Host "Java: $($javaVersion.Line.Split('"')[1])"
    } catch {
        $issues += "Java not found"
    }
    
    try {
        $nodeVersion = node --version
        Write-Host "Node.js: $nodeVersion"
    } catch {
        $issues += "Node.js not found"
    }
    
    try {
        $appiumVersion = appium --version
        Write-Host "Appium: $appiumVersion"
    } catch {
        $issues += "Appium not found"
    }
    
    try {
        $mvnVersion = mvn --version | Select-String "Apache Maven"
        Write-Host "Maven: $($mvnVersion.Line)"
    } catch {
        $issues += "Maven not found"
    }
    
    $serverRunning = netstat -an | findstr "4723"
    if ($serverRunning) {
        Write-Host "Appium Server: Running"
    } else {
        if ($StartAppium) {
            Write-Host "Starting Appium Server..."
            Start-Process -WindowStyle Minimized -FilePath "appium" -ArgumentList "--address", "127.0.0.1", "--port", "4723"
            Start-Sleep -Seconds 5
            
            $serverCheck = netstat -an | findstr "4723"
            if ($serverCheck) {
                Write-Host "Appium Server: Started"
            } else {
                $issues += "Failed to start Appium Server"
            }
        } else {
            $issues += "Appium Server not running"
        }
    }
    
    try {
        $devices = adb devices 2>$null | Select-String "device$"
        if ($devices) {
            Write-Host "Android Devices: $($devices.Count) connected"
        } else {
            $issues += "No Android devices connected"
        }
    } catch {
        $issues += "Android SDK not installed"
    }
    
    if ($issues.Count -gt 0) {
        Write-Host "Issues found:"
        foreach ($issue in $issues) {
            Write-Host "  $issue"
        }
        return $false
    }
    
    return $true
}

function Start-Tests {
    param([string]$Class, [string]$Platform)
    
    $mvnCommand = "mvn clean test"
    
    if ($Class) {
        $mvnCommand += " -Dtest=$Class"
        Write-Host "Running test class: $Class"
    } else {
        Write-Host "Running all tests"
    }
    
    Write-Host "Executing: $mvnCommand"
    
    Invoke-Expression $mvnCommand
}

if ($CheckSetup) {
    $setupOk = Test-Prerequisites
    if (-not $setupOk) {
        exit 1
    }
}

if ($StartAppium) {
    Test-Prerequisites | Out-Null
}

Start-Tests -Class $TestClass -Platform $Platform